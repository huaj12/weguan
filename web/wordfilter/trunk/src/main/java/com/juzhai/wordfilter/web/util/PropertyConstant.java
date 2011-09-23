/* 
 * PropertyConstant.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.util;

/**
 * 
 * @author xiaolin
 * 
 *         2008-5-8 create
 */
public class PropertyConstant {

	/**
	 * Region constant name.
	 */
	public static final String REGION_CHECKSTEP = "checkstep"; // Define steps
																// of
																// Filter'work

	public static final String REGION_APPCONFIG = "appconfig"; // Config how
																// many steps
																// application
																// need to do.

	public static final String REGION_PARAM = "param"; // Define all parameters
														// needed in the
														// checking steps.

	public static final String REGION_RESULT = "result"; // Define the result of
															// checking.

	public static final String REGION_SPAMTEXTDEADSCORE = "spamTextDeadScore"; // Define
																				// the
																				// upper
																				// limit
																				// of
																				// score
																				// on
																				// checking
																				// spam
																				// text.

	public static final String REGION_NOFILTERIP = "nofilterIP";

	/**
	 * parameter name .
	 */
	public static final String PARAM_SpamUserOverflowCount = "SpamUserOverflowCount";
	public static final String PARAM_SpamUserOverflowPeriodinMs = "SpamUserOverflowPeriodinMs";
	public static final String PARAM_SpamIPOverflowCount = "SpamIPOverflowCount";
	public static final String PARAM_SpamIPOverflowPeriodinMs = "SpamIPOverflowPeriodinMs";

	public static final String PARAM_MaxStringLength = "MaxStringLength";
	public static final String PARAM_MaxSequenceChinese = "MaxSequenceChinese";
	public static final String PARAM_MaxSequenceCharAndDigit = "MaxSequenceCharAndDigit";
	public static final String PARAM_SmallLineCount = "SmallLineCount";
	public static final String PARAM_NarrowTextRate = "NarrowTextRate";

	public static final String PARAM_SmallTextLength = "SmallTextLength";
	public static final String PARAM_MaxEmptyRate = "MaxEmptyRate";
	public static final String PARAM_SpamTextValve = "SpamTextValve";
	public static final String PARAM_LONGSPAMTEXT_VALUE = "LONGSPAMTEXT_VALUE";

	public static final String PARAM_MAX_TextLength = "MAX_TextLength";
	public static final String PARAM_MID_TextLength = "MID_TextLength";
	public static final String PARAM_MIN_TextLength = "MIN_TextLength";

	public static final String PARAM_ResponseCount = "ResponseCount";
	public static final String PARAM_MaxResponseBar = "MaxResponseBar";
	public static final String PARAM_MidResponseBar = "MidResponseBar";

	public static final String PARAM_SpamUserRepeatCount = "SpamUserRepeatCount";
	public static final String PARAM_SpamUserRepeatLength = "SpamUserRepeatLength";
	public static final String PARAM_UserMaxRepeat = "UserMaxRepeat";
	public static final String PARAM_SpamUserRepeatPeriod = "SpamUserRepeatPeriod";
	public static final String PARAM_RepeatUserBanPeriod = "RepeatUserBanPeriod";

	public static final String PARAM_SpamIpRepeatCount = "SpamIpRepeatCount";
	public static final String PARAM_IpMaxRepeat = "IpMaxRepeat";
	public static final String PARAM_SpamIpRepeatPeriod = "SpamIpRepeatPeriod";
	public static final String PARAM_RepeatIpBanPeriod = "RepeatIpBanPeriod";

	public static final String PARAM_MaxUserOverflowMap = "MaxUserOverflowMap";
	public static final String PARAM_MinUserOverflowMapLastCleanTime = "MinUserOverflowMapLastCleanTime";

	public static final String PARAM_MaxIpOverflowMap = "MaxIpOverflowMap";
	public static final String PARAM_MinIpOverflowMapLastCleanTime = "MinIpOverflowMapLastCleanTime";
	public static final String PARAM_MaxIpRepeatMap = "MaxIpRepeatMap";
	public static final String PARAM_MinIpRepeatMapLastCleanTime = "MinIpRepeatMapLastCleanTime";

	public static final String PARAM_MaxUserRepeatMap = "MaxUserRepeatMap";
	public static final String PARAM_MinUserRepeatMapLastCleanTime = "MinUserRepeatMapLastCleanTime";

}
