/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus.push;

/**
 * 
 * @author nbzhang
 */
public class QPushResult extends QPlusResult {

	QPushResult(String json) {
		super(json);
	}

	@Override
	public String toString() {
		return QPushResult.class.getSimpleName() + "[" + this.map + "]";
	}

	@Override
	public int getResultCode() {
		return getIntValue("ERRCODE");
	}
}
