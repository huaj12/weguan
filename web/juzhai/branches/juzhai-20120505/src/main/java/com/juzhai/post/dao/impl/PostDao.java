package com.juzhai.post.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.post.dao.IPostDao;

@Repository
public class PostDao implements IPostDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public void incrOrDecrResponseCnt(long postId, int p) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", postId);
		params.put("p", p);
		sqlSession.update("Post_Mapper.incrResponseCount", params);
	}

	@Override
	public int sumResponseCntByCreateUid(long uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		Object cnt = sqlSession.selectOne(
				"Post_Mapper.sumResponseCntByCreateUid", params);
		return cnt == null ? 0 : (Integer) cnt;
	}

	@Override
	public void incrOrDecrCommentCnt(long postId, int p) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", postId);
		params.put("p", p);
		sqlSession.update("Post_Mapper.incrCommentCount", params);
	}

}
