package com.juzhai.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.controller.form.CategoryLiatFrom;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.post.model.Category;
import com.juzhai.post.service.ICategoryService;

@Controller
@RequestMapping("/cms")
public class CmsCategoryController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ICategoryService categoryService;

	@RequestMapping(value = "/cmsShowCategoryList", method = RequestMethod.GET)
	public String cmsShowCategoryList(HttpServletRequest request, Model model) {
		List<Category> cats = categoryService.getAllCategory();
		model.addAttribute("cats", cats);
		return "cms/cmsCategoryList";
	}

	@RequestMapping(value = "/cmsUpdateCategory", method = RequestMethod.GET)
	public String cmsUpdateCategory(CategoryLiatFrom categoryForm) {
		categoryService.updateCategory(categoryForm);
		return "redirect:/cms/cmsShowCategoryList";
	}

	@RequestMapping(value = "/cmsDeleteCategory", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult cmsDeleteCategory(
			@RequestParam(defaultValue = "0") long catId) {
		AjaxResult ajaxResult = new AjaxResult();
		if (categoryService.deleteCategory(catId)) {
			ajaxResult.setSuccess(true);
		} else {
			ajaxResult.setErrorInfo("该分类下有内容");
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

}
