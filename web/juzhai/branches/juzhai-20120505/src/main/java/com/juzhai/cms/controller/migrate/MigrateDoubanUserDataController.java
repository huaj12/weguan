package com.juzhai.cms.controller.migrate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gdata.client.douban.DoubanService;
import com.juzhai.cms.task.UpdateDoubanUserTask;
import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;

@Controller
@RequestMapping("/cms")
public class MigrateDoubanUserDataController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private TpUserMapper tpUserMapper;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@ResponseBody
	@RequestMapping(value = "migDoubanUser")
	public String migDoubanUser(HttpServletRequest request) {
		log.error("start...");
		DoubanService doubanService = new DoubanService(null, null);
		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo("douban");
		example.setOrderByClause("uid desc");
		int i = 0;
		int maxResults = 1000;
		Pattern pattern = Pattern.compile("\\d*");
		while (true) {
			example.setLimit(new Limit(i, maxResults));
			List<TpUser> list = tpUserMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {
				break;
			}
			for (TpUser tpUser : list) {
				Matcher m = pattern.matcher(tpUser.getTpIdentity());
				if (!m.matches()) {
					if (!tpUser.getTpIdentity().contains("@")) {
						try {
							taskExecutor.execute(new UpdateDoubanUserTask(
									tpUser, doubanService, tpUserMapper));
						} catch (Exception e) {
							log.error("User[" + tpUser.getUid() + "] "
									+ e.getMessage());
						}
					}
				}
			}
			i += maxResults;
		}
		log.error("end...");
		return "success";
	}
}
