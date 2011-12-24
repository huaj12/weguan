package com.juzhai.act.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.AddRawActException;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.mapper.RawActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.model.RawActExample;
import com.juzhai.act.service.IActDetailService;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;

@Service
public class RawActService implements IRawActService {
	@Value(value = "${act.name.length.max}")
	private int actNameLengthMax;
	@Value(value = "${act.adress.length.max}")
	private int actAddressLengthMax;
	@Value(value = "${act.detail.length.max}")
	private int actDetailLengthMax;
	// TODO (review) 通常static和final的都定义在类顶部
	private final Log log = LogFactory.getLog(getClass());
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

	@Override
	public RawAct addRawAct(RawAct rawAct) throws AddRawActException {
		if (null == rawAct) {
			throw new AddRawActException(AddRawActException.ADD_RAWACT_IS_ERROR);
		}
		if (StringUtils.isEmpty(rawAct.getName())) {
			throw new AddRawActException(AddRawActException.NAME_IS_NULL);
		}
		// TODO (review) 判断0
		if (rawAct.getCity() == null) {
			throw new AddRawActException(AddRawActException.CITY_IS_NULL);
		}
		// TODO (review) 判断0
		if (rawAct.getProvince() == null) {
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
			// TODO (review) 忘了删了吧！哈哈
			e.printStackTrace();
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
	public void agreeRawAct(Act act, List<Long> categoryId, String detail,
			long rawActId) throws ActInputException {
		if (null == act) {
			return;
		}
		actService.createAct(act, categoryId);
		String filename = actImageService.intoActLogo(act.getId(),
				act.getLogo());
		if (StringUtils.isNotEmpty(filename)) {
			Act updateAct = new Act();
			updateAct.setLogo(filename);
			updateAct.setId(act.getId());
			actMapper.updateByPrimaryKeySelective(updateAct);
		}
		detail = actImageService.intoEditorImg(act.getId(), detail);
		if (StringUtils.isNotEmpty(detail)) {
			actDetailService.addActDetail(act.getId(), detail);
			// throw new ActInputException(
			// ActInputException.ACT_DETAIL_LOGO_INVALID);
		}
		delteRawAct(rawActId);
	}
}
