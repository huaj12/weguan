package com.juzhai.core.web.jstl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.common.InitData;
import com.juzhai.common.model.Face;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.core.util.TextTruncateUtil;

public class JzUtilFunction {

	private final static Pattern FACE_PATTERN = Pattern.compile("\\[(.+?)\\]");

	/**
	 * 截取字符串
	 * 
	 * @param originalText
	 * @param targetLength
	 * @param suffix
	 * @return
	 */
	public static String truncate(String originalText, int targetLength,
			String suffix) {
		return TextTruncateUtil.truncate(originalText, targetLength, suffix);
	}

	/**
	 * 比较时间
	 * 
	 * @param date
	 * @return
	 */
	public static boolean dateAfter(Date date) {
		return date.before(new Date());
	}

	/**
	 * 多少秒之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeSeconds(Date date) {
		return (System.currentTimeMillis() - date.getTime()) / 1000;
	}

	/**
	 * 多少分钟之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeMinutes(Date date) {
		return (System.currentTimeMillis() - date.getTime()) / 60000;
	}

	/**
	 * 多少天之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeDays(Date date) {
		return ((DateUtils.truncate(new Date(), Calendar.DATE).getTime() - DateUtils
				.truncate(date, Calendar.DATE).getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 显示几岁
	 * 
	 * @param year
	 * @return
	 */
	public static int age(Integer year, boolean birthSecret) {
		if (birthSecret) {
			return -1;
		}
		if (null == year || year <= 1900) {
			return -1;
		}
		Calendar c = Calendar.getInstance();
		int cYear = c.get(Calendar.YEAR);
		return cYear - year;
	}

	/**
	 * 显示空闲时间
	 * 
	 * @param freeDateList
	 */
	public static String showFreeDates(List<Date> freeDateList, int count) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "日");
		map.put(2, "一");
		map.put(3, "二");
		map.put(4, "三");
		map.put(5, "四");
		map.put(6, "五");
		map.put(7, "六");
		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance();
		int todayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < freeDateList.size(); i++) {
			if (i >= count) {
				break;
			}
			c.setTime(freeDateList.get(i));
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if (i > 0) {
				sb.append(",");
			}
			if (dayOfWeek == todayOfWeek) {
				sb.append("今天");
			} else if (dayOfWeek == todayOfWeek + 1
					|| (dayOfWeek == 1 && todayOfWeek == 7)) {
				sb.append("明天");
			} else if (dayOfWeek > todayOfWeek || dayOfWeek == 1) {
				sb.append("本周").append(map.get(dayOfWeek));
			} else {
				sb.append("下周").append(map.get(dayOfWeek));
			}
		}

		return sb.toString();
	}

	/**
	 * 离周末还有多少小时
	 * 
	 * @param date
	 * @return
	 */
	public static long gapWeekendHours() {
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			return 0;
		}
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (c.getTimeInMillis() - System.currentTimeMillis()) / 3600000;
	}

	/**
	 * 表情转换
	 * 
	 * @param content
	 * @return
	 */
	public static String convertFace(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		content = HtmlUtils.htmlEscape(content);
		StringBuffer sb = new StringBuffer();
		Matcher m = FACE_PATTERN.matcher(content);
		while (m.find()) {
			String name = m.group(1);
			Face face = InitData.FACE_MAP.get(name);
			if (null == face || StringUtils.isEmpty(face.getPic())) {
				m.appendReplacement(sb, m.group());
			} else {
				m.appendReplacement(sb,
						"<img src=\"" + StaticUtil.getPrefixImage()
								+ "/images/face/" + face.getPic()
								+ "\" alt=\"[" + name + "]\" title=\"[" + name
								+ "]\" width=\"22\" height=\"22\" />");
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * html转换
	 * 
	 * @param content
	 * @return
	 */
	public static String htmlOut(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		return HtmlUtils.htmlEscape(content);
	}

	/**
	 * 邮箱的网站
	 * 
	 * @param email
	 * @return
	 */
	public static String mailDomain(String email) {
		if (email.endsWith("@126.com"))
			return "http://email.126.com";
		if (email.endsWith("@163.com"))
			return "http://email.163.com";
		if (email.endsWith("@qq.com"))
			return "https://mail.qq.com";
		if (email.endsWith("@sina.com.cn") || email.endsWith("@sina.com"))
			return "http://mail.sina.com.cn";
		if (email.endsWith("@sohu.com"))
			return "http://mail.sohu.com";
		if (email.endsWith("@hotmail.com"))
			return "http://login.live.com";
		if (email.endsWith("@gmail.com"))
			return "http://www.gmail.com";
		if (email.endsWith("@yahoo.com.cn") || email.endsWith("@yahoo.com"))
			return "http://mail.cn.yahoo.com";
		return "javascript:void(0);";
	}

	public static String cleanString(String str) {
		if (str != null && !"".equals(str)) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			String strNoBlank = m.replaceAll("");
			return strNoBlank;
		} else {
			return str;
		}
	}

	// TODO (done) 老问题了，自己找
	public static String getLink(String link) {
		String head = "http://";
		if (StringUtils.isNotEmpty(link)) {
			if (!link.trim().startsWith(head)) {
				link = head + link.trim();
			}
		}
		return link;
	}

}
