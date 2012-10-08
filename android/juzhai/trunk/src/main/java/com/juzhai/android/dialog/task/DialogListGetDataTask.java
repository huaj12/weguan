package com.juzhai.android.dialog.task;

import android.content.Context;
import android.util.Log;

import com.juzhai.android.core.model.Result.DialogListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.dialog.exception.DialogException;
import com.juzhai.android.dialog.model.Dialog;
import com.juzhai.android.dialog.service.IDialogService;
import com.juzhai.android.dialog.service.impl.DialogService;

public class DialogListGetDataTask extends
		GetDataTask<DialogListResult, Dialog> {

	public DialogListGetDataTask(Context context,
			JuzhaiRefreshListView refreshListView) {
		super(context, refreshListView);
	}

	@Override
	protected DialogListResult doInBackground(Object... params) {
		DialogListResult result = null;
		int page = (Integer) params[0];
		try {
			IDialogService dialogService = new DialogService();
			result = dialogService.list(context, page);
		} catch (DialogException e) {
			Log.i("debug", e.getMessage());
		}
		return result;
	}
}
