package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.exception.InputReportException;
import com.juzhai.passport.mapper.ReportMapper;
import com.juzhai.passport.model.Report;
import com.juzhai.passport.model.ReportExample;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IReportService;
import com.juzhai.plug.controller.form.ReportForm;

@Service
public class ReportService implements IReportService {
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private IPassportService passportService;
	@Value("${report.description.length.max}")
	private int reportDescriptionLengthMax;
	@Value("${report.content.url.length.max}")
	private int reportContentUrlLengthMax;

	@Override
	public void save(ReportForm form, long uid) throws InputReportException {
		validateReport(form);
		Report report = new Report();
		report.setCreateTime(new Date());
		report.setDescription(form.getDescription());
		report.setHandle(0);
		report.setLastModifyTime(new Date());
		report.setReportType(form.getReportType());
		report.setReportUid(form.getReportUid());
		report.setUid(uid);
		report.setContentUrl(form.getContentUrl());
		report.setContentType(form.getContentType());
		reportMapper.insertSelective(report);
	}

	private void validateReport(ReportForm reportForm)
			throws InputReportException {
		int contentUrlLength = StringUtil.chineseLength(reportForm
				.getContentUrl());
		if (contentUrlLength > reportContentUrlLengthMax
				|| StringUtils.isEmpty(reportForm.getContentUrl())
				|| reportForm.getReportUid() < 1) {
			throw new InputReportException(
					InputReportException.ILLEGAL_OPERATION);
		}
		int descriptionLength = StringUtil.chineseLength(reportForm
				.getDescription());
		if (descriptionLength > reportDescriptionLengthMax) {
			throw new InputReportException(
					InputReportException.REPORT_DESCRIPTION_TOO_LONG);
		}

	}

	@Override
	public List<Report> listReport(int firstResult, int maxResults, int type) {
		return reportMapper.selectByExample(getListExample(type, firstResult,
				maxResults));
	}

	private ReportExample getListExample(int handle, int firstResult,
			int maxResults) {
		ReportExample example = new ReportExample();
		example.createCriteria().andHandleEqualTo(handle);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		return example;
	}

	@Override
	public void shieldUser(long id, Long uid, long time) {
		Report report = new Report();
		report.setId(id);
		report.setHandle(2);
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
		passportService.lockUser(uid, time);
		// TODO 调用发私信接口 已屏蔽

	}

	@Override
	public void unShieldUser(long id, Long uid) {
		Report report = new Report();
		report.setId(id);
		report.setHandle(1);
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
		passportService.lockUser(uid, 0);
		// TODO 调用发私信接口 解除屏蔽
	}

	@Override
	public int listReportCount(int type) {
		ReportExample example = new ReportExample();
		example.createCriteria().andHandleEqualTo(type);
		return reportMapper.countByExample(example);
	}

	@Override
	public void handleReport(long id) {
		Report report = new Report();
		report.setId(id);
		report.setHandle(1);
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
	}

	@Override
	public void deleteReport(long id) {
		reportMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Report getUserReport(long uid) {
		ReportExample example = new ReportExample();
		example.createCriteria().andHandleEqualTo(2).andReportUidEqualTo(uid);
		List<Report> list = reportMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
}
