package com.juzhai.dpsdk.model;

public class Paging implements java.io.Serializable {

	private static final long serialVersionUID = 5346354335997700326L;
	private int page = -1; // 页码。注意：最多返回200条分页内容。
	private int count = -1; // 指定每页返回的记录条数。

	public Paging() {
	}

	public Paging(int page) {
		setPage(page);
	}

	public Paging(int page, int count) {
		this(page);
		setCount(count);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page < 1) {
			throw new IllegalArgumentException(
					"page should be positive integer. passed:" + page);
		}
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		if (count < 1) {
			throw new IllegalArgumentException(
					"count should be positive integer. passed:" + count);
		}
		this.count = count;
	}

	public Paging count(int count) {
		setCount(count);
		return this;
	}

}
