package com.juzhai.home.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.PostCommentView;
import com.juzhai.post.model.PostComment;
import com.juzhai.post.service.IPostCommentService;

@Controller
@RequestMapping(value = "home")
public class CommentController extends HomeBaseController {

	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPostCommentService postCommentService;
	@Value("${comment.inbox.max.rows}")
	private int commentInboxMaxRows;
	@Value("${comment.outbox.max.rows}")
	private int commentOutboxMaxRows;

	@RequestMapping(value = "/comment/inbox/{page}", method = RequestMethod.GET)
	public String commentInbox(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeLogo(context, model);
		PagerManager pager = new PagerManager(page, commentInboxMaxRows,
				postCommentService.countInbox(context.getUid()));
		List<PostComment> list = postCommentService.listInbox(context.getUid(),
				pager.getFirstResult(), pager.getMaxResult());

		model.addAttribute("postCommentViewList",
				assemblePostCommentViewList(list));
		model.addAttribute("pager", pager);
		noticeService.emptyNotice(context.getUid(), NoticeType.COMMENT);
		loadFaces(model);
		return "web/home/comment/inbox";
	}

	@RequestMapping(value = "/comment/outbox/{page}", method = RequestMethod.GET)
	public String commentOutbox(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeLogo(context, model);
		PagerManager pager = new PagerManager(page, commentOutboxMaxRows,
				postCommentService.countOutbox(context.getUid()));
		List<PostComment> list = postCommentService.listOutbox(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());

		model.addAttribute("postCommentViewList",
				assemblePostCommentViewList(list));
		model.addAttribute("pager", pager);
		loadFaces(model);
		return "web/home/comment/outbox";
	}

	private List<PostCommentView> assemblePostCommentViewList(
			List<PostComment> list) {
		List<PostCommentView> postCommentViewList = new ArrayList<PostCommentView>(
				list.size());
		for (PostComment pc : list) {
			PostCommentView view = new PostCommentView();
			view.setPostComment(pc);
			view.setCreateUser(profileService.getProfileCacheByUid(pc
					.getCreateUid()));
			view.setPostCreateUser(profileService.getProfileCacheByUid(pc
					.getPostCreateUid()));
			if (pc.getParentCreateUid() > 0) {
				view.setParentUser(profileService.getProfileCacheByUid(pc
						.getParentCreateUid()));
			}
			postCommentViewList.add(view);
		}
		return postCommentViewList;
	}
}
