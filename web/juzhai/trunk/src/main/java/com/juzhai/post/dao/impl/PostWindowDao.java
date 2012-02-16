package com.juzhai.post.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.post.dao.IPostWindowDao;

@Repository
public class PostWindowDao implements IPostWindowDao {
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getMaxSequence() {
		Object obj = sqlSession.selectOne("PostWindow_Mapper.getMaxSequence");
		if (null == obj) {
			return 0;
		} else {
			return (Integer) obj;
		}
	}

}
