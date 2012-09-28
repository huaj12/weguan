/**
 * 
 */
package com.juzhai.android.dialog.activity;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.http.ResponseEntity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.R;
import com.juzhai.android.core.activity.UploadImageActivity;
import com.juzhai.android.core.model.Result.DialogContentListResult;
import com.juzhai.android.core.model.Result.DialogContentResult;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
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
import com.juzhai.android.dialog.model.LocationAndDialogContent;
import com.juzhai.android.dialog.service.IDialogContentService;
import com.juzhai.android.dialog.service.impl.DialogContentService;
import com.juzhai.android.passport.data.UserCache;

public class DailogContentListActivity extends NavigationActivity {
	private String sendMessageUri = "dialog/sendSms";
	private Dialog dialog;
	private final int PIC_REQUEST_CODE = 1;
	private EditText contentTextView = null;
	private ImageView picView;
	public Bitmap pic;
	private ListView dialogContentListView;
	private ProgressBar progressBar;
	private final Timer timer = new Timer();
	private TimerTask task;
	private DialogContentListResult result = null;
	private DialogContentListAdapter adapter = null;
	private BlockingQueue<LocationAndDialogContent> queue = new LinkedBlockingQueue<LocationAndDialogContent>(
			10);
	private boolean flag = true;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int location = msg.getData().getInt("location", -1);
			switch (msg.what) {
			case 1:
				if (progressBar.getVisibility() != View.GONE) {
					dialogContentListView.setVisibility(View.GONE);
				}
				break;
			case 2:
				progressBar.setVisibility(View.GONE);
				dialogContentListView.setVisibility(View.VISIBLE);
				if (result == null || !result.getSuccess()) {
					DialogUtils.showToastText(DailogContentListActivity.this,
							R.string.system_internet_erorr);
				} else {
					List<DialogContent> list = result.getResult().getList();
					Collections.reverse(list);
					adapter.pushDatas(list);
				}
				break;
			case 3:
				if (location >= 0) {
					updateMessageStatus(location, MessageStatus.SENDING);

				}
				break;
			case 4:
				if (location >= 0) {
					updateMessageStatus(location, MessageStatus.ERROR);
				}
				break;
			case 5:
				if (location >= 0) {
					DialogContent dialogContent = (DialogContent) msg.getData()
							.getSerializable("dialogContent");
					adapter.replaceDataNotNotifyData(location, dialogContent);
					updateMessageStatus(location, MessageStatus.SUCCESS);
				}
				break;
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
		dialogContentListView = (ListView) findViewById(R.id.dialog_content_list_view);
		Button uploadBtn = (Button) findViewById(R.id.upload_pic_btn);
		Button sendBtn = (Button) findViewById(R.id.send_message_btn);
		contentTextView = (EditText) findViewById(R.id.message_content_input);
		progressBar = (ProgressBar) findViewById(R.id.pro_bar);
		picView = (ImageView) findViewById(R.id.pic_view);

		uploadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DailogContentListActivity.this,
						UploadImageActivity.class);
				startActivityForResult(intent, PIC_REQUEST_CODE);
			}
		});
		adapter = new DialogContentListAdapter(dialog.getTargetUser(),
				DailogContentListActivity.this);
		dialogContentListView.setAdapter(adapter);

		task = new TimerTask() {

			@Override
			public void run() {
				LocationAndDialogContent ld = new LocationAndDialogContent();
				try {
					queue.add(ld);
				} catch (Exception e) {
					if (BuildConfig.DEBUG) {
						Log.d(getClass().getSimpleName(),
								"push  refresh dialogContent request is error",
								e);
					}
				}
			}
		};
		timer.schedule(task, 0, 10000);

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
				LocationAndDialogContent ld = new LocationAndDialogContent();
				DialogContent dialogContent = new DialogContent();
				dialogContent.setContent(contentTextView.getText().toString());
				dialogContent.setCreateTime(new Date().getTime());
				dialogContent.setSenderUid(UserCache.getUid());
				dialogContent.setReceiverUid(dialog.getTargetUser().getUid());
				dialogContent.setImage(pic);
				int location = adapter.addTempData(dialogContent);
				updateMessageStatus(location, MessageStatus.WAIT);
				ld.setDialogContent(dialogContent);
				ld.setLocation(location);
				try {
					queue.add(ld);
				} catch (Exception e) {
					// 队列不能加入元素了。
					DialogUtils.showToastText(DailogContentListActivity.this,
							R.string.frequency_exceeds_the_limit);
				}
				picView.setVisibility(View.GONE);
				picView.setImageBitmap(null);
				pic = null;
				contentTextView.setText(null);
			}
		});

		new Thread(new DailogContentThread()).start();
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
				LocationAndDialogContent ld = null;
				try {
					ld = queue.take();
				} catch (InterruptedException e) {
					if (BuildConfig.DEBUG) {
						Log.d(getClass().getSimpleName(),
								"send message queue take is error", e);
					}
					flag = false;
					break;
				}
				DialogContent dialogContent = ld.getDialogContent();
				int location = ld.getLocation();
				if (dialogContent != null) {
					Message msg = new Message();
					msg.what = 3;
					msg.getData().putInt("location", location);
					// 已加入等待列表
					handler.sendMessage(msg);
					ResponseEntity<DialogContentResult> responseEntity = null;
					try {
						Map<String, String> values = new HashMap<String, String>();
						values.put("content", dialogContent.getContent());
						values.put("uid",
								String.valueOf(dialogContent.getReceiverUid()));
						responseEntity = HttpUtils.uploadFile(sendMessageUri,
								values, UserCache.getUserStatus(), "dialogImg",
								pic, DialogContentResult.class);
					} catch (Exception e) {
						if (BuildConfig.DEBUG) {
							Log.d(getClass().getSimpleName(),
									"send message thread is error", e);
						}
					}

					if (responseEntity == null
							|| responseEntity.getBody() == null
							|| !responseEntity.getBody().getSuccess()) {
						// 发送失败
						msg = new Message();
						msg.what = 4;
						msg.getData().putInt("location", location);
						handler.sendMessage(msg);
					} else {
						// 发送成功
						msg = new Message();
						msg.what = 5;
						msg.getData().putInt("location", location);
						msg.getData().putSerializable("dialogContent",
								responseEntity.getBody().getResult());
						handler.sendMessage(msg);
					}
				} else {
					handler.sendEmptyMessage(1);
					long uid = dialog.getTargetUser().getUid();
					try {
						IDialogContentService dialogContentService = new DialogContentService();
						result = dialogContentService.list(uid, 1);
					} catch (DialogContentException e) {
						if (BuildConfig.DEBUG) {
							Log.d(getClass().getSimpleName(),
									"get list DialogContent is error", e);
						}
					}
					handler.sendEmptyMessage(2);
				}
			}

		}
	}

	private void updateMessageStatus(int location, MessageStatus status) {
		DialogContent dContent = (DialogContent) adapter.getItem(location);
		dContent.setStatus(status);
		adapter.replaceDataNotNotifyData(location, dContent);
		adapter.notifyDataSetChanged();
		dialogContentListView.setSelection(location);
	}

	@Override
	public void finish() {
		// 关闭前先关闭发送消息的线程
		flag = false;
		super.finish();
	}

}
