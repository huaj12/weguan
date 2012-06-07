package com.juzhai.post.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.bean.FunctionLevel;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;
import com.juzhai.home.service.IBlacklistService;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.post.controller.form.PostCommentForm;
import com.juzhai.post.dao.IPostDao;
import com.juzhai.post.exception.InputPostCommentException;
import com.juzhai.post.mapper.PostCommentMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostComment;
import com.juzhai.post.model.PostCommentExample;
import com.juzhai.post.service.IPostCommentService;
import com.juzhai.post.service.IPostService;
import com.juzhai.stats.counter.service.ICounter;
import com.juzhai.wordfilter.service.IWordFilterService;

@Service
public class PostCommentService implements IPostCommentService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IPostDao postDao;
	@Autowired
	private PostCommentMapper postCommentMapper;
	@Autowired
	private IPostService postService;
	@Autowired
	private IWordFilterService wordFilterService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private ICounter postCommentCounter;
	@Autowired
	private IBlacklistService blacklistService;
	@Value("${post.comment.content.length.min}")
	private int postCommentContentLengthMin;
	@Value("${post.comment.content.length.max}")
	private int postCommentContentLengthMax;
	@Value("${comment.content.wordfilter.application}")
	private int commentContentWordfilterApplication;

	@Override
	public PostComment comment(long uid, PostCommentForm form)
			throws InputPostCommentException {
		if (!passportService.isUse(FunctionLevel.COMMENT, uid)) {
			throw new InputPostCommentException(
					InputPostCommentException.COMMENT_USE_LOW_LEVEL);
		}

		if (form.getPostId() <= 0) {
			throw new InputPostCommentException(
					InputPostCommentException.POST_ID_NOT_EXIST);
		}
		Post post = postService.getPostById(form.getPostId());
		if (null == post || post.getDefunct()) {
			throw new InputPostCommentException(
					InputPostCommentException.POST_ID_NOT_EXIST);
		}
		if (blacklistService.isShield(post.getCreateUid(), uid)) {
			throw new InputPostCommentException(
					InputPostCommentException.COMMENT_BLACKLIST_USER);
		}
		PostComment parentComment = null;
		if (form.getParentId() > 0) {
			parentComment = postCommentMapper.selectByPrimaryKey(form
					.getParentId());
		}
		if (parentComment != null
				&& blacklistService.isShield(parentComment.getCreateUid(), uid)) {
			throw new InputPostCommentException(
					InputPostCommentException.COMMENT_BLACKLIST_USER);
		}

		// 验证内容
		int contentLength = StringUtil.chineseLength(form.getContent());
		if (contentLength < postCommentContentLengthMin
				|| contentLength > postCommentContentLengthMax) {
			throw new InputPostCommentException(
					InputPostCommentException.COMMENT_CONTENT_LENGTH_ERROR);
		}
		// 验证屏蔽字
		try {
			if (wordFilterService.wordFilter(
					commentContentWordfilterApplication, uid, null, form
							.getContent().getBytes("GBK")) < 0) {
				throw new InputPostCommentException(
						InputPostCommentException.COMMENT_CONTENT_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}
		// 内容重复
		String contentMd5 = null;
		if (StringUtils.isNotEmpty(form.getContent())) {
			contentMd5 = DigestUtils.md5Hex(form.getContent());
		}
		PostCommentExample example = new PostCommentExample();
		example.createCriteria().andCreateUidEqualTo(uid)
				.andContentMd5EqualTo(contentMd5)
				.andPostIdEqualTo(post.getId());
		if (postCommentMapper.countByExample(example) > 0) {
			throw new InputPostCommentException(
					InputPostCommentException.COMMENT_CONTENT_DUPLICATE);
		}

		PostComment postComment = new PostComment();
		postComment.setPostId(post.getId());
		postComment.setPostContent(post.getContent());
		postComment.setPostCreateUid(post.getCreateUid());
		postComment.setContent(form.getContent());
		postComment.setContentMd5(contentMd5);
		postComment.setCreateUid(uid);
		postComment.setCreateTime(new Date());
		postComment.setLastModifyTime(postComment.getCreateTime());
		postComment.setDefunct(false);
		postComment.setParentId(parentComment == null ? 0L : parentComment
				.getId());
		postComment.setParentCreateUid(parentComment == null ? 0L
				: parentComment.getCreateUid());
		postComment.setParentContent(parentComment == null ? StringUtils.EMPTY
				: parentComment.getContent());

		postCommentMapper.insertSelective(postComment);

		// 加留言数
		postDao.incrOrDecrCommentCnt(post.getId(), 1);

		Set<Long> noticeUids = new HashSet<Long>();
		// 通知
		if (uid != postComment.getPostCreateUid()) {
			noticeUids.add(postComment.getPostCreateUid());
		}
		if (postComment.getParentCreateUid() > 0
				&& uid != postComment.getParentCreateUid()) {
			noticeUids.add(postComment.getParentCreateUid());
		}
		for (Long noticeUid : noticeUids) {
			if (null != noticeUid && noticeUid > 0) {
				addToCommentInbox(noticeUid, postComment.getId());
			}
		}
		postCommentCounter.incr(null, 1L);
		return postComment;
	}

	@Override
	public void defunctComment(long postId) {
		PostCommentExample example = new PostCommentExample();
		example.createCriteria().andPostIdEqualTo(postId);
		PostComment postComment = new PostComment();
		postComment.setDefunct(true);
		postCommentMapper.updateByExampleSelective(postComment, example);
	}

	@Override
	public void deleteComment(long uid, long postCommentId)
			throws InputPostCommentException {
		PostComment postComment = postCommentMapper
				.selectByPrimaryKey(postCommentId);
		if (null == postComment) {
			return;
		}
		if (postComment.getPostCreateUid() != uid
				&& postComment.getCreateUid() != uid) {
			Passport passport = passportService.getPassportByUid(uid);
			if (null == passport || !passport.getAdmin()) {
				throw new InputPostCommentException(
						InputPostCommentException.ILLEGAL_OPERATION);
			}
		}

		// 通知里删除
		if (postComment.getCreateUid().longValue() != postComment
				.getPostCreateUid().longValue()) {
			removeFromCommentInbox(postComment.getPostCreateUid(),
					postComment.getId());
		}
		if (postComment.getParentCreateUid() > 0
				&& postComment.getParentCreateUid().longValue() != postComment
						.getCreateUid().longValue()) {
			removeFromCommentInbox(postComment.getParentCreateUid(),
					postComment.getId());
		}
		// 加留言数
		postDao.incrOrDecrCommentCnt(postComment.getPostId(), -1);
		postCommentMapper.deleteByPrimaryKey(postComment.getId());
	}

	private void addToCommentInbox(long uid, long postCommentId) {
		redisTemplate.opsForZSet().add(
				RedisKeyGenerator.genCommentInboxKey(uid), postCommentId,
				System.currentTimeMillis());
		noticeService.incrNotice(uid, NoticeType.COMMENT);
	}

	private void removeFromCommentInbox(long uid, long postCommentId) {
		redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genCommentInboxKey(uid), postCommentId);
	}

	@Override
	public List<PostComment> listInbox(long uid, int firstResult, int maxResults) {
		Set<Long> ids = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genCommentInboxKey(uid), firstResult,
				firstResult + maxResults - 1);
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		PostCommentExample example = new PostCommentExample();
		example.createCriteria().andIdIn(new ArrayList<Long>(ids));
		example.setOrderByClause("create_time desc");
		return postCommentMapper.selectByExample(example);
	}

	@Override
	public List<PostComment> listOutbox(long uid, int firstResult,
			int maxResults) {
		PostCommentExample example = new PostCommentExample();
		example.createCriteria().andCreateUidEqualTo(uid);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postCommentMapper.selectByExample(example);
	}

	@Override
	public int countInbox(long uid) {
		Long count = redisTemplate.opsForZSet().size(
				RedisKeyGenerator.genCommentInboxKey(uid));
		return count == null ? 0 : count.intValue();
	}

	@Override
	public int countOutbox(long uid) {
		PostCommentExample example = new PostCommentExample();
		example.createCriteria().andCreateUidEqualTo(uid);
		return postCommentMapper.countByExample(example);
	}

	@Override
	public List<PostComment> listPostComment(long postId, int firstResult,
			int maxResults) {
		PostCommentExample example = new PostCommentExample();
		example.createCriteria().andPostIdEqualTo(postId);
		example.setOrderByClause("create_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return postCommentMapper.selectByExample(example);
	}

	@Override
	public int countPostComment(long postId) {
		PostCommentExample example = new PostCommentExample();
		example.createCriteria().andPostIdEqualTo(postId);
		return postCommentMapper.countByExample(example);
	}

	@Override
	public int totalCount() {
		return postCommentMapper.countByExample(new PostCommentExample());
	}
}
