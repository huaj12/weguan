package com.easylife.weather.common.service.impl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.easylife.weather.R;
import com.easylife.weather.common.service.IShareService;
import com.easylife.weather.common.service.SharedApp;
import com.easylife.weather.core.stat.UmengEvent;
import com.easylife.weather.core.utils.WXUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.umeng.analytics.MobclickAgent;

public class ShareService implements IShareService {
	private IWXAPI api = null;

	@Override
	public void openSharePop(final String text, final Context context) {
		api = WXUtils.getInstance(context);
		WXTextObject wxTextObject = new WXTextObject();
		wxTextObject.text = text;
		WXMediaMessage wxMediaMessage = new WXMediaMessage();
		wxMediaMessage.mediaObject = wxTextObject;
		wxMediaMessage.description = text;
		final SendMessageToWX.Req sendReq = new SendMessageToWX.Req();
		sendReq.transaction = String.valueOf(System.currentTimeMillis());
		sendReq.message = wxMediaMessage;

		new AlertDialog.Builder(context)
				.setTitle(R.string.share_title)
				.setItems(
						new String[] {
								context.getResources().getString(
										R.string.wx_friend),
								context.getResources().getString(
										R.string.wx_circle),
								context.getResources().getString(R.string.mms),
								context.getResources().getString(
										R.string.cancel) },
						new OnClickListener() {

							@Override
							public void onClick(final DialogInterface dialog,
									int which) {
								dialog.cancel();
								switch (which) {
								case 0:
									sendReq.scene = SendMessageToWX.Req.WXSceneSession;
									api.sendReq(sendReq);
									break;
								case 1:
									sendReq.scene = SendMessageToWX.Req.WXSceneTimeline;
									api.sendReq(sendReq);
									break;
								case 2:
									Intent targeted = new Intent(
											Intent.ACTION_SEND);
									targeted.setPackage(SharedApp.MMS
											.getPackageName());
									targeted.setType("text/plain");
									targeted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									targeted.putExtra(Intent.EXTRA_TEXT, text);
									context.startActivity(Intent.createChooser(
											targeted,
											context.getResources().getString(
													R.string.share_title)));
									break;
								}
								if (which < 3 && which >= 0) {
									MobclickAgent.onEvent(context,
											UmengEvent.SHARE_FRIEND);
								}

							}
						}).show();
	}
}
