/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.passport.mapper.ThirdpartyMapper;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.ThirdpartyExample;

@Component("passportInitData")
@Lazy(false)
public class InitData {

	public static final Map<Long, Thirdparty> TP_MAP = new HashMap<Long, Thirdparty>();

	@Autowired
	private ThirdpartyMapper thirdpartyMapper;

	@PostConstruct
	public void init() {
		initTp();
	}

	private void initTp() {
		List<Thirdparty> list = thirdpartyMapper
				.selectByExample(new ThirdpartyExample());
		for (Thirdparty tp : list) {
			TP_MAP.put(tp.getId(), tp);
		}
	}
}
