package com.juzhai.app.util;

import java.util.ArrayList;
import java.util.List;

import com.juzhai.app.model.AppUser;
import com.juzhai.core.exception.JuzhaiAppException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Deprecated
public class ConvertObject {
	public static List<AppUser> constructUser(JSONObject json) throws JuzhaiAppException {

        try {
            //			int next_cursor = json.getInt("next_cursor");
            //			int previous_cursor = json.getInt("previous_cursor");



            JSONArray list = json.getJSONArray("users");
            int size = list.size();
            List<AppUser> users = new ArrayList<AppUser>(size);
            for (int i = 0; i < size; i++) {
                users.add(new AppUser(list.getJSONObject(i)));
            }
            return users;
        } catch (JSONException je) {
        	throw new JuzhaiAppException(je.getMessage(), je);
        }

    }
	
	public static  String  getString(JSONObject json,String key){
		try{
		 return json.getString(key);
		}catch (Exception e) {
			return "";
		}
	}
	
	public static  long  getLong(JSONObject json,String key){
		try{
		 return json.getLong(key);
		}catch (Exception e) {
			return 0;
		}
	}
	public static  int  getInt(JSONObject json,String key){
		try{
		 return json.getInt(key);
		}catch (Exception e) {
			return 0;
		}
	}
	
}
