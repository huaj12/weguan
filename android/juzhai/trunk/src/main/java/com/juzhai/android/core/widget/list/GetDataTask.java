package com.juzhai.android.core.widget.list;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.model.PageList;
import com.juzhai.android.core.model.Pager;
import com.juzhai.android.core.model.Result;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.Mode;

public abstract class GetDataTask<T extends Result<? extends PageList<E>>, E extends Entity>
		extends AsyncTask<Object, Integer, T> {

	protected JuzhaiRefreshListView refreshListView;

	protected Context context;

	public GetDataTask(Context context, JuzhaiRefreshListView refreshListView) {
		this.context = context;
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
			loadComplete();
		}
		if (null != refreshListView) {
			refreshListView.onRefreshComplete();
			if (null != result && result.getSuccess()) {
				refreshListView.setMode(result.getResult().getPager()
						.getHasNext() ? Mode.BOTH : Mode.PULL_DOWN_TO_REFRESH);
			}
		}
		super.onPostExecute(result);
	}

	protected void loadComplete() {
	}
}
