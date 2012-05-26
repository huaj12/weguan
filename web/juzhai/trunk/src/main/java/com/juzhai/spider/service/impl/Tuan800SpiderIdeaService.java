package com.juzhai.spider.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.common.InitData;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.web.jstl.JzUtilFunction;
import com.juzhai.passport.model.City;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.spider.RegexConfig;
import com.juzhai.spider.exception.SpiderIdeaException;

@Service
public class Tuan800SpiderIdeaService extends AbstractSpiderIdeaService {
	@Value("${idea.link.length.max}")
	private int ideaLinkLengthMax;

	@Override
	protected void getLink(RawIdeaForm form, String content, String joinType,
			String url) throws SpiderIdeaException {
		String outUrl = find(content,
				RegexConfig.REGEXS.get(joinType + "_outUrl"));
		String str = getContent(outUrl);
		String link = find(str, RegexConfig.REGEXS.get(joinType + "_tagerUrl"));
		if (link != null) {
			form.setLink(JzUtilFunction.truncate(link, ideaLinkLengthMax, ""));
		}

	}

	public static void main(String[] str) throws SpiderIdeaException {
		// DianpingSpiderIdeaService s = new DianpingSpiderIdeaService();
		// String content = s
		// .getContent("http://www.tuan800.com/deal/yilujin199_3121112");
		// String title = s.find(content,
		// "<div class=\"deal_content\">\\s*?<h1><a href=\"(.*?)\"");
		// String lala = s.getContent(title);
		// // String pic = s.find(content,
		// // "class=\"deal_out_link\">\\s*?<img src=\"(.*?)\"");
		// // String place = s.find(content, "\"adress\":\"(.*?)\"");
		// // String city = s.find(content, "_团800(.*?)团购网");
		// // String charge = s.find(content, "<h3>现价：<b>(.*?)</b>");
		// System.out.println(s.find(lala,
		// "<meta http-equiv=refresh content=\"0\" url=\"(.*?)\"/>"));
		String s = "http://tuan.qunar.com/team.php?ex_track=auto_4f3ca87c&id=QNRNTMzMDU2&outsrc=tuan800&src=tuan800&uid=02734&wi=Ul1kWld5fF98MnxffHxffHxffDI5ODM3NTN8X3wxMzM3OTU3OTAzODg4";
		System.out.println(StringUtil.chineseLength(s));
		// System.out.println(pic);
		// System.out.println(StringUtil.decodeUnicode(place));
		// System.out.println(charge);
		// System.out.println(city);
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
				RegexConfig.REGEXS.get(joinType + "_place"));
		if (StringUtils.isNotEmpty(place)) {
			place = StringUtil.decodeUnicode(place);
		}
		String cityString = find(content,
				RegexConfig.REGEXS.get(joinType + "_city"));
		City city = InitData.getCityByName(cityString);
		if (city != null) {
			form.setCity(city.getId());
			form.setProvince(city.getProvinceId());
		}
		form.setPlace(place);

	}

	@Override
	protected void getTime(RawIdeaForm form, String content, String joinType) {
		String startDate = find(content,
				RegexConfig.REGEXS.get(joinType + "_startDate"));
		String endDate = find(content,
				RegexConfig.REGEXS.get(joinType + "_endDate"));
		form.setStartDateString(getDate(startDate));
		form.setEndDateString(getDate(endDate));
	}

	private String getDate(String str) {
		try {
			Date d = new Date();
			d.setTime(Long.valueOf(str));
			return DateFormat.SDF_TIME.format(d);
		} catch (Exception e) {
			return null;
		}
	}

}
