package com.juzhai.passport.service;

import com.juzhai.passport.exception.ReportAccountException;

public interface IReportRemoteService {

	/**
	 * 是否被屏蔽
	 * 
	 * @param uid
	 * @throws ReportAccountException
	 */
	long isShield(long uid);
}
