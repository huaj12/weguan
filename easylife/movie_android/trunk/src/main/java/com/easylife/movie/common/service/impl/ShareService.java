package com.easylife.movie.common.service.impl;

import android.content.Context;
import android.content.Intent;
import cn.sharesdk.onekeyshare.ShareAllGird;

import com.easylife.movie.R;
import com.easylife.movie.common.service.IShareService;
import com.easylife.movie.video.model.Video;

public class ShareService implements IShareService {

	@Override
	public void openSharePop(final String text, final Context context,
			final Video video) {
		Intent i = new Intent(context, ShareAllGird.class);
		// 分享时Notification的图标
		i.putExtra("notif_icon", R.drawable.logo_tz);
		// 分享时Notification的标题
		i.putExtra("notif_title", context.getString(R.string.app_name));

		// text是分享文本，所有平台都需要这个字段
		if (video == null) {
			i.putExtra("text", text);
			i.putExtra("specialText", text);
		} else {
			i.putExtra("text", video.getTitle() + video.getVideoSrc());
			i.putExtra("specialText", video.getTitle() + video.getShareUrl());
		}
		context.startActivity(i);
	}
}
