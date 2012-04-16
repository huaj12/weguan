/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus;

/**
 * 
 * @author nbzhang
 */
public class QOpenBean implements java.io.Serializable {

	private static final long serialVersionUID = 4530954976594876363L;

	private String appopenid = "";

	private String appopenkey = "";

	private String useripaddr = "";

	public QOpenBean() {
	}

	public QOpenBean(String appopenid, String appopenkey, String useripaddr) {
		this.setAppopenid(appopenid);
		this.setAppopenkey(appopenkey);
		this.setUseripaddr(useripaddr);
	}

	@Override
	public String toString() {
		return QOpenBean.class.getSimpleName() + "[appopenid=" + this.appopenid
				+ ", appopenkey=" + this.appopenkey + ", useripaddr="
				+ this.useripaddr + "]";
	}

	public String getAppopenid() {
		return appopenid;
	}

	public void setAppopenid(String appopenid) {
		this.appopenid = appopenid;
	}

	public String getAppopenkey() {
		return appopenkey;
	}

	public void setAppopenkey(String appopenkey) {
		this.appopenkey = appopenkey;
	}

	public String getUseripaddr() {
		return useripaddr;
	}

	public void setUseripaddr(String useripaddr) {
		this.useripaddr = useripaddr;
	}
}
