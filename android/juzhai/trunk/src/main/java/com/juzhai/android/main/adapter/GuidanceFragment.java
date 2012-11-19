package com.juzhai.android.main.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.main.activity.LoginAndRegisterActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.main.service.impl.GuidanceService;
import com.juzhai.android.passport.data.UserCacheManager;

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
		RelativeLayout layout = new RelativeLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		layout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
		layout.setBackgroundColor(getResources().getColor(
				R.color.guidance_bg_color));
		LayoutParams imageLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ImageView imageView = new ImageView(getActivity());
		imageView.setLayoutParams(imageLayoutParams);
		layout.addView(imageView);
		switch (index) {
		case 0:
			imageView.setBackgroundResource(R.drawable.yd1);

			break;
		case 1:
			imageView.setBackgroundResource(R.drawable.yd2);
			break;
		case 2:
			imageView.setBackgroundResource(R.drawable.yd3);
			break;
		case 3:
			imageView.setBackgroundResource(R.drawable.yd4);
			Button button = new Button(getActivity());
			button.setBackgroundResource(R.drawable.guidance_selector_button);

			LayoutParams buttonLayoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			buttonLayoutParams.setMargins(-1, -1, -1,
					UIUtil.dip2px(getActivity(), 40));
			button.setLayoutParams(buttonLayoutParams);
			layout.addView(button);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new GuidanceService().completeGuide(getActivity());
					if (UserCacheManager.getUserCache(getActivity()).hasLogin()) {
						startActivity(new Intent(getActivity(),
								MainTabActivity.class));
					} else {
						startActivity(new Intent(getActivity(),
								LoginAndRegisterActivity.class));
					}
					getActivity().finish();
				}
			});
			break;
		}
		return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
}
