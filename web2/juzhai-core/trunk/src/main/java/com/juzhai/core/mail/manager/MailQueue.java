package com.juzhai.core.mail.manager;

import com.juzhai.core.mail.bean.Mail;

public interface MailQueue {

	/**
	 * 把mail加入队列，等待被发送
	 * 
	 * @param mail
	 *            要加入队列的邮件
	 */
	void push(String name, Mail mail);

	/**
	 * 把mail加入队列，等待被发送
	 * 
	 * @param mail
	 *            要加入队列的邮件
	 * @param priority
	 *            优先级
	 */
	void pushWithPriotity(String name, Mail mail, int priority);

	/**
	 * 取出第一封邮件
	 * 
	 * @return 若取不到，则返回null
	 */
	Mail pop(String name);

	/**
	 * 去除第一封邮件，此方法会被block，当没有邮件返回情况下
	 * 
	 * @param timeout
	 *            超时时间（秒）
	 * @return 返回邮件
	 */
	Mail blockPop(String name, int timeout);
}
