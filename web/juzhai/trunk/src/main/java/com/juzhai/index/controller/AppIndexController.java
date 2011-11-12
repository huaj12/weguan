package com.juzhai.index.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.index.controller.view.ActLiveView;
import com.juzhai.index.service.IActLiveService;

@Controller
@RequestMapping(value = "app")
public class AppIndexController extends BaseController {

	@Autowired
	private IActLiveService actLiveService;
	@Value("${live.act.max.results}")
	private int liveActMaxResults;

	@RequestMapping(value = "/showLive", method = RequestMethod.GET)
	public String showLive(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForApp(request);
		pageLive(request, model, 1);
		return "index/app/live";
	}

	@RequestMapping(value = "/ajax/pageLive", method = RequestMethod.GET)
	public String pageLive(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int page)
			throws NeedLoginException {
		checkLoginForApp(request);
		PagerManager pager = new PagerManager(page, liveActMaxResults,
				actLiveService.countActivists());
		List<ActLiveView> actLiveViewList = actLiveService.listActivists(
				pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("pager", pager);
		model.addAttribute("actLiveViewList", actLiveViewList);
		return "index/app/live_list";
	}
}
