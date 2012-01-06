package com.juzhai.plug.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.IAuthorizeURLService;
import com.juzhai.platform.service.IMessageService;
import com.juzhai.plug.service.IWeiboIviteService;

@Service
public class WeiboIviteService implements IWeiboIviteService{
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
		if(authInfo==null){
			return false;
		}
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if(tp==null){
			return false;
		}
		String link=authorizeURLService.getAuthorizeURLforCode(tp);
		return messageService.sendMessage(uid, null, null, content, authInfo, actId, link, "1", null);
	}
}
