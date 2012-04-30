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
			i += 500;
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
			i += 10;
			if (list.size() < num) {
				break;
			}
		}
		return "success";
	}
}
