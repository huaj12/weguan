package com.juzhai.mobile.common.web.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;

public class ListJsonResult extends AjaxResult {

	public void setResult(PagerManager pager, List<? extends Object> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pager", pager);
		super.setResult(map);
	}
}
