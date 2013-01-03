package com.google.gdata.v2.model.common;

import com.google.api.client.util.Key;
import com.google.gdata.v2.model.IDoubanObject;

/**
 * 
 * @author Zhibo Wei <uglytroll@dongxuexidu.com>
 */
public class DoubanCountObj implements IDoubanObject {

	@Override
	public String getObjName() {
		return "doubancommentscount";
	}

	@Key("@value")
	private int value;

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
