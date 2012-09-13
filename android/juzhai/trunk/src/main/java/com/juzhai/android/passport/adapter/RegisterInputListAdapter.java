package com.juzhai.android.passport.adapter;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.juzhai.android.R;

public class RegisterInputListAdapter extends BaseAdapter {
	private Context mContext = null;
	private EditText nicknameText;
	private EditText accountText;
	private EditText passwordText;
	private EditText rep_passwordText;
	private String name = null;

	public RegisterInputListAdapter(Context mContext, String name) {
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
				nicknameText = (EditText) convertView
						.findViewById(R.id.editInput);
				nicknameText.setHint(R.string.reg_input_tip_nickname);
			} else if (position == 1) {
				accountText = (EditText) convertView
						.findViewById(R.id.editInput);
				accountText.setHint(R.string.reg_input_tip_account);
			} else if (position == 2) {
				passwordText = (EditText) convertView
						.findViewById(R.id.editInput);
				passwordText.setHint(R.string.reg_input_tip_pwd);
				passwordText
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			} else {
				rep_passwordText = (EditText) convertView
						.findViewById(R.id.editInput);
				rep_passwordText.setHint(R.string.reg_input_tip_rep_pwd);
				rep_passwordText
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			}
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return 4;
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
