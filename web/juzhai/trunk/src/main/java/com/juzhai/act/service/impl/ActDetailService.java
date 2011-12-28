package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.juzhai.act.mapper.ActDetailMapper;
import com.juzhai.act.model.ActDetail;
import com.juzhai.act.service.IActDetailService;

@Service
public class ActDetailService implements IActDetailService {
	@Autowired
	private ActDetailMapper actDetailMapper;

	@Override
	public void addActDetail(long actId, String detail) {
		ActDetail a = new ActDetail();
		a.setActId(actId);
		List<String> list = matchImage(detail);
		if (CollectionUtils.isEmpty(list)) {
			a.setDisplay(false);
		} else {
			a.setDisplay(true);
		}
		a.setCreateTime(new Date());
		a.setDetail(detail);
		a.setLastModifyTime(new Date());
		actDetailMapper.insert(a);
	}

	@Override
	public ActDetail getActDetail(long actId) {
		return actDetailMapper.selectByPrimaryKey(actId);
	}

	private List<String> matchImage(String str) {
		List<String> imgList = new ArrayList<String>();
		String regEx = "<img.*?src=\"(.*?)\"";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		while (mat.find()) {
			imgList.add(mat.group(1));
		}
		return imgList;
	}

	@Override
	public void updateActDetail(long actId, String detail) {
		ActDetail a=new ActDetail();
		a.setActId(actId);
		List<String> list = matchImage(detail);
		if (CollectionUtils.isEmpty(list)) {
			a.setDisplay(false);
		} else {
			a.setDisplay(true);
		}
		a.setDetail(detail);
		a.setLastModifyTime(new Date());
		actDetailMapper.updateByPrimaryKeySelective(a);
	}

}
