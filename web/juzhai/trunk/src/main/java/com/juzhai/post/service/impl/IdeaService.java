package com.juzhai.post.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import weibo4j.model.IDs;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.cms.controller.form.IdeaForm;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StringUtil;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.post.dao.IIdeaDao;
import com.juzhai.post.exception.InputIdeaException;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
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
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	@Override
	public void addUser(long ideaId, long uid) {
		incrUseCount(ideaId);
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	@Override
	public void removeUser(long ideaId, long uid) {
		decrUseCount(ideaId);
		redisTemplate.opsForSet().remove(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	@Override
	public void removeIdea(long ideaId) throws InputIdeaException{
		// TODO (done) 逻辑调整 有人使用的情况下不能删除idea
		Idea idea=getIdeaById(ideaId);
		if(idea.getUseCount()>0){
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
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
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
		if (ideaForm.getPostId() != null && ideaForm.getPostId() != 0) {
			// TODO (done) 理解第一使用者的意思吗？再结合你controller里的代码看看，逻辑有问题
			idea.setFirstUid(ideaForm.getCreateUid());
		}
		ideaMapper.insertSelective(idea);

		// TODO (done) 第一使用者逻辑混乱，修改的时候来找我
		Long ideaId = idea.getId();
		if (ideaId != null) {
			String fileName = ideaImageService.uploadIdeaPic(ideaForm.getPostId(), ideaForm.getNewpic(), ideaId, ideaForm.getPic());
			if (StringUtils.isNotEmpty(fileName)) {
				idea.setPic(fileName);
				ideaMapper.updateByPrimaryKeySelective(idea);
			}
		}
		if (ideaForm.getPostId() != null && ideaForm.getPostId() != 0) {
			try {
				postService.markIdea(ideaForm.getPostId(), ideaId);
			} catch (InputPostException e) {
				throw new InputIdeaException(
						InputIdeaException.ILLEGAL_OPERATION);
			}
			addFirstUser(ideaId, ideaForm.getCreateUid());
		}

	}


	@Override
	public void updateIdea(IdeaForm ideaForm) throws InputIdeaException,
			UploadImageException {
		validateIdea(ideaForm);
		Idea idea = new Idea();
		idea.setCity(ideaForm.getCity());
		idea.setContent(ideaForm.getContent());
		idea.setContentMd5(ideaForm.getContentMd5());
		idea.setDate(ideaForm.getDate());
		idea.setLastModifyTime(new Date());
		idea.setLink(ideaForm.getLink());
		idea.setPlace(ideaForm.getPlace());
		Long ideaId = ideaForm.getIdeaId();
		idea.setId(ideaId);
		String fileName = ideaImageService.uploadIdeaPic(ideaForm.getPostId(), ideaForm.getNewpic(), ideaId, ideaForm.getPic());
		idea.setPic(fileName);
		ideaMapper.updateByPrimaryKeySelective(idea);
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
		ideaForm.setContentMd5(checkContentDuplicate(ideaForm.getContent()));

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
				// TODO (done) 日期格式错误
				ideaForm.setDate(DateUtils.parseDate(ideaForm.getDateString(),
						DateFormat.DATE_PATTERN));
			} catch (ParseException e) {
				throw new InputIdeaException(
						InputIdeaException.ILLEGAL_OPERATION);
			}
		}
	}

	private String checkContentDuplicate(String content)
			throws InputIdeaException {
		String contentMd5 = null;
		if (StringUtils.isNotEmpty(content)) {
			contentMd5 = DigestUtils.md5Hex(content);
		}
		IdeaExample example = new IdeaExample();
		example.createCriteria().andContentMd5EqualTo(contentMd5);
		if (ideaMapper.countByExample(example) > 0) {
			throw new InputIdeaException(
					InputIdeaException.IDEA_CONTENT_DUPLICATE);
		}
		return contentMd5;
	}

}
