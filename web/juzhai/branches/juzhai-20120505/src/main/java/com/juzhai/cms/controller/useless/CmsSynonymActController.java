package com.juzhai.cms.controller.useless;

import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequestMapping("/cms")
@Deprecated
public class CmsSynonymActController {
	// private final Log log = LogFactory.getLog(getClass());
	// @Autowired
	// private IActService actService;
	// @Autowired
	// private ISynonymActService synonymActService;
	// @Autowired
	// private SynonymActMapper synonymActMapper;
	// @Autowired
	// private IActSearchService actSearchService;
	// @Autowired
	// private MessageSource messageSource;
	// int cmsPage = 20;
	//
	// @RequestMapping(value = "/cmsSynonymAct")
	// @ResponseBody
	// public AjaxResult cmsSynonymAct(HttpServletRequest request, String name,
	// String actName, Model model) {
	// AjaxResult result = new AjaxResult();
	// try {
	// Act act = actService.getActByName(actName);
	// if (act == null) {
	// result.setSuccess(false);
	// result.setErrorCode("-1");
	// } else {
	// if (synonymActService.synonymAct(name, actName)) {
	// result.setSuccess(true);
	// } else {
	// result.setSuccess(false);
	// }
	// }
	// } catch (Exception e) {
	// result.setSuccess(false);
	// log.error("cmsSynonymAct is error", e);
	// }
	// return result;
	// }
	//
	// @RequestMapping(value = "/showSynonymActs", method = RequestMethod.GET)
	// public String showSynonymActs(Model model, Integer pageId) {
	// if (pageId == null) {
	// pageId = 1;
	// }
	// PagerManager pager = new PagerManager(pageId, cmsPage,
	// synonymActService.countSysonymActs());
	// List<SynonymAct> list = synonymActService.getSysonymActs(
	// pager.getFirstResult(), pager.getMaxResult());
	// List<CmsSynonymActView> synonymActViews = new
	// ArrayList<CmsSynonymActView>();
	// for (SynonymAct syn : list) {
	// Act act = actService.getActById(syn.getActId());
	// if (act == null) {
	// // System.out.println(syn.getActId()+" is delete");
	// continue;
	// }
	// synonymActViews.add(new CmsSynonymActView(act.getName(), syn));
	// }
	// model.addAttribute("synonymActViews", synonymActViews);
	// model.addAttribute("pager", pager);
	// return "cms/synonymActsList";
	// }
	//
	// @RequestMapping(value = "/deleteSynonymAct", method = RequestMethod.GET)
	// @ResponseBody
	// public AjaxResult deleteSynonymAct(Long id) {
	// AjaxResult result = new AjaxResult();
	// try {
	// if (id != null) {
	// synonymActMapper.deleteByPrimaryKey(id);
	// }
	// } catch (Exception e) {
	// result.setSuccess(false);
	// }
	// result.setSuccess(true);
	// return result;
	// }
	//
	// @RequestMapping(value = "/replaceSynonymAct", method = RequestMethod.GET)
	// @ResponseBody
	// public AjaxResult replaceSynonymAct(Long id, String actName) {
	// AjaxResult result = new AjaxResult();
	// Act act = actService.getActByName(actName);
	// if (act == null) {
	// result.setSuccess(false);
	// result.setErrorCode("-1");
	// } else {
	// if (synonymActService.updateSynonymAct(id, act.getId())) {
	// result.setSuccess(true);
	// } else {
	// result.setSuccess(false);
	// }
	// }
	// return result;
	// }
	//
	// /*--------------------------------近义词屏蔽词------------------------------*/
	// @RequestMapping(value = "/searchActs")
	// public String searchActs(HttpServletRequest request, Model model,
	// SearchActForm form) {
	// if (null != form && StringUtils.isNotEmpty(form.getStartDate())
	// && StringUtils.isNotEmpty(form.getEndDate())) {
	// try {
	// Date startDate = DateUtils.parseDate(form.getStartDate(),
	// DateFormat.DATE_PATTERN);
	// Date endDate = DateUtils.parseDate(form.getEndDate(),
	// DateFormat.DATE_PATTERN);
	//
	// PagerManager pager = new PagerManager(form.getPageId(), 10,
	// actService.countNewActs(startDate, endDate));
	// List<Act> actList = actService.searchNewActs(startDate,
	// endDate, form.getOrder(), pager.getFirstResult(),
	// pager.getMaxResult());
	// List<CmsActView> viewList = new ArrayList<CmsActView>(
	// actList.size());
	// for (Act act : actList) {
	// viewList.add(new CmsActView(act, actService
	// .listSynonymActs(act.getId()), actService
	// .isShieldAct(act.getId())));
	// }
	// model.addAttribute("cmsActViewList", viewList);
	// model.addAttribute("pager", pager);
	// } catch (ParseException e) {
	// log.error("parse search date error.", e);
	// }
	// model.addAttribute("searchActForm", form);
	// }
	// return "cms/act_list";
	// }
	//
	// @RequestMapping(value = "/showSynonym", method = RequestMethod.GET)
	// public String showSynonym(HttpServletRequest request, Model model,
	// long actId) {
	// Act act = actService.getActById(actId);
	// if (null != act) {
	// List<Act> synonymActList = actService.listSynonymActs(actId);
	// List<String> maybeSynonymList = actSearchService.indexSearchName(
	// act.getName(), 10);
	// maybeSynonymList.remove(act.getName());
	// model.addAttribute("maybeSynonymList", maybeSynonymList);
	// model.addAttribute("synonymActList", synonymActList);
	// model.addAttribute("act", act);
	// }
	// return "cms/synonym_list";
	// }
	//
	// @RequestMapping(value = "/addSynonym", method = RequestMethod.POST)
	// @ResponseBody
	// public AjaxResult addSynonym(HttpServletRequest request, Model model,
	// long actId, String synonymActName) {
	// AjaxResult ajaxResult = new AjaxResult();
	// if (actService.actExist(actId)) {
	// try {
	// actService.addSynonym(actId, synonymActName);
	// ajaxResult.setSuccess(true);
	// } catch (ActInputException e) {
	// ajaxResult.setSuccess(false);
	// ajaxResult.setErrorCode(e.getErrorCode());
	// ajaxResult.setErrorInfo(messageSource.getMessage(
	// e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
	// }
	// } else {
	// ajaxResult.setErrorInfo("The act is null");
	// }
	// return ajaxResult;
	// }
	//
	// @RequestMapping(value = "/removeSynonym", method = RequestMethod.POST)
	// @ResponseBody
	// public AjaxResult removeSynonym(HttpServletRequest request, Model model,
	// long actId, long removeActId) {
	// AjaxResult ajaxResult = new AjaxResult();
	// if (actService.actExist(actId)) {
	// actService.removeSynonym(actId, removeActId);
	// ajaxResult.setSuccess(true);
	// }
	// return ajaxResult;
	// }
	//
	// @RequestMapping(value = "/addShield", method = RequestMethod.POST)
	// @ResponseBody
	// public AjaxResult addShield(HttpServletRequest request, Model model,
	// long actId) {
	// AjaxResult ajaxResult = new AjaxResult();
	// if (actService.actExist(actId)) {
	// actService.addActShield(actId);
	// ajaxResult.setSuccess(true);
	// }
	// return ajaxResult;
	// }
	//
	// @RequestMapping(value = "/removeShield", method = RequestMethod.POST)
	// @ResponseBody
	// public AjaxResult removeSynonym(HttpServletRequest request, Model model,
	// long actId) {
	// AjaxResult ajaxResult = new AjaxResult();
	// if (actService.actExist(actId)) {
	// actService.removeActShield(actId);
	// ajaxResult.setSuccess(true);
	// }
	// return ajaxResult;
	// }
	//
	// @RequestMapping(value = "/showShield", method = RequestMethod.GET)
	// public String showShield(HttpServletRequest request, Model model) {
	// List<Act> shieldActList = actService.listShieldActs();
	// model.addAttribute("shieldActList", shieldActList);
	// return "cms/shield_list";
	// }
	//
	// /*--------------------------------近义词屏蔽词------------------------------*/
}
