package com.easylife.movie.video.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.easylife.movie.core.model.Entity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video extends Entity {
	private static final long serialVersionUID = -1962498238402862376L;
	private String id;
	private String title;
	private long categoryId;
	private String playTime;
	private String posterImg;
	private String videoSrc;
	private String shareUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getPosterImg() {
		return posterImg;
	}

	public void setPosterImg(String posterImg) {
		this.posterImg = posterImg;
	}

	public String getVideoSrc() {
		return videoSrc;
	}

	public void setVideoSrc(String videoSrc) {
		this.videoSrc = videoSrc;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	@Override
	public Object getIdentify() {
		return id;
	}

}
