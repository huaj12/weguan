package com.juzhai.dpsdk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.juzhai.dpsdk.exception.DianPingException;
import com.juzhai.dpsdk.http.Response;
import com.juzhai.dpsdk.json.model.JSONArray;
import com.juzhai.dpsdk.json.model.JSONException;
import com.juzhai.dpsdk.json.model.JSONObject;

public class Business extends DianPingResponse implements java.io.Serializable {

	private static final long serialVersionUID = -2779977894448965597L;
	private long id; // 商户ID
	private String name;// 商户名
	private String branchName;// 分店名
	private String address;// 地址
	private String telephone;// 带区号的电话
	private String city;// 城市
	private String[] regions;// 所在区域信息列表，如[徐汇区，徐家汇]
	private String[] categories;// 所属分类信息列表，如[宁波菜，婚宴酒店]
	private float avgRating;// 星级评分，5.0代表五星，4.5代表四星半，依此类推
	private String ratingImgUrl;// 星级图片链接
	private String ratingSImgUrl;// 星级图片（小）链接
	private int productGrade;// 产品/食品口味评价，1:一般，2:尚可，3:好，4:很好，5:非常好
	private int decorationGrade;// 环境评价，1:一般，2:尚可，3:好，4:很好，5:非常好
	private int serviceGrade;// 服务评价，1:一般，2:尚可，3:好，4:很好，5:非常好
	private int reviewCount;// 点评数量
	private long distance;// 商户与参数坐标的距离，单位为米，如不传入经纬度坐标，结果为-1
	private String businessUrl;// 商户页面链接;
	private String photoUrl;// 照片链接
	private boolean hasCoupon;// 是否有优惠券
	private long couponId;// 优惠券ID
	private String couponTitle;// 优惠券描述
	private String couponUrl;// 优惠券页面链接
	private boolean hasDeal;// 是否有团购
	private String dealId;// 团购单ID
	private String dealTitle;// 团购描述
	private String dealUrl;// 团购单页面链接

	public Business(JSONObject jsonObject) throws DianPingException {
		super();
		init(jsonObject);
	}

	private void init(JSONObject json) throws DianPingException {
		if (json != null) {
			try {
				id = json.getLong("business_id");
				name = json.getString("name");
				branchName = json.getString("branch_name");
				address = json.getString("address");
				telephone = json.getString("telephone");
				city = json.getString("city");
				regions = json.getJSONArray("regions").toString().split(",");
				categories = json.getJSONArray("categories").toString()
						.split(",");
				avgRating = json.getFloat("avg_rating");
				ratingImgUrl = json.getString("rating_img_url");
				ratingSImgUrl = json.getString("rating_s_img_url");
				productGrade = json.getInt("product_grade");
				decorationGrade = json.getInt("decoration_grade");
				serviceGrade = json.getInt("service_grade");
				reviewCount = json.getInt("review_count");
				distance = json.getLong("distance");
				businessUrl = json.getString("business_url");
				photoUrl = json.getString("photo_url");
				hasCoupon = json.getInt("has_coupon") == 1 ? true : false;
				hasDeal = json.getInt("has_deal") == 1 ? true : false;
				couponId = json.getLong("coupon_id");
				couponTitle = json.getString("coupon_title");
				couponUrl = json.getString("coupon_url");
				dealId = json.getString("deal_id");
				dealTitle = json.getString("deal_title");
				dealUrl = json.getString("deal_url");
			} catch (JSONException jsone) {
				throw new DianPingException(jsone.getMessage() + ":"
						+ json.toString(), jsone);
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String[] getRegions() {
		return regions;
	}

	public void setRegions(String[] regions) {
		this.regions = regions;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public float getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

	public String getRatingImgUrl() {
		return ratingImgUrl;
	}

	public void setRatingImgUrl(String ratingImgUrl) {
		this.ratingImgUrl = ratingImgUrl;
	}

	public String getRatingSImgUrl() {
		return ratingSImgUrl;
	}

	public void setRatingSImgUrl(String ratingSImgUrl) {
		this.ratingSImgUrl = ratingSImgUrl;
	}

	public int getProductGrade() {
		return productGrade;
	}

	public void setProductGrade(int productGrade) {
		this.productGrade = productGrade;
	}

	public int getDecorationGrade() {
		return decorationGrade;
	}

	public void setDecorationGrade(int decorationGrade) {
		this.decorationGrade = decorationGrade;
	}

	public int getServiceGrade() {
		return serviceGrade;
	}

	public void setServiceGrade(int serviceGrade) {
		this.serviceGrade = serviceGrade;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public String getBusinessUrl() {
		return businessUrl;
	}

	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public boolean isHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(boolean hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getCouponUrl() {
		return couponUrl;
	}

	public void setCouponUrl(String couponUrl) {
		this.couponUrl = couponUrl;
	}

	public boolean isHasDeal() {
		return hasDeal;
	}

	public void setHasDeal(boolean hasDeal) {
		this.hasDeal = hasDeal;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getDealTitle() {
		return dealTitle;
	}

	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
	}

	public String getDealUrl() {
		return dealUrl;
	}

	public void setDealUrl(String dealUrl) {
		this.dealUrl = dealUrl;
	}

	public static List<Business> constructBusiness(Response res)
			throws DianPingException {
		JSONObject json = res.asJSONObject();
		JSONArray list = null;
		try {
			if ("ok".equalsIgnoreCase(json.getString("status"))) {
				if (json.isNull("count") && json.getInt("count") == 0) {
					return Collections.emptyList();
				}
				list = json.getJSONArray("businesses");
				int size = list.length();
				List<Business> result = new ArrayList<Business>(size);
				for (int i = 0; i < size; i++) {
					result.add(new Business(list.getJSONObject(i)));
				}
				return result;
			} else {
				throw new DianPingException(json.getJSONObject("error")
						.getString("errorMessage"));
			}
		} catch (JSONException je) {
			throw new DianPingException(je);
		}

	}

}
