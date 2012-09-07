package com.juzhai.passport.service;

import java.util.List;

import com.juzhai.passport.bean.LockUserLevel;
import com.juzhai.passport.exception.InputReportException;
import com.juzhai.passport.model.Report;
import com.juzhai.plug.controller.form.ReportForm;

/**
 * 举报service
 * 
 * @author Administrator
 * 
 */
public interface IReportService extends IReportRemoteService {

	/**
	 * 保存用户举报信息
	 * 
	 * @param reportForm
	 * @param uid
	 * @throws InputReportException
	 */
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

	int countListReport(int type);

	/**
	 * 屏蔽某人
	 */
	void shieldUser(long id, Long uid, LockUserLevel lockUserLevel)
			throws InputReportException;

	/**
	 * 取消屏蔽
	 */
	void unShieldUser(Long uid);

	/**
	 * 忽略该举报
	 * 
	 * @param id
	 */
	void ignoreReport(long id);

	/**
	 * 删除举报
	 * 
	 * @param id
	 */
	void deleteReport(long id);

	/**
	 * 广告屏蔽
	 * 
	 * @param uid
	 * @param ip
	 */
	void adReport(long uid, String ip);

	// 某ip被屏蔽的次数
	int countIpReport(String ip);

}
