package com.juzhai.cms.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.cms.exception.RobotInputException;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.form.RegisterRobotForm;
import com.juzhai.passport.exception.InterestUserException;
import com.juzhai.post.exception.InputPostCommentException;
import com.juzhai.post.exception.InputPostException;

public interface IRobotService {
	/**
	 * 获取某城市下所有机器人
	 * 
	 * @param cityId
	 * @return
	 * @throws RobotInputException
	 */
	List<ProfileCache> listRobot(Long cityId);

	/**
	 * 添加机器人
	 * 
	 * @param uid
	 * @throws RobotInputException
	 */
	void add(Long uid) throws RobotInputException;

	/**
	 * 删除机器人
	 * 
	 * @param uid
	 * @param cityId
	 * @throws RobotInputException
	 */
	void del(Long uid, Long cityId) throws RobotInputException;

	/**
	 * 导入机器人帐号
	 * 
	 * @param robotConfig
	 * @return 成功导入的个数
	 * @throws RobotInputException
	 */
	int importRobot(MultipartFile robotConfig) throws RobotInputException;

	/**
	 * 机器人关注
	 * 
	 * @param cityId
	 * @param targetUid
	 */
	void interest(Long cityId, Long targetUid) throws RobotInputException,
			InterestUserException;

	/**
	 * 机器人留言
	 * 
	 * @param cityId
	 * @param text
	 * @throws RobotInputException
	 */
	void comment(Long cityId, Long postId, String text)
			throws RobotInputException, InputPostCommentException;

	/**
	 * 机器人响应
	 * 
	 * @param cityId
	 * @throws RobotInputException
	 */
	void response(Long cityId, Long postId) throws RobotInputException,
			InputPostException;

	/**
	 * 注册机器人
	 * 
	 * @param forms
	 * @return 注册成功的个数
	 */
	int registerRobot(List<RegisterRobotForm> forms);

}
