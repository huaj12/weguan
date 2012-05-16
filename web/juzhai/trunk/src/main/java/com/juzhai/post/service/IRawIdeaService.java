package com.juzhai.post.service;

import java.util.List;

import com.juzhai.cms.exception.RawIdeaInputException;
import com.juzhai.cms.model.RawIdea;
import com.juzhai.post.controller.form.RawIdeaForm;

public interface IRawIdeaService {

	RawIdea getRawIdea(Long id);

	/**
	 * 用户创建好主意
	 * 
	 * @param ideaForm
	 */
	void createRawIdea(RawIdeaForm rawIdeaForm) throws RawIdeaInputException;

	/**
	 * 取出未审核的好主意
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	List<RawIdea> listRawIdea(int firstResult, int maxResult);

	/**
	 * 未审核好主意总数
	 * 
	 * @return
	 */
	int countRawIdea();

	/**
	 * 删除用户用户提交的好主意
	 * 
	 * @param id
	 */
	void delRawIdea(Long id);

	/**
	 * 取出纠错的好主意
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	List<RawIdea> listCorrectionRawIdea(int firstResult, int maxResult);

	/**
	 * 纠错好主意总数
	 * 
	 * @return
	 */
	int countCorrectionRawIdea();

	/**
	 * 通过用户提交好主意
	 * 
	 * @param id
	 */
	void passRawIdea(Long id) throws RawIdeaInputException;

	/**
	 * 修改用户提交好主意
	 * 
	 * @param rawIdeaForm
	 * @throws RawIdeaInputException
	 */
	void updateRawIdea(RawIdeaForm rawIdeaForm) throws RawIdeaInputException;

}
