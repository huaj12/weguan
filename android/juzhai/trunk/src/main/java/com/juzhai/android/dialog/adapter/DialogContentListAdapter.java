package com.juzhai.android.dialog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.passport.model.User;

public class DialogContentListAdapter extends PageAdapter<DialogContent> {
	private User tagerUser;

	public DialogContentListAdapter(User tagerUser, Context mContext) {
		super(mContext);
		this.tagerUser = tagerUser;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_dialog_list, null);
			holder = new ViewHolder();
			holder.leftTextView = (TextView) convertView
					.findViewById(R.id.message_left_content);
			holder.leftUserLogo = (ImageView) convertView
					.findViewById(R.id.message_left_logo);
			holder.rightUserLogo = (ImageView) convertView
					.findViewById(R.id.message_rigth_logo);
			holder.rightTextView = (TextView) convertView
					.findViewById(R.id.message_rigth_content);
			holder.createTimeTextView = (TextView) convertView
					.findViewById(R.id.message_send_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final TextView leftTextView = holder.leftTextView;
		final TextView rightTextView = holder.rightTextView;
		final ImageView leftUserLogo = holder.leftUserLogo;
		final TextView createTimeTextView = holder.createTimeTextView;
		final ImageView rightUserLogo = holder.rightUserLogo;

		return null;
	}

	private class ViewHolder {
		public TextView leftTextView;
		public ImageView leftUserLogo;
		public ImageView rightUserLogo;
		public TextView createTimeTextView;
		public TextView rightTextView;
	}
}
