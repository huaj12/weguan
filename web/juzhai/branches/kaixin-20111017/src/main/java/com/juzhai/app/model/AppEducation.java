package com.juzhai.app.model;

import com.juzhai.app.util.ConvertObject;
import com.juzhai.core.exception.JuzhaiAppException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class AppEducation {
	private String schooltype;
    private String school;
    private String strClass;
    private String year;

    public AppEducation(JSONObject json) throws JuzhaiAppException {
        init(json);
    }

    private void init(JSONObject json) throws JuzhaiAppException {
        if (json != null) {
            try {
                schooltype = ConvertObject.getString(json,"schooltype");
                school = ConvertObject.getString(json,"school");
                strClass = ConvertObject.getString(json,"strClass");
                year = ConvertObject.getString(json,"year");
            } catch (JSONException jsone) {
                throw new JuzhaiAppException(jsone.getMessage() + ":" + json.toString(), jsone);
            }
        }
    }

    public String getSchool() {
        return school;
    }

    public String getSchooltype() {
        return schooltype;
    }

    public String getStrClass() {
        return strClass;
    }

    public String getYear() {
        return year;
    }

    public String toString() {
        return "Education{"
                + "schooltype='" + schooltype + '\''
                + ", school='" + school + '\''
                + ", strClass='" + strClass + '\''
                + ", year='" + year + '\''
                + '}';
    }
}
