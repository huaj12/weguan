package com.juzhai.post.service;

import java.util.List;

import com.juzhai.post.exception.InputAdException;
import com.juzhai.post.model.Ad;

public interface IAdService {
	/**
	 * 根据城市获取优惠信息
	 * 
	 * @param city
	 * @return
	 */
	List<Ad> listAd(long city, int firstResult, int maxResults);

	/**
	 * 优惠信息数量
	 * 
	 * @param city
	 * @return
	 */
	int countAd(long city);

	/**
	 * 发布优惠信息
	 * 
	 * @param rawId
	 */
	void addAd(long rawId) throws InputAdException;

	/**
	 * 删除优惠信息
	 * 
	 * @param id
	 */
	void remove(long id);

	/**
	 * 删除所有已发布过期的优惠信息
	 */
	void removeAllExpiredAd();

}
