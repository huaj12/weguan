/**
 * 
 */
package com.juzhai.android.post.activity;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.common.model.Category;
import com.juzhai.android.common.service.CommonData;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.activity.UploadImageActivity;
import com.juzhai.android.core.task.ProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.ImageUtils;
import com.juzhai.android.core.utils.StringUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.core.widget.wheelview.WheelViewDialog;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.model.Post;
import com.juzhai.android.post.service.IUserPostService;
import com.juzhai.android.post.service.impl.UserPostService;

public class SendPostActivity extends NavigationActivity {
	private Category selectedCategory;
	private Post post = new Post();
	private Bitmap postImage;
	private int restLength = Validation.POST_CONTENT_LENGTH_MAX;
	private ImageView imageView;
	private TextView countTip;
	private Button imageBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_send_post);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.send_post_title));
		Button finish = setRightFinishButton();
		final EditText contentText = (EditText) findViewById(R.id.post_content);
		final Button categoryBtn = (Button) findViewById(R.id.post_category_btn);
		final Button placeBtn = (Button) findViewById(R.id.post_place_btn);
		final Button timeBtn = (Button) findViewById(R.id.post_time_btn);
		imageBtn = (Button) findViewById(R.id.post_image_btn);
		imageView = (ImageView) findViewById(R.id.post_image);
		countTip = (TextView) findViewById(R.id.post_content_count_tip);
		Button cleanBtn = (Button) findViewById(R.id.post_clean_btn);
		setCountTip();
		final List<Category> categorys = CommonData
				.getCategorys(SendPostActivity.this);

		categoryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WheelViewDialog.showWheelView(R.string.post_category,
						selectedCategory, categorys, SendPostActivity.this,
						new WheelViewDialog.WheelViewCallBack() {
							@Override
							public void callback(int location) {
								selectedCategory = categorys.get(location);
								categoryBtn.setSelected(true);
								post.setCategoryId(selectedCategory
										.getCategoryId());
							}
						});
			}
		});

		placeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText placeEditText = new EditText(
						SendPostActivity.this);
				placeEditText.setText(post.getPlace());
				new AlertDialog.Builder(SendPostActivity.this)
						.setTitle(R.string.post_place)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(placeEditText)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String place = placeEditText.getText()
												.toString();
										if (StringUtils.isEmpty(place)) {
											placeBtn.setSelected(false);
										} else {
											int placeLength = StringUtil
													.chineseLength(place);
											if (placeLength > Validation.POST_PLACE_LENGTH_MAX) {
												DialogUtils
														.showToastText(
																SendPostActivity.this,
																R.string.send_post_place_length_invalid);
												return;
											}
											placeBtn.setSelected(true);
										}
										post.setPlace(place);
									}
								}).setNegativeButton(R.string.cancel, null)
						.show();

			}
		});

		timeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();

				int year = getDate(0);
				int month = getDate(1) - 1;
				int day = getDate(2);
				if (year <= 0) {
					year = cal.get(Calendar.YEAR);
					month = cal.get(Calendar.MONTH);
					day = cal.get(Calendar.DAY_OF_MONTH);
				}

				DatePickerDialog dlg = new DatePickerDialog(
						SendPostActivity.this, new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								post.setDate(year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth);
								timeBtn.setSelected(true);
							}
						}, year, month, day);
				dlg.setButton(
						AlertDialog.BUTTON_NEGATIVE,
						SendPostActivity.this.getResources().getString(
								R.string.clean),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								post.setDate(null);
								timeBtn.setSelected(false);
							}
						});
				dlg.show();
			}
		});

		imageBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SendPostActivity.this,
						UploadImageActivity.class);
				if (postImage != null) {
					intent.putExtra("isDeleteBtn", true);
				}
				startActivityForResult(intent,
						ActivityCode.RequestCode.PIC_REQUEST_CODE);
			}
		});

		contentText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				restLength = Validation.POST_CONTENT_LENGTH_MAX
						- StringUtil.chineseLength(contentText.getText()
								.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				setCountTip();
			}

			@Override
			public void afterTextChanged(Editable s) {
				setCountTip();
			}
		});

		cleanBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(SendPostActivity.this)
						.setTitle(R.string.note)
						.setMessage(R.string.clean_content)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										contentText.setText(null);
										// TODO (review) 调用了setText方法，不会触发onTextChanged事件？
										restLength = Validation.POST_CONTENT_LENGTH_MAX;
									}
								}).setNegativeButton(R.string.cancel, null)
						.show();
			}
		});

		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedCategory == null) {
					DialogUtils.showToastText(SendPostActivity.this,
							R.string.send_post_category_is_null);
					return;
				}
				String content = contentText.getText().toString();
				int contentLength = StringUtil.chineseLength(content);
				if (contentLength < Validation.POST_CONTENT_LENGTH_MIN) {
					DialogUtils.showToastText(SendPostActivity.this,
							R.string.send_post_content_too_little);
					return;
				}
				if (contentLength > Validation.POST_CONTENT_LENGTH_MAX) {
					DialogUtils.showToastText(SendPostActivity.this,
							R.string.send_post_content_too_more);
					return;
				}
				// 没有头像不能发布拒宅
				User user = UserCache.getUserInfo();
				if (!user.isHasLogo() || StringUtils.isEmpty(user.getLogo())) {
					DialogUtils.showToastText(SendPostActivity.this,
							R.string.send_post_user_logo_is_null);
					return;
				}
				post.setContent(content);
				new ProgressTask(SendPostActivity.this, new TaskCallback() {

					@Override
					public void successCallback() {
						DialogUtils.showToastText(SendPostActivity.this,
								R.string.send_ok);
						SendPostActivity.this.finish();
					}

					@Override
					public String doInBackground() {
						IUserPostService userPostService = new UserPostService();
						try {
							userPostService.sendPost(SendPostActivity.this,
									post, postImage);
						} catch (PostException e) {
							return e.getMessage();
						}
						return null;
					}
				}, false).execute();

			}
		});

		// TODO (review) 考虑封装弹出键盘（方案1.放入BaseActivity，方案2...，方案3....）
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(contentText,
						InputMethodManager.RESULT_UNCHANGED_SHOWN);
			}
		}, 300);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.RequestCode.PIC_REQUEST_CODE) {
			if (ActivityCode.ResultCode.PIC_RESULT_CODE == resultCode) {

				Bitmap image = data.getParcelableExtra("pic");
				if (image != null) {
					postImage = image;
					imageView.setImageBitmap(ImageUtils.zoomBitmap(image, 20,
							20, SendPostActivity.this));
					imageBtn.setSelected(true);
					imageView.setVisibility(View.VISIBLE);
				} else {
					DialogUtils.showToastText(SendPostActivity.this,
							R.string.select_pic_error);
				}
			} else if (ActivityCode.ResultCode.PIC_DELETE_RESULT_CODE == resultCode) {
				postImage = null;
				imageView.setImageBitmap(null);
				imageBtn.setSelected(false);
				imageView.setVisibility(View.GONE);
			}
		}
	}

	private void setCountTip() {
		countTip.setText(getResources().getString(
				R.string.post_content_tip_begin)
				+ (restLength / 2)
				+ getResources().getString(R.string.post_content_tip_end));
	}

	private int getDate(int index) {
		//TODO (review) 为什么不判断post.getDate()是否为null
		//TODO (review) 为什么要调用多次？
		//TODO (review) 当date没有值，获取当前时间，为什么不封装在一起
		try {
			return Integer.parseInt(post.getDate().split("-")[index]);
		} catch (Exception e) {
			return 0;
		}
	}
}
