package com.juzhai.post.service;

import java.util.List;

import com.juzhai.cms.controller.form.PostWindowSortForm;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.exception.InputPostWindowException;
import com.juzhai.post.model.PostWindow;

public interface IPostWindowService {
	/**
	 * 获取橱窗推荐内容
	 * @return
	 */
	List<PostWindow> listPostWindow();
	
	/**
	 * 取消橱窗推荐
	 * @param id
	 */
	void removePostWindow(long id) throws InputPostWindowException;
	/**
	 * 添加橱窗推荐
	 * @param postId
	 */
	void addPostWindow(long postId)throws InputPostException ;
	/**
	 * 橱窗推荐排序
	 * @param postWindowSortForm
	 */
	void sortPostWindow(List<PostWindowSortForm> list);
}
