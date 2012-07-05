package com.juzhai.core.mail.manager;

public interface IFrequencyStrategy {
	/**
	 * 获取发送频率
	 * 
	 * @param address
	 */
	long getfrequency(String address);
}
