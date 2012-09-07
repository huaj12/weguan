package com.juzhai.passport.dao;

public interface IUserGuideDao {

	int next(long uid);

	int complete(long uid);
}
