package com.juzhai.android.idea.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.image.ImageLoaderCallback;
import com.juzhai.android.core.widget.image.ImageViewLoader;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

public class IdeaListActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle("出去玩");
		setNavContentView(R.layout.idea_list);

		final ImageView imageView = (ImageView) findViewById(R.id.test_img);

		ImageViewLoader nid = ImageViewLoader.getInstance(this);
		nid.fetchImage(
				"http://static.51juzhai.com/upload/idea/0/0/118/450/ab8ea94a-d42a-49e3-8e13-0b4bf795ac06.jpg",
				0, imageView, new ImageLoaderCallback() {
					@Override
					public void imageLoaderFinish(Bitmap bitmap) {
						imageView.setImageBitmap(bitmap);
					}
				});
	}
}
