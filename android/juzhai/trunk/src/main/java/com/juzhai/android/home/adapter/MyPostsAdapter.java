package com.juzhai.android.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.PageAdapter;
import com.juzhai.android.passport.model.Post;
import com.juzhai.android.post.helper.IPostViewHelper;
import com.juzhai.android.post.helper.impl.PostViewHelper;

public class MyPostsAdapter extends PageAdapter<Post> {
	private IPostViewHelper postViewHelper = new PostViewHelper();

	public MyPostsAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_my_post, null);
			holder = new ViewHolder();
			holder.contentView = (TextView) convertView
					.findViewById(R.id.post_content);
			holder.postImageView = (ImageView) convertView
					.findViewById(R.id.post_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Post post = data.getDatas().get(position);
		TextView contentView = holder.contentView;
		ImageView postImageView = holder.postImageView;
		contentView.setText(mContext.getResources().getString(
				R.string.post_head)
				+ " : " + post.getContent());
		postViewHelper.showSmallPostImage(mContext, post, postImageView);
		return convertView;
	}

	private class ViewHolder {
		public TextView contentView;
		public ImageView postImageView;
	}

}
