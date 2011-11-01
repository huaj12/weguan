package com.juzhai.cms.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.juzhai.act.InitData;
import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.IActService;
import com.juzhai.cms.bean.SizeType;
import com.juzhai.cms.controller.form.AddActForm;
import com.juzhai.cms.controller.form.SearchActForm;
import com.juzhai.cms.controller.view.CmsActMagerView;
import com.juzhai.cms.controller.view.CmsActView;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.core.web.AjaxResult;
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
	private MessageSource messageSource;

	@RequestMapping(value = "/searchActs")
	public String searchActs(HttpServletRequest request, Model model,
			SearchActForm form) {
		if (null != form && StringUtils.isNotEmpty(form.getStartDate())
				&& StringUtils.isNotEmpty(form.getEndDate())) {
			try {
				Date startDate = DateUtils.parseDate(form.getStartDate(),
						new String[] { "yyyy.MM.dd" });
				Date endDate = DateUtils.parseDate(form.getEndDate(),
						new String[] { "yyyy.MM.dd" });

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
			String catId, String name, Integer pageId) {
		List<Act> acts = null;
		if (pageId == null)
			pageId = 1;
		Date startDate = null;
		Date endDate = null;
		try {
			if (!StringUtils.isEmpty(bDate)) {
				startDate = DateUtils.parseDate(bDate,
						new String[] { "yyyy.MM.dd" });
			}
			if (!StringUtils.isEmpty(eDate)) {
				endDate = DateUtils.parseDate(eDate,
						new String[] { "yyyy.MM.dd" });
			}
		} catch (ParseException e) {
			log.error("parse search date error.", e);
		}
		PagerManager pager = new PagerManager(pageId, 10,
				actService.searchActsCount(startDate, endDate, name, catId));
		acts = actService.searchActs(startDate, endDate, name, catId,
				pager.getFirstResult(), pager.getMaxResult());

		List<CmsActMagerView> viewList = new ArrayList<CmsActMagerView>(
				acts.size());
		for (Act act : acts) {
			String cword ="年龄: "+ SuitAge.getByIndex(act.getSuitAge()).getType()
					+"  群体:  "+ SuitStatus.getByIndex(act.getSuitStatus()).getType()
					+"  性别 :  "+ SuitGender.getByIndex(act.getSuitGender()).getType();
			City city = com.juzhai.passport.InitData.CITY_MAP
					.get(act.getCity());
			Province pro = com.juzhai.passport.InitData.PROVINCE_MAP.get(act
					.getProvince());
			String proName="";
			String cityName="";
			if(pro!=null){
				proName="省份:"+pro.getName()+" ";
			}
			if(city!=null){
				cityName="城市:"+city.getName()+" ";
			}
			String addr="";
			if(act.getAddress()!=null){
				addr="详细地址:"+act.getAddress();
			}
			String address = proName + cityName + addr;
			String logoWebPath ="";
			if(act.getLogo()!=null){
				logoWebPath=ImageUtil.generateFullImageWebPath(
					StaticUtil.u("/images/"), act.getId(), act.getLogo(),
					SizeType.BIG);
			}
			StringBuffer categorys = new StringBuffer();
			String cats = act.getCategoryIds();
			if (cats != null) {
				for (String cat : cats.split(",")) {
					Category c = InitData.CATEGORY_MAP.get(Long.valueOf(cat));
					categorys.append(c.getName() + " ");
				}
			}
			viewList.add(new CmsActMagerView(act, logoWebPath, address, cword,
					categorys.toString()));
		}
		model.addAttribute("cmsActMagerViews", viewList);
		model.addAttribute("pager", pager);
		model.addAttribute("acts", acts);
		model.addAttribute("eDate", eDate);
		model.addAttribute("bDate", bDate);
		model.addAttribute("catId", catId);
		model.addAttribute("name", name);
		return "cms/ajax/actManager_list";
	}

	@RequestMapping(value = "/showCreateAct", method = RequestMethod.GET)
	public String showCreateAct(Model model) {
		assembleCiteys(model);
		return "cms/createAct";
	}

	@RequestMapping(value = "/showUpdate", method = RequestMethod.GET)
	public String showUpdate(Model model, Long actId) {
		if (actId == null)
			actId = 0l;
		Act act = actService.getActById(actId);
		assembleCiteys(model);
		model.addAttribute("act", act);
		model.addAttribute("logoWebPath", ImageUtil.generateFullImageWebPath(
					StaticUtil.u("/images/"), act.getId(), act.getLogo(),
					SizeType.BIG));
		model.addAttribute("age", SuitAge.getByIndex(act.getSuitAge()));
		model.addAttribute("gender",SuitGender.getByIndex(act.getSuitGender()));
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
		List<Category> categoryList = new ArrayList<Category>();
		Set<Long> keys = InitData.CATEGORY_MAP.keySet();
		for (Long key : keys) {
			categoryList.add(InitData.CATEGORY_MAP.get(key));
		}

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("citys", citys);
		model.addAttribute("suitAges", SuitAge.values());
		model.addAttribute("suitGenders", SuitGender.values());
		model.addAttribute("suitStatus", SuitStatus.values());
		model.addAttribute("provinces", provinces);
	}

	@RequestMapping(value = "/createAct", method = RequestMethod.POST)
	public String createAct(AddActForm form, HttpServletRequest request) {
		UserContext context = (UserContext) request.getAttribute("context");
		Act act = ConverAct(form, context.getUid());
		try {
			if (act != null && act.getName() != null) {
				if (actService.getActByName(act.getName()) == null) {
					Act a = actService.createAct(act, form.getCatids());
					if (a.getLogo() != null && form.getImgFile() != null) {
						uploadImg(a.getId(), a.getLogo(), form.getImgFile()
								.getInputStream());
					}
				} else {
					log.error("create act name is exist");
				}
			} else {
				log.error("create act name is null");
			}
		} catch (Exception e) {
			log.error("create act is error.", e);
		}
		return null;
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
	public String updateAct(AddActForm form, HttpServletRequest request) {
		Act act = ConverAct(form, 0l);
		try {
			actService.updateAct(act);
		} catch (Exception e) {
			log.error("update act is error.", e);
		}
		return null;
	}

	private Act ConverAct(AddActForm form, Long uid) {
		if (form == null) {
			return null;
		}
		Act act = null;
		if (form.getId() != null) {
			try {
				act = actService.getActById(form.getId());
				if (form.getImgFile() != null&&form.getImgFile().getSize()>0) {
					UUID uuid = UUID.randomUUID();
					String fileName = uuid.toString()
							+ "."
							+ form.getImgFile().getContentType()
									.replaceAll("image/", "");
					String directoryPath = SystemConfig.IMAGE_HOME
							+ ImageUtil.generateHierarchyImagePath(
									form.getId(), SizeType.BIG);
					new File(directoryPath + act.getLogo()).delete();
					FileUtil.writeStreamToFile(directoryPath, fileName, form
							.getImgFile().getInputStream());
					for (SizeType sizeType : SizeType.values()) {
						if (sizeType.getType() > 0) {
							String distDirectoryPath = SystemConfig.IMAGE_HOME
									+ ImageUtil.generateHierarchyImagePath(
											form.getId(), sizeType);
							new File(distDirectoryPath + act.getLogo())
									.delete();
							ImageUtil.reduceImage(directoryPath + fileName,
									distDirectoryPath, fileName,
									sizeType.getType(), sizeType.getType());
						}
					}
					act.setLogo(fileName);
				}
			} catch (Exception e) {
				log.error("upload image file is error.", e);
			}
		} else {
			act = new Act();
			act.setCreateUid(uid);
			if (form.getImgFile() != null) {
				UUID uuid = UUID.randomUUID();
				if (form.getImgFile() != null) {
					String fileName = uuid.toString()
							+ "."
							+ form.getImgFile().getContentType()
									.replaceAll("image/", "");
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
						new String[] { "yyyy.MM.dd" });
				act.setStartTime(startTime);
			}
			if (!StringUtils.isEmpty(form.getEndTime())) {
				endTime = DateUtils.parseDate(form.getEndTime(),
						new String[] { "yyyy.MM.dd" });
				act.setEndTime(endTime);
			}
		} catch (ParseException e) {
			log.error("parse search date error.", e);
		}
		act.setIntro(form.getIntro());
		act.setMaxCharge(form.getMaxCharge());
		act.setMinCharge(form.getMinCharge());
		act.setMaxRoleNum(form.getMaxRoleNum());
		act.setMinRoleNum(form.getMinRoleNum());
		act.setName(form.getName());
		act.setCategoryIds(String.valueOf(form.getCatId()));
		if (!StringUtils.isEmpty(form.getSuiAge())) {
			act.setSuitAge(SuitAge.getSuitAge(form.getSuiAge()));
		}
		if (!StringUtils.isEmpty(form.getSuitGender())) {
			act.setSuitGender(SuitGender.getSuitGender(form.getSuitGender()));
		}
		if (!StringUtils.isEmpty(form.getSuitStatu())) {
			act.setSuitStatus(SuitStatus.getSuitStatus(form.getSuitStatu()));
		}
		act.setFullName(form.getFullName());
		return act;
	}

	private void uploadImg(long catId, String fileName, InputStream inputStream) {
		String directoryPath = SystemConfig.IMAGE_HOME
				+ ImageUtil.generateHierarchyImagePath(catId, SizeType.BIG);
		FileUtil.writeStreamToFile(directoryPath, fileName, inputStream);
		for (SizeType sizeType : SizeType.values()) {
			if (sizeType.getType() > 0) {
				String distDirectoryPath = SystemConfig.IMAGE_HOME
						+ ImageUtil.generateHierarchyImagePath(catId, sizeType);
				ImageUtil.reduceImage(directoryPath + fileName,
						distDirectoryPath, fileName, sizeType.getType(),
						sizeType.getType());
			}
		}
	}

}
