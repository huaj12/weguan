package com.juzhai.act.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.AddRawActException;
import com.juzhai.act.mapper.RawActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.model.RawActExample;
import com.juzhai.act.service.IActDetailService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.act.service.IUploadImageService;
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
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RawActMapper rawActMapper;
	@Autowired
	private IActService actService;
	@Autowired
	private IUploadImageService uploadImageService;
	@Autowired
	private IActDetailService actDetailService;

	@Override
	public RawAct addRawAct(RawAct rawAct) throws AddRawActException {
		if (null == rawAct) {
			throw new AddRawActException(AddRawActException.ADD_RAWACT_IS_ERROR);
		}
		if (StringUtils.isEmpty(rawAct.getName())) {
			throw new AddRawActException(AddRawActException.NAME_IS_NULL);
		}
		if (rawAct.getCity() == null) {
			throw new AddRawActException(AddRawActException.CITY_IS_NULL);
		}
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
	@Transactional
	public void agreeRawAct(Act act, List<Long> categoryId, String detail,
			long uid, long rawActid) throws ActInputException {
		if(null==act){
			return ;
		}
		actService.createAct(act, categoryId);
		String filename = uploadImageService.intoActLogo(act.getLogo(),
				act.getId());
		if (StringUtils.isEmpty(filename)) {
			throw new ActInputException(ActInputException.ACT_LOGO_INVALID);
		}
		detail = uploadImageService.intoEditorImg(detail, uid);
		if (StringUtils.isEmpty(detail)) {
			throw new ActInputException(
					ActInputException.ACT_DETAIL_LOGO_INVALID);
		}
		try {
			act.setLogo(filename);
			actDetailService.addActDetail(act.getId(), detail);
			actService.updateAct(act, categoryId);
			delteRawAct(rawActid);
		} catch (Exception e) {
			log.error("agreeRawAct is error" + e.getMessage());
		}

	}

}
