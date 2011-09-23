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
 * ����δͨ������У���
 * 
 * @author xiaolin
 * 
 *         2008-3-5 create
 */
public class FilterLogDAO {
	private static final Logger logger = Logger.getLogger(FilterLogDAO.class);

	private PreparedStatement ps; // ��Ԥ���������Ϊȫ�ֱ�������Ϊ�˽��仺�棬����Ƶ���Ĵ���.
	private DataSource dataSource = null;

	private Connection connection = null;

	/**
	 * ��������δͨ�����˵��û�������Ϣ��
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
			logger.error("��ΪPreparedStatementδ��ʼ�������β��뽫��ʧ����,��ʧ������"
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
				logger.info("���β�������:" + dataList.size() + "��");
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
	 * ��ȡԤ�������������Ӧ�ó�����ֻ����һ��ʵ����
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
			logger.error("�ر�statement����:" + e.getMessage(), e);
		}
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			logger.error("�ر����Ӵ���:" + e.getMessage(), e);
		}
	}
}
