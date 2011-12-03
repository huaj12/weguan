package com.juzhai.cms.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juzhai.act.InitData;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.SynonymAct;
import com.juzhai.act.service.IUserActService;
import com.juzhai.act.service.impl.ActService;
import com.juzhai.cms.mapper.AddActActionMapper;
import com.juzhai.cms.mapper.SearchActActionMapper;
import com.juzhai.cms.model.AddActAction;
import com.juzhai.cms.model.AddActActionExample;
import com.juzhai.cms.model.SearchActAction;
import com.juzhai.cms.model.SearchActActionExample;
import com.juzhai.cms.service.IUserActionService;
import com.juzhai.core.dao.Limit;

@Service
public class UserActionService implements IUserActionService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private SearchActActionMapper searchActActionMapper;

	@Autowired
	private AddActActionMapper addActActionMapper;

	@Value("${search.act.max.rows}")
	private int maxRows;
	@Value("${search.act.max.num}")
	private int searchMaxNum;
	@Value("${add.act.max.num}")
	private int addMaxNum;

	@Override
	public List<SearchActAction> getSearchActAction(int firstResult,
			int maxResults) {
		SearchActActionExample example = new SearchActActionExample();
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		return searchActActionMapper.selectByExample(example);
	}

	@Override
	public List<AddActAction> getAddActAction(int firstResult, int maxResults) {
		AddActActionExample example = new AddActActionExample();
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		return addActActionMapper.selectByExample(example);
	}

	@Override
	public int getSearchActActionCount() {
		return searchActActionMapper
				.countByExample(new SearchActActionExample());
	}

	@Override
	public int getAddActActionCount() {
		return addActActionMapper.countByExample(new AddActActionExample());
	}

	@Override
	public AddActAction createAddActAction(String name, long uid,
			String userName) {
		if (StringUtils.isEmpty(userName)) {
			log.equals("createAddActAction userName is null");
			return null;
		}
		if (StringUtils.isEmpty(name)) {
			log.equals("createAddActAction name is null");
			return null;
		}
		if (uid == 0) {
			log.equals("createAddActAction uid is null");
			return null;
		}
		AddActAction record = null;
		if (isExistAddActAction(uid, name)) {
			record = getAddActAction(name, uid);
			record.setCreateTime(new Date());
			addActActionMapper.updateByPrimaryKey(record);
		} else {
			record = new AddActAction();
			record.setCreateTime(new Date());
			record.setName(name);
			record.setUserId(uid);
			record.setUserName(userName);
			addActActionMapper.insert(record);
		}
		return record;
	}

	@Override
	public SearchActAction createSearchActAction(String name, long uid,
			String userName) {
		if (StringUtils.isEmpty(userName)) {
			log.equals("createSearchActAction userName is null");
			return null;
		}
		if (StringUtils.isEmpty(name)) {
			log.equals("createSearchActAction name is null");
			return null;
		}
		if (uid == 0) {
			log.equals("createSearchActAction uid is null");
			return null;
		}
		SearchActAction record = null;
		if (isExistSearchActAction(uid, name)) {
			record = getSearchActAction(name, uid);
			record.setCreateTime(new Date());
			searchActActionMapper.updateByPrimaryKey(record);
		} else {
			record = new SearchActAction();
			record.setCreateTime(new Date());
			record.setName(name);
			record.setUserId(uid);
			record.setUserName(userName);
			searchActActionMapper.insert(record);
		}
		return record;
	}

	@Override
	public List<SearchActAction> searchAutoMatch(String name) {
		if (StringUtils.isEmpty(name)) {
			return Collections.emptyList();
		}
		SearchActActionExample example = new SearchActActionExample();
		example.createCriteria().andNameLike(name);
		example.setLimit(new Limit(0, maxRows));
		return searchActActionMapper.selectByExample(example);
	}

	@Override
	public boolean addActActionMaximum(long uid) {
		if (uid == 0) {
			return false;
		}
		AddActActionExample example = new AddActActionExample();
		example.createCriteria().andUserIdEqualTo(uid);
		if (addActActionMapper.countByExample(example) <= addMaxNum) {
			return true;
		}
		return false;
	}

	@Override
	public boolean searchActActionMaximum(long uid) {
		if (uid == 0) {
			return false;
		}
		SearchActActionExample example = new SearchActActionExample();
		example.createCriteria().andUserIdEqualTo(uid);
		if (searchActActionMapper.countByExample(example) <= searchMaxNum) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistAddActAction(long uid, String name) {
		AddActActionExample example = new AddActActionExample();
		example.createCriteria().andUserIdEqualTo(uid).andNameEqualTo(name);
		if (addActActionMapper.countByExample(example) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistSearchActAction(long uid, String name) {
		SearchActActionExample example = new SearchActActionExample();
		example.createCriteria().andUserIdEqualTo(uid).andNameEqualTo(name);
		if (searchActActionMapper.countByExample(example) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public AddActAction getAddActAction(String name, long uid) {
		AddActActionExample example = new AddActActionExample();
		example.createCriteria().andUserIdEqualTo(uid).andNameEqualTo(name);
		List<AddActAction> list = addActActionMapper.selectByExample(example);
		if (list != null && list.get(0) != null) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public SearchActAction getSearchActAction(String name, long uid) {
		SearchActActionExample example = new SearchActActionExample();
		example.createCriteria().andUserIdEqualTo(uid).andNameEqualTo(name);
		List<SearchActAction> list = searchActActionMapper
				.selectByExample(example);
		if (list != null && list.get(0) != null) {
			return list.get(0);
		} else {
			return null;
		}

	}

}
