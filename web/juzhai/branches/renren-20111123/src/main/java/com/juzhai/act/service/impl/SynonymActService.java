package com.juzhai.act.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.SynonymAct;
import com.juzhai.act.model.SynonymActExample;
import com.juzhai.act.service.ISynonymActService;
import com.juzhai.core.dao.Limit;

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
		return synonymAct(actName, act.getId());
	}
	
	@Override
	public boolean synonymAct(String name, long actId) {
		if (StringUtils.isEmpty(name)) {
			log.error("synonymAct name is null");
			return false;
		}
		if (actId==0) {
			log.error("synonymAct actId is null");
			return false;
		}
		try {
			SynonymAct syn = new SynonymAct();
			syn.setActId(actId);
			syn.setCreateTime(new Date());
			syn.setLastModifyTime(new Date());
			syn.setName(name);
			synonymActMapper.insert(syn);
			// 将指向词加入内存
			InitData.SYNONYM_ACT.put(name, actId);
		} catch (Exception e) {
			log.error("synonymAct is error." + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<SynonymAct> getSysonymActs(int firstResult, int maxResults) {
		SynonymActExample example = new SynonymActExample();
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc");
		return synonymActMapper.selectByExample(example);
	}

	@Override
	public int countSysonymActs() {
		SynonymActExample example = new SynonymActExample();
		return synonymActMapper.countByExample(example);
	}

	@Override
	public boolean updateSynonymAct(Long id, long actId) {
		try {
			if (id == null) {
				return false;
			}
			SynonymAct synAct = synonymActMapper.selectByPrimaryKey(id);
			if (synAct == null) {
				return false;
			}
			if (actId == 0) {
				return false;
			}
			synAct.setActId(actId);
			synAct.setLastModifyTime(new Date());
			synonymActMapper.updateByPrimaryKey(synAct);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isExist(String name) {
		SynonymActExample example = new SynonymActExample();
		example.createCriteria().andNameEqualTo(name);
		if(synonymActMapper.countByExample(example)>0){
			return true;
		}
		return false;
	}

	

}
