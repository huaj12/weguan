package com.juzhai.core.mail.manager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.core.Constants;
import com.juzhai.core.mail.bean.Mail;

public class MailManager {
	private final Log log = LogFactory.getLog(getClass());
	
	private static final int DEFAULT_BLOCK_POP_MAIL_TIMEOUT = 60;
	
	private static final String DEFAULT_ENCODING = Constants.UTF8;

	private MailSender mailSender;

	private ThreadPoolTaskExecutor taskExecutor;

	private MailQueue mailQueue;

	private volatile boolean mailDaemonRunning;

	private volatile boolean mailDaemonStopping;

	private int blockPopMailTimeout = DEFAULT_BLOCK_POP_MAIL_TIMEOUT;

	private String encoding = DEFAULT_ENCODING;

	public void sendMail(final Mail mail, boolean immediately) {
		if (StringUtils.isEmpty(mail.getEncoding())) {
			mail.setEncoding(encoding);
		}
		if (immediately) {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						mailSender.send(mail);
					} catch (Exception e) {
						log.error("immediately send mail failed.", e);
					}
				}
			});
		} else {
			mailQueue.push(mail);
		}
	}

	public synchronized void startDaemon() {
		if (!mailDaemonRunning && !mailDaemonStopping) {
			Thread daemon = new Thread(new MailDaemon());
			daemon.start();
			mailDaemonRunning = true;
		}
	}

	public synchronized void stopDaemon() {
		if (mailDaemonRunning && !mailDaemonStopping) {
			mailDaemonStopping = true;
		}
	}

	private class MailDaemon implements Runnable {
		@Override
		public void run() {
			while (true) {
				Mail mail = mailQueue.blockPop(blockPopMailTimeout);
				if (null != mail) {
					try {
						mailSender.send(mail);
					} catch (Exception e) {
						log.error("daemon send mail failed.", e);
					}
				} else if (mailDaemonStopping) {
					break;
				}
			}
			mailDaemonRunning = false;
			mailDaemonStopping = false;
		}
	}
}
