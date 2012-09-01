package com.juzhai.post.service;

import com.juzhai.post.bean.Point;
import com.juzhai.post.model.Idea;

public interface IIdeaPositionService {
	/**
	 * 获取好主意坐标
	 * 
	 * @param ideaId
	 * @return
	 */
	Point getIdeaPoint(Long ideaId);

	/**
	 * 获取好主意坐标
	 * 
	 * @param idea
	 * @return
	 */
	Point getIdeaPoint(Idea idea);
}
