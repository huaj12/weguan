package com.juzhai.passport.controller;

import com.juzhai.core.controller.BaseController;

//@Controller
//@RequestMapping(value = "app")
public class UserGuideController extends BaseController {

	// private final Log log = LogFactory.getLog(getClass());
	//
	// @Autowired
	// private IUserGuideService userGuideService;
	// @Autowired
	// private IActCategoryService actCategoryService;
	// @Autowired
	// private IUserActService userActService;
	// @Autowired
	// private MessageSource messageSource;
	// @Value("${guid.act.size}")
	// private int guidActSize = 12;
	//
	// @RequestMapping(value = "/guide", method = RequestMethod.GET)
	// public String guide(HttpServletRequest request, Model model)
	// throws NeedLoginException {
	// UserContext context = checkLoginForApp(request);
	// // queryPoint(context.getUid(), model);
	// UserGuide userGuide = userGuideService.getUserGuide(context.getUid());
	// if (null == userGuide || userGuide.getComplete()) {
	// return error_500;
	// }
	// int currentStep = userGuide.getGuideStep() + 1;
	//
	// if (currentStep > InitData.GUIDE_STEPS.size()) {
	// return error_500;
	// } else {
	// model.addAttribute("step", currentStep);
	// model.addAttribute("totalStep", InitData.GUIDE_STEPS.size());
	// // if (currentStep == InitData.GUIDE_STEPS.size() + 1) {
	// // // 订阅邮箱步骤
	// // queryProfile(context.getUid(), model);
	// // model.addAttribute("userActCount",
	// // userActService.countUserActByUid(context.getUid()));
	// // return "guide/app/guide_email";
	// // } else {
	// Long actCategoryId = InitData.GUIDE_STEPS.get(currentStep - 1);
	// List<Act> actList = actCategoryService.getHotActList(
	// context.getTpId(), actCategoryId);
	// model.addAttribute("actCategory",
	// com.juzhai.act.InitData.ACT_CATEGORY_MAP.get(actCategoryId));
	// model.addAttribute("actList",
	// actList.subList(0, Math.min(actList.size(), guidActSize)));
	// queryProfile(context.getUid(), model);
	// return "guide/app/guide";
	// // }
	// }
	// }
	//
	// @RequestMapping(value = "/guide/next", method = RequestMethod.POST)
	// public String next(HttpServletRequest request, long[] actId,
	// String[] actName, String email, Model model)
	// throws NeedLoginException {
	// UserContext context = checkLoginForApp(request);
	// UserGuide userGuide = userGuideService.getUserGuide(context.getUid());
	// if (null == userGuide || userGuide.getComplete()) {
	// return error_500;
	// }
	// int currentStep = userGuide.getGuideStep() + 1;
	// if (currentStep > InitData.GUIDE_STEPS.size()) {
	// return error_500;
	// } else if (currentStep == InitData.GUIDE_STEPS.size()) {
	// // complete
	// try {
	// doGuideComplete(context, actId, actName);
	// } catch (ProfileInputException e) {
	// model.addAttribute("errorCode", e.getErrorCode());
	// model.addAttribute(
	// "error",
	// (messageSource.getMessage(e.getErrorCode(), null,
	// e.getErrorCode(), Locale.SIMPLIFIED_CHINESE)));
	// return guide(request, model);
	// }
	// Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
	// return "redirect:"
	// + SystemConfig.getDomain(tp == null ? null : tp.getName())
	// + "/app/index?isFirst=true";
	// } else {
	// doGuideNext(context, actId, actName);
	// return guide(request, model);
	// }
	// }
	//
	// private void doGuideNext(UserContext context, long[] actIds,
	// String[] actNames) {
	// guideAddActs(context, actIds, actNames);
	// userGuideService.nextGuide(context.getUid());
	// }
	//
	// private void guideAddActs(UserContext context, long[] actIds,
	// String[] actNames) {
	// if (null != actIds) {
	// for (long actId : actIds) {
	// try {
	// userActService.addAct(context.getUid(), actId, false);
	// } catch (ActInputException e) {
	// log.error("Add act by id failed in giude. [actId:" + actId
	// + ", message:" + e.getMessage() + "]");
	// }
	// }
	// }
	// if (null != actNames) {
	// for (String actName : actNames) {
	// try {
	// userActService.addAct(context.getUid(), actName, false);
	// } catch (ActInputException e) {
	// log.error("Add act by name failed in giude. [actName:"
	// + actName + ", message:" + e.getMessage() + "]");
	// }
	// }
	// }
	// }
	//
	// private void doGuideComplete(UserContext context, long[] actIds,
	// String[] actNames) throws ProfileInputException {
	// guideAddActs(context, actIds, actNames);
	// userGuideService.completeGuide(context.getUid());
	// }
	//
	// private void doGuideComplete(UserContext context, String email)
	// throws ProfileInputException {
	// if (StringUtils.isNotEmpty(email)) {
	// emailService.subEmail(context.getUid(), email);
	// }
	// userGuideService.completeGuide(context.getUid());
	// }
}
