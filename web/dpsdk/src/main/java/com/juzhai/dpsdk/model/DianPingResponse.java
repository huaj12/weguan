package com.juzhai.dpsdk.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.juzhai.dpsdk.exception.DianPingException;
import com.juzhai.dpsdk.http.HTMLEntity;
import com.juzhai.dpsdk.http.Response;
import com.juzhai.dpsdk.json.model.JSONException;
import com.juzhai.dpsdk.json.model.JSONObject;

public class DianPingResponse implements java.io.Serializable {
	private static final long serialVersionUID = -8534367925757058169L;
	private static Map<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>();
	private transient int rateLimitLimit = -1;
	private transient int rateLimitRemaining = -1;
	private transient long rateLimitReset = -1;

	public DianPingResponse() {
	}

	public DianPingResponse(Response res) {
		String limit = res.getResponseHeader("X-RateLimit-Limit");
		if (null != limit) {
			rateLimitLimit = Integer.parseInt(limit);
		}
		String remaining = res.getResponseHeader("X-RateLimit-Remaining");
		if (null != remaining) {
			rateLimitRemaining = Integer.parseInt(remaining);
		}
		String reset = res.getResponseHeader("X-RateLimit-Reset");
		if (null != reset) {
			rateLimitReset = Long.parseLong(reset);
		}
	}

	protected static void ensureRootNodeNameIs(String rootName, Element elem)
			throws DianPingException {
		if (!rootName.equals(elem.getNodeName())) {
			throw new DianPingException(
					"Unexpected root node name:"
							+ elem.getNodeName()
							+ ". Expected:"
							+ rootName
							+ ". Check the availability of the Weibo API at http://open.t.sina.com.cn/.");
		}
	}

	protected static void ensureRootNodeNameIs(String[] rootNames, Element elem)
			throws DianPingException {
		String actualRootName = elem.getNodeName();
		for (String rootName : rootNames) {
			if (rootName.equals(actualRootName)) {
				return;
			}
		}
		String expected = "";
		for (int i = 0; i < rootNames.length; i++) {
			if (i != 0) {
				expected += " or ";
			}
			expected += rootNames[i];
		}
		throw new DianPingException(
				"Unexpected root node name:"
						+ elem.getNodeName()
						+ ". Expected:"
						+ expected
						+ ". Check the availability of the Weibo API at http://open.t.sina.com.cn/.");
	}

	protected static void ensureRootNodeNameIs(String rootName, Document doc)
			throws DianPingException {
		Element elem = doc.getDocumentElement();
		if (!rootName.equals(elem.getNodeName())) {
			throw new DianPingException(
					"Unexpected root node name:"
							+ elem.getNodeName()
							+ ". Expected:"
							+ rootName
							+ ". Check the availability of the Weibo API at http://open.t.sina.com.cn/");
		}
	}

	protected static boolean isRootNodeNilClasses(Document doc) {
		String root = doc.getDocumentElement().getNodeName();
		return "nil-classes".equals(root) || "nilclasses".equals(root);
	}

	protected static String getChildText(String str, Element elem) {
		return HTMLEntity.unescape(getTextContent(str, elem));
	}

	protected static String getTextContent(String str, Element elem) {
		NodeList nodelist = elem.getElementsByTagName(str);
		if (nodelist.getLength() > 0) {
			Node node = nodelist.item(0).getFirstChild();
			if (null != node) {
				String nodeValue = node.getNodeValue();
				return null != nodeValue ? nodeValue : "";
			}
		}
		return "";
	}

	/* modify by sycheng add "".equals(str) */
	protected static int getChildInt(String str, Element elem) {
		String str2 = getTextContent(str, elem);
		if (null == str2 || "".equals(str2) || "null".equals(str)) {
			return -1;
		} else {
			return Integer.valueOf(str2);
		}
	}

	protected static long getChildLong(String str, Element elem) {
		String str2 = getTextContent(str, elem);
		if (null == str2 || "".equals(str2) || "null".equals(str)) {
			return -1;
		} else {
			return Long.valueOf(str2);
		}
	}

	protected static String getString(String name, JSONObject json,
			boolean decode) {
		String returnValue = null;
		try {
			returnValue = json.getString(name);
			if (decode) {
				try {
					returnValue = URLDecoder.decode(returnValue, "UTF-8");
				} catch (UnsupportedEncodingException ignore) {
				}
			}
		} catch (JSONException ignore) {
			// refresh_url could be missing
		}
		return returnValue;
	}

	protected static boolean getChildBoolean(String str, Element elem) {
		String value = getTextContent(str, elem);
		return Boolean.valueOf(value);
	}

	protected static Date getChildDate(String str, Element elem)
			throws DianPingException {
		return getChildDate(str, elem, "EEE MMM d HH:mm:ss z yyyy");
	}

	protected static Date getChildDate(String str, Element elem, String format)
			throws DianPingException {
		return parseDate(getChildText(str, elem), format);
	}

	protected static Date parseDate(String str, String format)
			throws DianPingException {
		if (str == null || "".equals(str)) {
			return null;
		}
		SimpleDateFormat sdf = formatMap.get(format);
		if (null == sdf) {
			sdf = new SimpleDateFormat(format, Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			formatMap.put(format, sdf);
		}
		try {
			synchronized (sdf) {
				// SimpleDateFormat is not thread safe
				return sdf.parse(str);
			}
		} catch (ParseException pe) {
			throw new DianPingException("Unexpected format(" + str
					+ ") returned from sina.com.cn");
		}
	}

	protected static int getInt(String key, JSONObject json)
			throws JSONException {
		String str = json.getString(key);
		if (null == str || "".equals(str) || "null".equals(str)) {
			return -1;
		}
		return Integer.parseInt(str);
	}

	protected static long getLong(String key, JSONObject json)
			throws JSONException {
		String str = json.getString(key);
		if (null == str || "".equals(str) || "null".equals(str)) {
			return -1;
		}
		return Long.parseLong(str);
	}

	protected static boolean getBoolean(String key, JSONObject json)
			throws JSONException {
		String str = json.getString(key);
		if (null == str || "".equals(str) || "null".equals(str)) {
			return false;
		}
		return Boolean.valueOf(str);
	}

	public int getRateLimitLimit() {
		return rateLimitLimit;
	}

	public int getRateLimitRemaining() {
		return rateLimitRemaining;
	}

	public long getRateLimitReset() {
		return rateLimitReset;
	}

}
