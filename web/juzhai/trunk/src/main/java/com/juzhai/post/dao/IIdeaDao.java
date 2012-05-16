package com.juzhai.post.dao;

import java.util.Date;
import java.util.Map;

public interface IIdeaDao {

	/**
	 * 增加或减少使用数
	 * 
	 * @param ideaId
	 * @param p
	 */
	void incrOrDecrUseCount(long ideaId, int p);

	/**
	 * 添加第一使用者
	 * 
	 * @param ideaId
	 * @param uid
	 */
	void addFirstUser(long ideaId, long uid);

	/**
	 * 最近流行的好主意
	 * 
	 * @param categoryId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Map<Long, Integer> getRecentPopIdeaId(long categoryId, Date startTime,
			Date endTime);
}
