/**
 * 
 */
package com.juzhai.test.asyn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.juzhai.android.R;

/**
 * @author kooks
 * 
 */
public class AsyncTaskAcitvity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.astnctask);
		CatTask task = new CatTask(AsyncTaskAcitvity.this,
				(ListView) findViewById(R.id.catList));
		task.execute("http://www.51juzhai.com/m/base/categoryList");
	}
}
