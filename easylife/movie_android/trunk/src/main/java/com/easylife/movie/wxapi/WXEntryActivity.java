package com.easylife.movie.wxapi;

import com.easylife.movie.core.activity.BaseActivity;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	@Override
	public void onResume() {
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
