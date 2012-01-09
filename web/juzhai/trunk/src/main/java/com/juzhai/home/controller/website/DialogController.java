package com.juzhai.home.controller.website;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.service.IDialogService;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;

@Controller
@RequestMapping(value = "home")
public class DialogController extends BaseController {

	@Autowired
	private IDialogService dialogService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private MessageSource messageSource;
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
		model.addAttribute("dialogContentViewList", dialogContentViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("targetUid", uid);
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
			Model model, long uid, long dialogContentId)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		boolean success = dialogService.deleteDialogContent(context.getUid(),
				uid, dialogContentId);
		AjaxResult result = new AjaxResult();
		result.setSuccess(success);
		return result;
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public AjaxResult sendSMS(HttpServletRequest request, Model model,
			String content, long targetUid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			dialogService.sendSMS(context.getUid(), targetUid, content);
		} catch (DialogException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
