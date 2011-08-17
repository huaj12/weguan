/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.mapper.TpUserAuthMapper;
import com.juzhai.passport.model.TpUserAuth;
import com.juzhai.passport.model.TpUserAuthExample;
import com.juzhai.passport.service.ITpUserAuthService;

@Service
public class TpUserAuthService implements ITpUserAuthService {

	private final Log log = LogFactory.getLog(getClass());

	private final String SESSION_AUTHINFO_NAME = "authInfo";

	@Autowired
	private TpUserAuthMapper tpUserAuthMapper;
	@Autowired
	private MemcachedClient memcachedClient;

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
		TpUserAuth tpUserAuth = getTpUserAuth(uid, tpId);
		if (null != tpUserAuth) {
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
		if (null != authInfo) {
			try {
				memcachedClient.set(MemcachedKeyGenerator.genAuthInfoKey(uid),
						30 * 60, authInfo.toJsonString());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public AuthInfo getAuthInfo(long uid, long tpId) {
		AuthInfo authInfo = null;
		if (tpId > 0) {
			try {
				String authInfoJsonString = memcachedClient.getAndTouch(
						MemcachedKeyGenerator.genAuthInfoKey(uid), 30 * 60);
				if (StringUtils.isNotEmpty(authInfoJsonString)) {
					authInfo = AuthInfo.convertToBean(authInfoJsonString);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (null == authInfo) {
				TpUserAuth tpUserAuth = getTpUserAuth(uid, tpId);
				if (null != tpUserAuth) {
					try {
						authInfo = AuthInfo.convertToBean(tpUserAuth
								.getAuthInfo());
						cacheAuthInfo(uid, authInfo);
					} catch (JsonGenerationException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
		return authInfo;
	}

	private TpUserAuth getTpUserAuth(long uid, long tpId) {
		TpUserAuthExample example = new TpUserAuthExample();
		example.createCriteria().andUidEqualTo(uid).andTpIdEqualTo(tpId);
		List<TpUserAuth> list = tpUserAuthMapper.selectByExample(example);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@Override
	public void saveAuthInfoToSession(HttpServletRequest request,
			AuthInfo authInfo) {
		HttpRequestUtil.setSessionAttribute(request, SESSION_AUTHINFO_NAME,
				authInfo);
	}

}
