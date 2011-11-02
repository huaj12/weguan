package com.juzhai.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.service.IAppService;
import com.juzhai.cms.bean.SizeType;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.IMessageService;

@Service
public class AppService implements IAppService {
	@Autowired
	private IMessageService messageService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IActService actService;
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean sendSysMessage(String fuids, String sendAct, String link,
			MsgType type, AuthInfo authInfo) {
		if (fuids == null)
			return false;
		try {
			// 内容
			String text = "";
			// 附言
			String word = "";
			if (MsgType.INVITE.equals(type)) {
				text = messageSource.getMessage(TpMessageKey.INVITE_FRIEND,
						new Object[] { sendAct }, Locale.SIMPLIFIED_CHINESE);
				word = messageSource.getMessage(
						TpMessageKey.INVITE_FRIEND_WORD, new Object[] { null },
						Locale.SIMPLIFIED_CHINESE);
			} else if (MsgType.RECOMMEND.equals(type)) {
				text = messageSource.getMessage(TpMessageKey.RECOMMEND_FRIEND,
						new Object[] { sendAct }, Locale.SIMPLIFIED_CHINESE);
				word = messageSource.getMessage(
						TpMessageKey.RECOMMEND_FRIEND_WORD,
						new Object[] { null }, Locale.SIMPLIFIED_CHINESE);
			}
			List<String> receiverIdentitys = new ArrayList<String>();
			for (String fuid : fuids.split("")) {
				receiverIdentitys.add(fuid);
			}
			// 发送系统消息
			messageService.sendSysMessage(receiverIdentitys, messageSource
					.getMessage(TpMessageKey.FEED_LINKTEXT, null,
							Locale.SIMPLIFIED_CHINESE), link, word, text, null,
					authInfo);
		} catch (Exception e) {
			log.error("send message is error.fuids=" + fuids, e);
			return false;
		}
		return true;
	}

	@Override
	public boolean sendMessage(String fuids, String word, AuthInfo authInfo) {
		return messageService.sendMessage(fuids, word, authInfo);
	}

	@Override
	public boolean sendFeed(long actId, long uid, long tpId) {
		Act act=actService.getActById(actId);
		if (act==null) {
			log.error("send Feed act is null");
			return false;
		}
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo == null) {
			log.error("send Feed authInfo is null");
			return false;
		}
		String picurl ="";
		if (act.getLogo() != null) {
			picurl = ImageUtil.generateFullImageWebPath(
					StaticUtil.u("/images/"), act.getId(), act.getLogo(),
					SizeType.MIDDLE);
		}
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		String text = messageSource.getMessage(TpMessageKey.FEED_TEXT_BACK,
				null, Locale.SIMPLIFIED_CHINESE);
		String word = word = messageSource.getMessage(TpMessageKey.FEED_WORD_BACK,
				new Object[] { act.getName() }, Locale.SIMPLIFIED_CHINESE);
		String linktext = messageSource.getMessage(TpMessageKey.FEED_LINKTEXT,
				null, Locale.SIMPLIFIED_CHINESE);
		String link = tp.getAppUrl() + "?goUri=/app/showAct/"+actId;
		return messageService.sendFeed(linktext, link, word, text, picurl,
				authInfo);
	}

}
