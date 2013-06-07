package com.easylife.weather.main.adapter;

import org.springframework.util.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylife.weather.R;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.model.WeatherInfo;

public class MainListAdapter extends BaseAdapter {
	protected Context mContext;
	protected LayoutInflater inflater;
	private WeatherInfo todayWeather;

	public MainListAdapter(WeatherInfo todayWeather, Context mContext) {
		this.mContext = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.todayWeather = todayWeather;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.item_main, null);
		ImageView lIcon = (ImageView) convertView
				.findViewById(R.id.l_item_image);
		TextView lText = (TextView) convertView.findViewById(R.id.l_item_text);

		ImageView rIcon = (ImageView) convertView
				.findViewById(R.id.r_item_image);
		TextView rText = (TextView) convertView.findViewById(R.id.r_item_text);
		switch (position) {
		case 0:
			getWeather(lIcon, lText);

			TextView tmpIcon = (TextView) convertView
					.findViewById(R.id.tmp_item_icon);
			TextView tmpText = (TextView) convertView
					.findViewById(R.id.tmp_item_text);
			getTmp(rIcon, rText, tmpIcon, tmpText);
			break;
		case 1:
			getWind(lIcon, lText);
			getHum(rIcon, rText);
			break;
		case 2:
			getUvidx(lIcon, lText);
			getPM2(rIcon, rText);
			break;
		default:
			break;
		}
		return convertView;
	}

	private void getTmp(ImageView icon, TextView text, TextView tmpIcon,
			TextView tmpText) {
		icon.setVisibility(View.GONE);
		text.setVisibility(View.GONE);
		tmpIcon.setVisibility(View.VISIBLE);
		tmpText.setVisibility(View.VISIBLE);
		String tmp = todayWeather.getNowTmp();
		if (!StringUtils.hasText(tmp)) {
			tmp = todayWeather.getMinTmp();
		}
		tmpIcon.setText(todayWeather.getNowTmp());
		if (!StringUtils.hasText(todayWeather.getMinTmp())
				&& !StringUtils.hasText(todayWeather.getMaxTmp())) {
			tmpText.setText(mContext.getResources().getString(R.string.tmp,
					todayWeather.getNowTmp(), todayWeather.getNowTmp()));
		} else if (StringUtils.hasText(todayWeather.getMinTmp())
				&& !StringUtils.hasText(todayWeather.getMaxTmp())) {
			tmpText.setText(mContext.getResources().getString(R.string.min_tmp,
					todayWeather.getMinTmp()));
		} else if (!StringUtils.hasText(todayWeather.getMinTmp())
				&& StringUtils.hasText(todayWeather.getMaxTmp())) {
			tmpText.setText(mContext.getResources().getString(R.string.max_tmp,
					todayWeather.getMaxTmp()));
		} else {
			tmpText.setText(mContext.getResources().getString(R.string.tmp,
					todayWeather.getMinTmp(), todayWeather.getMaxTmp()));
		}

	}

	private void getWeather(ImageView icon, TextView text) {
		int iconId = mContext.getResources().getIdentifier(
				"w" + todayWeather.getIcon(), "drawable",
				mContext.getPackageName());
		icon.setBackgroundResource(iconId);
		text.setText(todayWeather.getSky());
	}

	private void getPM2(ImageView icon, TextView text) {
		int pm25 = WeatherUtils.getPM2Level(todayWeather);
		if (pm25 != 0) {
			int iconId = mContext.getResources().getIdentifier("pm25_" + pm25,
					"drawable", mContext.getPackageName());
			icon.setBackgroundResource(iconId);
			if (pm25 > 2) {
				text.setText(mContext.getResources().getString(
						mContext.getResources().getIdentifier("pm25_" + pm25,
								"string", mContext.getPackageName())));
			} else {
				text.setText(mContext.getResources().getString(R.string.pm25,
						todayWeather.getAir().getAqigrade()));
			}
		}
	}

	private void getWind(ImageView icon, TextView text) {
		int wind = WeatherUtils.getWindLevel(todayWeather.getWindPower());
		String strWind = "0";
		if (wind > 6) {
			strWind = "7";
		}
		int iconId = mContext.getResources().getIdentifier("windy_" + strWind,
				"drawable", mContext.getPackageName());
		icon.setBackgroundResource(iconId);
		text.setText(mContext.getResources().getString(R.string.wind,
				todayWeather.getWindDirection(), wind));
	}

	private void getHum(ImageView icon, TextView text) {
		int hum = 0;
		if (StringUtils.hasText(todayWeather.getHum())) {
			hum = Integer.parseInt(todayWeather.getHum());
		}
		String humStr = "00";
		if (hum > 40 && hum < 70) {
			humStr = "40";
		} else if (hum > 70) {
			humStr = "70";
		}
		int iconId = mContext.getResources().getIdentifier(
				"humidity_" + humStr, "drawable", mContext.getPackageName());
		icon.setBackgroundResource(iconId);
		text.setText(mContext.getResources().getString(R.string.hum, hum, "%"));
	}

	private void getUvidx(ImageView icon, TextView text) {
		String uvidx = "00";
		if (todayWeather.getUvidxLv() > 3) {
			uvidx = "100";
		}
		text.setText(todayWeather.getUvidx());
		int iconId = mContext.getResources().getIdentifier("ray_" + uvidx,
				"drawable", mContext.getPackageName());
		icon.setBackgroundResource(iconId);

	}

}
