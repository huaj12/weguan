package com.juzhai.android.core.widget.tab;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.UIUtil;

public class TabBarItem<C> {

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

	public TabBarItem(int icon, int highligntIcon, int title, C content) {
		super();
		this.icon = icon;
		this.highligntIcon = highligntIcon;
		this.title = title;
		this.content = content;
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

	public View getTabItemView(LayoutInflater layoutInflater, int tabItemLayout) {
		View tabItemView = layoutInflater.inflate(tabItemLayout, null);
		TextView textView = (TextView) tabItemView
				.findViewById(R.id.tab_item_tv);
		textView.setPadding(0, UIUtil.dip2px(layoutInflater.getContext(), 7),
				0, 0);
		textView.setText(this.title);
		textView.setTextColor(layoutInflater.getContext().getResources()
				.getColor(android.R.color.white));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		textView.setCompoundDrawablesWithIntrinsicBounds(0, this.icon, 0, 0);
		return tabItemView;
	}

	public void selectedTab(TextView textView) {
		if (this.highligntIcon > 0) {
			textView.setCompoundDrawablesWithIntrinsicBounds(0,
					this.highligntIcon, 0, 0);
		}
	}

	public void unSelectedTab(TextView textView) {
		textView.setCompoundDrawablesWithIntrinsicBounds(0, this.icon, 0, 0);
	}
}
