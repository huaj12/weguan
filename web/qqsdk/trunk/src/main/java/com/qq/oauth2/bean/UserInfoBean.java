package com.qq.oauth2.bean;

public class UserInfoBean extends ResultBean {

	/** 昵称 */
	private String nickName;

	/** 小头像URL 30 */
	private String avatarSmall;

	/** 中头像URL 50 */
	private String avatarMiddle;

	/** 大头像URL100 */
	private String avatarLarge;

	/** 性别 */
	private String gender;

	/** 是否是黄钻 */
	private String isVip;

	/** 黄钻等级 */
	private String level;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarSmall() {
		return avatarSmall;
	}

	public void setAvatarSmall(String avatarSmall) {
		this.avatarSmall = avatarSmall;
	}

	public String getAvatarMiddle() {
		return avatarMiddle;
	}

	public void setAvatarMiddle(String avatarMiddle) {
		this.avatarMiddle = avatarMiddle;
	}

	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
