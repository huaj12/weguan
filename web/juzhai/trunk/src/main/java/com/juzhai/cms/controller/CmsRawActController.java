package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActDetailService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.act.service.IUploadImageService;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;

@Controller
@RequestMapping("/cms")
public class CmsRawActController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IRawActService rawActService;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private IActService actService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IUploadImageService uploadImageService;
	@Autowired
	private IActDetailService actDetailService;

	@RequestMapping(value = "/showRawActs", method = RequestMethod.GET)
	public String showRawActs(
			Model model) {
		List<RawAct> rawActs = rawActService.getRawActs(0, 10);
		model.addAttribute("rawActs", rawActs);
		return "cms/show_raw_act";
	}

	@RequestMapping(value = "/pageShowRawActs", method = RequestMethod.GET)
	public String pageShowRawActs(@RequestParam(defaultValue = "1") int pageId,
			Model model) {
		PagerManager pager = new PagerManager(pageId, 10,
				rawActService.getRawActCount());
		List<RawAct> rawActs = rawActService.getRawActs(pager.getFirstResult(),
				pager.getMaxResult());
		model.addAttribute("rawActs", rawActs);
		return "cms/raw_act_list";
	}

	@RequestMapping(value = "/showManagerRawAct", method = RequestMethod.GET)
	public String showManagerRawAct(Model model,@RequestParam(defaultValue = "0")  long id) {
		try {
			model.addAttribute("rawAct", rawActService.getRawAct(id));
			assembleCiteys(model);
		} catch (Exception e) {
			log.error("showManagerRawAct is error." + e.getMessage());
			return "";
		}
		return "show_manager_raw_act";
	}

	@RequestMapping(value = "/ajax/delRawAct", method = RequestMethod.POST)
	public String delRawAct(Model model,@RequestParam(defaultValue = "0")  long id) {
		try {
			rawActService.delteRawAct(id);
		} catch (Exception e) {
			log.error("showManagerRawAct is error." + e.getMessage());
			return "";
		}
		return "show_manager_raw_act";
	}
	
	@RequestMapping(value = "/ajax/AgreeRawAct", method = RequestMethod.POST)
	public AjaxResult AgreeRawAct(HttpServletRequest request,Long province,Long city, Model model,
			String name,String detail,String logo,Long categoryId,String address,String startTime,String endTime){
		UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();
		Act act=new Act();
		act.setAddress(address);
		act.setCity(city);
		act.setProvince(province);
		act.setName(name);
		List<Long> list=new ArrayList<Long>();
		list.add(categoryId);
		try {
			actService.createAct(act, list);
			String filename=uploadImageService.intoActLogo(logo, act.getId());
			act.setLogo(filename);
			actService.updateAct(act, list);
			detail=uploadImageService.intoEditorImg(detail, context.getUid());
			actDetailService.addActDetail(act.getId(), detail);
		} catch (Exception e) {
			result.setError(e.getMessage(), messageSource);
			return result;
		}
		result.setSuccess(true);
		return result;
	}
	
	

	private void assembleCiteys(Model model) {
		List<City> citys = new ArrayList<City>();
		List<Province> provinces = new ArrayList<Province>();
		for (Entry<Long, City> entry : com.juzhai.passport.InitData.CITY_MAP
				.entrySet()) {
			citys.add(entry.getValue());
		}
		for (Entry<Long, Province> entry : com.juzhai.passport.InitData.PROVINCE_MAP
				.entrySet()) {
			provinces.add(entry.getValue());
		}
		List<Category> categoryList = actCategoryService.findAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("citys", citys);
		model.addAttribute("provinces", provinces);
	}
}
