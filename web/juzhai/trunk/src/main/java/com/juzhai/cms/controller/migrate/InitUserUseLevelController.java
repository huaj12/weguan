package com.juzhai.cms.controller.migrate;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.mapper.UserGuideMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.PassportExample;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.model.UserGuideExample;

@Controller
@RequestMapping("/cms")
public class InitUserUseLevelController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private TpUserMapper tpUserMapper;
	@Autowired
	private PassportMapper passportMapper;
	@Autowired
	private UserGuideMapper userGuideMapper;
	private int maxRows = 200;

	@RequestMapping(value = "/initUserUseLevel", method = RequestMethod.GET)
	@ResponseBody
	public String initUserUseLevel(HttpServletRequest request, String keys) {
		log.error("init user use level start");
		resetPassProt();
		// 第三方注册默认使用级别1
		initTpUser();
		// 通过引导的默认使用级别为1
		initUserGuide();
		log.error("init user use level end");
		return "success";
	}

	private void resetPassProt() {
		Passport passport = new Passport();
		passport.setUseLevel(0);
		passportMapper
				.updateByExampleSelective(passport, new PassportExample());
	}

	private void initUserGuide() {
		UserGuideExample example = new UserGuideExample();
		int i = 0;
		example.setOrderByClause("uid desc");
		Passport passport = new Passport();
		passport.setUseLevel(1);
		while (true) {
			example.setLimit(new Limit(i, maxRows));
			List<UserGuide> list = userGuideMapper.selectByExample(example);
			List<Long> ids = new ArrayList<Long>(list.size());
			for (UserGuide user : list) {
				ids.add(user.getUid());
			}
			PassportExample pExample = new PassportExample();
			pExample.createCriteria().andIdIn(ids);
			passportMapper.updateByExampleSelective(passport, pExample);
			if (ids.size() < maxRows) {
				break;
			}
			i += maxRows;
		}
	}

	private void initTpUser() {
		TpUserExample example = new TpUserExample();
		int i = 0;
		example.setOrderByClause("uid desc");
		Passport passport = new Passport();
		passport.setUseLevel(1);
		while (true) {
			example.setLimit(new Limit(i, maxRows));
			List<TpUser> list = tpUserMapper.selectByExample(example);
			List<Long> ids = new ArrayList<Long>(list.size());
			for (TpUser user : list) {
				ids.add(user.getUid());
			}
			PassportExample pExample = new PassportExample();
			pExample.createCriteria().andIdIn(ids);
			passportMapper.updateByExampleSelective(passport, pExample);
			if (ids.size() < maxRows) {
				break;
			}
			i += maxRows;

		}

	}
}
