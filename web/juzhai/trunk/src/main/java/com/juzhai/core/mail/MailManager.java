package com.juzhai.core.mail;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.core.Constants;

public class MailManager {

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
		mail.setEncoding(encoding);
		if (immediately) {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					mailSender.send(mail);
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
					mailSender.send(mail);
				} else if (mailDaemonStopping) {
					break;
				}
			}
			mailDaemonRunning = false;
			mailDaemonStopping = false;
		}
	}
}
