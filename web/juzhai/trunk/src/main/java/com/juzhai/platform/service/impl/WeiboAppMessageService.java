package com.juzhai.platform.service.impl;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.cms.bean.SizeType;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IMessageService;

@Service
public class WeiboAppMessageService implements IMessageService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IActImageService actImageService;
	@Autowired
	private IActService actService;
	@Value("${show.feed.count}")
	private int feedCount = 3;
	private int weiboMaxLength = 280;

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext,
			String link, String word, String text, String picurl,
			AuthInfo authInfo) {

		return false;
	}

	@Override
	public boolean sendMessage(long sendId, String fuids, String fname,
			String content, AuthInfo authInfo, long actId, String link,
			String typeWeibo, String typeComment) {
		if (StringUtils.isEmpty(typeComment) && StringUtils.isEmpty(typeWeibo)) {
			return false;
		}
		try {
			String text = messageSource.getMessage(
					TpMessageKey.WEIBO_SEND_MESSAGE, new Object[] { fname,
							content }, Locale.SIMPLIFIED_CHINESE);
			String appLink = "";
			if (actId > 0) {
				appLink = link + "?goUri=/app/showAct/" + actId;
			} else {
				appLink = link + "?goUri=/app/" + sendId;
			}
			text = subContent(text, appLink);
			Comments comment = new Comments(authInfo.getToken());
			Timeline timeline = new Timeline(authInfo.getToken());
			if (StringUtils.isNotEmpty(typeComment)) {
				List<Status> status = timeline.getUserTimeline(fuids, "", 1,
						null, 0, 1);
				if (status != null && status.size() > 0) {
					String id = status.get(0).getId();
					int comment_ori = 0;
					if (StringUtils.isNotEmpty(typeWeibo)) {
						comment_ori = 1;
					}
					comment.createComment(text, id, comment_ori);
					return true;
				} else {
					return false;
				}
			} else {
				sendWeibo(actId, timeline, content);
				return true;
			}
		} catch (Exception e) {
			log.error("weibo sendMessage is error." + e.getMessage() + "uid:"
					+ authInfo.getTpIdentity());
			return false;
		}
	}

	private void sendWeibo(long actId, Timeline timeline, String content)
			throws WeiboException {
		Act act = actService.getActById(actId);
		if (act != null) {
			byte[] imgContent = actImageService.getActFile(act.getId(),
					act.getLogo(), SizeType.BIG);
			if (imgContent == null) {
				timeline.UpdateStatus(content);
			} else {
				ImageItem item = new ImageItem(imgContent);
				timeline.UploadStatus(content, item);
			}
		} else {
			timeline.UpdateStatus(content);
		}
	}

	private String subContent(String text, String appLink) {
		int count = weiboMaxLength - StringUtil.chineseLength(appLink);
		text = TextTruncateUtil.truncate(text, count - 5, "...") + appLink;
		return text;
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo, String name,
			long actId) {
		return false;
	}

	@Override
	public boolean sendQuestionMessage(AuthInfo authInfo, List<String> fuids,
			long sendId, String linktext, String link, String word, String text) {
		try {
			String content = getContent(TpMessageKey.WEIBO_QUERTION_MESSAGE,
					new Object[] { word, link + "?goUri=/app/judge" });
			for (String fuid : fuids) {
				Comments comment = new Comments(authInfo.getToken());
				Timeline timeline = new Timeline(authInfo.getToken());
				List<Status> status = timeline.getUserTimeline(fuid, "", 1,
						null, 0, 1);
				if (status != null && status.size() > 0) {
					String id = status.get(0).getId();
					comment.createComment(content, id);
				}
			}
			return true;
		} catch (Exception e) {
			log.error("weibo sendQuestionMessage is error." + e.getMessage()
					+ "uid:" + authInfo.getTpIdentity());
			return false;
		}

	}

	@Override
	public boolean sendMatchMessage(long sendId, List<String> fuids,
			String linktext, String link, String word, String text,
			String picurl, AuthInfo authInfo, long actId) {
		// TODO Auto-generated method stub
		return false;
	}

	private String getContent(String code, Object[] args) {
		return messageSource.getMessage(code, args, StringUtils.EMPTY,
				Locale.SIMPLIFIED_CHINESE);
	}

}
