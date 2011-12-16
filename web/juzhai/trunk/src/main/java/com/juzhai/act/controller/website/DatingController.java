package com.juzhai.act.controller.website;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.bean.ConsumeType;
import com.juzhai.act.bean.ContactType;
import com.juzhai.act.controller.form.DatingForm;
import com.juzhai.act.exception.DatingInputException;
import com.juzhai.act.service.IDatingService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping(value = "act")
public class DatingController extends BaseController {

	@Autowired
	private IDatingService datingService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private MessageSource messageSource;
	@Value("${dating.user.act.count}")
	private int datingUserActCount = 10;

	@RequestMapping(value = "/openDating", method = RequestMethod.GET)
	public String openDating(HttpServletRequest request, long uid, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		boolean error = false;
		try {
			queryProfile(uid, model);
			datingService.checkCanDate(context.getUid(), uid);
			model.addAttribute("userActViewList",
					userActService.pageUserActView(uid, 1, datingUserActCount));
		} catch (DatingInputException e) {
			error = true;
			String errorInfo = messageSource.getMessage(e.getErrorCode(), null,
					Locale.SIMPLIFIED_CHINESE);
			model.addAttribute("error", error);
			model.addAttribute("errorInfo", errorInfo);
		}
		return "web/dating/dating_dialog";
	}

	@ResponseBody
	@RequestMapping(value = "/date", method = RequestMethod.POST)
	public AjaxResult date(HttpServletRequest request, DatingForm datingForm,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			datingService.date(context.getUid(), datingForm.getUid(),
					datingForm.getActId(),
					ConsumeType.getConsumeTypeByValue(datingForm
							.getConsumeType()),
					ContactType.getContactTypeByValue(datingForm
							.getContactType()), datingForm.getContactValue());
			result.setSuccess(true);
		} catch (DatingInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteDating", method = RequestMethod.POST)
	public AjaxResult deleteDating(HttpServletRequest request, long datingId,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		datingService.deleteDating(context.getUid(), datingId);
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/modifyDating", method = RequestMethod.POST)
	public AjaxResult modifyDating(HttpServletRequest request,
			DatingForm datingForm, Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			datingService.modifyDating(context.getUid(), datingForm
					.getDatingId(), datingForm.getActId(), ConsumeType
					.getConsumeTypeByValue(datingForm.getConsumeType()),
					ContactType.getContactTypeByValue(datingForm
							.getContactType()), datingForm.getContactValue());
			result.setSuccess(true);
		} catch (DatingInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
