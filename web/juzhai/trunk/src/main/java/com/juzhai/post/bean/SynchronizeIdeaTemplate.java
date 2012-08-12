package com.juzhai.post.bean;

public enum SynchronizeIdeaTemplate {
	SYNCHRONIZE_TEXT("synchronize.idea.text"), SYNCHRONIZE_ADDRESS(
			"synchronize.idea.address"), SYNCHRONIZE_LINK(
			"synchronize.idea.link");

	private String name;

	private SynchronizeIdeaTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
