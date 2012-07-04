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

	private long interval;

	private String queueKey;

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
			mailQueue.push(queueKey, mail);
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
				Mail mail = mailQueue.blockPop(queueKey, blockPopMailTimeout);
				if (null != mail) {
					try {
						if (interval > 0) {
							Thread.sleep(interval);
						}
						// mailSender.send(mail);
						System.out.println(mail.getReceiver().getEmailAddress()
								+ "key:" + queueKey);
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

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public void setQueueKey(String queueKey) {
		this.queueKey = queueKey;
	}

}
