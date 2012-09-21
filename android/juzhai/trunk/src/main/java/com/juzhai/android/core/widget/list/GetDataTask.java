package com.juzhai.android.core.widget.list;

import java.util.List;

import android.os.AsyncTask;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.model.PageList;
import com.juzhai.android.core.model.Pager;
import com.juzhai.android.core.model.Result;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshBase.Mode;

public abstract class GetDataTask<T extends Result<? extends PageList<E>>, E extends Entity>
		extends AsyncTask<Object, Integer, T> {

	protected JuzhaiRefreshListView refreshListView;

	public GetDataTask(JuzhaiRefreshListView refreshListView) {
		this.refreshListView = refreshListView;
	}

	protected void onPostExecute(T result) {
		if (null == result || !result.getSuccess()) {
			DialogUtils.showToastText(refreshListView.getContext(),
					R.string.system_internet_erorr);
		} else {
			// add or override
			Pager pager = result.getResult().getPager();
			PageAdapter<E> adapter = refreshListView.getPageAdapter();
			adapter.setPager(pager);
			List<E> list = result.getResult().getList();
			if (pager.getCurrentPage() == 1) {
				adapter.setDatas(list);
			} else {
				adapter.pushDatas(list);
			}

			refreshListView
					.setMode(result.getResult().getPager().getHasNext() ? Mode.BOTH
							: Mode.PULL_DOWN_TO_REFRESH);
		}
		if (null != refreshListView) {
			refreshListView.onRefreshComplete();
		}
		super.onPostExecute(result);
	}
}
