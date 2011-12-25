package com.juzhai.cms.controller.view;

import java.io.Serializable;

import com.juzhai.act.InitData;
import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.core.web.jstl.JzCoreFunction;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;

public class CmsActMagerView implements Serializable {
	private static final long serialVersionUID = -1871387836944777684L;

	private Act act;

	private String logoWebPath;

	private String address;

	private String gender;

	private String age;

	private String status;

	private String category;

	private String proName;

	private String cityName;

	public CmsActMagerView(Act act){
		String age = SuitAge.getByIndex(act.getSuitAge()).getType();
		String status = SuitStatus.getByIndex(act.getSuitStatus())
				.getType();
		String gender = SuitGender.getByIndex(act.getSuitGender())
				.getType();
		City city = com.juzhai.passport.InitData.CITY_MAP
				.get(act.getCity());
		Province pro = com.juzhai.passport.InitData.PROVINCE_MAP.get(act
				.getProvince());
		String proName = "";
		String cityName = "";
		if (pro != null) {
			proName = pro.getName();
		}
		if (city != null) {
			cityName = city.getName();
		}
		String address = "";
		if (act.getAddress() != null) {
			address = act.getAddress();
		}
		String logoWebPath = "";
		if (act.getLogo() != null) {
			logoWebPath = JzCoreFunction.actLogo(act.getId(),
					act.getLogo(), 0);
		}
		StringBuffer categorys = new StringBuffer();
		String cats = act.getCategoryIds();
		if (cats != null) {
			for (String cat : cats.split(",")) {
				Category c = InitData.CATEGORY_MAP.get(Long.valueOf(cat));
				if (c != null) {
					categorys.append(c.getName() + " ");
				}
			}
		}
		this.act = act;
		this.logoWebPath = logoWebPath;
		this.address = address;
		this.category = categorys.toString();
		this.status = status;
		this.age = age;
		this.gender = gender;
		this.proName = proName;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public String getLogoWebPath() {
		return logoWebPath;
	}

	public void setLogoWebPath(String logoWebPath) {
		this.logoWebPath = logoWebPath;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
