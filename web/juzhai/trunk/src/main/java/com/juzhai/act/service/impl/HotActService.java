package com.juzhai.act.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.mapper.HotActMapper;
import com.juzhai.act.model.HotAct;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IHotActService;

@Service
public class HotActService implements IHotActService {

	@Autowired
	private HotActMapper hotActMapper;
	@Autowired
	private IActService actService;

	@Override
	public void activeHotAct(long actId) {
		if (actService.actExist(actId)) {
			HotAct hotAct = hotActMapper.selectByPrimaryKey(actId);
			if (null != hotAct && !hotAct.getActive()) {
				hotAct.setLastModifyTime(new Date());
				hotAct.setActive(true);
				hotActMapper.updateByPrimaryKeySelective(hotAct);
			} else {
				hotAct = new HotAct();
				hotAct.setActId(actId);
				hotAct.setActive(true);
				hotAct.setCreateTime(new Date());
				hotAct.setLastModifyTime(hotAct.getCreateTime());
				hotActMapper.insertSelective(hotAct);
			}
		}
	}

	@Override
	public void cancelHotAct(long actId) {
		if (actId > 0) {
			HotAct hotAct = new HotAct();
			hotAct.setActId(actId);
			hotAct.setActive(false);
			hotAct.setLastModifyTime(new Date());
			hotActMapper.updateByPrimaryKeySelective(hotAct);
		}
	}

}
