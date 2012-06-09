package com.juzhai.home.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.bean.FunctionLevel;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.mapper.DialogContentMapper;
import com.juzhai.home.mapper.DialogMapper;
import com.juzhai.home.model.Dialog;
import com.juzhai.home.model.DialogContent;
import com.juzhai.home.model.DialogExample;
import com.juzhai.home.service.IBlacklistService;
import com.juzhai.home.service.IDialogService;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.wordfilter.service.IWordFilterService;

@Service
public class DialogService implements IDialogService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private DialogMapper dialogMapper;
	@Autowired
	private DialogContentMapper dialogContentMapper;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IWordFilterService wordFilterService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private IBlacklistService blacklistService;
	@Value("${dialog.content.length.max}")
	private int dialogContentLengthMax;
	@Value("${dialog.content.length.min}")
	private int dialogContentLengthMin;
	@Value("${dialog.content.wordfilter.application}")
	private int dialogContentWordfilterApplication;
	@Value("${dialog.content.cache.expire.time}")
	private int dialogContentCacheExpireTime = 0;
	@Value("${official.notice.uid}")
	private long officialNoticeUid;

	@Override
	public long sendSMS(long uid, long targetUid, String content)
			throws DialogException {
		if (!passportService.isUse(FunctionLevel.SENDSMS, uid)) {
			throw new DialogException(DialogException.USE_LOW_LEVEL);
		}
		return sendContent(uid, targetUid, content);
	}

	private long sendContent(long uid, long targetUid, String content)
			throws DialogException {
		if (blacklistService.isShield(targetUid, uid)) {
			throw new DialogException(DialogException.DIALOG_BLACKLIST_USER);
		}
		if (null != content) {
			content = content.trim();
		}
		if (StringUtil.chineseLength(content) < dialogContentLengthMin
				|| StringUtil.chineseLength(content) > dialogContentLengthMax) {
			throw new DialogException(DialogException.DIALOG_CONTENT_INVALID);
		}
		try {
			if (wordFilterService.wordFilter(
					dialogContentWordfilterApplication, uid, null,
					content.getBytes("GBK")) < 0) {
				throw new DialogException(DialogException.DIALOG_CONTENT_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}

		long senderDialogId = updateDialog(uid, targetUid, false);
		long receiverDialogId = updateDialog(targetUid, uid, true);

		DialogContent dialogContent = new DialogContent();
		dialogContent.setContent(content);
		dialogContent.setSenderUid(uid);
		dialogContent.setReceiverUid(targetUid);
		dialogContent.setCreateTime(new Date());
		dialogContent.setLastModifyTime(dialogContent.getCreateTime());
		dialogContentMapper.insertSelective(dialogContent);

		// 插入关系
		redisTemplate.opsForList().leftPush(
				RedisKeyGenerator.genDialogContentsKey(senderDialogId),
				dialogContent.getId());
		redisTemplate.opsForList().leftPush(
				RedisKeyGenerator.genDialogContentsKey(receiverDialogId),
				dialogContent.getId());
		noticeService.incrNotice(targetUid, NoticeType.DIALOG);
		return dialogContent.getId();
	}

	@Override
	public long sendSMS(long uid, long targetUid,
			DialogContentTemplate template, Object... params)
			throws DialogException {
		String content = messageSource.getMessage(template.getName(), null,
				Locale.SIMPLIFIED_CHINESE);
		if (StringUtils.isEmpty(content)) {
			return 0L;
		}
		return sendContent(uid, targetUid, content);
	}

	@Override
	public long sendDatingSMS(long uid, long targetUid,
			DialogContentTemplate template, Object... params)
			throws DialogException {
		if (!passportService.isUse(FunctionLevel.SENDSMS, uid)) {
			throw new DialogException(DialogException.USE_LOW_LEVEL);
		}
		return sendSMS(uid, targetUid, template, params);
	}

	@Override
	public long sendOfficialSMS(long targetUid, DialogContentTemplate template,
			Object... params) {
		try {
			return sendSMS(officialNoticeUid, targetUid, template, params);
		} catch (DialogException e) {
			return 0L;
		}
	}

	@Override
	public boolean deleteDialog(long uid, long dialogId) {
		DialogExample example = new DialogExample();
		example.createCriteria().andIdEqualTo(dialogId).andUidEqualTo(uid);
		if (dialogMapper.deleteByExample(example) > 0) {
			redisTemplate.delete(RedisKeyGenerator
					.genDialogContentsKey(dialogId));
			return true;
		}
		return false;
	}

	@Override
	public long deleteDialogContent(long uid, long targetUid,
			long dialogContentId) {
		Dialog dialog = getDialogByUidAndTargetUid(uid, targetUid);
		if (null != dialog && dialog.getUid() == uid) {
			redisTemplate.opsForList().remove(
					RedisKeyGenerator.genDialogContentsKey(dialog.getId()), 1,
					dialogContentId);
			long count = getDialogContentCnt(dialog.getId());
			if (count <= 0) {
				deleteDialog(uid, dialog.getId());
			}
			return count;
		}
		return 0L;
	}

	private Dialog getDialogByUidAndTargetUid(long uid, long targetUid) {
		DialogExample example = new DialogExample();
		example.createCriteria().andUidEqualTo(uid)
				.andTargetUidEqualTo(targetUid);
		List<Dialog> list = dialogMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	private long updateDialog(long uid, long targetUid, boolean hasNew) {
		Dialog dialog = getDialogByUidAndTargetUid(uid, targetUid);
		if (null == dialog) {
			dialog = new Dialog();
			dialog.setUid(uid);
			dialog.setTargetUid(targetUid);
			dialog.setCreateTime(new Date());
			dialog.setLastModifyTime(dialog.getCreateTime());
			dialog.setHasNew(hasNew);
			dialogMapper.insert(dialog);
		} else {
			dialog.setHasNew(hasNew);
			dialog.setLastModifyTime(new Date());
			dialogMapper.updateByPrimaryKeySelective(dialog);
		}
		return dialog.getId();
	}

	@Override
	public List<DialogView> listDialog(long uid, int firstResult, int maxResults) {
		DialogExample example = new DialogExample();
		example.createCriteria().andUidEqualTo(uid);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc");
		List<Dialog> list = dialogMapper.selectByExample(example);
		List<DialogView> dialogViewList = new ArrayList<DialogView>();
		for (Dialog dialog : list) {
			DialogView view = new DialogView();
			view.setDialog(dialog);
			view.setDialogContentCnt(getDialogContentCnt(dialog.getId()));
			view.setDialogContent(getDialogContent(getFirstDialogContent(dialog
					.getId())));
			view.setTargetProfile(profileService.getProfileCacheByUid(dialog
					.getTargetUid()));
			view.setShield(blacklistService.isShield(uid, dialog.getTargetUid()));
			dialogViewList.add(view);
		}
		return dialogViewList;
	}

	private long getFirstDialogContent(long dialogId) {
		Long dialogContentId = redisTemplate.opsForList().index(
				RedisKeyGenerator.genDialogContentsKey(dialogId), 0);
		return null == dialogContentId ? 0L : dialogContentId;
	}

	private long getDialogContentCnt(long dialogId) {
		return redisTemplate.opsForList().size(
				RedisKeyGenerator.genDialogContentsKey(dialogId));
	}

	@Override
	public List<DialogContentView> listDialogContent(long uid, long targetUid,
			int firstResult, int maxResults) {
		Dialog dialog = getDialogByUidAndTargetUid(uid, targetUid);
		if (null == dialog) {
			return Collections.emptyList();
		}
		List<Long> dialogContentIdList = redisTemplate.opsForList().range(
				RedisKeyGenerator.genDialogContentsKey(dialog.getId()),
				firstResult, firstResult + maxResults - 1);
		List<DialogContentView> viewList = new ArrayList<DialogContentView>();
		for (long dialogContentId : dialogContentIdList) {
			DialogContentView view = new DialogContentView();
			view.setDialogContent(getDialogContent(dialogContentId));
			view.setProfile(profileService.getProfileCacheByUid(view
					.getDialogContent().getSenderUid()));
			view.setReceiverProfile(profileService.getProfileCacheByUid(view
					.getDialogContent().getReceiverUid()));
			viewList.add(view);
		}
		return viewList;
	}

	@Override
	public DialogContent getDialogContent(long dialogContentId) {
		String key = MemcachedKeyGenerator.genDialogContentKey(dialogContentId);
		DialogContent dialogContent = null;
		try {
			dialogContent = memcachedClient.get(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == dialogContent) {
			dialogContent = dialogContentMapper
					.selectByPrimaryKey(dialogContentId);
			if (null != dialogContent) {
				try {
					memcachedClient.set(key, dialogContentCacheExpireTime,
							dialogContent);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return dialogContent;
	}

	@Override
	public int countDialong(long uid) {
		DialogExample example = new DialogExample();
		example.createCriteria().andUidEqualTo(uid);
		return dialogMapper.countByExample(example);
	}

	@Override
	public int countDialogContent(long uid, long targetUid) {
		Dialog dialog = getDialogByUidAndTargetUid(uid, targetUid);
		if (null == dialog) {
			return 0;
		}
		Long count = getDialogContentCnt(dialog.getId());
		return count == null ? 0 : count.intValue();
	}

}
