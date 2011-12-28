package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.RawActInputException;
import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.cms.controller.form.AgreeRawActForm;
import com.juzhai.cms.controller.view.CmsRawActView;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping("/cms")
public class CmsRawActController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	private static final String ERROR = "cms/error";
	@Autowired
	private IRawActService rawActService;
	@Autowired
	private IActImageService actImageService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/showRawActs", method = RequestMethod.GET)
	public String showRawActs(@RequestParam(defaultValue = "1") int pageId,
			Model model) {
		// TODO（done）每页显示一个？
		PagerManager pager = new PagerManager(pageId, 20,
				rawActService.getRawActCount());
		List<RawAct> rawActs = rawActService.getRawActs(pager.getFirstResult(),
				pager.getMaxResult());
		List<CmsRawActView> list = new ArrayList<CmsRawActView>();
		for (RawAct r : rawActs) {
			ProfileCache cache = profileService.getProfileCacheByUid(r
					.getCreateUid());
			list.add(new CmsRawActView(r, cache.getNickname()));
		}
		model.addAttribute("rawActs", list);
		model.addAttribute("pager", pager);
		return "cms/show_raw_act";
	}

	@RequestMapping(value = "/showManagerRawAct", method = RequestMethod.GET)
	public String showManagerRawAct(Model model,
			@RequestParam(defaultValue = "0") long id) {
		try {
			model.addAttribute("rawAct", rawActService.getRawAct(id));
			assembleCiteys(model);
		} catch (Exception e) {
			log.error("showManagerRawAct is error." + e.getMessage());
			return ERROR;
		}
		return "cms/show_manager_raw_act";
	}

	@RequestMapping(value = "/ajax/delRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delRawAct(Model model,
			@RequestParam(defaultValue = "0") long id, String reasonMsg,
			long receive) {
		AjaxResult result = new AjaxResult();
		try {
			if (id > 0) {
				rawActService.delteRawAct(id);
				// TODO 发送删除原因receive接收者 拒绝原因reasonMsg
			} else {
				result.setSuccess(false);
				result.setErrorInfo("showManagerRawAct is error. id is null");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorInfo("showManagerRawAct is error." + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/ajax/agreeRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult agreeRawAct(HttpServletRequest request, Model model,
			AgreeRawActForm agreeRawActForm) {
		// UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();

		try {
			rawActService.agreeRawAct(agreeRawActForm);
		} catch (ActInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (RawActInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/logo/upload")
	@ResponseBody
	public AjaxResult logoUpload(HttpServletRequest request, Model model,
			@RequestParam("profileLogo") MultipartFile profileLogo) {
		AjaxResult result = new AjaxResult();
		UserContext context = (UserContext) request.getAttribute("context");
		try {
			String[] urls = actImageService.uploadRawActLogo(context.getUid(),
					profileLogo);
			result.setResult(urls);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/kindEditor/upload")
	@ResponseBody
	public Map<String, Object> kindEditorUpload(HttpServletRequest request,
			@RequestParam("imgFile") MultipartFile imgFile) {
		try {
			UserContext context = (UserContext) request.getAttribute("context");
			String[] urls = actImageService.uploadRawActLogo(context.getUid(),
					imgFile);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", 0);
			map.put("url", urls[0]);
			return map;
		} catch (UploadImageException e) {
			return getError(e.getErrorCode());
		}

	}

	private Map<String, Object> getError(String errorCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 1);
		map.put("message", messageSource.getMessage(errorCode, null,
				Locale.SIMPLIFIED_CHINESE));
		return map;
	}
}
