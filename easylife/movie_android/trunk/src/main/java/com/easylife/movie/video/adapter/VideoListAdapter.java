package com.easylife.movie.video.adapter;

import java.util.List;

import org.springframework.util.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylife.movie.R;
import com.easylife.movie.core.utils.ImageUtils;
import com.easylife.movie.core.widget.image.ImageLoaderCallback;
import com.easylife.movie.core.widget.image.ImageViewLoader;
import com.easylife.movie.core.widget.list.PageAdapter;
import com.easylife.movie.video.model.Video;

public class VideoListAdapter extends PageAdapter<Video> {

	public VideoListAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_main, null);
			holder = new ViewHolder();
			holder.itemImage = (ImageView) convertView
					.findViewById(R.id.item_image);
			holder.itemText = (TextView) convertView
					.findViewById(R.id.item_text);
			holder.itemTag = (ImageView) convertView
					.findViewById(R.id.item_tag);
			holder.itemTime = (TextView) convertView
					.findViewById(R.id.item_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ImageView itemImage = holder.itemImage;
		TextView itemText = holder.itemText;
		ImageView itemTag = holder.itemTag;
		TextView itemTime = holder.itemTime;
		List<Video> list = data.getDatas();
		Video video = list.get(position);
		if (StringUtils.hasText(video.getPosterImg())) {
			ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
			nid.fetchImage(video.getPosterImg(), R.drawable.loading_small,
					itemImage, new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils
										.zoomWidthCutMiddle(bitmap, 82, 49,
												mContext);
								itemImage.setImageBitmap(zoomBitmap);
							}
						}
					});
		}
		itemText.setText(video.getTitle());
		int iconId = mContext.getResources().getIdentifier(
				"tag" + video.getCategoryId(), "drawable",
				mContext.getPackageName());
		itemTag.setBackgroundResource(iconId);
		itemTime.setText(video.getPlayTime());
		return convertView;
	}

	private class ViewHolder {
		public ImageView itemImage;
		public TextView itemText;
		public ImageView itemTag;
		public TextView itemTime;
	}
}
