package com.juzhai.act.service;

import com.juzhai.act.model.ActDetail;

public interface IActDetailService {
	void addActDetail(long actId,String detail);
	
	void updateActDetail(long actId,String detail);
	
	ActDetail getActDetail(long actId);
	
}
