package com.juzhai.mobile.home.v12.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.common.web.response.ListJsonResult;
import com.juzhai.mobile.post.v12.controller.view.PostMView;
import com.juzhai.mobile.post.v12.controller.viewHelper.IPostMViewHelper;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostRemoteService;

@Controller("v12HomeController")
@RequestMapping(value = "home")
public class HomeMController extends BaseController {

	@Autowired
	private IPostRemoteService postService;
	@Autowired
	private IPostMViewHelper postMViewHelper;
	@Autowired
	@Value("${mobile.my.post.max.rows}")
	private int mobileMyPostMaxRows = 2;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult home(HttpServletRequest request, Long uid, int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		if (uid == null || uid <= 0) {
			uid = context.getUid();
		}

		PagerManager pager = new PagerManager(page, mobileMyPostMaxRows,
				postService.countUserPost(uid));
		List<Post> postList = postService.listUserPost(uid, null,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostMView> postViewList = new ArrayList<PostMView>(postList.size());
		for (Post post : postList) {
			postViewList.add(postMViewHelper.createPostMView(context, post));
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, postViewList);

		return result;
	}
}
