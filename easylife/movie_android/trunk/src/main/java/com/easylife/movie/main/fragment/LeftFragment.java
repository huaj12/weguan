package com.easylife.movie.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.easylife.movie.R;
import com.easylife.movie.common.service.CommonData;
import com.easylife.movie.main.activity.MainActivity;
import com.easylife.movie.video.adapter.CategorytListAdapter;

public class LeftFragment extends Fragment {
	private Context context;
	private ListView categoryListView;

	public LeftFragment() {
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_left, null);
		categoryListView = (ListView) view
				.findViewById(R.id.category_list_view);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (context == null) {
			return;
		}
		categoryListView.setAdapter(new CategorytListAdapter(context));
		categoryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int location, long arg3) {
				Intent intent = new Intent(context, MainActivity.class);
				long categoryId = CommonData.getCategorys(context)
						.get(location).getCategoryId();
				intent.putExtra("categoryId", categoryId);
				context.startActivity(intent);
			}
		});

	}
}
