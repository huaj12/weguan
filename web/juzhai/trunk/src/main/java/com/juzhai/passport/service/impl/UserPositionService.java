package com.juzhai.passport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.dao.IUserPositionDao;
import com.juzhai.passport.mapper.UserPositionMapper;
import com.juzhai.passport.model.UserPositionExample;
import com.juzhai.passport.service.IUserPositionService;

@Service
public class UserPositionService implements IUserPositionService {

	@Autowired
	private UserPositionMapper userPositionMapper;
	@Autowired
	private IUserPositionDao userPositionDao;

	@Override
	public void updatePosition(long uid, double longitude, double latitude) {
		UserPositionExample example = new UserPositionExample();
		example.createCriteria().andUidEqualTo(uid);
		if (userPositionMapper.countByExample(example) > 0) {
			userPositionDao.update(uid, longitude, latitude);
		} else {
			userPositionDao.insert(uid, longitude, latitude);
		}
	}

}
