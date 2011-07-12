package com.weguan.passport.form;

import org.springframework.web.multipart.MultipartFile;

public class ModifyForm {

	private String oldPwd;

	private String pwdConf;

	private String password;

	private String emailAddress;

	private MultipartFile avatar;

	private String blogTitle;

	private String blogAbout;

	private String template;

	public String getPwdConf() {
		return pwdConf;
	}

	public void setPwdConf(String pwdConf) {
		this.pwdConf = pwdConf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogAbout() {
		return blogAbout;
	}

	public void setBlogAbout(String blogAbout) {
		this.blogAbout = blogAbout;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
}
