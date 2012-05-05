package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.mapper.ActChartsMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActCharts;
import com.juzhai.act.model.ActChartsExample;
import com.juzhai.act.service.IActChartsService;
import com.juzhai.act.service.IActService;

//@Service
public class ActChartsService implements IActChartsService {

	@Autowired
	private ActChartsMapper actChartsMapper;
	@Autowired
	private IActService actService;

	@Override
	public Map<Integer, List<Act>> showActCharts() {
		ActChartsExample example = new ActChartsExample();
		example.setOrderByClause("sequence asc,id asc");
		List<ActCharts> actChartsList = actChartsMapper
				.selectByExample(example);
		Map<Integer, List<Act>> result = new HashMap<Integer, List<Act>>();
		List<Long> actIds = new ArrayList<Long>();
		for (ActCharts actCharts : actChartsList) {
			actIds.add(actCharts.getActId());
		}
		Map<Long, Act> actMap = actService.getMultiActByIds(actIds);
		for (ActCharts actCharts : actChartsList) {
			Act act = actMap.get(actCharts.getActId());
			if (null != act) {
				List<Act> actList = result.get(actCharts.getRoleCode());
				if (null == actList) {
					actList = new ArrayList<Act>();
					result.put(actCharts.getRoleCode(), actList);
				}
				actList.add(act);
			}
		}
		return result;
	}

}
