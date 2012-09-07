package com.juzhai.core.rabbit.listener;

public interface IRabbitMessageListener<T, R> {

	/**
	 * 接受消息处理
	 * 
	 * @param object
	 * @return 如果不需要返回则返回null
	 */
	public R handleMessage(T object);
}
