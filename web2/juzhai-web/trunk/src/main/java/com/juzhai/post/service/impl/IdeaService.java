package com.juzhai.post.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.stats.counter.service.ICounter;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.jstl.JzUtilFunction;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.service.IDialogService;
import com.juzhai.idea.bean.ShowIdeaOrder;
import com.juzhai.idea.controller.view.IdeaUserView;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.ISynchronizeService;
import com.juzhai.post.InitData;
import com.juzhai.post.bean.SynchronizeIdeaTemplate;
import com.juzhai.post.dao.IIdeaDao;
import com.juzhai.post.exception.InputIdeaException;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.mapper.IdeaInterestMapper;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.model.IdeaInterest;
import com.juzhai.post.model.IdeaInterestExample;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.service.IIdeaImageService;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;

@Service
public class IdeaService implements IIdeaService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IdeaMapper ideaMapper;
	@Autowired
	private IIdeaDao ideaDao;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IIdeaImageService ideaImageService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IImageManager imageManager;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private IdeaInterestMapper ideaInterestMapper;
	@Autowired
	private ICounter ideaInterestCounter;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ISynchronizeService synchronizeService;
	@Value("${idea.content.length.min}")
	private int ideaContentLengthMin;
	@Value("${idea.content.length.max}")
	private int ideaContentLengthMax;
	@Value("${idea.place.length.min}")
	private int ideaPlaceLengthMin;
	@Value("${idea.place.length.max}")
	private int ideaPlaceLengthMax;
	@Value("${idea.recent.day.before}")
	private int ideaRecentDayBefore;
	@Value("${synchronize.place.length.max}")
	private int synchronizePlaceLengthMax;
	@Value("${synchronize.title.length.max}")
	private int synchronizeTitleLengthMax;
	@Value("${idea.detail.length.max}")
	private int ideaDetailLengthMax;

	@Override
	public Idea getIdeaById(long ideaId) {
		return ideaMapper.selectByPrimaryKey(ideaId);
	}

	@Override
	public void addFirstUser(long ideaId, long uid) {
		ideaDao.addFirstUser(ideaId, uid);
		redisTemplate.opsForZSet().add(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid,
				System.currentTimeMillis());
	}

	@Override
	public void addUser(long ideaId, long uid) {
		incrUseCount(ideaId);
		redisTemplate.opsForZSet().add(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid,
				System.currentTimeMillis());
	}

	@Override
	public void removeUser(long ideaId, long uid) {
		decrUseCount(ideaId);
		redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	@Override
	public void removeIdea(long ideaId) throws InputIdeaException {
		Idea idea = getIdeaById(ideaId);
		if (idea.getUseCount() > 0) {
			throw new InputIdeaException(InputIdeaException.IDEA_CAN_NOT_DELETE);
		}
		redisTemplate.delete(RedisKeyGenerator.genIdeaUsersKey(ideaId));
		ideaMapper.deleteByPrimaryKey(ideaId);
	}

	private void incrUseCount(long ideaId) {
		ideaDao.incrOrDecrUseCount(ideaId, 1);
	}

	private void decrUseCount(long ideaId) {
		ideaDao.incrOrDecrUseCount(ideaId, -1);
	}

	@Override
	public boolean isUseIdea(long uid, long ideaId) {
		return redisTemplate.opsForZSet().score(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid) != null;
	}

	@Override
	public List<Idea> listIdeaByCityAndCategory(Long cityId, Long categoryId,
			ShowIdeaOrder orderType, int firstResult, int maxResults) {
		IdeaExample example = createIdeaExample(cityId, categoryId);
		example.setOrderByClause(orderType.getColumn() + " desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return ideaMapper.selectByExample(example);
	}

	@Override
	public int countIdeaByCityAndCategory(Long cityId, Long categoryId) {
		IdeaExample example = createIdeaExample(cityId, categoryId);
		return ideaMapper.countByExample(example);
	}

	private IdeaExample createIdeaExample(Long cityId, Long categoryId) {
		IdeaExample example = new IdeaExample();
		if (null != cityId && cityId > 0) {
			IdeaExample.Criteria c1 = example.or().andCityEqualTo(cityId);
			IdeaExample.Criteria c2 = example.or().andCityEqualTo(0L);
			if (null != categoryId && categoryId > 0) {
				c1.andCategoryIdEqualTo(categoryId);
				c2.andCategoryIdEqualTo(categoryId);
			}
			c1.andDefunctEqualTo(false);
			c2.andDefunctEqualTo(false);
		} else {
			IdeaExample.Criteria c = example.createCriteria()
					.andDefunctEqualTo(false);
			if (null != categoryId && categoryId > 0) {
				c.andCategoryIdEqualTo(categoryId);
			}
		}
		return example;
	}

	@Override
	public void addIdea(IdeaForm ideaForm) throws InputIdeaException,
			UploadImageException {
		validateIdea(ideaForm);
		// 如果有上传图片验证合法性
		if (ideaForm.getNewpic() != null && ideaForm.getNewpic().getSize() != 0) {
			imageManager.checkImage(ideaForm.getNewpic());
		}
		Post post = null;
		if (ideaForm.getPostId() != null && ideaForm.getPostId() > 0) {
			post = postService.getPostById(ideaForm.getPostId());
		}
		Idea idea = new Idea();
		idea.setCity(ideaForm.getCity());
		idea.setContent(ideaForm.getContent());
		idea.setContentMd5(ideaForm.getContentMd5());
		idea.setCreateTime(new Date());
		idea.setStartTime(ideaForm.getStartTime());
		idea.setEndTime(ideaForm.getEndTime());
		idea.setCharge(ideaForm.getCharge());
		idea.setTown(ideaForm.getTown());
		idea.setLastModifyTime(new Date());

		idea.setLink(JzUtilFunction.getLink(ideaForm.getLink()));
		idea.setBuyLink(JzUtilFunction.getLink(ideaForm.getBuyLink()));
		idea.setPlace(ideaForm.getPlace());
		idea.setDetail(ideaForm.getDetail());
		if (ideaForm.getGender() != null) {
			idea.setGender(ideaForm.getGender());
		}
		if (ideaForm.getCategoryId() != null) {
			idea.setCategoryId(ideaForm.getCategoryId());
		}
		idea.setRandom(ideaForm.getRandom());
		if (post != null) {
			idea.setCreateUid(post.getCreateUid());
		}
		ideaMapper.insertSelective(idea);

		Long ideaId = idea.getId();
		// ideaDetailService.updateIdeaDetail(ideaId, ideaForm.getDetail());
		String fileName = null;
		if (ideaForm.getPostId() == null && ideaForm.getNewpic().getSize() == 0
				&& StringUtils.isNotEmpty(ideaForm.getPic())) {
			fileName = ideaImageService.intoIdeaLogo(ideaId, ideaForm.getPic());
		} else {
			fileName = ideaImageService.uploadIdeaPic(ideaForm.getPostId(),
					ideaForm.getNewpic(), ideaId, ideaForm.getPic());
		}
		if (StringUtils.isNotEmpty(fileName)) {
			Idea picIdea = new Idea();
			picIdea.setId(ideaId);
			picIdea.setPic(fileName);
			ideaMapper.updateByPrimaryKeySelective(picIdea);
		}
		if (null != post) {
			try {
				postService.markIdea(post.getId(), ideaId);
			} catch (InputPostException e) {
				throw new InputIdeaException(
						InputIdeaException.ILLEGAL_OPERATION);
			}
			addFirstUser(ideaId, post.getCreateUid());
			// 发私信通知
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(post.getCreateUid());
			if (null != profileCache) {
				dialogService
						.sendOfficialSMS(post.getCreateUid(),
								DialogContentTemplate.BECOME_IDEA, profileCache
										.getNickname(), TextTruncateUtil
										.truncate(post.getContent(), 40, "..."));
			}
		}

	}

	@Override
	public void updateIdea(IdeaForm ideaForm) throws InputIdeaException,
			UploadImageException {
		validateIdea(ideaForm);
		// 如果有上传图片验证合法性
		if (ideaForm.getNewpic() != null && ideaForm.getNewpic().getSize() != 0) {
			imageManager.checkImage(ideaForm.getNewpic());
		}
		Long ideaId = ideaForm.getIdeaId();
		Idea idea = getIdeaById(ideaId);
		idea.setCity(ideaForm.getCity());
		idea.setContent(ideaForm.getContent());
		idea.setContentMd5(ideaForm.getContentMd5());
		idea.setStartTime(ideaForm.getStartTime());
		idea.setEndTime(ideaForm.getEndTime());
		idea.setCharge(ideaForm.getCharge());
		idea.setTown(ideaForm.getTown());
		idea.setLastModifyTime(new Date());
		idea.setLink(JzUtilFunction.getLink(ideaForm.getLink()));
		idea.setBuyLink(JzUtilFunction.getLink(ideaForm.getBuyLink()));
		idea.setPlace(ideaForm.getPlace());
		idea.setGender(ideaForm.getGender());
		idea.setCategoryId(ideaForm.getCategoryId());
		idea.setRandom(ideaForm.getRandom());
		idea.setDetail(ideaForm.getDetail());
		// ideaDetailService.updateIdeaDetail(ideaId, ideaForm.getDetail());
		String fileName = ideaImageService.uploadIdeaPic(ideaForm.getPostId(),
				ideaForm.getNewpic(), ideaId, ideaForm.getPic());
		idea.setPic(fileName);
		ideaMapper.updateByPrimaryKey(idea);
	}

	private void validateIdea(IdeaForm ideaForm) throws InputIdeaException {
		if (ideaForm.getCategoryId() == null || ideaForm.getCategoryId() == 0) {
			throw new InputIdeaException(
					InputIdeaException.IDEA_CATEGORY_IS_NULL);
		}
		// 验证城市
		if (ideaForm.getCity() == null) {
			throw new InputIdeaException(InputIdeaException.IDEA_CITY_IS_NULL);
		}
		// 验证内容字数
		int contentLength = StringUtil.chineseLength(ideaForm.getContent());
		if (contentLength < ideaContentLengthMin
				|| contentLength > ideaContentLengthMax) {
			throw new InputIdeaException(
					InputIdeaException.IDEA_CONTENT_LENGTH_ERROR);
		}

		// 验证内容是否重复
		ideaForm.setContentMd5(checkContentDuplicate(ideaForm.getContent(),
				ideaForm.getIdeaId()));

		// 验证地点字数
		int placeLength = StringUtil.chineseLength(ideaForm.getPlace());
		if (placeLength < ideaPlaceLengthMin
				|| placeLength > ideaPlaceLengthMax) {
			throw new InputIdeaException(
					InputIdeaException.IDEA_PLACE_LENGTH_ERROR);
		}
		// 验证日期格式
		if (StringUtils.isNotEmpty(ideaForm.getStartDateString())
				&& StringUtils.isEmpty(ideaForm.getEndDateString())) {
			ideaForm.setEndDateString(ideaForm.getStartDateString());
			ideaForm.setStartDateString(null);
		}
		try {
			if (StringUtils.isNotEmpty(ideaForm.getStartDateString())) {
				ideaForm.setStartTime(DateUtils.parseDate(
						ideaForm.getStartDateString(), DateFormat.TIME_PATTERN));
			}
			if (StringUtils.isNotEmpty(ideaForm.getEndDateString())) {
				ideaForm.setEndTime(DateUtils.parseDate(
						ideaForm.getEndDateString(), DateFormat.TIME_PATTERN));
			}
		} catch (ParseException e) {
			throw new InputIdeaException(InputIdeaException.ILLEGAL_OPERATION);
		}
		// TODO (done) detail验证
		int detailLength = StringUtil.chineseLength(ideaForm.getDetail());
		if (detailLength > ideaDetailLengthMax) {
			throw new InputIdeaException(
					InputIdeaException.IDEA_DETAIL_TOO_LONG);
		}

	}

	@Override
	public String checkContentDuplicate(String content, Long id)
			throws InputIdeaException {
		String contentMd5 = null;
		if (StringUtils.isNotEmpty(content)) {
			contentMd5 = DigestUtils.md5Hex(content);
		}
		IdeaExample example = new IdeaExample();
		IdeaExample.Criteria criteria = example.createCriteria();
		criteria.andContentMd5EqualTo(contentMd5);
		if (id != null) {
			criteria.andIdNotEqualTo(id);
		}
		if (ideaMapper.countByExample(example) > 0) {
			throw new InputIdeaException(
					InputIdeaException.IDEA_CONTENT_DUPLICATE);
		}
		return contentMd5;
	}

	@Override
	public List<IdeaUserView> listIdeaAllUsers(long ideaId, int firstResult,
			int maxResults) {
		List<IdeaUserView> ideaUserViewList = new ArrayList<IdeaUserView>();
		Set<TypedTuple<Long>> users = redisTemplate.opsForZSet()
				.reverseRangeWithScores(
						RedisKeyGenerator.genIdeaUsersKey(ideaId), firstResult,
						firstResult + maxResults - 1);
		for (TypedTuple<Long> user : users) {
			IdeaUserView view = new IdeaUserView();
			view.setProfileCache(profileService.getProfileCacheByUid(user
					.getValue()));
			view.setCreateTime(new Date(user.getScore().longValue()));
			ideaUserViewList.add(view);
		}
		return ideaUserViewList;
	}

	@Override
	public int countIdeaAllUsers(long ideaId) {
		return redisTemplate.opsForZSet()
				.size(RedisKeyGenerator.genIdeaUsersKey(ideaId)).intValue();
	}

	@Override
	public List<IdeaUserView> listIdeaUsers(long ideaId, Long cityId,
			Integer gender, int firstResult, int maxResults) {
		PostExample example = newIdeaUsersExample(ideaId, cityId, gender);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("create_time desc");
		List<Post> list = postMapper.selectByExample(example);
		List<IdeaUserView> ideaUserViewList = new ArrayList<IdeaUserView>(
				list.size());
		for (Post post : list) {
			IdeaUserView view = new IdeaUserView();
			view.setProfileCache(profileService.getProfileCacheByUid(post
					.getCreateUid()));
			view.setCreateTime(post.getCreateTime());
			ideaUserViewList.add(view);
		}
		return ideaUserViewList;
	}

	@Override
	public int countIdeaUsers(long ideaId, Long cityId, Integer gender) {
		PostExample example = newIdeaUsersExample(ideaId, cityId, gender);
		return postMapper.countByExample(example);
	}

	private PostExample newIdeaUsersExample(long ideaId, Long cityId,
			Integer gender) {
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria()
				.andIdeaIdEqualTo(ideaId).andDefunctEqualTo(false);
		if (cityId != null && cityId > 0) {
			c.andUserCityEqualTo(cityId);
		}
		if (gender != null) {
			c.andUserGenderEqualTo(gender);
		}
		return example;
	}

	@Override
	public Idea getRandomIdea(long cityId) {
		List<Idea> list = new ArrayList<Idea>();
		List<Idea> ideaList = InitData.RANDOM_IDEA.get(cityId);
		if (CollectionUtils.isNotEmpty(ideaList)) {
			list.addAll(ideaList);
		}
		if (cityId > 0) {
			ideaList = InitData.RANDOM_IDEA.get(0L);
			if (CollectionUtils.isNotEmpty(ideaList)) {
				list.addAll(ideaList);
			}
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		int index = RandomUtils.nextInt(list.size());
		return list.get(index);
	}

	@Override
	public void ideaRandom(long ideaId, boolean random) {
		Idea idea = new Idea();
		idea.setId(ideaId);
		idea.setRandom(random);
		ideaMapper.updateByPrimaryKeySelective(idea);
	}

	@Override
	public void ideaWindow(long ideaId, boolean window) {
		Idea idea = new Idea();
		idea.setId(ideaId);
		idea.setWindow(window);
		idea.setCreateWindowTime(new Date());
		ideaMapper.updateByPrimaryKeySelective(idea);

	}

	@Override
	public List<Idea> listIdeaWindow(long city, long categoryId,
			int firstResult, int maxResults) {
		IdeaExample example = getIdeaWindowExample(city, categoryId);
		example.setOrderByClause("create_window_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return ideaMapper.selectByExample(example);
	}

	@Override
	public int countIdeaWindow(long city, long categoryId) {
		IdeaExample example = getIdeaWindowExample(city, categoryId);
		return ideaMapper.countByExample(example);
	}

	private IdeaExample getIdeaWindowExample(long city, long categoryId) {
		IdeaExample example = new IdeaExample();
		if (city > 0) {
			IdeaExample.Criteria c1 = example.or().andCityEqualTo(city);
			IdeaExample.Criteria c2 = example.or().andCityEqualTo(0L);
			if (categoryId > 0) {
				c1.andCategoryIdEqualTo(categoryId);
				c2.andCategoryIdEqualTo(categoryId);
			}
			c1.andDefunctEqualTo(false);
			c2.andDefunctEqualTo(false);
			c1.andWindowEqualTo(true);
			c2.andWindowEqualTo(true);
		} else {
			IdeaExample.Criteria c = example.createCriteria()
					.andDefunctEqualTo(false).andWindowEqualTo(true);
			if (categoryId > 0) {
				c.andCategoryIdEqualTo(categoryId);
			}
		}

		return example;
	}

	@Override
	public List<Idea> listUnUsedWindowIdea(long uid, Long cityId,
			int firstResult, int maxResults) {
		List<Long> ideaIdList = usedIdeaIds(uid);

		IdeaExample ideaExample = new IdeaExample();
		if (null != cityId && cityId > 0) {
			IdeaExample.Criteria c1 = ideaExample.or().andCityEqualTo(cityId)
					.andDefunctEqualTo(false).andWindowEqualTo(true);
			IdeaExample.Criteria c2 = ideaExample.or().andCityEqualTo(0L)
					.andDefunctEqualTo(false).andWindowEqualTo(true);
			if (CollectionUtils.isNotEmpty(ideaIdList)) {
				c1.andIdNotIn(ideaIdList);
				c2.andIdNotIn(ideaIdList);
			}
		} else {
			IdeaExample.Criteria c = ideaExample.createCriteria()
					.andDefunctEqualTo(false).andWindowEqualTo(true);
			if (CollectionUtils.isNotEmpty(ideaIdList)) {
				c.andIdNotIn(ideaIdList);
			}
		}
		ideaExample.setOrderByClause("create_window_time desc");
		ideaExample.setLimit(new Limit(firstResult, maxResults));
		return ideaMapper.selectByExample(ideaExample);
	}

	private List<Long> usedIdeaIds(long uid) {
		// TODO 用redis
		List<Long> ideaIdList = null;
		if (uid > 0) {
			PostExample example = new PostExample();
			example.createCriteria().andCreateUidEqualTo(uid)
					.andIdeaIdGreaterThan(0L);
			List<Post> postList = postMapper.selectByExample(example);
			ideaIdList = new ArrayList<Long>(postList.size());
			for (Post post : postList) {
				ideaIdList.add(post.getIdeaId());
			}
		}
		return ideaIdList;
	}

	@Override
	public List<Idea> listRecentIdeas(long uid, Long cityId,
			List<Long> excludeIdeaIds, int firstResult, int maxResults) {
		List<Long> ideaIdList = usedIdeaIds(uid);
		if (CollectionUtils.isNotEmpty(excludeIdeaIds)) {
			if (null != ideaIdList) {
				ideaIdList.addAll(excludeIdeaIds);
			} else {
				ideaIdList = excludeIdeaIds;
			}
		}
		IdeaExample ideaExample = new IdeaExample();
		if (null != cityId && cityId > 0) {
			IdeaExample.Criteria c1 = ideaExample
					.or()
					.andCityEqualTo(cityId)
					.andDefunctEqualTo(false)
					.andCreateTimeGreaterThanOrEqualTo(
							DateUtils.addDays(new Date(), -ideaRecentDayBefore));
			IdeaExample.Criteria c2 = ideaExample
					.or()
					.andCityEqualTo(0L)
					.andDefunctEqualTo(false)
					.andCreateTimeGreaterThanOrEqualTo(
							DateUtils.addDays(new Date(), -ideaRecentDayBefore));
			if (CollectionUtils.isNotEmpty(ideaIdList)) {
				c1.andIdNotIn(ideaIdList);
				c2.andIdNotIn(ideaIdList);
			}
		} else {
			IdeaExample.Criteria c = ideaExample
					.createCriteria()
					.andDefunctEqualTo(false)
					.andCreateTimeGreaterThanOrEqualTo(
							DateUtils.addDays(new Date(), -ideaRecentDayBefore));
			if (CollectionUtils.isNotEmpty(ideaIdList)) {
				c.andIdNotIn(ideaIdList);
			}
		}
		ideaExample.setOrderByClause("use_count desc, create_time asc");
		ideaExample.setLimit(new Limit(firstResult, maxResults));
		return ideaMapper.selectByExample(ideaExample);
	}

	@Override
	public void defunctIdea(long ideaId) {
		Idea idea = new Idea();
		idea.setId(ideaId);
		idea.setDefunct(true);
		ideaMapper.updateByPrimaryKeySelective(idea);
	}

	@Override
	public void cancelDefunctIdea(long ideaId) {
		Idea idea = new Idea();
		idea.setId(ideaId);
		idea.setDefunct(false);
		ideaMapper.updateByPrimaryKeySelective(idea);
	}

	@Override
	public void defunctExpireIdea() {
		Date date = DateUtils.truncate(new Date(), Calendar.DATE);
		IdeaExample example = new IdeaExample();
		example.createCriteria().andEndTimeLessThanOrEqualTo(date);
		Idea idea = new Idea();
		idea.setDefunct(true);
		ideaMapper.updateByExampleSelective(idea, example);
	}

	@Override
	public int countCmsIdeaByCityAndCategory(Boolean window, Long cityId,
			Long categoryId, Boolean random, boolean defunct) {
		IdeaExample example = createCmsIdeaExample(window, cityId, categoryId,
				random, defunct);
		return ideaMapper.countByExample(example);
	}

	@Override
	public List<Idea> listCmsIdeaByCityAndCategory(Boolean window, Long cityId,
			Long categoryId, Boolean random, boolean defunct,
			ShowIdeaOrder oderType, int firstResult, int maxResults) {
		IdeaExample example = createCmsIdeaExample(window, cityId, categoryId,
				random, defunct);
		if (window != null && window) {
			example.setOrderByClause(" create_window_time desc");
		} else {
			example.setOrderByClause(oderType.getColumn() + " desc");
		}

		example.setLimit(new Limit(firstResult, maxResults));
		return ideaMapper.selectByExample(example);
	}

	private IdeaExample createCmsIdeaExample(Boolean window, Long cityId,
			Long categoryId, Boolean random, boolean defunct) {
		IdeaExample example = new IdeaExample();
		IdeaExample.Criteria c = example.createCriteria();
		if (null != cityId) {
			c.andCityEqualTo(cityId);
		}
		if (null != categoryId && categoryId > 0) {
			c.andCategoryIdEqualTo(categoryId);
		}
		if (window != null) {
			c.andWindowEqualTo(window);
		}
		if (random != null) {
			c.andRandomEqualTo(random);
		}
		c.andDefunctEqualTo(defunct);
		return example;
	}

	@Override
	public int totalCount() {
		IdeaExample example = new IdeaExample();
		example.createCriteria().andDefunctEqualTo(false);
		return ideaMapper.countByExample(example);
	}

	@Override
	public void interestIdea(long uid, long ideaId) throws InputIdeaException {
		Idea idea = ideaMapper.selectByPrimaryKey(ideaId);
		if (idea == null || idea.getDefunct()) {
			throw new InputIdeaException(InputIdeaException.ILLEGAL_OPERATION);
		}
		IdeaInterestExample example = new IdeaInterestExample();
		example.createCriteria().andUidEqualTo(uid).andIdeaIdEqualTo(ideaId);
		if (ideaInterestMapper.countByExample(example) > 0) {
			throw new InputIdeaException(
					InputIdeaException.IDEA_INTEREST_DUPLICATE);
		}
		ideaDao.incrOrDecrInterestCnt(ideaId, 1);
		IdeaInterest ideaInterest = new IdeaInterest();
		ideaInterest.setUid(uid);
		ideaInterest.setIdeaId(ideaId);
		ideaInterest.setCreateTime(new Date());
		ideaInterest.setLastModifyTime(ideaInterest.getCreateTime());
		ideaInterestMapper.insertSelective(ideaInterest);

		// interest列表缓存
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genInterestIdeasKey(uid), ideaId);
		ideaInterestCounter.incr(null, 1l);
	}

	@Override
	public List<IdeaUserView> listIdeaInterest(long ideaId, int firstResult,
			int maxResults) {
		IdeaInterestExample example = new IdeaInterestExample();
		example.createCriteria().andIdeaIdEqualTo(ideaId);
		example.setOrderByClause("create_time desc ");
		example.setLimit(new Limit(firstResult, maxResults));
		List<IdeaInterest> list = ideaInterestMapper.selectByExample(example);
		List<IdeaUserView> ideaUserViewList = new ArrayList<IdeaUserView>(
				list.size());
		for (IdeaInterest ideaInterest : list) {
			IdeaUserView view = new IdeaUserView();
			view.setProfileCache(profileService
					.getProfileCacheByUid(ideaInterest.getUid()));
			view.setCreateTime(ideaInterest.getCreateTime());
			ideaUserViewList.add(view);
		}
		return ideaUserViewList;

	}

	@Override
	public boolean isInterestIdea(long uid, long ideaId) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genInterestIdeasKey(uid), ideaId);
	}

	@Override
	public void unInterestIdea(long uid, long ideaId) throws InputIdeaException {
		Idea idea = ideaMapper.selectByPrimaryKey(ideaId);
		if (idea == null) {
			throw new InputIdeaException(InputIdeaException.ILLEGAL_OPERATION);
		}
		IdeaInterestExample example = new IdeaInterestExample();
		example.createCriteria().andUidEqualTo(uid).andIdeaIdEqualTo(ideaId);
		// 存在才删除
		if (ideaInterestMapper.countByExample(example) == 0) {
			throw new InputIdeaException(InputIdeaException.ILLEGAL_OPERATION);
		}
		ideaInterestMapper.deleteByExample(example);
		ideaDao.incrOrDecrInterestCnt(ideaId, -1);
		// 更新interest列表缓存
		redisTemplate.opsForSet().remove(
				RedisKeyGenerator.genInterestIdeasKey(uid), ideaId);

	}

	@Override
	public List<Idea> listUserInterestIdea(long uid, int firstResult,
			int maxResults) {
		IdeaInterestExample example = new IdeaInterestExample();
		example.createCriteria().andUidEqualTo(uid);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		List<IdeaInterest> list = ideaInterestMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		List<Long> ideaIds = new ArrayList<Long>(list.size());
		for (IdeaInterest interest : list) {
			ideaIds.add(interest.getIdeaId());
		}
		IdeaExample ideaExample = new IdeaExample();
		ideaExample.createCriteria().andIdIn(ideaIds);
		Map<Long, Idea> ideaMap = new HashMap<Long, Idea>();
		for (Idea idea : ideaMapper.selectByExample(ideaExample)) {
			ideaMap.put(idea.getId(), idea);
		}
		List<Idea> ideaList = new ArrayList<Idea>(ideaMap.size());
		for (Long id : ideaIds) {
			ideaList.add(ideaMap.get(id));
		}
		return ideaList;
	}

	@Override
	public int countUserInterestIdea(long uid) {
		IdeaInterestExample example = new IdeaInterestExample();
		example.createCriteria().andUidEqualTo(uid);
		return ideaInterestMapper.countByExample(example);
	}

	@Override
	public Idea getNewWindowIdea() {
		IdeaExample example = new IdeaExample();
		example.createCriteria().andCityEqualTo(0L).andDefunctEqualTo(false)
				.andWindowEqualTo(true);
		example.setLimit(new Limit(0, 1));
		example.setOrderByClause("create_window_time desc");
		List<Idea> list = ideaMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public void shareIdea(long uid, long tpId, String content, long ideaId) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo == null) {
			return;
		}
		Idea idea = getIdeaById(ideaId);
		if (idea == null) {
			return;
		}
		if (StringUtils.isEmpty(content)) {
			String place = idea.getPlace();
			if (StringUtils.isNotEmpty(place)) {
				place = messageSource.getMessage(
						SynchronizeIdeaTemplate.SYNCHRONIZE_ADDRESS.getName(),
						new Object[] { place }, Locale.SIMPLIFIED_CHINESE);
				place = TextTruncateUtil.truncate(place,
						synchronizePlaceLengthMax, "...");
			}
			content = messageSource.getMessage(
					SynchronizeIdeaTemplate.SYNCHRONIZE_TEXT.getName(),
					new Object[] { idea.getContent(), place },
					Locale.SIMPLIFIED_CHINESE);
		}
		String link = messageSource.getMessage(
				SynchronizeIdeaTemplate.SYNCHRONIZE_LINK.getName(),
				new Object[] { String.valueOf(ideaId) },
				Locale.SIMPLIFIED_CHINESE);
		String title = TextTruncateUtil.truncate(
				HtmlUtils.htmlUnescape(content), synchronizeTitleLengthMax,
				"...");
		byte[] image = null;
		String imageUrl = null;
		if (StringUtils.isNotEmpty(idea.getPic())) {
			image = ideaImageService.getIdeaFile(ideaId, idea.getPic(),
					JzImageSizeType.ORIGINAL);
			imageUrl = JzResourceFunction.ideaPic(ideaId, idea.getPic(),
					JzImageSizeType.MIDDLE.getType());
		}
		try {
			synchronizeService.sendMessage(authInfo, title, content, link,
					image, imageUrl);
		} catch (Exception e) {
			log.error("shareIdea is error " + e.getMessage());
		}
	}
}
