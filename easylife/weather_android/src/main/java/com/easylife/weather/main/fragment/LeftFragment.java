package com.easylife.weather.main.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.util.CollectionUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easylife.weather.R;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.model.ForecastHour;
import com.easylife.weather.main.model.WeatherInfo;

public class LeftFragment extends Fragment {
	private Context context;
	private LinearLayout todayLayout;
	private LinearLayout futureLayout;
	private LayoutInflater inflater;
	private TextView todayWeatherView;
	private LinearLayout todayWeatherDivider;
	private TextView futureWeatherView;
	private LinearLayout futureWeatherDivider;
	private RelativeLayout noDataLayout;

	public LeftFragment() {
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_left, null);
		todayLayout = (LinearLayout) view.findViewById(R.id.today_weather_view);
		futureLayout = (LinearLayout) view
				.findViewById(R.id.future_weather_view);
		todayWeatherDivider = (LinearLayout) view
				.findViewById(R.id.today_weather_title_divider);
		todayWeatherView = (TextView) view
				.findViewById(R.id.today_weather_title_view);
		futureWeatherView = (TextView) view
				.findViewById(R.id.future_weather_title_view);
		futureWeatherDivider = (LinearLayout) view
				.findViewById(R.id.future_weather_title_divider);
		noDataLayout = (RelativeLayout) view
				.findViewById(R.id.no_data_tip_layout);
		this.inflater = inflater;
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		WeatherInfo weatherInfo = WeatherDataManager.getWeatherInfos(
				DateUtil.getToday(), context);
		List<ForecastHour> forecastList = weatherInfo.getForecastHours();
		if (!CollectionUtils.isEmpty(forecastList)) {
			for (ForecastHour forecastHour : forecastList) {
				View itemView = inflater.inflate(R.layout.item_weater, null);
				ImageView iconView = (ImageView) itemView
						.findViewById(R.id.icon_view);
				TextView timeView = (TextView) itemView
						.findViewById(R.id.time_view);
				TextView weatherView = (TextView) itemView
						.findViewById(R.id.weather_view);

				int iconId = context.getResources().getIdentifier(
						"w" + forecastHour.getIcon() + "_s", "drawable",
						context.getPackageName());
				iconView.setBackgroundDrawable(context.getResources()
						.getDrawable(iconId));
				timeView.setText(WeatherUtils.getforecastHourText(
						forecastHour.getTimeInterval(), context));
				weatherView.setText(context.getResources().getString(
						R.string.weather_info, forecastHour.getSky(),
						forecastHour.getMinTmp(), forecastHour.getMaxTmp()));
				todayLayout.addView(itemView);
			}
		} else {
			todayLayout.setVisibility(View.GONE);
			todayWeatherView.setVisibility(View.GONE);
			todayWeatherDivider.setVisibility(View.GONE);
		}
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < 4; i++) {
			cal.add(Calendar.DAY_OF_WEEK, 1);
			String date = DateUtil.sdf.format(cal.getTime());
			WeatherInfo info = WeatherDataManager
					.getWeatherInfos(date, context);
			if (info != null && info.getIcon() != null) {
				View itemView = inflater.inflate(R.layout.item_weater, null);
				ImageView iconView = (ImageView) itemView
						.findViewById(R.id.icon_view);
				TextView timeView = (TextView) itemView
						.findViewById(R.id.time_view);
				TextView weatherView = (TextView) itemView
						.findViewById(R.id.weather_view);
				int iconId = context.getResources().getIdentifier(
						"w" + info.getIcon() + "_s", "drawable",
						context.getPackageName());
				iconView.setBackgroundDrawable(context.getResources()
						.getDrawable(iconId));
				SimpleDateFormat futureDate = new SimpleDateFormat("MM.dd");
				if (DateUtil.getTomorrow().equals(date)) {
					timeView.setText(context.getResources()
							.getString(
									R.string.future_date,
									context.getResources().getString(
											R.string.tomorrow),
									futureDate.format(cal.getTime())));
				} else {
					timeView.setText(context.getResources().getString(
							R.string.future_date,
							DateUtil.WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1],
							futureDate.format(cal.getTime())));
				}
				weatherView.setText(context.getResources().getString(
						R.string.weather_info, info.getDaytimeSky(),
						info.getMinTmp(), info.getMaxTmp()));
				futureLayout.addView(itemView);
				flag = true;
			}
		}
		if (!flag) {
			futureWeatherView.setVisibility(View.GONE);
			futureWeatherDivider.setVisibility(View.GONE);
			futureLayout.setVisibility(View.GONE);
		}

		if (futureWeatherView.getVisibility() == View.GONE
				&& todayWeatherView.getVisibility() == View.GONE) {
			noDataLayout.setVisibility(View.VISIBLE);
		}

	}
}
