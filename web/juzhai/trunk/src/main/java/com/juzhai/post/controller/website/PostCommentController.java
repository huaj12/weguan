package com.juzhai.post.controller.website;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.form.PostCommentForm;
import com.juzhai.post.controller.view.PostCommentView;
import com.juzhai.post.exception.InputPostCommentException;
import com.juzhai.post.model.PostComment;
import com.juzhai.post.service.IPostCommentService;

@Controller
@RequestMapping(value = "post")
public class PostCommentController extends BaseController {

	@Autowired
	private IPostCommentService postCommentService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Value("${newest.comment.count}")
	private int newestCommentCount;

	@RequestMapping(value = "/quickcomment", method = RequestMethod.POST)
	public String saveComment(HttpServletRequest request, PostCommentForm form,
			Model model, @RequestParam(defaultValue = "false") boolean isReply)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			PostComment postComment = postCommentService.comment(context, form);
			if (isReply) {
				model.addAttribute("result", result);
			} else {
				model.addAttribute("success", true);
				model.addAttribute("postCommentView",
						assemblePostCommentView(postComment));
			}
		} catch (InputPostCommentException e) {
			result.setError(e.getErrorCode(), messageSource);
			model.addAttribute("result", result);
		}
		return "web/post/comment_fragment";
	}

	private PostCommentView assemblePostCommentView(PostComment postComment) {
		PostCommentView view = new PostCommentView();
		view.setPostComment(postComment);
		view.setCreateUser(profileService.getProfileCacheByUid(postComment
				.getCreateUid()));
		if (postComment.getParentCreateUid() > 0) {
			view.setParentUser(profileService.getProfileCacheByUid(postComment
					.getParentCreateUid()));
		}
		return view;
	}

	@RequestMapping(value = "/delcomment", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteComment(HttpServletRequest request, Model model,
			long postCommentId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			postCommentService.deleteComment(context.getUid(), postCommentId);
		} catch (InputPostCommentException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/newcomment", method = RequestMethod.GET)
	public String listNewComment(HttpServletRequest request, Model model,
			long postId) {
		List<PostComment> list = postCommentService.listPostComment(postId, 0,
				newestCommentCount);
		List<PostCommentView> postCommentViewList = new ArrayList<PostCommentView>(
				list.size());
		for (PostComment postComment : list) {
			postCommentViewList.add(assemblePostCommentView(postComment));
		}
		model.addAttribute("postCommentViewList", postCommentViewList);
		model.addAttribute("postId", postId);
		return "web/post/comment_list";
	}
}
