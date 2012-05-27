package com.juzhai.spider.share.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.juzhai.common.InitData;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Town;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.spider.share.ShareRegexConfig;
import com.juzhai.spider.share.exception.SpiderIdeaException;

@Service
public class DianpingSpiderIdeaService extends AbstractSpiderIdeaService {

	@Override
	protected void getPic(RawIdeaForm form, String content, String joinType)
			throws SpiderIdeaException {
		String pic = null;
		if (content.indexOf("class=\"thumb-switch\">") != -1) {
			pic = find(content,
					ShareRegexConfig.REGEXS.get(joinType + "_picSwitch"));
		} else {
			pic = find(content, ShareRegexConfig.REGEXS.get(joinType + "_pic"));
		}
		uploadPic(form, pic);
	}

	public static void main(String[] str) throws SpiderIdeaException {
		DianpingSpiderIdeaService s = new DianpingSpiderIdeaService();
		String content = s.getContent("http://www.dianping.com/shop/4704774");
		String title = s.find(content, "content=\"(.*?),");
		System.out.println(title);
	}

	@Override
	protected void getCharge(RawIdeaForm form, String charge) {
		if (StringUtils.isNotEmpty(charge)) {
			form.setCharge(Integer.parseInt(charge));
		}

	}

	@Override
	protected void getAddress(RawIdeaForm form, String content, String joinType) {
		String place = find(content,
				ShareRegexConfig.REGEXS.get(joinType + "_place"));
		String townString = find(content,
				ShareRegexConfig.REGEXS.get(joinType + "_town"));
		String cityString = find(content,
				ShareRegexConfig.REGEXS.get(joinType + "_city"));
		City city = InitData.getCityByName(cityString);
		if (city != null) {
			form.setCity(city.getId());
			form.setProvince(city.getProvinceId());
			if (StringUtils.isNotEmpty(townString)) {
				Town town = InitData.getTownByNameAndCityId(city.getId(),
						townString);
				if (town != null) {
					form.setTown(town.getId());
				}
			}
		}
		form.setPlace(place);

	}

	@Override
	protected void getTime(RawIdeaForm form, String content, String joinType) {

	}

}
