package com.juzhai.core.mail.manager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.core.mail.bean.Mail;

public class FrequencyMailManager extends AbstractMailManager {
	private final Log log = LogFactory.getLog(getClass());

	private String queueKey;

	private long interval;

	@Override
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

	@Override
	public Runnable getRunnable() {
		return new Runnable() {

			@Override
			public void run() {
				if (log.isDebugEnabled()) {
					log.debug("start mail daemon");
				}
				while (true) {
					Mail mail = mailQueue.blockPop(queueKey,
							blockPopMailTimeout);
					if (null != mail) {
						try {
							Thread.sleep(interval);
							// mailSender.send(mail);
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
		};
	}

	public void setQueueKey(String queueKey) {
		this.queueKey = queueKey;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}
