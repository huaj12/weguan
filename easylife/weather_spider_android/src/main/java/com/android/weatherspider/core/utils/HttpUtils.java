package com.android.weatherspider.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.util.Xml;

import com.android.weatherspider.core.ApplicationContext;
import com.android.weatherspider.core.data.SharedPreferencesManager;
import com.android.weatherspider.model.Air;
import com.android.weatherspider.model.ForecastHour;
import com.android.weatherspider.model.WeatherInfo;

public class HttpUtils {
	private static int CONNECT_TIMEOUT = 20000;
	private static int READ_TIMEOUT = 30000;
	private final static String CDNSECRET = "CDNSecret";
	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd");

	public static InputStream getContent(String host, String uri) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet();
		try {
			httpget.setURI(new URI(uri));
			HttpHost httphost = new HttpHost(host, 80, "http");
			return httpclient.execute(httphost, httpget).getEntity()
					.getContent();
		} catch (Exception e) {
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	public static String getContent(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet accessget = new HttpGet(url);
		String content = "";
		try {
			content = httpclient.execute(accessget, responseHandler);
		} catch (Exception e) {
			Log.e("weather_spider", e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return toUtf8String(content);
	}

	public static String toUtf8String(String s) {
		try {
			return new String(s.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String getMD5URL(String s, String s1, Context context) {

		String s7 = getCDNSecret(context);
		String s2 = s7;

		String s3 = (new StringBuilder()).append(s2).append(s).toString();
		String s4 = Long.toHexString(System.currentTimeMillis() / 1000L)
				.toUpperCase();
		String s5 = computeMD5((new StringBuilder()).append(s3).append(s4)
				.toString());
		String s6 = (new StringBuilder()).append(s5).append("/").append(s4)
				.append(s).append(s1).toString();
		return s6;

	}

	public static String getCDNSecret(Context context) {
		SharedPreferencesManager dataManager = new SharedPreferencesManager(
				context);
		String secret = dataManager.getString(CDNSECRET);
		if (StringUtils.hasText(secret)) {
			return secret;
		}
		InputStream inputstream;
		String s;
		inputstream = null;
		s = "mojisecret";
		try {
			inputstream = getContent("weather.moji001.com",
					"/weather/GetCDNSecret");
		} catch (Exception exception) {
		}
		if (inputstream == null) {
			return s;
		}

		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(inputstream));
		do {

			try {
				String s2 = bufferedreader.readLine();
				if (s2 == null)
					break;
				String s3 = s2.trim();
				if (s3 == null || s3.equals(""))
					continue;
				s = s3;
				dataManager.commit(CDNSECRET, s);
				break;
			}
			// Misplaced declaration of an exception variable
			catch (IOException ioexception) {
			}

		} while (true);

		return s;
	}

	private static String computeMD5(String s) {

		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		char ac[] = s.toCharArray();
		byte abyte0[] = new byte[ac.length];
		for (int i = 0; i < ac.length; i++)
			abyte0[i] = (byte) ac[i];

		byte abyte1[] = messagedigest.digest(abyte0);
		StringBuffer stringbuffer = new StringBuffer();
		for (int j = 0; j < abyte1.length; j++) {
			int k = 0xff & abyte1[j];
			if (k < 16)
				stringbuffer.append("0");
			stringbuffer.append(Integer.toHexString(k));
		}

		return stringbuffer.toString();

	}

	public static String getCDNURL(long city) {
		return (new StringBuilder()).append("/data/xml/weather/").append("200")
				.append("/").append(city).append(".xml").toString();
	}

	public static String getCDNParameter(long city, long userId) {
		return (new StringBuilder()).append("?&Platform=Android&BaseOSVer=")
				.append("10").append("&UserID=").append(userId)
				.append("&CityID=").append(city).append("&Version=")
				.append("10023502").append("&IMEI=")
				.append("35278404110901160").append("&SdkVer=").append("2.3.3")
				.append("&Device=phone").append("&Model=").append("sdk")
				.append("&PartnerKey=").append("5040").append("&DV=")
				.append("200").append("&VersionType=").append("1").toString();
	}

	public static WeatherInfo parse(String domain, long city, long userid,
			Context context) {
		WeatherInfo todayinfo = new WeatherInfo();
		Calendar cal = Calendar.getInstance();
		String today = sdf.format(cal.getTime());
		todayinfo.setDate(today);
		try {
			String content = getContent(domain
					+ getMD5URL(getCDNURL(city), getCDNParameter(city, userid),
							context));
			if (!StringUtils.hasText(content)) {
				return null;
			}
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if ("hour".equals(parser.getName())) {
						List<ForecastHour> forecastHourList = todayinfo
								.getForecastHours();
						if (CollectionUtils.isEmpty(forecastHourList)) {
							forecastHourList = new ArrayList<ForecastHour>();
						}
						ForecastHour forecastHour = new ForecastHour();
						forecastHour.setTimeInterval(parser.getAttributeValue(
								"", "timeinterval"));
						forecastHour.setSky(parser.getAttributeValue("", "wd"));
						forecastHour.setMinTmp(parser.getAttributeValue("",
								"ltmp"));
						forecastHour.setMaxTmp(parser.getAttributeValue("",
								"htmp"));
						forecastHour.setWindPower(parser.getAttributeValue("",
								"wl"));
						forecastHour.setWindDirection(parser.getAttributeValue(
								"", "wdir"));
						forecastHour.setId(parser.getAttributeValue("", "id"));
						forecastHourList.add(forecastHour);
						todayinfo.setForecastHours(forecastHourList);
					}
					if ("dt".equals(parser.getName())) {
						String date = parser.getAttributeValue("", "date");
						if (date.equals(today)) {
							todayinfo.setDaytimeSky(parser.getAttributeValue(
									"", "hwd"));
							todayinfo.setDescription(parser.getAttributeValue(
									"", "kn"));
							todayinfo.setWindPower(parser.getAttributeValue("",
									"wl"));
							todayinfo.setWindDirection(parser
									.getAttributeValue("", "wdir"));
							todayinfo.setNightSky(parser.getAttributeValue("",
									"lwd"));
							todayinfo.setLastFestival(parser.getAttributeValue(
									"", "ftv"));
							todayinfo.setMinTmp(parser.getAttributeValue("",
									"ltmp"));
							todayinfo.setMaxTmp(parser.getAttributeValue("",
									"htmp"));
							todayinfo.setSunrise(parser.getAttributeValue("",
									"sr"));
							todayinfo.setSunset(parser.getAttributeValue("",
									"ss"));
						}
					}
					if ("cc".equals(parser.getName())) {
						todayinfo.setLunarCalendar(parser.getAttributeValue("",
								"ldt"));
						todayinfo.setUpdateTime(parser.getAttributeValue("",
								"upt"));
						todayinfo
								.setNowTmp(parser.getAttributeValue("", "tmp"));
						todayinfo.setHum(parser.getAttributeValue("", "hum"));
						todayinfo.setSky(parser.getAttributeValue("", "wd"));
					}
					if ("day".equals(parser.getName())) {
						String date = parser.getAttributeValue("", "date");
						if (date.equals(today)) {
							todayinfo.setDaytimeWindPower(parser
									.getAttributeValue("", "hwl"));
							todayinfo.setNightWindPower(parser
									.getAttributeValue("", "lwl"));
							todayinfo.setDaytimeWindDirection(parser
									.getAttributeValue("", "hwdir"));
							todayinfo.setNightWindDirection(parser
									.getAttributeValue("", "lwdir"));
						}
					}
					if ("air".equals(parser.getName())) {
						Air air = new Air();
						air.setAqigrade(parser
								.getAttributeValue("", "aqigrade"));
						air.setCityaveragename(parser.getAttributeValue("",
								"cityaveragename"));
						air.setCo(parser.getAttributeValue("", "co"));
						air.setUpdateTime(parser.getAttributeValue("", "ptime"));
						air.setDesc(parser.getAttributeValue("", "desc"));
						air.setHiaqi(parser.getAttributeValue("", "hiaqi"));
						air.setHname(parser.getAttributeValue("", "hname"));
						air.setLevel(parser.getAttributeValue("", "lv"));
						air.setLiaqi(parser.getAttributeValue("", "liaqi"));
						air.setLname(parser.getAttributeValue("", "lname"));
						air.setNo2(parser.getAttributeValue("", "no2"));
						air.setO3(parser.getAttributeValue("", "o3"));
						air.setPm10(parser.getAttributeValue("", "pmtenaqi"));
						air.setPm25(parser.getAttributeValue("", "pmtwoaqi"));
						air.setSo2(parser.getAttributeValue("", "so2"));
						air.setTitle(parser.getAttributeValue("", "title"));
						todayinfo.setAir(air);
					}
					break;
				case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				}
				event = parser.next();
			}
		} catch (Exception e) {

		}
		return todayinfo;
	}

	// public static Map<String, WeatherInfo> parse(String domain, long city,
	// long userid, Context context) {
	// Calendar cal = Calendar.getInstance();
	// String today = sdf.format(cal.getTime());
	// Map<String, WeatherInfo> weathers = null;
	// try {
	// String content = getContent(domain
	// + getMD5URL(getCDNURL(city), getCDNParameter(city, userid),
	// context));
	// if (!StringUtils.hasText(content)) {
	// return Collections.emptyMap();
	// }
	// XmlPullParser parser = Xml.newPullParser();
	// parser.setInput(new StringReader(content));
	// int event = parser.getEventType();
	// while (event != XmlPullParser.END_DOCUMENT) {
	// switch (event) {
	// case XmlPullParser.START_DOCUMENT:
	// weathers = new HashMap<String, WeatherInfo>();
	// for (int i = 0; i < 5; i++) {
	// if (i == 0) {
	// cal.add(Calendar.DAY_OF_WEEK, 0);
	// } else {
	// cal.add(Calendar.DAY_OF_WEEK, 1);
	// }
	// WeatherInfo wInfo = new WeatherInfo();
	// wInfo.setDate(sdf.format(cal.getTime()));
	// weathers.put(wInfo.getDate(), wInfo);
	// }
	// break;
	// case XmlPullParser.START_TAG:
	// WeatherInfo todayinfo = null;
	// if ("hour".equals(parser.getName())) {
	// todayinfo = weathers.get(today);
	// List<ForecastHour> forecastHourList = todayinfo
	// .getForecastHours();
	// if (CollectionUtils.isEmpty(forecastHourList)) {
	// forecastHourList = new ArrayList<ForecastHour>();
	// }
	// ForecastHour forecastHour = new ForecastHour();
	// forecastHour.setTimeInterval(parser.getAttributeValue(
	// "", "timeinterval"));
	// forecastHour.setSky(parser.getAttributeValue("", "wd"));
	// forecastHour.setMinTmp(parser.getAttributeValue("",
	// "ltmp"));
	// forecastHour.setMaxTmp(parser.getAttributeValue("",
	// "htmp"));
	// forecastHour.setWindPower(parser.getAttributeValue("",
	// "wl"));
	// forecastHour.setWindDirection(parser.getAttributeValue(
	// "", "wdir"));
	// forecastHour.setId(parser.getAttributeValue("", "id"));
	// forecastHourList.add(forecastHour);
	// todayinfo.setForecastHours(forecastHourList);
	// weathers.put(today, todayinfo);
	// }
	// if ("dt".equals(parser.getName())) {
	// String date = parser.getAttributeValue("", "date");
	// WeatherInfo info = weathers.get(date);
	// if (info != null) {
	// info.setDaytimeSky(parser.getAttributeValue("",
	// "hwd"));
	// info.setDescription(parser.getAttributeValue("",
	// "kn"));
	// info.setWindPower(parser
	// .getAttributeValue("", "wl"));
	// info.setWindDirection(parser.getAttributeValue("",
	// "wdir"));
	// info.setNightSky(parser
	// .getAttributeValue("", "lwd"));
	// info.setLastFestival(parser.getAttributeValue("",
	// "ftv"));
	// info.setMinTmp(parser.getAttributeValue("", "ltmp"));
	// info.setMaxTmp(parser.getAttributeValue("", "htmp"));
	// info.setSunrise(parser.getAttributeValue("", "sr"));
	// info.setSunset(parser.getAttributeValue("", "ss"));
	// weathers.put(date, info);
	// }
	// }
	// if ("cc".equals(parser.getName())) {
	// todayinfo = weathers.get(today);
	// todayinfo.setLunarCalendar(parser.getAttributeValue("",
	// "ldt"));
	// todayinfo.setUpdateTime(parser.getAttributeValue("",
	// "upt"));
	// todayinfo
	// .setNowTmp(parser.getAttributeValue("", "tmp"));
	// todayinfo.setHum(parser.getAttributeValue("", "hum"));
	// todayinfo.setSky(parser.getAttributeValue("", "wd"));
	// weathers.put(today, todayinfo);
	// }
	// if ("day".equals(parser.getName())) {
	// String date = parser.getAttributeValue("", "date");
	// WeatherInfo info = weathers.get(date);
	// if (info != null) {
	// info.setDaytimeWindPower(parser.getAttributeValue(
	// "", "hwl"));
	// info.setNightWindPower(parser.getAttributeValue("",
	// "lwl"));
	// info.setDaytimeWindDirection(parser
	// .getAttributeValue("", "hwdir"));
	// info.setNightWindDirection(parser
	// .getAttributeValue("", "lwdir"));
	// weathers.put(date, info);
	// }
	// }
	// if ("air".equals(parser.getName())) {
	// todayinfo = weathers.get(today);
	// Air air = new Air();
	// air.setAqigrade(parser
	// .getAttributeValue("", "aqigrade"));
	// air.setCityaveragename(parser.getAttributeValue("",
	// "cityaveragename"));
	// air.setCo(parser.getAttributeValue("", "co"));
	// air.setUpdateTime(parser.getAttributeValue("", "ptime"));
	// air.setDesc(parser.getAttributeValue("", "desc"));
	// air.setHiaqi(parser.getAttributeValue("", "hiaqi"));
	// air.setHname(parser.getAttributeValue("", "hname"));
	// air.setLevel(parser.getAttributeValue("", "lv"));
	// air.setLiaqi(parser.getAttributeValue("", "liaqi"));
	// air.setLname(parser.getAttributeValue("", "lname"));
	// air.setNo2(parser.getAttributeValue("", "no2"));
	// air.setO3(parser.getAttributeValue("", "o3"));
	// air.setPm10(parser.getAttributeValue("", "pmtenaqi"));
	// air.setPm25(parser.getAttributeValue("", "pmtwoaqi"));
	// air.setSo2(parser.getAttributeValue("", "so2"));
	// air.setTitle(parser.getAttributeValue("", "title"));
	// todayinfo.setAir(air);
	// weathers.put(today, todayinfo);
	// }
	// break;
	// case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
	// break;
	// case XmlPullParser.END_DOCUMENT:
	// break;
	// }
	// event = parser.next();
	// }
	// } catch (Exception e) {
	//
	// }
	// return weathers;
	// }

	public static <T> ResponseEntity<T> get(Context context, String uri,
			Map<String, Object> values, Class<T> responseType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<Object> requestEntity = new HttpEntity<Object>(
				requestHeaders);
		RestTemplate restTemplate = createRestTemplate(context);
		if (null == restTemplate) {
			throw new RestClientException("no network");
		}
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		ApplicationContext config = (ApplicationContext) context
				.getApplicationContext();
		try {
			ResponseEntity<T> responseEntity = restTemplate.exchange(
					config.getBaseUrl() + createHttpParam(uri, values),
					HttpMethod.GET, requestEntity, responseType);
			return responseEntity;
		} catch (RestClientException e) {
			throw e;
		}
	}

	private static String createHttpParam(String uri, Map<String, Object> values) {
		if (CollectionUtils.isEmpty(values)) {
			return uri;
		}
		StringBuilder str = new StringBuilder();
		if (uri.indexOf("?") == -1) {
			str.append("?");
		}
		for (Entry<String, Object> entry : values.entrySet()) {
			if (str.length() != 1) {
				str.append('&');
			}
			str.append(entry.getKey());
			str.append('=');
			str.append(String.valueOf(entry.getValue()));
		}
		return uri + str.toString();
	}

	private static RestTemplate createRestTemplate(final Context context) {
		boolean hasNetwork = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cwjManager.getActiveNetworkInfo() != null) {
			hasNetwork = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		if (!hasNetwork) {
			return null;
		}
		SimpleClientHttpRequestFactory scrf = new SimpleClientHttpRequestFactory();
		scrf.setConnectTimeout(CONNECT_TIMEOUT);
		scrf.setReadTimeout(READ_TIMEOUT);
		RestTemplate restTemplate = new RestTemplate(scrf);
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response)
					throws IOException {
				if (null == response || response.getStatusCode() == null) {
					return false;
				}
				return !HttpStatus.OK.equals(response.getStatusCode());
			}

			@Override
			public void handleError(ClientHttpResponse response)
					throws IOException {
			}
		});
		return restTemplate;
	}

	public static <T> ResponseEntity<T> post(Context context, String uri,
			Map<String, Object> values, Class<T> responseType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		requestHeaders.setContentType(new MediaType("application",
				"x-www-form-urlencoded"));
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		if (!CollectionUtils.isEmpty(values)) {
			for (Entry<String, Object> entry : values.entrySet()) {
				formData.add(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				formData, requestHeaders);
		RestTemplate restTemplate = createRestTemplate(context);
		if (null == restTemplate) {
			throw new RestClientException("no network");
		}
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		ApplicationContext config = (ApplicationContext) context
				.getApplicationContext();
		try {
			ResponseEntity<T> responseEntity = restTemplate.exchange(
					config.getBaseUrl() + uri, HttpMethod.POST, requestEntity,
					responseType);
			return responseEntity;
		} catch (RestClientException e) {
			throw e;
		}
	}
}
