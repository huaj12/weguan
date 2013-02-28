package com.juzhai.dpsdk.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
	 * @param offsetType
	 *            偏移类型，0:未偏移，1:高德坐标系偏移，2:四维图新坐标系偏移，如不传入默认为0
	 * @return
	 * @throws DianPingException
	 */
	public List<Business> findBusiness(String city, String region,
			String category, String keyword, Integer radius, Integer hasCoupon,
			Integer hasDeal, int sort, int limit, Double latitude,
			Double longitude, int offsetType) throws DianPingException {
		List<PostParameter> parameter = new ArrayList<PostParameter>();
		if (StringUtils.isNotEmpty(city)) {
			parameter.add(new PostParameter("city", city));
		}
		if (StringUtils.isNotEmpty(region)) {
			parameter.add(new PostParameter("region", region));
		}
		if (StringUtils.isNotEmpty(category)) {
			parameter.add(new PostParameter("category", category));
		}
		if (StringUtils.isNotEmpty(keyword)) {
			parameter.add(new PostParameter("keyword", keyword));
		}
		if (hasCoupon != null) {
			parameter.add(new PostParameter("has_coupon", hasCoupon));
		}
		if (hasDeal != null) {
			parameter.add(new PostParameter("has_deal", hasDeal));
		}
		parameter.add(new PostParameter("sort", sort));
		parameter.add(new PostParameter("limit", limit));
		if (latitude != null && longitude != null) {
			parameter.add(new PostParameter("latitude", latitude));
			parameter.add(new PostParameter("longitude", longitude));
			// 传入坐标才需要搜索半径
			if (radius != null) {
				parameter.add(new PostParameter("radius", radius));
			}
			parameter.add(new PostParameter("offset_type", offsetType));
		}
		return Business.constructBusiness(client.get(getBaseURL()
				+ "business/find_businesses",
				parameter.toArray(new PostParameter[parameter.size()])));
	}
}
