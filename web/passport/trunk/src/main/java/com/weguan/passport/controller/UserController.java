package com.weguan.passport.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weguan.passport.core.SystemConfig;
import com.weguan.passport.core.web.UserContext;
import com.weguan.passport.exception.NeedLoginException;
import com.weguan.passport.exception.WeguanException;
import com.weguan.passport.form.ModifyForm;
import com.weguan.passport.model.Blog;
import com.weguan.passport.service.IBlogService;
import com.weguan.passport.service.IRegisterService;

@Controller
@RequestMapping(value = "/home")
public class UserController extends BaseController {

	@Autowired
	private IBlogService blogService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginReturnContext(request);
		Blog blog = blogService.getBlogByUid(context.getUid());
		model.addAttribute("blog", blog);
		model.addAttribute("isOwn", true);
		return "user/IndexPage";
	}

	@RequestMapping(value = "/modify")
	@ResponseBody
	public Object[] modify(HttpServletRequest request, ModifyForm modifyForm,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginReturnContext(request);
		try {
			// update passport
			registerService.modify(context.getUid(), modifyForm);
			// update blog
			blogService.modify(context.getUid(), modifyForm);
		} catch (WeguanException e) {
			return _modifyResult(false, e.getErrorCode());
		}
		return _modifyResult(true, SystemConfig.BASIC_DOMAIN + "/home");
	}

	private Object[] _modifyResult(boolean isSuccess, String info) {
		return new Object[] {
				isSuccess,
				isSuccess ? info : messageSource.getMessage(info, null,
						Locale.SIMPLIFIED_CHINESE) };
	}
}
