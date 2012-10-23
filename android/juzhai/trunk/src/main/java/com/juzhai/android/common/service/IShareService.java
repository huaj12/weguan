package com.juzhai.android.common.service;

import android.content.Context;
import android.net.Uri;

import com.juzhai.android.idea.model.Idea;

public interface IShareService {

	/**
	 * 打开好主意分享框
	 * 
	 * @param context
	 * @param idea
	 * @param bitmap
	 */
	void openIdeaSharePop(Context context, Idea idea, Uri imageUri);

	/**
	 * 打开邀请分享框
	 * 
	 * @param context
	 */
	void openInvitePop(Context context);
}
