/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.mapper.ConstellationMapper;
import com.juzhai.passport.mapper.ProfessionMapper;
import com.juzhai.passport.mapper.ThirdpartyMapper;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.ConstellationExample;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.ProfessionExample;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.ThirdpartyExample;

@Component("passportInitData")
@Lazy(false)
public class InitData {

	public static final Map<Long, Thirdparty> TP_MAP = new HashMap<Long, Thirdparty>();
	public static final Map<Long, Profession> PROFESSION_MAP = new LinkedHashMap<Long, Profession>();
	public static final Map<Long, Constellation> CONSTELLATION_MAP = new HashMap<Long, Constellation>();
	@Autowired
	private ThirdpartyMapper thirdpartyMapper;
	@Autowired
	private ProfessionMapper professionMapper;
	@Autowired
	private ConstellationMapper constellationMapper;

	@PostConstruct
	public void init() {
		initTp();
		// initGuideSteps();
		initConstellation();
		initProfession();
	}

	// 职业
	private void initProfession() {
		List<Profession> list = professionMapper
				.selectByExample(new ProfessionExample());
		for (Profession profession : list) {
			PROFESSION_MAP.put(profession.getId(), profession);
		}
	}

	private void initConstellation() {
		List<Constellation> list = constellationMapper
				.selectByExample(new ConstellationExample());
		for (Constellation constellation : list) {
			CONSTELLATION_MAP.put(constellation.getId(), constellation);
		}
	}

	private void initTp() {
		List<Thirdparty> list = thirdpartyMapper
				.selectByExample(new ThirdpartyExample());
		for (Thirdparty tp : list) {
			TP_MAP.put(tp.getId(), tp);
		}
	}

	public static Thirdparty getTpByTpNameAndJoinType(String name,
			JoinTypeEnum joinType) {
		for (Thirdparty tp : TP_MAP.values()) {
			if (StringUtils.equals(tp.getName(), name)
					&& StringUtils.equals(tp.getJoinType(), joinType.getName())) {
				return tp;
			}
		}
		return null;
	}

	public static Constellation getConstellation(int month, int date) {
		if (month <= 0 || date <= 0) {
			return null;
		}
		for (Constellation c : CONSTELLATION_MAP.values()) {
			if (c.getStartMonth() + 1 == month
					|| (c.getStartMonth() == 12 && month == 1)
					|| (c.getStartMonth() == month && c.getStartDate() <= date)) {
				if (c.getEndMonth() == month + 1
						|| (c.getEndMonth() == 1 && month == 12)
						|| (c.getEndMonth() == month && c.getEndDate() >= date)) {
					return c;
				}
			}
		}
		return null;
	}
}
