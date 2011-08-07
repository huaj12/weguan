/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.KeyGenerator;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.FriendsBean;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;
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
	@Autowired
	private TpUserMapper tpUserMapper;

	@Override
	public FriendsBean getFriends(long uid, long tpId) {
		FriendsBean fb = null;
		try {
			fb = memcachedClient.get(KeyGenerator.genFriendsKey(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == fb) {
			final AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
			if (null != authInfo) {
				List<String> allFriendIds = authorizeService
						.getAllFriends(authInfo);
				List<String> appFriendIds = authorizeService
						.getAppFriends(authInfo);

				fb = new FriendsBean();
				fb.setUninstallFriends(getNonAppFriends(allFriendIds,
						appFriendIds));
				fb.setInstallFriends(getAppFriendUids(appFriendIds, tpId));
				try {
					memcachedClient.set(KeyGenerator.genFriendsKey(uid),
							24 * 3600, fb);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return fb;
	}

	private List<String> getNonAppFriends(List<String> allFriendIds,
			List<String> appFriendIds) {
		List<String> nonAppFriends = new ArrayList<String>();
		if (CollectionUtils.isEmpty(appFriendIds)) {
			nonAppFriends.addAll(allFriendIds);
		} else {
			for (String tpUid : allFriendIds) {
				if (!appFriendIds.contains(tpUid)) {
					nonAppFriends.add(tpUid);
				}
			}
		}
		return nonAppFriends;
	}

	private List<Long> getAppFriendUids(List<String> appFriendIds, long tpId) {
		if (CollectionUtils.isEmpty(appFriendIds)) {
			return Collections.emptyList();
		}
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return Collections.emptyList();
		}
		List<Long> appFriendUids = new ArrayList<Long>(appFriendIds.size());

		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo(tp.getName())
				.andTpIdentityIn(appFriendIds);
		List<TpUser> tpUserList = tpUserMapper.selectByExample(example);
		for (TpUser tpUser : tpUserList) {
			appFriendUids.add(tpUser.getUid());
		}
		return appFriendUids;
	}
}
