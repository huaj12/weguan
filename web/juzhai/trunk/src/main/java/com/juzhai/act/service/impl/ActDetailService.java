package com.juzhai.act.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.juzhai.act.mapper.ActDetailMapper;
import com.juzhai.act.model.ActDetail;
import com.juzhai.act.service.IActDetailService;

@Service
public class ActDetailService implements IActDetailService {
	@Autowired
	private ActDetailMapper actDetailMapper;

	@Override
	public void addActDetail(long actId, String detail) {
		ActDetail a = new ActDetail();
		a.setActId(actId);
		// TODO (review) display字段用于判断项目页是否显示富文本？判断条件问max或者看demo
		a.setDisplay(false);
		// a.setCreateTime(new Date());
		a.setDetail(detail);
		// a.setLastModifyTime(new Date());
		actDetailMapper.insert(a);
	}

	@Override
	public ActDetail getActDetail(long actId) {
		return actDetailMapper.selectByPrimaryKey(actId);
	}

}
