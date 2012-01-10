package com.juzhai.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.cms.mapper.SpiderUrlMapper;
import com.juzhai.cms.model.SpiderUrlExample;
import com.juzhai.cms.service.ISpiderUrlService;

@Service
public class SpiderUrlService implements ISpiderUrlService {
	@Autowired
	private SpiderUrlMapper spiderUrlMapper;

	@Override
	public boolean isUrlExist(String md5Url) {
		SpiderUrlExample example=new SpiderUrlExample();
		example.createCriteria().andMd5UrlEqualTo(md5Url);
		return spiderUrlMapper.countByExample(example)>0?true:false;
	}

}
