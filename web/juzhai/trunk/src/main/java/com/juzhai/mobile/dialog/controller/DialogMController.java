package com.juzhai.mobile.dialog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.model.DialogContent;
import com.juzhai.home.service.IDialogService;
import com.juzhai.mobile.common.web.response.ListJsonResult;
import com.juzhai.mobile.dialog.controller.view.DialogContentMView;
import com.juzhai.mobile.dialog.controller.view.DialogMView;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Controller
@RequestMapping(value = "mobile/dialog")
public class DialogMController extends BaseController {

	@Autowired
	private IDialogService dialogService;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IIdeaService ideaService;
	// @Value("mobile.dialog.max.rows")
	private int mobileDialogMaxRows = 1;
	// @Value("mobile.dialog.content.max.rows")
	private int mobileDialogContentsMaxRows = 10;
	private int mobileRefreshDialogContentsCount = 10;

	@RequestMapping(value = "/dialogList", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult dialogList(HttpServletRequest request, Model model,
			int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, mobileDialogMaxRows,
				dialogService.countDialong(context.getUid()));
		List<DialogView> dialogViewlist = dialogService.listDialog(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		List<DialogMView> list = new ArrayList<DialogMView>(
				dialogViewlist.size());
		for (DialogView dialogView : dialogViewlist) {
			DialogMView dialogMView = new DialogMView();
			dialogMView.setDialogId(dialogView.getDialog().getId());
			dialogMView.setReceiverUid(dialogView.getDialogContent()
					.getReceiverUid());
			dialogMView.setLatestContent(dialogView.getDialogContent()
					.getContent());
			dialogMView.setCreateTime(dialogView.getDialogContent()
					.getCreateTime().getTime());
			dialogMView.setDialogContentCount(dialogView.getDialogContentCnt());
			dialogMView.setTargetUser(userMViewHelper.createUserMView(context,
					dialogView.getTargetProfile(), false));

			list.add(dialogMView);
		}
		noticeService.emptyNotice(context.getUid(), NoticeType.DIALOG);

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, list);
		return result;
	}

	@RequestMapping(value = "/deleteDialog", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteDialog(HttpServletRequest request, long dialogId)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		boolean success = dialogService
				.deleteDialog(context.getUid(), dialogId);
		AjaxResult result = new AjaxResult();
		result.setSuccess(success);
		return result;
	}

	@RequestMapping(value = "/sendDate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendDate(HttpServletRequest request, long targetUid,
			long ideaId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		Idea idea = ideaService.getIdeaById(ideaId);
		if (idea == null || targetUid <= 0) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
			return result;
		}
		if (StringUtils.isNotEmpty(idea.getContent())) {
			try {
				dialogService.sendDatingSMS(context, targetUid,
						DialogContentTemplate.PRIVATE_DATE, idea.getContent());
			} catch (DialogException e) {
				result.setError(e.getErrorCode(), messageSource);
			}
		}
		return result;
	}

	@RequestMapping(value = "/dialogContentList", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult dialogContentList(HttpServletRequest request,
			int page, long uid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page,
				mobileDialogContentsMaxRows, dialogService.countDialogContent(
						context.getUid(), uid));
		List<DialogContentView> dialogContentViewList = dialogService
				.listDialogContent(context.getUid(), uid,
						pager.getFirstResult(), pager.getMaxResult());
		List<DialogContentMView> list = new ArrayList<DialogContentMView>(
				dialogContentViewList.size());
		for (DialogContentView dialogContentView : dialogContentViewList) {
			list.add(DialogContentMView
					.converFromDialogContentView(dialogContentView));
		}
		// noticeService.emptyNotice(context.getUid(), NoticeType.DIALOG);

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, list);
		return result;
	}

	@RequestMapping(value = "/refreshDialogContent", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult refreshDialogContent(HttpServletRequest request,
			long uid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		List<DialogContentView> dialogContentViewList = dialogService
				.listDialogContent(context.getUid(), uid, 0,
						mobileRefreshDialogContentsCount);
		List<DialogContentMView> list = new ArrayList<DialogContentMView>(
				dialogContentViewList.size());
		for (DialogContentView dialogContentView : dialogContentViewList) {
			list.add(DialogContentMView
					.converFromDialogContentView(dialogContentView));
		}
		ListJsonResult result = new ListJsonResult();
		result.setResult(null, list);
		return result;
	}

	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendSms(
			HttpServletRequest request,
			String content,
			long uid,
			@RequestParam(value = "dialogImg", required = false) MultipartFile dialogImg)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			long dialogContentId = dialogService.sendSMS(context, uid, content,
					dialogImg);
			DialogContent dialogContent = dialogService
					.getDialogContent(dialogContentId);
			result.setResult(DialogContentMView
					.converFromDialogContent(dialogContent));
		} catch (DialogException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/notice/nums", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult noticeNums(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		result.setResult(noticeService.getNoticeNum(context.getUid(),
				NoticeType.DIALOG));
		return result;
	}
}
