package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juzhai.act.exception.ActAdInputException;
import com.juzhai.act.mapper.ActAdMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActAd;
import com.juzhai.act.model.ActAdExample;
import com.juzhai.act.service.IActAdService;
import com.juzhai.act.service.IActService;
import com.juzhai.cms.model.RawAd;
import com.juzhai.cms.service.IRawAdService;

@Service
public class ActAdService implements IActAdService {
	@Autowired
	private IActService actService;
	@Autowired
	private IRawAdService rawAdService;
	@Autowired
	private ActAdMapper actAdMapper;

	@Override
	@Transactional
	public void createActAd(String actName, long rawAdId)
			throws ActAdInputException {
		Act act = actService.getActByName(actName);
		if (act == null) {
			throw new ActAdInputException(
					ActAdInputException.ACT_AD_NAME_IS_NOT_EXIST);
		}
		RawAd rawAd = rawAdService.getRawAd(rawAdId);
		if (rawAd == null) {
			throw new ActAdInputException(
					ActAdInputException.ACT_AD_RAW_AD_ID_IS_NULL);
		}
		if (isUrlExist(rawAd.getTargetUrl(), act.getId())) {
			throw new ActAdInputException(
					ActAdInputException.ACT_AD_URL_IS_EXIST);
		}
		try {
			ActAd actAd = rawAdIntoActAd(rawAd);
			int sequence = getAdCount(act.getId());
			actAd.setSequence((sequence + 1));
			actAd.setActId(act.getId());
			actAdMapper.insert(actAd);
			String actIds = rawAd.getActIds();
			if (StringUtils.isEmpty(actIds)) {
				rawAd.setActIds(String.valueOf(act.getId()));
			} else {
				String[] ids = actIds.split(",");
				List<String> list = new ArrayList<String>();
				for (String id : ids) {
					list.add(id);
				}
				list.add(String.valueOf(act.getId()));
				rawAd.setActIds(StringUtils.join(list, ","));
			}
			rawAd.setStatus(1);
			rawAdService.updateRawAd(rawAd);
		} catch (Exception e) {
			throw new ActAdInputException(
					ActAdInputException.CREATE_ACT_AD_IS_ERROR);
		}

	}

	private int getAdCount(long actId) {
		ActAdExample example = new ActAdExample();
		example.createCriteria().andActIdEqualTo(actId);
		return actAdMapper.countByExample(example);
	}

	private ActAd rawAdIntoActAd(RawAd rawAd) {
		ActAd ad = new ActAd();
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
		return ad;

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
	public boolean isUrlExist(String url, long actId) {
		ActAdExample example = new ActAdExample();
		example.createCriteria().andLinkEqualTo(url).andActIdEqualTo(actId);
		return actAdMapper.countByExample(example) > 0 ? true : false;
	}

	@Override
	@Transactional
	public void remove(long actId, long rawAdId) throws ActAdInputException {
		RawAd rawAd = rawAdService.getRawAd(rawAdId);
		if (rawAd == null) {
			throw new ActAdInputException(
					ActAdInputException.REMOVE_ACT_AD_IS_NOT_EXIST);
		}
		ActAdExample example = new ActAdExample();
		example.createCriteria().andLinkEqualTo(rawAd.getTargetUrl())
				.andActIdEqualTo(actId);
		List<ActAd> lists = actAdMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(lists)) {
			throw new ActAdInputException(
					ActAdInputException.REMOVE_ACT_AD_IS_NOT_EXIST);
		}
		try {
			ActAd ad = lists.get(0);
			actAdMapper.deleteByPrimaryKey(ad.getId());
			String actIds = rawAd.getActIds();
			String[] ids = actIds.split(",");
			List<String> newIds = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				if (!String.valueOf(actId).equals(ids[i])) {
					newIds.add(ids[i]);
				}
			}
			if (CollectionUtils.isEmpty(newIds)) {
				rawAd.setStatus(0);
			}
			rawAd.setActIds(StringUtils.join(newIds, ","));
			rawAdService.updateRawAd(rawAd);
		} catch (Exception e) {
			throw new ActAdInputException(
					ActAdInputException.REMOVE_ACT_AD_IS_ERROR);
		}
	}

	@Override
	public List<Act> getActByRawAd(long rawAdId) {
		List<Act> list = new ArrayList<Act>();
		RawAd rawAd = rawAdService.getRawAd(rawAdId);
		if (rawAd == null) {
			return null;
		}
		String actIds = rawAd.getActIds();
		if (StringUtils.isEmpty(actIds)) {
			return null;
		}
		String[] ids = actIds.split(",");
		for (String id : ids) {
			long actId = 0;
			try {
				actId = Long.valueOf(id);
			} catch (Exception e) {
			}
			Act act = actService.getActById(actId);
			if (act != null) {
				list.add(act);
			}
		}
		return list;
	}

}
