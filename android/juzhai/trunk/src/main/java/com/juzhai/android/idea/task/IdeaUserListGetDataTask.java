package com.juzhai.android.idea.task;

import android.content.Context;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.idea.service.impl.IdeaService;

public class IdeaUserListGetDataTask extends
		GetDataTask<IdeaUserListResult, IdeaUser> {
	private TextView ideaContentBegin;
	private long city = 0;
	private int page = 1;
	private Integer gender = null;

	public IdeaUserListGetDataTask(Context context,
			JuzhaiRefreshListView refreshListView, TextView ideaContentBegin) {
		super(context, refreshListView);
		this.ideaContentBegin = ideaContentBegin;
	}

	@Override
	protected IdeaUserListResult doInBackground(Object... params) {
		long ideaId = (Long) params[0];
		city = (Long) params[1];
		gender = (params[2] == null ? null : (Integer) params[2]);
		page = (Integer) params[3];
		try {
			IIdeaService ideaService = new IdeaService();
			return ideaService
					.listIdeaUser(context, ideaId, city, gender, page);
		} catch (IdeaException e) {
			return null;
		}
	}

	@Override
	protected void onPostExecute(IdeaUserListResult result) {
		super.onPostExecute(result);
		if (result != null && result.getSuccess()) {
			if (city > 0 && page == 1 && gender == null) {
				ideaContentBegin.setText(context.getResources().getString(
						R.string.idea_users_content_begin_text,
						result.getResult().getList().size()));
			}
		}

	}

}
