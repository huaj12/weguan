/**
 * 
 */
package com.juzhai.android.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.JzUtils;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;

public class PreviewActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_preview_image);
		Intent intent = getIntent();
		String imageUrl = intent.getStringExtra("imageUrl");
		Bitmap imageBitmap = intent.getParcelableExtra("imageBitmap");
		int defaultImage = intent.getIntExtra("defaultImage",
				R.drawable.load_pic_s);
		final ImageView imageView = (ImageView) findViewById(R.id.preview_image);
		final ProgressBar bar = (ProgressBar) findViewById(R.id.progressbar);
		if (imageBitmap == null) {
			ImageViewLoader nid = ImageViewLoader
					.getInstance(PreviewActivity.this);
			nid.fetchImage(JzUtils.getImageUrl(imageUrl), defaultImage,
					imageView, new ImageLoaderCallback() {
						@Override
						public void imageLoaderFinish(Bitmap bitmap) {
							if (bitmap != null) {
								bar.setVisibility(View.GONE);
								imageView.setImageBitmap(bitmap);
							}
						}
					});
		} else {
			bar.setVisibility(View.GONE);
			imageView.setImageBitmap(imageBitmap);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.finish();
		return super.onTouchEvent(event);
	}

}
