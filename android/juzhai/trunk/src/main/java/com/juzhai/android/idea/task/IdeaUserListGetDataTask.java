package com.juzhai.android.idea.task;

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
			return ideaService.listIdeaUser(ideaId, page);
		} catch (IdeaException e) {
			return null;
		}
	}
}
