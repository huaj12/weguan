package com.juzhai.core.pager;

import java.util.ArrayList;
import java.util.List;

public class PagerManager {

	private int currentPage = 1;

	private int preOffset = 3;

	private int nextOffset = 3;

	private int results = 10;

	private int totalResults = 0;

	public PagerManager(int page, int results) {
		super();
		page = page <= 0 ? 1 : page;
		this.currentPage = page;
		this.results = results;
	}

	public PagerManager(int page, int results, int totalResults) {
		super();
		page = page <= 0 ? 1 : page;
		this.currentPage = page;
		this.results = results;
		this.totalResults = totalResults;
	}

	public PagerManager(int currentPage, int preOffset, int nextOffset,
			int results, int totalResults) {
		super();
		this.currentPage = currentPage;
		this.preOffset = preOffset;
		this.nextOffset = nextOffset;
		this.results = results;
		this.totalResults = totalResults;
	}

	public int getTotalPage() {
		if (0 == results) {
			return 0;
		}
		return totalResults % results == 0 ? totalResults / results
				: totalResults / results + 1;
	}

	public boolean isHasPre() {
		return currentPage > 1;
	}

	public boolean isHasNext() {
		return getTotalPage() > currentPage;
	}

	public List<String> getShowPages() {
		int startPage = currentPage - preOffset <= 0 ? 1 : currentPage
				- preOffset;
		int endPage = currentPage + nextOffset > getTotalPage() ? getTotalPage()
				: currentPage + nextOffset;

		int d_value = preOffset + nextOffset - (endPage - startPage);
		if (0 < d_value) {
			endPage = (endPage + d_value) > getTotalPage() ? getTotalPage()
					: (endPage + d_value);
			startPage = endPage == getTotalPage() ? (endPage - (preOffset + nextOffset)) < 1 ? 1
					: (endPage - (preOffset + nextOffset))
					: startPage;
		}

		List<String> showPages = new ArrayList<String>();
		for (int i = startPage; i <= endPage; i++) {
			showPages.add(String.valueOf(i));
		}
		return showPages;
	}

	public int getFirstResult() {
		return (currentPage - 1) * results;
	}

	public int getMaxResult() {
		return results;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (0 >= currentPage) {
			currentPage = 1;
		}
		this.currentPage = currentPage;
	}

	public int getResults() {
		return results;
	}

	public void setResults(int results) {
		this.results = results;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getPreOffset() {
		return preOffset;
	}

	public void setPreOffset(int preOffset) {
		if (0 > preOffset) {
			preOffset = 0;
		}
		this.preOffset = preOffset;
	}

	public int getNextOffset() {
		return nextOffset;
	}

	public void setNextOffset(int nextOffset) {
		if (0 > nextOffset) {
			nextOffset = 0;
		}
		this.nextOffset = nextOffset;
	}
}
