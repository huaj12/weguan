package com.juzhai.post.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.cms.controller.view.CmsPostView;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.bean.ShowPostOrder;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.ISynchronizeService;
import com.juzhai.post.InitData;
import com.juzhai.post.bean.PostResult;
import com.juzhai.post.bean.PurposeType;
import com.juzhai.post.bean.SynchronizeWeiboTemplate;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.dao.IPostDao;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.mapper.PostResponseMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.model.PostExample.Criteria;
import com.juzhai.post.model.PostResponse;
import com.juzhai.post.model.PostResponseExample;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostCommentService;
import com.juzhai.post.service.IPostImageService;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.stats.counter.service.ICounter;
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
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IWordFilterService wordFilterService;
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ICounter responsePostCounter;
	@Autowired
	private ICounter auditPostCounter;
	@Autowired
	private ICounter postIdeaCounter;
	@Autowired
	private ICounter postCounter;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private ISynchronizeService synchronizeService;
	@Autowired
	private IPostCommentService postCommentService;
	@Autowired
	private IPostSearchService postSearchService;
	@Autowired
	private ProfileMapper profileMapper;
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
	@Value("${all.response.cnt.expire.time}")
	private int allResponseCntExpireTime;
	@Value("${synchronize.title.length.max}")
	private int synchronizeTitleLengthMax;
	@Value("${post.response.content.length.min}")
	private int postResponseContentLengthMin;
	@Value("${post.response.content.length.max}")
	private int postResponseContentLengthMax;
	@Value("${dialog.content.wordfilter.application}")
	private int dialogContentWordfilterApplication;
	@Value("${synchronize.place.length.max}")
	private int synchronizePlaceLengthMax;

	@Override
	public long createPost(long uid, PostForm postForm)
			throws InputPostException {
		// Boolean isForbid = null;
		// try {
		// isForbid = memcachedClient.get(MemcachedKeyGenerator
		// .genPostForbidKey(uid));
		// } catch (Exception e) {
		// }
		// if (null != isForbid && isForbid) {
		// throw new InputPostException(InputPostException.POST_TOO_FREQUENT);
		// }
		// 判断是否有头像
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		if (StringUtils.isEmpty(profile.getLogoPic())
				&& profile.getLogoVerifyState() != LogoVerifyState.VERIFYING
						.getType()) {
			throw new InputPostException(InputPostException.PROFILE_LOGO_EMPTY);
		}
		long postId = 0L;
		if (postForm.getIdeaId() > 0) {
			// 发表from idea
			postId = createPostByIdea(uid, postForm);
		} else {
			postId = createPostByForm(uid, postForm);
		}
		// try {
		// memcachedClient.setWithNoReply(
		// MemcachedKeyGenerator.genPostForbidKey(uid),
		// postIntervalExpireTime, true);
		// } catch (Exception e) {
		// }
		return postId;
	}

	private long createPostByIdea(long uid, PostForm postForm)
			throws InputPostException {
		long ideaId = postForm.getIdeaId();
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
			return 0L;
		}

		Post post = new Post();
		post.setContent(idea.getContent());
		post.setContentMd5(idea.getContentMd5());
		post.setPurposeType(postForm.getPurposeType());
		post.setLink(idea.getLink());
		post.setPlace(idea.getPlace());
		if (idea.getStartTime() == null) {
			post.setDateTime(idea.getEndTime());
		}
		post.setCategoryId(idea.getCategoryId() <= 0 ? InitData.OTHER_CATEGORY_ID
				: idea.getCategoryId());
		post.setIdeaId(idea.getId());
		post.setCity(idea.getCity());
		post.setPic(idea.getPic());

		post.setVerifyType(VerifyType.QUALIFIED.getType());

		createPost(uid, post, null, null, null);

		// 添加idea的useCount或者firstUser
		if (idea.getUseCount() == 0) {
			ideaService.addFirstUser(ideaId, uid);
		} else {
			ideaService.addUser(ideaId, uid);
		}
		if (post.getVerifyType().intValue() == VerifyType.QUALIFIED.getType()) {
			// 更新用户最新一条拒宅
			setUserLatestPost(post);
		}
		// 每日发布idea统计
		postIdeaCounter.incr(null, 1L);
		return post.getId();
	}

	private long createPostByForm(long uid, PostForm postForm)
			throws InputPostException {
		Post repost = null;
		if (postForm.getPostId() > 0) {
			// 转发
			repost = postMapper.selectByPrimaryKey(postForm.getPostId());
			if (repost == null || repost.getDefunct()
					|| repost.getCreateUid() == uid) {
				throw new InputPostException(
						InputPostException.ILLEGAL_OPERATION);
			}
		}
		Idea picIdea = null;
		if (postForm.getPicIdeaId() > 0) {
			picIdea = ideaService.getIdeaById(postForm.getPicIdeaId());
			if (null == picIdea) {
				throw new InputPostException(
						InputPostException.ILLEGAL_OPERATION);
			}
		}

		// 验证
		validatePostForm(uid, postForm);

		Post post = new Post();
		post.setContent(postForm.getContent());
		post.setContentMd5(postForm.getContentMd5());
		post.setLink(postForm.getLink());
		post.setCategoryId(postForm.getCategoryId() <= 0 ? InitData.OTHER_CATEGORY_ID
				: postForm.getCategoryId());
		post.setPlace(postForm.getPlace());
		post.setDateTime(postForm.getDate());
		post.setPurposeType(postForm.getPurposeType());

		createPost(uid, post, repost, picIdea, postForm.getPic());
		// 每日发布拒宅统计
		postCounter.incr(null, 1L);
		return post.getId();
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

	private void createPost(long uid, Post post, Post repost, Idea picIdea,
			String tmpImgFilePath) throws InputPostException {
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		if (null == profile) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		post.setCreateUid(uid);
		post.setCreateTime(new Date());
		post.setLastModifyTime(post.getCreateTime());
		post.setUserCity(profile.getCity());
		post.setUserGender(profile.getGender());
		if (StringUtils.isEmpty(profile.getLogoPic())) {
			post.setVerifyType(VerifyType.RAW.getType());
		}
		postMapper.insertSelective(post);

		if (StringUtils.isNotEmpty(tmpImgFilePath)) {
			String fileName = null;
			if (null != picIdea
					&& StringUtils.equals(picIdea.getPic(), tmpImgFilePath)) {
				postImageService.copyImgFromIdea(post.getId(), picIdea.getId(),
						picIdea.getPic());
				fileName = picIdea.getPic();
			} else if (null != repost
					&& StringUtils.equals(repost.getPic(), tmpImgFilePath)) {
				// 使用转发的图片
				if (repost.getIdeaId() != null && repost.getIdeaId() > 0) {
					postImageService.copyImgFromIdea(post.getId(),
							repost.getIdeaId(), repost.getPic());
				} else {
					postImageService.copyImgFromPost(post.getId(),
							repost.getId(), repost.getPic());
				}
				fileName = repost.getPic();
			} else {
				fileName = postImageService.saveImg(post.getId(),
						tmpImgFilePath);
			}
			if (StringUtils.isNotEmpty(fileName)) {
				Post updatePost = new Post();
				updatePost.setId(post.getId());
				updatePost.setPic(fileName);
				postMapper.updateByPrimaryKeySelective(updatePost);
			}
		}
		// 前台发布好主意。
		if (post.getVerifyType() != null
				&& VerifyType.QUALIFIED.getType() == post.getVerifyType()) {
			postSearchService.createIndex(post.getId());
		}

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
	public long modifyPost(long uid, PostForm postForm)
			throws InputPostException {
		Post post = postMapper.selectByPrimaryKey(postForm.getPostId());
		boolean flag = false;
		if (VerifyType.QUALIFIED.getType() == post.getVerifyType()) {
			flag = true;
		}
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
		// 处理图片
		if (StringUtils.isEmpty(postForm.getPic())) {
			// 删除了图片
			post.setPic(StringUtils.EMPTY);
		} else if (!StringUtils.equals(post.getPic(), postForm.getPic())) {
			// 重新上传
			String fileName = postImageService.saveImg(post.getId(),
					postForm.getPic());
			if (StringUtils.isNotEmpty(fileName)) {
				post.setPic(fileName);
			}
		} else if (breakIdeaId > 0) {
			// 图片复制
			postImageService.copyImgFromIdea(post.getId(), breakIdeaId,
					postForm.getPic());
		}

		post.setContent(postForm.getContent());
		post.setContentMd5(postForm.getContentMd5());
		// post.setLink(postForm.getLink());
		post.setCategoryId(postForm.getCategoryId() <= 0 ? InitData.OTHER_CATEGORY_ID
				: postForm.getCategoryId());
		post.setPlace(postForm.getPlace());
		post.setDateTime(postForm.getDate());
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
		// 用户修改通过状态的拒宅，修改前通过修改后不通过
		if (flag && VerifyType.QUALIFIED.getType() != post.getVerifyType()) {
			postSearchService.deleteIndex(post.getId());
		}
		return post.getId();
	}

	@Override
	public void responsePost(long uid, long postId, String content)
			throws InputPostException {
		// validate content
		int contentLength = StringUtil.chineseLength(content);
		if (contentLength < postResponseContentLengthMin
				|| contentLength > postResponseContentLengthMax) {
			throw new InputPostException(
					InputPostException.POST_RESPONSE_CONTENT_LENGTH_ERROR);
		}
		try {
			if (wordFilterService.wordFilter(
					dialogContentWordfilterApplication, uid, null,
					content.getBytes("GBK")) < 0) {
				throw new InputPostException(
						InputPostException.POST_RESPONSE_CONTENT_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}
		Post post = postMapper.selectByPrimaryKey(postId);
		if (post == null || post.getDefunct() || post.getCreateUid() == uid) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		PostResponseExample example = new PostResponseExample();
		example.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId);
		if (postResponseMapper.countByExample(example) > 0) {
			return;
			// throw new
			// InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		postDao.incrOrDecrResponseCnt(postId, 1);
		PostResponse postResponse = new PostResponse();
		postResponse.setUid(uid);
		postResponse.setPostId(postId);
		postResponse.setCreateTime(new Date());
		postResponse.setLastModifyTime(postResponse.getCreateTime());
		postResponseMapper.insertSelective(postResponse);

		// response列表缓存
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genResponsePostsKey(uid), postId);
		// 发送私信
		try {
			dialogService.sendSMS(uid, post.getCreateUid(),
					DialogContentTemplate.RESPONSE_POST, post.getContent(),
					content == null ? StringUtils.EMPTY : content);
		} catch (DialogException e) {
		}

		responsePostCounter.incr(null, 1L);
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
			redisTemplate.opsForSet().remove(
					RedisKeyGenerator.genResponsePostsKey(pr.getUid()), postId);
		}
		postResponseMapper.deleteByExample(example);
		postMapper.deleteByPrimaryKey(postId);
		postCommentService.defunctComment(postId);
		updateUserLatestPost(post);
		// 用户删除通过状态的拒宅
		if (VerifyType.QUALIFIED.getType() == post.getVerifyType()) {
			postSearchService.deleteIndex(postId);
		}
	}

	// TODO 代码重构
	@Override
	public void deletePost(long postId) throws InputPostException {
		Post post = postMapper.selectByPrimaryKey(postId);
		if (post == null) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		if (post.getIdeaId() > 0) {
			throw new InputPostException(
					InputPostException.POST_DEL_IDEA_ID_EXIST);
			// ideaService.removeUser(post.getIdeaId(), uid);
		}
		PostResponseExample example = new PostResponseExample();
		example.createCriteria().andPostIdEqualTo(postId);
		List<PostResponse> prList = postResponseMapper.selectByExample(example);
		for (PostResponse pr : prList) {
			redisTemplate.opsForSet().remove(
					RedisKeyGenerator.genResponsePostsKey(pr.getUid()), postId);
		}
		postResponseMapper.deleteByExample(example);
		// 标注删除的拒宅信息
		post.setDefunct(true);
		postMapper.updateByPrimaryKeySelective(post);
		postCommentService.defunctComment(postId);
		updateUserLatestPost(post);
		if (VerifyType.QUALIFIED.getType() == post.getVerifyType()) {
			// 后台删除通过状态的拒宅
			postSearchService.deleteIndex(postId);
		}
	}

	@Override
	public void shieldPost(long postId) throws InputPostException {
		Post post = postMapper.selectByPrimaryKey(postId);
		if (postId <= 0) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		boolean flag = false;
		if (VerifyType.QUALIFIED.getType() == post.getVerifyType()) {
			flag = true;
		}
		post.setLastModifyTime(new Date());
		post.setVerifyType(VerifyType.SHIELD.getType());
		postMapper.updateByPrimaryKeySelective(post);
		updateUserLatestPost(getPostById(postId));
		// 后台屏蔽通过状态的拒宅
		if (flag) {
			postSearchService.deleteIndex(postId);
		}
	}

	@Override
	public void handlePost(List<Long> postIds) throws InputPostException {
		if (CollectionUtils.isEmpty(postIds)) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		PostExample example = new PostExample();
		Post post = new Post();
		post.setLastModifyTime(new Date());
		post.setVerifyType(VerifyType.QUALIFIED.getType());
		example.createCriteria().andIdIn(postIds);
		if (postMapper.updateByExampleSelective(post, example) > 0) {
			auditPostCounter.incr(null, 1L);
		}
		for (Long postId : postIds) {
			if (postId != null && postId > 0) {
				setUserLatestPost(getPostById(postId));
				// 后台拒宅通过审核 取消屏蔽
				postSearchService.createIndex(postId);
			}
		}

		// 通过审核

	}

	@Override
	public void markIdea(long postId, long ideaId) throws InputPostException {
		if (postId <= 0) {
			throw new InputPostException(InputPostException.ILLEGAL_OPERATION);
		}
		Post post = new Post();
		post.setId(postId);
		post.setLastModifyTime(new Date());
		post.setIdeaId(ideaId);
		post.setVerifyType(VerifyType.QUALIFIED.getType());
		postMapper.updateByPrimaryKeySelective(post);

		setUserLatestPost(getPostById(postId));
	}

	private void setUserLatestPost(Post post) {
		if (null == post) {
			if (log.isDebugEnabled()) {
				log.debug("set latest post is null.");
			}
			return;
		}
		Long latestPostId = getUserLatestPost(post.getCreateUid());
		if (null == latestPostId
				|| latestPostId.longValue() != post.getId().longValue()) {
			Profile profile = profileService.getProfile(post.getCreateUid());
			if (null != profile
					&& (profile.getLastUpdateTime() == null || profile
							.getLastUpdateTime().before(post.getCreateTime()))) {
				profileService.updateLastUpdateTime(post.getCreateUid(),
						post.getCreateTime());
				redisTemplate.opsForValue().set(
						RedisKeyGenerator.genUserLatestPostKey(post
								.getCreateUid()), post.getId());
			}
		}
	}

	private void updateUserLatestPost(Post delPost) {
		if (null == delPost) {
			if (log.isDebugEnabled()) {
				log.debug("update latest post is null.");
			}
			return;
		}
		long uid = delPost.getCreateUid();
		// 更新用户最新一条拒宅
		Long latestPostId = getUserLatestPost(uid);
		if (null != latestPostId
				&& latestPostId.longValue() == delPost.getId().longValue()) {
			PostExample postExample = new PostExample();
			postExample.createCriteria().andCreateUidEqualTo(uid)
					.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
					.andDefunctEqualTo(false);
			postExample.setLimit(new Limit(0, 1));
			postExample.setOrderByClause("create_time desc");
			List<Post> list = postMapper.selectByExample(postExample);
			if (CollectionUtils.isNotEmpty(list)) {
				Post post = list.get(0);
				// 更新
				profileService.updateLastUpdateTime(uid, post.getCreateTime());
				redisTemplate.opsForValue().set(
						RedisKeyGenerator.genUserLatestPostKey(uid),
						post.getId());
			} else {
				profileService.delLastUpdateTime(uid);
				redisTemplate.delete(RedisKeyGenerator
						.genUserLatestPostKey(uid));
			}

		}
	}

	@Override
	public Long getUserLatestPost(long uid) {
		return redisTemplate.opsForValue().get(
				RedisKeyGenerator.genUserLatestPostKey(uid));
	}

	@Override
	public PostResult listNewOrOnlinePosts(long uid, Long cityId, Long townId,
			Integer gender, ShowPostOrder order, int firstResult, int maxResults) {
		PostResult result = new PostResult();
		ProfileExample example = getNewestPostExample(uid, cityId, townId,
				gender);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause(order.getColumn() + " desc");
		List<Profile> profileList = profileMapper.selectByExample(example);
		List<Long> uidList = new ArrayList<Long>();
		Map<Long, Profile> profileMap = new HashMap<Long, Profile>(
				profileList.size());
		for (Profile profile : profileList) {
			profileMap.put(profile.getUid(), profile);
			uidList.add(profile.getUid());
		}
		Map<Long, Post> postMap = getMultiUserLatestPosts(uidList);
		List<Post> postList = new ArrayList<Post>();
		for (long userId : uidList) {
			Post post = postMap.get(userId);
			if (null != post) {
				postList.add(post);
			}
		}
		result.setPosts(postList);
		result.setProfileMap(profileMap);
		return result;
	}

	@Override
	public int countNewestPost(long uid, Long cityId, Long townId,
			Integer gender) {
		ProfileExample example = getNewestPostExample(uid, cityId, townId,
				gender);
		return profileMapper.countByExample(example);
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
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
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
	public int countResponsePost(long uid, Long cityId, Integer gender) {
		List<Long> postIds = responsePostIds(uid);
		if (CollectionUtils.isEmpty(postIds)) {
			return 0;
		}
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria().andIdIn(postIds)
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
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
	public List<Post> listInterestUserPost(long uid, Long cityId,
			Integer gender, int firstResult, int maxResults) {
		List<Long> uids = interestUserService.interestUids(uid);
		if (CollectionUtils.isEmpty(uids)) {
			return Collections.emptyList();
		}
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria().andCreateUidIn(uids)
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
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
	public int countInterestUserPost(long uid, Long cityId, Integer gender) {
		List<Long> uids = interestUserService.interestUids(uid);
		if (CollectionUtils.isEmpty(uids)) {
			return 0;
		}
		PostExample example = new PostExample();
		PostExample.Criteria c = example.createCriteria().andCreateUidIn(uids)
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
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
	public List<Long> responsePostIds(long uid) {
		Set<Long> postIds = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genResponsePostsKey(uid));
		if (CollectionUtils.isEmpty(postIds)) {
			return Collections.emptyList();
		} else {
			return new ArrayList<Long>(postIds);
		}
	}

	@Override
	public boolean isResponsePost(long uid, long postId) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genResponsePostsKey(uid), postId);
	}

	@Override
	public List<Post> listUserPost(long uid, List<Long> excludePostIds,
			int firstResult, int maxResults) {
		PostExample example = new PostExample();
		Criteria criteria = example.createCriteria();
		criteria.andCreateUidEqualTo(uid).andDefunctEqualTo(false);
		if (CollectionUtils.isNotEmpty(excludePostIds)) {
			criteria.andIdNotIn(excludePostIds);
		}
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
	public List<Post> listUnhandlePost(long city, int firstResult,
			int maxResults) {
		return cmsListPost(city, null, VerifyType.RAW, firstResult, maxResults);
	}

	@Override
	public List<Post> listShieldPost(long city, int firstResult, int maxResults) {
		return cmsListPost(city, null, VerifyType.SHIELD, firstResult,
				maxResults);
	}

	@Override
	public List<Post> listHandlePost(long city, Integer gender,
			int firstResult, int maxResults) {
		return cmsListPost(city, gender, VerifyType.QUALIFIED, firstResult,
				maxResults);
	}

	private List<Post> cmsListPost(long city, Integer gender,
			VerifyType verifyType, int firstResult, int maxResults) {
		PostExample example = new PostExample();
		PostExample.Criteria criteria = example.createCriteria();
		criteria.andVerifyTypeEqualTo(verifyType.getType()).andDefunctEqualTo(
				false);
		if (city > 0) {
			criteria.andUserCityEqualTo(city);
		}
		if (gender != null) {
			criteria.andUserGenderEqualTo(gender);
		}
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postMapper.selectByExample(example);
	}

	@Override
	public int countUnhandlePost(long city) {
		return cmsCountPost(VerifyType.RAW, city, null);
	}

	@Override
	public int countShieldPost(long city) {
		return cmsCountPost(VerifyType.SHIELD, city, null);
	}

	@Override
	public int countHandlePost(long city, Integer gender) {
		return cmsCountPost(VerifyType.QUALIFIED, city, gender);
	}

	private int cmsCountPost(VerifyType verifyType, long city, Integer gender) {
		PostExample example = new PostExample();
		PostExample.Criteria criteria = example.createCriteria();
		if (city > 0) {
			criteria.andUserCityEqualTo(city);
		}
		if (gender != null) {
			criteria.andUserGenderEqualTo(gender);
		}
		criteria.andVerifyTypeEqualTo(verifyType.getType()).andDefunctEqualTo(
				false);
		return postMapper.countByExample(example);
	}

	@Override
	public Post getPostById(long postId) {
		return postMapper.selectByPrimaryKey(postId);
	}

	@Override
	public List<ProfileCache> listResponseUser(long postId, int firstResult,
			int maxResults) {
		List<PostResponse> list = listPostResponse(postId, firstResult,
				maxResults);
		List<ProfileCache> profileList = new ArrayList<ProfileCache>(
				list.size());
		for (PostResponse pr : list) {
			profileList.add(profileService.getProfileCacheByUid(pr.getUid()));
		}
		return profileList;
	}

	@Override
	public List<PostResponse> listPostResponse(long postId, int firstResult,
			int maxResults) {
		PostResponseExample example = new PostResponseExample();
		example.createCriteria().andPostIdEqualTo(postId);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postResponseMapper.selectByExample(example);
	}

	@Override
	public int countResponseUser(long postId) {
		PostResponseExample example = new PostResponseExample();
		example.createCriteria().andPostIdEqualTo(postId);
		return postResponseMapper.countByExample(example);
	}

	@Override
	public void synchronizeWeibo(long uid, long tpId, long postId) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (authInfo == null) {
			return;
		}
		Post post = getPostById(postId);
		String purposeType = messageSource.getMessage(
				"purpose.type." + post.getPurposeType(), null,
				Locale.SIMPLIFIED_CHINESE);
		String content = purposeType + post.getContent();
		String place = post.getPlace();
		String time = null;
		if (post.getDateTime() != null) {
			time = DateFormat.SDF.format(post.getDateTime());
		}

		try {
			if (StringUtils.isNotEmpty(time)) {
				time = " "
						+ messageSource.getMessage(
								SynchronizeWeiboTemplate.SYNCHRONIZE_TIME
										.getName(), new Object[] { time },
								Locale.SIMPLIFIED_CHINESE) + " ";
			}
			if (StringUtils.isNotEmpty(place)) {
				place = " "
						+ messageSource.getMessage(
								SynchronizeWeiboTemplate.SYNCHRONIZE_ADDRESS
										.getName(), new Object[] { place },
								Locale.SIMPLIFIED_CHINESE) + " ";
				place = TextTruncateUtil.truncate(place,
						synchronizePlaceLengthMax, "...");
			}

			String text = messageSource.getMessage(
					SynchronizeWeiboTemplate.SYNCHRONIZE_TEXT.getName(),
					new Object[] { content, time == null ? "" : time, place },
					Locale.SIMPLIFIED_CHINESE);
			String title = TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(content), synchronizeTitleLengthMax,
					"...");
			String link = messageSource.getMessage(
					SynchronizeWeiboTemplate.SYNCHRONIZE_LINK.getName(),
					new Object[] { String.valueOf(uid) },
					Locale.SIMPLIFIED_CHINESE);
			byte[] image = null;
			String imageUrl = null;
			if (StringUtils.isNotEmpty(post.getPic())) {
				image = postImageService.getPostFile(postId, post.getIdeaId(),
						post.getPic(), JzImageSizeType.ORIGINAL);
				imageUrl = JzResourceFunction.postPic(postId, post.getIdeaId(),
						post.getPic(), JzImageSizeType.MIDDLE.getType());
			}
			synchronizeService.sendMessage(authInfo, title, text, link, image,
					imageUrl);
		} catch (Exception e) {
			log.error("synchronizeWeibo is error " + e.getMessage());
		}
	}

	@Override
	public int getAllResponseCnt(long uid) {
		Integer cnt = null;
		try {
			cnt = memcachedClient.get(MemcachedKeyGenerator
					.genAllResponseCnt(uid));
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
		}
		if (null == cnt) {
			cnt = postDao.sumResponseCntByCreateUid(uid);
			try {
				memcachedClient.set(
						MemcachedKeyGenerator.genAllResponseCnt(uid),
						allResponseCntExpireTime, cnt);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(e.getMessage(), e);
				}
			}
		}
		return cnt;
	}

	@Override
	public Map<Long, Post> getMultiUserLatestPosts(List<Long> uidList) {
		List<Long> postIdList = new ArrayList<Long>();
		for (Long uid : uidList) {
			if (uid != null && uid > 0) {
				Long postId = getUserLatestPost(uid);
				if (postId != null && postId > 0) {
					postIdList.add(postId);
				}
			}
		}
		if (CollectionUtils.isEmpty(postIdList)) {
			return Collections.emptyMap();
		}
		PostExample example = new PostExample();
		example.createCriteria().andIdIn(postIdList);
		List<Post> postList = postMapper.selectByExample(example);
		Map<Long, Post> map = new HashMap<Long, Post>(postList.size());
		for (Post post : postList) {
			map.put(post.getCreateUid(), post);
		}
		return map;
	}

	@Override
	public int totalCount() {
		return postMapper.countByExample(new PostExample());
	}

	@Override
	public int responseTotalCount() {
		return postResponseMapper.countByExample(new PostResponseExample());
	}

	@Override
	public List<CmsPostView> getpost(int type, long id) {
		PostExample example = new PostExample();
		PostExample.Criteria criteria = example.createCriteria();
		// type=1根据uid查询
		// type=2根据post查询
		if (type == 1) {
			criteria.andCreateUidEqualTo(id);
		} else if (type == 2) {
			criteria.andIdEqualTo(id);
		}
		criteria.andDefunctEqualTo(false);
		example.setOrderByClause("create_time desc");
		List<Post> list = postMapper.selectByExample(example);
		List<CmsPostView> views = new ArrayList<CmsPostView>();
		for (Post post : list) {
			CmsPostView postView = new CmsPostView(post,
					profileService.getProfileCacheByUid(post.getCreateUid()));
			views.add(postView);
		}
		return views;
	}

	@Override
	public List<Post> getUserQualifiedPost(long uid, int firstResult,
			int maxResults) {
		PostExample example = new PostExample();
		example.createCriteria().andCreateUidEqualTo(uid)
				.andDefunctEqualTo(false)
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType());
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postMapper.selectByExample(example);
	}

	private ProfileExample getNewestPostExample(long uid, Long city, Long town,
			Integer gender) {
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria();
		if (null != gender) {
			c.andGenderEqualTo(gender);
		}
		if (null != city && city > 0) {
			c.andCityEqualTo(city);
		}
		if (null != town && town > 0) {
			c.andTownEqualTo(town);
		}
		if (uid != 0) {
			c.andUidNotEqualTo(uid);
		}
		c.andLastUpdateTimeIsNotNull();
		return example;
	}

}
