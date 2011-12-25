package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.AddRawActException;
import com.juzhai.act.exception.RawActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.RawAct;
import com.juzhai.cms.controller.form.AgreeRawActForm;

public interface IRawActService {
	/**
	 * 添加项目
	 * 
	 * @param rawAct
	 * @return
	 * @throws AddRawActException
	 */
	RawAct addRawAct(RawAct rawAct) throws AddRawActException;

	List<RawAct> getRawActs(int firstResult, int maxResults);

	RawAct getRawAct(long id);

	void delteRawAct(long id);

	int getRawActCount();

	/**
	 * 审核通过项目
	 * 
	 * @param act
	 * @param categoryId
	 * @param detail
	 * @param rawActId
	 * @throws ActInputException
	 */
	void agreeRawAct(AgreeRawActForm agreeRawActForm) throws ActInputException,RawActInputException;
}
