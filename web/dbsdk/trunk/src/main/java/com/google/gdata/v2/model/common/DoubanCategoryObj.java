/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gdata.v2.model.common;

import com.google.api.client.util.Key;
import com.google.gdata.v2.model.IDoubanObject;

/**
 * 
 * @author Zhibo Wei <uglytroll@dongxuexidu.com>
 */
public class DoubanCategoryObj implements IDoubanObject {

	@Override
	public String getObjName() {
		return "category";
	}

	@Key("@scheme")
	private String scheme;

	@Key("@term")
	private String term;

	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * @param scheme
	 *            the scheme to set
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term
	 *            the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

}
