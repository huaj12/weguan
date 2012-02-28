package com.juzhai.platform.service.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.douban.DoumailEntry;
import com.google.gdata.data.douban.MiniblogEntry;
import com.google.gdata.data.douban.MiniblogFeed;
import com.google.gdata.data.douban.Namespaces;
import com.google.gdata.data.douban.UserEntry;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;
import com.qq.connect.ShareToken;
import com.qq.oauth.Config;

@Service
public class QqConnectSynchronizeService implements ISynchronizeService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {
		ShareToken share = new ShareToken(authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", title);
			map.put("link", link);
			map.put("summary", text);
			map.put("images", imageUrl);
			map.put("format", "json");
			map.put("source", "1");
			map.put("type", "4");
			share.addShare(authInfo.getToken(), authInfo.getTokenSecret(),
					authInfo.getTpIdentity(), map);
		} catch (Exception e) {
			log.error("QQ content sendMessage is error." + e.getMessage());
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {
	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {

	}

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {

		return Collections.emptyList();
	}

}
