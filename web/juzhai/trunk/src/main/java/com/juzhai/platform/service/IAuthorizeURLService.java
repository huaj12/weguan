package com.juzhai.platform.service;

import com.juzhai.passport.model.Thirdparty;

public interface IAuthorizeURLService {
	String getAuthorizeURLforCode(Thirdparty tp);
}
