package com.juzhai.android.dialog.task;

import android.util.Log;

import com.juzhai.android.core.model.Result.DialogContentListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.dialog.exception.DialogContentException;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.dialog.service.IDialogContentService;
import com.juzhai.android.dialog.service.impl.DialogContentService;

public class DialogContentListGetDataTask extends
		GetDataTask<DialogContentListResult, DialogContent> {

	public DialogContentListGetDataTask(JuzhaiRefreshListView refreshListView) {
		super(refreshListView);

	}

	@Override
	protected DialogContentListResult doInBackground(Object... params) {
		DialogContentListResult result = null;
		long uid = (Long) params[0];
		int page = (Integer) params[1];
		try {
			IDialogContentService dialogContentService = new DialogContentService();
			result = dialogContentService.list(uid, page);
		} catch (DialogContentException e) {
			Log.i("debug", e.getMessage());
		}
		return result;
	}

}
