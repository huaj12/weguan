package com.easylife.weather.wxapi;

import com.easylife.weather.core.activity.BaseActivity;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	@Override
	protected void onResume() {
		super.onResume();
		this.finish();
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
	}

}
