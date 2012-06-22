package com.juzhai.cms.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.juzhai.cms.exception.RobotInputException;
import com.juzhai.cms.service.IRobotService;
import com.juzhai.common.InitData;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.InterestUserException;
import com.juzhai.post.exception.InputPostCommentException;
import com.juzhai.post.exception.InputPostException;

@Controller
@RequestMapping("/cms")
public class CmsRobotController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IRobotService robotService;

	@RequestMapping(value = "/robot/import", method = RequestMethod.POST)
	public ModelAndView importConfig(HttpServletRequest request, Model model,
			@RequestParam("robotConfig") MultipartFile robotConfig) {
		ModelMap mmap = new ModelMap();
		try {
			int count = robotService.importRobot(robotConfig);
			mmap.addAttribute("msg", "success import count=" + count);
		} catch (RobotInputException e) {
			mmap.addAttribute("msg", messageSource.getMessage(e.getErrorCode(),
					null, Locale.SIMPLIFIED_CHINESE));
		}
		return new ModelAndView("redirect:/cms/robot/show", mmap);
	}

	@RequestMapping(value = "/robot/show", method = RequestMethod.GET)
	public String show(String msg, Model model, Long province, Long city) {
		List<ProfileCache> profileList = robotService.listRobot(city);
		model.addAttribute("profileList", profileList);
		model.addAttribute("msg", msg);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("cityId", city);
		model.addAttribute("provinceId", province);

		return "/cms/robot/show";
	}

	@RequestMapping(value = "/robot/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult add(Long uid, Long cityId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			robotService.add(uid, cityId);
		} catch (RobotInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/robot/del", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult del(Long uid, Long cityId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			robotService.del(uid, cityId);
		} catch (RobotInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/robot/interest", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult interest(Long cityId, Long targetUid) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			robotService.interest(cityId, targetUid);
		} catch (RobotInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		} catch (InterestUserException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/robot/comment", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult comment(Long cityId, Long postId, String text) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			robotService.comment(cityId, postId, text);
		} catch (RobotInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		} catch (InputPostCommentException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/robot/response", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult response(Long cityId, Long postId) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			robotService.response(cityId, postId);
		} catch (RobotInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		} catch (InputPostException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}
}
