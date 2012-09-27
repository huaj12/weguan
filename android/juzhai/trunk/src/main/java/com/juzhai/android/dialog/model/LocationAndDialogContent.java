package com.juzhai.android.dialog.model;

import java.io.Serializable;

public class LocationAndDialogContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7042057015921319909L;

	private DialogContent dialogContent;
	private int location;

	public DialogContent getDialogContent() {
		return dialogContent;
	}

	public void setDialogContent(DialogContent dialogContent) {
		this.dialogContent = dialogContent;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

}
