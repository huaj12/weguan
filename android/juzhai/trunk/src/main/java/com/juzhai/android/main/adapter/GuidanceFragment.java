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
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.main.service.impl.GuidanceService;
import com.juzhai.android.passport.activity.LoginActivity;
import com.juzhai.android.passport.data.UserCache;

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
		switch (index) {
		case 0:
			layout.setBackgroundResource(R.drawable.yd1);
			break;
		case 1:
			layout.setBackgroundResource(R.drawable.yd2);
			break;
		case 2:
			layout.setBackgroundResource(R.drawable.yd3);
			break;
		case 3:
			layout.setBackgroundResource(R.drawable.yd4);
			Button button = new Button(getActivity());
			button.setBackgroundResource(R.drawable.guidance_selector_button);

			LayoutParams buttonLayoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			buttonLayoutParams.setMargins(-1, -1, -1,
					UIUtil.dip2px(getActivity(), 40));
			button.setLayoutParams(buttonLayoutParams);
			layout.addView(button);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new GuidanceService().completeGuide(getActivity());
					if (UserCache.hasLogin()) {
						startActivity(new Intent(getActivity(),
								MainTabActivity.class));
					} else {
						startActivity(new Intent(getActivity(),
								LoginActivity.class));
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
