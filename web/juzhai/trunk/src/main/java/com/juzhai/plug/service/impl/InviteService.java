package com.juzhai.plug.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.common.bean.TpMessageKey;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.ISynchronizeService;
import com.juzhai.platform.service.IUserService;
import com.juzhai.plug.bean.SynchronizeInviteTemplate;
import com.juzhai.plug.service.IInviteService;

@Service
public class InviteService implements IInviteService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private ISynchronizeService synchronizeService;
	@Autowired
	private IUserService userService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean sendIvite(String content, long tpId, long uid,
			List<String> fuids) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo == null) {
			return false;
		}
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (tp == null) {
			return false;
		}
		String link = SystemConfig.getDomain();
		synchronizeService.inviteMessage(authInfo, content + link, null, fuids);
		return true;
	}

	@Override
	public String showInvite(String fuids, String names, long tpId, long uid) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (null == authInfo) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotEmpty(fuids)) {
			list = userService.getUserNames(authInfo,
					Arrays.asList(fuids.split(",")));
		}
		if (StringUtils.isNotEmpty(names)) {
			list.addAll(Arrays.asList(names.split(",")));
		}
		List<String> atNames = new ArrayList<String>(list.size());
		for (String str : list) {
			atNames.add("@" + str);
		}
		return messageSource.getMessage(TpMessageKey.CONNECT_INVITE_TEXT,
				new Object[] { StringUtils.join(atNames, " ") },
				Locale.SIMPLIFIED_CHINESE);
	}

	@Override
	public void inviteSynchronize(long uid, long tpId, String content) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo == null) {
			return;
		}
		if (StringUtils.isEmpty(content)) {
			content = messageSource.getMessage(
					SynchronizeInviteTemplate.SYNCHRONIZE_TEXT.getName(), null,
					Locale.SIMPLIFIED_CHINESE);
		}
		String link = messageSource.getMessage(
				SynchronizeInviteTemplate.SYNCHRONIZE_LINK.getName(), null,
				Locale.SIMPLIFIED_CHINESE);
		String title = messageSource.getMessage(
				SynchronizeInviteTemplate.SYNCHRONIZE_TITLE.getName(), null,
				Locale.SIMPLIFIED_CHINESE);
		String imagePath = messageSource.getMessage(
				SynchronizeInviteTemplate.SYNCHRONIZE_IMAGE.getName(), null,
				Locale.SIMPLIFIED_CHINESE);
		try {
			File file = new File(StaticUtil.IMAGE_FILE_ROOT_PATH + imagePath);
			byte[] image = FileUtils.readFileToByteArray(file);
			synchronizeService.sendMessage(authInfo, title, content, link,
					image, JzResourceFunction.u("/images" + imagePath));
		} catch (Exception e) {
			log.error("invite synchronize is error " + e.getMessage());
		}
	}
}
