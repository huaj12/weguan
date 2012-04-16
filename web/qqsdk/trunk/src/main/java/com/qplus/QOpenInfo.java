/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus;

/**
 * 
 * @author nbzhang
 */
public class QOpenInfo implements java.io.Serializable {

	private static final long serialVersionUID = -8917417593105420500L;

	private String face;

	private String gender;

	private String nick;

	private String outh;

	@Override
	public String toString() {
		return QOpenInfo.class.getSimpleName() + "[face=" + this.face
				+ ", gender=" + this.gender + ", nick=" + this.nick + ", outh="
				+ this.outh + "]";
	}

	public QOpenInfo() {
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getOuth() {
		return outh;
	}

	public void setOuth(String outh) {
		this.outh = outh;
	}
}
