/**
 * 
 */
package com.juzhai.android.lab.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juzhai.android.R;

/**
 * @author kooks
 * 
 */
public class SharedPreferencesActivity extends Activity {
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	EditText show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);
		// 该数据只能被本应用程序读取
		sharedPreferences = getSharedPreferences("juzhai-android", MODE_PRIVATE);
		editor = sharedPreferences.edit();
		Button btRead = (Button) findViewById(R.id.read);
		Button btWrite = (Button) findViewById(R.id.write);
		show = (EditText) findViewById(R.id.show);
		btRead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sharedPreferences.contains("time")) {
					String time = sharedPreferences.getString("time", null);
					show.setText(time);
				} else {
					Toast.makeText(SharedPreferencesActivity.this, "没有存储信息",
							5000).show();
				}
			}
		});
		btWrite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
				editor.putString("time", sdf.format(new Date()));
				editor.commit();
			}
		});
	}
}
