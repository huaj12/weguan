package com.juzhai.android.home.helper;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.passport.model.User;

public interface IUserViewHelper {

	/**
	 * 显示用户头像
	 * 
	 * @param context
	 *            环境上下文
	 * @param user
	 *            用户对象
	 * @param imageView
	 *            要显示的view
	 * @param width
	 *            头像宽度
	 * @param height
	 *            头像高度
	 */
	void showUserLogo(Context context, User user, ImageView imageView,
			int width, int height);

	/**
	 * 显示昵称，会根据性别显示不同颜色的字体
	 * 
	 * @param context
	 *            环境上下文
	 * @param user
	 *            用户对象
	 * @param textView
	 *            要显示的View
	 */
	void showUserNickname(Context context, User user, TextView textView);

	/**
	 * 显示用户在线状态
	 * 
	 * @param context
	 *            环境上下文
	 * @param user
	 *            用户对象
	 * @param textView
	 *            要显示的View
	 */
	void showOnlineState(Context context, User user, TextView textView);

	/**
	 * 显示用户新头像。（包括审核状态）
	 * 
	 * @param context
	 * @param user
	 * @param imageView
	 * @param textView
	 * @param width
	 * @param height
	 */
	void showUserNewLogo(Context context, User user, ImageView imageView,
			TextView textView, int width, int height);

	/**
	 * 带有city（不带星座）的用户详情
	 * 
	 * @param user
	 * @param mContext
	 * @return
	 */
	String getUserInfoCity(User user, Context mContext);

}
