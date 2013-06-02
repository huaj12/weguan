package com.easylife.weather.core.exception;

import android.content.Context;

public class WeatherException extends Exception {

	private static final long serialVersionUID = 3202312995038551098L;

	private int messageId = -1;

	private Context context;

	public WeatherException(Context context, int messageId) {
		super();
		this.context = context;
		this.messageId = messageId;
	}

	public WeatherException(Context context, String detailMessage) {
		super(detailMessage);
		this.context = context;
	}

	public WeatherException(Context context, int messageId, Throwable throwable) {
		super(throwable);
		this.context = context;
		this.messageId = messageId;
	}

	public WeatherException(Context context, String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.context = context;
	}

	@Override
	public String getMessage() {
		String message = null;
		if (this.messageId >= 0) {
			message = context.getString(this.messageId);
		} else {
			message = super.getMessage();
		}
		return message;
	}

	public int getMessageId() {
		return messageId;
	}
}
