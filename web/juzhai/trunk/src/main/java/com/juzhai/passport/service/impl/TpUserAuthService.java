/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.mapper.TpUserAuthMapper;
import com.juzhai.passport.model.TpUserAuth;
import com.juzhai.passport.model.TpUserAuthExample;
import com.juzhai.passport.service.ITpUserAuthService;

@Service
public class TpUserAuthService implements ITpUserAuthService {

	private final Log log = LogFactory.getLog(getClass());

	private static final String SESSION_AUTHINFO_NAME = "authInfo";

	@Autowired
	private TpUserAuthMapper tpUserAuthMapper;

	@Override
	public void updateTpUserAuth(long uid, long tpId, AuthInfo authInfo) {
		if (null == authInfo) {
			return;
		}
		String authInfoString = null;
		try {
			authInfoString = authInfo.toJsonString();
		} catch (JsonGenerationException e) {
			log.error(e.getMessage(), e);
			return;
		}
		TpUserAuthExample example = new TpUserAuthExample();
		example.createCriteria().andUidEqualTo(uid).andTpIdEqualTo(tpId);
		List<TpUserAuth> list = tpUserAuthMapper.selectByExample(example);
		TpUserAuth tpUserAuth;
		if (CollectionUtils.isNotEmpty(list)) {
			tpUserAuth = list.get(0);
			tpUserAuth.setAuthInfo(authInfoString);
			tpUserAuth.setLastModifyTime(new Date());
			tpUserAuthMapper.updateByPrimaryKeySelective(tpUserAuth);
		} else {
			tpUserAuth = new TpUserAuth();
			tpUserAuth.setUid(uid);
			tpUserAuth.setTpId(tpId);
			tpUserAuth.setAuthInfo(authInfoString);
			tpUserAuth.setCreateTime(new Date());
			tpUserAuth.setLastModifyTime(tpUserAuth.getCreateTime());
			tpUserAuthMapper.insertSelective(tpUserAuth);
		}
	}

	@Override
	public void cacheAuthInfo(long uid, AuthInfo authInfo) {
		// TODO use memcached
	}

	@Override
	public void saveAuthInfoToSession(HttpServletRequest request,
			AuthInfo authInfo) {
		HttpRequestUtil.setSessionAttribute(request, SESSION_AUTHINFO_NAME,
				authInfo);
	}

}
