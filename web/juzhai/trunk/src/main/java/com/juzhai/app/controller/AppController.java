package com.juzhai.app.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.InitData;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.app.util.AppPlatformUtils;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.msg.AppConfig;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;

@Controller
public class AppController  {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	ITpUserService tpUserService;
	@Autowired
	IActService actService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@RequestMapping(value = "inviteCallBack/{tpId}", method = RequestMethod.GET)
	public String callback(HttpServletRequest request, @PathVariable long tpId,
			Model model,String uid,String fuids,String callbackkey) {
		// TODO: 接入人人这里需要修改
		try{
			Act act=actService.getActByName(callbackkey);
			TpUser sendUser=tpUserService.getTpUserByTpIdAndIdentity(tpId, uid);
			if(sendUser==null){
				log.error("inviteCallBack uid is not bind");
			}
			//验证签名
			 String queryString =request.getQueryString();
			 AuthInfo authInfo =tpUserAuthService.getAuthInfo(sendUser.getUid(), tpId);
			 if(AppPlatformUtils.checkSignFromQuery(queryString, authInfo.getAppSecret())){
				ActMsg actMsg=new ActMsg(act.getId(), MsgType.INVITE);
				String[] fids=fuids.split(",");
				for(int i=0;i<fids.length;i++){
					String fid=fids[i];
					TpUser receiverUser=tpUserService.getTpUserByTpIdAndIdentity(tpId, fid);
					if(receiverUser!=null&&receiverUser.getUid()>0){
						msgMessageService.sendActMsg(sendUser.getUid(), receiverUser.getUid(), actMsg);
					}else{
						msgMessageService.sendActMsg(sendUser.getUid(), tpId, fid, actMsg);
					}
				}
			 }else{
				 log.error("inviteCallBack sig is error");
			 }
		}catch (Exception e) {
			log.error("inviteCallBack is error",e);
		}
		return null;
	}
}
