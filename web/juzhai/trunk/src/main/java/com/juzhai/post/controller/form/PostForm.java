package com.juzhai.post.controller.form;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class PostForm {

	private long postId;

	private long ideaId;

	private int purposeType;

	private String content;

	private String contentMd5;

	private long categoryId;

	private String place;

	private String dateString;

	private long picIdeaId;

	private String pic;

	private String link;

	private Date date;

	private MultipartFile postImg;

	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public int getPurposeType() {
		return purposeType;
	}

	public void setPurposeType(int purposeType) {
		this.purposeType = purposeType;
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

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getContentMd5() {
		return contentMd5;
	}

	public void setContentMd5(String contentMd5) {
		this.contentMd5 = contentMd5;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getPicIdeaId() {
		return picIdeaId;
	}

	public void setPicIdeaId(long picIdeaId) {
		this.picIdeaId = picIdeaId;
	}

	public MultipartFile getPostImg() {
		return postImg;
	}

	public void setPostImg(MultipartFile postImg) {
		this.postImg = postImg;
	}
}
