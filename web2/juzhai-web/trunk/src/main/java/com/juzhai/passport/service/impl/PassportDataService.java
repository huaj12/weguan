package com.juzhai.passport.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IPassportDataRemoteService;

@Service
public class PassportDataService implements IPassportDataRemoteService {

	@Override
	public Map<Long, Thirdparty> getTpMap() {
		return InitData.TP_MAP;
	}

	@Override
	public Map<Long, Profession> getProfessionMap() {
		return InitData.PROFESSION_MAP;
	}

	@Override
	public Map<Long, Constellation> getConstellationMap() {
		return InitData.CONSTELLATION_MAP;
	}

}
