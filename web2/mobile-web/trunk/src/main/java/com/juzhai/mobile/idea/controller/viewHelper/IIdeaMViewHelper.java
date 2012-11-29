package com.juzhai.mobile.idea.controller.viewHelper;

import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.idea.controller.view.IdeaMView;
import com.juzhai.post.model.Idea;

public interface IIdeaMViewHelper {

	IdeaMView createPostMView(UserContext context, Idea idea);
}
