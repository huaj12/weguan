package com.juzhai.android.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.common.model.City;
import com.juzhai.android.common.model.Profession;
import com.juzhai.android.common.model.Province;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.CategoryResult;
import com.juzhai.android.core.model.Result.ProfessionResult;
import com.juzhai.android.core.model.Result.ProvinceCityResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.utils.JacksonSerializer;

public class CommonData {
	public static final String SHARED_PREFERNCES_CATEGORY = "category";
	public static final String SHARED_PROVINCE_CITY = "provinceCity";
	public static final String SHARED_PREFERNCES_PROFESSION = "profession";
	private static List<Province> provinceList = null;
	private static List<City> cityList = null;
	private static List<Profession> professionList = null;
	private static List<Category> categoryList = null;

	public static List<Category> getCategorys(Context context) {
		// TODO (done) category为什么没有其他三个数据的待遇？
		if (CollectionUtils.isEmpty(categoryList)) {
			String jsonString = new SharedPreferencesManager(context)
					.getString(SHARED_PREFERNCES_CATEGORY);
			if (StringUtils.isNotEmpty(jsonString)) {
				try {
					CategoryResult result = JacksonSerializer.toBean(
							jsonString, CategoryResult.class);
					categoryList = result.getResult();
					return categoryList;
				} catch (Exception e) {
					if (BuildConfig.DEBUG) {
						Log.d("getCategorys", "json to Category is error", e);
					}
				}
			}
			return Collections.emptyList();
		} else {
			return categoryList;
		}
	}

	public static List<Category> getAllCategorys(Context context) {
		List<Category> list = new ArrayList<Category>();
		Category cat = new Category();
		cat.setCategoryId(0);
		cat.setName(context.getResources().getString(R.string.all));
		list.add(cat);
		list.addAll(getCategorys(context));
		return list;

	}

	public static List<Province> getProvinces(Context context) {
		if (CollectionUtils.isEmpty(provinceList)) {
			String jsonString = new SharedPreferencesManager(context)
					.getString(SHARED_PROVINCE_CITY);
			if (StringUtils.isNotEmpty(jsonString)) {
				try {
					ProvinceCityResult result = JacksonSerializer.toBean(
							jsonString, ProvinceCityResult.class);
					provinceList = result.getResult().getProvinceList();
					return provinceList;
				} catch (Exception e) {
					if (BuildConfig.DEBUG) {
						Log.d("getProvinces", "json to province is error", e);
					}
				}
			}
			return Collections.emptyList();
		} else {
			return provinceList;
		}
	}

	public static List<City> getCitys(Context context) {
		if (CollectionUtils.isEmpty(cityList)) {
			String jsonString = new SharedPreferencesManager(context)
					.getString(SHARED_PROVINCE_CITY);
			if (StringUtils.isNotEmpty(jsonString)) {
				try {
					ProvinceCityResult result = JacksonSerializer.toBean(
							jsonString, ProvinceCityResult.class);
					cityList = result.getResult().getCityList();
					return cityList;
				} catch (Exception e) {
					if (BuildConfig.DEBUG) {
						Log.d("getCitys", "json to city is error", e);
					}
				}
			}
			return Collections.emptyList();
		} else {
			return cityList;
		}
	}

	public static List<Profession> getProfessionList(Context context) {
		// TODO (done)
		// 如果professionList不是null，只是一个空列表，也需要每次去磁盘上取？city和province同理
		if (CollectionUtils.isEmpty(professionList)) {
			String jsonString = new SharedPreferencesManager(context)
					.getString(SHARED_PREFERNCES_PROFESSION);
			if (StringUtils.isNotEmpty(jsonString)) {
				try {
					ProfessionResult result = JacksonSerializer.toBean(
							jsonString, ProfessionResult.class);
					professionList = new ArrayList<Profession>(result
							.getResult().size());
					for (Map<Long, String> mapAll : result.getResult()) {
						for (Entry<Long, String> map : mapAll.entrySet()) {
							Profession profession = new Profession();
							profession.setId(map.getKey());
							profession.setName(map.getValue());
							professionList.add(profession);
						}
					}
					Profession profession = new Profession();
					profession.setId(0);
					profession.setName(context.getResources().getString(
							R.string.other));
					professionList.add(profession);
					return professionList;
				} catch (Exception e) {
					if (BuildConfig.DEBUG) {
						Log.d("getProfessionList",
								"json to profession is error", e);
					}
				}
			}
			return Collections.emptyList();
		} else {
			return professionList;
		}
	}

