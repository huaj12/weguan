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
import com.juzhai.android.common.model.ProvinceCity;
import com.juzhai.android.core.model.Entity;

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
				ProvinceCity result = objectMapper.readValue(context
						.getAssets().open("provinceCity.txt"),
						ProvinceCity.class);
				provinceList = result.getProvinceList();
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
				ProvinceCity result = objectMapper.readValue(context
						.getAssets().open("provinceCity.txt"),
						ProvinceCity.class);
				cityList = result.getCityList();
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
				List<Map<Long, String>> mapList = objectMapper.readValue(
						context.getAssets().open("profession.txt"),
						new TypeReference<List<Map<Long, String>>>() {
						});
				professionList = new ArrayList<Profession>(mapList.size());
				for (Map<Long, String> mapAll : mapList) {
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

	// TODO (done)
	// 这个类不应该带有显示层的功能，这个类只提供通过categoryId获取Category，具体获取category什么信息，调用方自己取
	public static Category getCategory(long catId, Context context) {
		List<Category> list = getCategorys(context);
		for (Category category : list) {
			if (category.getCategoryId() == catId) {
				return category;
			}
		}
		return null;
	}
}
