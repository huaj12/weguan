package com.juzhai.android.main.service;

import android.content.Context;

public interface IGuidanceService {

	boolean hasGuide(Context context);

	void completeGuide(Context context);
}
