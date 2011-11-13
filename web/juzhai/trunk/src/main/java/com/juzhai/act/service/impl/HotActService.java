package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.mapper.HotActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.HotAct;
import com.juzhai.act.model.HotActExample;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IHotActService;
import com.juzhai.core.dao.Limit;

@Service
public class HotActService implements IHotActService {

	@Autowired
	private HotActMapper hotActMapper;
	@Autowired
	private IActService actService;

	@Override
	public boolean activeHotAct(String actName) {
		Act act = actService.getActByName(actName);
		if (null != act) {
			activeHotAct(act.getId());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void activeHotAct(long actId) {
		if (actService.actExist(actId)) {
			HotAct hotAct = hotActMapper.selectByPrimaryKey(actId);
			if (null != hotAct) {
				if (!hotAct.getActive()) {
					hotAct.setLastModifyTime(new Date());
					hotAct.setActive(true);
					hotActMapper.updateByPrimaryKeySelective(hotAct);
				}
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

	@Override
	public void deleteHotAct(long actId) {
		if (actId > 0) {
			hotActMapper.deleteByPrimaryKey(actId);
		}
	}

	@Override
	public List<Act> listHotAct(boolean active, int firestResult, int maxResult) {
		HotActExample example = new HotActExample();
		example.createCriteria().andActiveEqualTo(active);
		example.setOrderByClause("last_modify_time desc");
		example.setLimit(new Limit(firestResult, maxResult));
		List<HotAct> hotActList = hotActMapper.selectByExample(example);
		List<Long> actIds = new ArrayList<Long>();
		for (HotAct hotAct : hotActList) {
			actIds.add(hotAct.getActId());
		}
		return actService.getActListByIds(actIds);
	}

	@Override
	public int countHotAct(boolean active) {
		HotActExample example = new HotActExample();
		example.createCriteria().andActiveEqualTo(active);
		return hotActMapper.countByExample(example);
	}

	@Override
	public boolean isExistHotAct(long actId) {
		HotActExample example = new HotActExample();
		example.createCriteria().andActIdEqualTo(actId);
		int count=hotActMapper.countByExample(example);
		if(count>0){
			return true;
		}
		return false;
	}
}
