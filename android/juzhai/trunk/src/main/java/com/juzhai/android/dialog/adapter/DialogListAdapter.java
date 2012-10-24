package com.juzhai.android.dialog.adapter;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.dialog.activity.DialogListActivity;
import com.juzhai.android.dialog.model.Dialog;
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;

public class DialogListAdapter extends PageAdapter<Dialog> {
	private IUserViewHelper userViewHelper = new UserViewHelper();

	public DialogListAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_dialog_list, null);
			holder = new ViewHolder();
			holder.nicknameTextView = (TextView) convertView
					.findViewById(R.id.user_nickname);
			holder.logoView = (ImageView) convertView
					.findViewById(R.id.user_logo);
			holder.sendFlagView = (ImageView) convertView
					.findViewById(R.id.dialog_send_flag);
			holder.contentTextView = (TextView) convertView
					.findViewById(R.id.dialog_content);
			holder.createTimeTextView = (TextView) convertView
					.findViewById(R.id.dialog_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final TextView nicknameTextView = holder.nicknameTextView;
		final TextView contentTextView = holder.contentTextView;
		final ImageView logoView = holder.logoView;
		final TextView createTimeTextView = holder.createTimeTextView;
		final ImageView sendFlagView = holder.sendFlagView;

		final Dialog dialog = data.getDatas().get(position);
		final User user = dialog.getTargetUser();
		if (StringUtils.isNotEmpty(user.getLogo())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			nid.fetchImage(JzUtils.getImageUrl(user.getLogo()),
					R.drawable.user_face_unload, logoView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								// TODO (review) 尽量数字不写死
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, 60, 60, mContext);
								logoView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		}
		userViewHelper.showUserNickname(mContext, user, nicknameTextView);
		contentTextView.setText(TextTruncateUtil.truncate(
				dialog.getLatestContent(), 33, "..."));
		// 接受者
		if (UserCache.getUid() == dialog.getReceiverUid()) {
			sendFlagView.setBackgroundResource(R.drawable.mess_ta_sendto_me);
		} else {
			sendFlagView.setBackgroundResource(R.drawable.mess_i_sendto_ta);
		}
		createTimeTextView.setText(JzUtils.showTime(mContext,
				new Date(dialog.getCreateTime())));
		logoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserHomeActivity.class);
				intent.putExtra("uid", user.getUid());
				((DialogListActivity) mContext).pushIntent(intent);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		public TextView nicknameTextView;
		public ImageView logoView;
		public ImageView sendFlagView;
		public TextView createTimeTextView;
		public TextView contentTextView;
	}
}
