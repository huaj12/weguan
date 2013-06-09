package com.easylife.movie.common.service;

import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.easylife.movie.BuildConfig;
import com.easylife.movie.main.bean.Channel;
import com.easylife.movie.video.model.Category;

public class CommonData {
	public static final String SHARED_PREFERNCES_CATEGORY = "category";
	private static List<Category> categoryList = null;
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static List<Category> getCategorys(Context context) {
		ApplicationInfo info = null;
		String channelId = null;
		try {
			info = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);

			channelId = info.metaData.getString("UMENG_CHANNEL");
		} catch (Exception e) {
		}
		if (categoryList == null) {
			try {
				categoryList = objectMapper.readValue(
						context.getAssets().open("category.txt"),
						new TypeReference<List<Category>>() {
						});
				if (Channel.APPCHINA.getChannel().equals(channelId)) {
					// 应用汇 删除片花
					categoryList.remove(new Category(10l));
				}
				return categoryList;
			} catch (Exception e) {
				if (BuildConfig.DEBUG) {
					Log.d("getCategorys", "json to Category is error", e);
				}
			}
			return Collections.emptyList();
		} else {
			return categoryList;
		}
	}

	public static Category getCategory(long id, Context context) {
		for (Category category : getCategorys(context)) {
			if (category.getCategoryId() == id) {
				return category;
			}
		}
		return null;
	}
}
