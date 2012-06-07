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
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.PassportExample;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;

@Controller
@RequestMapping("/cms")
public class InitUserUseLevelController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private TpUserMapper tpUserMapper;
	@Autowired
	private PassportMapper passportMapper;
	private int maxRows = 200;

	@RequestMapping(value = "/initUserUseLevel", method = RequestMethod.GET)
	@ResponseBody
	public String initUserUseLevel(HttpServletRequest request, String keys) {
		log.error("init user use level start");
		// 第三方注册默认使用级别1
		initTpUser();
		// 邮箱验证过的默认使用级别1
		initEmail();
		log.error("init user use level end");
		return "success";
	}

	private void initEmail() {
		PassportExample example = new PassportExample();
		example.createCriteria().andEmailActiveEqualTo(true);
		Passport passport = new Passport();
		passport.setUseLevel(1);
		passportMapper.updateByExampleSelective(passport, example);
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
