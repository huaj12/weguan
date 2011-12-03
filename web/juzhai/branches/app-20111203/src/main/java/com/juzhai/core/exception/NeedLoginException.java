package com.juzhai.core.exception;

public class NeedLoginException extends JuzhaiException {

	public enum RunType {
		APP, CONNET, WEB
	}

	private static final long serialVersionUID = 2003130278035357678L;

	private RunType runType;

	public NeedLoginException(RunType runType) {
		super();
		this.runType = runType;
	}

	public RunType getRunType() {
		return runType;
	}
}
