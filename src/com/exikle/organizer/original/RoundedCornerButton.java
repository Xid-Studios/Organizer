package com.exikle.organizer.original;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Action;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
class RoundedCornerButton extends JButton {

	private static final float arcwidth = 16.0f;

	private static final float archeight = 16.0f;

	protected static final int focusstroke = 2;

	protected final Color fc = new Color(100, 150, 255, 200);

	protected final Color ac = new Color(230, 230, 230);

	protected final Color rc = Color.ORANGE;

	protected Shape shape;

	protected Shape border;

	protected Shape base;

	public RoundedCornerButton() {
		this(null, null);
	}

	public RoundedCornerButton(Icon icon) {
		this(null, icon);
	}

	public RoundedCornerButton(String text) {
		this(text, null);
	}

	public RoundedCornerButton(Action a) {
		this();
		setAction(a);
	}

	public RoundedCornerButton(String text, Icon icon) {
		setModel(new DefaultButtonModel());
		init(text, icon);
		setContentAreaFilled(false);
		setBackground(new Color(250, 250, 250));
		setOpaque(false);
		initShape();
	}

	protected void initShape() {
		if (!getBounds().equals(base)) {
			base = getBounds();
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1, arcwidth, archeight);
			border = new RoundRectangle2D.Float(focusstroke,
					focusstroke, getWidth() - 1 - focusstroke * 2,
					getHeight() - 1 - focusstroke * 2, arcwidth,
					archeight);
		}
	}

	private void paintFocusAndRollover(Graphics2D g2, Color color) {
		g2.setPaint(new GradientPaint(0, 0, color, getWidth() - 1,
				getHeight() - 1, color.brighter(), true));
		g2.fill(shape);
		g2.setColor(getBackground());
		g2.fill(border);
	}

	@Override
	protected void paintComponent(Graphics g) {
		initShape();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (getModel().isArmed()) {
			g2.setColor(ac);
			g2.fill(shape);
		} else if (isRolloverEnabled() && getModel().isRollover()) {
			paintFocusAndRollover(g2, rc);
		} else if (hasFocus()) {
			paintFocusAndRollover(g2, fc);
		} else {
			g2.setColor(getBackground());
			g2.fill(shape);
		}
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setColor(getBackground());
		super.paintComponent(g2);
	}

	@Override
	protected void paintBorder(Graphics g) {
		initShape();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getForeground());
		g2.draw(shape);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
	}

	@Override
	public boolean contains(int x, int y) {
		initShape();
		return shape.contains(x, y);
	}
}

@SuppressWarnings("serial")
class RoundButton extends RoundedCornerButton {

	public RoundButton() {
		this(null, null);
	}

	public RoundButton(Icon icon) {
		this(null, icon);
	}

	public RoundButton(String text) {
		this(text, null);
	}

	public RoundButton(Action a) {
		this();
		setAction(a);
	}

	public RoundButton(String text, Icon icon) {
		setModel(new DefaultButtonModel());
		init(text, icon);
		setFocusPainted(false);
		setOpaque(false);
		initShape();
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.width = d.height = Math.max(d.width, d.height);
		return d;
	}

	@Override
	protected void initShape() {
		if (!getBounds().equals(base)) {
			base = getBounds();
			shape = new Ellipse2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1);
			border = new Ellipse2D.Float(focusstroke, focusstroke,
					getWidth() - 1 - focusstroke * 2, getHeight() - 1
							- focusstroke * 2);
		}
	}
}