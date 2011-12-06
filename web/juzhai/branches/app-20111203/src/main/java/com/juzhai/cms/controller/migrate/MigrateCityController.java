package com.juzhai.cms.controller.migrate;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kx4j.KxException;
import kx4j.KxSDK;
import kx4j.User;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.core.dao.Limit;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;

@Controller
@RequestMapping("/cms")
public class MigrateCityController {

	@Autowired
	private TpUserMapper tpUserMapper;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	private int errorCount=0;
	private int successCount=0;

	@RequestMapping(value = "migrateCityController")
	public String migrateCityController(HttpServletRequest request) {
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP
		.get(1l);
		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo(tp.getName());
		List<TpUser> list = tpUserMapper.selectByExample(example);
		List<String> uids = new ArrayList<String>();
		int i = 0;
		for (TpUser tpUser : list) {
			i++;
			uids.add(tpUser.getTpIdentity());
			if (i % 50 == 0) {
				updateUserInfo(tp, uids);
			}else if(i==list.size()){
				updateUserInfo(tp, uids);
			}
		}
		System.out.println(successCount+":"+errorCount);
		return null;
	}
	
	private void updateUserInfo(Thirdparty tp,List<String> uids){
		//指定拒宅器的session_key
		AuthInfo authInfo=tpUserAuthService.getAuthInfo(4790, tp.getId());
		KxSDK kxSDK = newKxSDK(tp.getAppKey(),tp.getAppSecret(), authInfo.getSessionKey());
		try {
			List<User> users = kxSDK.getUsers(
					StringUtils.join(uids, ","), "uid,birthday", 0,
					50);
			for (User user : users) {
				if (StringUtils.isEmpty(user.getBirthday())) {
					errorCount++;
					continue;
				}
				TpUser tUser = tpUserService
						.getTpUserByTpIdAndIdentity(tp.getId(),
								String.valueOf(user.getUid()));
				if(tUser==null){
					errorCount++;
					continue;
				}
				Profile profile = profileMapper
						.selectByPrimaryKey(tUser.getUid());
				if (StringUtils.isNotEmpty(user.getBirthday())) {
					try {
						String[] birthdays = user.getBirthday().split(
								"[^0-9]");
						String year = birthdays[0];
						if (year.length() < 3) {
							year = "19" + year;
						}
						int birthYear = Integer.valueOf(year);
						int brithMonth = Integer.valueOf(birthdays[1]);
						int brithDay = Integer.valueOf(birthdays[2]);
						if (birthYear > 1900) {
							profile.setBirthYear(birthYear);
						}
						if (brithMonth > 0 && brithMonth < 13) {
							profile.setBirthMonth(brithMonth);
						}
						if (brithDay > 0 && brithDay < 32) {
							profile.setBirthDay(brithDay);
						}
					} catch (Exception e) {
						errorCount++;
					}
				}
				profileMapper.updateByPrimaryKey(profile);
				successCount++;
				
			}
		} catch (KxException e) {
			e.printStackTrace();
			errorCount++;
		}
		uids.clear();
	}

	private KxSDK newKxSDK(String key, String secret, String sessionKey) {
		KxSDK kxSDK = new KxSDK();
		kxSDK.setOAuthConsumer(key, secret);
		kxSDK.setToken(sessionKey, "kaixin001");
		return kxSDK;
	}

	public static void main(String[] args) {
		KxSDK kxSDK = new KxSDK();
		kxSDK.setOAuthConsumer("299862518832a5f8c85dc57c1fe0ff61", "ef6a5fa8e10520232d1ae67728c30d29");
		kxSDK.setToken("118771921_100016778_118771921_1323137690_fb8c48e09cb78378751587ce4e4fd391", "kaixin001");
		String[] fields = new String[] { "name", "gender", "birthday",
				"hometown", "city", "logo120", "bodyform", "interest",
				"school", "company" };
		List<User>  users = null;
			try {
				users =kxSDK.getUsers("118792276", StringUtils.join(fields, ","), 0, 50);
			} catch (KxException e) {
				e.printStackTrace();
			}
	}
}
