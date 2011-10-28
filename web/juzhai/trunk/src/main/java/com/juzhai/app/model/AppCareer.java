package com.juzhai.app.model;

import com.juzhai.app.util.ConvertObject;
import com.juzhai.core.exception.JuzhaiAppException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
@Deprecated
public class AppCareer {
	 private String company;
     private String dept;
     private String beginyear;
     private String beginmonth;
     private String endyear;
     private String endmonth;

     public AppCareer(JSONObject json) throws JuzhaiAppException {
         init(json);
     }

     private void init(JSONObject json) throws JuzhaiAppException {
         if (json != null) {
             try {
                 company = ConvertObject.getString(json,"company");
                 dept = ConvertObject.getString(json,"dept");
                 beginyear = ConvertObject.getString(json,"beginyear");
                 beginmonth = ConvertObject.getString(json,"beginmonth");
                 endyear = ConvertObject.getString(json,"endyear");
                 endmonth = ConvertObject.getString(json,"endmonth");
             } catch (JSONException jsone) {
                 throw new JuzhaiAppException(jsone.getMessage() + ":" + json.toString(), jsone);
             }
         }
     }

     public String getBeginmonth() {
         return beginmonth;
     }

     public String getBeginyear() {
         return beginyear;
     }

     public String getCompany() {
         return company;
     }

     public String getDept() {
         return dept;
     }

     public String getEndmonth() {
         return endmonth;
     }

     public String getEndyear() {
         return endyear;
     }

     public String toString() {
         return "AppCareer{"
                 + "company='" + company + '\''
                 + ", dept='" + dept + '\''
                 + ", beginyear='" + beginyear + '\''
                 + ", beginmonth='" + beginmonth + '\''
                 + ", endyear='" + endyear + '\''
                 + ", endmonth='" + endmonth + '\''
                 + '}';
     }
 }
