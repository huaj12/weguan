package com.juzhai.passport.service;

import java.util.List;

import com.juzhai.passport.exception.InputReportException;
import com.juzhai.passport.model.Report;
import com.juzhai.plug.controller.form.ReportForm;

/**
 * 举报service
 * 
 * @author Administrator
 * 
 */
public interface IReportService {

	void save(ReportForm reportForm, long uid) throws InputReportException;

	/**
	 * 获取举报列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param type
	 * @return
	 */
	List<Report> listReport(int firstResult, int maxResults, int type);

	int listReportCount(int type);

	/**
	 * 屏蔽某人
	 */
	void shieldUser(long id, Long uid, long time);

	/**
	 * 取消屏蔽
	 */
	void unShieldUser(long id, Long uid);

	/**
	 * 忽略该举报
	 * 
	 * @param id
	 */
	void handleReport(long id);

	/**
	 * 删除举报
	 * 
	 * @param id
	 */
	void deleteReport(long id);

	/**
	 * 被封号用户的被举报信息
	 * 
	 * @param uid
	 * @return
	 */
	Report getUserReport(long uid);

}
