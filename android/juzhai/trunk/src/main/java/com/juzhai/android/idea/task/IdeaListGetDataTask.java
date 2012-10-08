package com.juzhai.android.idea.task;

import android.content.Context;

import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.idea.service.impl.IdeaService;

public class IdeaListGetDataTask extends GetDataTask<IdeaListResult, Idea> {

	public IdeaListGetDataTask(Context context,
			JuzhaiRefreshListView refreshListView) {
		super(context, refreshListView);
	}

	@Override
	protected IdeaListResult doInBackground(Object... params) {
		long categoryId = (Long) params[0];
		String orderType = String.valueOf(params[1]);
		int page = (Integer) params[2];
		try {
			IIdeaService ideaService = new IdeaService();
			return ideaService.list(context, categoryId, orderType, page);
		} catch (IdeaException e) {
			return null;
		}
	}
}
