/**
 * 
 */
package com.juzhai.android.dialog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.UploadImageActivity;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.adapter.DialogContentListAdapter;
import com.juzhai.android.dialog.listener.SendMessageListener;
import com.juzhai.android.dialog.model.Dialog;
import com.juzhai.android.dialog.task.DialogContentListGetDataTask;

public class DailogContentListActivity extends NavigationActivity {
	private Dialog dialog;
	private final int PIC_REQUEST_CODE = 1;
	private EditText contentTextView = null;
	private ImageView picView;
	public Bitmap pic;
	private JuzhaiRefreshListView dialogContentListView;
	private SendMessageListener sendMessageListener;

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
		dialogContentListView = (JuzhaiRefreshListView) findViewById(R.id.dialog_content_list_view);
		Button uploadBtn = (Button) findViewById(R.id.upload_pic_btn);
		Button sendBtn = (Button) findViewById(R.id.send_message_btn);
		contentTextView = (EditText) findViewById(R.id.message_content_input);
		picView = (ImageView) findViewById(R.id.pic_view);
		dialogContentListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullDownToRefresh(refreshView);
						new DialogContentListGetDataTask(dialogContentListView)
								.execute(dialog.getTargetUser().getUid(), 1);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullUpToRefresh(refreshView);
						new DialogContentListGetDataTask(dialogContentListView)
								.execute(
										dialog.getTargetUser().getUid(),
										dialogContentListView.getPageAdapter()
												.getPager().getCurrentPage() + 1);
					}
				});
		dialogContentListView.setAdapter(new DialogContentListAdapter(dialog
				.getTargetUser(), DailogContentListActivity.this));
		uploadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DailogContentListActivity.this,
						UploadImageActivity.class);
				startActivityForResult(intent, PIC_REQUEST_CODE);
			}
		});
		sendMessageListener = new SendMessageListener(
				DailogContentListActivity.this, contentTextView, dialog
						.getTargetUser().getUid(), dialogContentListView);
		sendBtn.setOnClickListener(sendMessageListener);
		dialogContentListView.manualRefresh();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PIC_REQUEST_CODE
				&& UploadImageActivity.PIC_RESULT_CODE == resultCode) {
			pic = data.getParcelableExtra("pic");
			sendMessageListener.setPic(pic);
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

}
