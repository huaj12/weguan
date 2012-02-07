package com.juzhai.post.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.bean.PurposeType;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.dao.IPostDao;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.mapper.PostResponseMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.model.PostResponse;
import com.juzhai.post.model.PostResponseExample;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostImageService;
import com.juzhai.post.service.IPostService;
import com.juzhai.wordfilter.service.IWordFilterService;

@Service
public class PostService implements IPostService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private PostMapper postMapper;
	@Autowired
	private PostResponseMapper postResponseMapper;
	@Autowired
	private IPostDao postDao;
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPostImageService postImageService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private RedisTemplate<String, Post> redisTemplate;
	@Autowired
	private RedisTemplate<String, Long> longRedisTemplate;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IWordFilterService wordFilterService;
	@Value("${post.content.wordfilter.application}")
	private int postContentWordfilterApplication;
	@Value("${post.content.length.min}")
	private int postContentLengthMin;
	@Value("${post.content.length.max}")
	private int postContentLengthMax;
	@Value("${post.place.length.min}")
	private int postPlaceLengthMin;
	@Value("${post.place.length.max}")
	private int postPlaceLengthMax;
	@Value("${post.interval.expire.time}")
	private int postIntervalExpireTime;

	@Override
	public void createPost(long uid, PostForm postForm)
			throws InputPostException {
		Boolean isForbid = null;
		try {
			isForbid = memcachedClient.get(MemcachedKeyGenerator
					.genPostForbidKey(uid));
		} catch (Exception e) {
		}
		if (null != isForbid && isForbid) {
			throw new InputPostException(InputPostException.POST_TOO_FREQUENT);
		}
		if (postForm.getIdeaId() > 0) {
			// 发表from idea
			createPostByIdea(uid, postForm.getIdeaId());
		} else {
			createPostByForm(uid, postForm);
		}
		try {
			memcachedClient.setWithNoReply(
					MemcachedKeyGenerator.genPostForbidKey(uid),
					postIntervalExpireTime, true);
		} catch (Exception e) {
		}
	}

	private void createPostByIdea(long uid, long ideaId)
			throws InputPostException {
		Idea idea = ideaService.getIdeaById(ideaId);
		if (null == idea) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		if (hasPostIdea(uid, ideaId)) {
			throw new InputPostException(InputPostException.IDEA_HAS_POSTED);
		}
		// 验证内容是否重复
		try {
			checkContentDuplicate(uid, null, idea.getContentMd5(), 0);
		} catch (InputPostException e) {
			return;
		}

		Post post = new Post();
		post.setContent(idea.getContent());
		post.setContentMd5(idea.getContentMd5());
		post.setLink(idea.getLink());
		post.setPlace(idea.getPlace());
		post.setDateTime(idea.getDate());
		post.setCategoryId(post.getCategoryId());
		post.setIdeaId(idea.getId());
		post.setCity(idea.getCity());
		post.setPic(idea.getPic());
		post.setVerifyType(VerifyType.QUALIFIED.getType());

		createPost(uid, post, null);

		// 添加idea的useCount或者firstUser
		if (idea.getUseCount() == 0) {
			ideaService.addFirstUser(ideaId, uid);
		} else {
			ideaService.addUser(ideaId, uid);
		}

	}

	private void createPostByForm(long uid, PostForm postForm)
			throws InputPostException {
		// 验证
		validatePostForm(uid, postForm);

		Post post = new Post();
		post.setContent(postForm.getContent());
		post.setContentMd5(postForm.getContentMd5());
		post.setLink(postForm.getLink());
		post.setPlace(postForm.getPlace());
		post.setDateTime(postForm.getDate());
		post.setPurposeType(postForm.getPurposeType());

		createPost(uid, post, postForm.getPic());
	}

	private void validatePostForm(long uid, PostForm postForm)
			throws InputPostException {
		// 验证内容字数
		int contentLength = StringUtil.chineseLength(postForm.getContent());
		if (contentLength < postContentLengthMin
				|| contentLength > postContentLengthMax) {
			throw new InputPostException(
					InputPostException.POST_CONTENT_LENGTH_ERROR);
		}
		// 验证屏蔽字
		try {
			if (wordFilterService.wordFilter(postContentWordfilterApplication,
					uid, null, postForm.getContent().getBytes("GBK")) < 0) {
				throw new InputPostException(
						InputPostException.POST_CONTENT_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}

		// 验证内容是否重复
		postForm.setContentMd5(checkContentDuplicate(uid,
				postForm.getContent(), null, postForm.getPostId()));

		// 验证地点字数
		int placeLength = StringUtil.chineseLength(postForm.getPlace());
		if (placeLength < postPlaceLengthMin
				|| placeLength > postPlaceLengthMax) {
			throw new InputPostException(
					InputPostException.POST_CONTENT_LENGTH_ERROR);
		}
		// 验证日期格式
		if (StringUtils.isNotEmpty(postForm.getDateString())) {
			try {
				postForm.setDate(DateUtils.parseDate(postForm.getDateString(),
						DateFormat.DATE_PATTERN));
			} catch (ParseException e) {
				throw new InputPostException(
						InputPostException.ILLEGAL_OPERATION);
			}
		}
		// TODO 验证链接格式

		// 验证purposeType
		if (PurposeType.getPurposeTypeByType(postForm.getPurposeType()) == null) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
	}

	private void createPost(long uid, Post post, String tmpImgFilePath)
			throws InputPostException {
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		if (null == profile) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		post.setCreateUid(uid);
		post.setCreateTime(new Date());
		post.setLastModifyTime(post.getCreateTime());
		post.setUserCity(profile.getCity());
		post.setUserGender(profile.getGender());
		postMapper.insertSelective(post);

		// 处理图片
		if (StringUtils.isNotEmpty(tmpImgFilePath)) {
			String fileName = postImageService.saveImg(post.getId(),
					tmpImgFilePath);
			if (StringUtils.isNotEmpty(fileName)) {
				Post updatePost = new Post();
				updatePost.setId(post.getId());
				updatePost.setPic(fileName);
				postMapper.updateByPrimaryKeySelective(updatePost);
			}
		}

		// TODO 建lucene索引

		// 更新用户最新一条拒宅
		setUserLatestPost(uid, post);
	}

	private String checkContentDuplicate(long uid, String content,
			String contentMd5, long postId) throws InputPostException {
		if (StringUtils.isNotEmpty(content)) {
			contentMd5 = DigestUtils.md5Hex(content);
		}
		PostExample example = new PostExample();
		example.createCriteria().andCreateUidEqualTo(uid)
				.andContentMd5EqualTo(contentMd5).andIdNotEqualTo(postId);
		if (postMapper.countByExample(example) > 0) {
			throw new InputPostException(
					InputPostException.POST_CONTENT_DUPLICATE);
		}
		return contentMd5;
	}

	@Override
	public boolean hasPostIdea(long uid, long ideaId) {
		PostExample example = new PostExample();
		example.createCriteria().andCreateUidEqualTo(uid)
				.andIdeaIdEqualTo(ideaId);
		return postMapper.countByExample(example) > 0;
	}

	@Override
	public void modifyPost(long uid, PostForm postForm)
			throws InputPostException {
		Post post = postMapper.selectByPrimaryKey(postForm.getPostId());
		if (null == post || post.getDefunct() || post.getCreateUid() != uid) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		if (null == profile) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}

		// 验证form
		validatePostForm(uid, postForm);

		// 判断是否脱离Idea
		long breakIdeaId = 0L;
		if (post.getIdeaId() > 0) {
			if (!StringUtils.equals(post.getContentMd5(),
					postForm.getContentMd5())
					|| !StringUtils
							.equals(post.getPlace(), postForm.getPlace())
					|| !StringUtils.equals(post.getLink(), postForm.getLink())
					|| !StringUtils.equals(post.getPic(), postForm.getPic())) {
				breakIdeaId = post.getIdeaId();
				post.setIdeaId(0L);
			}
		}
		post.setContent(postForm.getContent());
		post.setContent(postForm.getContentMd5());
		post.setLink(postForm.getLink());
		post.setPlace(postForm.getPlace());
		post.setDateTime(postForm.getDate());
		post.setPic(postForm.getPic());
		post.setPurposeType(postForm.getPurposeType());
		if (post.getIdeaId() <= 0) {
			post.setVerifyType(VerifyType.RAW.getType());
		}
		post.setLastModifyTime(post.getLastModifyTime());
		post.setUserCity(profile.getCity());
		post.setUserGender(profile.getGender());
		postMapper.updateByPrimaryKey(post);

		if (breakIdeaId > 0) {
			ideaService.removeUser(breakIdeaId, uid);
		}

		// TODO update lucene索引
	}

	@Override
	public void responsePost(long uid, long postId) throws InputPostException {
		Post post = postMapper.selectByPrimaryKey(postId);
		if (post == null || post.getDefunct() || post.getCreateUid() == uid) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		PostResponseExample example = new PostResponseExample();
		example.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId);
		if (postResponseMapper.countByExample(example) > 0) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		postDao.incrOrDecrResponseCnt(postId, 1);
		PostResponse postResponse = new PostResponse();
		postResponse.setUid(uid);
		postResponse.setPostId(postId);
		postResponse.setCreateTime(new Date());
		postResponse.setLastModifyTime(postResponse.getCreateTime());
		postResponseMapper.insertSelective(postResponse);

		// response列表缓存
		longRedisTemplate.opsForSet().add(
				RedisKeyGenerator.genResponsePostsKey(uid), postId);
	}

	@Override
	public void deletePost(long uid, long postId) throws InputPostException {
		Post post = postMapper.selectByPrimaryKey(postId);
		if (post == null || post.getCreateUid() != uid) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		if (post.getIdeaId() > 0) {
			ideaService.removeUser(post.getIdeaId(), uid);
		}
		PostResponseExample example = new PostResponseExample();
		example.createCriteria().andPostIdEqualTo(postId);
		List<PostResponse> prList = postResponseMapper.selectByExample(example);
		for (PostResponse pr : prList) {
			longRedisTemplate.opsForSet().remove(
					RedisKeyGenerator.genResponsePostsKey(pr.getUid()), postId);
		}
		postResponseMapper.deleteByExample(example);
		postMapper.deleteByPrimaryKey(postId);

		// 更新用户最新一条拒宅
		Post latestPost = getUserLatestPost(uid);
		if (null != latestPost && latestPost.getId() == postId) {
			PostExample postExample = new PostExample();
			postExample.createCriteria().andCreateUidEqualTo(uid)
					.andDefunctEqualTo(false);
			postExample.setLimit(new Limit(0, 1));
			postExample.setOrderByClause("create_time desc");
			List<Post> list = postMapper.selectByExample(postExample);
			if (CollectionUtils.isNotEmpty(list)) {
				setUserLatestPost(uid, list.get(0));
			} else {
				redisTemplate.delete(RedisKeyGenerator
						.genUserLatestPostKey(uid));
			}
		}
	}

	// TODO 代码重构
	@Override
	public void deletePost(long postId) throws InputPostException {
		Post post = postMapper.selectByPrimaryKey(postId);
		if (post == null) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		long uid = post.getCreateUid();
		if (post.getIdeaId() > 0) {
			ideaService.removeUser(post.getIdeaId(), uid);
		}
		PostResponseExample example = new PostResponseExample();
		example.createCriteria().andPostIdEqualTo(postId);
		List<PostResponse> prList = postResponseMapper.selectByExample(example);
		for (PostResponse pr : prList) {
			longRedisTemplate.opsForSet().remove(
					RedisKeyGenerator.genResponsePostsKey(pr.getUid()), postId);
		}
		postResponseMapper.deleteByExample(example);
		// 标注删除的拒宅信息
		post.setDefunct(true);
		postMapper.updateByPrimaryKeySelective(post);

		// 更新用户最新一条拒宅
		Post latestPost = getUserLatestPost(uid);
		if (null != latestPost && latestPost.getId() == postId) {
			PostExample postExample = new PostExample();
			postExample.createCriteria().andCreateUidEqualTo(uid)
					.andDefunctEqualTo(false);
			postExample.setLimit(new Limit(0, 1));
			postExample.setOrderByClause("create_time desc");
			List<Post> list = postMapper.selectByExample(postExample);
			if (CollectionUtils.isNotEmpty(list)) {
				setUserLatestPost(uid, list.get(0));
			} else {
				redisTemplate.delete(RedisKeyGenerator
						.genUserLatestPostKey(uid));
			}
		}
	}

	@Override
	public void shieldPost(long postId) throws InputPostException {
		// TODO (done) 传入负数呢？类似问题不要再犯了
		if (postId <= 0) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		Post post = new Post();
		post.setId(postId);
		post.setLastModifyTime(new Date());
		post.setVerifyType(VerifyType.SHIELD.getType());
		postMapper.updateByPrimaryKeySelective(post);

	}

	@Override
	public void unShieldPost(long postId) throws InputPostException {
		// TODO (done) 传入负数呢？类似问题不要再犯了
		if (postId <= 0) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		Post post = new Post();
		post.setId(postId);
		post.setLastModifyTime(new Date());
		post.setVerifyType(VerifyType.QUALIFIED.getType());
		postMapper.updateByPrimaryKeySelective(post);

	}

	@Override
	public void handlePost(List<Long> postIds) throws InputPostException {
		if (CollectionUtils.isEmpty(postIds)) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		// TODO (done) 为什么不用in？
		PostExample example=new PostExample();
		Post post = new Post();
		post.setLastModifyTime(new Date());
		post.setVerifyType(VerifyType.QUALIFIED.getType());
		example.createCriteria().andIdeaIdIn(postIds);
		postMapper.updateByExampleSelective(post, example);
		
	}

	@Override
	public void markIdea(long postId, long ideaId) throws InputPostException {
		// TODO (done) 不需要select一下
		if (postId<=0) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		Post post=new Post();
		post.setId(postId);
		post.setLastModifyTime(new Date());
		post.setIdeaId(ideaId);
		post.setVerifyType(VerifyType.QUALIFIED.getType());
		postMapper.updateByPrimaryKeySelective(post);
	}

	private void setUserLatestPost(long uid, Post post) {
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genUserLatestPostKey(uid), post);
	}

	@Override
	public Post getUserLatestPost(long uid) {
		return redisTemplate.opsForValue().get(
				RedisKeyGenerator.genUserLatestPostKey(uid));
	}

	@Override
	public List<Post> listNewestPost(long uid, Long cityId, Integer gender,
			int firstResult, int maxResults) {
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria()
				.andCreateUidNotEqualTo(uid).andVerifyTypeNotEqualTo(2)
				.andDefunctEqualTo(false);
		if (null != cityId && cityId > 0) {
			c.andUserCityEqualTo(cityId);
		}
		if (null != gender && gender >= 0) {
			c.andUserGenderEqualTo(gender);
		}
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postMapper.selectByExample(example);
	}

	@Override
	public int countNewestPost(long uid, Long cityId, Integer gender) {
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria()
				.andCreateUidNotEqualTo(uid).andVerifyTypeNotEqualTo(2)
				.andDefunctEqualTo(false);
		if (null != cityId && cityId > 0) {
			c.andUserCityEqualTo(cityId);
		}
		if (null != gender && gender >= 0) {
			c.andUserGenderEqualTo(gender);
		}
		return postMapper.countByExample(example);
	}

	@Override
	public List<Post> listResponsePost(long uid, Long cityId, Integer gender,
			int firstResult, int maxResults) {
		List<Long> postIds = responsePostIds(uid);
		if (CollectionUtils.isEmpty(postIds)) {
			return Collections.emptyList();
		}
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria().andIdIn(postIds)
				.andVerifyTypeNotEqualTo(2).andDefunctEqualTo(false);
		if (null != cityId && cityId > 0) {
			c.andUserCityEqualTo(cityId);
		}
		if (null != gender && gender >= 0) {
			c.andUserGenderEqualTo(gender);
		}
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postMapper.selectByExample(example);
	}

	@Override
	public int countResponsePost(long uid, Long cityId, Integer gender) {
		List<Long> postIds = responsePostIds(uid);
		if (CollectionUtils.isEmpty(postIds)) {
			return 0;
		}
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria().andIdIn(postIds)
				.andVerifyTypeNotEqualTo(2).andDefunctEqualTo(false);
		if (null != cityId && cityId > 0) {
			c.andUserCityEqualTo(cityId);
		}
		if (null != gender && gender >= 0) {
			c.andUserGenderEqualTo(gender);
		}
		return postMapper.countByExample(example);
	}

	@Override
	public List<Post> listInterestUserPost(long uid, Long cityId,
			Integer gender, int firstResult, int maxResults) {
		List<Long> uids = interestUserService.interestUids(uid);
		if (CollectionUtils.isEmpty(uids)) {
			return Collections.emptyList();
		}
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria().andCreateUidIn(uids)
				.andVerifyTypeNotEqualTo(2).andDefunctEqualTo(false);
		if (null != cityId && cityId > 0) {
			c.andUserCityEqualTo(cityId);
		}
		if (null != gender && gender >= 0) {
			c.andUserGenderEqualTo(gender);
		}
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postMapper.selectByExample(example);
	}

	@Override
	public int countInterestUserPost(long uid, Long cityId, Integer gender) {
		List<Long> uids = interestUserService.interestUids(uid);
		if (CollectionUtils.isEmpty(uids)) {
			return 0;
		}
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria().andCreateUidIn(uids)
				.andVerifyTypeNotEqualTo(2).andDefunctEqualTo(false);
		if (null != cityId && cityId > 0) {
			c.andUserCityEqualTo(cityId);
		}
		if (null != gender && gender >= 0) {
			c.andUserGenderEqualTo(gender);
		}
		return postMapper.countByExample(example);
	}

	@Override
	public List<Long> responsePostIds(long uid) {
		Set<Long> postIds = longRedisTemplate.opsForSet().members(
				RedisKeyGenerator.genResponsePostsKey(uid));
		if (CollectionUtils.isEmpty(postIds)) {
			return Collections.emptyList();
		} else {
			return new ArrayList<Long>(postIds);
		}
	}

	@Override
	public boolean isResponsePost(long uid, long postId) {
		return longRedisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genResponsePostsKey(uid), postId);
	}

	@Override
	public List<Post> listUserPost(long uid, int firstResult, int maxResults) {
		PostExample example = new PostExample();
		example.createCriteria().andCreateUidEqualTo(uid)
				.andDefunctEqualTo(false);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postMapper.selectByExample(example);
	}

	@Override
	public int countUserPost(long uid) {
		PostExample example = new PostExample();
		example.createCriteria().andCreateUidEqualTo(uid)
				.andDefunctEqualTo(false);
		return postMapper.countByExample(example);
	}

	@Override
	public List<Post> listUnhandlePost(int firstResult, int maxResults) {
		return cmsListPost(VerifyType.RAW, firstResult, maxResults);
	}

	@Override
	public List<Post> listShieldPost(int firstResult, int maxResults) {
		return cmsListPost(VerifyType.SHIELD, firstResult, maxResults);
	}

	@Override
	public List<Post> listHandlePost(int firstResult, int maxResults) {
		return cmsListPost(VerifyType.QUALIFIED, firstResult, maxResults);
	}

	private List<Post> cmsListPost(VerifyType verifyType, int firstResult,
			int maxResults) {
		PostExample example = new PostExample();
		example.createCriteria().andVerifyTypeEqualTo(verifyType.getType())
				.andDefunctEqualTo(false);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postMapper.selectByExample(example);
	}

	@Override
	public int countUnhandlePost() {
		return cmsCountPost(VerifyType.RAW);
	}

	@Override
	public int countShieldPost() {
		return cmsCountPost(VerifyType.SHIELD);
	}

	@Override
	public int countHandlePost() {
		return cmsCountPost(VerifyType.QUALIFIED);
	}

	private int cmsCountPost(VerifyType verifyType) {
		PostExample example = new PostExample();
		example.createCriteria().andVerifyTypeEqualTo(verifyType.getType())
				.andDefunctEqualTo(false);
		return postMapper.countByExample(example);
	}

}
