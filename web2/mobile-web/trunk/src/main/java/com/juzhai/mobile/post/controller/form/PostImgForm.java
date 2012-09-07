package com.juzhai.mobile.post.controller.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class PostImgForm implements Serializable {

	private static final long serialVersionUID = 5623117948832694507L;

	private MultipartFile postImg;

	public MultipartFile getPostImg() {
		return postImg;
	}

	public void setPostImg(MultipartFile postImg) {
		this.postImg = postImg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
