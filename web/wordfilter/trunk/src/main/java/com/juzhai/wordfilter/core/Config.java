package com.juzhai.wordfilter.core;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.juzhai.wordfilter.web.util.PropertiesInfo;
import com.juzhai.wordfilter.web.util.PropertyConstant;

public class Config {
	static public final int DoIsSpamUser = 1; // black user check
	static public final int DoIsSpamUserOverflow = (1 << 1); // user post too
																// quickly check
	static public final int DoIsSpamIp = (1 << 2); // black IP check
	static public final int DoIsSpamIpOverflow = (1 << 3); // IP post too
															// quickly check
	static public final int DoIsSpamAgent = (1 << 4); // black agent check
	static public final int DoIsUglyText = (1 << 5); // text shape check
	static public final int DoIsSpamText = (1 << 6); // spam text check
	static public final int DoIsSpamUserRepeat = (1 << 7); // user repeat text
															// check
	static public final int DoIsSpamIpRepeat = (1 << 8); // IP repeat text check
	static public final int DoCheckOutsideUrl = (1 << 9); // check outside url
															// (not tudou.com)
	static public final int DoAll = 0xFFFFFFFF; // check all

	public static Map<String, Integer> stepMap = new HashMap<String, Integer>();
	static {
		stepMap.put("DoIsSpamUser", DoIsSpamUser);
		stepMap.put("DoIsSpamUserOverflow", DoIsSpamUserOverflow);
		stepMap.put("DoIsSpamIp", DoIsSpamIp);
		stepMap.put("DoIsSpamIpOverflow", DoIsSpamIpOverflow);
		stepMap.put("DoIsSpamAgent", DoIsSpamAgent);
		stepMap.put("DoIsUglyText", DoIsUglyText);
		stepMap.put("DoIsSpamText", DoIsSpamText);
		stepMap.put("DoIsSpamUserRepeat", DoIsSpamUserRepeat);
		stepMap.put("DoIsSpamIpRepeat", DoIsSpamIpRepeat);
		stepMap.put("DoCheckOutsideUrl", DoCheckOutsideUrl);
		stepMap.put("DoAll", DoAll);
	}
	static public int[] AppDo = PropertiesInfo.getInstance().getAppConfig();
	/*
	 * all the client applications should be listed in this array
	 */
	// static public final int[] AppDo = { // appId
	// DoAll, // 0 - program
	// DoAll, // 1 - space
	// DoAll, // 2 - bbs
	// DoAll, // 3 - group
	// DoAll, // 4 - playlist
	// DoAll, // 5 - tag
	// DoIsSpamIp|DoIsUglyText|DoIsSpamText, // 6 - name
	// DoAll, // 7 - photo
	// DoIsSpamUser|DoIsSpamIp|DoIsUglyText|DoIsSpamText, // 8 - blog
	// DoIsSpamUser|DoIsSpamIp|DoIsSpamUserOverflow|DoIsSpamText, // 9 - webchat
	// DoAll, // 10 -
	// DoAll // 11 -
	// };

	// text submitted by the application should be in GBK char set
	static public Charset CharSetInstance = Charset.forName("GBK");

