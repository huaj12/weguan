package com.juzhai.passport.dao;

public interface IUserPositionDao {

	int insert(long uid, double lon, double lat);

	int update(long uid, double lon, double lat);
}
