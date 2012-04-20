/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus.push;

/**
 * 
 * @author nbzhang
 */
public class QPushBean implements java.io.Serializable {

	private int optype;

	private int num;

	private String text = "";

	private String customize = "";

	private String qplusid = "";

	private long instanceid;

	private String pushmsgid = "";

	public QPushBean() {
	}

	public QPushBean(int optype, int count, String text, String customize) {
		this.setNum(count);
		this.setCustomize(customize);
		this.setOptype(optype);
		this.setText(text);
	}

	@Override
	public String toString() {
		return QPushBean.class.getSimpleName() + "[optype=" + this.optype
				+ ", count=" + this.num + ", text=" + this.text
				+ ", customize=" + this.customize + "]";
	}

	public int getOptype() {
		return optype;
	}

	public void setOptype(int optype) {
		this.optype = optype;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text != null) {
			this.text = text;
		}
	}

	public String getCustomize() {
		return customize;
	}

	public void setCustomize(String customize) {
		if (customize != null) {
			this.customize = customize;
		}
	}

	public String getQplusid() {
		return qplusid;
	}

	public void setQplusid(String qplusid) {
		if (qplusid != null) {
			this.qplusid = qplusid;
		}
	}

	public String getPushmsgid() {
		return pushmsgid;
	}

	public void setPushmsgid(String pushmsgid) {
		if (pushmsgid != null) {
			this.pushmsgid = pushmsgid;
		}
	}

	public long getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(long instanceid) {
		this.instanceid = instanceid;
	}
}
