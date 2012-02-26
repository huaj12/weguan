package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.Link;
import com.google.gdata.data.douban.UserEntry;
import com.google.gdata.data.douban.UserFeed;
import com.google.gdata.data.extensions.ContactEntry;
import com.google.gdata.data.extensions.ContactFeed;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.platform.service.IRelationshipService;

@Service
public class DoubanConnectRelationshipService implements IRelationshipService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		List<TpFriend> friendIdList = new ArrayList<TpFriend>();
		String uid = authInfo.getTpIdentity();
		DoubanService doubanService = DoubanService.getDoubanService(
				authInfo.getToken(), authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			while (true) {
				int i = 1;
				UserFeed userFeed = doubanService.getUserFriends(uid, i, 50);
				List<UserEntry> users = userFeed.getEntries();
				for (UserEntry user : users) {
					TpFriend tpFriend = new TpFriend();
					tpFriend.setUserId(user.getUid());
					List<Link> links = user.getLinks();
					for (Link link : links) {
						if ("icon".equals(link.getRel())) {
							tpFriend.setLogoUrl(link.getHref());
						}
					}
					tpFriend.setCity(user.getLocation());
					friendIdList.add(tpFriend);
				}
				if (users.size() < 50) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("douban  getAllFriends is erorr." + e.getMessage());
			return null;
		}
		return friendIdList;
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		List<String> fuids = new ArrayList<String>();
		String uid = authInfo.getTpIdentity();
		DoubanService doubanService = DoubanService.getDoubanService(
				authInfo.getToken(), authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			while (true) {
				int i = 1;
				UserFeed userFeed = doubanService.getUserFriends(uid, i, 50);
				List<UserEntry> users = userFeed.getEntries();
				for (UserEntry user : users) {
					if (isInstalled(authInfo.getThirdpartyName(), user.getUid())) {
						fuids.add(user.getUid());
					}
				}
				if (users.size() < 50) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("douban  getAppFriends is erorr." + e.getMessage());
			return null;
		}
		return fuids;
	}

	@Override
	public List<String> getInstallFollows(AuthInfo authInfo) {
		List<String> fuids = new ArrayList<String>();
		String uid = authInfo.getTpIdentity();
		DoubanService doubanService = DoubanService.getDoubanService(
				authInfo.getToken(), authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			while (true) {
				int i = 1;
				ContactFeed contactFeed = doubanService.getContacts(uid, i, 50);
				List<ContactEntry> users = contactFeed.getEntries();
				for (ContactEntry user : users) {
					if (isInstalled(authInfo.getThirdpartyName(), user.getId())) {
						fuids.add(user.getId());
					}
				}
				if (users.size() < 50) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("douban  getAppFriends is erorr." + e.getMessage());
			return null;
		}
		return fuids;
	}

	private boolean isInstalled(String tpName, String tpIdentity) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genTpInstallUsersKey(tpName), tpIdentity);
	}

}
