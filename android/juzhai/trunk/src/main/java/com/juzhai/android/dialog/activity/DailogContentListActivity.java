/**
 * 
 */
package com.juzhai.android.dialog.activity;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.activity.UploadImageActivity;
import com.juzhai.android.core.model.PageList;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.adapter.DialogContentListAdapter;
import com.juzhai.android.dialog.bean.MessageStatus;
import com.juzhai.android.dialog.exception.DialogContentException;
import com.juzhai.android.dialog.model.Dialog;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.dialog.service.impl.DialogContentService;
import com.juzhai.android.passport.data.UserCache;

public class DailogContentListActivity extends NavigationActivity {
	private final int PIC_REQUEST_CODE = 1;
	private final Timer timer = new Timer();
	private boolean flag = true;
	private Dialog dialog;
	private ImageView picView;
	private Bitmap pic;
	private DialogContentListAdapter adapter;
	private ListView dialogContentListView;
	private BlockingQueue<DialogContent> queue = new LinkedBlockingQueue<DialogContent>(
			10);
	private DialogContentService dialogContentService = new DialogContentService();
	private ProgressDialog progressDialog;
	private MyHandler myHandler = new MyHandler(this);

	static class MyHandler extends Handler {

		WeakReference<DailogContentListActivity> mActivity;

