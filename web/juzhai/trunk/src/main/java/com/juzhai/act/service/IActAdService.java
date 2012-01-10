package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.ActAdInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActAd;

public interface IActAdService {
	void createActAd(String actName,long rawAdId) throws ActAdInputException;
	
	boolean isUrlExist(String url,long actId);
	
	void remove(long actAdId)throws ActAdInputException;
	
	List<Act> getActByRawAd(long id);
	
	List<ActAd> getActAds(long actId);
	
}
