package com.juzhai.home.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.model.DialogContent;

public interface IDialogService extends IDialogRemoteService {

	/**
	 * 发送私信
	 * 
	 * @param uid
	 * @param targetUid
	 * @param content
	 * @throws DialogException
	 */
	long sendSMS(UserContext context, long targetUid, String content)
			throws DialogException;

	/**
	 * 根据模版发送私信
	 * 
	 * @param uid
	 * @param targetUid
	 * @param template
	 * @return
	 * @throws DialogException
	 */
	long sendSMS(long uid, long targetUid, DialogContentTemplate template,
			Object... params) throws DialogException;

	/**
	 * 官方发送私信
	 * 
	 * @param targetUid
	 * @param template
	 * @param params
	 * @return
	 */
	long sendOfficialSMS(long targetUid, DialogContentTemplate template,
			Object... params);

	/**
	 * 删除回复内容
	 * 
	 * @param uid
	 * @param targetUid
	 * @param dialogContentId
	 */
	long deleteDialogContent(long uid, long targetUid, long dialogContentId);

	/**
	 * 后台查看私信列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<DialogView> cmsListDialog(int firstResult, int maxResults);

	/**
	 * 私信总数
	 * 
	 * @return
	 */
	int cmsCountDialog();

}
