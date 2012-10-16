/**
 * 
 */
package com.juzhai.android.core.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.ImageUtils;

/**
 * @author kooks
 * 
 */
public class UploadImageActivity extends Activity {
	private String filename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_upload_image);
		boolean isCancelBtn = getIntent().getBooleanExtra("isCancelBtn", false);
		Button alubmBtn = (Button) findViewById(R.id.upload_album);
		Button cameraBtn = (Button) findViewById(R.id.upload_camera);
		Button cancelBtn = (Button) findViewById(R.id.delete_upload);
		alubmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent,
						ActivityCode.RequestCode.ALBUM_REQUEST_CODE);
			}
		});
		cameraBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				filename = ImageUtils.getFileName();
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), filename)));
				startActivityForResult(intent,
						ActivityCode.RequestCode.CAMERA_REQUEST_CODE);
			}
		});
		if (isCancelBtn) {
			cancelBtn.setVisibility(View.VISIBLE);
			cancelBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setResult(ActivityCode.ResultCode.PIC_RESULT_CODE,
							getIntent());
					finish();
				}
			});
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case ActivityCode.RequestCode.ALBUM_REQUEST_CODE:
			if (data != null && data.getData() != null) {
				startPhotoZoom(data.getData());
			} else {
				finish();
			}
			break;
		// 如果是调用相机拍照时
		case ActivityCode.RequestCode.CAMERA_REQUEST_CODE:
			if (filename != null) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ File.separator + filename);
				startPhotoZoom(Uri.fromFile(temp));
			} else {
				finish();
			}
			break;
		// 取得裁剪后的图片
		case ActivityCode.RequestCode.CUT_REQUEST_CODE:
			if (data != null) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap pic = extras.getParcelable("data");
					data.putExtra("pic", pic);
					setResult(ActivityCode.ResultCode.PIC_RESULT_CODE, data);
				}
			}
			finish();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent,
				ActivityCode.RequestCode.CUT_REQUEST_CODE);
	}

}
