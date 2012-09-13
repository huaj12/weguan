package com.juzhai.android.passport.adapter;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.juzhai.android.R;

public class LoginInputListAdapter extends BaseAdapter {
	private Context mContext = null;
	private EditText accountText;
	private EditText passwordText;
	private String name = null;

	public LoginInputListAdapter(Context mContext, String name) {
		this.mContext = mContext;
		this.name = name;
	}

	LayoutInflater inflater = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			if (inflater == null) {
				inflater = (LayoutInflater) mContext.getSystemService(name);
			}
			convertView = inflater.inflate(R.layout.input_list_item, null);

			if (position == 0) {
				accountText = (EditText) convertView
						.findViewById(R.id.editInput);
				accountText.setTag(R.id.editInput);
				accountText.setHint(R.string.login_input_tip_account);
			} else if (position == 1) {
				passwordText = (EditText) convertView
						.findViewById(R.id.editInput);
				passwordText.setHint(R.string.login_input_tip_pwd);
				passwordText
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			}
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}
}
