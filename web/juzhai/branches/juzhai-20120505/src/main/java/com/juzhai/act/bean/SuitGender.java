package com.juzhai.act.bean;

public enum SuitGender {
	ALL("不限"), MALE("男"), FEMALE("女");
	private String type;

	private SuitGender(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public static int getSuitGender(String gender){
		return SuitGender.valueOf(gender).ordinal();
	}
	public static SuitGender getByIndex(int index){
		if(index<0||index>SuitGender.values().length-1)return null;
		return SuitGender.values()[index];
	}
}
