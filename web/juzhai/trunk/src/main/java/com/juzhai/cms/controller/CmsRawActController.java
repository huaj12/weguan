package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.juzhai.passport.model.Town;

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
	public String showRawActs(@RequestParam(defaultValue = "1") int pageId,
			Model model) {
		PagerManager pager = new PagerManager(pageId, 1,
				rawActService.getRawActCount());
		List<RawAct> rawActs = rawActService.getRawActs(pager.getFirstResult(),
				pager.getMaxResult());
		model.addAttribute("rawActs", rawActs);
		model.addAttribute("pager", pager);
		return "cms/show_raw_act";
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
		return "cms/show_manager_raw_act";
	}

	@RequestMapping(value = "/ajax/delRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delRawAct(Model model,@RequestParam(defaultValue = "0")  long id) {
		AjaxResult result = new AjaxResult();
		try {
			rawActService.delteRawAct(id);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorInfo("showManagerRawAct is error." + e.getMessage());
			return result;
		}
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/ajax/AgreeRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult AgreeRawAct(@RequestParam(defaultValue = "0")  long id,HttpServletRequest request,Long town,Long province,Long city, Model model,
			String name,String detail,String logo,Long categoryIds,String address,String startTime,String endTime,@RequestParam(defaultValue = "0")Long createUid){
		UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();
		Act act=new Act();
		act.setAddress(address);
		act.setCity(city);
		act.setProvince(province);
		act.setTown(town);
		act.setName(name);
		act.setCreateUid(createUid);
		List<Long> list=new ArrayList<Long>();
		list.add(categoryIds);
		try {
			if (StringUtils.isNotEmpty(startTime)) {
				act.setStartTime( DateUtils.parseDate(startTime,
						new String[] { "yyyy-MM-dd" }));
			}
			if (StringUtils.isNotEmpty(endTime)) {
				act.setEndTime( DateUtils.parseDate(endTime,
						new String[] { "yyyy-MM-dd" }));
			}
			actService.createAct(act, list);
			String filename=uploadImageService.intoActLogo(logo, act.getId());
			act.setLogo(filename);
			actService.updateAct(act, list);
			detail=uploadImageService.intoEditorImg(detail, context.getUid());
			actDetailService.addActDetail(act.getId(), detail);
			rawActService.delteRawAct(id);
		} catch (Exception e) {
			result.setErrorInfo(e.getMessage());
			result.setSuccess(false);
			return result;
		}
		result.setSuccess(true);
		return result;
	}
	
	

	private void assembleCiteys(Model model) {
		List<City> citys = new ArrayList<City>();
		List<Province> provinces = new ArrayList<Province>();
		List<Town> towns = new ArrayList<Town>();
		for (Entry<Long, City> entry : com.juzhai.passport.InitData.CITY_MAP
				.entrySet()) {
			citys.add(entry.getValue());
		}
		for (Entry<Long, Province> entry : com.juzhai.passport.InitData.PROVINCE_MAP
				.entrySet()) {
			provinces.add(entry.getValue());
		}
		for (Entry<Long, Town> entry : com.juzhai.passport.InitData.TOWN_MAP
				.entrySet()) {
			towns.add(entry.getValue());
		}

		model.addAttribute("towns", towns);
		List<Category> categoryList =actCategoryService.findAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("citys", citys);
		model.addAttribute("provinces", provinces);
	}
}
