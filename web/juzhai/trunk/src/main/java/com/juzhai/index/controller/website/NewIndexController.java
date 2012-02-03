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

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowActOrder;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Controller
public class NewIndexController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IIdeaService ideaService;
	@Value("${web.show.ideas.max.rows}")
	private int webShowIdeasMaxRows;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return showIdeas(request, model);
	}

	@RequestMapping(value = "/showIdeas", method = RequestMethod.GET)
	public String showIdeas(HttpServletRequest request, Model model) {
		return pageShowIdeas(request, model, ShowActOrder.HOT_TIME.getType(),
				0L, 1);
	}

	@RequestMapping(value = "/showIdeas/{orderType}_{cityId}/{page}", method = RequestMethod.GET)
	public String pageShowIdeas(HttpServletRequest request, Model model,
			@PathVariable String orderType, @PathVariable long cityId,
			@PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		ShowIdeaOrder order = ShowIdeaOrder.getShowIdeaOrderByType(orderType);
		PagerManager pager = new PagerManager(page, webShowIdeasMaxRows,
				ideaService.countIdeaByCity(cityId));
		List<Idea> ideaList = ideaService.listIdeaByCity(cityId, order,
				pager.getFirstResult(), pager.getMaxResult());
		List<IdeaView> ideaViewList = new ArrayList<IdeaView>();
		for (Idea idea : ideaList) {
			IdeaView ideaView = new IdeaView();
			ideaView.setIdea(idea);
			if (context.hasLogin()) {
				ideaView.setHasUsed(ideaService.isUseIdea(context.getUid(),
						idea.getId()));
			}
			ideaViewList.add(ideaView);
		}
		model.addAttribute("pager", pager);
		model.addAttribute("orderType", orderType);
		model.addAttribute("cityId", cityId);
		model.addAttribute("ideaViewList", ideaViewList);
		return "web/index/cqw/show_ideas";
	}

	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String aboutUs(HttpServletRequest request, Model model) {
		return "web/index/about_us";
	}
}
