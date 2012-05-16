package com.juzhai.post.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.post.dao.IIdeaDao;

@Repository
public class IdeaDao implements IIdeaDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public void incrOrDecrUseCount(long ideaId, int p) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", ideaId);
		params.put("p", p);
		sqlSession.update("Idea_Mapper.incrUseCount", params);
	}

	@Override
	public void addFirstUser(long ideaId, long uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", ideaId);
		params.put("uid", uid);
		sqlSession.update("Idea_Mapper.addFirstUser", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, Integer> getRecentPopIdeaId(long categoryId,
			Date startTime, Date endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", categoryId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		Object reslutMap = sqlSession.selectOne("Idea_Mapper.getRecentPopIdea",
				params);
		return (Map<Long, Integer>) reslutMap;
	}

}
