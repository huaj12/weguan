package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import weibo4j.Friendships;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.platform.service.IRelationshipService;
@Service
public class WeiboConnectRelationshipService implements IRelationshipService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		List<TpFriend> friendIdList = new ArrayList<TpFriend>();
		String uid = authInfo.getTpIdentity();
		Friendships fm = new Friendships(authInfo.getToken());
		try {
			List<User> users = fm.getFriendsBilateral(uid);
			for (User user : users) {
				TpFriend tpFriend = new TpFriend();
				tpFriend.setUserId(user.getId());
				tpFriend.setName(user.getName());
				// 值1表示男性；值0表示女性
				String gender = user.getGender();
				int sex = 0;
				if ("m".equals(gender)) {
					sex = 1;
				}
				tpFriend.setGender(sex);
				tpFriend.setLogoUrl(user.getAvatarLarge());
				// 微博获取不到生日需要申请高级接口
				tpFriend.setCity(user.getLocation());
				friendIdList.add(tpFriend);
			}
		} catch (WeiboException e) {
			log.error("weibo  getAllFriends is erorr." + e.getMessage());
			return null;
		}
		return friendIdList;
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		String uid = authInfo.getTpIdentity();
		Friendships fm = new Friendships(authInfo.getToken());
		List<String> fuids = new ArrayList<String>();
		try {
			String[] ids = fm.getFriendsBilateralIds(uid);
			for (String id : ids) {
				if (isInstalled(authInfo.getThirdpartyName(), id)) {
					fuids.add(id);
				}
			}
		} catch (WeiboException e) {
			log.error("weibo  getAppFriends is erorr." + e.getMessage());
			return null;
		}
		return fuids;
	}

	@Override
	public List<String> getInstallFollows(AuthInfo authInfo) {
		String uid = authInfo.getTpIdentity();
		Friendships fm = new Friendships(authInfo.getToken());
		List<String> fuids = new ArrayList<String>();
		try {
			String[] ids = fm.getFriendsIds(uid);
			for (String id : ids) {
				if (isInstalled(authInfo.getThirdpartyName(), id)) {
					fuids.add(id);
				}
			}
		} catch (WeiboException e) {
			log.error("weibo  getAppFriends is erorr." + e.getMessage());
			return null;
		}
		return fuids;
	}

	private boolean isInstalled(String tpName, String tpIdentity) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genTpInstallUsersKey(tpName), tpIdentity);
	}

}
