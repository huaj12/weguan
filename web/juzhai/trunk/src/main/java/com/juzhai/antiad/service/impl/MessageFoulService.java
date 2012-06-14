package com.juzhai.antiad.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.home.mapper.DialogContentMapper;
import com.juzhai.home.model.DialogContent;
import com.juzhai.home.model.DialogContentExample;

@Service
public class MessageFoulService extends AbstractFoulservice {
	@Autowired
	private DialogContentMapper dialogContentMapper;

	@Override
	protected boolean contentIsFoul(String content, long sendUid) {
		// 判断内容是否重复
		if (StringUtils.isNotEmpty(content)) {
			DialogContentExample example = new DialogContentExample();
			example.createCriteria().andSenderUidEqualTo(sendUid);
			// 第一条是刚插入的数据。最近的第二条数据
			example.setLimit(new Limit(1, 1));
			example.setOrderByClause("create_time desc");
			List<DialogContent> list = dialogContentMapper
					.selectByExample(example);
			if (CollectionUtils.isNotEmpty(list)) {
				DialogContent dialogContent = list.get(0);
				if (content.trim().equals(dialogContent.getContent().trim())) {
					return true;
				}
			}
		}
		return false;
	}
}
