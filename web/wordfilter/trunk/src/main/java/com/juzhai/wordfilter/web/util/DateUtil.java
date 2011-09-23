package com.juzhai.wordfilter.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author kenny
 * 
 */
public class DateUtil {
	/** ���ڸ�ʽyyyy-MM-dd�ַ������� */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/** ���ڸ�ʽyyyy-MM-dd HH:mm:ss�ַ������� */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** ���ڸ�ʽyyyy-MM�ַ������� */
	public static final String MONTH_FORMAT = "yyyy-MM";

	/** һ����ʱ���Լ��long������ */
	public static final long MONTH_LONG = 2651224907l;

	/**
	 * �õ���ǰ���ڵ�ǰ/��beforeDays�����������
	 * 
	 * @param beforeDays
	 * @return
	 */
	public static String getDate(int beforeDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		String a = dateToString(c.getTime(), DATE_FORMAT);
		return a;
	}

	/**
	 * �õ���ǰ���ڵ�ǰ/��beforeDays�����������,��ʽ�Զ�
	 * 
	 * @param beforeDays
	 * @param dateFormat
	 * @return
	 */
	public static String getDate(int beforeDays, String dateFormat) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		String a = dateToString(c.getTime(), dateFormat);
		return a;
	}

	/**
	 * ����������ת��Ϊyyyy-MM-dd�ַ���
	 * 
	 * @param dateValue
	 * @return String
	 */
	public static String dateToString(Date dateValue) {
		return dateToString(dateValue, DATETIME_FORMAT);
	}

	/**
	 * ����������ת��Ϊָ����ʽ���ַ���
	 * 
	 * @param dateValue
	 * @param format
	 * @return String
	 */
	public static String dateToString(Date dateValue, String format) {
		if (dateValue == null || format == null) {
			return null;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(dateValue);
		}
	}

	/**
	 * ������yyyy-MM-dd�ַ���תΪ�������ͣ����ת��ʧ�ܷ���null
	 * 
	 * @param stringValue
	 * @return Date
	 */
	public static Date stringToDate(String stringValue) {
		return stringToDate(stringValue, DATE_FORMAT);
	}

	/**
	 * ��ָ�����ڸ�ʽ���ַ���תΪ�������ͣ����ת��ʧ�ܷ���null
	 * 
	 * @param stringValue
	 * @param format
	 * @return Date
	 */
	public static Date stringToDate(String stringValue, String format) {
		Date dateValue = null;
		if (stringValue != null && format != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				dateValue = dateFormat.parse(stringValue);

			} catch (ParseException ex) {
				dateValue = null;
			}
		}
		return dateValue;
	}

	/**
	 * ��õ�ǰ��
	 * 
	 * @return string
	 */
	public static String getNowYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	/**
	 * ��õ�ǰ��
	 * 
	 * @return string
	 */
	public static String getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			return "0" + month;
		} else {
			return String.valueOf(month);
		}
	}

	/**
	 * ��õ�ǰ��
	 * 
	 * @return string
	 */
	public static String getNowDay() {
		return dateToString(new Date(), "dd");

	}

	/**
	 * ����
	 * 
	 * @return
	 */
	public static String getYestday() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -1);
		return dateToString(date.getTime(), "dd");

	}

	/**
	 * ���ؼ�����ǰ��Date����
	 * 
	 * @param monthCount
	 *            ������
	 * @return Date
	 */
	public static Date getDateFront(int monthCount) {
		return new Date(Calendar.getInstance().getTimeInMillis() - MONTH_LONG
				* monthCount);
	}

	/**
	 * ���ص�ǰСʱ
	 * 
	 * @return string
	 */
	public static String getNowHour() {
		return dateToString(new Date(), "HH");
	}

	/**
	 * ���ص�ǰ����
	 * 
	 * @return string
	 */
	public static String getNowMinute() {
		return dateToString(new Date(), "mm");
	}

	/**
	 * ����ʱ�������ֵ
	 * 
	 * @param stringDate
	 * @param num
	 * @return Date
	 */
	public static Date setDate(String stringDate, int num) {
		if (stringDate != null) {
			Date date = stringToDate(stringDate, "yyyy-MM-dd");
			return setDate(date, num);
		} else {
			return null;
		}
	}

	/**
	 * ����ʱ�������ֵ
	 * 
	 * @param date
	 * @param num
	 * @return Date
	 */
	public static Date setDate(Date date, int num) {
		Date dateValue = null;
		Calendar c = null;
		if (date != null) {
			c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, num);
			dateValue = c.getTime();
		}
		return dateValue;
	}

	/**
	 * ȡ���������ڵ�ʱ����,��������
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDayBetween(Date d1, Date d2) {
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		if (d1.before(d2)) {
			before.setTime(d1);
			after.setTime(d2);
		} else {
			before.setTime(d2);
			after.setTime(d1);
		}
		int days = 0;

		int startDay = before.get(Calendar.DAY_OF_YEAR);
		int endDay = after.get(Calendar.DAY_OF_YEAR);

		int startYear = before.get(Calendar.YEAR);
		int endYear = after.get(Calendar.YEAR);
		before.clear();
		before.set(startYear, 0, 1);

		while (startYear != endYear) {
			before.set(startYear++, Calendar.DECEMBER, 31);
			days += before.get(Calendar.DAY_OF_YEAR);
		}
		return days + endDay - startDay;
	}

	public static Date addDay(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

	/** for test only */
	public static void main(String[] args) {
		// Date myDate = DateUtil.stringToDate("2009-10-21 16:58:26",
		// "yyyy-MM-dd hh:mm:ss");
		Date myDate = DateUtil.stringToDate("12365698", "yyyy-MM-dd");

		System.out.println("myDate:" + myDate);

		Date endDate = DateUtil.addDay(myDate, 15);
		Date currentDate = new Date();
		System.out.println(endDate.after(currentDate));
		System.out.println("��ǰ�죺" + DateUtil.getDate(-3));
		System.out.println("����" + DateUtil.getDate(1));
		System.out.println("����" + DateUtil.getDate(1).replaceAll("-", ""));
		System.out
				.println("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Poco 0.31; .NET CLR 1.1.4322; InfoPath.1; Alexa Toolbar)"
						.length());
		System.out.println(DateUtil.addDay(myDate, 5));
		System.out.println(DateUtil.getNowYear() + DateUtil.getNowMonth()
				+ DateUtil.getNowDay());
	}
}
