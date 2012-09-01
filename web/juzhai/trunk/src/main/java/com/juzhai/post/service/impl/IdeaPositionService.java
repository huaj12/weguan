package com.juzhai.post.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.post.bean.Point;
import com.juzhai.post.dao.IIdeaPositionDao;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaPositionService;
import com.juzhai.post.service.IMapService;

@Service
public class IdeaPositionService implements IIdeaPositionService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IIdeaPositionDao ideaPositionDao;
	@Autowired
	private IMapService mapService;

	@Override
	public Point getIdeaPoint(Idea idea) {
		if (idea == null) {
			return null;
		}
		String pointStr = ideaPositionDao.getLocation(idea.getId());
		Point point = getPoint(pointStr);
		if (point == null) {
			point = mapService.geocode(idea.getCity(), idea.getPlace());
			if (point != null) {
				ideaPositionDao.insert(idea.getId(), point.getLng(),
						point.getLat());
			}
		}
		return point;
	}

	private Point getPoint(String pointStr) {
		if (StringUtils.isEmpty(pointStr)) {
			return null;
		}
		Point point = null;
		try {
			pointStr = pointStr.substring(pointStr.indexOf("(") + 1,
					pointStr.indexOf(")"));
			String str[] = pointStr.split(" ");
			point = new Point();
			point.setLat(Double.valueOf(str[1]));
			point.setLng(Double.valueOf(str[0]));
		} catch (Exception e) {
			log.error("ideaDetail getPoint is error", e);
		}
		return point;
	}
}
