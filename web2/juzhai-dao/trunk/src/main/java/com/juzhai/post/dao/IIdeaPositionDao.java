package com.juzhai.post.dao;

public interface IIdeaPositionDao {

	int insert(long ideaId, double lon, double lat);

	int update(long ideaId, double lon, double lat);

	String getLocation(long ideaId);
}
