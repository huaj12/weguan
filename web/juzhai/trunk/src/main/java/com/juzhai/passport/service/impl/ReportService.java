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
	// TODO (done) uid不能放在form里？
	public void save(ReportForm form, long createUid)
			throws InputReportException {
		validateReport(form, createUid);
		Report report = new Report();
		report.setCreateTime(new Date());
		report.setDescription(form.getDescription());
		report.setHandle(ReportHandleEnum.HANDLEING.getType());
		// TODO (done) lastModifyTime的值能不能用上面的createTime？
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
		// TODO (done) url需要验证长度吗？用户输入的？
		// TODO (done) 为什么打开弹框，需要url？如果不需要组装url的话，打开举报框，需要进服务端请求吗？
		String url = null;
		String reportUrlTemplate = ReportContentType
				.getReportUrlTemplate(reportForm.getContentType());
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
		// TODO (done) 一个方法调用，需要提取出来吗？
		ReportExample example = new ReportExample();
		example.createCriteria().andHandleEqualTo(type);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		return reportMapper.selectByExample(example);
	}

	@Override
	public void shieldUser(long id, Long uid, long time) {
		Report report = new Report();
		report.setId(id);
		// TODO (done) handle为什么没有枚举？
		report.setHandle(ReportHandleEnum.INVALID.getType());
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
		passportService.lockUser(uid, time);
		// TODO 调用发私信接口 已屏蔽

	}

	@Override
	// TODO (done) 解锁需要依赖于report?解锁不应该改变report的状态吧，report处理过就处理过了，状态不应该能还原
	public void unShieldUser(long id, Long uid) {
		passportService.lockUser(uid, 0);
		// TODO 调用发私信接口 解除屏蔽
	}

	@Override
	// TODO (done) 方法名取的不好，count开头吧
	public int countListReport(int type) {
		ReportExample example = new ReportExample();
		example.createCriteria().andHandleEqualTo(type);
		return reportMapper.countByExample(example);
	}

	@Override
	public void handleReport(long id) {
		Report report = new Report();
		report.setId(id);
		// TODO (done) handle为什么没有枚举？有几种状态？“未处理，已处理，无效”我的理解
		report.setHandle(ReportHandleEnum.HANDLED.getType());
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
	}

	@Override
	public void deleteReport(long id) {
		reportMapper.deleteByPrimaryKey(id);
	}

}
