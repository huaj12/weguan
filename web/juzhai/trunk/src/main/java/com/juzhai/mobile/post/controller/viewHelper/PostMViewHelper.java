package com.juzhai.mobile.post.controller.viewHelper;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.post.controller.view.PostMView;
import com.juzhai.post.InitData;
import com.juzhai.post.bean.PurposeType;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;

@Component
public class PostMViewHelper implements IPostMViewHelper {

	@Autowired
	private IPostService postService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public PostMView createPostMView(UserContext context, Post post) {
		PostMView postView = new PostMView();
		postView.setPostId(post.getId());
		postView.setContent(post.getContent());
		postView.setPic(JzResourceFunction.postPic(post.getId(),
				post.getIdeaId(), post.getPic(),
				JzImageSizeType.MIDDLE.getType()));
		postView.setBigPic(JzResourceFunction.postPic(post.getId(),
				post.getIdeaId(), post.getPic(), JzImageSizeType.BIG.getType()));
		postView.setPlace(post.getPlace());
		postView.setPurpose(messageSource.getMessage(
				PurposeType.getWordMessageKey(post.getPurposeType()), null,
				Locale.SIMPLIFIED_CHINESE));
		postView.setRespCnt(post.getResponseCnt());
		if (null != post.getDateTime()) {
			postView.setDate(DateFormat.SDF.format(post.getDateTime()));
		}
		Category category = InitData.CATEGORY_MAP.get(post.getCategoryId());
		if (null != category) {
			postView.setCategoryName(category.getName());
		}
		if (null != context && context.hasLogin()
				&& context.getUid() != post.getCreateUid()) {
			postView.setHasResp(postService.isResponsePost(context.getUid(),
					post.getId()));
		}
		return postView;
	}

}
