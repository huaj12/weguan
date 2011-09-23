package com.juzhai.wordfilter.core;

public class MultiWordsItem {
	public int[] wordIds;
	public int id;
	public int score;

	public int hashCode() {
		return id;
	}

	public boolean equals(Object obj) {
		return ((MultiWordsItem) obj).id == id;
	}
}
