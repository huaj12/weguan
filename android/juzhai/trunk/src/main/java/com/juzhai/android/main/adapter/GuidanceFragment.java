package com.juzhai.android.main.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.juzhai.android.R;

public final class GuidanceFragment extends Fragment {

	static final String INDEX_KEY = "index";

	public GuidanceFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int index = getArguments().getInt(INDEX_KEY);
		ImageView imageView = new ImageView(getActivity());
		switch (index) {
		case 0:
			imageView.setImageResource(R.drawable.app_bg);
			break;
		case 1:
			imageView.setImageResource(R.drawable.arrow_down);
			break;
		case 2:
			imageView.setImageResource(R.drawable.back_selector_button);
			break;
		case 3:
			imageView.setImageResource(R.drawable.about_selector_button);
			break;
		}
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		layout.setGravity(Gravity.CENTER);
		layout.addView(imageView);

		return layout;
	}
}
