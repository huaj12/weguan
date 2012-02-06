package com.juzhai.post.controller.website;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;
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

	@RequestMapping(value = "/responsePost", method = RequestMethod.POST)
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
			PostForm postForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			postService.createPost(context.getUid(), postForm);
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
			postService.createPost(context.getUid(), postForm);
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		if (sendWeibo) {
			// TODO 发送微博
		}
		return result;
	}
}
