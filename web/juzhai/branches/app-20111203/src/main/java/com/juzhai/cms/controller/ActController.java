package com.juzhai.cms.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.juzhai.act.InitData;
import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.cms.controller.form.AddActForm;
import com.juzhai.cms.controller.form.SearchActForm;
import com.juzhai.cms.controller.view.CmsActMagerView;
import com.juzhai.cms.controller.view.CmsActView;
import com.juzhai.cms.service.IUploadImageService;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzCoreFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;

@Controller
@RequestMapping("/cms")
public class ActController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IActService actService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IUploadImageService uploadImageService;
	@Autowired
	private IActCategoryService actCategoryService;

	@RequestMapping(value = "/searchActs")
	public String searchActs(HttpServletRequest request, Model model,
			SearchActForm form) {
		if (null != form && StringUtils.isNotEmpty(form.getStartDate())
				&& StringUtils.isNotEmpty(form.getEndDate())) {
			try {
				Date startDate = DateUtils.parseDate(form.getStartDate(),
						new String[] { "yyyy-MM-dd" });
				Date endDate = DateUtils.parseDate(form.getEndDate(),
						new String[] { "yyyy-MM-dd" });

				PagerManager pager = new PagerManager(form.getPageId(), 10,
						actService.countNewActs(startDate, endDate));
				List<Act> actList = actService.searchNewActs(startDate,
						endDate, form.getOrder(), pager.getFirstResult(),
						pager.getMaxResult());
				List<CmsActView> viewList = new ArrayList<CmsActView>(
						actList.size());
				for (Act act : actList) {
					viewList.add(new CmsActView(act, actService
							.listSynonymActs(act.getId()), actService
							.isShieldAct(act.getId())));
				}
				model.addAttribute("cmsActViewList", viewList);
				model.addAttribute("pager", pager);
			} catch (ParseException e) {
				log.error("parse search date error.", e);
			}
			model.addAttribute("searchActForm", form);
		}
		return "cms/act_list";
	}

	@RequestMapping(value = "/showSynonym", method = RequestMethod.GET)
	public String showSynonym(HttpServletRequest request, Model model,
			long actId) {
		Act act = actService.getActById(actId);
		if (null != act) {
			List<Act> synonymActList = actService.listSynonymActs(actId);
			List<String> maybeSynonymList = actService.indexSearchName(
					act.getName(), 10);
			maybeSynonymList.remove(act.getName());
			model.addAttribute("maybeSynonymList", maybeSynonymList);
			model.addAttribute("synonymActList", synonymActList);
			model.addAttribute("act", act);
		}
		return "cms/synonym_list";
	}

	@RequestMapping(value = "/addSynonym", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addSynonym(HttpServletRequest request, Model model,
			long actId, String synonymActName) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			try {
				actService.addSynonym(actId, synonymActName);
				ajaxResult.setSuccess(true);
			} catch (ActInputException e) {
				ajaxResult.setSuccess(false);
				ajaxResult.setErrorCode(e.getErrorCode());
				ajaxResult.setErrorInfo(messageSource.getMessage(
						e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			}
		} else {
			ajaxResult.setErrorInfo("The act is null");
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/removeSynonym", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult removeSynonym(HttpServletRequest request, Model model,
			long actId, long removeActId) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			actService.removeSynonym(actId, removeActId);
			ajaxResult.setSuccess(true);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/addShield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addShield(HttpServletRequest request, Model model,
			long actId) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			actService.addActShield(actId);
			ajaxResult.setSuccess(true);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/removeShield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult removeSynonym(HttpServletRequest request, Model model,
			long actId) {
		AjaxResult ajaxResult = new AjaxResult();
		if (actService.actExist(actId)) {
			actService.removeActShield(actId);
			ajaxResult.setSuccess(true);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/showShield", method = RequestMethod.GET)
	public String showShield(HttpServletRequest request, Model model) {
		List<Act> shieldActList = actService.listShieldActs();
		model.addAttribute("shieldActList", shieldActList);
		return "cms/shield_list";
	}

	@RequestMapping(value = "/showActManager", method = RequestMethod.GET)
	public String showActManager(HttpServletRequest request, Model model) {
		assembleCiteys(model);
		return "cms/actManager";
	}

	@RequestMapping(value = "/searchAct", method = RequestMethod.GET)
	public String searchAct(Model model, String bDate, String eDate,
			String name, Integer pageId) {
		List<Act> acts = null;
		if (pageId == null)
			pageId = 1;
		Date startDate = null;
		Date endDate = null;
		try {
			if (!StringUtils.isEmpty(bDate)) {
				startDate = DateUtils.parseDate(bDate,
						new String[] { "yyyy-MM-dd" });
			}
			if (!StringUtils.isEmpty(eDate)) {
				endDate = DateUtils.parseDate(eDate,
						new String[] { "yyyy-MM-dd" });
			}
		} catch (ParseException e) {
			log.error("parse search date error.", e);
		}
		PagerManager pager = new PagerManager(pageId, 10,
				actService.searchActsCount(startDate, endDate, name));
		acts = actService.searchActs(startDate, endDate, name,
				pager.getFirstResult(), pager.getMaxResult());

		List<CmsActMagerView> viewList = new ArrayList<CmsActMagerView>(
				acts.size());
		for (Act act : acts) {
			String age = SuitAge.getByIndex(act.getSuitAge()).getType();
			String status = SuitStatus.getByIndex(act.getSuitStatus())
					.getType();
			String gender = SuitGender.getByIndex(act.getSuitGender())
					.getType();
			City city = com.juzhai.passport.InitData.CITY_MAP
					.get(act.getCity());
			Province pro = com.juzhai.passport.InitData.PROVINCE_MAP.get(act
					.getProvince());
			String proName = "";
			String cityName = "";
			if (pro != null) {
				proName = pro.getName();
			}
			if (city != null) {
				cityName = city.getName();
			}
			String address = "";
			if (act.getAddress() != null) {
				address = act.getAddress();
			}
			String logoWebPath = "";
			if (act.getLogo() != null) {
				logoWebPath = JzCoreFunction.actLogo(act.getId(),
						act.getLogo(), 0);
			}
			StringBuffer categorys = new StringBuffer();
			String cats = act.getCategoryIds();
			if (cats != null) {
				for (String cat : cats.split(",")) {
					Category c = InitData.CATEGORY_MAP.get(Long.valueOf(cat));
					if (c != null) {
						categorys.append(c.getName() + " ");
					}
				}
			}
			viewList.add(new CmsActMagerView(act, logoWebPath, proName,
					cityName, address, age, gender, status, categorys
							.toString()));
		}
		model.addAttribute("cmsActMagerViews", viewList);
		model.addAttribute("pager", pager);
		model.addAttribute("acts", acts);
		model.addAttribute("eDate", eDate);
		model.addAttribute("bDate", bDate);
		model.addAttribute("name", name);
		return "cms/ajax/actManager_list";
	}

	@RequestMapping(value = "/showCreateAct", method = RequestMethod.GET)
	public String showCreateAct(Model model, String msg, String actName,
			Long addUid) {
		assembleCiteys(model);
		model.addAttribute("msg", msg);
		model.addAttribute("actName", actName);
		model.addAttribute("addUid", addUid);
		return "cms/createAct";
	}

	@RequestMapping(value = "/showUpdate", method = RequestMethod.GET)
	public String showUpdate(Model model, Long actId) {
		if (actId == null)
			actId = 0l;
		Act act = actService.getActById(actId);
		assembleCiteys(model);
		model.addAttribute("act", act);
		model.addAttribute("logoWebPath",
				JzCoreFunction.actLogo(act.getId(), act.getLogo(), 0));
		model.addAttribute("age", SuitAge.getByIndex(act.getSuitAge()));
		model.addAttribute("gender", SuitGender.getByIndex(act.getSuitGender()));
		model.addAttribute("stauts", SuitStatus.getByIndex(act.getSuitStatus()));
		return "cms/updateAct";
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
		List<Category> categoryList =actCategoryService.findAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("citys", citys);
		model.addAttribute("suitAges", SuitAge.values());
		model.addAttribute("suitGenders", SuitGender.values());
		model.addAttribute("suitStatus", SuitStatus.values());
		model.addAttribute("provinces", provinces);
	}

	@RequestMapping(value = "/createAct", method = RequestMethod.POST)
	public ModelAndView createAct(AddActForm form, Long addUid,
			HttpServletRequest request) {
		UserContext context = (UserContext) request.getAttribute("context");
		Act act = converAct(form, context.getUid());
		ModelMap mmap = new ModelMap();
		try {
			String logo = null;
			long actId = 0;
			if (act != null && act.getName() != null) {
				Act oldAct = actService.getActByName(act.getName());
				if (oldAct == null) {
					Act a = actService.createAct(act, form.getCatIds());
					if (addUid != null) {
						userActService.addAct(addUid, a.getId());
					}
					actId = a.getId();
					logo = a.getLogo();
					mmap.addAttribute("msg", "create is success");
				} else {
					act.setId(oldAct.getId());
					act.setActive(oldAct.getActive());
					if (CollectionUtils.isNotEmpty(form.getCatIds())) {
						act.setCategoryIds(StringUtils.join(form.getCatIds(),
								","));
					}
					act.setCreateTime(oldAct.getCreateTime());
					act.setCreateUid(oldAct.getCreateUid());
					act.setPopularity(oldAct.getPopularity());
					if (act.getProvince() == null) {
						act.setProvince(0l);
					}
					if (act.getCity() == null) {
						act.setCity(0l);
					}
					actService.updateAct(act, form.getCatIds());
					if (StringUtils.isNotEmpty(oldAct.getLogo())) {
						uploadImageService.deleteImg(act.getId(),
								oldAct.getLogo());
					}
					actId = act.getId();
					logo = act.getLogo();
					mmap.addAttribute("msg", "replace is success");
				}
				if (logo != null && form.getImgFile() != null
						&& form.getImgFile().getSize() > 0 && actId > 0) {
					uploadImageService
							.uploadImg(actId, logo, form.getImgFile());
				}

			} else {
				mmap.addAttribute("msg", "create act name is null");
			}
		} catch (Exception e) {
			mmap.addAttribute("msg", "create act is error.");
			log.error("create act is error.", e);
		}
		return new ModelAndView("redirect:/cms/showCreateAct", mmap);
	}

	@RequestMapping(value = "/selectCity", method = RequestMethod.GET)
	public String selectCity(Model model, String proId) {
		List<City> citys = new ArrayList<City>();
		for (Entry<Long, City> entry : com.juzhai.passport.InitData.CITY_MAP
				.entrySet()) {
			if (proId.equals(String.valueOf(entry.getValue().getProvinceId()))) {
				citys.add(entry.getValue());
			}
		}
		model.addAttribute("citys", citys);
		return "cms/ajax/citys_list";
	}

	@RequestMapping(value = "/updateAct", method = RequestMethod.POST)
	public ModelAndView updateAct(AddActForm form, HttpServletRequest request) {
		Act act = converAct(form, 0L);
		ModelMap mmap = new ModelMap();
		try {
			if (form.getCatIds() != null) {
				actService.updateAct(act, form.getCatIds());
			} else {
				mmap.addAttribute("msg", "create act catIds is null");
				log.error("create act catIds is null");
			}
		} catch (Exception e) {
			mmap.addAttribute("msg", "update act is error.");
			log.error("update act is error.", e);
		}
		return new ModelAndView("redirect:/cms/showActManager", mmap);
	}

	private Act converAct(AddActForm form, Long uid) {
		if (form == null) {
			return null;
		}
		Act act = null;
		if (form.getId() != null) {
			act = actService.getActById(form.getId());
			if (act == null) {
				return null;
			}
			if (form.getImgFile() != null && form.getImgFile().getSize() > 0) {
				UUID uuid = UUID.randomUUID();
				String fileName = uuid.toString() + "."
						+ uploadImageService.getImgType(form.getImgFile());
				uploadImageService.uploadImg(form.getId(), fileName,
						form.getImgFile());
				if (!StringUtils.isEmpty(act.getLogo())) {
					uploadImageService.deleteImg(form.getId(), act.getLogo());
				}
				act.setLogo(fileName);
			}
		} else {
			act = new Act();
			act.setCreateUid(uid);
			if (form.getImgFile() != null && form.getImgFile().getSize() > 0) {
				UUID uuid = UUID.randomUUID();
				if (form.getImgFile() != null) {
					String fileName = uuid.toString() + "."
							+ uploadImageService.getImgType(form.getImgFile());
					act.setLogo(fileName);
				}
			}
		}
		if (!form.getCheckAddress()) {
			act.setAddress(form.getAddress());
			act.setCity(form.getCity());
			act.setProvince(form.getProvince());
		}
		Date startTime = null;
		Date endTime = null;
		try {
			if (!StringUtils.isEmpty(form.getStartTime())) {
				startTime = DateUtils.parseDate(form.getStartTime(),
						new String[] { "yyyy-MM-dd" });
				act.setStartTime(startTime);
			}
			if (!StringUtils.isEmpty(form.getEndTime())) {
				endTime = DateUtils.parseDate(form.getEndTime(),
						new String[] { "yyyy-MM-dd" });
				act.setEndTime(endTime);
			}
		} catch (ParseException e) {
			log.error("parse search date error.", e);
		}
		String intro = form.getIntro();
		if (intro != null)
			act.setIntro(subString(200, form.getIntro()));
		if (form.getMaxCharge() == null) {
			act.setMaxCharge(0);
		} else {
			act.setMaxCharge(form.getMaxCharge());
		}
		if (form.getMinCharge() == null) {
			act.setMinCharge(0);
		} else {
			act.setMinCharge(form.getMinCharge());
		}
		if (form.getMaxRoleNum() == null) {
			act.setMaxRoleNum(0);
		} else {
			act.setMaxRoleNum(form.getMaxRoleNum());
		}
		if (form.getMinRoleNum() == null) {
			act.setMinRoleNum(0);
		} else {
			act.setMinRoleNum(form.getMinRoleNum());
		}
		act.setName(subString(10, form.getName()));
		act.setCategoryIds(StringUtils.join(form.getCatIds(), ","));
		if (!StringUtils.isEmpty(form.getSuiAge())) {
			act.setSuitAge(SuitAge.getSuitAge(form.getSuiAge()));
		}
		if (!StringUtils.isEmpty(form.getSuitGender())) {
			act.setSuitGender(SuitGender.getSuitGender(form.getSuitGender()));
		}
		if (!StringUtils.isEmpty(form.getSuitStatu())) {
			act.setSuitStatus(SuitStatus.getSuitStatus(form.getSuitStatu()));
		}
		act.setFullName(subString(30, form.getFullName()));
		return act;
	}

	private String subString(int len, String str) {
		if (str != null && str.length() > len) {
			return str.substring(0, len);
		} else {
			return str;
		}

	}

}
