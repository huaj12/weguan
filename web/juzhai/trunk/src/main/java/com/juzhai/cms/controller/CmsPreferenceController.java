package com.juzhai.cms.controller;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.form.PreferenceForm;
import com.juzhai.cms.controller.form.PreferenceListForm;
import com.juzhai.cms.controller.view.CmsPreferenceView;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.preference.bean.Input;
import com.juzhai.preference.exception.InputPreferenceException;
import com.juzhai.preference.model.Preference;
import com.juzhai.preference.service.IPreferenceService;

@Controller
@RequestMapping("/cms")
public class CmsPreferenceController {
	@Autowired
	private IPreferenceService preferenceService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/show/add/preference", method = RequestMethod.GET)
	public String showAddPrefernce(Model model, String msg,
			PreferenceForm preferenceForm) {
		model.addAttribute("msg", msg);
		model.addAttribute("form", preferenceForm);
		return "/cms/preference/add_preference";
	}

	@RequestMapping(value = "/show/update/preference", method = RequestMethod.GET)
	public String showUpdatePrefernce(Model model, Long id) {
		CmsPreferenceView view = new CmsPreferenceView();
		try {
			Preference preference = preferenceService.getPreference(id);
			view.setDefaultValues(StringUtils.split(
					preference.getDefaultAnswer(), StringUtil.separator));
			view.setPreference(preference);
			view.setInput(Input.convertToBean(preference.getInput()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("view", view);
		return "/cms/preference/update_preference";
	}

	@RequestMapping(value = "/add/preference", method = RequestMethod.POST)
	public String addPrefernce(Model model, PreferenceForm preferenceForm) {
		try {
			preferenceService.addPreference(preferenceForm);
		} catch (InputPreferenceException e) {
			String msg = messageSource.getMessage(e.getMessage(), null,
					Locale.SIMPLIFIED_CHINESE);
			return showAddPrefernce(model, msg, preferenceForm);
		}
		return listPreference(model);
	}

	@RequestMapping(value = "/update/preference", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult updatePrefernce(Model model, PreferenceForm preferenceForm) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			preferenceService.updatePreference(preferenceForm);
		} catch (InputPreferenceException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/list/preference", method = RequestMethod.GET)
	public String listPreference(Model model) {
		List<Preference> list = preferenceService.listPreference();
		model.addAttribute("prefernces", list);
		return "/cms/preference/list_preference";
	}

	@RequestMapping(value = "/update/preference", method = RequestMethod.GET)
	public String updatePrefernce(Model model,
			PreferenceListForm preferenceListForm) {
		preferenceService.updatePreferenceSort(preferenceListForm);
		return listPreference(model);
	}

	@RequestMapping(value = "/delete/preference", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult deletePrefernce(Long id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			preferenceService.shieldPreference(id);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/load/preference", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult loadPrefernce() {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			preferenceService.loadPreferenceCache();
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/update/sort/preference", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult updatePreferenceSort(PreferenceListForm preferenceListForm) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			preferenceService.updatePreferenceSort(preferenceListForm);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}
}
