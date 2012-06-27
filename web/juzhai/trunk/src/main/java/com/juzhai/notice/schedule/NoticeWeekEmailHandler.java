package com.juzhai.notice.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.factory.MailFactory;
import com.juzhai.core.mail.manager.MailManager;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.core.web.jstl.JzDataFunction;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.jstl.JzUtilFunction;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;
import com.juzhai.post.service.IRecommendIdeaService;
import com.juzhai.post.service.IRecommendPostService;

@Component
public class NoticeWeekEmailHandler extends AbstractScheduleHandler {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private IPostService postService;
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IRecommendIdeaService recommendIdeaService;
	@Autowired
	private IRecommendPostService recommendPostService;
	@Value("${mail.idea.max.rows}")
	private int mailIdeaMaxRows;
	@Value("${mail.post.max.rows}")
	private int mailPostMaxRows;
	private static JzResourceFunction jzr = new JzResourceFunction();
	private static JzDataFunction jzd = new JzDataFunction();
	private static JzUtilFunction jzu = new JzUtilFunction();

	@Override
	protected void doHandle() {
		startMailDaemon();
		int firstResult = 0;
		int maxResults = 200;
		Map<String, Object> params = initData();
		while (true) {
			List<Profile> profileList = profileService.getEmailProfiles(
					firstResult, maxResults);
			if (CollectionUtils.isEmpty(profileList)) {
				break;
			}
			for (Profile profile : profileList) {
				if (StringUtils.isEmpty(profile.getEmail())) {
					continue;
				}
				Mail mail = MailFactory.create(profile.getEmail(),
						profile.getNickname(), true);
				mail.buildSubject("/mail/week/subject.vm",
						buildSubjectProp(profile));
				mail.buildText("/mail/week/content.vm", params);
				mailManager.sendMail(mail, false);
			}
			firstResult += maxResults;
		}
		stopMailDaemon();
	}

	private void startMailDaemon() {
		mailManager.startDaemon();
	}

	private void stopMailDaemon() {
		mailManager.startDaemon();
	}

	private Map<String, Object> buildSubjectProp(Profile profile) {
		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("nickname", profile.getNickname());
		return prop;
	}

	private Map<String, Object> initData() {
		Map<String, Object> params = new HashMap<String, Object>();

		int totalPostCount = postService.totalCount();
		int totalIdeaCount = ideaService.totalCount();
		List<Idea> ideaList = new ArrayList<Idea>();
		ideaList.addAll(recommendIdeaService.listRecommendIdea(mailIdeaMaxRows));
		List<PostView> postList = new ArrayList<PostView>();
		List<Post> list = recommendPostService
				.listRecommendPost(mailPostMaxRows);
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			PostView view = new PostView();
			view.setPost(post);
			view.setProfileCache(cache);
			postList.add(view);
		}
		params.put("totalIdeaCount", totalIdeaCount);
		params.put("totalPostCount", totalPostCount);
		params.put("ideaList", ideaList);
		params.put("postList", postList);
		params.put("jzr", jzr);
		params.put("jzd", jzd);
		params.put("jzu", jzu);
		return params;
	}

}
