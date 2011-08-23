/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;

@Service
public class ActService implements IActService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ActMapper actMapper;
	@Autowired
	private InitData actInitData;

	@Override
	public void verifyAct(long rawActId, String actCategoryIds) {
		Act act = actMapper.selectByPrimaryKey(rawActId);
		if (null != act) {
			act.setId(rawActId);
			act.setCategoryIds(actCategoryIds);
			act.setLastModifyTime(new Date());
			act.setActive(true);
			actMapper.updateByPrimaryKeySelective(act);

			// 加载Act
			actInitData.loadAct(act);
		}
	}

	@Override
	public void replaceAct(long srcActId, long desActId) {
		// TODO 替换Act

	}

}
