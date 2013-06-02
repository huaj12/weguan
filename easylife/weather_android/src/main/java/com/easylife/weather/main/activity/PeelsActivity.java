package com.easylife.weather.main.activity;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;

import com.easylife.weather.R;
import com.easylife.weather.core.activity.BaseActivity;
import com.easylife.weather.core.data.CommonData;
import com.easylife.weather.main.adapter.PeelsAdapter;
import com.easylife.weather.main.data.WeatherDataManager;

public class PeelsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_peels);
		GridView gridview = (GridView) findViewById(R.id.peels_grid_view);

		gridview.setAdapter(new PeelsAdapter(CommonData
				.getColors(PeelsActivity.this), PeelsActivity.this));
		gridview.setSelection(selectColorIndex());

	}

	private int selectColorIndex() {
		List<String> colors = CommonData.getColors(PeelsActivity.this);
		for (int i = 0; i < colors.size(); i++) {
			String color = colors.get(i);
			int colorInt = Color.argb(255,
					Integer.parseInt(color.substring(0, 2), 16),
					Integer.parseInt(color.substring(2, 4), 16),
					Integer.parseInt(color.substring(4, 6), 16));
			if (colorInt == WeatherDataManager
					.getBackgroundColor(PeelsActivity.this)) {
				return i;
			}
		}
		return 0;
	}
}
