package com.juzhai.home.controller.form;

public class AnswerForm {

	private long questionId;

	private String tpIdentity;

	private int answerId;

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getTpIdentity() {
		return tpIdentity;
	}

	public void setTpIdentity(String tpIdentity) {
		this.tpIdentity = tpIdentity;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
}
