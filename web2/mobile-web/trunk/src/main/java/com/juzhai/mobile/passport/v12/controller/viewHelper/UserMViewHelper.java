package com.juzhai.mobile.passport.v12.controller.viewHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.common.model.City;
import com.juzhai.common.model.Province;
import com.juzhai.common.model.Town;
import com.juzhai.core.image.ImageUrl;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.service.IInterestUserRemoteService;
import com.juzhai.mobile.InitData;
import com.juzhai.mobile.passport.v12.controller.view.UserMView;
import com.juzhai.passport.bean.OnlineStatus;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthRemoteService;
import com.juzhai.passport.service.IUserOnlineRemoteService;
import com.juzhai.post.service.IPostRemoteService;

@Component("v12UserMViewHelper")
public class UserMViewHelper implements IUserMViewHelper {

	@Autowired
	private IInterestUserRemoteService interestUserRemoteService;
	@Autowired
	private IPostRemoteService postRemoteService;
	@Autowired
	private IUserOnlineRemoteService userOnlineRemoteService;
	@Autowired
	private ITpUserAuthRemoteService tpUserAuthRemoteService;

	@Override
	public UserMView createUserMView(UserContext context,
			ProfileCache profileCache, boolean isCompleteGuide) {
		if (null == profileCache) {
			return null;
		}
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
		Province province = InitData.getProvinceMap().get(
				profileCache.getProvince());
		if (null != province) {
			userView.setProvinceId(province.getId());
			userView.setProvinceName(province.getName());
		}
		City city = InitData.getCityMap().get(profileCache.getCity());
		if (null != city) {
			userView.setCityId(city.getId());
			userView.setCityName(city.getName());
		}
		Town town = InitData.getTownMap().get(profileCache.getTown());
		if (null != town) {
			userView.setTownId(town.getId());
			userView.setTownName(town.getName());
		}
		userView.setLogo(ImageUrl.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.MIDDLE.getType()));
		userView.setSmallLogo(ImageUrl.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.SMALL.getType()));
		userView.setBigLogo(ImageUrl.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.BIG.getType()));
		userView.setOriginalLogo(ImageUrl.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.ORIGINAL.getType()));
		userView.setNewLogo(ImageUrl.userLogo(profileCache.getUid(),
				profileCache.getNewLogoPic(), LogoSizeType.MIDDLE.getType()));
		userView.setLogoVerifyState(profileCache.getLogoVerifyState());
		userView.setHasLogo(StringUtils.isNotEmpty(profileCache.getLogoPic()));
		Constellation con = InitData.getConstellationMap().get(
				profileCache.getConstellationId());
		if (null != con) {
			userView.setConstellation(con.getName());
		}
		userView.setProfessionId(profileCache.getProfessionId());
		userView.setProfession(profileCache.getProfession());
		if (context != null && context.hasLogin()) {
			if (context.getUid() == profileCache.getUid().longValue()) {
				userView.setInterestUserCount(interestUserRemoteService
						.countInterestUser(userView.getUid()));
				userView.setInterestMeCount(interestUserRemoteService
						.countInterestMeUser(userView.getUid()));
				userView.setPostCount(postRemoteService.countUserPost(userView
						.getUid()));
				userView.setTpId(context.getTpId());
				Thirdparty tp = InitData.getTpMap().get(context.getTpId());
				if (null != tp) {
					userView.setTpName(tp.getName());
				}
				if (context.getTpId() > 0) {
					userView.setTokenExpired(tpUserAuthRemoteService
							.isTokenExpired(context.getUid(), context.getTpId()));
				}
			} else {
				userView.setHasInterest(interestUserRemoteService.isInterest(
						context.getUid(), profileCache.getUid()));
			}
		}
		userView.setOnlineStatus(OnlineStatus
				.getStatus(
						userOnlineRemoteService.getLastUserOnlineTime(userView
								.getUid())).getType());
		return userView;
	}
}
