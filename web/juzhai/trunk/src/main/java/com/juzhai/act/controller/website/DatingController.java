package com.juzhai.act.controller.website;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

import com.juzhai.act.bean.ConsumeType;
import com.juzhai.act.bean.ContactType;
import com.juzhai.act.controller.form.DatingForm;
import com.juzhai.act.exception.DatingInputException;
import com.juzhai.act.model.Dating;
import com.juzhai.act.service.IActService;
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
	@Autowired
	private IActService actService;
	@Value("${dating.user.act.count}")
	private int datingUserActCount = 10;

	@RequestMapping(value = "/openDating", method = RequestMethod.GET)
	public String openDating(HttpServletRequest request, long uid,
			@RequestParam(defaultValue = "0") long datingId, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			queryProfile(uid, model);
			datingService.checkCanDate(context.getUid(), uid, datingId);
			model.addAttribute("userActViewList",
					userActService.pageUserActView(uid, 0, datingUserActCount));
			if (datingId > 0) {
				Dating dating = datingService.getDatingByDatingId(datingId);
				if (null != dating) {
					if (dating.getStarterUid() != context.getUid()
							|| dating.getReceiverUid() != uid) {
						throw new DatingInputException(
								DatingInputException.ILLEGAL_OPERATION);
					}
					model.addAttribute("dating", dating);
				}
			}
		} catch (DatingInputException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
		}
		return "web/dating/dating_dialog";
	}

	@ResponseBody
	@RequestMapping(value = "/date", method = RequestMethod.POST)
	public AjaxResult date(HttpServletRequest request, DatingForm datingForm,
			Model model) throws NeedLoginException {
		AjaxResult ajaxResult = null;
		if (datingForm.getDatingId() > 0) {
			ajaxResult = modifyDating(request, datingForm, model);
		} else {
			ajaxResult = newDate(request, datingForm, model);
		}
		if (null != ajaxResult && ajaxResult.isSuccess()) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("datingId", datingForm.getDatingId());
			result.put("actId", datingForm.getActId());
			result.put("actName", actService.getActById(datingForm.getActId())
					.getName());
			result.put("consumeType", datingForm.getConsumeType());
			ajaxResult.setResult(result);
		}
		return ajaxResult;
	}

	@ResponseBody
	@RequestMapping(value = "/removeDating", method = RequestMethod.POST)
	public AjaxResult removeDating(HttpServletRequest request, long datingId,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		datingService.deleteDating(context.getUid(), datingId);
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		return result;
	}

	private AjaxResult newDate(HttpServletRequest request,
			DatingForm datingForm, Model model) throws NeedLoginException {
		if (datingForm.getDatingId() > 0) {
			return modifyDating(request, datingForm, model);
		}
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		long datingId = 0;
		try {
			datingId = datingService.date(context.getUid(),
					datingForm.getUid(), datingForm.getActId(),
					ConsumeType.getConsumeTypeByValue(datingForm
							.getConsumeType()),
					ContactType.getContactTypeByValue(datingForm
							.getContactType()), datingForm.getContactValue());
			result.setSuccess(true);
		} catch (DatingInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		datingForm.setDatingId(datingId);
		return result;
	}

	private AjaxResult modifyDating(HttpServletRequest request,
			DatingForm datingForm, Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		long datingId = 0;
		try {
			datingId = datingService.modifyDating(context.getUid(), datingForm
					.getDatingId(), datingForm.getActId(), ConsumeType
					.getConsumeTypeByValue(datingForm.getConsumeType()),
					ContactType.getContactTypeByValue(datingForm
							.getContactType()), datingForm.getContactValue());
			result.setSuccess(true);
		} catch (DatingInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		datingForm.setDatingId(datingId);
		return result;
	}

	@RequestMapping(value = "/openDatingResp", method = RequestMethod.GET)
	public String openDatingResp(HttpServletRequest request, Model model,
			long datingId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			datingService.checkCanRespDating(context.getUid(), datingId);
			model.addAttribute("datingId", datingId);
		} catch (DatingInputException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
		}
		return "web/dating/dating_resp_dialog";
	}

	@ResponseBody
	@RequestMapping(value = "/respDating", method = RequestMethod.POST)
	public AjaxResult respDating(HttpServletRequest request,
			DatingForm datingForm, Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			datingService.acceptDating(context.getUid(), datingForm
					.getDatingId(), ContactType
					.getContactTypeByValue(datingForm.getContactType()),
					datingForm.getContactValue());
			result.setSuccess(true);
			result.setResult(datingForm.getDatingId());
		} catch (DatingInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
