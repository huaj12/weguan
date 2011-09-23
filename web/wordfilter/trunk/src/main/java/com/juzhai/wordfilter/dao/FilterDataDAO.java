/* 
 * FilterDataDAO.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.juzhai.wordfilter.core.SpamStruct;
import com.juzhai.wordfilter.web.util.AppReference;

/**
 * 过滤参考信息的dao访问类
 * 
 * @author xiaolin
 * 
 *         2008-3-3 create
 */
public class FilterDataDAO {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(FilterDataDAO.class);

	private NamedParameterJdbcTemplate dbTemplate;

	private AppReference appReference;

	/**
	 * 从数据库中获取所有垃圾词,以List<SpamStruct>的数据形式返回.
	 */
	public List<SpamStruct> getSpamWords() {
		String sql = "select word,weight from spam_word where weight>0";
		SqlRowSet result = dbTemplate.queryForRowSet(sql,
				(SqlParameterSource) null);

		List<SpamStruct> data = new ArrayList<SpamStruct>();
		while (result.next()) {
			String word = result.getString(1);
			int score = result.getInt(2);
			SpamStruct struct = new SpamStruct(word, score);
			data.add(struct);
		}
		return data;
	}

	/**
	 * 获取被屏蔽的用户列表，返回的数据形式:Set<Integer>。
	 */
	public Set<Integer> getSpamUsers() {
		String sql = "select userID from spam_user where release_time>now()";
		SqlRowSet result = dbTemplate.queryForRowSet(sql,
				(SqlParameterSource) null);

		Set<Integer> data = new HashSet<Integer>();
		while (result.next()) {
			int userid = result.getInt(1);
			data.add(userid);
		}
		return data;
	}

	/**
	 * 获取被屏蔽的ip，以Set<String>的数据形式返回
	 */
	public Set<String> getSpamIPs() {
		String sql = "select ip from spam_ip where release_time>NOW()";
		SqlRowSet result = dbTemplate.queryForRowSet(sql,
				(SqlParameterSource) null);

		Set<String> data = new HashSet<String>();
		while (result.next()) {
			String ip = result.getString(1);
			data.add(ip);
		}
		return data;
	}

	/**
	 * 获取被屏蔽的用户列表，返回的数据形式:Map<Integer,HashSet<Integer>>。
	 */
	public Map<Integer, HashSet<Integer>> getSpamUsersForApp() {
		String sql = "select userID,app from spam_user where release_time>now()";
		SqlRowSet result = dbTemplate.queryForRowSet(sql,
				(SqlParameterSource) null);

		Map<String, HashSet<Integer>> map = new HashMap<String, HashSet<Integer>>();

		while (result.next()) {
			int userid = result.getInt(1);
			String app = result.getString(2);

			processUser(app, userid, map);

		}
		return transformUser(map);
	}

	private void processUser(String appStr, int userId,
			Map<String, HashSet<Integer>> map) {
		if (appStr.length() > 0)
			appStr = appStr.substring(1, appStr.length() - 1);
		String[] apps = appStr.split("\\|");
		for (int i = 0; i < apps.length; i++) {
			HashSet<Integer> set = map.get(apps[i]);
			if (set == null) {
				set = new HashSet<Integer>();
				map.put(apps[i], set);
			}
			set.add(userId);
		}
	}

	/**
	 * transform for user data
	 * 
	 * @param map
	 * @return
	 */
	private Map<Integer, HashSet<Integer>> transformUser(
			Map<String, HashSet<Integer>> map) {
		if (map == null || map.size() == 0)
			return new HashMap<Integer, HashSet<Integer>>();

		Map<Integer, HashSet<Integer>> spamMap = new HashMap<Integer, HashSet<Integer>>();
		Iterator<String> apps = map.keySet().iterator();
		while (apps.hasNext()) {
			String app = apps.next();
			Integer id = appReference.getIdByAppName(app);
			if (id == AppReference.UNDEFINEDID)
				continue;
			spamMap.put(id, map.get(app));
		}

		return spamMap;
	}

	private Map<Integer, HashSet<String>> transformIp(
			Map<String, HashSet<String>> map) {
		if (map == null || map.size() == 0)
			return new HashMap<Integer, HashSet<String>>();

		Map<Integer, HashSet<String>> spamMap = new HashMap<Integer, HashSet<String>>();
		Iterator<String> apps = map.keySet().iterator();
		while (apps.hasNext()) {
			String app = apps.next();
			Integer id = appReference.getIdByAppName(app);
			if (id == AppReference.UNDEFINEDID)
				continue;
			spamMap.put(id, map.get(app));
		}

		return spamMap;
	}

	/**
	 * 获取被屏蔽的ip，以Map<Integer,HashSet<String>>的数据形式返回
	 */
	public Map<Integer, HashSet<String>> getSpamIPsForApp() {
		String sql = "select ip,app from spam_ip where release_time>NOW()";
		SqlRowSet result = dbTemplate.queryForRowSet(sql,
				(SqlParameterSource) null);

		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();

		while (result.next()) {
			String ip = result.getString(1);
			String app = result.getString(2);

			processIp(app, ip, map);
		}
		return transformIp(map);
	}

	private void processIp(String appStr, String ip,
			Map<String, HashSet<String>> map) {
		if (appStr.length() > 0)
			appStr = appStr.substring(1, appStr.length() - 1);
		String[] apps = appStr.split("\\|");
		for (int i = 0; i < apps.length; i++) {
			HashSet<String> set = map.get(apps[i]);
			if (set == null) {
				set = new HashSet<String>();
				map.put(apps[i], set);
			}
			set.add(ip);
		}
	}

	@SuppressWarnings("unchecked")
	public int insertUser(int userId, Date releaseTime) {
		String sql = "insert into spam_user(userID, start_time, release_time, app)"
				+ " values (:userID, :start_time, :release_time, :app) ON DUPLICATE KEY UPDATE release_time=:release_time";

		Map parameterMap = new HashMap();
		parameterMap.put("userID", userId);
		parameterMap.put("start_time", new Date());
		parameterMap.put("release_time", releaseTime);
		parameterMap.put("app", "app");

		return dbTemplate.update(sql, parameterMap);

	}

	@SuppressWarnings("unchecked")
	public int insertIp(String ip, Date releaseTime) {
		String sql = "insert into spam_ip(ip, start_time, release_time, app)"
				+ " values (:ip, :start_time, :release_time, :app) ON DUPLICATE KEY UPDATE release_time=:release_time";

		Map parameterMap = new HashMap();
		parameterMap.put("ip", ip);
		parameterMap.put("start_time", new Date());
		parameterMap.put("release_time", releaseTime);
		parameterMap.put("app", "app");

		return dbTemplate.update(sql, parameterMap);
	}

	public NamedParameterJdbcTemplate getDbTemplate() {
		return dbTemplate;
	}

	public void setDbTemplate(NamedParameterJdbcTemplate dbTemplate) {
		this.dbTemplate = dbTemplate;
	}

	public AppReference getAppReference() {
		return appReference;
	}

	public void setAppReference(AppReference appReference) {
		this.appReference = appReference;
	}
}