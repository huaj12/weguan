package com.juzhai.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.Question;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.service.IAppService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.jstl.JzCoreFunction;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ThirdpartyNameEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
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
	private ITpUserService tpUserService;
	@Autowired
	private IActService actService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	@Value("${show.feed.count}")
	private int feedCount = 3;
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean sendSysMessage(long sendId,String fuids, long actId, String link,
			MsgType type, AuthInfo authInfo) {
		if (fuids == null)
			return false;
		try {
			// 内容
			String text = "";
			// 附言
			String word = "";
			Act act = actService.getActById(actId);
			if (act == null) {
				log.error("send message act is null actId=" + actId);
				return false;
			}
			if (MsgType.INVITE.equals(type)) {
				text = messageSource.getMessage(TpMessageKey.INVITE_FRIEND,
						new Object[] { act.getName() },
						Locale.SIMPLIFIED_CHINESE);
				word = messageSource.getMessage(
						TpMessageKey.INVITE_FRIEND_WORD, new Object[] { null },
						Locale.SIMPLIFIED_CHINESE);
			} else if (MsgType.RECOMMEND.equals(type)) {
				int count = userActService.countUserActByActId(
						ThirdpartyNameEnum.KAIXIN001.getName(), actId);
				text = messageSource.getMessage(TpMessageKey.RECOMMEND_FRIEND,
						new Object[] { count, act.getName() },
						Locale.SIMPLIFIED_CHINESE);
				word = messageSource.getMessage(
						TpMessageKey.RECOMMEND_FRIEND_WORD, null,
						Locale.SIMPLIFIED_CHINESE);
			}
			List<String> receiverIdentitys = new ArrayList<String>();
			for (String fuid : fuids.split(",")) {
				receiverIdentitys.add(fuid);
			}
			String picurl = JzCoreFunction.actLogo(act.getId(), act.getLogo(),
					120);
			// 发送系统消息
			messageService.sendMatchMessage(sendId,receiverIdentitys, messageSource
					.getMessage(TpMessageKey.FEED_LINKTEXT, null,
							Locale.SIMPLIFIED_CHINESE), link, word, text,
					picurl, authInfo,act.getId());
		} catch (Exception e) {
			log.error("send message is error.fuids=" + fuids, e);
			return false;
		}
		return true;
	}


	@Override
	public boolean sendFeed(long actId, long uid, long tpId) {
		Act act = actService.getActById(actId);
		if (act == null) {
			log.error("send Feed act is null");
			return false;
		}
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo == null) {
			log.error("send Feed authInfo is null");
			return false;
		}
		String picurl = JzCoreFunction.actLogo(act.getId(), act.getLogo(), 120);
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		int count = userActService.countUserActByActId(
				ThirdpartyNameEnum.KAIXIN001.getName(), actId);
		String text = "";
		if (count > feedCount) {
			text = messageSource.getMessage(TpMessageKey.FEED_TEXT,
					new Object[] { act.getName(), count - 1 },
					Locale.SIMPLIFIED_CHINESE);
		} else {
			text = messageSource.getMessage(
					TpMessageKey.FEED_TEXT_COUNT_DEFAULT,
					new Object[] { act.getName() }, Locale.SIMPLIFIED_CHINESE);
		}
		String word = "";
		if(StringUtils.isNotEmpty(act.getIntro())){
			word=TextTruncateUtil.truncate(act.getIntro(), 100, "...") ;
		}else{
			word=messageSource.getMessage(TpMessageKey.FEED_WORD_BACK,
					null, Locale.SIMPLIFIED_CHINESE);
		}
		String name=act.getName();
		String linktext = messageSource.getMessage(TpMessageKey.FEED_LINKTEXT,
				null, Locale.SIMPLIFIED_CHINESE);
		String link = tp.getAppUrl() + "?goUri=/app/showAct/" + actId;
		return messageService.sendFeed(linktext, link, word, text, picurl,
				authInfo,name);
	}

	@Override
	public boolean aboutFriends(List<String> fuids, String content,
			long sendId, long tpId, long actId) {
		try {
			if (fuids == null || fuids.size() == 0) {
				log.error("about friends  fuids is null");
				return false;
			}
			if (fuids.size() > 30) {
				fuids=fuids.subList(0, 30);
			}
			if (StringUtils.isEmpty(content)) {
				content = "";
			}
			if (content.trim().length() > 30) {
				log.error("about friends  content is too long ");
				return false;
			}
			AuthInfo authInfo = tpUserAuthService.getAuthInfo(sendId, tpId);
			if (authInfo == null) {
				log.error("about friends  authInfo is null");
				return false;
			}
			Thirdparty tp = InitData.TP_MAP.get(tpId);
			for (String fuid : fuids) {
				String link = tp.getAppUrl();
				messageService.sendMessage(sendId,fuid,
						content, authInfo,actId,link);
			}
		} catch (Exception e) {
			log.error("aboutFriends is error.");
			return false;
		}
		return true;
	}

	private String getContent(String code, Object[] args) {
		return messageSource.getMessage(code, args, StringUtils.EMPTY,
				Locale.SIMPLIFIED_CHINESE);
	}


	@Override
	public boolean sendQuestionMessage(long uid, long tpId, List<String> fuids,
			String linktext, String link, String word, String text) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if(null!=authInfo){
			return messageService.sendQuestionMessage(authInfo, fuids,uid, linktext, link, word, text);
		}else{
			return false;	
		}
	}


	
}
