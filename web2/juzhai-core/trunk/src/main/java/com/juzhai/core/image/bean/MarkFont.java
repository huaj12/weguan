package com.juzhai.core.image.bean;

import java.awt.Color;
import java.awt.Font;

public class MarkFont {
	private int x;
	private int y;
	private Font font;
	private Color color;
	private String content;

	public MarkFont(int x, int y, Font font, Color color, String content) {
		this.x = x;
		this.y = y;
		this.font = font;
		this.color = color;
		this.content = content;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
