/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.dao.IActDao;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.mapper.ActLinkMapper;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActExample;
import com.juzhai.act.model.ActExample.Criteria;
import com.juzhai.act.model.ActLink;
import com.juzhai.act.model.ActLinkExample;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActDetailService;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.cms.controller.form.AddActForm;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.ThirdpartyNameEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.search.rabbit.message.ActIndexMessage;
import com.juzhai.search.rabbit.message.ActIndexMessage.ActionType;
import com.juzhai.search.service.IActSearchService;
import com.juzhai.wordfilter.service.IWordFilterService;

@Service
public class ActService implements IActService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ActMapper actMapper;
	@Autowired
	private IActDao actDao;
	@Autowired
	private RabbitTemplate actIndexCreateRabbitTemplate;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IWordFilterService wordFilterService;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private ActLinkMapper actLinkMapper;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActDetailService actDetailService;
	@Autowired
	private IActImageService actImageService;
	@Autowired
	private IActSearchService actSearchService;
	@Value("${act.name.length.min}")
	private int actNameLengthMin = 2;
	@Value("${act.name.length.max}")
	private int actNameLengthMax = 20;
	@Value("${act.name.wordfilter.application}")
	private int actNameWordfilterApplication = 0;
	@Value("${act.cache.expire.time}")
	private int actCacheExpireTime = 0;
	@Value("${act.detail.length.max}")
	private int actDetailLengthMax;
	@Value("${act.adress.length.max}")
	private int actAdressLengthMax;
	@Value("${act.full.name.length.max}")
	private int actFullNameLengthMax;
	@Value("${act.intro.length.max}")
	private int actIntroLengthMax;

	@Override
	public boolean actExist(long actId) {
		try {
			Act act = memcachedClient.get(MemcachedKeyGenerator
					.genActCacheKey(actId));
			if (null != act) {
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		ActExample example = new ActExample();
		example.createCriteria().andIdEqualTo(actId);
		return actMapper.countByExample(example) > 0;
	}

	@Override
	public Act getActById(long actId) {
		String key = MemcachedKeyGenerator.genActCacheKey(actId);
		Act act = null;
		try {
			act = memcachedClient.get(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == act) {
			act = actMapper.selectByPrimaryKey(actId);
			if (null != act) {
				try {
					memcachedClient.set(key, actCacheExpireTime, act);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return act;
	}

	@Override
	public Map<Long, Act> getMultiActByIds(List<Long> actIds) {
		// 判断是否为空
		if (CollectionUtils.isEmpty(actIds)) {
			return Collections.emptyMap();
		}
		// 组装Key
		Map<String, Long> actKeyMap = new HashMap<String, Long>(actIds.size());
		for (long actId : actIds) {
			actKeyMap.put(MemcachedKeyGenerator.genActCacheKey(actId), actId);
		}
		// 缓存搜索
		Map<Long, Act> actMap = new HashMap<Long, Act>();
		try {
			Map<String, Act> actCacheMap = memcachedClient.get(actKeyMap
					.keySet());
			for (Map.Entry<String, Act> entry : actCacheMap.entrySet()) {
				actMap.put(actKeyMap.get(entry.getKey()), entry.getValue());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 数据库搜索未被缓存的
		if (actMap.size() != actIds.size()) {
			// 组装需要数据库搜索的ActId
			List<Long> searchActIds = new ArrayList<Long>();
			for (long actId : actIds) {
				if (!actMap.containsKey(actId)) {
					searchActIds.add(actId);
				}
			}
			if (CollectionUtils.isNotEmpty(searchActIds)) {
				// 数据库搜索
				ActExample example = new ActExample();
				example.createCriteria().andIdIn(searchActIds);
				List<Act> actList = actMapper.selectByExample(example);
				for (Act act : actList) {
					// 更新缓存
					try {
						memcachedClient.set(MemcachedKeyGenerator
								.genActCacheKey(act.getId()),
								actCacheExpireTime, act);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					// 放入返回Map中
					actMap.put(act.getId(), act);
				}
			}
		}

		return actMap;
	}

	@Override
	public List<Act> getActListByIds(List<Long> actIds) {
		// 判断是否为空
		if (CollectionUtils.isEmpty(actIds)) {
			return Collections.emptyList();
		}
		List<Act> actList = new ArrayList<Act>(actIds.size());
		Map<Long, Act> actMap = getMultiActByIds(actIds);
		for (long actId : actIds) {
			Act act = actMap.get(actId);
			if (null != act) {
				actList.add(act);
			}
		}
		return actList;
	}

	@Override
	public Act getActByName(String name) {
		return actDao.selectActByName(StringUtils.trim(name));
	}

	@Override
	public void inOrDePopularity(long actId, int p) {
		if (p != 0) {
			actDao.incrOrDecrPopularity(actId, p);
		}
	}

	@Override
	public void inOrDeTpActPopularity(String tpName, long actId, int p) {
		if (p != 0 && ThirdpartyNameEnum.getThirdpartyNameEnum(tpName) != null) {
			redisTemplate.opsForValue().increment(
					RedisKeyGenerator.genTpActPopularityKey(actId, tpName), p);
		}
	}

	@Override
	public long getTpActPopularity(long tpId, long actId) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null != tp) {
			return redisTemplate.opsForValue().increment(
					RedisKeyGenerator.genTpActPopularityKey(actId, tpId), 0);
		} else {
			return 0L;
		}
	}

	@Override
	public List<Act> searchNewActs(Date startDate, Date endDate, int order,
			int firstResult, int maxResults) {
		ActExample example = new ActExample();
		example.createCriteria().andCreateTimeBetween(startDate, endDate);
		switch (order) {
		case 1:
			example.setOrderByClause("create_time desc, id desc");
			break;
		default:
			example.setOrderByClause("popularity desc, id desc");
			break;
		}
		example.setLimit(new Limit(firstResult, maxResults));
		return actMapper.selectByExample(example);
	}

	@Override
	public int countNewActs(Date startDate, Date endDate) {
		ActExample example = new ActExample();
		example.createCriteria().andCreateTimeBetween(startDate, endDate);
		return actMapper.countByExample(example);
	}

	private void clearActCache(long actId) {
		try {
			memcachedClient.deleteWithNoReply(MemcachedKeyGenerator
					.genActCacheKey(actId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public List<Act> queryActs(Date beginDate, Date endDate, String name,
			int firstResult, int maxResults) {
		ActExample example = new ActExample();
		Criteria criteria = example.createCriteria();
		if (beginDate != null && endDate != null) {
			criteria.andCreateTimeBetween(beginDate, endDate);
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameEqualTo(name);
		}
		example.setOrderByClause("last_modify_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return actMapper.selectByExample(example);
	}

	@Override
	public int queryActsCount(Date beginDate, Date endDate, String name) {
		ActExample example = new ActExample();
		Criteria criteria = example.createCriteria();
		if (beginDate != null && endDate != null) {
			criteria.andCreateTimeBetween(beginDate, endDate);
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameEqualTo(name);
		}
		return actMapper.countByExample(example);
	}

	@Override
	public void cmsUpdateAct(long uid, AddActForm addActForm)
			throws ActInputException, UploadImageException, ParseException {
		if (addActForm.getId() == null || addActForm.getId() <= 0) {
			throw new ActInputException(ActInputException.ILLEGAL_OPERATION);
		}
		Act act = actMapper.selectByPrimaryKey(addActForm.getId());
		if (null == act) {
			throw new ActInputException(ActInputException.ILLEGAL_OPERATION);
		}
		boolean oldActive = act.getActive();
		validationAddActForm(addActForm);
		// 先上传图片
		String logo = null;
		if (addActForm.getImgFile() != null
				&& addActForm.getImgFile().getSize() > 0) {
			logo = actImageService.uploadActLogo(uid, act.getId(),
					addActForm.getImgFile());
		}
		assembleAct(uid, addActForm, act, logo);
		actMapper.updateByPrimaryKey(act);
		if (oldActive) {
			actSearchService.updateIndex(act);
		} else {
			actSearchService.createIndex(act);
		}
		updateActCategory(act.getId(), addActForm.getCatIds());
		clearActCache(act.getId());
		if (StringUtils.isNotEmpty(addActForm.getDetail())) {
			actDetailService.updateActDetail(act.getId(),
					addActForm.getDetail());
		}
	}

	@Override
	public List<ActLink> listActLinkByActId(long actId, int count) {
		ActLinkExample example = new ActLinkExample();
		example.createCriteria().andActIdEqualTo(actId);
		example.setLimit(new Limit(0, count));
		example.setOrderByClause("sequence asc, last_modify_time desc");
		return actLinkMapper.selectByExample(example);
	}

	@Override
	public void cmsCreateAct(long uid, AddActForm addActForm)
			throws UploadImageException, ActInputException, ParseException {
		validationAddActForm(addActForm);

		Act act = new Act();
		assembleAct(uid, addActForm, act, null);
		actMapper.insertSelective(act);
		actSearchService.createIndex(act);
		updateActCategory(act.getId(), addActForm.getCatIds());
		if (addActForm.getAddUid() != null && addActForm.getAddUid() > 0) {
			userActService.addAct(addActForm.getAddUid(), act.getId());
		}
		if (StringUtils.isNotEmpty(addActForm.getDetail())) {
			actDetailService.updateActDetail(act.getId(),
					addActForm.getDetail());
		}
		if (addActForm.getImgFile() != null
				&& addActForm.getImgFile().getSize() > 0) {
			String logo = actImageService.uploadActLogo(uid, act.getId(),
					addActForm.getImgFile());
			Act updateAct = new Act();
			updateAct.setId(act.getId());
			updateAct.setLogo(logo);
			actMapper.updateByPrimaryKeySelective(updateAct);
		}
	}

	private void assembleAct(long uid, AddActForm addActForm, Act act,
			String logo) throws ParseException {
		act.setName(addActForm.getName());
		act.setCategoryIds(StringUtils.join(addActForm.getCatIds(), ","));
		act.setActive(true);
		if (act.getCreateUid() == null) {
			act.setCreateUid(uid);
		}
		act.setFullName(addActForm.getFullName());
		act.setIntro(addActForm.getIntro());
		if (StringUtils.isNotEmpty(logo)) {
			act.setLogo(logo);
		}
		act.setProvince(addActForm.getProvince() == null ? 0 : addActForm
				.getProvince());
		act.setCity(addActForm.getCity() == null ? 0 : addActForm.getCity());
		act.setTown(addActForm.getTown() == null ? -1 : addActForm.getTown());
		act.setAddress(addActForm.getAddress());
		if (StringUtils.isNotEmpty(addActForm.getStartTime())) {
			act.setStartTime(DateUtils.parseDate(addActForm.getStartTime(),
					DateFormat.DATE_PATTERN));
		} else {
			act.setStartTime(null);
		}
		if (StringUtils.isNotEmpty(addActForm.getEndTime())) {
			act.setEndTime(DateUtils.parseDate(addActForm.getEndTime(),
					DateFormat.DATE_PATTERN));
		} else {
			act.setEndTime(null);
		}
		if (StringUtils.isNotEmpty(addActForm.getSuitAge())) {
			act.setSuitAge(SuitAge.getSuitAge(addActForm.getSuitAge()));
		}
		if (StringUtils.isNotEmpty(addActForm.getSuitGender())) {
			act.setSuitGender(SuitGender.getSuitGender(addActForm
					.getSuitGender()));
		}
		if (StringUtils.isNotEmpty(addActForm.getSuitStatus())) {
			act.setSuitStatus(SuitStatus.getSuitStatus(addActForm
					.getSuitStatus()));
		}
		act.setMinRoleNum(addActForm.getMinRoleNum() == null ? 0 : addActForm
				.getMinRoleNum());
		act.setMaxRoleNum(addActForm.getMaxRoleNum() == null ? 0 : addActForm
				.getMaxRoleNum());
		act.setMinCharge(addActForm.getMinCharge() == null ? 0 : addActForm
				.getMinCharge());
		act.setMaxCharge(addActForm.getMaxCharge() == null ? 0 : addActForm
				.getMaxCharge());
		act.setKeyWords(addActForm.getKeyWords());
		if (act.getCreateTime() == null) {
			act.setCreateTime(new Date());
		}
		act.setLastModifyTime(new Date());
	}

	private void validationAddActForm(AddActForm addActForm)
			throws ActInputException {
		// check name;
		long nameLength = StringUtil.chineseLength(addActForm.getName());
		if (nameLength < actNameLengthMin || nameLength > actNameLengthMax) {
			throw new ActInputException(ActInputException.ACT_NAME_INVALID);
		}
		Act existAct = getActByName(addActForm.getName());
		if (null != existAct
				&& (addActForm.getId() == null || existAct.getId().longValue() != addActForm
						.getId().longValue())) {
			throw new ActInputException(ActInputException.ACT_NAME_EXISTENCE);
		}
		// check fullName;
		if (StringUtils.isNotEmpty(addActForm.getFullName())
				&& StringUtil.chineseLength(addActForm.getFullName()) > actFullNameLengthMax) {
			throw new ActInputException(ActInputException.ACT_FULL_NAME_INVALID);
		}
		// check intro;
		if (StringUtils.isNotEmpty(addActForm.getIntro())
				&& StringUtil.chineseLength(addActForm.getIntro()) > actIntroLengthMax) {
			throw new ActInputException(ActInputException.ACT_INTRO_INVALID);
		}
		// check detail;
		if (StringUtils.isNotEmpty(addActForm.getDetail())
				&& StringUtil.chineseLength(addActForm.getDetail()) > actDetailLengthMax) {
			throw new ActInputException(
					ActInputException.ACT_DETAIL_IS_TOO_LONG);
		}
		// check address;
		if (StringUtils.isNotEmpty(addActForm.getAddress())
				&& StringUtil.chineseLength(addActForm.getAddress()) > actAdressLengthMax) {
			throw new ActInputException(ActInputException.ACT_ADDRESS_INVALID);
		}
	}

	private void updateActCategory(long id, List<Long> categoryIds) {
		if (categoryIds != null) {
			actCategoryService.updateActCategory(id, categoryIds);
		}
	}

	// TODO (old) 下面两个方法需要重构

	@Override
	public Act createAct(long uid, String actName, List<Long> categoryIds)
			throws ActInputException {
		try {
			if (wordFilterService.wordFilter(actNameWordfilterApplication, uid,
					null, actName.getBytes("GBK")) < 0) {
				throw new ActInputException(ActInputException.ACT_NAME_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}
		long length = StringUtil.chineseLength(actName);
		if (length < actNameLengthMin || length > actNameLengthMax) {
			throw new ActInputException(ActInputException.ACT_NAME_INVALID);
		}
		Act act = actDao.insertAct(uid, actName, null);
		updateActCategory(act.getId(), categoryIds);
		if (null != act) {
			// // 加载Act
			// actInitData.loadAct(act);
			// if (log.isDebugEnabled()) {
			// log.debug("load new act to InitData");
			// }
			// add 索引
			ActIndexMessage msgMessage = new ActIndexMessage();
			msgMessage.buildBody(act).buildActionType(ActionType.CREATE);
			actIndexCreateRabbitTemplate.convertAndSend(msgMessage);
			if (log.isDebugEnabled()) {
				log.debug("send act index create message");
			}
		}
		return act;
	}

	@Override
	public Act createAct(Act act, List<Long> categoryIds)
			throws ActInputException {
		if (act == null || act.getName() == null || act.getCreateUid() == 0) {
			throw new ActInputException(ActInputException.ILLEGAL_OPERATION);
		}
		try {
			if (wordFilterService.wordFilter(actNameWordfilterApplication,
					act.getCreateUid(), null, act.getName().getBytes("GBK")) < 0) {
				throw new ActInputException(ActInputException.ACT_NAME_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}
		long length = StringUtil.chineseLength(act.getName());
		if (length < actNameLengthMin || length > actNameLengthMax) {
			throw new ActInputException(ActInputException.ACT_NAME_INVALID);
		}
		Act a = getActByName(act.getName());
		if (a != null) {
			throw new ActInputException(ActInputException.ACT_NAME_EXISTENCE);
		}
		actDao.inserAct(act, categoryIds);
		updateActCategory(act.getId(), categoryIds);
		if (null != act) {
			// // 加载Act
			// actInitData.loadAct(act);
			// if (log.isDebugEnabled()) {
			// log.debug("load new act to InitData");
			// }
			// add 索引
			ActIndexMessage msgMessage = new ActIndexMessage();
			msgMessage.buildBody(act).buildActionType(ActionType.CREATE);
			actIndexCreateRabbitTemplate.convertAndSend(msgMessage);
			if (log.isDebugEnabled()) {
				log.debug("send act index create message");
			}
		}
		return act;
	}
}
