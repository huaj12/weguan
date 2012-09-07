package com.juzhai.core.mail.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.core.mail.bean.Mail;

public class SimpleMailManager extends AbstractMailManager {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	protected void doSendMail(Mail mail) throws Exception {
		mailSender.send(mail);
	}

	@Override
	protected void sendMailImmediately(final Mail mail) {
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
	}

	@Override
	protected String getMailManagerName() {
		return this.getClass().getSimpleName();
	}

	// @Override
	// public Runnable getRunnable() {
	// return new Runnable() {
	//
	// @Override
	// public void run() {
	// if (log.isDebugEnabled()) {
	// log.debug("start mail daemon");
	// }
	// while (true) {
	// Mail mail = mailQueue.blockPop(blockPopMailTimeout);
	// if (null != mail) {
	// try {
	// mailSender.send(mail);
	// if (log.isDebugEnabled()) {
	// log.debug("send mail to ["
	// + mail.getReceiver().getEmailAddress()
	// + "].");
	// }
	// } catch (Exception e) {
	// log.error("daemon send mail failed.", e);
	// }
	// } else if (mailDaemonStopping) {
	// break;
	// }
	// }
	// mailDaemonRunning = false;
	// mailDaemonStopping = false;
	// if (log.isDebugEnabled()) {
	// log.debug("end mail daemon");
	// }
	// }
	// };
	// }

}
