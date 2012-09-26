package com.juzhai.android.dialog.listener;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.DialogContentResult;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.passport.data.UserCache;

public class SendMessageListener implements OnClickListener {
	private String uri = "dialog/sendSms";
	private ProgressDialog progressDialog;
	private Context context;
	private EditText contentTextView;
	private Bitmap pic;
	private long uid;
	private JuzhaiRefreshListView dialogContentListView;
	private DialogContent dialogContent;

	public SendMessageListener(Context context, EditText contentTextView,
			long uid, JuzhaiRefreshListView dialogContentListView) {
		super();
		this.context = context;
		this.uid = uid;
		this.dialogContentListView = dialogContentListView;
		this.contentTextView = contentTextView;
	}

	@Override
	public void onClick(View v) {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				ResponseEntity<DialogContentResult> responseEntity = null;
				try {
					Map<String, String> values = new HashMap<String, String>();
					values.put("content", contentTextView.getText().toString());
					values.put("uid", String.valueOf(uid));
					responseEntity = HttpUtils.uploadFile(uri, values,
							UserCache.getUserStatus(), "dialogImg", pic,
							DialogContentResult.class);
				} catch (Exception e) {
					if (BuildConfig.DEBUG) {
						Log.d("debug", "", e);
					}
					return context.getResources().getString(
							R.string.system_internet_erorr);
				}
				DialogContentResult result = responseEntity.getBody();
				if (!result.getSuccess()) {
					return result.getErrorInfo();
				}
				dialogContent = result.getResult();
				return null;
			}

			@Override
			protected void onPostExecute(String errorInfo) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				if (StringUtils.isNotEmpty(errorInfo)) {
					DialogUtils.showToastText(context, errorInfo);
				} else {
					dialogContentListView.getPageAdapter().pushData(
							dialogContent);
				}
			}

			@Override
			protected void onPreExecute() {
				if (StringUtils.isEmpty(contentTextView.getText().toString())) {
					DialogUtils
							.showToastText(context, R.string.content_is_null);
					return;
				}
				if (progressDialog != null) {
					progressDialog.show();
				} else {
					progressDialog = ProgressDialog.show(
							context,
							context.getResources().getString(R.string.sending),
							context.getResources().getString(
									R.string.please_wait), true, false);
				}
			}
		}.execute();
	}

	public void setPic(Bitmap pic) {
		this.pic = pic;
	}

}
