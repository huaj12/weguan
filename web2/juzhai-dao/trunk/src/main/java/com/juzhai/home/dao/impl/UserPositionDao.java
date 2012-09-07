package com.juzhai.home.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.home.dao.IUserPositionDao;

@Repository
public class UserPositionDao implements IUserPositionDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int insert(long uid, double lon, double lat) {
		Date cDate = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		// map.put("lon", lon);
		// map.put("lat", lat);
		map.put("point", "POINT(" + lon + " " + lat + ")");
		map.put("createTime", cDate);
		map.put("lastModifyTime", cDate);
		return sqlSession
				.insert("User_Position_Mapper.insertUserPosition", map);
	}

	@Override
	public int update(long uid, double lon, double lat) {
		Date cDate = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("point", "POINT(" + lon + " " + lat + ")");
		map.put("lastModifyTime", cDate);
		return sqlSession
				.update("User_Position_Mapper.updateUserPosition", map);
	}

}
