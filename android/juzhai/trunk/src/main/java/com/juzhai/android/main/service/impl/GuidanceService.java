package com.juzhai.android.main.service.impl;

import android.content.Context;

import com.juzhai.android.core.ApplicationContext;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.main.service.IGuidanceService;

public class GuidanceService implements IGuidanceService {

	private final String guideKey = "guidance_";

	@Override
	public boolean hasGuide(Context context) {
		String version = ApplicationContext.getVersionName(context);
		return new SharedPreferencesManager(context).getBoolean(guideKey
				+ version);
	}

	@Override
	public void completeGuide(Context context) {
		String version = ApplicationContext.getVersionName(context);
		new SharedPreferencesManager(context).commitBool(guideKey + version,
				true);
	}

}
