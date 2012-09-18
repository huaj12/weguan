package com.juzhai.android.core.model;

import java.util.List;

public class PagerManager {
	private Boolean hasPre;
	private Boolean hasNext;
	private List<String> showPages;
	private Integer results;
	private Integer preOffset;
	private Integer maxResult;
	private Integer firstResult;
	private Integer totalResults;
	private Integer totalPage;
	private Integer currentPage;
	private Integer nextOffset;

	public Boolean getHasPre() {
		return hasPre;
	}

	public void setHasPre(Boolean hasPre) {
		this.hasPre = hasPre;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public List<String> getShowPages() {
		return showPages;
	}

	public void setShowPages(List<String> showPages) {
		this.showPages = showPages;
	}

	public Integer getResults() {
		return results;
	}

	public void setResults(Integer results) {
		this.results = results;
	}

	public Integer getPreOffset() {
		return preOffset;
	}

	public void setPreOffset(Integer preOffset) {
		this.preOffset = preOffset;
	}

	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getNextOffset() {
		return nextOffset;
	}

	public void setNextOffset(Integer nextOffset) {
		this.nextOffset = nextOffset;
	}

}
