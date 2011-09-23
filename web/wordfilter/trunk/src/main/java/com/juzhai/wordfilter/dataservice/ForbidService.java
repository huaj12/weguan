package com.juzhai.wordfilter.dataservice;

import java.util.Date;

import org.apache.log4j.Logger;

import com.juzhai.wordfilter.core.Config;
import com.juzhai.wordfilter.core.Filter;
import com.juzhai.wordfilter.dao.FilterDataDAO;
import com.juzhai.wordfilter.web.util.DateUtil;
import com.juzhai.wordfilter.web.util.StringUtil;

public class ForbidService {
	private static final Logger logger = Logger.getLogger(ForbidService.class);

	private FilterDataDAO filterDataDAO;

	@SuppressWarnings("deprecation")
	public int forbidUser(String userId, String releaseTime) {
		if (logger.isInfoEnabled()) {
			logger.info("forbid user : " + userId + "\treleasetime: "
					+ releaseTime);
		}

		try {
			int user = StringUtil.stringToInt(userId, -1);

			if (user == -1)
				return Config.RET_UserForbidden_Failed;

			Date time = DateUtil.stringToDate(releaseTime,
					DateUtil.DATETIME_FORMAT);

			int ret = filterDataDAO.insertUser(user, time);

			if (ret <= 0)
				return Config.RET_UserForbidden_Failed;

			Filter.getInstance().addSpamUser(user);

			return Config.RET_UserForbidden_OK;
		} catch (Exception e) {
			return Config.RET_UserForbidden_Failed;
		}

	}

	@SuppressWarnings("deprecation")
	public int forbidIp(String ip, String releaseTime) {
		if (logger.isInfoEnabled()) {
			logger.info("forbid ip : " + ip + "\treleasetime: " + releaseTime);
		}
		try {
			if (ip == null || ip.length() == 0)
				return Config.RET_IpForbidend_Failed;

			ip = ip.trim();

			Date time = DateUtil.stringToDate(releaseTime,
					DateUtil.DATETIME_FORMAT);

			int ret = filterDataDAO.insertIp(ip, time);

			if (ret <= 0)
				return Config.RET_IpForbidend_Failed;

			Filter.getInstance().addSpamIp(ip);

			return Config.RET_IpForbidend_OK;
		} catch (Exception e) {
			return Config.RET_IpForbidend_Failed;
		}
	}

	public FilterDataDAO getFilterDataDAO() {
		return filterDataDAO;
	}

	public void setFilterDataDAO(FilterDataDAO filterDataDAO) {
		this.filterDataDAO = filterDataDAO;
	}

}
