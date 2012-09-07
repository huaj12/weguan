package com.juzhai.search.bean;

import java.util.List;

import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class LuceneResult<T> {
	public static final SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
			"<i>", "</i>");
	private int totalHits;
	private List<T> result;

	public LuceneResult(int totalHits, List<T> result) {
		this.totalHits = totalHits;
		this.result = result;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

}
