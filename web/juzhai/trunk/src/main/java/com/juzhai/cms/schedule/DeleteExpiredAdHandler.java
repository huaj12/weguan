package com.juzhai.cms.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.act.mapper.ActAdMapper;
import com.juzhai.act.model.ActAd;
import com.juzhai.act.model.ActAdExample;
import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class DeleteExpiredAdHandler extends AbstractScheduleHandler {
	@Autowired
	private ActAdMapper actAdMapper;
	private final Log log = LogFactory.getLog(getClass());

	@Override
	protected void doHandle() {
		try {
			ActAdExample example = new ActAdExample();
			example.createCriteria().andEndTimeLessThan(new Date());
			List<ActAd> actAds = actAdMapper.selectByExample(example);
			for (ActAd ad : actAds) {
				actAdMapper.deleteByPrimaryKey(ad.getId());
			}
		} catch (Exception e) {
			log.error("delete Expired Ad Handler is error");
		}
	}

}
