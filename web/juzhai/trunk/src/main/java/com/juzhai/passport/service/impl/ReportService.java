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
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IReportService;
import com.juzhai.plug.bean.ReportContentType;
import com.juzhai.plug.controller.form.ReportForm;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.search.service.IProfileSearchService;

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
	@Autowired
	private IProfileSearchService profileSearchService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private IPostSearchService postSearchService;
	@Value("${user.post.lunece.rows}")
	private int userPostLuneceRows;

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
		ReportContentType reportContentType = ReportContentType
				.getReportContentTypeEnum(reportForm.getContentType());
		if (reportContentType == null) {
			throw new InputReportException(
					InputReportException.ILLEGAL_OPERATION);
		}
		String url = null;
		String urlTemplate = reportContentType.getUrl();
		switch (reportContentType) {
		case COMMENT:
			url = messageSource.getMessage(urlTemplate,
					new Object[] { String.valueOf(reportForm.getContentId()) },
					Locale.SIMPLIFIED_CHINESE);
			break;
		case MESSAGE:
			url = messageSource.getMessage(urlTemplate,
					new Object[] { String.valueOf(reportForm.getReportUid()),
							String.valueOf(createUid) },
					Locale.SIMPLIFIED_CHINESE);
			break;
		case PROFILE:
			url = messageSource.getMessage(urlTemplate,
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
	public void shieldUser(long id, Long uid, LockUserLevel lockUserLevel)
			throws InputReportException {
		if (lockUserLevel == null) {
			throw new InputReportException(
					InputReportException.ILLEGAL_OPERATION);
		}
		long time = lockUserLevel.getLockTime();
		Report report = new Report();
		report.setId(id);
		report.setHandle(ReportHandleEnum.HANDLED.getType());
		report.setLastModifyTime(new Date());
		reportMapper.updateByPrimaryKeySelective(report);
		passportService.lockUser(uid, new Date(System.currentTimeMillis()
				+ time));
		// 用户永久封号
		if (lockUserLevel.getLevel() == 3) {
			if (profileService.isValidUser(uid)) {
				profileSearchService.deleteIndex(uid);
			}
			// 被永久封号用户的所有通过拒宅
			int i = 0;
			while (true) {
				// TODO (review) 为什么要搜用户的所有拒宅？
				List<Post> posts = postService.listUserPost(uid, i,
						userPostLuneceRows);
				for (Post p : posts) {
					if (VerifyType.QUALIFIED.getType() == p.getVerifyType()) {
						postSearchService.deleteIndex(p.getId());
					}
				}
				i += userPostLuneceRows;
				if (posts.size() < userPostLuneceRows) {
					break;
				}
			}
			PostExample postExample = new PostExample();
			postExample.createCriteria().andCreateUidEqualTo(uid)
					.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType());
			Post post = new Post();
			post.setVerifyType(VerifyType.SHIELD.getType());
			post.setLastModifyTime(new Date());
			postMapper.updateByExampleSelective(post, postExample);
		}

	}

	@Override
	public void unShieldUser(Long uid) {
		passportService.lockUser(uid, null);
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