		MyHandler(DailogContentListActivity mActivity) {
			super();
			this.mActivity = new WeakReference<DailogContentListActivity>(
					mActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			// 刷新列表
			mActivity.get().adapter.notifyDataSetChanged();
			mActivity.get().dialogContentListView
					.setSelection(mActivity.get().adapter.getCount() - 1);

			// 发送失败
			if (msg.what == 0) {
				DialogUtils.showToastText(mActivity.get(), msg.getData()
						.getString("errorInfo"));
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = (Dialog) getIntent().getSerializableExtra("dialog");
		if (dialog == null) {
			popIntent();
		}
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.dialog_content_title_begin)
						+ dialog.getTargetUser().getNickname()
						+ getResources().getString(
								R.string.dialog_content_title_end));
		setNavContentView(R.layout.page_dialog_content_list);

		// 获取view
		dialogContentListView = (ListView) findViewById(R.id.dialog_content_list_view);
		adapter = new DialogContentListAdapter(dialog.getTargetUser(),
				DailogContentListActivity.this);
		dialogContentListView.setAdapter(adapter);
		Button uploadBtn = (Button) findViewById(R.id.upload_pic_btn);
		Button sendBtn = (Button) findViewById(R.id.send_message_btn);
		final EditText contentTextView = (EditText) findViewById(R.id.message_content_input);
		picView = (ImageView) findViewById(R.id.pic_view);

		// 绑定事件
		uploadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DailogContentListActivity.this,
						UploadImageActivity.class);
				startActivityForResult(intent, PIC_REQUEST_CODE);
			}
		});

		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int contentLengt = StringUtil.chineseLength(contentTextView
						.getText().toString());
				if (contentLengt < Validation.SEND_MESSAGE_MIN_LENGTH
						|| contentLengt > Validation.SEND_MESSAGE_MAX_LENGTH) {
					DialogUtils.showToastText(DailogContentListActivity.this,
							R.string.send_message_length_invalid);
					return;
				}

				// 组装对象
				DialogContent dialogContent = new DialogContent();
				dialogContent.setContent(contentTextView.getText().toString());
				dialogContent.setCreateTime(new Date().getTime());
				dialogContent.setSenderUid(UserCache.getUid());
				dialogContent.setReceiverUid(dialog.getTargetUser().getUid());
				dialogContent.setImage(pic);
				dialogContent.setStatus(MessageStatus.WAIT);
				if (!queue.offer(dialogContent)) {
					// 队列不能加入元素了。
					DialogUtils.showToastText(DailogContentListActivity.this,
							R.string.frequency_exceeds_the_limit);
					return;
				}
				adapter.pushData(dialogContent);

				// 清空输入信息
				picView.setVisibility(View.GONE);
				picView.setImageBitmap(null);
				pic = null;
				contentTextView.setText(null);
			}
		});

		new Thread(new DailogContentThread()).start();

		// 锁屏获取列表
		new AsyncTask<Void, Integer, List<DialogContent>>() {
			@Override
			protected List<DialogContent> doInBackground(Void... params) {
				try {
					PageList<DialogContent> pageList = dialogContentService
							.list(dialog.getTargetUser().getUid(), 1);
					if (null == pageList) {
						return null;
					} else {
						return pageList.getList();
					}
				} catch (DialogContentException e) {
					return null;
				}
			}

			protected void onPreExecute() {
				if (progressDialog != null) {
					progressDialog.show();
				} else {
					progressDialog = ProgressDialog.show(
							DailogContentListActivity.this, null,
							getResources().getString(R.string.please_wait),
							true, false);
				}
			};

			protected void onPostExecute(List<DialogContent> result) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				if (null != result) {
					Collections.reverse(result);
					adapter.pushDatas(result);
					dialogContentListView.setSelection(adapter.getCount() - 1);
				} else {
					// 报错
					DialogUtils.showToastText(DailogContentListActivity.this,
							R.string.system_internet_erorr);
				}
				// 定时任务
				timer.schedule(new MyTimeTask(), 10000);
			};
		}.execute();
	}

	private class MyTimeTask extends TimerTask {
		@Override
		public void run() {
			if (!queue.offer(new DialogContent())) {
				if (BuildConfig.DEBUG) {
					Log.d(getClass().getSimpleName(),
							"push refresh dialogContent request is error. Queue is full");
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PIC_REQUEST_CODE
				&& UploadImageActivity.PIC_RESULT_CODE == resultCode) {
			pic = data.getParcelableExtra("pic");
			if (picView == null) {
				DialogUtils.showToastText(DailogContentListActivity.this,
						R.string.select_pic_error);
				picView.setVisibility(View.GONE);
				return;
			}
			picView.setVisibility(View.VISIBLE);
			picView.setImageBitmap(ImageUtils.zoomBitmap(pic,
					UIUtil.dip2px(DailogContentListActivity.this, 30),
					UIUtil.dip2px(DailogContentListActivity.this, 30)));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class DailogContentThread implements Runnable {
		public void run() {
			while (flag) {
				DialogContent dialogContent = null;
				try {
					dialogContent = queue.take();
				} catch (InterruptedException e) {
					if (BuildConfig.DEBUG) {
						Log.d(getClass().getSimpleName(),
								"send message queue take is error", e);
					}
					continue;
				}
				if (dialogContent.getContent() != null) {
					dialogContent.setStatus(MessageStatus.SENDING);
					updateUI(true, null);
					try {
						DialogContent sentDialogContent = dialogContentService
								.sendSms(DailogContentListActivity.this,
										dialogContent.getReceiverUid(),
										dialogContent.getContent(),
										dialogContent.getImage());
						dialogContent.setDialogContentId(sentDialogContent
								.getDialogContentId());
						dialogContent.setImgUrl(sentDialogContent.getImgUrl());
						dialogContent.setStatus(MessageStatus.SUCCESS);
						adapter.refreshIdentify();
						updateUI(true, null);
					} catch (DialogContentException e) {
						// 发送失败
						dialogContent.setStatus(MessageStatus.ERROR);
						// 发送更新通知
						updateUI(false, e.getMessageId() > 0 ? getResources()
								.getString(e.getMessageId()) : e.getMessage());
					}
				} else {
					PageList<DialogContent> pageList = dialogContentService
							.refreshList(dialog.getTargetUser().getUid());
					if (pageList != null
							&& !CollectionUtils.isEmpty(pageList.getList())) {
						List<DialogContent> list = pageList.getList();
						Collections.reverse(list);
						adapter.pushDatasWithoutNotify(list);
						// 发送更新通知
						updateUI(true, null);
						timer.schedule(new MyTimeTask(), 10000);
					}
				}
			}

		}
	}

	private void updateUI(boolean isSuccess, String errorInfo) {
		Message msg = new Message();
		msg.what = isSuccess ? 1 : 0;
		if (StringUtils.hasText(errorInfo)) {
			msg.getData().putString("errorInfo", errorInfo);
		}
		myHandler.sendMessage(msg);
	}

	@Override
	public void finish() {
		// 关闭前先关闭发送消息的线程
		flag = false;
		timer.cancel();
		super.finish();
	}
}
