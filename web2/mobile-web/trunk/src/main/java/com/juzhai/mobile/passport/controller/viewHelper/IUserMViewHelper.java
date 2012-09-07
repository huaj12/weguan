package com.juzhai.mobile.passport.controller.viewHelper;

import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.passport.controller.view.UserMView;
import com.juzhai.passport.bean.ProfileCache;

public interface IUserMViewHelper {

	UserMView createUserMView(UserContext context, ProfileCache profileCache,
			boolean isCompleteGuide);
}
