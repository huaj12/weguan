package com.juzhai.android.passport;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.juzhai.android.R;

public class InitDate {
	private Context mContext;

	public InitDate(Context mContext) {
		this.mContext = mContext;
	}

	public ArrayList<HashMap<String, Object>> getTpLoginList() {
		ArrayList<HashMap<String, Object>> tpLoginMap = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> sina = new HashMap<String, Object>();
		HashMap<String, Object> douban = new HashMap<String, Object>();
		HashMap<String, Object> qq = new HashMap<String, Object>();

		sina.put("image_logo", R.drawable.sina_login_icon);
		sina.put("item_title",
				mContext.getResources().getString(R.string.sinaLogin));
		sina.put("arrow", R.drawable.login_icon_arrow);

		douban.put("image_logo", R.drawable.db_login_icon);
		douban.put("item_title",
				mContext.getResources().getString(R.string.doubanLogin));
		douban.put("arrow", R.drawable.login_icon_arrow);

		qq.put("image_logo", R.drawable.qq_login_icon);
		qq.put("item_title", mContext.getResources()
				.getString(R.string.qqLogin));
		qq.put("arrow", R.drawable.login_icon_arrow);

		tpLoginMap.add(sina);
		tpLoginMap.add(douban);
		tpLoginMap.add(qq);
		return tpLoginMap;
	}
}
