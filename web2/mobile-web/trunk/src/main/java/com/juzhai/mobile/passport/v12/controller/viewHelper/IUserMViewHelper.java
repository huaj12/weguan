package com.juzhai.mobile.passport.v12.controller.viewHelper;

import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.passport.v12.controller.view.UserMView;
import com.juzhai.passport.bean.ProfileCache;

public interface IUserMViewHelper {

	UserMView createUserMView(UserContext context, ProfileCache profileCache,
			boolean isCompleteGuide);
}
