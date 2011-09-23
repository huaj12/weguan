package com.juzhai.wordfilter.core;

public class SpamStruct {
	private String text; // the content of spam word
	private int score; // the weight of spam word

	public SpamStruct() {

	}

	public SpamStruct(String text, int score) {
		this.text = text;
		this.score = score;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
