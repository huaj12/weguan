package com.easylife.weather.core.widget.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.easylife.weather.R;

public class SwitchButton extends View implements OnClickListener {
	private boolean checked = false;
	private OnChangedListener onChangedListener;
	private Context context;

	public SwitchButton(Context context) {
		super(context);
		this.context = context;
		init();
		setOnClickListener(this);

	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setOnClickListener(this);
		init();
	}

	private void init() {
		if (checked) {
			this.setBackgroundResource(R.drawable.turnon);
		} else {
			this.setBackgroundResource(R.drawable.turnoff);
		}
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		init();
	}

	public interface OnChangedListener {
		abstract void OnChanged(boolean checkState);
	}

	public void setOnChangedListener(OnChangedListener onChangedListener) {
		this.onChangedListener = onChangedListener;
	}

	@Override
	public void onClick(View v) {
		checked = !checked;
		init();
		onChangedListener.OnChanged(checked);
	}

}
