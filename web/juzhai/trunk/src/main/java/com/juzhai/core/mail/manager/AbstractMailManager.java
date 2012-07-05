package com.juzhai.core.mail.manager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.core.Constants;
import com.juzhai.core.mail.bean.Mail;

public abstract class AbstractMailManager implements MailManager {
	private final Log log = LogFactory.getLog(getClass());

	private static final int DEFAULT_BLOCK_POP_MAIL_TIMEOUT = 60;

	private static final String DEFAULT_ENCODING = Constants.UTF8;

	protected MailSender mailSender;

	protected ThreadPoolTaskExecutor taskExecutor;

	protected MailQueue mailQueue;

	protected volatile boolean mailDaemonRunning;

	protected volatile boolean mailDaemonStopping;

	protected int blockPopMailTimeout = DEFAULT_BLOCK_POP_MAIL_TIMEOUT;

	protected String encoding = DEFAULT_ENCODING;

	public void sendMail(final Mail mail, boolean immediately) {
		if (StringUtils.isEmpty(mail.getEncoding())) {
			mail.setEncoding(encoding);
		}
		if (immediately) {
			sendMailImmediately(mail);
		} else {
			mailQueue.push(mail);
		}
	}

	public synchronized void startDaemon() {
		// TODO 如果当前要结束，直接关闭结束标志
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
			if (log.isDebugEnabled()) {
				log.debug("start mail daemon");
			}
			while (true) {
				Mail mail = mailQueue.blockPop(blockPopMailTimeout);
				if (null != mail) {
					try {
						doSendMail(mail);
						if (log.isDebugEnabled()) {
							log.debug("send mail to ["
									+ mail.getReceiver().getEmailAddress()
									+ "].");
						}
					} catch (Exception e) {
						log.error("daemon send mail failed.", e);
					}
				} else if (mailDaemonStopping) {
					break;
				}
			}
			mailDaemonRunning = false;
			mailDaemonStopping = false;
			if (log.isDebugEnabled()) {
				log.debug("end mail daemon");
			}
		}
	}

	protected abstract void doSendMail(Mail mail) throws Exception;

	protected abstract void sendMailImmediately(Mail mail);

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
