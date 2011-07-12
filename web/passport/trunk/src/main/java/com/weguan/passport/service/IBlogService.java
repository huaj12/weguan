package com.weguan.passport.service;

import com.weguan.passport.exception.BlogException;
import com.weguan.passport.form.ModifyForm;
import com.weguan.passport.model.Blog;

public interface IBlogService {

	/**
	 * 根据用户ID获取BLOG对象
	 * 
	 * @param uid
	 * @return
	 */
	public Blog getBlogByUid(long uid);

	/**
	 * 更新博客设置
	 * 
	 * @param uid
	 * @param modifyForm
	 */
	public void modify(long uid, ModifyForm modifyForm) throws BlogException;
}
