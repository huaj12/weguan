package com.juzhai.passport.service;

import java.util.Map;

import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Thirdparty;

public interface IPassportDataRemoteService {

	Map<Long, Thirdparty> getTpMap();

	Map<Long, Profession> getProfessionMap();

	Map<Long, Constellation> getConstellationMap();
}
