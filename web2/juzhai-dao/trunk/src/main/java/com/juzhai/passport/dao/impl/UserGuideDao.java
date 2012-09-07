/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.passport.dao.IUserGuideDao;

@Repository
public class UserGuideDao implements IUserGuideDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int next(long uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		return sqlSession.update("UserGuide_Mapper.next", params);
	}

	@Override
	public int complete(long uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		return sqlSession.update("UserGuide_Mapper.complete", params);
	}

}
