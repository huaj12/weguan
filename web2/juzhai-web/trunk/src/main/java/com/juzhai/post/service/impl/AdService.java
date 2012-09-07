package com.juzhai.post.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.cms.mapper.RawAdMapper;
import com.juzhai.cms.model.RawAd;
import com.juzhai.core.dao.Limit;
import com.juzhai.post.exception.InputAdException;
import com.juzhai.post.mapper.AdMapper;
import com.juzhai.post.model.Ad;
import com.juzhai.post.model.AdExample;
import com.juzhai.post.service.IAdService;

@Service
public class AdService implements IAdService {
	@Autowired
	private AdMapper adMapper;
	@Autowired
	private RawAdMapper rawAdMapper;

	@Override
	public List<Ad> listAd(long city, int firstResult, int maxResults) {
		AdExample example = getAdExample(city);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		return adMapper.selectByExample(example);
	}

	@Override
	public int countAd(long city) {
		return adMapper.countByExample(getAdExample(city));
	}

	private AdExample getAdExample(long city) {
		AdExample example = new AdExample();
		if (city > 0) {
			example.createCriteria().andCityEqualTo(city);
		}
		return example;
	}

	@Override
	public void addAd(long rawId) throws InputAdException {
		RawAd rawAd = rawAdMapper.selectByPrimaryKey(rawId);
		if (rawAd == null) {
			throw new InputAdException(InputAdException.ILLEGAL_OPERATION);
		}
		if (isLinkExist(rawAd.getMd5TargetUrl())) {
			throw new InputAdException(InputAdException.AD_IS_EXIST);
		}
		Ad ad = new Ad();
		ad.setAddress(rawAd.getAddress());
		ad.setCity(rawAd.getCity());
		ad.setCreateTime(new Date());
		ad.setDiscount(rawAd.getDiscount());
		ad.setDistrict(rawAd.getCircle());
		ad.setEndTime(rawAd.getEndDate());
		ad.setLastModifyTime(new Date());
		ad.setLink(rawAd.getTargetUrl());
		ad.setName(rawAd.getTitle());
		ad.setPicUrl(rawAd.getImg());
		ad.setPrice(rawAd.getPrice());
		ad.setPrimePrice(rawAd.getOriginal());
		ad.setSource(rawAd.getSource());
		ad.setSourceLink(getDomain(rawAd.getTargetUrl()));
		ad.setStartTime(rawAd.getStartDate());
		ad.setMd5Link(rawAd.getMd5TargetUrl());
		adMapper.insertSelective(ad);

	}

	private boolean isLinkExist(String md5Link) {
		AdExample example = new AdExample();
		example.createCriteria().andMd5LinkEqualTo(md5Link);
		return adMapper.countByExample(example) > 0 ? true : false;
	}

	private String getDomain(String url) {
		Pattern p = Pattern.compile(
				"(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}

	@Override
	public void remove(long id) {
		adMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void removeAllExpiredAd() {
		AdExample example = new AdExample();
		example.createCriteria().andEndTimeLessThan(new Date());
		List<Ad> ads = adMapper.selectByExample(example);
		for (Ad ad : ads) {
			adMapper.deleteByPrimaryKey(ad.getId());
		}

	}

}
