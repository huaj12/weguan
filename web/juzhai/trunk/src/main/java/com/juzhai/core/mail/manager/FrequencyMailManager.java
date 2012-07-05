package com.juzhai.core.mail.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.core.mail.bean.Mail;

public class FrequencyMailManager extends AbstractMailManager {
	private final Log log = LogFactory.getLog(getClass());

	private IFrequencyStrategy frequencyStrategy;

	@Override
	public Runnable getRunnable() {
		return new Runnable() {

			@Override
			public void run() {
				if (log.isDebugEnabled()) {
					log.debug("start mail daemon");
				}
				while (true) {
					Mail mail = mailQueue.blockPop(blockPopMailTimeout);
					if (null != mail) {
						try {
							long interval = frequencyStrategy.getfrequency(mail
									.getReceiver().getEmailAddress());
							if (interval > 0) {
								Thread.sleep(interval);
							}
							mailSender.send(mail);
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

	public void setFrequencyStrategy(IFrequencyStrategy frequencyStrategy) {
		this.frequencyStrategy = frequencyStrategy;
	}

}
