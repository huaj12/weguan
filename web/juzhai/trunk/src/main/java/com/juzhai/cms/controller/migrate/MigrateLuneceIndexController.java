package com.juzhai.cms.controller.migrate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.dao.Limit;
import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;

@Controller
@RequestMapping("/cms")
public class MigrateLuneceIndexController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private Indexer<Profile> profileIndexer;
	@Autowired
	private Indexer<Post> postIndexer;
	private int num = 200;

	@RequestMapping(value = "/create/index/profile", method = RequestMethod.GET)
	@ResponseBody
	public String createProfileIndex() {
		int i = 0;
		ProfileExample example = new ProfileExample();
		example.createCriteria().andLogoPicNotEqualTo(StringUtils.EMPTY);
		example.setOrderByClause("uid desc");
		while (true) {
			example.setLimit(new Limit(i, num));
			List<Profile> list = profileMapper.selectByExample(example);
			for (Profile profile : list) {
				if (userGuideService.isCompleteGuide(profile.getUid())) {
					try {
						profileIndexer.addIndex(profile, true);
					} catch (Exception e) {
						log.error(
								"create profile index failed.[uid:"
										+ profile.getUid() + "]", e);
					}
				}
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
		PostExample example = new PostExample();
		example.createCriteria()
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
				.andDefunctEqualTo(false);
		example.setOrderByClause("id desc");
		example.setLimit(new Limit(i, num));
		while (true) {
			List<Post> list = postMapper.selectByExample(example);
			for (Post post : list) {
				try {
					postIndexer.addIndex(post, true);
				} catch (Exception e) {
					log.error(
							"create post index failed.[postId:" + post.getId()
									+ "]", e);
				}
			}
			i += num;
			if (list.size() < num) {
				break;
			}
		}
		return "success";
	}
}