	// the period of the recent 3 posts by a user can't be shorter than 30
	// second
	static public int SpamUserOverflowCount = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SpamUserOverflowCount);
	static public long SpamUserOverflowPeriodinMs = PropertiesInfo
			.getInstance().getLongParamValue(
					PropertyConstant.PARAM_SpamUserOverflowPeriodinMs);

	// the period of the recent 3 posts by an IP can't be shorter than 20 second
	static public int SpamIPOverflowCount = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SpamIPOverflowCount);
	static public long SpamIPOverflowPeriodinMs = PropertiesInfo.getInstance()
			.getLongParamValue(PropertyConstant.PARAM_SpamIPOverflowPeriodinMs);

	// text can't be longer than 1024 bytes
	static public int MaxStringLength = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MaxStringLength);

	// Chinese term in the text can't be longer than 80 bytes
	static public int MaxSequenceChinese = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MaxSequenceChinese);

	// Non-Chinese term in the text can't be longer than 80 bytes
	static public int MaxSequenceCharAndDigit = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MaxSequenceCharAndDigit);

	// when the number of the lines of a text is bigger than 5,
	// each line length should be longer than 4 bytes on average.
	static public int SmallLineCount = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SmallLineCount);
	static public float NarrowTextRate = PropertiesInfo.getInstance()
			.getFloatParamValue(PropertyConstant.PARAM_NarrowTextRate);

	// when the text length is longer than 40 bytes,
	// the empty character ratio should be smaller than 0.6
	static public int SmallTextLength = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SmallTextLength);
	static public float MaxEmptyRate = PropertiesInfo.getInstance()
			.getFloatParamValue(PropertyConstant.PARAM_MaxEmptyRate);

	// If the accumulate spam score of a text is bigger than 9, it is spam!
	static public int SpamTextValve = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SpamTextValve);

	// If the text is too long , it'll use this value as checking score
	static public int LONGSPAMTEXT_VALUE = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_LONGSPAMTEXT_VALUE);
	// text length checking on different filter level
	//
	// MAX - 1000
	// MID - 200
	// MIN - 0
	static public int MAX_TextLength = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MAX_TextLength);
	static public int MID_TextLength = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MID_TextLength);
	static public int MIN_TextLength = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MIN_TextLength);

	// the average response time to the recent 5 request
	// --------------------------------
	// | response time | filter level |
	// --------------------------------
	// | < 25 ms | MAX |
	// | 25 -- 50 ms | MID |
	// | > 50 | MIN |
	// --------------------------------
	static public int ResponseCount = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_ResponseCount);
	static public long MaxResponseBar = PropertiesInfo.getInstance()
			.getLongParamValue(PropertyConstant.PARAM_MaxResponseBar);
	static public long MidResponseBar = PropertiesInfo.getInstance()
			.getLongParamValue(PropertyConstant.PARAM_MidResponseBar);

	// if each unique text (only the 5 recent ones are recorded),
	// whose length is bigger than 10 bytes,
	// is posted by a user for 5 times in recent 30 minutes,
	// the user will be banned in the next 8 hours
	static public int SpamUserRepeatCount = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SpamUserRepeatCount);
	static public int SpamUserRepeatLength = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SpamUserRepeatLength);
	static public int UserMaxRepeat = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_UserMaxRepeat);
	static public long SpamUserRepeatPeriod = PropertiesInfo.getInstance()
			.getLongParamValue(PropertyConstant.PARAM_SpamUserRepeatPeriod);
	static public long RepeatUserBanPeriod = PropertiesInfo.getInstance()
			.getLongParamValue(PropertyConstant.PARAM_RepeatUserBanPeriod);

	// if each unique text (only the 10 recent ones are recorded),
	// whose length is bigger than 10 bytes,
	// is posted by an IP for 10 times in recent 30 minutes,
	// the user will be banned in the next 4 hours
	static public int SpamIpRepeatCount = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_SpamIpRepeatCount);
	// static public final int SpamIpRepeatLength = 10; this should be the same
	// as the user one
	static public int IpMaxRepeat = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_IpMaxRepeat);
	static public long SpamIpRepeatPeriod = PropertiesInfo.getInstance()
			.getLongParamValue(PropertyConstant.PARAM_SpamIpRepeatPeriod);
	static public long RepeatIpBanPeriod = PropertiesInfo.getInstance()
			.getLongParamValue(PropertyConstant.PARAM_RepeatIpBanPeriod);

	// when the size of the userOverflowMap is larger than 1M, it will be
	// cleaned
	// but the cleaning willn't be done in the next 1 hour
	static public int MaxUserOverflowMap = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MaxUserOverflowMap);
	static public long MinUserOverflowMapLastCleanTime = PropertiesInfo
			.getInstance().getLongParamValue(
					PropertyConstant.PARAM_MinUserOverflowMapLastCleanTime);

	static public int MaxIpOverflowMap = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MaxIpOverflowMap);
	static public long MinIpOverflowMapLastCleanTime = PropertiesInfo
			.getInstance().getLongParamValue(
					PropertyConstant.PARAM_MinIpOverflowMapLastCleanTime);

	static public int MaxIpRepeatMap = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MaxIpRepeatMap);
	static public long MinIpRepeatMapLastCleanTime = PropertiesInfo
			.getInstance().getLongParamValue(
					PropertyConstant.PARAM_MinIpRepeatMapLastCleanTime);

	static public int MaxUserRepeatMap = PropertiesInfo.getInstance()
			.getIntParamValue(PropertyConstant.PARAM_MaxUserRepeatMap);
	static public long MinUserRepeatMapLastCleanTime = PropertiesInfo
			.getInstance().getLongParamValue(
					PropertyConstant.PARAM_MinUserRepeatMapLastCleanTime);

	// the character in the array will be regards as empty character
	static public char[] EmptyChars = new char[] { ' ', '¡¡', '/', '|', '.',
			'=', '[', ']', '{', '}', '\n', ',', '#', '`', '^', '-', '+', '~',
			'*', '$', };
	static public final String[] TOP_DOMAINS = new String[] { ".AC", ".AD",
			".AE", ".AERO", ".AF", ".AG", ".AI", ".AL", ".AM", ".AN", ".AO",
			".AQ", ".AR", ".ARPA", ".AS", ".ASIA", ".AT", ".AU", ".AW", ".AX",
			".AZ", ".BA", ".BB", ".BD", ".BE", ".BF", ".BG", ".BH", ".BI",
			".BIZ", ".BJ", ".BL", ".BM", ".BN", ".BO", ".BR", ".BS", ".BT",
			".BV", ".BW", ".BY", ".BZ", ".CA", ".CAT", ".CC", ".CD", ".CF",
			".CG", ".CH", ".CI", ".CK", ".CL", ".CM", ".CN", ".CO", ".COM",
			".COOP", ".CR", ".CU", ".CV", ".CX", ".CY", ".CZ", ".DE", ".DJ",
			".DK", ".DM", ".DO", ".DZ", ".EC", ".EDU", ".EE", ".EG", ".EH",
			".ER", ".ES", ".ET", ".EU", ".FI", ".FJ", ".FK", ".FM", ".FO",
			".FR", ".GA", ".GB", ".GD", ".GE", ".GF", ".GG", ".GH", ".GI",
			".GL", ".GM", ".GN", ".GOV", ".GP", ".GQ", ".GR", ".GS", ".GT",
			".GU", ".GW", ".GY", ".HK", ".HM", ".HN", ".HR", ".HT", ".HU",
			".ID", ".IE", ".IL", ".IM", ".IN", ".INFO", ".INT", ".IO", ".IQ",
			".IR", ".IS", ".IT", ".JE", ".JM", ".JO", ".JOBS", ".JP", ".KE",
			".KG", ".KH", ".KI", ".KM", ".KN", ".KP", ".KR", ".KW", ".KY",
			".KZ", ".LA", ".LB", ".LC", ".LI", ".LK", ".LR", ".LS", ".LT",
			".LU", ".LV", ".LY", ".MA", ".MC", ".MD", ".ME", ".MF", ".MG",
			".MH", ".MIL", ".MK", ".ML", ".MM", ".MN", ".MO", ".MOBI", ".MP",
			".MQ", ".MR", ".MS", ".MT", ".MU", ".MUSEUM", ".MV", ".MW", ".MX",
			".MY", ".MZ", ".NA", ".NAME", ".NC", ".NE", ".NET", ".NF", ".NG",
			".NI", ".NL", ".NO", ".NP", ".NR", ".NU", ".NZ", ".OM", ".ORG",
			".PA", ".PE", ".PF", ".PG", ".PH", ".PK", ".PL", ".PM", ".PN",
			".PR", ".PRO", ".PS", ".PT", ".PW", ".PY", ".QA", ".RE", ".RO",
			".RS", ".RU", ".RW", ".SA", ".SB", ".SC", ".SD", ".SE", ".SG",
			".SH", ".SI", ".SJ", ".SK", ".SL", ".SM", ".SN", ".SO", ".SR",
			".ST", ".SU", ".SV", ".SY", ".SZ", ".TC", ".TD", ".TEL", ".TF",
			".TG", ".TH", ".TJ", ".TK", ".TL", ".TM", ".TN", ".TO", ".TP",
			".TR", ".TRAVEL", ".TT", ".TV", ".TW", ".TZ", ".UA", ".UG", ".UK",
			".UM", ".US", ".UY", ".UZ", ".VA", ".VC", ".VE", ".VG", ".VI",
			".VN", ".VU", ".WF", ".WS", ".YE", ".YT", ".YU", ".ZA", ".ZM",
			".ZW" };
	static public final String[] WHITE_DOMAINS = new String[] { "TUDOU.COM",
			"TOODOU.COM" };
	static public final int RET_Pass = 0;
	static public final int RET_SpamUser = -1;
	static public final int RET_SpamUserOverflow = -2;
	static public final int RET_SpamIP = -3;
	static public final int RET_SpamIPOverflow = -4;
	static public final int RET_SpamAgent = -5;
	static public final int RET_TextTooLong = -6;
	static public final int RET_ChineseTooLong = -7;
	static public final int RET_CharAndDigitialTooLong = -8;
	static public final int RET_ShapeTooNarrow = -9;
	static public final int RET_TooManyEmpties = -10;
	static public final int RET_SpamText = -11;
	static public final int RET_RepeatUser = -12;
	static public final int RET_RepeatIp = -13;
	static public final int RET_RepeatBannedUser = -14;
	static public final int RET_RepeatBannedIp = -15;
	static public final int RET_OutsideURL = -16;
	static public final int RET_UnknownApp = -99;

	static public final int RET_NoTxtParameter = -100;

	public static final int RET_UserForbidden_OK = 0;
	public static final int RET_IpForbidend_OK = 0;
	public static final int RET_UserForbidden_Failed = -1000;
	public static final int RET_IpForbidend_Failed = -2000;

	/**
	 * This variable indicates whether system should record the illegal data
	 * which is sent by user. The default value is true , which means recording
	 * the log.
	 */
	public static boolean isRecordLog = true;

	private static Map<Integer, Integer> deadScoreMap = PropertiesInfo
			.getInstance().getAppDeadScoreConfig();

	public static int getSpamTextDeadScore(int app) {
		Integer score = deadScoreMap.get(app);
		if (score == null)
			score = SpamTextValve;

		return score;
	}

	private static Set<String> saftIPs = null;
	static {
		saftIPs = getNoFilterIP();
		// saftIPs.add("220.181.40.45");
		// saftIPs.add("220.181.45.69");
		// saftIPs.add("123.151.165.97");
		// saftIPs.add("123.151.165.1");
		// saftIPs.add("123.151.165.2");
		// saftIPs.add("123.151.165.3");
		// saftIPs.add("123.151.165.4");
		// saftIPs.add("123.151.165.5");
		// saftIPs.add("123.151.165.6");
		// saftIPs.add("123.151.165.7");
		// saftIPs.add("222.66.106.203");
	}

	public static boolean isSafeIp(String ip) {
		if (ip == null || ip.length() < 1)
			return true;

		return saftIPs.contains(ip);
	}

	private static Set<String> getNoFilterIP() {
		Map<String, String> map = PropertiesInfo.getInstance().get(
				PropertyConstant.REGION_NOFILTERIP);
		if (map == null) {
			return new HashSet<String>();
		}
		Collection<String> values = map.values();
		if (values == null) {
			return new HashSet<String>();
		} else {
			return new HashSet<String>(values);
		}
	}
}