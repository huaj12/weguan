package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.AddRawActException;
import com.juzhai.act.model.RawAct;

public interface IRawActService {
	RawAct addRawAct(RawAct rawAct) throws AddRawActException;
	
	List<RawAct> getRawActs(int firstResult, int maxResults);
	
	RawAct getRawAct(long id);
	
	void delteRawAct(long id);
	
	int getRawActCount();
}
