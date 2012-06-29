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
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.service.IPassportService;
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
	private IPassportService passportService;
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
	@Value("${week.mail.idea.max.rows}")
	private int weekMailIdeaMaxRows;
	@Value("${week.mail.post.max.rows}")
	private int weekMailPostMaxRows;

	@Override
	protected void doHandle() {
		startMailDaemon();
		int firstResult = 0;
		int maxResults = 200;
		Map<String, Object> params = initData();
		while (true) {
			List<Passport> passPortList = passportService.getEmailPassports(
					firstResult, maxResults);
			if (CollectionUtils.isEmpty(passPortList)) {
				break;
			}
			for (Passport passport : passPortList) {
				if (StringUtils.isEmpty(passport.getEmail())) {
					continue;
				}
				ProfileCache profile = profileService
						.getProfileCacheByUid(passport.getId());
				Mail mail = MailFactory.create(passport.getEmail(),
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
		mailManager.stopDaemon();
	}

	private Map<String, Object> buildSubjectProp(ProfileCache profile) {
		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("nickname", profile.getNickname());
		return prop;
	}

	private Map<String, Object> initData() {
		Map<String, Object> params = new HashMap<String, Object>();

		int totalPostCount = postService.totalCount();
		int totalIdeaCount = ideaService.totalCount();
		List<Idea> ideaList = new ArrayList<Idea>();
		for (Idea idae : recommendIdeaService
				.listRecommendIdea(weekMailIdeaMaxRows)) {
			idae.setPic(JzResourceFunction.ideaPic(idae.getId(), idae.getPic(),
					200));
			idae.setContent(JzUtilFunction.truncate(idae.getContent(), 54,
					"..."));
			ideaList.add(idae);
		}
		List<PostView> postList = new ArrayList<PostView>();
		List<Post> list = recommendPostService
				.listRecommendPost(weekMailPostMaxRows);
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			post.setContent(JzUtilFunction.truncate(post.getContent(), 50,
					"..."));
			cache.setLogoPic(JzResourceFunction.userLogo(cache.getUid(),
					cache.getLogoPic(), 80));
			PostView view = new PostView();
			view.setPost(post);
			view.setProfileCache(cache);
			view.setConstellationName(JzDataFunction.constellationName(cache
					.getConstellationId()));
			view.setAge(JzUtilFunction.age(cache.getBirthYear(),
					cache.getBirthSecret()));
			postList.add(view);
		}
		params.put("totalIdeaCount", totalIdeaCount);
		params.put("totalPostCount", totalPostCount);
		params.put("ideaList", ideaList);
		params.put("postList", postList);
		return params;
	}

}
