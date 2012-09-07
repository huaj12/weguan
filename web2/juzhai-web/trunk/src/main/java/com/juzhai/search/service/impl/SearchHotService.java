package com.juzhai.search.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.post.model.Post;
import com.juzhai.search.bean.LuceneResult;
import com.juzhai.search.exception.InputSearchHotException;
import com.juzhai.search.mapper.SearchHotMapper;
import com.juzhai.search.model.SearchHot;
import com.juzhai.search.model.SearchHotExample;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.search.service.ISearchHotService;

@Service
public class SearchHotService implements ISearchHotService {
	@Autowired
	private SearchHotMapper searchHotMapper;
	@Autowired
	private IPostSearchService postSearchService;

	@Override
	public List<SearchHot> getSearchHotByCity(long city, int count) {
		List<Long> citys = new ArrayList<Long>(2);
		citys.add(city);
		if (city != 0) {
			citys.add(0l);
		}
		SearchHotExample example = new SearchHotExample();
		example.createCriteria().andCityIn(citys);
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

	@Override
	public List<SearchHot> getSearchHotByCity(long city, int firstResult,
			int maxResults) {
		SearchHotExample example = new SearchHotExample();
		example.createCriteria().andCityEqualTo(city);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return searchHotMapper.selectByExample(example);
	}

	@Override
	public int countSearchHotByCity(long city) {
		SearchHotExample example = new SearchHotExample();
		example.createCriteria().andCityEqualTo(city);
		return searchHotMapper.countByExample(example);
	}

	@Override
	public void updateWordHot() {
		int count = 200;
		SearchHotExample example = new SearchHotExample();
		example.setOrderByClause("id desc");
		int i = 0;
		while (true) {
			example.setLimit(new Limit(i, count));
			List<SearchHot> list = searchHotMapper.selectByExample(example);
			setHot(list);
			if (list.size() < count) {
				break;
			}
			i += count;
		}
	}

	private void setHot(List<SearchHot> list) {
		for (SearchHot searchHot : list) {
			LuceneResult<Post> result = postSearchService.searchPosts(
					searchHot.getName(), null, searchHot.getCity(), 0, 0, 1);
			searchHot.setHot(result.getTotalHits());
			searchHotMapper.updateByPrimaryKeySelective(searchHot);
		}
	}
}
