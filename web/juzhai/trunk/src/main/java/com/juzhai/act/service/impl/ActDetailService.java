package com.juzhai.act.service.impl;

import java.util.Date;

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
		ActDetail a=new ActDetail();
		a.setActId(actId);
//		a.setCreateTime(new Date());
		a.setDetail(detail);
//		a.setLastModifyTime(new Date());
		actDetailMapper.insert(a);
	}

}
