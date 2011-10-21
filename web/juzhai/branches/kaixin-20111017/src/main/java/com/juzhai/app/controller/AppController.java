package com.juzhai.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.app.util.AppPlatformUtils;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;

@Controller
public class AppController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IAccountService accountService;

	@RequestMapping(value = "dialogSysnewsCallBack", method = RequestMethod.GET)
	public String dialogSysnewsCallBack(HttpServletRequest request,
			Model model, String uid, String fuids, String name,Integer num) {
		// TODO: 接入人人这里需要修改
		String queryString = request.getQueryString();
		try {
			long tpId = 0;
			long actId = 0;
			if (!name.isEmpty()) {
				try{
				String[] str = name.split("-");
				tpId = Long.valueOf(str[1]);
				actId = Long.valueOf(str[0]);
				}catch (Exception e) {
				}
			}
		
			Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
			if(tp==null||actId==0){
				log.error("dialogSysnewsCallBack prams is null tpId="+tpId+"  actId="+actId);
				return null;
			}
			// 验证签名
			if (AppPlatformUtils.checkSignFromQuery(queryString,
					tp.getAppSecret())) {
				TpUser sendUser = tpUserService.getTpUserByTpIdAndIdentity(tpId,
						uid);
				if (sendUser == null) {
					log.error("dialogSysnewsCallBack uid is not bind");
					return null;
				}
				ActMsg actMsg = new ActMsg(actId, sendUser.getUid(),
						MsgType.INVITE);
				String[] fids = fuids.split(",");
				for (String fid:fids) {
					TpUser receiverUser = tpUserService
							.getTpUserByTpIdAndIdentity(tpId, fid);
					if (receiverUser != null && receiverUser.getUid() > 0) {
						msgMessageService.sendActMsg(sendUser.getUid(),
								receiverUser.getUid(), actMsg);
					} else {
						msgMessageService.sendActMsg(sendUser.getUid(), tpId,
								fid, actMsg);
					}
				}
				//有内容加积分
				if(num==null){
					num=1;
				}
				accountService.profitPoint(sendUser.getUid(), ProfitAction.INVITE_FRIEND, num);
			} else {
				log.error("inviteCallBack sig is error");
			}
		} catch (Exception e) {
			log.error("inviteCallBack is error queryString=" + queryString, e);
		}
		return null;
	}
	// 拒宅无内容邀请回调
	@RequestMapping(value = "requestCallBack", method = RequestMethod.GET)
	public String requestCallBack(HttpServletRequest request, Long tpId, String uid,Integer num){
		String queryString = request.getQueryString();
		try {
			Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
			if(tp==null){
				log.error("requestBack prams is null tpId="+tpId);
				return null;
			}
			if (AppPlatformUtils.checkSignFromQuery(queryString,
					tp.getAppSecret())) {
				TpUser sendUser = tpUserService.getTpUserByTpIdAndIdentity(tpId,
						uid);
				if (sendUser == null) {
					log.error("requestBack uid is not bind");
					return null;
				}
				// 加积分
				if(num==null){
					num=1;
				}
				accountService.profitPoint(sendUser.getUid(), ProfitAction.INVITE_FRIEND, num);
			} else {
				log.error("requestBack sig is error ");
			}
		}catch (Exception e) {
			log.error("requestBack  is error queryString=" + queryString, e);
		}
		return null;
	}
	
	// 拒宅召集令回调
	@RequestMapping(value = "feedCallBack", method = RequestMethod.GET)
	public String feedCallBack(HttpServletRequest request, Long tpId, String uid) {
		// 验证签名
		String queryString = request.getQueryString();
		try {
			Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
			if(tp==null){
				log.error("feedCallBack prams is null tpId="+tpId);
				return null;
			}
			if (AppPlatformUtils.checkSignFromQuery(queryString,
					tp.getAppSecret())) {
				TpUser sendUser = tpUserService.getTpUserByTpIdAndIdentity(tpId,
						uid);
				if (sendUser == null) {
					log.error("feedCallBack uid is not bind");
					return null;
				}
				// 加积分
				accountService.profitPoint(sendUser.getUid(),
						ProfitAction.TP_FEED);
			} else {
				log.error("feedCallBack sig is error ");
			}
		} catch (Exception e) {
			log.error("feedCallBack is error queryString=" + queryString, e);
		}
		return null;
	}

}
