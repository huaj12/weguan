package com.juzhai.android.post.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.juzhai.android.core.model.Entity;

//TODO (done) Post是Passport的？
public class Post extends Entity {
	private static final long serialVersionUID = -4148599586995728665L;
	private long postId;
	private String purpose;
	private String content;
	private String place;
	private String pic;
	private String bigPic;
	private String categoryName;
	private String date;
	private int respCnt;
	private boolean hasResp;
	@JsonIgnore
	private long categoryId;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getRespCnt() {
		return respCnt;
	}

	public void setRespCnt(int respCnt) {
		this.respCnt = respCnt;
	}

	public boolean isHasResp() {
		return hasResp;
	}

	public void setHasResp(boolean hasResp) {
		this.hasResp = hasResp;
	}

	public String getBigPic() {
		return bigPic;
	}

	public void setBigPic(String bigPic) {
		this.bigPic = bigPic;
	}

	@Override
	public Object getIdentify() {
		return this.postId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

}
