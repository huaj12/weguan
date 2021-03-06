package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.bean.LockUserLevel;
import com.juzhai.passport.bean.ReportHandleEnum;
import com.juzhai.passport.exception.InputReportException;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.mapper.ReportMapper;
import com.juzhai.passport.model.Profile;
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
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private ProfileMapper profileMapper;
	@Value("${user.post.lucene.rows}")
	private int userPostLuceneRows;
	@Value("${report.description.length.max}")
	private int reportDescriptionLengthMax;

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
			Profile profile = profileMapper.selectByPrimaryKey(uid);
			profile.setLastUpdateTime(null);
			profileMapper.updateByPrimaryKey(profile);
			redisTemplate.delete(RedisKeyGenerator.genUserLatestPostKey(uid));
			profileService.clearProfileCache(uid);
			// 被永久封号用户的所有通过拒宅
			int i = 0;
			while (true) {
				List<Post> posts = postService.getUserQualifiedPost(uid, i,
						userPostLuceneRows);
				for (Post p : posts) {
					postSearchService.deleteIndex(p.getId());
				}
				i += userPostLuceneRows;
				if (posts.size() < userPostLuceneRows) {
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
		boolean flag = passportService.isPermanentLock(uid);// 是否永久封号
		passportService.lockUser(uid, null);
		if (flag) {
			if (profileService.isValidUser(uid)) {
				profileSearchService.createIndex(uid);
			}
		}
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
