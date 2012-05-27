package com.juzhai.spider.share.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.common.InitData;
import com.juzhai.core.util.DateFormat;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Town;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.spider.share.ShareRegexConfig;
import com.juzhai.spider.share.bean.DoubanSpiderWordTemplate;
import com.juzhai.spider.share.exception.SpiderIdeaException;

@Service
public class DoubanSpiderIdeaService extends AbstractSpiderIdeaService {
	@Autowired
	private MessageSource messageSource;

	@Override
	protected void getDetail(RawIdeaForm form, String content, String joinType) {
		String detail = null;
		if (content.indexOf("id=\"foldDescHook\">") != -1) {
			detail = find(content,
					ShareRegexConfig.REGEXS.get(joinType + "_detailMore"));
		} else {
			detail = find(content,
					ShareRegexConfig.REGEXS.get(joinType + "_detail"));
		}
		form.setDetail(detail);
	}

	private void getPlace(RawIdeaForm from, String address) {

		String[] str = address.split(" ");
		if (str != null && str.length > 0) {
			City city = InitData.getCityByName(str[0]);
			if (city != null) {
				from.setCity(city.getId());
				from.setProvince(city.getProvinceId());
				if (str.length == 3) {
					Town town = InitData.getTownByNameAndCityId(city.getId(),
							str[1]);
					if (town != null) {
						from.setTown(town.getId());
					}
					from.setPlace(str[2]);
				} else if (str.length == 2) {
					from.setPlace(str[1]);
				}

			}
		}
	}

	public void getDate(RawIdeaForm from, String time) {
		try {
			String[] dates = new String[2];
			String[] days = new String[2];
			String[] hours = null;
			Calendar c = Calendar.getInstance();
			String year = String.valueOf(c.get(c.YEAR));
			String wordYear = messageSource.getMessage(
					DoubanSpiderWordTemplate.YEAR.getName(), null,
					Locale.SIMPLIFIED_CHINESE);
			String wordTo = messageSource.getMessage(
					DoubanSpiderWordTemplate.TO.getName(), null,
					Locale.SIMPLIFIED_CHINESE);
			String wordWeekly = messageSource.getMessage(
					DoubanSpiderWordTemplate.WEEKLY.getName(), null,
					Locale.SIMPLIFIED_CHINESE);
			String wordEveryDay = messageSource.getMessage(
					DoubanSpiderWordTemplate.EVERYDAY.getName(), null,
					Locale.SIMPLIFIED_CHINESE);
			String wordMonth = messageSource.getMessage(
					DoubanSpiderWordTemplate.MONTH.getName(), null,
					Locale.SIMPLIFIED_CHINESE);
			String wordDay = messageSource.getMessage(
					DoubanSpiderWordTemplate.DAY.getName(), null,
					Locale.SIMPLIFIED_CHINESE);
			if (time.indexOf(wordYear) != -1) {
				year = time.substring(0, time.indexOf(wordYear));
				if (year.indexOf(wordTo) != -1) {
					year = time.substring(time.indexOf(wordTo) + 1,
							time.indexOf(wordYear));
				}
				time = time.replaceAll(year + wordYear, "");
			}
			if (time.indexOf(wordWeekly) != -1) {
				String[] str = time.substring(0, time.indexOf(wordWeekly))
						.split(wordTo);
				days[0] = year + "-" + getDay(str[0]);
				days[1] = year + "-" + getDay(str[1]);
			} else if (time.indexOf(wordEveryDay) != -1) {
				String[] str = time.substring(0, time.indexOf(wordEveryDay))
						.split(wordTo);
				days[0] = year + "-" + getDay(str[0]);
				days[1] = year + "-" + getDay(str[1]);
			} else {
				if (time.indexOf(wordTo) == -1) {
					String day = year + "-" + getDay(time);
					days[0] = day;
					days[1] = day;
				} else {
					String[] str = time.split(wordTo);
					days[0] = year
							+ "-"
							+ str[0].replaceAll(wordMonth, "-")
									.replaceAll(wordDay, "").trim() + ":00";
					days[1] = year
							+ "-"
							+ str[1].replaceAll(wordMonth, "-")
									.replaceAll(wordDay, "").trim() + ":00";
				}
			}
			if (days[0].indexOf(":") == -1) {
				hours = getHour(time);
				for (int i = 0; i < 2; i++) {
					dates[i] = days[i].trim() + " " + hours[i].trim() + ":00";
				}
			} else {
				for (int i = 0; i < 2; i++) {
					dates[i] = days[i].trim();
				}
			}
			// 防止页面出现2012-1-1应该是2012-01-01
			from.setStartDateString(DateFormat.SDF_TIME
					.format(DateFormat.SDF_TIME.parse(dates[0])));
			from.setEndDateString(DateFormat.SDF_TIME
					.format(DateFormat.SDF_TIME.parse(dates[1])));
		} catch (Exception e) {
		}

	}

