package com.juzhai.passport.controller.website;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.juzhai.preference.bean.Input;
import com.juzhai.preference.model.Preference;
import com.juzhai.preference.model.UserPreference;
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
		Collection<Preference> preferences = preferenceService
				.listCachePreference().values();
		List<UserPreferenceView> views = new ArrayList<UserPreferenceView>();
		List<UserPreference> userPreferences = userPreferenceService
				.listUserPreference(context.getUid());
		// TODO Preference能进行缓存，这次版本不改了
		for (Preference preference : preferences) {
			try {
				UserPreferenceView view = new UserPreferenceView(preference,
						null, Input.convertToBean(preference.getInput()));
				// TODO (done) 如果我没有UserPreference，那页面上还有preference显示吗？
				for (UserPreference userPreference : userPreferences) {
					// TODO (done) 这里永远不会有相等的情况
					if (userPreference.getPreferenceId().longValue() == preference
							.getId().longValue()) {
						if (StringUtils.isEmpty(userPreference.getAnswer())) {
							userPreference.setAnswer(preference
									.getDefaultAnswer());
						}
						view.setUserPreference(userPreference);
						break;
					}
				}
				if (view.getUserPreference() == null) {
					UserPreference defaultUserPreference = new UserPreference();
					defaultUserPreference.setAnswer(preference
							.getDefaultAnswer());
					view.setUserPreference(defaultUserPreference);
				}
				views.add(view);
			} catch (Exception e) {
				log.error("preference convertToBean json is error ");
			}
		}
		model.addAttribute("views", views);
		return "web/profile/preference";
	}

	@ResponseBody
	@RequestMapping(value = "/preference/save", method = RequestMethod.POST)
	public AjaxResult save(HttpServletRequest request, Model model,
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
