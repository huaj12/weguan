package com.juzhai.post.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.post.dao.IIdeaPositionDao;

@Repository
public class IdeaPositionDao implements IIdeaPositionDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int insert(long ideaId, double lon, double lat) {
		Date cDate = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ideaId", ideaId);
		map.put("point", "POINT(" + lon + " " + lat + ")");
		map.put("createTime", cDate);
		map.put("lastModifyTime", cDate);
		return sqlSession
				.insert("Idea_Position_Mapper.insertIdeaPosition", map);
	}

	@Override
	public int update(long ideaId, double lon, double lat) {
		Date cDate = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ideaId", ideaId);
		map.put("point", "POINT(" + lon + " " + lat + ")");
		map.put("lastModifyTime", cDate);
		return sqlSession
				.update("Idea_Position_Mapper.updateIdeaPosition", map);
	}

	@Override
	public String getLocation(long ideaId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ideaId", ideaId);
		String obj = sqlSession.selectOne(
				"Idea_Position_Mapper.getIdeaLocation", map);
		return obj;
	}

}
