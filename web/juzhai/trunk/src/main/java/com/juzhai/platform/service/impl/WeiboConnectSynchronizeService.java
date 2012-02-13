package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;

public class WeiboConnectSynchronizeService implements ISynchronizeService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ITpUserService tpUserService;

	// @Override
	// public List<UserWeibo> listWeibo(long uid, long fuid, long tpId) {
	// List<UserWeibo> userWeibos = null;
	// try {
	// userWeibos = memcachedClient.get(MemcachedKeyGenerator
	// .genUserWeiboKey(fuid));
	//
	// if (CollectionUtils.isEmpty(userWeibos)) {
	// AuthInfo authInfo = getAuthInfo(uid, fuid, tpId);
	// if (null == authInfo) {
	// return null;
	// }
	// userWeibos = getWeibos(fuid, authInfo);
	// }
	// } catch (Exception e) {
	// log.error("listWeibo is error error message=" + e.getMessage());
	// }
	// return userWeibos;
	//
	// }
	//
	// private List<UserWeibo> getWeibos(long fuid, AuthInfo authInfo)
	// throws Exception {
	// List<UserWeibo> userWeibos = new ArrayList<UserWeibo>();
	// Timeline timeline = new Timeline(authInfo.getToken());
	// TpUser fUser = tpUserService.getTpUserByUid(fuid);
	// List<Status> status = timeline.getUserTimeline(fUser.getTpIdentity(),
	// "", userWeiboSize, null, 0, 1);
	// if (CollectionUtils.isNotEmpty(status)) {
	// for (Status s : status) {
	// UserWeibo userWeibo = new UserWeibo();
	// userWeibo.setContent(s.getText());
	// userWeibo.setTime(s.getCreatedAt());
	// userWeibos.add(userWeibo);
	// }
	// memcachedClient.set(MemcachedKeyGenerator.genUserWeiboKey(fuid),
	// userWeiboExpireTime, userWeibos);
	// }
	// return userWeibos;
	// }
	//
	// private AuthInfo getAuthInfo(long uid, long fuid, long tpId) {
	// TpUser fUser = tpUserService.getTpUserByUid(fuid);
	// if (fUser == null) {
	// return null;
	// }
	// TpUser user = tpUserService.getTpUserByUid(uid);
	// if (user == null || !user.getTpName().equals(fUser.getTpName())) {
	// // 来访者和被访者不是同一个tpid
	// // 获取小秘书的authinfo
	// long tagerUid = NoticeConfig
	// .getValue(ThirdpartyNameEnum.getThirdpartyNameEnum(fUser
	// .getTpName()), "uid");
	// long tagerTpId = NoticeConfig
	// .getValue(ThirdpartyNameEnum.getThirdpartyNameEnum(fUser
	// .getTpName()), "tpId");
	// return tpUserAuthService.getAuthInfo(tagerUid, tagerTpId);
	// } else {
	// return tpUserAuthService.getAuthInfo(uid, tpId);
	// }
	//
	// }
	//
	// @Override
	// public List<UserWeibo> refreshListWeibo(long uid, long fuid, long tpId) {
	// AuthInfo authInfo = getAuthInfo(uid, fuid, tpId);
	// List<UserWeibo> userWeibos = null;
	// try {
	// userWeibos = getWeibos(fuid, authInfo);
	// } catch (Exception e) {
	// log.error("refreshListWeibo is error error message="
	// + e.getMessage());
	// }
	// return userWeibos;
	// }

	@Override
	public void sendMessage(AuthInfo authInfo, String text, byte[] image) {
		send(authInfo, text, image);
	}

	private void send(AuthInfo authInfo, String text, byte[] image) {
		try {
			Timeline timeline = new Timeline(authInfo.getToken());
			if (image != null) {
				ImageItem item = new ImageItem(image);
				timeline.UploadStatus(text, item);
			} else {
				timeline.UpdateStatus(text);
			}
		} catch (Exception e) {
			log.error("weibo connect sendMessage is error. " + e.getMessage(),
					e);
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image) {
		send(authInfo, text, image);
	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {
		if (ArrayUtils.isEmpty(fuids)) {
			return;
		}

		for (String fuid : fuids) {
			try {
				Comments comment = new Comments(authInfo.getToken());
				Timeline timeline = new Timeline(authInfo.getToken());
				List<Status> status = timeline.getUserTimeline(fuid, "", 1,
						null, 0, 1);
				if (CollectionUtils.isNotEmpty(status)) {
					String id = status.get(0).getId();
					comment.createComment(text, id);
				}
			} catch (Exception e) {
				log.error("connect weibo sendSysMessage is error."
						+ e.getMessage() + "fuid:" + fuid);
			}
		}

	}

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		try {
			Timeline timeline = new Timeline(authInfo.getToken());
			TpUser fUser = tpUserService.getTpUserByUid(fuid);
			List<Status> status = timeline.getUserTimeline(
					fUser.getTpIdentity(), "", size, null, 0, 1);
			if (CollectionUtils.isNotEmpty(status)) {
				for (Status s : status) {
					UserStatus userStatus = new UserStatus();
					userStatus.setContent(s.getText());
					userStatus.setTime(s.getCreatedAt());
					userStatusList.add(userStatus);
				}
			}
		} catch (WeiboException e) {
			log.error("weibo connect listStatus is error. " + e.getMessage(), e);
		}
		return userStatusList;
	}

}
