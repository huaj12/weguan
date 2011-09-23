/* 
 * Token.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.util;

import java.io.Serializable;

/**
 * 
 * @author xiaolin
 * 
 *         2008-5-14 create
 */
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	private byte[] text;
	private int offset;

	private int length;

	private int score;

	public Token(int offset, byte[] text) {
		this.offset = offset;
		this.text = text;
		this.length = text.length;
	}

	public byte[] getText() {
		return text;
	}

	public void setText(byte[] text) {
		this.text = text;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
