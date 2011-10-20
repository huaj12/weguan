package com.juzhai.cms.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.home.bean.Feed;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;

@Controller
@RequestMapping("/cms")
public class PopularizeController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IInboxService inboxService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@RequestMapping(value = "/autoKxAnswer", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model, int start,
			int end) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				log.error("start auto answer...");
				String[] kxUids = parseUids();
				int index = 0;
				for (String kxUid : kxUids) {
					index++;
					if (StringUtils.isEmpty(kxUid)) {
						continue;
					}
					TpUser tpUser = tpUserService.getTpUserByTpIdAndIdentity(
							1L, kxUid.trim());
					if (tpUser != null) {
						for (int i = 0; i < 20; i++) {
							Feed feed = inboxService.showQuestion(tpUser
									.getUid());
							if (feed == null || feed.getQuestion() == null) {
								break;
							}
							int answer = 1;
							if (feed.getQuestion().getType() == 0) {
								answer = 5;
							}
							inboxService.answer(tpUser.getUid(), 1L, feed
									.getQuestion().getId(), feed.getTpFriend()
									.getUserId(), answer);
						}
						log.error("user[" + tpUser.getUid() + "] the " + index
								+ " end");
						try {
							Thread.sleep(RandomUtils.nextInt(5000));
						} catch (InterruptedException e) {
						}
					}
				}
				log.error("end auto answer...");
			}
		});
		return null;
	}

	private String[] parseUids() {
		String[] uids = new String[0];
		try {
			File file = new File(getClass().getClassLoader()
					.getResource("/cms/kxUsers").getFile());
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (true) {
				try {
					String uid = br.readLine();
					if (StringUtils.isEmpty(uid)) {
						break;
					}
					uids = (String[]) ArrayUtils.add(uids, uid);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		return uids;
	}
}
