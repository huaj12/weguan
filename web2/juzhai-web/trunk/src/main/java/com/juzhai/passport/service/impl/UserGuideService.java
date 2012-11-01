package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.juzhai.common.InitData;
import com.juzhai.common.model.City;
import com.juzhai.core.bean.UseLevel;
import com.juzhai.core.stats.counter.service.ICounter;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.service.IDialogService;
import com.juzhai.home.service.IGuessYouService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.dao.IUserGuideDao;
import com.juzhai.passport.mapper.UserGuideMapper;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.service.IPassportService;
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
	@Autowired
	private IPassportService passportService;
	@Autowired
	private ICounter guideCounter;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IGuessYouService guessYouService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	// @Override
	// public void craeteUserGuide(long uid) {
	// UserGuide userGuide = new UserGuide();
	// userGuide.setUid(uid);
	// userGuide.setCreateTime(new Date());
	// userGuide.setLastModifyTime(userGuide.getCreateTime());
	// userGuideMapper.insertSelective(userGuide);
	// }

	@Override
	public void createAndCompleteGuide(long uid) {
		UserGuide userGuide = new UserGuide();
		userGuide.setUid(uid);
		userGuide.setComplete(true);
		userGuide.setGuideStep(1);
		userGuide.setCreateTime(new Date());
		userGuide.setLastModifyTime(userGuide.getCreateTime());
		try {
			userGuideMapper.insertSelective(userGuide);
		} catch (DuplicateKeyException e) {
			return;
		}

		afterGuide(uid);
	}

	private void afterGuide(long uid) {
		sendWelcomeDialog(uid);
		statGuide();
		obtainLikeUser(uid);
		// 完成引导后提升用户使用等级
		passportService.setUseLevel(uid, UseLevel.Level1);
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

	// @Override
	// public void nextGuide(long uid) {
	// userGuideDao.next(uid);
	// }

	@Override
	public void completeGuide(long uid) {
		userGuideDao.complete(uid);
		afterGuide(uid);
	}

	private void sendWelcomeDialog(long uid) {
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (null != profileCache) {
			Long cityId = profileCache.getCity();
			String qq = InitData.SPECIAL_CITY_QQ_MAP.get(cityId);

			City city = InitData.CITY_MAP.get(profileCache.getCity());
			String qqContent = StringUtils.EMPTY;
			if (StringUtils.isNotEmpty(qq) && null != city
					&& profileCache.getGender() == 0) {
				qqContent = messageSource.getMessage(
						DialogContentTemplate.WELCOME_USER.getName() + ".qq",
						new Object[] { city.getName(), qq },
						Locale.SIMPLIFIED_CHINESE);
			}
			dialogService.sendOfficialSMS(profileCache.getUid(),
					DialogContentTemplate.WELCOME_USER, qqContent);
		}
	}

	private void statGuide() {
		guideCounter.incr(null, 1L);
	}

	private void obtainLikeUser(final long uid) {
		if (!guessYouService.existRescueUsers(uid)) {
			// 启动一个线程来获取和保存
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					guessYouService.updateLikeUsers(uid);
				}
			});
		}
	}
}
