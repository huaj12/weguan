package com.juzhai.search.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.search.exception.InputSearchHotException;
import com.juzhai.search.mapper.SearchHotMapper;
import com.juzhai.search.model.SearchHot;
import com.juzhai.search.model.SearchHotExample;
import com.juzhai.search.service.ISearchHotService;

@Service
public class SearchHotService implements ISearchHotService {
	@Autowired
	private SearchHotMapper searchHotMapper;

	@Override
	public List<SearchHot> getSearchHotByCity(long city, int count) {
		SearchHotExample example = new SearchHotExample();
		example.createCriteria().andCityEqualTo(city);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(0, count));
		return searchHotMapper.selectByExample(example);
	}

	@Override
	public void add(String name, long city) throws InputSearchHotException {
		if (StringUtils.isEmpty(name)) {
			throw new InputSearchHotException(
					InputSearchHotException.SEARCH_HOT_NAME_IS_NULL);
		}
		if (isExist(name, city)) {
			throw new InputSearchHotException(
					InputSearchHotException.SEARCH_HOT_NAME_CITY_IS_EXIST);
		}

		SearchHot searchHot = new SearchHot();
		searchHot.setCity(city);
		searchHot.setCreateTime(new Date());
		searchHot.setLastModifyTime(searchHot.getCreateTime());
		searchHot.setName(name);
		searchHotMapper.insertSelective(searchHot);
	}

	@Override
	public void delete(long id) {
		searchHotMapper.deleteByPrimaryKey(id);
	}

	@Override
	public boolean isExist(String name, long city) {
		SearchHotExample example = new SearchHotExample();
		example.createCriteria().andCityEqualTo(city).andNameEqualTo(name);
		return searchHotMapper.countByExample(example) > 0 ? true : false;
	}
}
