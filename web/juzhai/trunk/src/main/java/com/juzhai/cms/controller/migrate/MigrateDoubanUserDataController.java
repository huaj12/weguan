package com.juzhai.cms.controller.migrate;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gdata.client.douban.DoubanService;
import com.juzhai.cms.task.UpdateDoubanUserTask;
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
		AtomicInteger index = new AtomicInteger();
		DoubanService doubanService = new DoubanService(null, null);
		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo("douban");
		List<TpUser> list = tpUserMapper.selectByExample(example);
		Pattern pattern = Pattern.compile("\\d*");
		Future<Boolean> future = null;
		for (TpUser tpUser : list) {
			Matcher m = pattern.matcher(tpUser.getTpIdentity());
			boolean b = m.matches();
			if (!b) {
				future = taskExecutor.submit(new UpdateDoubanUserTask(tpUser,
						doubanService, tpUserMapper));
				try {
					if (future.get() != null && !future.get().booleanValue()) {
						index.getAndIncrement();
					}
				} catch (Exception e) {
					log.error("migDoubanUser get future is error."
							+ e.getMessage());
				}
			}
		}
		if (index.get() > 0) {
			return "error:" + index.get();
		} else {
			return "success";
		}
	}
}
