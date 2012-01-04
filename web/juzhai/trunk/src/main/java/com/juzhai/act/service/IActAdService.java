package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.ActAdInputException;
import com.juzhai.act.model.Act;

public interface IActAdService {
	void createActAd(String actName,long rawAdId) throws ActAdInputException;
	
	boolean isUrlExist(String url,long actId);
	
	void remove(long actId,long rawAdId)throws ActAdInputException;
	
	List<Act> getActByRawAd(long id);
	
}
