package com.juzhai.cms.task;

import java.util.concurrent.CountDownLatch;

import com.juzhai.cms.service.IVerifyLogoService;
import com.juzhai.passport.model.Profile;

public class RobotVerifyingLogoTask implements Runnable {
	private Profile profile;
	private IVerifyLogoService verifyLogoService;
	private CountDownLatch down;

	public RobotVerifyingLogoTask(Profile profile,
			IVerifyLogoService verifyLogoService, CountDownLatch down) {
		super();
		this.profile = profile;
		this.verifyLogoService = verifyLogoService;
		this.down = down;
	}

	@Override
	public void run() {
		try {
			Boolean flag = verifyLogoService.realPic(profile.getNewLogoPic());
			if (flag != null && !flag) {
				// 网络图片
				verifyLogoService.denyLogo(profile.getUid());
			}
		} finally {
			down.countDown();
		}
	}
}
