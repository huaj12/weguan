package com.juzhai.act.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.AddRawActException;
import com.juzhai.act.exception.RawActInputException;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.mapper.RawActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.model.RawActExample;
import com.juzhai.act.service.IActDetailService;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.cms.controller.form.AgreeRawActForm;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.notice.bean.SysNoticeType;
import com.juzhai.notice.service.ISysNoticeService;

@Service
public class RawActService implements IRawActService {
	private final Log log = LogFactory.getLog(getClass());
	@Value(value = "${act.name.length.max}")
	private int actNameLengthMax;
	@Value(value = "${act.adress.length.max}")
	private int actAddressLengthMax;
	@Value(value = "${act.detail.length.max}")
	private int actDetailLengthMax;
	@Autowired
	private RawActMapper rawActMapper;
	@Autowired
	private ActMapper actMapper;
	@Autowired
	private IActService actService;
	@Autowired
	private IActDetailService actDetailService;
	@Autowired
	private IActImageService actImageService;
	@Autowired
	private ISysNoticeService sysNoticeService;

	@Override
	public RawAct addRawAct(RawAct rawAct) throws AddRawActException {
		if (null == rawAct) {
			throw new AddRawActException(AddRawActException.ADD_RAWACT_IS_ERROR);
		}
		if (StringUtils.isEmpty(rawAct.getName())) {
			throw new AddRawActException(AddRawActException.NAME_IS_NULL);
		}
		if (rawAct.getCity() == null || rawAct.getCity() == 0) {
			throw new AddRawActException(AddRawActException.CITY_IS_NULL);
		}
		if (rawAct.getProvince() == null || rawAct.getProvince() == 0) {
			throw new AddRawActException(AddRawActException.PROVINCE_IS_NULL);
		}
		if (StringUtil.chineseLength(rawAct.getName()) > actNameLengthMax) {
			throw new AddRawActException(AddRawActException.NAME_IS_TOO_LONG);
		}
		if (StringUtil.chineseLength(rawAct.getAddress()) > actAddressLengthMax) {
			throw new AddRawActException(AddRawActException.ADDRESS_IS_TOO_LONG);
		}
		if (StringUtil.chineseLength(rawAct.getDetail()) > actDetailLengthMax) {
			throw new AddRawActException(AddRawActException.DETAIL_IS_TOO_LONG);
		}
		try {
			rawAct.setCreateTime(new Date());
			rawAct.setLastModifyTime(new Date());
			rawActMapper.insertSelective(rawAct);
		} catch (Exception e) {
			log.error("add rawact is error", e);
			throw new AddRawActException(AddRawActException.ADD_RAWACT_IS_ERROR);
		}
		return rawAct;
	}

	@Override
	public List<RawAct> getRawActs(int firstResult, int maxResults) {
		RawActExample example = new RawActExample();
		example.setOrderByClause("last_modify_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return rawActMapper.selectByExample(example);
	}

	@Override
	public int getRawActCount() {
		return rawActMapper.countByExample(new RawActExample());
	}

	@Override
	public RawAct getRawAct(long id) {
		return rawActMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delteRawAct(long id) {
		rawActMapper.deleteByPrimaryKey(id);
	}

	@Override
	// @Transactional
	public void agreeRawAct(AgreeRawActForm agreeRawActForm)
			throws ActInputException, RawActInputException {
		Act act = new Act();
		act.setAddress(agreeRawActForm.getAddress());
		act.setCity(agreeRawActForm.getCity());
		act.setProvince(agreeRawActForm.getProvince());
		act.setTown(agreeRawActForm.getTown());
		act.setName(agreeRawActForm.getName());
		act.setCreateUid(agreeRawActForm.getCreateUid());
		act.setLogo(agreeRawActForm.getLogo());
		List<Long> list = new ArrayList<Long>();
		list.add(agreeRawActForm.getCategoryId());
		try {
			if (StringUtils.isNotEmpty(agreeRawActForm.getStartTime())) {
				act.setStartTime(DateUtils.parseDate(
						agreeRawActForm.getStartTime(), DateFormat.DATE_PATTERN));
			}
			String endTime = agreeRawActForm.getEndTime();
			if (StringUtils.isNotEmpty(endTime)) {
				act.setEndTime(DateUtils.parseDate(endTime,
						DateFormat.DATE_PATTERN));
			}
		} catch (ParseException e) {
			throw new RawActInputException(
					RawActInputException.RAW_ACT_TIME_INVALID);
		}
		String detail = agreeRawActForm.getDetail();
		if (StringUtils.isEmpty(detail)) {
			throw new RawActInputException(
					RawActInputException.RAW_ACT_DETAIL_LOGO_INVALID);
		}
		long rawActId = agreeRawActForm.getRawActId();
		if (rawActId == 0) {
			throw new RawActInputException(
					RawActInputException.RAW_ACT_ID_IS_NULL);
		}
		actService.createAct(act, list);
		String filename = actImageService.intoActLogo(act.getId(),
				act.getLogo());
		if (StringUtils.isEmpty(filename)) {
			throw new RawActInputException(
					RawActInputException.RAW_ACT_LOGO_INVALID);
		}
		Act updateAct = new Act();
		updateAct.setLogo(filename);
		updateAct.setId(act.getId());
		actMapper.updateByPrimaryKeySelective(updateAct);
		actDetailService.updateActDetail(act.getId(), detail);
		delteRawAct(rawActId);

		// 发送系统通知
		sysNoticeService.sendSysNotice(act.getCreateUid(),
				SysNoticeType.ADD_ACT_SUCCESS, act.getName(), act.getId());
	}

	@Override
	public void rejectRawAct(long rawActId, String reasonMsg) {
		RawAct rawAct = getRawAct(rawActId);
		if (null != rawAct) {
			delteRawAct(rawActId);
			sysNoticeService.sendSysNotice(rawAct.getCreateUid(),
					SysNoticeType.ADD_ACT_FAILURE, rawAct.getName(), reasonMsg);
		}
	}
}
