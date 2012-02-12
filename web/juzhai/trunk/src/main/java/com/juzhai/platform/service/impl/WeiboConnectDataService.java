package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import weibo4j.Timeline;
import weibo4j.model.Status;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.notice.NoticeConfig;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ThirdpartyNameEnum;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserWeibo;
import com.juzhai.platform.service.IDataService;

@Service
public class WeiboConnectDataService implements IDataService {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${user.weibo.expire.time}")
	private int userWeiboExpireTime;
	@Value("${user.weibo.size}")
	private int userWeiboSize;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private ITpUserService tpUserService;

	@Override
	public List<UserWeibo> listWeibo(long uid, long fuid, long tpId) {

		List<UserWeibo> userWeibos = null;
		try {
			userWeibos = memcachedClient.get(MemcachedKeyGenerator
					.genUserWeiboKey(fuid));

			if (CollectionUtils.isEmpty(userWeibos)) {
				// TODO (none) 应该先判断缓存
				AuthInfo authInfo = getAuthInfo(uid, fuid, tpId);
				userWeibos = getWeibos(fuid, authInfo);
			}
		} catch (Exception e) {
			log.error("listWeibo is error error message=" + e.getMessage());
		}
		return userWeibos;

	}

	private List<UserWeibo> getWeibos(long fuid, AuthInfo authInfo)
			throws Exception {
		List<UserWeibo> userWeibos = new ArrayList<UserWeibo>();
		Timeline timeline = new Timeline(authInfo.getToken());
		TpUser fUser = tpUserService.getTpUserByUid(fuid);
		List<Status> status = timeline.getUserTimeline(fUser.getTpIdentity(),
				"", userWeiboSize, null, 0, 1);
		if (CollectionUtils.isNotEmpty(status)) {
			for (Status s : status) {
				UserWeibo userWeibo = new UserWeibo();
				userWeibo.setContent(s.getText());
				userWeibo.setTime(s.getCreatedAt());
				userWeibos.add(userWeibo);
			}
			memcachedClient.set(MemcachedKeyGenerator.genUserWeiboKey(fuid),
					userWeiboExpireTime, userWeibos);
		}
		return userWeibos;
	}

	private AuthInfo getAuthInfo(long uid, long fuid, long tpId) {
		TpUser fUser = tpUserService.getTpUserByUid(fuid);
		// TODO (none)
		// 逻辑错了，判断是否一个平台，是根据tpName。应该根据uid获取tpUser，然后和fUser判断tpName
		TpUser user = tpUserService.getTpUserByUid(uid);
		if (!user.getTpName().equals(fUser.getTpName())) {
			// 来访者和被访者不是同一个tpid
			// 获取小秘书的authinfo
			long tagerUid = NoticeConfig.getValue(
					ThirdpartyNameEnum.getThirdpartyNameEnum(fUser.getTpName()), "uid");
			long tagerTpId = NoticeConfig.getValue(
					ThirdpartyNameEnum.getThirdpartyNameEnum(fUser.getTpName()), "tpId");
			return tpUserAuthService.getAuthInfo(tagerUid, tagerTpId);
		} else {
			return tpUserAuthService.getAuthInfo(uid, tpId);
		}

	}

	@Override
	public List<UserWeibo> refreshListWeibo(long uid, long fuid, long tpId) {
		AuthInfo authInfo = getAuthInfo(uid, fuid, tpId);
		List<UserWeibo> userWeibos = null;
		try {
			userWeibos = getWeibos(fuid, authInfo);
		} catch (Exception e) {
			log.error("refreshListWeibo is error error message="
					+ e.getMessage());
		}
		return userWeibos;
	}

}