	// 获取小时
	private String[] getHour(String time) {
		String hour = time.substring(time.indexOf(":") - 2);
		return hour.split("-");
	}

	// 获取日期
	private String getDay(String str) {
		return str
				.substring(
						0,
						str.indexOf(messageSource.getMessage(
								DoubanSpiderWordTemplate.DAY.getName(), null,
								Locale.SIMPLIFIED_CHINESE)))
				.replaceAll(
						messageSource.getMessage(
								DoubanSpiderWordTemplate.MONTH.getName(), null,
								Locale.SIMPLIFIED_CHINESE), "-").trim();
	}

	@Override
	public void getCharge(RawIdeaForm from, String charge) {
		try {
			Integer minCharge = null;
			List<String> list = findList(charge, "(\\d{1,6}+)");
			if (CollectionUtils.isNotEmpty(list)) {
				if (list.size() == 1) {
					minCharge = Integer.parseInt(list.get(0));
				} else {
					int[] intArray = new int[list.size()];
					for (int i = 0; i < list.size(); i++) {
						intArray[i] = Integer.parseInt(list.get(i));
					}
					minCharge = getMinArray(intArray);
				}
			}
			from.setCharge(minCharge);
		} catch (Exception e) {
		}

	}

	private int getMinArray(int[] intArray) {
		int minArray = intArray[0];
		for (int i = 0; i < intArray.length; i++) {
			if (intArray[i] < minArray) {
				minArray = intArray[i];
			}
		}
		return minArray;
	}

	@Override
	protected void getAddress(RawIdeaForm form, String content, String joinType) {
		String address = find(content,
				ShareRegexConfig.REGEXS.get(joinType + "_address"));
		getPlace(form, address);

	}

	@Override
	protected void getTime(RawIdeaForm form, String content, String joinType) {
		String time = find(content,
				ShareRegexConfig.REGEXS.get(joinType + "_time"));
		getDate(form, time);
	}

	public static void main(String[] str) throws SpiderIdeaException {
		DianpingSpiderIdeaService s = new DianpingSpiderIdeaService();
		String content = s.getContent("http://www.douban.com/event/16030182/");
		String title = null;
		System.out.println(content.indexOf("id=\"foldDescHook\">"));
		if (content.indexOf("id=\"foldDescHook\">") != -1) {
			title = s
					.find(content,
							"<div id=\"edesc_f\" class=\"wr\" style=\"display:none\">(.*?)<a href=\"#\" id=\"foldDescHook\">");
		} else {
			title = s.find(content,
					"活动详情</h2>\\s*?<div class=\"wr\">(.*?)</div>");
		}
		String time = s.find(content, "时间:&nbsp;&nbsp;</span>(.*?)\\s*?</div>");
		System.out.println(title);
		String[] str1 = time.split("至");

		System.out.println("2012" + "-"
				+ str1[0].replaceAll("月", "-").replaceAll("日", "").trim()
				+ ":00");
		System.out.println("2012" + "-"
				+ str1[1].replaceAll("月", "-").replaceAll("日", "").trim()
				+ ":00");
	}
}
