package com.juzhai.plug.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.juzhai.core.SystemConfig;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.IAuthorizeURLService;
import com.juzhai.platform.service.IMessageService;
import com.juzhai.plug.service.IWeiboIviteService;

@Service
public class WeiboIviteService implements IWeiboIviteService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IMessageService messageService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IAuthorizeURLService authorizeURLService;

	@Override
	public boolean sendWeiboIvite(String content, long actId, long tpId,
			long uid) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo == null) {
			return false;
		}
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (tp == null) {
			return false;
		}
		String link = SystemConfig.getDomain();
		return messageService.sendMessage(uid, null, null, content, authInfo,
				actId, link, "1", null);
	}

	@Override
	public List<String> getInviteReceiverName(String fuids, long tpId, long uid) {
		List<String> list = new ArrayList<String>();
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo != null) {
			Users users = new Users(authInfo.getToken());
			if (StringUtils.isNotEmpty(fuids)) {
				for (String fuid : fuids.split(",")) {
					User user = null;
					try {
						user = users.showUserById(fuid);
					} catch (WeiboException e) {
						log.error("getInviteReceiverName is error"
								+ e.getMessage());
					}
					if (user != null) {
						list.add("@" + user.getName());
					}
				}
			}
		}

		return list;
	}
}
