package com.juzhai.core.pager;

import java.util.ArrayList;
import java.util.List;

public class PagerManager {

	private int currentPage = 1;

	private int preOffset = 4;

	private int nextOffset = 4;

	private int results = 10;

	private int totalResults = 0;
	
	public String stringUrl;

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
	

	public String getStringUrl() {
		return stringUrl;
	}

	public void setStringUrl(String url) {
		StringBuffer tag=new StringBuffer();
		String divBegin="<div class='page'>";
		String divEnd="</div>";
		tag.append(divBegin);
		if(1!=currentPage){
			tag.append("<a href=\""+url+"&page=1"+"\" class=\"link\"><p class=\"l\"></p><strong class=\"m\">首页</strong><p class=\"r\"></p></a>");
			tag.append("<a href=\""+url+"&page="+(currentPage-1)+"\" class=\"link\"><p class=\"l\"></p><strong class=\"m\">上一页</strong><p class=\"r\"></p></a>");
		}
		List<String> pages=getShowPages();
		for(int i=0;i<pages.size();i++){
			if(Integer.parseInt(pages.get(i))==currentPage){
				tag.append("<a href=\"#\" class=\"active\"><p class=\"l\"></p><strong class=\"m\">"+pages.get(i)+"</strong><p class=\"r\"></p></a>");	
			}else{
				tag.append("<a href=\""+url+"&page="+pages.get(i)+"\" class=\"link\"><p class=\"l\"></p><strong class=\"m\">"+pages.get(i)+"</strong><p class=\"r\"></p></a>");
			}
		}
		if(getTotalPage()==currentPage){
			tag.append("<a href=\""+url+"&page="+(currentPage+1)+"\" class=\"link\"><p class=\"l\"></p><strong class=\"m\">下一页</strong><p class=\"r\"></p></a>");
			tag.append("<a href=\""+url+"&page="+getTotalPage()+"\" class=\"link\"><p class=\"l\"></p><strong class=\"m\">尾页</strong><p class=\"r\"></p></a>");
		}
		tag.append(divEnd);
		this.stringUrl =tag.toString();
	}
	

}
