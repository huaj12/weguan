package com.juzhai.act.bean;

public enum SuitAge {
	ALL("不限"), CHILD("幼儿"), KID("少儿"), YOUTH("青年"), MIDDLE("中年"), OLD("老年");
	private String type;

	private SuitAge(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public static int getSuitAge(String age){
		return SuitAge.valueOf(age).ordinal();
	}
	
	public static SuitAge getByIndex(int index){
		if(index<0||index>SuitAge.values().length-1)return null;
		return SuitAge.values()[index];
	}
}
