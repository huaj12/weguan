package com.juzhai.mobile.post.v12.controller.viewHelper;

import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.post.v12.controller.view.PostMView;
import com.juzhai.post.model.Post;

public interface IPostMViewHelper {

	PostMView createPostMView(UserContext context, Post post);
}
