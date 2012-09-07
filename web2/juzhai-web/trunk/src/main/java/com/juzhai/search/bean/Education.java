package com.juzhai.search.bean;

public enum Education {
	CZ(1, "education.text.cz"), GZ(2, "education.text.gz"), ZZ(3,
			"education.text.zz"), DZ(4, "education.text.dz"), BK(5,
			"education.text.bk"), SS(6, "education.text.ss"), BS(7,
			"education.text.bs");

	private int type;

	private String text;

	private Education(int type, String text) {
		this.type = type;
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
