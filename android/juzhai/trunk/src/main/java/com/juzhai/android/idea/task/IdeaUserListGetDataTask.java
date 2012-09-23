package com.juzhai.android.idea.task;

import java.util.HashMap;
import java.util.Map;

import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.idea.service.impl.IdeaService;

public class IdeaUserListGetDataTask extends
		GetDataTask<IdeaUserListResult, IdeaUser> {

	public IdeaUserListGetDataTask(JuzhaiRefreshListView refreshListView) {
		super(refreshListView);
	}

	@Override
	protected IdeaUserListResult doInBackground(Object... params) {
		long ideaId = (Long) params[0];
		int page = (Integer) params[1];
		try {
			IIdeaService ideaService = new IdeaService();
			Map<String, String> values = new HashMap<String, String>();
			values.put("ideaId", String.valueOf(ideaId));
			return ideaService.listIdeaUser(values, page);
		} catch (IdeaException e) {
			return null;
		}
	}
}
