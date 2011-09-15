package com.juzhai.app.model;

import java.util.ArrayList;
import java.util.List;

import com.juzhai.app.util.ConvertObject;
import com.juzhai.core.exception.JuzhaiAppException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AppUser {
	static final String[] POSSIBLE_ROOT_NAMES = new String[] { "user",
			"sender", "recipient", "retweeting_user" };
	private long uid;
	private String name;
	private int gender; // 性别, 1.男, 2.女, 0.未知
	private String hometown;
	private String city;
	private String logo120;
	private String logo50;
	private String birthday;
	private String bodyform;
	private String blood;
	private String marriage;
	private String trainwith;
	private String interest;
	private String favbook;
	private String favmovie;
	private String favtv;
	private String idol;
	private String motto;
	private String wishlist;
	private String intro;
	private List<AppEducation> education = new ArrayList<AppEducation>();
	// private String schooltype;
	// private String school;
	// private String strClass; // 班级
	// private String year;
	private List<AppCareer> career = new ArrayList<AppCareer>();
	// private String company;
	// private String dept;
	// private String beginyear;
	// private String beginmonth;
	// private String endyear;
	// private String endmonth;
	private int isStar; // 0,非明星；1：明星
	private static final long serialVersionUID = -6345893237975349030L;

	public AppUser(JSONObject json) throws JuzhaiAppException {
		if (json != null) {
			uid = ConvertObject.getLong(json, "uid");
			name = ConvertObject.getString(json,"name");
			hometown = ConvertObject.getString(json,"hometown");
			city = ConvertObject.getString(json,"city");
			logo120 = ConvertObject.getString(json,"logo120");
			logo50 = ConvertObject.getString(json,"logo50");
			birthday = ConvertObject.getString(json,"birthday");
			bodyform = ConvertObject.getString(json,"bodyform");
			blood = ConvertObject.getString(json,"blood");
			marriage = ConvertObject.getString(json,"marriage");
			trainwith = ConvertObject.getString(json,"trainwith");
			interest = ConvertObject.getString(json,"interest");
			favbook = ConvertObject.getString(json,"favbook");
			favmovie = ConvertObject.getString(json,"favmovie");
			favtv = ConvertObject.getString(json,"favtv");
			idol = ConvertObject.getString(json,"idol");
			motto = ConvertObject.getString(json,"motto");
			wishlist = ConvertObject.getString(json,"wishlist");
			intro = ConvertObject.getString(json,"intro");
			if (ConvertObject.getString(json,"education").length() != 0) {
				JSONArray educationJsonArr = json.getJSONArray("education");
				int len = educationJsonArr.size();
				for (int i = 0; i < len; i++) {
					AppEducation oneEdu = new AppEducation(educationJsonArr
							.getJSONObject(i));
					education.add(oneEdu);
				}
			}
			if (ConvertObject.getString(json,"career").length() != 0) {
				JSONArray careerJsonArr = json.getJSONArray("career");
				int len = careerJsonArr.size();
				for (int i = 0; i < len; i++) {
					AppCareer oneCareer = new AppCareer(careerJsonArr
							.getJSONObject(i));
					career.add(oneCareer);
				}
			}

			isStar = ConvertObject.getInt(json,"isStar");
			gender = ConvertObject.getInt(json,"gender");
		}
	}
}