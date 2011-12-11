package com.juzhai.act.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.exception.AddRawActException;
import com.juzhai.act.mapper.RawActMapper;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IRawActService;
import com.juzhai.core.util.StringUtil;
@Service
public class RawActService implements IRawActService {
	@Value(value = "${act.name.length.max}")
	private int actNameLengthMax;
	@Value(value = "${act.adress.length.max}")
	private int actAddressLengthMax;
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RawActMapper rawActMapper;
	
	@Override
	public RawAct addRawAct(RawAct rawAct) throws AddRawActException {
		if(null==rawAct){
			 throw new AddRawActException();
		}
		if(StringUtils.isEmpty(rawAct.getName())){
			throw new AddRawActException();//项目名字不能为空
		}
		if(StringUtils.isEmpty(rawAct.getLogo())){
			throw new AddRawActException();//请先上传一张项目图片
		}
		if(StringUtils.isEmpty(rawAct.getAddress())){
			throw new AddRawActException();//请填写详细地址
		}
		if(StringUtil.chineseLength(rawAct.getName())>actNameLengthMax){
			throw new AddRawActException();
		}
		if(StringUtil.chineseLength(rawAct.getAddress())>actAddressLengthMax){
			throw new AddRawActException();
		}
		rawAct.setCreateTime(new Date());
		rawAct.setLastModifyTime(new Date());
		rawActMapper.insert(rawAct);
		return rawAct;
	}

}
