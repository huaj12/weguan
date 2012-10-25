package com.juzhai.android.core.widget.tab;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import android.app.ActivityGroup;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

import com.juzhai.android.R;

public class TabBar<C> {

	private TabHost tabHost;

	private List<TabBarItem<C>> tabBarItems;

	private int bgResources;

	private int tabHostResource;

	public TabBar(int tabHostResource) {
		this.tabHostResource = tabHostResource;
	}

	// public TabBar(TabHost tabHost) {
	// super();
	// this.tabHost = tabHost;
	// }

	// public TabBar(TabHost tabHost, List<TabBarItem<C>> tabBarItems) {
	// super();
	// this.tabHost = tabHost;
	// this.tabBarItems = tabBarItems;
	// }

	public TabHost getTabHost() {
		return tabHost;
	}

	public List<TabBarItem<C>> getTabBarItems() {
		return tabBarItems;
	}

	public void addTabBarItem(TabBarItem<C> tabBarItem) {
		if (tabBarItems == null) {
			tabBarItems = new ArrayList<TabBarItem<C>>();
		}
		tabBarItems.add(tabBarItem);
	}

	public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
		if (tabHost != null) {
			tabHost.setOnTabChangedListener(onTabChangeListener);
		}
	}

	public int getBgResources() {
		return bgResources;
	}

	public void setBgResources(int bgResources) {
		this.bgResources = bgResources;
	}

	public View build(LayoutInflater layoutInflater,
			ActivityGroup activityGroup, int currentIndex) {
		if (tabHostResource <= 0) {
			Log.e("tabBar", "tabHostResource is empty.");
			return null;
		}
		if (currentIndex >= 0) {
			tabHost.setCurrentTab(currentIndex);
		}
		View view = layoutInflater.inflate(tabHostResource, null);
		tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		if (activityGroup == null) {
			tabHost.setup();
		} else {
			tabHost.setup(activityGroup.getLocalActivityManager());
		}

		if (null == tabHost) {
			Log.e("tabBar", "tabHost is null.");
			return null;
		}
		if (CollectionUtils.isEmpty(tabBarItems)) {
			Log.e("tabBar", "tabBarItems is empty.");
			return null;
		}

		setTabSpecs(layoutInflater);
		setTabWidgetBg();
		setTabHostListener();
		refreshTabBarItemState();
		return view;
	}

	private void setTabSpecs(LayoutInflater layoutInflater) {
		for (TabBarItem<C> tabBarItem : tabBarItems) {
			TabHost.TabSpec tabSpec = tabHost.newTabSpec(
					String.valueOf(tabBarItem.getTitle())).setIndicator(
					tabBarItem
							.getTabItemView(layoutInflater, R.layout.item_tab));
			if (tabBarItem.getContent() instanceof Integer) {
				tabSpec.setContent((Integer) tabBarItem.getContent());
			} else if (tabBarItem.getContent() instanceof Intent) {
				tabSpec.setContent((Intent) tabBarItem.getContent());
			} else if (tabBarItem.getContent() instanceof TabContentFactory) {
				tabSpec.setContent((TabContentFactory) tabBarItem.getContent());
			}
			tabHost.addTab(tabSpec);
		}
	}

	private void setTabHostListener() {
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				refreshTabBarItemState();
			}
		});
	}

	private void refreshTabBarItemState() {
		for (int i = 0; i < tabBarItems.size(); i++) {
			TabBarItem<C> tabBarItem = tabBarItems.get(i);
			if (i == tabHost.getCurrentTab()) {
				tabBarItem.selectedTab();
			} else {
				tabBarItem.unSelectedTab();
			}
		}
	}

	private void setTabWidgetBg() {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			View view = tabHost.getTabWidget().getChildAt(i);
			view.setBackgroundResource(bgResources);
		}
	}

	public void setBadgeValue(int tabIndex, int badgeValue) {
		TabBarItem<C> tabBarItem = tabBarItems.get(tabIndex);
		if (null != tabBarItem) {
			tabBarItem.updateBadgeValue(badgeValue);
		}
	}

	public int getBadgeValue(int tabIndex) {
		TabBarItem<C> tabBarItem = tabBarItems.get(tabIndex);
		if (null != tabBarItem) {
			return tabBarItem.getBadgeValue();
		}
		return 0;
	}
}
