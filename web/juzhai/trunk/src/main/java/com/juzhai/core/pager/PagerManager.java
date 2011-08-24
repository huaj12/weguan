package com.juzhai.core.pager;

import java.util.ArrayList;
import java.util.List;

public class PagerManager {

	private int currentPage = 1;

	private int preOffset = 4;

	private int nextOffset = 4;

	private int results = 10;

	private int totalResults = 0;

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
		return (currentPage - 1) * results + 1;
	}

	public int getMaxResult() {
		return currentPage * results;
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
