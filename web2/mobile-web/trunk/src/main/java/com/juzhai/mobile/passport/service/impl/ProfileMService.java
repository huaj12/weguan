package com.juzhai.mobile.passport.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.common.model.City;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.mobile.InitData;
import com.juzhai.mobile.passport.controller.form.ProfileMForm;
import com.juzhai.mobile.passport.service.IProfileMService;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.service.IProfileRemoteService;
import com.juzhai.passport.service.IUserGuideRemoteService;

@Service
public class ProfileMService implements IProfileMService {

	@Autowired
	private IProfileRemoteService profileService;
	@Autowired
	private IUserGuideRemoteService userGuideService;
	@Autowired
	private IImageManager imageManager;

	@Override
	public void updateLogoAndProfile(long uid, ProfileMForm profileForm)
			throws UploadImageException, ProfileInputException {
		String logoPath = null;
		if (profileForm != null && profileForm.getLogo() != null
				&& !profileForm.getLogo().isEmpty()) {
			String[] paths = imageManager
					.uploadTempImage(profileForm.getLogo());
			if (paths != null && paths.length == 2) {
				logoPath = paths[1];
			}
		}
		profileService.updateLogoAndProfile(uid,
				convertToProfile(uid, profileForm), logoPath);
	}

	@Override
	public void guideLogoAndProfile(long uid, ProfileMForm profileForm)
			throws UploadImageException, ProfileInputException {
		String logoPath = null;
		if (profileForm != null && profileForm.getLogo() != null) {
			String[] paths = imageManager
					.uploadTempImage(profileForm.getLogo());
			if (paths != null && paths.length == 2) {
				logoPath = paths[1];
			}
		}
		profileService.guideLogoAndProfile(uid,
				convertToProfile(uid, profileForm), logoPath);
		UserGuide userGuide = userGuideService.getUserGuide(uid);
		if (userGuide == null) {
			userGuideService.createAndCompleteGuide(uid);
		} else {
			userGuideService.completeGuide(uid);
		}
	}

	private Profile convertToProfile(long uid, ProfileMForm profileForm)
			throws UploadImageException {
		Profile profile = profileService.getProfile(uid);
		profile.setNickname(profileForm.getNickname());
		profile.setGender(profileForm.getGender());
		profile.setFeature(profileForm.getFeature());
		profile.setProfessionId(profileForm.getProfessionId());
		profile.setProfession(profileForm.getProfession());
		if (StringUtils.isNotEmpty(profileForm.getBirth())) {
			try {
				Date birth = DateUtils.parseDate(profileForm.getBirth(),
						DateFormat.DATE_PATTERN);
				Calendar c = DateUtils.toCalendar(birth);
				profile.setBirthYear(c.get(Calendar.YEAR));
				profile.setBirthMonth(c.get(Calendar.MONTH) + 1);
				profile.setBirthDay(c.get(Calendar.DAY_OF_MONTH));
			} catch (ParseException e) {
			}
		}
		City city = InitData.getCityMap().get(profileForm.getCityId());
		if (null == city) {
			profile.setCity(0L);
			profile.setProvince(0L);
			profile.setTown(-1L);
		} else {
			profile.setCity(city.getId());
			profile.setProvince(city.getProvinceId());
			if (InitData.hasTown(profile.getCity())) {
				profile.setTown(0L);
			} else {
				profile.setTown(-1L);
			}
		}
		return profile;
	}
}
