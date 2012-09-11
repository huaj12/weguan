package com.juzhai.android.lab.asyn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CatTask extends AsyncTask<String, Integer, List<Category>> {
	private ProgressDialog progressDialog;
	private Context context;
	private ListView cats;

	public CatTask(Context context, ListView cats) {
		this.context = context;
		this.cats = cats;
	}

	@Override
	protected void onPostExecute(List<Category> result) {
		// 关闭等待框
		progressDialog.dismiss();
		List<String> catNames = new ArrayList<String>();
		for (Category cat : result) {
			catNames.add(cat.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, catNames);
		cats.setAdapter(adapter);
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, "Loading...",
				"Please wait...", true, false);
	}

	@Override
	protected List<Category> doInBackground(String... params) {
		String url = params[0];
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		Results results = restTemplate.getForObject(url, Results.class);
		return results.getResult();
	}

}
