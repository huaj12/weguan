package com.juzhai.notice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.notice.bean.SysNoticeType;
import com.juzhai.notice.mapper.SysNoticeMapper;
import com.juzhai.notice.model.SysNotice;
import com.juzhai.notice.model.SysNoticeExample;
import com.juzhai.notice.service.ISysNoticeService;

@Service
public class SysNoticeService implements ISysNoticeService {

	@Autowired
	private SysNoticeMapper sysNoticeMapper;
	@Autowired
	private MessageSource messageSource;

	// @Autowired
	// private NoticeService noticeService;

	@Override
	public void sendSysNotice(long uid, SysNoticeType sysNoticeType,
			Object... params) {
		SysNotice sysNotice = new SysNotice();
		sysNotice.setUid(uid);
		sysNotice.setCreateTime(new Date());
		sysNotice.setLastModifyTime(sysNotice.getCreateTime());
		sysNotice.setContent(messageSource.getMessage(sysNoticeType.getName(),
				params, null));
		sysNoticeMapper.insertSelective(sysNotice);
//		noticeService.incrNotice(uid, NoticeType.SYS_NOTICE);
	}

	@Override
	public List<SysNotice> listSysNoticeByUid(long uid, int firstResult,
			int maxResults) {
		SysNoticeExample example = new SysNoticeExample();
		example.createCriteria().andUidEqualTo(uid);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		return sysNoticeMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public int countSysNoticeByUid(long uid) {
		SysNoticeExample example = new SysNoticeExample();
		example.createCriteria().andUidEqualTo(uid);
		return sysNoticeMapper.countByExample(example);
	}

	@Override
	public void delSysNotice(long uid, long sysNoticeId) throws JuzhaiException {
		SysNoticeExample example = new SysNoticeExample();
		example.createCriteria().andIdEqualTo(sysNoticeId).andUidEqualTo(uid);
		if (sysNoticeMapper.deleteByExample(example) <= 0) {
			throw new JuzhaiException(JuzhaiException.ILLEGAL_OPERATION);
		}
	}

}
