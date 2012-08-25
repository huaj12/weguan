package com.juzhai.mobile.passport.controller.viewHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.passport.controller.view.UserMView;
import com.juzhai.passport.bean.OnlineStatus;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.IUserOnlineService;
import com.juzhai.post.service.IPostService;

@Component
public class UserMViewHelper implements IUserMViewHelper {

	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IUserOnlineService userOnlineService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;

	@Override
	public UserMView createUserMView(UserContext context,
			ProfileCache profileCache, boolean isCompleteGuide) {
		UserMView userView = new UserMView();
		userView.setHasGuided(isCompleteGuide);
		userView.setUid(profileCache.getUid());
		userView.setNickname(profileCache.getNickname());
		if (null != profileCache.getGender()) {
			userView.setGender(profileCache.getGender());
		}
		userView.setBirthYear(profileCache.getBirthYear());
		userView.setBirthMonth(profileCache.getBirthMonth());
		userView.setBirthDay(profileCache.getBirthDay());
		userView.setFeature(profileCache.getFeature());
		Province province = com.juzhai.common.InitData.PROVINCE_MAP
				.get(profileCache.getProvince());
		if (null != province) {
			userView.setProvinceId(province.getId());
			userView.setProvinceName(province.getName());
		}
		City city = com.juzhai.common.InitData.CITY_MAP.get(profileCache
				.getCity());
		if (null != city) {
			userView.setCityId(city.getId());
			userView.setCityName(city.getName());
		}
		Town town = com.juzhai.common.InitData.TOWN_MAP.get(profileCache
				.getTown());
		if (null != town) {
			userView.setTownId(town.getId());
			userView.setTownName(town.getName());
		}
		userView.setLogo(JzResourceFunction.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.MIDDLE.getType()));
		userView.setSmallLogo(JzResourceFunction.userLogo(
				profileCache.getUid(), profileCache.getLogoPic(),
				LogoSizeType.SMALL.getType()));
		userView.setBigLogo(JzResourceFunction.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.BIG.getType()));
		userView.setOriginalLogo(JzResourceFunction.userLogo(
				profileCache.getUid(), profileCache.getLogoPic(),
				LogoSizeType.ORIGINAL.getType()));
		userView.setNewLogo(JzResourceFunction.userLogo(profileCache.getUid(),
				profileCache.getNewLogoPic(), LogoSizeType.MIDDLE.getType()));
		userView.setLogoVerifyState(profileCache.getLogoVerifyState());
		userView.setHasLogo(StringUtils.isNotEmpty(profileCache.getLogoPic()));
		Constellation con = com.juzhai.passport.InitData.CONSTELLATION_MAP
				.get(profileCache.getConstellationId());
		if (null != con) {
			userView.setConstellation(con.getName());
		}
		userView.setProfessionId(profileCache.getProfessionId());
		userView.setProfession(profileCache.getProfession());
		if (context != null && context.hasLogin()) {
			if (context.getUid() == profileCache.getUid().longValue()) {
				userView.setInterestUserCount(interestUserService
						.countInterestUser(userView.getUid()));
				userView.setInterestMeCount(interestUserService
						.countInterestMeUser(userView.getUid()));
				userView.setPostCount(postService.countUserPost(userView
						.getUid()));
				userView.setTpId(context.getTpId());
				userView.setTpName(context.getTpName());
				if (context.getTpId() > 0) {
					userView.setTokenExpired(tpUserAuthService.isTokenExpired(
							context.getUid(), context.getTpId()));
				}
			} else {
				userView.setHasInterest(interestUserService.isInterest(
						context.getUid(), profileCache.getUid()));
			}
		}
		userView.setOnlineStatus(OnlineStatus.getStatus(
				userOnlineService.getLastUserOnlineTime(userView.getUid()))
				.getType());
		return userView;
	}
}
