package com.juzhai.mobile.idea.v12.controller.viewHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.common.model.City;
import com.juzhai.common.model.Town;
import com.juzhai.core.image.ImageUrl;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.idea.service.IIdeaRemoteService;
import com.juzhai.mobile.InitData;
import com.juzhai.mobile.idea.v12.controller.view.IdeaMView;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.Idea;

@Component("v12IdeaMViewHelper")
public class IdeaMViewHelper implements IIdeaMViewHelper {

	@Autowired
	private IIdeaRemoteService ideaService;

	@Override
	public IdeaMView createIdeaMView(UserContext context, Idea idea) {
		IdeaMView ideaMView = new IdeaMView();
		ideaMView.setIdeaId(idea.getId());
		ideaMView.setContent(idea.getContent());
		ideaMView.setPlace(idea.getPlace());
		ideaMView.setCharge(idea.getCharge());
		if (StringUtils.isNotEmpty(idea.getPic())) {
			ideaMView.setPic(ImageUrl.ideaPic(idea.getId(), idea.getPic(),
					JzImageSizeType.MIDDLE.getType()));
		}
		City city = InitData.getCityMap().get(idea.getCity());
		if (null != city) {
			ideaMView.setCityName(city.getName());
		}
		Town town = InitData.getTownMap().get(idea.getTown());
		if (null != town) {
			ideaMView.setTownName(town.getName());
		}
		Category category = InitData.getCategoryMap().get(idea.getCategoryId());
		if (null != category) {
			ideaMView.setCategoryName(category.getName());
		}
		ideaMView.setUseCount(idea.getUseCount());
		if (context.hasLogin()) {
			ideaMView.setHasUsed(ideaService.isUseIdea(context.getUid(),
					idea.getId()));
		}
		ideaMView.setBigPic(ImageUrl.ideaPic(idea.getId(), idea.getPic(),
				JzImageSizeType.BIG.getType()));
		if (idea.getStartTime() != null) {
			ideaMView.setStartTime(DateFormat.MOBILE_SDF_TIME.format(idea
					.getStartTime()));
		}
		if (idea.getEndTime() != null) {
			ideaMView.setEndTime(DateFormat.MOBILE_SDF_TIME.format(idea
					.getEndTime()));
		}
		return ideaMView;
	}
}