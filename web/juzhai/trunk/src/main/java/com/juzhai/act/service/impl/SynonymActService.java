package com.juzhai.act.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.SynonymAct;
import com.juzhai.act.service.ISynonymActService;

@Service
public class SynonymActService implements ISynonymActService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private SynonymActMapper synonymActMapper;
	@Autowired
	private ActService actService;

	@Override
	public boolean synonymAct(String name, String actName) {
		if (StringUtils.isEmpty(name)) {
			log.error("synonymAct name is null");
			return false;
		}
		Act act = actService.getActByName(actName);
		if (act == null) {
			log.error("synonymAct actName is not exist");
			return false;
		}
		try {
			SynonymAct syn = new SynonymAct();
			syn.setActId(act.getId());
			syn.setCreateTime(new Date());
			syn.setLastModifyTime(new Date());
			syn.setName(name);
			synonymActMapper.insert(syn);
			// 将指向词加入内存
			InitData.SYNONYM_ACT.put(name, act.getId());
		} catch (Exception e) {
			log.error("synonymAct is error." + e.getMessage());
			return false;
		}
		return true;
	}

}
