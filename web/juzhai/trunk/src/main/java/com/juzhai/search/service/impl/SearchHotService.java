package com.juzhai.search.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<SearchHot> getSearchHotByCity(long city) {
		SearchHotExample example = new SearchHotExample();
		example.createCriteria().andCityEqualTo(city);
		return searchHotMapper.selectByExample(example);
	}

	@Override
	public void add(String name, long city) throws InputSearchHotException {
		if (StringUtils.isEmpty(name)) {
			throw new InputSearchHotException(
					InputSearchHotException.SEARCH_HOT_NAME_IS_NULL);
		}
		SearchHot searchHot = new SearchHot();
		searchHot.setCity(city);
		searchHot.setCreateTime(new Date());
		searchHot.setLastModifyTime(new Date());
		searchHot.setName(name);
		searchHotMapper.insertSelective(searchHot);
	}

	@Override
	public void delete(long id) throws InputSearchHotException {
		try {
			searchHotMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			throw new InputSearchHotException(
					InputSearchHotException.ILLEGAL_OPERATION);
		}
	}

}
