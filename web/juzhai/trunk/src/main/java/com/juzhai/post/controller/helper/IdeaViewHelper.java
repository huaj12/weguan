package com.juzhai.post.controller.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Component
public class IdeaViewHelper {
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IProfileService profileService;
	@Value("${web.show.ideas.user.count}")
	private int webShowIdeasUserCount;

	public List<IdeaView> assembleIdeaView(UserContext context,
			List<Idea> ideaList) {
		List<IdeaView> ideaViewList = new ArrayList<IdeaView>(ideaList.size());
		for (Idea idea : ideaList) {
			IdeaView ideaView = new IdeaView();
			ideaView.setIdea(idea);
			if (context.hasLogin()) {
				ideaView.setHasUsed(ideaService.isUseIdea(context.getUid(),
						idea.getId()));
				ideaView.setHasInterest(ideaService.isInterestIdea(
						context.getUid(), idea.getId()));
			}
			if (idea.getCreateUid() > 0) {
				ideaView.setProfileCache(profileService
						.getProfileCacheByUid(idea.getCreateUid()));
			}
			ideaView.setIdeaUserViews(ideaService.listIdeaAllUsers(
					idea.getId(), 0, webShowIdeasUserCount));

			ideaViewList.add(ideaView);
		}
		return ideaViewList;
	}
}