	public static String[] getAllCategoryNames(Context context) {
		return getCategoryNames(getAllCategorys(context));
	}

	public static String[] getCategoryNames(Context context) {
		return getCategoryNames(getCategorys(context));
	}

	private static String[] getCategoryNames(List<Category> categorys) {
		String[] categoryNames = new String[categorys.size()];
		for (int i = 0; i < categorys.size(); i++) {
			categoryNames[i] = categorys.get(i).getName();
		}
		return categoryNames;
	}

	public static void initDate(final Context context) {
		final SharedPreferencesManager manager = new SharedPreferencesManager(
				context);
		// 加载类别
		if (!manager.isExist(SHARED_PREFERNCES_CATEGORY)) {
			new AsyncTask<Void, Void, Boolean>() {
				private final String CATEGORY_URI = "base/categoryList";

				@Override
				protected Boolean doInBackground(Void... params) {
					initCategory();
					return true;
				}

				private void initCategory() {
					ResponseEntity<CategoryResult> response = HttpUtils.get(
							context, CATEGORY_URI, CategoryResult.class);
					if (response != null && response.getBody() != null
							&& response.getBody().getSuccess()) {
						CategoryResult categoryResult = response.getBody();
						try {
							manager.commit(SHARED_PREFERNCES_CATEGORY,
									JacksonSerializer.toString(categoryResult));
						} catch (JsonGenerationException e) {
							if (BuildConfig.DEBUG) {
								Log.d(getClass().getSimpleName(),
										"Category to json is error", e);
							}
						}

					}
				}
			}.execute();
		}
		// 加载城市
		if (!manager.isExist(SHARED_PROVINCE_CITY)) {
			new AsyncTask<Void, Void, Boolean>() {
				private final String PROVINCE_CITY_URI = "base/provinceCityList";

				@Override
				protected Boolean doInBackground(Void... params) {
					initProvinceCity();
					return true;
				}

				private void initProvinceCity() {
					ResponseEntity<ProvinceCityResult> response = HttpUtils
							.get(context, PROVINCE_CITY_URI,
									ProvinceCityResult.class);
					if (response != null && response.getBody() != null
							&& response.getBody().getSuccess()) {
						ProvinceCityResult result = response.getBody();
						try {
							manager.commit(SHARED_PROVINCE_CITY,
									JacksonSerializer.toString(result));
						} catch (JsonGenerationException e) {
							if (BuildConfig.DEBUG) {
								Log.d(getClass().getSimpleName(),
										"provinceCity to json is error", e);
							}
						}

					}
				}
			}.execute();
		}

		// 加载职业
		if (!manager.isExist(SHARED_PREFERNCES_PROFESSION)) {
			new AsyncTask<Void, Void, Boolean>() {
				private final String PROFESSION_URI = "base/professionList";

				@Override
				protected Boolean doInBackground(Void... params) {
					initProfession();
					return true;
				}

				private void initProfession() {
					ResponseEntity<ProfessionResult> response = HttpUtils.get(
							context, PROFESSION_URI, ProfessionResult.class);
					if (response != null && response.getBody() != null
							&& response.getBody().getSuccess()) {
						ProfessionResult result = response.getBody();
						try {
							manager.commit(SHARED_PREFERNCES_PROFESSION,
									JacksonSerializer.toString(result));
						} catch (JsonGenerationException e) {
							if (BuildConfig.DEBUG) {
								Log.d(getClass().getSimpleName(),
										"profession to json is error", e);
							}
						}

					}
				}
			}.execute();
		}
	}

}
