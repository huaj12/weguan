package com.juzhai.cms.controller.migrate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.model.UserActExample;
import com.juzhai.core.dao.Limit;
import com.juzhai.passport.InitData;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;

//@Controller
@RequestMapping("/cms")
public class MigrateWebDataController {

	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private UserActMapper userActMapper;

	@ResponseBody
	@RequestMapping(value = "migUserAct")
	public String migUserActGenderAndCity(HttpServletRequest request) {
		ProfileExample example = new ProfileExample();
		example.setOrderByClause("uid asc");
		int firstResult = 0;
		int maxResults = 200;
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			List<Profile> profileList = profileMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(profileList)) {
				break;
			}
			for (Profile profile : profileList) {
				UserActExample userActExample = new UserActExample();
				userActExample.createCriteria().andUidEqualTo(profile.getUid());
				UserAct userAct = new UserAct();
				userAct.setCity(profile.getCity());
				userAct.setGender(profile.getGender());
				userActMapper.updateByExampleSelective(userAct, userActExample);
			}
			firstResult += maxResults;
		}

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "migConstellation")
	public String migConstellation(HttpServletRequest request) {
		ProfileExample example = new ProfileExample();
		example.setOrderByClause("uid asc");
		int firstResult = 0;
		int maxResults = 200;
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			List<Profile> profileList = profileMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(profileList)) {
				break;
			}
			for (Profile profile : profileList) {
				Integer month = profile.getBirthMonth();
				Integer date = profile.getBirthDay();
				if (null != month && month > 0 && null != date && date > 0) {
					Constellation c = InitData.getConstellation(month, date);
					if (null != c) {
						Profile updateProfile = new Profile();
						updateProfile.setUid(profile.getUid());
						updateProfile.setConstellationId(c.getId());
						profileMapper
								.updateByPrimaryKeySelective(updateProfile);
					}
				}
			}
			firstResult += maxResults;
		}

		return "success";
	}
}
