package com.easylife.movie.main.bean;

public enum Channel {
	APPCHINA("appchina");
	private String channel;

	private Channel(String channel) {
		this.channel = channel;
	}

	public String getChannel() {
		return channel;
	}

	public static Channel getChannelEnum(String name) {
		for (Channel joinTypeEnum : values()) {
			if (joinTypeEnum.getChannel().equals(name)) {
				return joinTypeEnum;
			}
		}
		return null;
	}

}
