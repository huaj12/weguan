package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.bean.LockUserLevel;
import com.juzhai.passport.bean.ReportHandleEnum;
import com.juzhai.passport.exception.InputReportException;
import com.juzhai.passport.mapper.ReportMapper;
import com.juzhai.passport.model.Report;
import com.juzhai.passport.model.ReportExample;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IReportService;
import com.juzhai.plug.bean.ReportContentType;
import com.juzhai.plug.controller.form.ReportForm;

@Service
public class ReportService implements IReportService {
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private IPassportService passportService;
	@Value("${report.description.length.max}")
	private int reportDescriptionLengthMax;
	@Autowired
	private MessageSource messageSource;

	@Override
	public void save(ReportForm form, long createUid)
			throws InputReportException {
		validateReport(form, createUid);
		Report report = new Report();
		report.setCreateTime(new Date());
		report.setDescription(form.getDescription());
		report.setHandle(ReportHandleEnum.HANDLEING.getType());
		report.setLastModifyTime(report.getCreateTime());
		report.setReportType(form.getReportType());
		report.setReportUid(form.getReportUid());
		report.setCreateUid(createUid);
		report.setContentUrl(form.getContentUrl());
		report.setContentType(form.getContentType());
		reportMapper.insertSelective(report);
	}

	private void validateReport(ReportForm reportForm, long createUid)
			throws InputReportException {
		if (reportForm.getContentType() > ReportContentType.values().length
				|| reportForm.getContentType() < 0) {
			throw new InputReportException(
					InputReportException.ILLEGAL_OPERATION);
		}
		// TODO (review) 代码写的不够漂亮
		String url = null;
		String reportUrlTemplate = ReportContentType.getReportContentTypeEnum(
				reportForm.getContentType()).getUrl();
		switch (ReportContentType.getReportContentTypeEnum(reportForm
				.getContentType())) {
		case COMMENT:
			url = messageSource.getMessage(reportUrlTemplate,
					new Object[] { String.valueOf(reportForm.getContentId()) },
					Locale.SIMPLIFIED_CHINESE);
			break;
		case MESSAGE:
			url = messageSource.getMessage(reportUrlTemplate,
					new Object[] { String.valueOf(reportForm.getReportUid()),
							String.valueOf(createUid) },
					Locale.SIMPLIFIED_CHINESE);
			break;
		case PROFILE:
			url = messageSource.getMessage(reportUrlTemplate,
					new Object[] { String.valueOf(reportForm.getReportUid()) },
					Locale.SIMPLIFIED_CHINESE);
			break;
		}
		reportForm.setContentUrl(url);
		int descriptionLength = StringUtil.chineseLength(reportForm
				.getDescription());
		if (descriptionLength > reportDescriptionLengthMax) {
			throw new InputReportException(
					InputReportException.REPORT_DESCRIPTION_TOO_LONG);
		}

	}

	@Override
	public List<Report> listReport(int firstResult, int maxResults, int type) {
		ReportExample example = new ReportExample();
		example.createCriteria().andHandleEqualTo(type);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		return reportMapper.selectByExample(example);
	}

	@Override
	public void shieldUser(long id, Long uid, LockUserLevel lockUserLevel) {
		// TODO (review) lockUserLevel会不会可能是null？有想到吗？
		long time = lockUserLevel.getLockTime();
		Report report = new Report();
		report.setId(id);
		report.setHandle(ReportHandleEnum.HANDLED.getType());
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
		passportService.lockUser(uid, System.currentTimeMillis() + time);
		// TODO 调用发私信接口 已屏蔽

	}

	@Override
	// TODO (review) 为什么还有reportId参数？另外做一个被锁用户列表，只能在那列表里进行解锁操作
	public void unShieldUser(Long uid) {
		passportService.lockUser(uid, 0);
		// TODO 调用发私信接口 解除屏蔽
	}

	@Override
	public int countListReport(int type) {
		ReportExample example = new ReportExample();
		example.createCriteria().andHandleEqualTo(type);
		return reportMapper.countByExample(example);
	}

	@Override
	public void ignoreReport(long id) {
		Report report = new Report();
		report.setId(id);
		report.setHandle(ReportHandleEnum.INVALID.getType());
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
	}

	@Override
	public void deleteReport(long id) {
		reportMapper.deleteByPrimaryKey(id);
	}

}
