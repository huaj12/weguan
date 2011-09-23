/* 
 * FilterLogDAO.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.juzhai.wordfilter.web.util.DateUtil;

/**
 * 对于未通过过滤校验的
 * 
 * @author xiaolin
 * 
 *         2008-3-5 create
 */
public class FilterLogDAO {
	private static final Logger logger = Logger.getLogger(FilterLogDAO.class);

	private PreparedStatement ps; // 将预编译对象作为全局变量，是为了将其缓存，避免频繁的创建.
	private DataSource dataSource = null;

	private Connection connection = null;

	/**
	 * 批量插入未通过过滤的用户操作信息。
	 */
	public void batchUpdate(List<Object[]> dataList) throws SQLException {
		if (dataList == null || dataList.size() == 0)
			return;
		try {
			getCachedStatement();
		} catch (SQLException e) {
			throw e;
		}

		if (ps == null) {
			logger.error("因为PreparedStatement未初始化，本次插入将丢失数据,丢失数量："
					+ dataList.size());
			return;
		}

		try {
			for (Object[] obj : dataList) {
				ps.setString(1, (String) obj[0]); // app
				ps.setInt(2, (Integer) obj[1]); // uid
				ps.setString(3, (String) obj[2]); // ip
				ps.setString(4, (String) obj[3]); // agent
				ps.setString(5, (String) obj[4]); // txt
				ps.setInt(6, (Integer) obj[5]); // mode
				ps.setInt(7, (Integer) obj[6]); // result
				ps.setInt(8, (Integer) obj[7]); // manual
				ps.setString(9, DateUtil.dateToString(new Date())); // add_time

				ps.addBatch();
			}
			ps.executeBatch();

			if (logger.isInfoEnabled()) {
				logger.info("本次插入数据:" + dataList.size() + "条");
			}
		} finally {
			dispose();
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 获取预编译对象，在整个应用程序中只存在一个实例。
	 */
	private void getCachedStatement() throws SQLException {
		String sql = "insert into spam_checklog (app,uid,ip,agt,txt,mode,result,manual,add_time) values(?,?,?,?,?,?,?,?,?)";
		connection = dataSource.getConnection();
		ps = connection.prepareStatement(sql);
	}

	private void dispose() {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			logger.error("关闭statement错误:" + e.getMessage(), e);
		}
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			logger.error("关闭连接错误:" + e.getMessage(), e);
		}
	}
}
