package com.juzhai.home.controller;

import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.service.IDialogService;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.stats.counter.service.ICounter;

@Controller
@RequestMapping(value = "home")
public class DialogController extends BaseController {

	@Autowired
	private IDialogService dialogService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private ICounter dialogContentCounter;
	@Autowired
	private ICounter privateDateCounter;
	@Autowired
	private IIdeaService ideaService;
	@Value("${show.dialogs.max.rows}")
	private int showDialogsMaxRows;
	@Value("${show.dialog.contents.max.rows}")
	private int showDialogContentsMaxRows;

	@RequestMapping(value = "/dialog/{page}", method = RequestMethod.GET)
	public String dialogList(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, showDialogsMaxRows,
				dialogService.countDialong(context.getUid()));
		List<DialogView> dialogViewList = dialogService.listDialog(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("dialogViewList", dialogViewList);
		model.addAttribute("pager", pager);
		noticeService.emptyNotice(context.getUid(), NoticeType.DIALOG);
		return "web/home/dialog/dialog";
	}

	@RequestMapping(value = "/dialogContent/{uid}/{page}", method = RequestMethod.GET)
	public String dialogContentList(HttpServletRequest request, Model model,
			@PathVariable int page, @PathVariable long uid)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, showDialogContentsMaxRows,
				dialogService.countDialogContent(context.getUid(), uid));
		List<DialogContentView> dialogContentViewList = dialogService
				.listDialogContent(context.getUid(), uid,
						pager.getFirstResult(), pager.getMaxResult());
		isShield(model, context.getUid(), uid);
		model.addAttribute("dialogContentViewList", dialogContentViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("targetProfile",
				profileService.getProfileCacheByUid(uid));
		loadFaces(model);
		return "web/home/dialog/dialog_content";
	}

	@RequestMapping(value = "/deleteDialog", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteDialog(HttpServletRequest request, Model model,
			long dialogId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		boolean success = dialogService
				.deleteDialog(context.getUid(), dialogId);
		AjaxResult result = new AjaxResult();
		result.setSuccess(success);
		return result;
	}

	@RequestMapping(value = "/deleteDialogContent", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteDialogContent(HttpServletRequest request,
			Model model, long targetUid, long dialogContentId)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		long dialogContentCnt = dialogService.deleteDialogContent(
				context.getUid(), targetUid, dialogContentId);
		AjaxResult result = new AjaxResult();
		result.setResult(dialogContentCnt);
		return result;
	}

	@RequestMapping(value = "/presendmsg", method = RequestMethod.GET)
	public String preSendMessage(HttpServletRequest request, Model model,
			long targetUid) throws NeedLoginException {
		checkLoginForWeb(request);
		ProfileCache profile = profileService.getProfileCacheByUid(targetUid);
		if (null == profile) {
			return error_500;
		}
		model.addAttribute("targetUid", profile.getUid());
		model.addAttribute("targetNickname", profile.getNickname());
		loadFaces(model);
		return "web/home/dialog/send_box";
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendSMS(HttpServletRequest request, Model model,
			String content, long targetUid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			dialogService.sendSMS(context, targetUid, content);
			dialogContentCounter.incr(null, 1L);
		} catch (DialogException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/senddate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendDate(HttpServletRequest request, Model model,
			String content, long targetUid, Long ideaId)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		Idea idea = null;
		if (null != ideaId && ideaId > 0) {
			idea = ideaService.getIdeaById(ideaId);
			if (null != idea) {
				content = idea.getContent();
			}
		}
		try {
			if (StringUtils.isNotEmpty(content)) {
				if (null == idea) {
					dialogService.sendDatingSMS(context, targetUid,
							DialogContentTemplate.PRIVATE_DATE, content);
				} else {
					dialogService.sendSMS(context.getUid(), targetUid,
							DialogContentTemplate.PRIVATE_DATE, content);
				}
				privateDateCounter.incr(null, 1L);
			}
		} catch (DialogException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/replyMessage", method = RequestMethod.POST)
	public String replySMS(HttpServletRequest request, Model model,
			String content, long targetUid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			long dialogContentId = dialogService.sendSMS(context, targetUid,
					content);
			model.addAttribute("success", true);
			DialogContentView view = new DialogContentView();
			view.setDialogContent(dialogService
					.getDialogContent(dialogContentId));
			view.setProfile(profileService.getProfileCacheByUid(view
					.getDialogContent().getSenderUid()));
			model.addAttribute("dialogContentView", view);
			model.addAttribute("targetProfile",
					profileService.getProfileCacheByUid(targetUid));
			dialogContentCounter.incr(null, 1L);
		} catch (DialogException e) {
			model.addAttribute("success", false);
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
		}
		return "web/home/dialog/dialog_content_fragment";
	}

	@RequestMapping(value = "/inviteEditProfile", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult inviteEditProfile(HttpServletRequest request,
			Model model, long uid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			dialogService.sendSMS(context.getUid(), uid,
					DialogContentTemplate.EDIT_PROFILE);
		} catch (DialogException e) {
		}
		return result;
	}

	@RequestMapping(value = "/inviteUploadLogo", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult inviteUploadLogo(HttpServletRequest request, Model model,
			long uid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			dialogService.sendSMS(context.getUid(), uid,
					DialogContentTemplate.UPLOAD_LOGO);
		} catch (DialogException e) {
		}
		return result;
	}

	@RequestMapping(value = "/rescueUserSMS", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult rescueUser(HttpServletRequest request, Model model,
			String uids, String postContent) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			String[] uidStrs = uids.split(",");
			for (String uidStr : uidStrs) {
				dialogService.sendSMS(context.getUid(), Long.parseLong(uidStr),
						DialogContentTemplate.RESCUE_USER, postContent);
			}
		} catch (DialogException e) {
		}
		return result;
	}
}
