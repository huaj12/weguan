package com.juzhai.post.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.juzhai.passport.model.Profile;
import com.juzhai.post.model.Post;

public class PostResult implements Serializable {
	private static final long serialVersionUID = -2500679204600476446L;

	List<Post> posts;
	Map<Long, Profile> profileMap;

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Map<Long, Profile> getProfileMap() {
		return profileMap;
	}

	public void setProfileMap(Map<Long, Profile> profileMap) {
		this.profileMap = profileMap;
	}

}
