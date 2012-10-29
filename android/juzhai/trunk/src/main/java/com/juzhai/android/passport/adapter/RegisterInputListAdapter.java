package com.juzhai.android.passport.adapter;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.juzhai.android.R;

public class RegisterInputListAdapter extends BaseAdapter {

	private LayoutInflater inflater = null;

	public RegisterInputListAdapter(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_input_list, null);
			EditText editText = (EditText) convertView
					.findViewById(R.id.edit_input);
			editText.setTag(position);
			if (position == 0) {
				editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				editText.setHint(R.string.reg_input_tip_account);
			} else if (position == 1) {
				editText.setHint(R.string.reg_input_tip_nickname);
			} else if (position == 2) {
				editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				editText.setHint(R.string.reg_input_tip_pwd);
				editText.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
			} else {
				editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				editText.setHint(R.string.reg_input_tip_rep_pwd);
				editText.setTransformationMethod(PasswordTransformationMethod
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
