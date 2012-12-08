/**
 * 
 */
package com.juzhai.android.post.activity;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.util.StringUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.juzhai.android.core.utils.UIUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.core.widget.wheelview.WheelViewDialog;
import com.juzhai.android.core.widget.wheelview.WheelViewDialog.WheelViewDialogListener;
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
	private EditText contentText;
	private int itemIndex = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_send_post);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.send_post_title));
		Button finish = setRightFinishButton();
		finish.setText(R.string.send_post_finish_button);
		contentText = (EditText) findViewById(R.id.post_content);
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
		selectedCategory = categorys.get(0);
		post.setCategoryId(selectedCategory.getCategoryId());
		categoryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedCategory != null) {
					itemIndex = CommonData.getDataIndxex(
							(Long) selectedCategory.getIdentify(), categorys);
				}
				new WheelViewDialog<Category>(SendPostActivity.this,
						R.string.send_post_select_category_title, itemIndex,
						categorys, new WheelViewDialogListener() {
							@Override
							public void onClickPositive(int selectedIndex) {
								selectedCategory = categorys.get(selectedIndex);
								categoryBtn.setSelected(true);
								post.setCategoryId(selectedCategory
										.getCategoryId());
								openKeyboard(contentText);
							}

							@Override
							public void onClickNegative(int selectedIndex) {
								openKeyboard(contentText);
							}
						}).show();
			}
		});

		placeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText placeEditText = new EditText(
						SendPostActivity.this);
				placeEditText.setText(post.getPlace());
				new AlertDialog.Builder(SendPostActivity.this)
						.setTitle(R.string.send_post_place_title)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(placeEditText)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String place = placeEditText.getText()
												.toString();
										if (!StringUtils.hasText(place)) {
											placeBtn.setSelected(false);
										} else {
											// int placeLength = StringUtil
											// .chineseLength(place);
											// if (placeLength >
											// Validation.POST_PLACE_LENGTH_MAX)
											// {
											// DialogUtils
											// .showErrorDialog(
											// SendPostActivity.this,
											// R.string.send_post_place_length_invalid);
											// }
											placeBtn.setSelected(true);
										}
										post.setPlace(place);
										openKeyboard(contentText);
									}
								})
						.setNegativeButton(R.string.cancel,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										openKeyboard(contentText);
									}
								}).show();
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.FILL_PARENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER;
				params.setMargins(UIUtil.dip2px(SendPostActivity.this, 10),
						UIUtil.dip2px(SendPostActivity.this, 5),
						UIUtil.dip2px(SendPostActivity.this, 10),
						UIUtil.dip2px(SendPostActivity.this, 5));
				placeEditText.setLayoutParams(params);
				placeEditText.setSingleLine(true);
			}
		});

		timeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] dates = getDate();
				DatePickerDialog dlg = new DatePickerDialog(
						SendPostActivity.this, new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								post.setDate(year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth);
								timeBtn.setSelected(true);
								openKeyboard(contentText);
							}
						}, dates[0], dates[1], dates[2]);
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
								openKeyboard(contentText);
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
				uploadImageDialogAnim();
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
				if (StringUtils.hasText(contentText.getText().toString())) {
					new AlertDialog.Builder(SendPostActivity.this)
							.setTitle(R.string.note)
							.setMessage(R.string.clean_content)
							.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											contentText.setText(null);
										}
									}).setNegativeButton(R.string.cancel, null)
							.show();
				}
			}
		});

		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 没有头像不能发布拒宅
				// User user = UserCache.getUserInfo();
				// if (!user.isHasLogo())
				// {
				// DialogUtils.showErrorDialog(SendPostActivity.this,
				// R.string.send_post_user_logo_is_null);
				// DialogUtils.showToastText(SendPostActivity.this,
				// R.string.send_post_user_logo_is_null);
				// return;
				// }
				if (selectedCategory == null) {
					DialogUtils.showErrorDialog(SendPostActivity.this,
							R.string.send_post_category_is_null_error);
					// DialogUtils.showToastText(SendPostActivity.this,
					// R.string.send_post_category_is_null);
					return;
				}
				int placeLength = StringUtil.chineseLength(post.getPlace());
				if (placeLength > Validation.POST_PLACE_LENGTH_MAX) {
					DialogUtils.showErrorDialog(SendPostActivity.this,
							R.string.send_post_place_length_invalid_error);
					return;
				}
				String content = contentText.getText().toString();
				int contentLength = StringUtil.chineseLength(content);
				if (contentLength < Validation.POST_CONTENT_LENGTH_MIN
						|| contentLength > Validation.POST_CONTENT_LENGTH_MAX) {
					DialogUtils.showErrorDialog(SendPostActivity.this,
							R.string.send_post_content_length_invalid_error);
					// DialogUtils.showToastText(SendPostActivity.this,
					// R.string.send_post_content_too_little);
					return;
				}
				post.setContent(content);
				new ProgressTask(SendPostActivity.this, new TaskCallback() {

					@Override
					public void successCallback() {
						DialogUtils.showSuccessDialog(SendPostActivity.this,
								R.string.send_ok, 0);
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								popIntent();
							}
						}, 2000);
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
					DialogUtils.showErrorDialog(SendPostActivity.this,
							R.string.select_pic_error);
					// DialogUtils.showToastText(SendPostActivity.this,
					// R.string.select_pic_error);
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
				R.string.send_post_content_tip, (restLength / 2)));
	}

	private int[] getDate() {
		int[] dates = new int[3];
		if (post.getDate() == null) {
			Calendar cal = Calendar.getInstance();
			dates[0] = cal.get(Calendar.YEAR);
			dates[1] = cal.get(Calendar.MONTH);
			dates[2] = cal.get(Calendar.DAY_OF_MONTH);
		} else {
			String[] str = post.getDate().split("-");
			dates[0] = Integer.parseInt(str[0]);
			dates[1] = Integer.parseInt(str[1]) - 1;
			dates[2] = Integer.parseInt(str[2]);
		}
		return dates;
	}

	@Override
	protected void onResume() {
		contentText.setFocusable(true);
		openKeyboard(contentText);
		super.onResume();
	}
}
