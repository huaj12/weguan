package com.juzhai.cms.controller.useless;

import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequestMapping("/cms")
@Deprecated
public class UserActionController {
	// private final Log log = LogFactory.getLog(getClass());
	// @Autowired
	// private IActService actService;
	// @Autowired
	// private UserActionService userActionService;
	// @Autowired
	// private SearchActActionMapper searchActActionMapper;
	// @Autowired
	// private AddActActionMapper addActActionMapper;
	// int cmsPage=20;
	//
	// @RequestMapping(value = "/showSearchActAction", method =
	// RequestMethod.GET)
	// public String showSearchActAction(HttpServletRequest request, Model
	// model,
	// Integer pageId) {
	// if (pageId == null) {
	// pageId = 1;
	// }
	// PagerManager pager = new PagerManager(pageId, cmsPage,
	// userActionService.getSearchActActionCount());
	// List<SearchActAction> list = userActionService.getSearchActAction(
	// pager.getFirstResult(), pager.getMaxResult());
	// model.addAttribute("lists", list);
	// model.addAttribute("pager", pager);
	// return "cms/searchActActionList";
	// }
	//
	// @RequestMapping(value = "/showAddActAction", method = RequestMethod.GET)
	// public String showAddActAction(HttpServletRequest request, Model model,
	// Integer pageId) {
	// if (pageId == null) {
	// pageId = 1;
	// }
	// PagerManager pager = new PagerManager(pageId, cmsPage,
	// userActionService.getAddActActionCount());
	// List<AddActAction> list = userActionService.getAddActAction(
	// pager.getFirstResult(), pager.getMaxResult());
	// model.addAttribute("lists", list);
	// model.addAttribute("pager", pager);
	// return "cms/addActActionList";
	// }
	//
	//
	//
	// @RequestMapping(value = "/deleteAddActAction", method =
	// RequestMethod.GET)
	// public String deleteAddActAction(Long id) {
	// if (id != null) {
	// addActActionMapper.deleteByPrimaryKey(id);
	// }
	// return null;
	// }
	//
	// @RequestMapping(value = "/deleteSearchActAction", method =
	// RequestMethod.GET)
	// public String deleteSearchActAction(Long id) {
	// if (id != null) {
	// searchActActionMapper.deleteByPrimaryKey(id);
	// }
	// return null;
	// }
}
