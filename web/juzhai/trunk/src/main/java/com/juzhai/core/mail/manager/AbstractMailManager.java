package com.juzhai.core.mail.manager;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.core.Constants;
import com.juzhai.core.mail.bean.Mail;

public abstract class AbstractMailManager {
	private static final int DEFAULT_BLOCK_POP_MAIL_TIMEOUT = 60;

	private static final String DEFAULT_ENCODING = Constants.UTF8;

	protected MailSender mailSender;

	protected ThreadPoolTaskExecutor taskExecutor;

	protected MailQueue mailQueue;

	protected volatile boolean mailDaemonRunning;

	protected volatile boolean mailDaemonStopping;

	protected int blockPopMailTimeout = DEFAULT_BLOCK_POP_MAIL_TIMEOUT;

	protected String encoding = DEFAULT_ENCODING;

	public abstract void sendMail(final Mail mail, boolean immediately);

	public synchronized void startDaemon() {
		// TODO 如果当前要结束，直接关闭结束标志
		if (!mailDaemonRunning && !mailDaemonStopping) {
			Thread daemon = new Thread(getRunnable());
			daemon.start();
			mailDaemonRunning = true;
		}
	}

	public synchronized void stopDaemon() {
		if (mailDaemonRunning && !mailDaemonStopping) {
			mailDaemonStopping = true;
		}
	}

	public abstract Runnable getRunnable();

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setMailQueue(MailQueue mailQueue) {
		this.mailQueue = mailQueue;
	}

	public void setBlockPopMailTimeout(int blockPopMailTimeout) {
		this.blockPopMailTimeout = blockPopMailTimeout;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
