package com.juzhai.platform.service.impl;

import java.util.List;
import java.util.Locale;

import kx4j.http.ImageItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.model.Status;

import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.service.IMessageService;

@Service
public class WeiboAppMessageService implements IMessageService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext,
			String link, String word, String text, String picurl,
			AuthInfo authInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMessage(long sendId, String fuids, String content,
			AuthInfo authInfo, long actId, String link) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo, String name) {
//		String uid = newWeibo(authInfo);
//		Timeline timeline = new Timeline();
//		//
//		 ImageItem item=new ImageItem(content);
//		 timeline.UploadStatus(status, item);
		return false;
	}

	@Override
	public boolean sendQuestionMessage(AuthInfo authInfo, List<String> fuids,
			long sendId, String linktext, String link, String word, String text) {
		try {
			String content = getContent(TpMessageKey.WEIBO_QUERTION_MESSAGE,
					new Object[] { word, link + "?goUri=/app/judge" });
			String uid = newWeibo(authInfo);
			Comments comment = new Comments();
			Timeline timeline = new Timeline();
			List<Status> status = timeline.getUserTimeline(uid, null, 1, null,
					0, 1);
			if (status != null && status.size() > 0) {
				String id = status.get(0).getId();
				comment.createComment(content, id);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error("weibo sendQuestionMessage is error."+e.getMessage());
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

	private String newWeibo(AuthInfo authInfo) {
		Weibo weibo = new Weibo();
		weibo.setToken(authInfo.getToken());
		String uid = authInfo.getTpIdentity();
		return uid;
	}

	private String getContent(String code, Object[] args) {
		return messageSource.getMessage(code, args, StringUtils.EMPTY,
				Locale.SIMPLIFIED_CHINESE);
	}

}
