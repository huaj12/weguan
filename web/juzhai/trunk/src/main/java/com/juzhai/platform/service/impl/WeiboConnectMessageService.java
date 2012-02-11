package com.juzhai.platform.service.impl;

import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
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
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IMessageService;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;

@Service
public class WeiboConnectMessageService implements IMessageService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IActImageService actImageService;
	@Autowired
	private IPostService postService;
	@Value("${show.feed.count}")
	private int feedCount = 3;
	private int weiboMaxLength = 280;

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext,
			String link, String word, String text, String picurl,
			AuthInfo authInfo) {
		if (CollectionUtils.isEmpty(fuids)) {
			return false;
		}
		String fuid = fuids.get(0);
		try {
			text = subContent(text, null);
			Comments comment = new Comments(authInfo.getToken());
			Timeline timeline = new Timeline(authInfo.getToken());
			List<Status> status = timeline.getUserTimeline(fuid, "", 1, null,
					0, 1);
			if (status != null && status.size() > 0) {
				String id = status.get(0).getId();
				comment.createComment(text, id);
				return true;
			}
		} catch (Exception e) {
			log.error("connect weibo sendSysMessage is error." + e.getMessage()
					+ "fuid:" + fuid);
		}
		return false;
	}

	@Override
	public boolean sendMessage(long sendId, String fuids, String fname,
			String content, AuthInfo authInfo, long postId, String link,
			String typeWeibo, String typeComment) {
		if (StringUtils.isEmpty(typeComment) && StringUtils.isEmpty(typeWeibo)) {
			return false;
		}
		try {
			String text = content;
			text = subContent(text, "");
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
				sendWeibo(postId, timeline, text);
				return true;
			}
		} catch (Exception e) {
			log.error("connect weibo sendMessage is error." + e.getMessage()
					+ "uid:" + authInfo.getTpIdentity());
			return false;
		}
	}

	private String subContent(String text, String appLink) {
		int count = weiboMaxLength - StringUtil.chineseLength(appLink);
		text = TextTruncateUtil.truncate(text, count - 5, "...") + appLink;
		return text;
	}

	private void sendWeibo(long postId, Timeline timeline, String content)
			throws WeiboException {
		Post post =postService.getPostById(postId);
		if (post != null) {
			byte[] imgContent = actImageService.getActFile(post.getId(),
					post.getPic(), LogoSizeType.BIG);
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

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo, String name,
			long actId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendQuestionMessage(AuthInfo authInfo, List<String> fuids,
			long sendId, String linktext, String link, String word, String text) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMatchMessage(long sendId, List<String> fuids,
			String linktext, String link, String word, String text,
			String picurl, AuthInfo authInfo, long actId) {
		// TODO Auto-generated method stub
		return false;
	}

}
