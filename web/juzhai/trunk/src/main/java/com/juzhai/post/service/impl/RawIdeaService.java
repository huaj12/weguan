package com.juzhai.post.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.web.jstl.JzUtilFunction;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.bean.PurposeType;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.post.exception.InputIdeaException;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.exception.InputRawIdeaException;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.mapper.RawIdeaMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.RawIdea;
import com.juzhai.post.model.RawIdeaExample;
import com.juzhai.post.service.IIdeaDetailService;
import com.juzhai.post.service.IIdeaImageService;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;
import com.juzhai.post.service.IRawIdeaService;

@Service
public class RawIdeaService implements IRawIdeaService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IDialogService dialogService;
	@Value("${post.content.length.min}")
	private int postContentLengthMin;
	@Value("${post.content.length.max}")
	private int postContentLengthMax;
	@Value("${post.place.length.min}")
	private int postPlaceLengthMin;
	@Value("${post.place.length.max}")
	private int postPlaceLengthMax;
	@Value("${idea.detail.length.max}")
	private int ideaDetailLengthMax;
	@Value("${idea.link.length.max}")
	private int ideaLinkLengthMax;
	@Value("${create.idea.expire.time}")
	private int createIdeaExpireTime;
	@Value("${create.idea.count}")
	private int createIdeaCount;

	@Autowired
	private RawIdeaMapper rawIdeaMapper;
	@Autowired
	private IdeaMapper ideaMapper;
	@Autowired
	private IIdeaImageService ideaImageService;
	@Autowired
	private IIdeaDetailService ideaDetailService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private IPostService postService;
	@Autowired
	private IProfileService profileService;

	@Override
	public void createRawIdea(RawIdeaForm rawIdeaForm)
			throws InputRawIdeaException {
		Integer userCreateIdeaCount = null;
		// 创建好主意才判断
		if (rawIdeaForm.getCreateUid() != null) {
			try {
				userCreateIdeaCount = memcachedClient.get(MemcachedKeyGenerator
						.genCreateIdeaCountKey(rawIdeaForm.getCreateUid()));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (userCreateIdeaCount == null) {
				userCreateIdeaCount = 0;
			}
			if (userCreateIdeaCount > createIdeaCount) {
				throw new InputRawIdeaException(
						InputRawIdeaException.RAW_IDEA_CREATE_TO_MORE);
			}
		}
		validateRawIdea(rawIdeaForm);
		RawIdea rawIdea = conversionRawIdeaForm(rawIdeaForm);
		if (rawIdea.getIdeaId() == null) {
			dialogService.sendOfficialSMS(rawIdea.getCreateUid(),
					DialogContentTemplate.USER_CREATE_IDEA);
		} else {
			dialogService.sendOfficialSMS(rawIdea.getCorrectionUid(),
					DialogContentTemplate.USER_UPDATE_IDEA);
		}
		rawIdeaMapper.insertSelective(rawIdea);

		if (rawIdeaForm.getCreateUid() != null) {
			try {
				if (userCreateIdeaCount == null) {
					userCreateIdeaCount = 1;
				} else {
					userCreateIdeaCount++;
				}
				memcachedClient.set(MemcachedKeyGenerator
						.genCreateIdeaCountKey(rawIdeaForm.getCreateUid()),
						createIdeaExpireTime, userCreateIdeaCount);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	private void validateRawIdea(RawIdeaForm rawIdeaForm)
			throws InputRawIdeaException {
		int contentLength = StringUtil.chineseLength(rawIdeaForm.getContent());
		if (contentLength < postContentLengthMin
				|| contentLength > postContentLengthMax) {
			throw new InputRawIdeaException(
					InputRawIdeaException.RAW_IDEA_CONTENT_LENGTH_ERROR);
		}
		if (StringUtils.isEmpty(rawIdeaForm.getPic())) {
			throw new InputRawIdeaException(
					InputRawIdeaException.RAW_IDEA_PIC_IS_NULL);
		}
		if (rawIdeaForm.getCategoryId() == null
				|| rawIdeaForm.getCategoryId() <= 0) {
			throw new InputRawIdeaException(
					InputRawIdeaException.RAW_IDEA_CATEGORYID_IS_NULL);

		}
		// 验证日期格式
		if (StringUtils.isNotEmpty(rawIdeaForm.getStartDateString())
				&& StringUtils.isNotEmpty(rawIdeaForm.getEndDateString())) {
			try {
				rawIdeaForm.setStartTime(DateUtils.parseDate(
						rawIdeaForm.getStartDateString(),
						DateFormat.TIME_PATTERN));
				rawIdeaForm
						.setEndTime(DateUtils.parseDate(
								rawIdeaForm.getEndDateString(),
								DateFormat.TIME_PATTERN));
			} catch (ParseException e) {
				throw new InputRawIdeaException(
						InputRawIdeaException.ILLEGAL_OPERATION);
			}
			// 开始日期大于结束日期
			if (rawIdeaForm.getStartTime().getTime() > rawIdeaForm.getEndTime()
					.getTime()) {
				throw new InputRawIdeaException(
						InputRawIdeaException.RAW_IDEA_TIME_IS_ERROR);
			}
		}
		// // 如果类别是拒宅灵感。则没有时间和地点选项
		// if (rawIdeaForm.getCategoryId() == 8) {
		// if (rawIdeaForm.getStartTime() != null
		// || rawIdeaForm.getEndTime() != null
		// || rawIdeaForm.getCity() != null
		// || rawIdeaForm.getTown() != null
		// || rawIdeaForm.getPlace() != null) {
		// throw new RawIdeaInputException(
		// RawIdeaInputException.ILLEGAL_OPERATION);
		//
		// }
		// }
		//
		// // 如果类别是聚会活动或者演出展览时间地点详情是必填
		// if (rawIdeaForm.getCategoryId() == 3
		// || rawIdeaForm.getCategoryId() == 6) {
		// if (rawIdeaForm.getStartTime() == null
		// || rawIdeaForm.getEndTime() == null) {
		// throw new RawIdeaInputException(
		// RawIdeaInputException.RAW_IDEA_TIME_IS_NULL);
		//
		// }
		// if (rawIdeaForm.getCity() == null) {
		// throw new RawIdeaInputException(
		// RawIdeaInputException.RAW_IDEA_CITY_IS_NULL);
		// }
		// if (rawIdeaForm.getTown() == null) {
		// throw new RawIdeaInputException(
		// RawIdeaInputException.RAW_IDEA_TOWN_IS_NULL);
		// }
		// if (StringUtils.isEmpty(rawIdeaForm.getPlace())) {
		// throw new RawIdeaInputException(
		// RawIdeaInputException.RAW_IDEA_ADDRESS_IS_NULL);
		// }
		// if (StringUtils.isEmpty(rawIdeaForm.getDetail())) {
		// throw new RawIdeaInputException(
		// RawIdeaInputException.RAW_IDEA_DETAIL_IS_NULL);
		// }
		// }

		int placeLength = StringUtil.chineseLength(rawIdeaForm.getPlace());
		if (placeLength < postPlaceLengthMin
				|| placeLength > postPlaceLengthMax) {
			throw new InputRawIdeaException(
					InputRawIdeaException.RAW_IDEA_ADDRESS_TOO_LONG);

		}

		int detailLength = StringUtil.chineseLength(rawIdeaForm.getDetail());
		if (detailLength > ideaDetailLengthMax) {
			throw new InputRawIdeaException(
					InputRawIdeaException.RAW_IDEA_DETAIL_TOO_LONG);
		}

		int linkLength = StringUtil.chineseLength(rawIdeaForm.getLink());
		if (linkLength > ideaLinkLengthMax) {
			throw new InputRawIdeaException(
					InputRawIdeaException.RAW_IDEA_LINK_TOO_LONG);
		}
		rawIdeaForm.setContentMd5(checkContentDuplicate(
				rawIdeaForm.getContent(), null));

	}

	private String checkContentDuplicate(String content, String contentMd5)
			throws InputRawIdeaException {
		if (StringUtils.isNotEmpty(content)) {
			contentMd5 = DigestUtils.md5Hex(content);
		}
		// RawIdeaExample example = new RawIdeaExample();
		// example.createCriteria().andContentMd5EqualTo(contentMd5);
		// if (rawIdeaMapper.countByExample(example) > 0) {
		// throw new RawIdeaInputException(
		// RawIdeaInputException.RAW_IDEA_CONTENT_EXIST);
		// }
		return contentMd5;
	}

	@Override
	public List<RawIdea> listRawIdea(int firstResult, int maxResult) {
		RawIdeaExample example = getRawIdeaExample();
		example.setLimit(new Limit(firstResult, maxResult));
		example.setOrderByClause("create_time desc");
		return rawIdeaMapper.selectByExample(example);
	}

	@Override
	public void delRawIdea(Long id) {
		rawIdeaMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<RawIdea> listCorrectionRawIdea(int firstResult, int maxResult) {
		RawIdeaExample example = getCorrectionRawIdeaExample();
		example.setLimit(new Limit(firstResult, maxResult));
		example.setOrderByClause("create_time desc");
		return rawIdeaMapper.selectByExample(example);
	}

	private Idea ideaCopyRawIdea(Long id) throws InputRawIdeaException {
		RawIdea rawIdea = rawIdeaMapper.selectByPrimaryKey(id);
		if (rawIdea == null) {
			throw new InputRawIdeaException(
					InputRawIdeaException.ILLEGAL_OPERATION);
		}
		String contentMd5;
		try {
			contentMd5 = ideaService.checkContentDuplicate(
					rawIdea.getContent(), null);
		} catch (InputIdeaException e) {
			throw new InputRawIdeaException(
					InputRawIdeaException.RAW_IDEA_CONTENT_EXIST);
		}
		Idea idea = new Idea();
		idea.setCategoryId(rawIdea.getCategoryId());
		idea.setCharge(rawIdea.getCharge());
		idea.setCity(rawIdea.getCity());
		idea.setContent(rawIdea.getContent());
		idea.setContentMd5(contentMd5);
		idea.setCreateTime(new Date());
		idea.setCreateUid(rawIdea.getCreateUid());
		idea.setDefunct(false);
		idea.setEndTime(rawIdea.getEndTime());
		idea.setLastModifyTime(idea.getCreateTime());
		idea.setLink(rawIdea.getLink());
		idea.setPlace(rawIdea.getPlace());
		idea.setStartTime(rawIdea.getStartTime());
		idea.setTown(rawIdea.getTown());
		ideaMapper.insertSelective(idea);
		if (idea.getId() != null) {
			String newPic = ideaImageService.intoIdeaLogo(idea.getId(),
					rawIdea.getPic());
			idea.setPic(newPic);
			ideaMapper.updateByPrimaryKeySelective(idea);
			ideaDetailService.updateIdeaDetail(idea.getId(),
					rawIdea.getDetail());
		}
		return idea;
	}

	@Override
	public void passRawIdea(RawIdeaForm rawIdeaForm)
			throws InputRawIdeaException {
		validateRawIdea(rawIdeaForm);
		RawIdea rawIdea = conversionRawIdeaForm(rawIdeaForm);
		rawIdeaMapper.updateByPrimaryKeySelective(rawIdea);
		// 修改后通过审核
		Idea idae = ideaCopyRawIdea(rawIdea.getId());
		// 发送私信
		dialogService.sendOfficialSMS(idae.getCreateUid(),
				DialogContentTemplate.PASS_RAW_IDEA,
				JzUtilFunction.truncate(idae.getContent(), 15, "..."));
		// 通过后删除该拒宅
		delRawIdea(rawIdea.getId());
		// 有头像且是通过状态才发拒宅
		ProfileCache profile = profileService.getProfileCacheByUid(idae
				.getCreateUid());
		if (StringUtils.isNotEmpty(profile.getLogoPic())
				&& profile.getLogoVerifyState() == LogoVerifyState.VERIFIED
						.getType()) {
			PostForm postForm = new PostForm();
			postForm.setIdeaId(idae.getId());
			postForm.setPurposeType(PurposeType.WANT.getType());
			try {
				postService.createPost(idae.getCreateUid(), postForm);
			} catch (InputPostException e) {
				log.error("cms passRawIdea  create post is error", e);
			}
		}

	}

	private RawIdea conversionRawIdeaForm(RawIdeaForm rawIdeaForm) {
		RawIdea rawIdea = new RawIdea();
		rawIdea.setId(rawIdeaForm.getId());
		rawIdea.setCategoryId(rawIdeaForm.getCategoryId());
		rawIdea.setCharge(rawIdeaForm.getCharge());
		rawIdea.setCity(rawIdeaForm.getCity());
		rawIdea.setContent(rawIdeaForm.getContent());
		rawIdea.setContentMd5(rawIdeaForm.getContentMd5());
		if (rawIdea.getId() == null) {
			rawIdea.setCreateTime(new Date());
			rawIdea.setLastModifyTime(rawIdea.getCreateTime());
		} else {
			rawIdea.setLastModifyTime(new Date());
		}
		rawIdea.setCreateUid(rawIdeaForm.getCreateUid());
		rawIdea.setCorrectionUid(rawIdeaForm.getCorrectionUid());
		rawIdea.setDetail(rawIdeaForm.getDetail());
		rawIdea.setEndTime(rawIdeaForm.getEndTime());
		rawIdea.setStartTime(rawIdeaForm.getStartTime());
		rawIdea.setIdeaId(rawIdeaForm.getIdeaId());
		rawIdea.setLink(rawIdeaForm.getLink());
		rawIdea.setPic(rawIdeaForm.getPic());
		rawIdea.setPlace(rawIdeaForm.getPlace());
		rawIdea.setTown(rawIdeaForm.getTown());
		return rawIdea;
	}

	@Override
	public int countRawIdea() {
		RawIdeaExample example = getRawIdeaExample();
		return rawIdeaMapper.countByExample(example);
	}

	@Override
	public int countCorrectionRawIdea() {
		RawIdeaExample example = getCorrectionRawIdeaExample();
		return rawIdeaMapper.countByExample(example);
	}

	private RawIdeaExample getCorrectionRawIdeaExample() {
		RawIdeaExample example = new RawIdeaExample();
		example.createCriteria().andIdeaIdIsNotNull();
		return example;
	}

	private RawIdeaExample getRawIdeaExample() {
		RawIdeaExample example = new RawIdeaExample();
		example.createCriteria().andIdeaIdIsNull();
		return example;
	}

	@Override
	public RawIdea getRawIdea(Long id) {
		return rawIdeaMapper.selectByPrimaryKey(id);
	}
}
