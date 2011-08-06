/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.KeyGenerator;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.FriendsBean;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.ITpUserAuthService;

@Service
public class FriendService implements IFriendService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IAuthorizeService authorizeService;
	@Autowired
	private MemcachedClient memcachedClient;

	@Override
	public FriendsBean getFriends(long uid, long tpId) {
		FriendsBean fb = null;
		if (tpId > 0) {
			final AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
			if (null != authInfo) {
				// TODO 重构
				fb = authorizeService.getFriends(authInfo);
				try {
					memcachedClient.set(KeyGenerator.genFriendsKey(uid), 0, fb);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		return fb;
	}

}
