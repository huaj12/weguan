package com.juzhai.cms.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.cms.mapper.RawAdMapper;
import com.juzhai.cms.model.RawAd;
import com.juzhai.cms.model.RawAdExample;
import com.juzhai.core.schedule.AbstractScheduleHandler;
@Component
public class DeleteExpiredRawAdHandler extends AbstractScheduleHandler {
	@Autowired
	private RawAdMapper rawAdMapper;
	@Override
	protected void doHandle() {
		try {
		RawAdExample example=new RawAdExample();
		example.createCriteria().andEndDateLessThan(new Date());
		List<RawAd> list=rawAdMapper.selectByExample(example);
		for(RawAd rawAd:list){
			rawAdMapper.deleteByPrimaryKey(rawAd.getId());
		}
		} catch (Exception e) {
			log.error("delete expired raw ad handler is error");
		}
	}

}
