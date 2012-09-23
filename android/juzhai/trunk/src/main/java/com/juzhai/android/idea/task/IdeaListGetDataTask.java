package com.juzhai.android.idea.task;

import java.util.HashMap;
import java.util.Map;

import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.idea.service.impl.IdeaService;

public class IdeaListGetDataTask extends GetDataTask<IdeaListResult, Idea> {

	public IdeaListGetDataTask(JuzhaiRefreshListView refreshListView) {
		super(refreshListView);
	}

	@Override
	protected IdeaListResult doInBackground(Object... params) {
		int categoryId = (Integer) params[0];
		String orderType = String.valueOf(params[1]);
		int page = (Integer) params[2];
		try {
			IIdeaService ideaService = new IdeaService();
			Map<String, String> values = new HashMap<String, String>();
			values.put("categoryId", String.valueOf(categoryId));
			values.put("orderType", orderType);
			return ideaService.list(values, page);
		} catch (IdeaException e) {
			return null;
		}
	}
}
