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
		//TODO (review) 不要求顺序和数量？
		return searchHotMapper.selectByExample(example);
	}

	@Override
	public void add(String name, long city) throws InputSearchHotException {
		if (StringUtils.isEmpty(name)) {
			throw new InputSearchHotException(
					InputSearchHotException.SEARCH_HOT_NAME_IS_NULL);
		}
		//TODO (review) 加了唯一索引，代码为什么不判断是否能插入吗？
		SearchHot searchHot = new SearchHot();
		searchHot.setCity(city);
		searchHot.setCreateTime(new Date());
		//TODO (review) 上面有当前时间了吧，为什么不拿来用？
		searchHot.setLastModifyTime(new Date());
		searchHot.setName(name);
		searchHotMapper.insertSelective(searchHot);
	}

	@Override
	public void delete(long id) throws InputSearchHotException {
		try {
			searchHotMapper.deleteByPrimaryKey(id);
			//TODO (review) 这里会抛什么异常？
		} catch (Exception e) {
			throw new InputSearchHotException(
					InputSearchHotException.ILLEGAL_OPERATION);
		}
	}

}
