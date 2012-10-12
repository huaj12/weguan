package com.juzhai.android.core.widget.tab;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.UIUtil;

public class TabBarItem<C> {

	/**
	 * tab的View
	 */
	private View tabItemView;

	/**
	 * tabItem上的图标
	 */
	private int icon;

	/**
	 * tabItem上高亮的图标
	 */
	private int highligntIcon;

	/**
	 * tabItem上的title
	 */
	private int title;

	/**
	 * 内容
	 */
	private C content;

	/**
	 * 消息提示数
	 */
	private int badgeValue;

	public TabBarItem(int icon, int highligntIcon, int title, C content) {
		super();
		this.icon = icon;
		this.highligntIcon = highligntIcon;
		this.title = title;
		this.content = content;
	}

	public TabBarItem(int icon, int highligntIcon, int title, C content,
			int badgeValue) {
		super();
		this.icon = icon;
		this.highligntIcon = highligntIcon;
		this.title = title;
		this.content = content;
		this.badgeValue = badgeValue;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public int getHighligntIcon() {
		return highligntIcon;
	}

	public void setHighligntIcon(int highligntIcon) {
		this.highligntIcon = highligntIcon;
	}

	public C getContent() {
		return content;
	}

	public void setContent(C content) {
		this.content = content;
	}

	public int getBadgeValue() {
		return badgeValue;
	}

	public void setBadgeValue(int badgeValue) {
		this.badgeValue = badgeValue;
	}

	public View getTabItemView(LayoutInflater layoutInflater, int tabItemLayout) {
		this.tabItemView = layoutInflater.inflate(tabItemLayout, null);
		TextView textView = (TextView) tabItemView
				.findViewById(R.id.tab_item_tv);
		textView.setPadding(0, UIUtil.dip2px(layoutInflater.getContext(), 7),
				0, 0);
		textView.setText(this.title);
		textView.setTextColor(layoutInflater.getContext().getResources()
				.getColor(android.R.color.white));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		textView.setCompoundDrawablesWithIntrinsicBounds(0, this.icon, 0, 0);

		TextView badgeView = (TextView) tabItemView
				.findViewById(R.id.tab_item_badge);
		if (badgeValue > 0) {
			badgeView.setText(String.valueOf(badgeValue));
			badgeView.setVisibility(View.VISIBLE);
		} else {
			badgeView.setVisibility(View.GONE);
		}

		return tabItemView;
	}

	public void selectedTab() {
		if (tabItemView != null) {
			TextView textView = (TextView) tabItemView
					.findViewById(R.id.tab_item_tv);
			if (this.highligntIcon > 0) {
				textView.setCompoundDrawablesWithIntrinsicBounds(0,
						this.highligntIcon, 0, 0);
			}
		}
	}

	public void unSelectedTab() {
		if (tabItemView != null) {
			TextView textView = (TextView) tabItemView
					.findViewById(R.id.tab_item_tv);
			if (this.highligntIcon > 0) {
				textView.setCompoundDrawablesWithIntrinsicBounds(0, this.icon,
						0, 0);
			}
		}
	}

	public void updateBadgeValue(int badgeValue) {
		if (tabItemView != null) {
			this.badgeValue = badgeValue;
			TextView textView = (TextView) tabItemView
					.findViewById(R.id.tab_item_badge);
			if (badgeValue > 0) {
				textView.setText(String.valueOf(badgeValue));
				textView.setVisibility(View.VISIBLE);
			} else {
				textView.setVisibility(View.GONE);
			}
		}
	}
}
