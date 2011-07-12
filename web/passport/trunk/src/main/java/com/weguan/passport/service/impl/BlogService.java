package com.weguan.passport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weguan.passport.exception.BlogException;
import com.weguan.passport.form.ModifyForm;
import com.weguan.passport.mapper.BlogMapper;
import com.weguan.passport.model.Blog;
import com.weguan.passport.service.IBlogService;

@Service
public class BlogService implements IBlogService {

	@Autowired
	private BlogMapper blogMapper;

	@Override
	public Blog getBlogByUid(long uid) {
		return blogMapper.selectByPrimaryKey(uid);
	}

	@Override
	public void modify(long uid, ModifyForm modifyForm) throws BlogException {
		// TODO Auto-generated method stub
		
	}

}
