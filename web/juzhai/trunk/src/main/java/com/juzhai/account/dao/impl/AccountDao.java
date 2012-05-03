package com.juzhai.account.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juzhai.account.dao.IAccountDao;

//@Repository
public class AccountDao implements IAccountDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int updatePoint(long uid, int point) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("p", point);
		return sqlSession.update("Account_Mapper.updatePoint", params);
	}

}
