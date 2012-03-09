package com.juzhai.post.controller.website;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.controller.view.PostCommentView;
import com.juzhai.post.controller.view.ResponseUserView;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostComment;
import com.juzhai.post.model.PostResponse;
import com.juzhai.post.service.IPostCommentService;
import com.juzhai.post.service.IPostImageService;
import com.juzhai.post.service.IPostService;

@Controller
@RequestMapping(value = "post")
public class PostController extends BaseController {

	@Autowired
	private IPostService postService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IPostImageService postImageService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IPostCommentService postCommentService;
	@Autowired
	private IProfileService profileService;
	@Value("${post.comment.user.max.rows}")
	private int postCommentUserMaxRows;
	@Value("${post.response.user.max.rows}")
	private int postResponseUserMaxRows;
	@Value("${post.detail.right.idea.rows}")
	private int postDetailRightIdeaRows;

	@RequestMapping(value = "/response", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult responsePost(HttpServletRequest request, Model model,
			long postId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			postService.responsePost(context.getUid(), postId);
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/postIdea", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult postIdea(HttpServletRequest request, Model model,
			PostForm postForm, boolean sendWeibo) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			long postId = postService.createPost(context.getUid(), postForm);
			if (sendWeibo && postId > 0) {
				postService.synchronizeWeibo(context.getUid(),
						context.getTpId(), postId);
			}
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/pic/upload", method = RequestMethod.POST)
	public String uploadPostImg(HttpServletRequest request, Model model,
			@RequestParam("postPic") MultipartFile postPic) {
		AjaxResult result = new AjaxResult();
		try {
			checkLoginForWeb(request);
			try {
				String[] urls = postImageService.uploadPic(postPic);
				result.setResult(urls);
			} catch (UploadImageException e) {
				result.setError(e.getErrorCode(), messageSource);
			}
		} catch (NeedLoginException e) {
			result.setError(NeedLoginException.IS_NOT_LOGIN, messageSource);
		}
		model.addAttribute("result", result.toJson());
		return "web/common/ajax/ajax_result";
	}

	@RequestMapping(value = "/createPost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult createPost(HttpServletRequest request, Model model,
			PostForm postForm, boolean sendWeibo) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			long postId = postService.createPost(context.getUid(), postForm);
			if (sendWeibo && postId > 0) {
				postService.synchronizeWeibo(context.getUid(),
						context.getTpId(), postId);
			}
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/prepareModifyPost", method = RequestMethod.GET)
	public String prepareModifyPost(HttpServletRequest request, Model model,
			long postId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		Post post = postService.getPostById(postId);
		if (null == post || post.getCreateUid() != context.getUid()) {
			return error_404;
		}
		initPostForm(model, post);
		return "web/home/index/send_post";
	}

	@RequestMapping(value = "/prepareRepost", method = RequestMethod.GET)
	public String prepareRepost(HttpServletRequest request, Model model,
			long postId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		ProfileCache loginUser = getLoginUserCache(request);
		if (StringUtils.isEmpty(loginUser.getLogoPic())
				&& loginUser.getLogoVerifyState() != LogoVerifyState.VERIFYING
						.getType()) {
			return "/web/profile/face_dialog_" + loginUser.getLogoVerifyState();
		}
		Post post = postService.getPostById(postId);
		if (null == post || post.getCreateUid() == context.getUid()) {
			return error_404;
		}
		initPostForm(model, post);

		model.addAttribute("isRepost", true);
		return "web/home/index/send_post";
	}

	private void initPostForm(Model model, Post post) {
		PostForm postForm = new PostForm();
		postForm.setIdeaId(post.getIdeaId());
		postForm.setPostId(post.getId());
		postForm.setContent(post.getContent());
		postForm.setCategoryId(post.getCategoryId());
		postForm.setDate(post.getDateTime());
		postForm.setLink(post.getLink());
		postForm.setPlace(post.getPlace());
		postForm.setPic(post.getPic());
		postForm.setPurposeType(post.getPurposeType());
		model.addAttribute("postForm", postForm);
		loadCategoryList(model);
	}

	@RequestMapping(value = "/deletePost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deletePost(HttpServletRequest request, Model model,
			long postId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			postService.deletePost(context.getUid(), postId);
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/modifyPost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult modifyPost(HttpServletRequest request, Model model,
			PostForm postForm, boolean sendWeibo) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			long postId = postService.modifyPost(context.getUid(), postForm);

			if (sendWeibo && postId > 0) {
				postService.synchronizeWeibo(context.getUid(),
						context.getTpId(), postId);
			}
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/{postId}", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, Model model,
			@PathVariable long postId) {
		return commentUser(request, model, postId);
	}

	@RequestMapping(value = "/{postId}/comment", method = RequestMethod.GET)
	public String commentUser(HttpServletRequest request, Model model,
			@PathVariable long postId) {
		return pageCommentUser(request, model, postId, 1);
	}

	@RequestMapping(value = "/{postId}/comment/{page}", method = RequestMethod.GET)
	public String pageCommentUser(HttpServletRequest request, Model model,
			@PathVariable long postId, @PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (!postDetail(request, model, postId, context)) {
			return error_404;
		}
		int totalCnt = (Integer) model.asMap().get("commentTotalCnt");
		PagerManager pager = new PagerManager(page, postCommentUserMaxRows,
				totalCnt);
		List<PostComment> list = postCommentService.listPostComment(postId,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostCommentView> postCommentViewList = new ArrayList<PostCommentView>(
				list.size());
		for (PostComment pc : list) {
			PostCommentView view = new PostCommentView();
			view.setPostComment(pc);
			view.setCreateUser(profileService.getProfileCacheByUid(pc
					.getCreateUid()));
			if (pc.getParentCreateUid() > 0) {
				view.setParentUser(profileService.getProfileCacheByUid(pc
						.getParentCreateUid()));
			}
			postCommentViewList.add(view);
		}
		model.addAttribute("pager", pager);
		model.addAttribute("postCommentViewList", postCommentViewList);
		model.addAttribute("pageType", "comment");
		return "web/post/detail";
	}

	@RequestMapping(value = "/{postId}/respuser", method = RequestMethod.GET)
	public String responseUser(HttpServletRequest request, Model model,
			@PathVariable long postId) {
		return pageResponseUser(request, model, postId, 1);
	}

	@RequestMapping(value = "/{postId}/respuser/{page}", method = RequestMethod.GET)
	public String pageResponseUser(HttpServletRequest request, Model model,
			@PathVariable long postId, @PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (!postDetail(request, model, postId, context)) {
			return error_404;
		}
		int totalCnt = (Integer) model.asMap().get("respTotalCnt");
		PagerManager pager = new PagerManager(page, postResponseUserMaxRows,
				totalCnt);
		List<PostResponse> postResponseList = postService.listPostResponse(
				postId, pager.getFirstResult(), pager.getMaxResult());
		List<ResponseUserView> responseUserViewList = new ArrayList<ResponseUserView>(
				postResponseList.size());
		for (PostResponse pr : postResponseList) {
			ResponseUserView view = new ResponseUserView();
			view.setProfileCache(profileService.getProfileCacheByUid(pr
					.getUid()));
			view.setCreateTime(pr.getCreateTime());
			if (context.hasLogin() && context.getUid() != pr.getUid()) {
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), pr.getUid()));
			}
			responseUserViewList.add(view);
		}
		model.addAttribute("pager", pager);
		model.addAttribute("responseUserViewList", responseUserViewList);
		model.addAttribute("pageType", "response");
		return "web/post/detail";
	}

	private boolean postDetail(HttpServletRequest request, Model model,
			long postId, UserContext context) {
		Post post = postService.getPostById(postId);
		if (null == post || post.getDefunct()) {
			return false;
		}
		model.addAttribute("post", post);
		ProfileCache profileCache = profileService.getProfileCacheByUid(post
				.getCreateUid());
		model.addAttribute("postProfile", profileCache);
		if (context.hasLogin() && context.getUid() != post.getCreateUid()) {
			boolean hasInterest = interestUserService.isInterest(
					context.getUid(), post.getCreateUid());
			boolean hasResponse = postService.isResponsePost(context.getUid(),
					post.getId());
			model.addAttribute("hasInterest", hasInterest);
			model.addAttribute("hasResponse", hasResponse);
		}
		Long cityId = 0L;
		if (context.hasLogin()) {
			ProfileCache profile = getLoginUserCache(request);
			if (null != profile) {
				cityId = profile.getCity();
			}
		}
		// ideaWidget(context, cityId, model, postDetailRightIdeaRows);
		model.addAttribute("commentTotalCnt",
				postCommentService.countPostComment(postId));
		model.addAttribute("respTotalCnt",
				postService.countResponseUser(postId));
		return true;
	}
}
