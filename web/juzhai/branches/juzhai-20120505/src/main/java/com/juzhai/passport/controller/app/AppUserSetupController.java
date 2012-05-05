package com.juzhai.passport.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.service.IUserSetupService;

//@Controller
@RequestMapping(value = "app")
public class AppUserSetupController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IUserSetupService userSetupService;

	@RequestMapping(value = "/setupAdvise", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult setupAdvise(HttpServletRequest request, Model model,
			boolean isAdvise) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		result.setResult(userSetupService.setupTpAdvise(context.getUid(),
				isAdvise));
		return result;
	}
}
