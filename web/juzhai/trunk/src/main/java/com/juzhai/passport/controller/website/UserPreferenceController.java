package com.juzhai.passport.controller.website;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.controller.form.UserPreferenceListForm;
import com.juzhai.passport.controller.view.UserPreferenceView;
import com.juzhai.passport.model.Preference;
import com.juzhai.preference.bean.Input;
import com.juzhai.preference.service.IPreferenceService;
import com.juzhai.preference.service.IUserPreferenceService;

@Controller
@RequestMapping(value = "profile")
public class UserPreferenceController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IUserPreferenceService userPreferenceService;
	@Autowired
	private IPreferenceService preferenceService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/preference", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		List<Preference> preferences = preferenceService.listPreference();
		List<UserPreferenceView> views = new ArrayList<UserPreferenceView>();
		for (Preference preference : preferences) {
			try {
				views.add(new UserPreferenceView(preference,
						userPreferenceService.getUserPreference(
								preference.getId(), context.getUid()), Input
								.convertToBean(preference.getInput())));
			} catch (Exception e) {
				log.error("preference convertToBean json is error ");
			}
		}
		model.addAttribute("views", views);
		return "web/profile/preference";
	}

	@ResponseBody
	@RequestMapping(value = "/add/preference", method = RequestMethod.POST)
	public AjaxResult add(HttpServletRequest request, Model model,
			UserPreferenceListForm userPreferenceListForm)
			throws NeedLoginException {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = checkLoginForWeb(request);
		try {
			userPreferenceService.addUserPreference(userPreferenceListForm,
					context.getUid());
		} catch (Exception e) {
			ajaxResult.setError(e.getMessage(), messageSource);
		}
		return ajaxResult;
	}
}
