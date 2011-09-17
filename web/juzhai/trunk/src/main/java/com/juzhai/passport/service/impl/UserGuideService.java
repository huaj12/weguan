package com.juzhai.passport.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.dao.IUserGuideDao;
import com.juzhai.passport.mapper.UserGuideMapper;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.service.IUserGuideService;

@Service
public class UserGuideService implements IUserGuideService {

	@Autowired
	private UserGuideMapper userGuideMapper;
	@Autowired
	private IUserGuideDao userGuideDao;

	@Override
	public void craeteUserGuide(long uid) {
		UserGuide userGuide = new UserGuide();
		userGuide.setUid(uid);
		userGuide.setCreateTime(new Date());
		userGuide.setLastModifyTime(userGuide.getCreateTime());
		userGuideMapper.insertSelective(userGuide);
	}

	@Override
	public UserGuide getUserGuide(long uid) {
		// TODO 考虑缓存
		return userGuideMapper.selectByPrimaryKey(uid);
	}

	@Override
	public boolean isCompleteGuide(long uid) {
		UserGuide userGuide = getUserGuide(uid);
		return userGuide == null ? false : userGuide.getComplete();
	}

	@Override
	public void nextGuide(long uid) {
		userGuideDao.next(uid);
	}

	@Override
	public void completeGuide(long uid) {
		userGuideDao.complete(uid);
	}

}
