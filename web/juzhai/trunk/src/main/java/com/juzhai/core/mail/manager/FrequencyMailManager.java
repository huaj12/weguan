package com.juzhai.core.mail.manager;

import com.juzhai.core.mail.bean.Mail;

public class FrequencyMailManager extends AbstractMailManager {

	private IFrequencyStrategy frequencyStrategy;

	@Override
	protected void doSendMail(Mail mail) throws Exception {
		long interval = frequencyStrategy.getfrequency(mail.getReceiver()
				.getEmailAddress());
		if (interval > 0) {
			Thread.sleep(interval);
		}
		mailSender.send(mail);
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
	// long interval = frequencyStrategy.getfrequency(mail
	// .getReceiver().getEmailAddress());
	// if (interval > 0) {
	// Thread.sleep(interval);
	// }
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

	public void setFrequencyStrategy(IFrequencyStrategy frequencyStrategy) {
		this.frequencyStrategy = frequencyStrategy;
	}

	@Override
	protected void sendMailImmediately(Mail mail) {
		throw new UnsupportedOperationException(
				"Frequenct Mail Manager unsupport immediately send mail. Please use Simple Mail Manager");
	}

}
