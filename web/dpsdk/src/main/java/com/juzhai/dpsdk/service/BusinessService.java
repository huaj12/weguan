package com.juzhai.dpsdk.service;

import java.util.List;

import com.juzhai.dpsdk.DianPing;
import com.juzhai.dpsdk.exception.DianPingException;
import com.juzhai.dpsdk.model.Business;
import com.juzhai.dpsdk.model.PostParameter;

public class BusinessService extends DianPing {
	private static final long serialVersionUID = 6661073095033402454L;

	public BusinessService(String appkey, String appSecret) {
		super(appkey, appSecret);
	}

	/**
	 * 搜索商户
	 * 
	 * @param city
	 * @param region
	 * @param category
	 * @param keyword
	 * @param radius
	 *            搜索半径，单位为米，最小值1，最大值5000，如不传入默认为1000
	 * @param hasCoupon
	 *            根据是否有优惠券来筛选返回的商户，1:有，0:没有
	 * @param hasDeal
	 *            根据是否有团购来筛选返回的商户，1:有，0:没有
	 * @param sort
	 *            结果排序，1:默认，2:星级高优先，3:产品评价高优先，4:环境评价高优先，5:服务评价高优先，6:点评数量多优先，7:
	 *            离传入经纬度坐标距离近优先
	 * @param limit
	 *            返回的商户结果条目数上限，最小值1，最大值20，如不传入默认为20
	 * @param latitude
	 *            纬度坐标，须与经度坐标同时传入，与城市名称二者必选其一传入
	 * @param longitude
	 *            经度坐标，须与纬度坐标同时传入，与城市名称二者必选其一传入
	 * @return
	 * @throws DianPingException
	 */
	public List<Business> findBusiness(String city, String region,
			String category, String keyword, int radius, int hasCoupon,
			int hasDeal, int sort, int limit, double latitude, double longitude)
			throws DianPingException {
		return Business.constructBusiness(client.get(getBaseURL()
				+ "business/find_businesses", new PostParameter[] {
				new PostParameter("city", city),
				new PostParameter("region", region),
				new PostParameter("category", category),
				new PostParameter("keyword", keyword),
				new PostParameter("has_coupon", hasCoupon),
				new PostParameter("has_deal", hasDeal),
				new PostParameter("sort", sort),
				new PostParameter("limit", limit),
				new PostParameter("latitude", latitude),
				new PostParameter("longitude", longitude) }));
	}

}
