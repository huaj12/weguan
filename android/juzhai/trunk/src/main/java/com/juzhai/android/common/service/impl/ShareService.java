package com.juzhai.android.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.util.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

import com.juzhai.android.R;
import com.juzhai.android.common.service.IShareService;
import com.juzhai.android.common.service.SharedApp;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.idea.model.Idea;

public class ShareService implements IShareService {

	private static final String INVITE_IMAGE = "share_pic.jpg";

	@Override
	public void openIdeaSharePop(Context context, Idea idea, Uri imageUri) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imageUri != null) {
			intent.setType("image/*"); // 分享图片信息类型
		} else {
			intent.setType("text/plain");
		}
		List<ResolveInfo> resInfo = context.getPackageManager()
				.queryIntentActivities(intent, 0);
		if (!resInfo.isEmpty()) {
			List<Intent> targetedShareIntents = new ArrayList<Intent>();
			for (ResolveInfo info : resInfo) {
				ActivityInfo activityInfo = info.activityInfo;
				SharedApp sharedApp = SharedApp
						.getSharedAppByPackageName(activityInfo.packageName);
				if (sharedApp != null
						&& (!StringUtils.hasText(sharedApp.getName()) || sharedApp
								.getName().equals(activityInfo.name))) {
					Intent targeted = new Intent(Intent.ACTION_SEND);
					targeted.setPackage(activityInfo.packageName);
					if (imageUri != null && !SharedApp.QZONE.equals(sharedApp)
							&& !SharedApp.RENREN.equals(sharedApp)) {
						// if (imageUri != null) {
						targeted.setType("image/*"); // 分享图片信息类型
					} else {
						targeted.setType("text/plain");
					}
					targeted.putExtra(Intent.EXTRA_STREAM, imageUri);
					targeted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (SharedApp.EMAIL.equals(sharedApp)) {
						targeted.putExtra(
								Intent.EXTRA_SUBJECT,
								context.getString(R.string.share_to_email_idea_subject));

						String place = StringUtil.EMPTY;
						if (StringUtils.hasLength(idea.getPlace())) {
							place = context.getString(
									R.string.share_idea_place, idea.getPlace());
						}
						targeted.putExtra(Intent.EXTRA_TEXT, context.getString(
								R.string.share_to_email_idea_text,
								idea.getContent(), place, idea.getIdeaId(),
								idea.getBigPic()));
					} else if (SharedApp.MMS.equals(sharedApp)) {
						targeted.putExtra(Intent.EXTRA_TEXT, context.getString(
								R.string.share_to_mms_idea_text,
								idea.getContent()));
					} else {
						String place = StringUtil.EMPTY;
						if (StringUtils.hasLength(idea.getPlace())) {
							place = context.getString(
									R.string.share_idea_place, idea.getPlace());
						}
						targeted.putExtra(Intent.EXTRA_TEXT, context.getString(
								R.string.share_to_tp_idea_text,
								idea.getContent(), place, idea.getIdeaId()));
					}
					targetedShareIntents.add(targeted);
				}
			}
			Collections.sort(targetedShareIntents, new Comparator<Intent>() {
				@Override
				public int compare(Intent intent1, Intent intent2) {
					int index1 = SharedApp.getSharedAppByPackageName(
							intent1.getPackage()).ordinal();
					int index2 = SharedApp.getSharedAppByPackageName(
							intent2.getPackage()).ordinal();
					return index1 - index2;
				}
			});
			// 分享框标题
			Intent chooserIntent = Intent.createChooser(targetedShareIntents
					.remove(targetedShareIntents.size() - 1), context
					.getString(R.string.share_title));
			if (chooserIntent == null) {
				return;
			}
			chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
					targetedShareIntents.toArray(new Parcelable[] {}));
			context.startActivity(chooserIntent);
		}
	}

	@Override
	public void openInvitePop(Context context) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		List<ResolveInfo> resInfo = context.getPackageManager()
				.queryIntentActivities(intent, 0);
		if (!resInfo.isEmpty()) {
			List<Intent> targetedShareIntents = new ArrayList<Intent>();
			for (ResolveInfo info : resInfo) {
				ActivityInfo activityInfo = info.activityInfo;
				SharedApp sharedApp = SharedApp
						.getSharedAppByPackageName(activityInfo.packageName);
				if (sharedApp != null
						&& (!StringUtils.hasText(sharedApp.getName()) || sharedApp
								.getName().equals(activityInfo.name))) {
					Intent targeted = new Intent(Intent.ACTION_SEND);
					targeted.setPackage(activityInfo.packageName);
					// if (!SharedApp.QZONE.equals(sharedApp)
					// && !SharedApp.RENREN.equals(sharedApp)) {
					// targeted.setType("image/*"); // 分享图片信息类型
					// } else {
					targeted.setType("text/plain");
					// }
					targeted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (SharedApp.EMAIL.equals(sharedApp)) {
						targeted.putExtra(Intent.EXTRA_SUBJECT,
								context.getString(R.string.invite_subject));
					}
					targeted.putExtra(Intent.EXTRA_TEXT,
							context.getString(R.string.invite_text));
					targetedShareIntents.add(targeted);
				}
			}
			Collections.sort(targetedShareIntents, new Comparator<Intent>() {
				@Override
				public int compare(Intent intent1, Intent intent2) {
					int index1 = SharedApp.getSharedAppByPackageName(
							intent1.getPackage()).ordinal();
					int index2 = SharedApp.getSharedAppByPackageName(
							intent2.getPackage()).ordinal();
					return index1 - index2;
				}
			});
			// 分享框标题
			Intent chooserIntent = Intent.createChooser(targetedShareIntents
					.remove(targetedShareIntents.size() - 1), context
					.getString(R.string.invite_title));
			if (chooserIntent == null) {
				return;
			}
			chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
					targetedShareIntents.toArray(new Parcelable[] {}));
			context.startActivity(chooserIntent);
		}
	}
}
