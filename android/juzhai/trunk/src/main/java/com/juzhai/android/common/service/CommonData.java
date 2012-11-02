package com.juzhai.android.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.common.model.City;
import com.juzhai.android.common.model.Profession;
import com.juzhai.android.common.model.Province;
import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.model.Result.ProfessionResult;
import com.juzhai.android.core.model.Result.ProvinceCityResult;

public class CommonData {
	public static final String SHARED_PREFERNCES_CATEGORY = "category";
	public static final String SHARED_PROVINCE_CITY = "provinceCity";
	public static final String SHARED_PREFERNCES_PROFESSION = "profession";
	private static List<Province> provinceList = null;
	private static List<City> cityList = null;
	private static List<Profession> professionList = null;
	private static List<Category> categoryList = null;
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static List<Category> getCategorys(Context context) {
		if (categoryList == null) {
			try {
				categoryList = objectMapper.readValue(
						context.getAssets().open("category.txt"),
						new TypeReference<List<Category>>() {
						});
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

	public static List<Category> getAllCategorys(Context context) {
		List<Category> list = new ArrayList<Category>();
		Category cat = new Category();
		cat.setCategoryId(0);
		cat.setName(context.getResources().getString(R.string.category_all));
		list.add(cat);
		list.addAll(getCategorys(context));
		return list;

	}

	public static List<Province> getProvinces(Context context) {
		if (provinceList == null) {
			try {
				ProvinceCityResult result = objectMapper.readValue(context
						.getAssets().open("provinceCity.txt"),
						ProvinceCityResult.class);
				provinceList = result.getResult().getProvinceList();
				return provinceList;
			} catch (Exception e) {
				if (BuildConfig.DEBUG) {
					Log.d("getProvinces", "json to province is error", e);
				}
			}
			return Collections.emptyList();
		} else {
			return provinceList;
		}
	}

	public static List<City> getCitys(Context context) {
		if (cityList == null) {
			try {
				ProvinceCityResult result = objectMapper.readValue(context
						.getAssets().open("provinceCity.txt"),
						ProvinceCityResult.class);
				cityList = result.getResult().getCityList();
				return cityList;
			} catch (Exception e) {
				if (BuildConfig.DEBUG) {
					Log.d("getCitys", "json to city is error", e);
				}
			}
			return Collections.emptyList();
		} else {
			return cityList;
		}
	}

	public static List<Profession> getProfessionList(Context context) {
		if (professionList == null) {
			try {
				ProfessionResult result = objectMapper.readValue(context
						.getAssets().open("profession.txt"),
						ProfessionResult.class);
				professionList = new ArrayList<Profession>(result.getResult()
						.size());
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
					Log.d("getProfessionList", "json to profession is error", e);
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

	public static <T extends Entity> int getDataIndxex(long id, List<T> datas) {
		for (int i = 0; i < datas.size(); i++) {
			long identify = (Long) datas.get(i).getIdentify();
			if (identify == id) {
				return i;
			}
		}
		return 0;
	}

	public static List<City> getSelectCity(long provinceId, List<City> allCitys) {
		List<City> ciyts = new ArrayList<City>();
		for (City city : allCitys) {
			if (provinceId == city.getProvinceId()) {
				ciyts.add(city);
			}
		}
		return ciyts;
	}
}
