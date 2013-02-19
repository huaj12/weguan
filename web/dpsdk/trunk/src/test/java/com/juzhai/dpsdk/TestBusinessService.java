package com.juzhai.dpsdk;

import java.util.List;

import com.juzhai.dpsdk.exception.DianPingException;
import com.juzhai.dpsdk.model.Business;
import com.juzhai.dpsdk.service.BusinessService;

public class TestBusinessService {

	/**
	 * @param args
	 * @throws DianPingException
	 */
	public static void main(String[] args) throws DianPingException {
		List<Business> list = new BusinessService("78173911",
				"7148a129e6bc4cc1a0cf4d801e534507").findBusiness("上海", "徐汇区",
				"美食", "", 5000, 1, 0, 2, 20, 0, 0);
	}

}
