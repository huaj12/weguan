package com.juzhai.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.common.bean.ActiveCodeType;
import com.juzhai.common.mapper.ActiveCodeMapper;
import com.juzhai.common.model.ActiveCode;
import com.juzhai.common.model.ActiveCodeExample;
import com.juzhai.common.service.IActiveCodeService;

@Service
public class ActiveCodeService implements IActiveCodeService {

	@Autowired
	private ActiveCodeMapper activeCodeMapper;
	@Value("${active.code.expire.seconds}")
	private int activeCodeExpireSeconds;

	@Override
	public String generateActiveCode(long uid, ActiveCodeType activeCodeType) {
		String code = UUID.randomUUID().toString();
		ActiveCodeExample example = new ActiveCodeExample();
		example.createCriteria().andUidEqualTo(uid)
				.andTypeEqualTo(activeCodeType.getType());
		List<ActiveCode> list = activeCodeMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			ActiveCode activeCode = list.get(0);
			activeCode.setCode(code);
			activeCode.setCreateTime(new Date());
			activeCode.setExpireTime(DateUtils.addSeconds(
					activeCode.getCreateTime(), activeCodeExpireSeconds));
			activeCodeMapper.updateByExample(activeCode, example);
		} else {
			ActiveCode activeCode = new ActiveCode();
			activeCode.setCode(code);
			activeCode.setType(activeCodeType.getType());
			activeCode.setUid(uid);
			activeCode.setCreateTime(new Date());
			activeCode.setExpireTime(DateUtils.addSeconds(
					activeCode.getCreateTime(), activeCodeExpireSeconds));
			activeCodeMapper.insert(activeCode);
		}
		return code;
	}

	// @Override
	// public boolean checkAndDel(long uid, String code,
	// ActiveCodeType activeCodeType) {
	// ActiveCode activeCode = getActiveCode(uid, code, activeCodeType);
	// if (null == activeCode) {
	// return false;
	// } else {
	// activeCodeMapper.deleteByPrimaryKey(code);
	// return activeCode.getExpireTime().after(new Date());
	// }
	// }

	@Override
	public long check(String code, ActiveCodeType activeCodeType) {
		ActiveCode activeCode = getActiveCode(code, activeCodeType);
		return activeCode != null
				&& activeCode.getExpireTime().after(new Date()) ? activeCode
				.getUid() : 0L;
	}

	@Override
	public void del(String code) {
		activeCodeMapper.deleteByPrimaryKey(code);
	}

	private ActiveCode getActiveCode(String code, ActiveCodeType activeCodeType) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		ActiveCode activeCode = activeCodeMapper.selectByPrimaryKey(code);
		if (null == activeCode
				|| activeCode.getType().intValue() != activeCodeType.getType()) {
			return null;
		}
		return activeCode;
	}

	@Override
	public void delExpired() {
		ActiveCodeExample example = new ActiveCodeExample();
		example.createCriteria().andExpireTimeLessThanOrEqualTo(new Date());
		activeCodeMapper.deleteByExample(example);
	}

}
