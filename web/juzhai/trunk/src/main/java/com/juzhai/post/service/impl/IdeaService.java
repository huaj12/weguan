package com.juzhai.post.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.InitData;
import com.juzhai.post.controller.view.IdeaUserView;
import com.juzhai.post.dao.IIdeaDao;
import com.juzhai.post.exception.InputIdeaException;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.service.IIdeaImageService;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;

@Service
public class IdeaService implements IIdeaService {

	@Autowired
	private IdeaMapper ideaMapper;
	@Autowired
	private IIdeaDao ideaDao;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;
	@Value("${idea.content.length.min}")
	private int ideaContentLengthMin;
	@Value("${idea.content.length.max}")
	private int ideaContentLengthMax;
	@Value("${idea.place.length.min}")
	private int ideaPlaceLengthMin;
	@Value("${idea.place.length.max}")
	private int ideaPlaceLengthMax;
	@Autowired
	private IIdeaImageService ideaImageService;
	@Autowired
	private IPostService postService;

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
	public List<Idea> listIdeaByCity(Long cityId, ShowIdeaOrder orderType,
			int firstResult, int maxResults) {
		IdeaExample example = new IdeaExample();
		if (null != cityId && cityId > 0) {
			example.createCriteria().andCityEqualTo(cityId);
		}
		example.setOrderByClause(orderType.getColumn() + " desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return ideaMapper.selectByExample(example);
	}

	@Override
	public int countIdeaByCity(Long cityId) {
		IdeaExample example = new IdeaExample();
		if (null != cityId && cityId > 0) {
			example.createCriteria().andCityEqualTo(cityId);
		}
		return ideaMapper.countByExample(example);
	}

	@Override
	public void addIdea(IdeaForm ideaForm) throws InputIdeaException,
			UploadImageException {
		validateIdea(ideaForm);
		Idea idea = new Idea();
		idea.setCity(ideaForm.getCity());
		idea.setContent(ideaForm.getContent());
		idea.setContentMd5(ideaForm.getContentMd5());
		idea.setCreateTime(new Date());
		idea.setDate(ideaForm.getDate());
		idea.setLastModifyTime(new Date());
		idea.setLink(ideaForm.getLink());
		idea.setPlace(ideaForm.getPlace());
		if (ideaForm.getGender() != null) {
			idea.setGender(ideaForm.getGender());
		}
		if (ideaForm.getCategoryId() != null) {
			idea.setCategoryId(ideaForm.getCategoryId());
		}

		ideaMapper.insertSelective(idea);

		Long ideaId = idea.getId();
		String fileName = ideaImageService.uploadIdeaPic(ideaForm.getPostId(),
				ideaForm.getNewpic(), ideaId, ideaForm.getPic());
		if (StringUtils.isNotEmpty(fileName)) {
			Idea picIdea = new Idea();
			picIdea.setId(ideaId);
			picIdea.setPic(fileName);
			ideaMapper.updateByPrimaryKeySelective(picIdea);
		}
		if (ideaForm.getPostId() != null && ideaForm.getPostId() > 0) {
			try {
				postService.markIdea(ideaForm.getPostId(), ideaId);
			} catch (InputPostException e) {
				throw new InputIdeaException(
						InputIdeaException.ILLEGAL_OPERATION);
			}
			if (ideaForm.getCreateUid() != null && ideaForm.getCreateUid() > 0) {
				addFirstUser(ideaId, ideaForm.getCreateUid());
			}
		}

	}

	@Override
	public void updateIdea(IdeaForm ideaForm) throws InputIdeaException,
			UploadImageException {
		validateIdea(ideaForm);
		Long ideaId = ideaForm.getIdeaId();
		Idea idea = getIdeaById(ideaId);
		idea.setCity(ideaForm.getCity());
		idea.setContent(ideaForm.getContent());
		idea.setContentMd5(ideaForm.getContentMd5());
		idea.setDate(ideaForm.getDate());
		idea.setLastModifyTime(new Date());
		idea.setLink(ideaForm.getLink());
		idea.setPlace(ideaForm.getPlace());
		idea.setGender(ideaForm.getGender());
		idea.setCategoryId(ideaForm.getCategoryId());
		idea.setRandom(ideaForm.getRandom());

		String fileName = ideaImageService.uploadIdeaPic(ideaForm.getPostId(),
				ideaForm.getNewpic(), ideaId, ideaForm.getPic());
		idea.setPic(fileName);
		ideaMapper.updateByPrimaryKey(idea);
	}

	private void validateIdea(IdeaForm ideaForm) throws InputIdeaException {
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
		if (StringUtils.isNotEmpty(ideaForm.getDateString())) {
			try {
				ideaForm.setDate(DateUtils.parseDate(ideaForm.getDateString(),
						DateFormat.DATE_PATTERN));
			} catch (ParseException e) {
				throw new InputIdeaException(
						InputIdeaException.ILLEGAL_OPERATION);
			}
		}
	}

	private String checkContentDuplicate(String content, Long id)
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
	public List<IdeaUserView> listIdeaUsers(long ideaId, int firstResult,
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
	public int countIdeaUsers(long ideaId) {
		return redisTemplate.opsForZSet()
				.size(RedisKeyGenerator.genIdeaUsersKey(ideaId)).intValue();
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
		System.out.println("size:" + list.size());
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		int index = RandomUtils.nextInt(list.size());
		System.out.println("index:" + index);
		return list.get(index);
	}
	
	@Override
	public void ideaRandom(long ideaId, boolean random) {
		Idea idea = new Idea();
		idea.setId(ideaId);
		idea.setRandom(random);
		ideaMapper.updateByPrimaryKeySelective(idea);
	}
}
