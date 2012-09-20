package com.juzhai.android.idea.adapter.viewholder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IdeaListViewHolder {
	TextView contentText;
	ImageView imageView;
	Button wantbtn;
	TextView textView;

	public TextView getContentText() {
		return contentText;
	}

	public void setContentText(TextView contentText) {
		this.contentText = contentText;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public Button getWantbtn() {
		return wantbtn;
	}

	public void setWantbtn(Button wantbtn) {
		this.wantbtn = wantbtn;
	}

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

}
