package com.juzhai.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.InitData;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.cms.service.IUserActionService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.search.service.IActSearchService;

@Controller
public class SearchController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IUserActionService userActionService;
	@Autowired
	private IActService actService;
	@Autowired
	private IActSearchService actSearchService;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/searchAct", method = RequestMethod.GET)
	public String searchAct(Model model, String name, HttpServletRequest request)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);

		Long actId = InitData.SYNONYM_ACT.get(name);
		if (actId == null) {
			Act act = actService.getActByName(name);
			if (act != null) {
				actId = act.getId();
			}
		}
		if (actId != null) {
			return "redirect:/app/showAct/" + actId;
		} else {
			long uid = context.getUid();
			ProfileCache cache = profileService.getProfileCacheByUid(uid);
			if (cache != null) {
				if (userActionService.searchActActionMaximum(uid)) {
					userActionService.createSearchActAction(name, uid,
							cache.getNickname());
				} else {
					log.error("uid:" + uid + " Exceeds the limit");
				}
			} else {
				log.error("searchAct uid is not exist");
			}
			model.addAttribute("name", name);
			return "app/search/search";
		}
	}

	@RequestMapping(value = "/searchAutoMatch", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> autoSearch(String q, int limit, Model model) {
		if (log.isDebugEnabled()) {
			log.debug("search q:" + q + ", limit:" + limit + ".");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<String> queryResults = actSearchService.indexSearchName(q,
					limit);
			for (String name : queryResults) {
				Map<String, Object> nameMap = new HashMap<String, Object>();
				nameMap.put("name", name);
				result.add(nameMap);
			}
		}
		return result;
	}
}
