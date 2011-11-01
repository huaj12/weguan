package com.juzhai.act.bean;

public enum SuitStatus {
	ALL("不限"), SINGLE("单身"), LOVERS("情侣"), FAMILY("家人"), SCHOOLMATE("同学"), BROTHER("兄弟"), SISTER("姐妹");
	private String type;

	private SuitStatus(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public static int getSuitStatus(String status){
		return SuitStatus.valueOf(status).ordinal();
	}
	public static SuitStatus getByIndex(int index){
		if(index<0||index>SuitStatus.values().length-1)return null;
		return SuitStatus.values()[index];
	}
}
