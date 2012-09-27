package com.juzhai.android.main.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GuidancePagerAdapter extends FragmentPagerAdapter {

	public GuidancePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		GuidanceFragment fragment = new GuidanceFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(GuidanceFragment.INDEX_KEY, position);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
