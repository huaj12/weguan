package com.juzhai.cms.bean;

public enum AdSource {
	MEITUAN("美团"), LASHOU("拉手"), DAZHONGDIANPING("大众点评团"), WOWOTUAN("窝窝团"), TUAN58(
			"58团"), DIDATUAN("嘀嗒团"), FTUAN("F团"), MANZUOWANG("满座网"), JUAN24(
			"24劵"), GAOPENG("高彭"), NUOMIWANG("糯米网"), QUNAER("去哪儿团"), AILITUAN(
			"爱丽团"), GANJITUAN("赶集团"), FANTONGTUAN("饭统饭团"), JUQIWANG("聚齐网"), QIANPIN(
			"千品网");

	private String name;

	private AdSource(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static AdSource getJoinTypeEnum(String name) {
		for (AdSource joinTypeEnum : values()) {
			if (joinTypeEnum.getName().equals(name)) {
				return joinTypeEnum;
			}
		}
		return null;
	}
}
