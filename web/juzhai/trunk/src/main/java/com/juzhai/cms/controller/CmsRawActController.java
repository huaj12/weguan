package com.juzhai.cms.controller;

import java.text.ParseException;
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
	// TODO (review) 不用的就删掉吧
	private IActService actService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	// TODO (review) 不用的就删掉吧
	private IUploadImageService uploadImageService;
	@Autowired
	// TODO (review) 不用的就删掉吧
	private IActDetailService actDetailService;

	@RequestMapping(value = "/showRawActs", method = RequestMethod.GET)
	public String showRawActs(@RequestParam(defaultValue = "1") int pageId,
			Model model) {
		// TODO（review）每页显示一个？
		PagerManager pager = new PagerManager(pageId, 1,
				rawActService.getRawActCount());
		List<RawAct> rawActs = rawActService.getRawActs(pager.getFirstResult(),
				pager.getMaxResult());
		model.addAttribute("rawActs", rawActs);
		model.addAttribute("pager", pager);
		return "cms/show_raw_act";
	}

	@RequestMapping(value = "/showManagerRawAct", method = RequestMethod.GET)
	public String showManagerRawAct(Model model,
			@RequestParam(defaultValue = "0") long id) {
		try {
			model.addAttribute("rawAct", rawActService.getRawAct(id));
			// TODO (review) 这部分代码看到太多了，想过公用吗？
			assembleCiteys(model);
		} catch (Exception e) {
			log.error("showManagerRawAct is error." + e.getMessage());
			// TODO (review) 返回空字符串是什么意思？
			return "";
		}
		return "cms/show_manager_raw_act";
	}

	@RequestMapping(value = "/ajax/delRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult delRawAct(Model model,
			@RequestParam(defaultValue = "0") long id) {
		// TODO (review) 为什么要有defaultValue=0?首先已经是0了，然后下面是不是应该判断一下是否>0呢？
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

	// TODO (review) 注意规范！规范比任何都重要！！
	@RequestMapping(value = "/ajax/AgreeRawAct", method = RequestMethod.POST)
	@ResponseBody
	// TODO (review) 参数过多，应该封装成一个form
	public AjaxResult agreeRawAct(@RequestParam(defaultValue = "0") long id,
			HttpServletRequest request, Long town, Long province, Long city,
			Model model, String name, String detail, String logo,
			Long categoryIds, String address, String startTime, String endTime,
			@RequestParam(defaultValue = "0") Long createUid) {
		// UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();
		// TODO (review) 封装到service里去吧
		Act act = new Act();
		act.setAddress(address);
		act.setCity(city);
		act.setProvince(province);
		act.setTown(town);
		act.setName(name);
		act.setCreateUid(createUid);
		act.setLogo(logo);
		List<Long> list = new ArrayList<Long>();
		list.add(categoryIds);
		try {
			if (StringUtils.isNotEmpty(startTime)) {
				act.setStartTime(DateUtils.parseDate(startTime,
						new String[] { "yyyy-MM-dd" }));
			}
			if (StringUtils.isNotEmpty(endTime)) {
				act.setEndTime(DateUtils.parseDate(endTime,
						new String[] { "yyyy-MM-dd" }));
			}
		} catch (ParseException e) {
			result.setErrorInfo(e.getMessage());
			result.setSuccess(false);
			return result;
		}
		try {
			rawActService.agreeRawAct(act, list, detail, id);
		} catch (ActInputException e) {
			result.setError(e.getErrorCode(), messageSource);
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	// TODO (review)想想怎么公用?另外下面的循环是否有必要?
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
		List<Category> categoryList = actCategoryService.findAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("citys", citys);
		model.addAttribute("provinces", provinces);
	}
}
