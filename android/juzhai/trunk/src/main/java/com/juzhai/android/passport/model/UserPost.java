package com.juzhai.android.passport.model;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;

public class UserPost extends Entity {
	private static final long serialVersionUID = -4148599586995728665L;
	private long postId;
	private String purpose;
	private String content;
	private String place;
	private String pic;
	private String bigPic;
	private String categoryName;
	private String date;
	private int respCnt;
	private boolean hasResp;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getRespCnt() {
		return respCnt;
	}

	public void setRespCnt(int respCnt) {
		this.respCnt = respCnt;
	}

	public boolean isHasResp() {
		return hasResp;
	}

	public void setHasResp(boolean hasResp) {
		this.hasResp = hasResp;
	}

	public String getBigPic() {
		return bigPic;
	}

	public void setBigPic(String bigPic) {
		this.bigPic = bigPic;
	}

	@Override
	public Object getIdentify() {
		return this.postId;
	}

	public void setPostImage(final ImageView imageView, final int width,
			final int height, final Context mContext) {
		imageView.setVisibility(View.VISIBLE);
		ImageViewLoader nid = ImageViewLoader.getInstance(mContext);
		if (StringUtils.isNotEmpty(getPic())) {
			nid.fetchImage(JzUtils.getImageUrl(getPic()), 0, imageView,
					new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								Bitmap zoomBitmap = ImageUtils.zoomBitmap(
										bitmap, UIUtil.dip2px(mContext, width),
										UIUtil.dip2px(mContext, height));
								imageView.setImageBitmap(ImageUtils
										.getRoundedCornerBitmap(zoomBitmap, 10));
							}
						}
					});
		} else {
			imageView.setVisibility(View.GONE);
		}
	}

}
