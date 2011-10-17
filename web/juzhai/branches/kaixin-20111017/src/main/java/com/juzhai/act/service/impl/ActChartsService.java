package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.mapper.ActChartsMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActCharts;
import com.juzhai.act.model.ActChartsExample;
import com.juzhai.act.service.IActChartsService;

@Service
public class ActChartsService implements IActChartsService {

	@Autowired
	private ActChartsMapper actChartsMapper;

	@Override
	public Map<Integer, List<Act>> showActCharts() {
		ActChartsExample example = new ActChartsExample();
		example.setOrderByClause("sequence asc,id asc");
		List<ActCharts> actChartsList = actChartsMapper
				.selectByExample(example);
		Map<Integer, List<Act>> result = new HashMap<Integer, List<Act>>();
		for (ActCharts actCharts : actChartsList) {
			Act act = InitData.ACT_MAP.get(actCharts.getActId());
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
