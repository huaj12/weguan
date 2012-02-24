package com.juzhai.passport.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.dao.IUserGuideDao;
import com.juzhai.passport.mapper.UserGuideMapper;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IUserGuideService;

@Service
public class UserGuideService implements IUserGuideService {

	@Autowired
	private UserGuideMapper userGuideMapper;
	@Autowired
	private IUserGuideDao userGuideDao;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IDialogService dialogService;

	@Override
	public void craeteUserGuide(long uid) {
		UserGuide userGuide = new UserGuide();
		userGuide.setUid(uid);
		userGuide.setCreateTime(new Date());
		userGuide.setLastModifyTime(userGuide.getCreateTime());
		userGuideMapper.insertSelective(userGuide);
	}

	@Override
	public void createAndCompleteGuide(long uid) {
		UserGuide userGuide = new UserGuide();
		userGuide.setUid(uid);
		userGuide.setComplete(true);
		userGuide.setGuideStep(1);
		userGuide.setCreateTime(new Date());
		userGuide.setLastModifyTime(userGuide.getCreateTime());
		userGuideMapper.insertSelective(userGuide);

		sendWelcomeDialog(uid);
	}

	@Override
	public UserGuide getUserGuide(long uid) {
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

		sendWelcomeDialog(uid);
	}

	private void sendWelcomeDialog(long uid) {
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (null != profileCache) {
			dialogService.sendOfficialSMS(profileCache.getUid(),
					DialogContentTemplate.WELCOME_USER,
					profileCache.getNickname());
		}
	}
}
