package com.juzhai.core.mail.manager;

import com.juzhai.core.mail.bean.Mail;

public interface MailManager {

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 * @param immediately
	 *            true：直接发送。false：放入发送队列
	 */
	void sendMail(final Mail mail, boolean immediately);

	/**
	 * 启动后台线程
	 */
	// TODO 考虑是否能不暴露给用户
	void startDaemon();

	/**
	 * 停止线程
	 */
	// TODO 考虑是否能不暴露给用户
	void stopDaemon();
}
