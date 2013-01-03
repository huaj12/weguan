package com.google.gdata.v2.model.common;

import com.google.api.client.util.Key;
import com.google.gdata.v2.model.IDoubanObject;

/**
 * 
 * @author Zhibo Wei <uglytroll@dongxuexidu.com>
 */
public class DoubanWhereObj implements IDoubanObject {

	@Key("@valueString")
	private String value;

	@Override
	public String getObjName() {
		return "doubanwhere";
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
