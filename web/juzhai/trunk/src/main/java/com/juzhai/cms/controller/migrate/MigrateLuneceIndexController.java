package com.juzhai.cms.controller.migrate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.search.service.IProfileSearchService;

@Controller
@RequestMapping("/cms")
public class MigrateLuneceIndexController {
	@Autowired
	private IProfileSearchService profileSearchService;
	@Autowired
	private IPostSearchService postSearchService;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private PostMapper postMapper;
	private int num = 10;

	// TODO (review)
	// 批量处理写了很多次，为什么还是会出现这么严重的问题？问题不止一个！不解释。自己看下面两个批量处理代码逻辑吧。非常严重的问题！！！！
	// TODO (review) 下面批量处理的代码，有性能优化的提升，自己找一下
	// TODO (review) 这里后台建索引，为什么要走rabbitmq呢？仔细想想

	@RequestMapping(value = "/create/index/profile", method = RequestMethod.GET)
	@ResponseBody
	public String createProfileIndex() {
		int i = 0;
		while (true) {
			ProfileExample example = new ProfileExample();
			example.setLimit(new Limit(i, num));
			List<Profile> list = profileMapper.selectByExample(example);
			for (Profile profile : list) {
				profileSearchService.createIndex(profile.getUid());
			}
			i += num;
			if (list.size() < num) {
				break;
			}
		}
		return "success";
	}

	@RequestMapping(value = "/create/index/post", method = RequestMethod.GET)
	@ResponseBody
	public String createPostIndex() {
		int i = 0;
		while (true) {
			PostExample example = new PostExample();
			example.createCriteria().andVerifyTypeEqualTo(1)
					.andDefunctEqualTo(false);
			example.setLimit(new Limit(i, num));
			List<Post> list = postMapper.selectByExample(example);
			for (Post post : list) {
				postSearchService.createIndex(post.getId());
			}
			i += num;
			if (list.size() < num) {
				break;
			}
		}
		return "success";
	}
}
