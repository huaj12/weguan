package com.juzhai.index.controller.website;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.controller.view.CategoryActView;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;
import com.juzhai.act.service.IShowActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowActOrder;

@Controller
public class IndexController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IShowActService showActService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IUserActService userActService;
	@Value("${web.show.category.size}")
	private int webShowCategorySize;
	@Value("${web.show.acts.max.rows}")
	private int webShowActsMaxRows;

	@RequestMapping(value = "/showActs", method = RequestMethod.GET)
	public String showActs(HttpServletRequest request, Model model) {
		return pageShowActs(request, model, ShowActOrder.HOT_TIME.getType(),
				0L, 1);
	}

	@RequestMapping(value = "/showActs/{orderType}/{categoryId}/{page}", method = RequestMethod.GET)
	public String pageShowActs(HttpServletRequest request, Model model,
			@PathVariable String orderType, @PathVariable long categoryId,
			@PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		List<Category> categoryList = categoryService
				.listCategories(webShowCategorySize);
		ShowActOrder order = ShowActOrder.getShowActOrderByType(orderType);
		long cityId = fetchCityId(request);
		PagerManager pager = new PagerManager(page, webShowActsMaxRows,
				showActService.countShowActs(cityId, categoryId));
		List<Act> actList = showActService.listShowActs(cityId, categoryId,
				order, pager.getFirstResult(), pager.getMaxResult());
		List<CategoryActView> categoryActViewList = new ArrayList<CategoryActView>();
		for (Act act : actList) {
			CategoryActView view = new CategoryActView();
			view.setAct(act);
			if (context.hasLogin()) {
				view.setHasUsed(userActService.hasAct(context.getUid(),
						act.getId()));
			}
			categoryActViewList.add(view);
		}
		model.addAttribute("categoryActViewList", categoryActViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("orderType", orderType);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("pageType", "cqw");
		return "web/index/cqw/show_acts";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return "web/index/index";
	}
}
