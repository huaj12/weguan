package com.juzhai.wordfilter.core;

import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.wordfilter.dataservice.ISpamDataService;
import com.juzhai.wordfilter.web.util.SpringContextUtil;

public class Filter {
	private static final Log log = LogFactory.getLog(Filter.class);

	private String[] spamAgentList; // read only
	private Set<Integer> spamUsers; // read only
	private Set<String> spamIPs; // read only
	private SpamHelper spamHelper; // read only

	private HashMap<Integer, Queue<Long>> userOverflowMap = new HashMap<Integer, Queue<Long>>(
			Config.MaxUserOverflowMap);
	private long userOverflowMapLastCleanTime = new Date().getTime();
	private HashMap<Integer, UserRepeatStruct> userRepeatMap = new HashMap<Integer, UserRepeatStruct>(
			Config.MaxUserRepeatMap);
	private long userRepeatMapLastCleanTime = new Date().getTime();
	private HashMap<String, Queue<Long>> ipOverflowMap = new HashMap<String, Queue<Long>>(
			Config.MaxIpOverflowMap);
	private long ipOverflowMapLastCleanTime = new Date().getTime();
	private HashMap<String, IpRepeatStruct> ipRepeatMap = new HashMap<String, IpRepeatStruct>(
			Config.MaxIpRepeatMap);
	private long ipRepeatMapLastCleanTime = new Date().getTime();

	enum FilterLevel {
		MIN, MID, MAX
	}

	private FilterLevel level;
	private Queue<Long> responseQueue = new ArrayDeque<Long>(
			Config.ResponseCount);
	static Filter instance = null;

	public static Filter getInstance() {
		if (instance != null)
			return instance;
		return init();
	}

	public void addSpamUser(int userId) {
		spamUsers.add(userId);
	}

	public void addSpamIp(String ip) {
		spamIPs.add(ip);
	}

	public static Filter init() {
		if (instance != null)
			return instance;
		instance = new Filter();
		instance.spamHelper = new SpamHelper();
		CharHelper.Init();// TODO initialization
		try {
			UserRepeatStruct.md = MessageDigest.getInstance("MD5");
			IpRepeatStruct.md = MessageDigest.getInstance("MD5");
			instance.spamHelper.Initial(((ISpamDataService) SpringContextUtil
					.getBean("spamDataService")).getSpamWords());
			if (log.isInfoEnabled()) {
				log.info("Spam words have been initialized successfully");
			}
		} catch (Throwable e) {
			log.error("initing filter error:" + e.getMessage(), e);
			instance = null;
			return null;
		}
		instance.spamUsers = ((ISpamDataService) SpringContextUtil
				.getBean("spamDataService")).getSpamUsers();
		if (log.isInfoEnabled()) {
			log.info("Spam user data have been initialized successfully");
		}
		instance.spamAgentList = new String[0];// TODO initialization
		instance.spamIPs = ((ISpamDataService) SpringContextUtil
				.getBean("spamDataService")).getSpamIPs();
		if (log.isInfoEnabled()) {
			log.info("Spam IP data have been initialized successfully");
		}
		return instance;
	}

	public int Check(byte[] text, int userId, String ip, String agent,
			int application) {
		long beginTime = new Date().getTime();
		int ret = check(text, userId, ip, agent, application, beginTime);
		AdjustLevel(new Date().getTime() - beginTime);
		return ret;
	}

	private Filter() {

	}

	// automatically adjust checking level.
	private void AdjustLevel(long time) {
		synchronized (responseQueue) {
			if (responseQueue.size() >= Config.ResponseCount) {
				long l = time - responseQueue.poll();
				if (l > Config.MID_TextLength)
					level = FilterLevel.MIN;
				else if (l > Config.MaxResponseBar)
					level = FilterLevel.MID;
				else
					level = FilterLevel.MAX;
			}
			responseQueue.add(time);
		}
	}

	private int check(byte[] text, int userId, String ip, String agent,
			int appId, long time) {
		// 1. check appId
		if (appId < 0 || appId >= Config.AppDo.length)
			return Config.RET_UnknownApp;

		CleanStringType cst = new CleanStringType();

		// 2. check the shape of the text and clean the text ( merge spaces, etc
		// )
		boolean isCheckUrl = DoStep(appId, Config.DoCheckOutsideUrl);
		boolean cleanResult = CleanString(text, isCheckUrl, cst, false);
		if (!cleanResult && DoStep(appId, Config.DoIsUglyText)) {
			return cst.retValue;
		}
		if (!cleanResult && isCheckUrl && cst.retValue == Config.RET_OutsideURL) {
			return cst.retValue;
		}

		int maxLength;
		int ret;
		// TODO for debug
		/*
		 * if ( level == FilterLevel.MIN ) maxLength = Config.MIN_TextLength;
		 * else if ( level == FilterLevel.MID ) maxLength =
		 * Config.MID_TextLength; else
		 */
		maxLength = Config.MAX_TextLength;

		// 3. check spam text
		if (DoStep(appId, Config.DoIsSpamText)) {
			ret = spamHelper.IsTextSpam(Config.getSpamTextDeadScore(appId),
					cst.clearString, cst.strLen, maxLength);
			if (ret != Config.RET_Pass)
				return ret;

		}

		// 4. check userId
		if (userId > 0) {
			// 4.1 check black user
			if (DoStep(appId, Config.DoIsSpamUser) && IsSpamUser(userId)) {
				return Config.RET_SpamUser;
			}
			// 4.2 check user who posts too quickly
			if (DoStep(appId, Config.DoIsSpamUserOverflow)
					&& IsSpamUserOverflow(userId, time, appId)) {
				return Config.RET_SpamUserOverflow;
			}
			// 4.3 check user who repeats text
			if (DoStep(appId, Config.DoIsSpamUserRepeat)
					&& Config.RET_Pass != (ret = IsSpamUserRepeat(userId, time,
							cst.clearString, cst.strLen))) {
				return ret;
			}
		}

		// 5. check IP
		if (ip != null && ip.length() > 0)// white IP
		{
			// 5.1 check black IP
			if (DoStep(appId, Config.DoIsSpamIp) && IsSpamIp(ip)) {
				return Config.RET_SpamIP;
			}

			if (!Config.isSafeIp(ip)) {
				// 5.2 check IP which posts too quickly
				if (DoStep(appId, Config.DoIsSpamIpOverflow)
						&& IsSpamIpOverflow(ip, time)) {
					return Config.RET_SpamIPOverflow;
				}

				// 5.3 check IP which repeats text
				if (DoStep(appId, Config.DoIsSpamIpRepeat)
						&& Config.RET_Pass != (ret = IsSpamIpRepeat(ip, time,
								cst.clearString, cst.strLen))) {
					return ret;
				}
			}
		}

		// 6. check agent
		// if ( agent != null && agent.length() > 0 &&
		// DoStep(appId, Config.DoIsSpamAgent) && IsSpamAgent(agent) )
		// {
		// return Config.RET_SpamAgent;
		// }

		return Config.RET_Pass;
	}

	private boolean DoStep(int appId, int step) {
		return (Config.AppDo[appId] & step) != 0;
	}

	private boolean IsSpamUser(int userId) {
		return spamUsers.contains(userId);
	}

	private boolean IsSpamUserOverflow(int userId, long time, int appId) {
		Queue<Long> q;
		boolean ret;
		synchronized (userOverflowMap) {
			if (userOverflowMap.containsKey(userId)) {
				q = userOverflowMap.get(userId);
				if (q.size() == Config.SpamUserOverflowCount) {
					long userOverflowLimit = 0;
					if (appId == 8)// if app is webchat,it'll get a new time.
					// This modification is temporary, and the
					// second edition will change the logic.
					{
						userOverflowLimit = 6000; // 6 seconds
					} else {
						userOverflowLimit = Config.SpamUserOverflowPeriodinMs;
					}
					ret = time - q.poll().longValue() < userOverflowLimit;
				} else {
					ret = false;
				}
			} else {
				userOverflowMap.put(userId, q = new ArrayDeque<Long>(
						Config.SpamUserOverflowCount));
				ret = false;

				// clean the map periodically
				if (level == FilterLevel.MAX
						&& time - userOverflowMapLastCleanTime > Config.MinUserOverflowMapLastCleanTime
						&& userOverflowMap.size() > Config.MaxUserOverflowMap) {
					ArrayList<Integer> deleteList = new ArrayList<Integer>(
							Config.MaxUserOverflowMap / 2);
					Iterator<Map.Entry<Integer, Queue<Long>>> i = userOverflowMap
							.entrySet().iterator();
					Map.Entry<Integer, Queue<Long>> entry;
					while (i.hasNext()) {
						entry = i.next();
						if (time - entry.getValue().peek() > Config.SpamUserOverflowPeriodinMs) {
							deleteList.add(entry.getKey());
						}
					}
					Iterator<Integer> j = deleteList.iterator();
					while (j.hasNext()) {
						userOverflowMap.remove(j.next());
					}
					userOverflowMapLastCleanTime = time;
				}
			}
			q.add(time);
		}
		return ret;
	}

	private boolean IsSpamIp(String ip) {
		return spamIPs.contains(ip);
	}

	private boolean IsSpamIpOverflow(String ip, long time) {
		Queue<Long> q;
		boolean ret;
		synchronized (ipOverflowMap) {
			if (ipOverflowMap.containsKey(ip)) {
				q = ipOverflowMap.get(ip);
				if (q.size() == Config.SpamIPOverflowCount) {
					ret = time - q.poll().longValue() < Config.SpamIPOverflowPeriodinMs;
				} else {
					ret = false;
				}
			} else {
				ipOverflowMap.put(ip, q = new LinkedList<Long>());
				ret = false;

				// clean the map periodically
				if (level == FilterLevel.MAX
						&& time - ipOverflowMapLastCleanTime > Config.MinIpOverflowMapLastCleanTime
						&& ipOverflowMap.size() > Config.MaxIpOverflowMap) {
					ArrayList<String> deleteList = new ArrayList<String>();
					Iterator<Map.Entry<String, Queue<Long>>> i = ipOverflowMap
							.entrySet().iterator();
					Map.Entry<String, Queue<Long>> entry;
					while (i.hasNext()) {
						entry = i.next();
						if (time - entry.getValue().peek() > Config.SpamIPOverflowPeriodinMs)
							deleteList.add(entry.getKey());
					}
					Iterator<String> j = deleteList.iterator();
					while (j.hasNext()) {
						ipOverflowMap.remove(j.next());
					}
					ipOverflowMapLastCleanTime = time;
				}
			}
			q.add(time);
		}
		return ret;
	}

	private int IsSpamUserRepeat(int userId, long time, byte[] text, int length) {
		if (length <= Config.SpamUserRepeatLength)
			return Config.RET_Pass;

		UserRepeatStruct ps;
		synchronized (userRepeatMap) {
			ps = userRepeatMap.get(userId);
			if (ps == null) {
				userRepeatMap.put(userId, new UserRepeatStruct(time, text,
						length));

				// clean the map periodically
				if (level == FilterLevel.MAX
						&& time - userRepeatMapLastCleanTime > Config.MinUserRepeatMapLastCleanTime
						&& userRepeatMap.size() > Config.MaxIpRepeatMap) {
					ArrayList<Integer> deleteList = new ArrayList<Integer>(
							Config.MaxIpRepeatMap / 2);
					Iterator<Map.Entry<Integer, UserRepeatStruct>> i = userRepeatMap
							.entrySet().iterator();
					Map.Entry<Integer, UserRepeatStruct> entry;
					UserRepeatStruct urs;
					while (i.hasNext()) {
						entry = i.next();
						urs = entry.getValue();
						if (urs.banned) {
							if (time - urs.time > Config.RepeatUserBanPeriod) {
								deleteList.add(entry.getKey());
							}
						} else {
							if (time - urs.time > Config.SpamUserRepeatPeriod) {
								deleteList.add(entry.getKey());
							}
						}

					}
					Iterator<Integer> j = deleteList.iterator();
					while (j.hasNext()) {
						userRepeatMap.remove(j.next());
					}
					userRepeatMapLastCleanTime = time;
				}
				return Config.RET_Pass;
			} else {
				return ps.Check(time, text, length);
			}
		}
	}

	private int IsSpamIpRepeat(String ip, long time, byte[] text, int length) {
		if (length <= Config.SpamUserRepeatLength)// this is not a bug.
			return Config.RET_Pass;

		IpRepeatStruct ps;
		synchronized (ipRepeatMap) {
			ps = ipRepeatMap.get(ip);
			if (ps == null) {
				ipRepeatMap.put(ip, new IpRepeatStruct(time, text, length));

				// clean the map periodically
				if (level == FilterLevel.MAX
						&& time - ipRepeatMapLastCleanTime > Config.MinIpRepeatMapLastCleanTime
						&& ipRepeatMap.size() > Config.MaxIpRepeatMap) {
					ArrayList<String> deleteList = new ArrayList<String>(
							Config.MaxIpRepeatMap / 2);
					Iterator<Map.Entry<String, IpRepeatStruct>> i = ipRepeatMap
							.entrySet().iterator();
					Map.Entry<String, IpRepeatStruct> entry;
					IpRepeatStruct irs;
					while (i.hasNext()) {
						entry = i.next();
						irs = entry.getValue();
						if (irs.banned) {
							if (time - irs.time > Config.RepeatIpBanPeriod) {
								deleteList.add(entry.getKey());
							}
						} else {
							if (time - irs.time > Config.SpamIpRepeatPeriod) {
								deleteList.add(entry.getKey());
							}
						}

					}
					Iterator<String> j = deleteList.iterator();
					while (j.hasNext()) {
						ipRepeatMap.remove(j.next());
					}
					ipRepeatMapLastCleanTime = time;
				}
				return Config.RET_Pass;
			} else {
				return ps.Check(time, text, length);
			}
		}
	}

	/*
	 * check the shape of the text and when the shape is acceptable, clean the
	 * text text - input ret - output ret.retValue - error code when the text is
	 * NOT acceptable ret.clearString - cleaned text when the text is acceptable
	 * ret.strLen - cleaned text length when the text is acceptable
	 * 
	 * return whether the shape of the text is acceptable
	 */
	public static boolean CleanString(byte[] text, boolean checkUrl,
			CleanStringType ret, boolean isIniting) {
		// check too long
		if (!isIniting && text.length > Config.MaxStringLength) {
			ret.retValue = Config.RET_TextTooLong;
			return false;
		}

		int strLen = text.length; // text length
		int validLength = strLen;
		if (strLen == 0) {
			ret.clearString = new byte[0];
			ret.strLen = 0;
			return true;
		}

		int charType; // type of the current char
		int preCharType = -2; // type of the preceding char
		int maxChLength = 0; // max Chinese character length
		int maxEnLength = 0; // max English and digital character length
		int brCount = 0; // line count
		int emptyCount = 0; // empty character count

		byte[] sb = new byte[strLen]; // cleaned text buffer
		int pSb = 0; // cleaned text length
		int termLength = 0; // current term length
		boolean bPreEmpty = false;
		byte b; // when the char is of single byte, current char
		// when the char is of double bytes, high byte

		byte b2 = 0; // when the char is of double bytes, low byte
		int s = 0; // current char when in single byte or in double bytes
		boolean bSingle; // whether the current char is of single byte
		boolean bEmpty; // whether the current char is empty char
		boolean bChinese = true;// whether the current term is in Chinese

		byte tmpB;

		URL_STATUS status = URL_STATUS.US_READY;
		int pBuffer = 0;
		byte[] urlBuffer;

		boolean isStartHttp = false;
		if (checkUrl) {
			urlBuffer = TakeBytes();
		} else
			urlBuffer = null;

		try {
			for (int i = 0; i < strLen; i++) {
				// take a byte
				b = text[i];
				if (b == '\r') {
					status = URL_STATUS.US_READY;
					validLength--;
					continue;
				}

				bSingle = (b >= 0);

				if (bSingle) {
					tmpB = b;
					charType = CharHelper.GetType(b);
					bEmpty = CharHelper.IsEmpty(b);
				} else {
					i++;
					if (i >= strLen)
						break;
					b2 = text[i];
					s = ((b & 255) << 8) | (b2 & 255);
					charType = CharHelper.GetType(s);
					bEmpty = CharHelper.IsEmpty(s);
					tmpB = CharHelper.Full2Half2(s);
				}

				if (!isIniting && checkUrl) {
					if (tmpB == 0) {
						if (status == URL_STATUS.US_NAME_X
								&& !CheckUrlIsTudou(urlBuffer, pBuffer)) {
							ret.retValue = Config.RET_OutsideURL;
							return false;
						}
						status = URL_STATUS.US_READY;
					} else {
						switch (status) {
						case US_READY:
							if (isStartHttp) {
								ret.retValue = Config.RET_OutsideURL;
								return false;
							}
							if (urlChar[tmpB] == ALPHA) {
								status = URL_STATUS.US_PROTOCOL;
								urlBuffer[0] = tmpB;
								pBuffer = 1;
							}
							break;
						case US_PROTOCOL:
							if (tmpB == ':') {
								status = URL_STATUS.US_COLON;
							} else if (tmpB == '.') {
								if (pBuffer >= URL_MAX_LENGTH) {
									status = URL_STATUS.US_READY;
								} else {
									status = URL_STATUS.US_DOT;
									urlBuffer[pBuffer++] = tmpB;
								}
							} else if (urlChar[tmpB] == ALPHA) {
								if (pBuffer >= URL_MAX_LENGTH) {
									status = URL_STATUS.US_READY;
								} else {
									urlBuffer[pBuffer++] = tmpB;
								}
							} else {
								status = URL_STATUS.US_READY;
							}
							break;
						case US_COLON:
							if (tmpB == '/') {
								status = URL_STATUS.US_SLASH1;
							} else if (urlChar[tmpB] == ALPHA)// the case:
							// "url:http://abc.com
							{
								status = URL_STATUS.US_NAME1;
								urlBuffer[0] = tmpB;
								pBuffer = 1;
							} else {
								status = URL_STATUS.US_READY;
							}
							break;
						case US_SLASH1:
							if (tmpB == '/') {
								status = URL_STATUS.US_SLASH2;
								// 88888888888888888888888888888888888888
								if (pBuffer == 4) {
									isStartHttp = (urlBuffer[0] == 'H' || urlBuffer[0] == 'h')
											&& (urlBuffer[1] == 'T' || urlBuffer[1] == 't')
											&& (urlBuffer[2] == 'T' || urlBuffer[2] == 't')
											&& (urlBuffer[3] == 'P' || urlBuffer[3] == 'p');

								}
								// 8888888888888888888888888888888888888
							} else if (urlChar[tmpB] == ALPHA)// the case:
							// "url:/http://abc.com"
							{
								status = URL_STATUS.US_NAME1;
								urlBuffer[0] = tmpB;
								pBuffer = 1;
							} else {
								status = URL_STATUS.US_READY;
							}
							break;
						case US_SLASH2:
							if (urlChar[tmpB] == ALPHA)// the case:
							// "url:/http://abc.com"
							{
								status = URL_STATUS.US_NAME1;
								urlBuffer[0] = tmpB;
								pBuffer = 1;
							} else {
								status = URL_STATUS.US_READY;
							}
							break;
						case US_NAME1:
							if (tmpB == '.') {
								if (pBuffer >= URL_MAX_LENGTH) {
									status = URL_STATUS.US_READY;
								} else {
									status = URL_STATUS.US_DOT;
									urlBuffer[pBuffer++] = tmpB;
								}
							} else if (urlChar[tmpB] == ALPHA) {
								if (pBuffer >= URL_MAX_LENGTH) {
									status = URL_STATUS.US_READY;
								} else {
									status = URL_STATUS.US_NAME1;
									urlBuffer[pBuffer++] = tmpB;
								}
							} else {
								status = URL_STATUS.US_READY;
							}
							break;
						case US_DOT:
							if (urlChar[tmpB] == ALPHA
									&& pBuffer < URL_MAX_LENGTH) {
								status = URL_STATUS.US_NAME_X;
								urlBuffer[pBuffer++] = tmpB;
							} else {
								status = URL_STATUS.US_READY;
							}
							break;
						case US_NAME_X:
							if (tmpB == '.') {
								if (pBuffer >= URL_MAX_LENGTH) {
									status = URL_STATUS.US_READY;
								} else {
									status = URL_STATUS.US_DOT;
									urlBuffer[pBuffer++] = tmpB;
								}
								break;
							} else {
								if (urlChar[tmpB] == ALPHA) {
									if (pBuffer >= URL_MAX_LENGTH) {
										status = URL_STATUS.US_READY;
									} else {
										urlBuffer[pBuffer++] = tmpB;
									}
									break;
								} else if (urlChar[tmpB] == LEGAL) {
									if (!CheckUrlIsTudou(urlBuffer, pBuffer)) {
										ret.retValue = Config.RET_OutsideURL;
										return false;
									} else {
										isStartHttp = false;
									}
									status = URL_STATUS.US_TAIL;
									break;
								}
							}
							// other
							if (!CheckUrlIsTudou(urlBuffer, pBuffer)) {
								ret.retValue = Config.RET_OutsideURL;
								return false;
							} else {
								isStartHttp = false;
							}
							status = URL_STATUS.US_READY;
							break;
						case US_TAIL:
							if (!(urlChar[tmpB] == ALPHA || urlChar[tmpB] == LEGAL)) {
								status = URL_STATUS.US_READY;
							}
							break;
						default:
							break;
						}
					}
				}

				if (bEmpty) {
					emptyCount++;
					bPreEmpty = true;
				} else {
					if (bPreEmpty) {
						if ((charType == CharHelper.CT_DigitAndCharHalf || charType == CharHelper.CT_DigitAndCharFull)
								&& bChinese) {
							sb[pSb++] = ' ';
						}
						bPreEmpty = false;
					}
					if (bSingle) {
						sb[pSb++] = b;
						bChinese = false;
					} else {
						if (charType >= CharHelper.CT_SymbolFull
								&& charType <= CharHelper.CT_DigitAndCharFull) {
							sb[pSb++] = CharHelper.Full2Half(s);
							bChinese = false;
						} else {
							sb[pSb++] = b;
							sb[pSb++] = b2;
							bChinese = true;
						}
					}
				}

				if (charType == CharHelper.CT_Return)
					brCount++;

				if (preCharType != charType) {
					if (preCharType == CharHelper.CT_Chinese) {
						if (maxChLength < termLength) {
							maxChLength = termLength;
							if (!isIniting
									&& maxChLength > Config.MaxSequenceChinese) {
								ret.retValue = Config.RET_ChineseTooLong;
								return false;
							}
						}
					} else {
						if (maxEnLength < termLength) {
							maxEnLength = termLength;
							if (!isIniting
									&& maxEnLength > Config.MaxSequenceCharAndDigit) {
								ret.retValue = Config.RET_CharAndDigitialTooLong;
								return false;
							}
						}
					}
					termLength = bSingle ? 1 : 2;
					preCharType = charType;
				} else {
					termLength += bSingle ? 1 : 2;
				}
			}
			if (!isIniting && isStartHttp) {
				ret.retValue = Config.RET_OutsideURL;
				return false;
			}
			if (!isIniting && checkUrl && status == URL_STATUS.US_NAME_X
					&& !CheckUrlIsTudou(urlBuffer, pBuffer)) {
				ret.retValue = Config.RET_OutsideURL;
				return false;
			}
		} finally {
			if (urlBuffer != null)
				BackBytes(urlBuffer);
		}

		ret.clearString = sb;
		ret.strLen = pSb;

		if (preCharType == CharHelper.CT_Chinese) {
			if (!isIniting && termLength > Config.MaxSequenceChinese) {
				ret.retValue = Config.RET_ChineseTooLong;
				return false;
			}
		} else {
			if (!isIniting && termLength > Config.MaxSequenceCharAndDigit) {
				ret.retValue = Config.RET_CharAndDigitialTooLong;
				return false;
			}
		}

		if (!isIniting
				&& brCount > Config.SmallLineCount
				&& (float) validLength / (float) brCount < Config.NarrowTextRate) {
			ret.retValue = Config.RET_ShapeTooNarrow;
			return false;
		}

		if (!isIniting
				&& (float) emptyCount / (float) strLen > Config.MaxEmptyRate) {
			ret.retValue = Config.RET_TooManyEmpties;
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private boolean IsSpamAgent(String agent) {
		for (int i = 0; i < spamAgentList.length; i++) {
			if (agent.indexOf(spamAgentList[i]) >= 0)
				return true;
		}
		return false;
	}

	/**
	 * update all old spam data cached in memory with new spam data .
	 * 
	 * @param spamAgentList
	 *            --types of browser that be prohibited
	 * @param spamUsers
	 *            --users prohibited
	 * @param spamIPs
	 *            --ips prohibited
	 * @param spamWords
	 *            --illegal words
	 */
	public void updateSpamData(String[] spamAgentList, Set<Integer> spamUsers,
			Set<String> spamIPs, List<SpamStruct> spamWords) {
		boolean result = instance.spamHelper.Initial(spamWords);
		if (log.isInfoEnabled()) {
			if (result)
				log.info("Spam words have been updated successfully");
			else
				log.error("initialization of Spam words failed");
		}
		instance.spamUsers = spamUsers;
		if (log.isInfoEnabled()) {
			log.info("Spam user data have been updated successfully");
		}
		// this property is not used currently.
		// instance.spamAgentList=spamAgentList;
		instance.spamIPs = spamIPs;
		if (log.isInfoEnabled()) {
			log.info("Spam IP data have been updated successfully");

			log.info("System has finished updating spam data successfully!");
		}
	}

	public void releaseMemory() {
		userOverflowMap = new HashMap<Integer, Queue<Long>>(
				Config.MaxUserOverflowMap);
		userRepeatMap = new HashMap<Integer, UserRepeatStruct>(
				Config.MaxUserRepeatMap);
		ipOverflowMap = new HashMap<String, Queue<Long>>(
				Config.MaxIpOverflowMap);
		ipRepeatMap = new HashMap<String, IpRepeatStruct>(Config.MaxIpRepeatMap);
		if (log.isInfoEnabled()) {
			log.info("Release memory successfully!");
		}
	}

	/**
	 * Check whether the specified text is contained in white domain.
	 * 
	 * @param bytes
	 *            the text should be checked
	 * @return Return true if the domain is illegal, otherwise return false.
	 */
	public static boolean checkIllegalDomain(byte[] bytes, int length) {
		String domain = new String(bytes, 0, length, Config.CharSetInstance)
				.toUpperCase();
		boolean illegalDomain = false;
		for (String td : Config.TOP_DOMAINS) {
			if (domain.endsWith(td)) {
				illegalDomain = true;
				break;
			}
		}
		if (!illegalDomain)
			return false;

		boolean whiteDomain = false;
		for (String wd : Config.WHITE_DOMAINS) {
			if (domain.endsWith(wd)) {
				whiteDomain = true;
				break;
			}
		}
		return !whiteDomain;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Filter filter;
		// filter = init();
		//
		// if ( filter == null )
		// {
		// return;
		// }
		// String line;
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(System.in));
		// StringBuilder sb = new StringBuilder();
		// while(true)
		// {
		// try {
		// line = reader.readLine();
		// if ( line.equals("EXIT") )
		// {
		// break;
		// }
		// if ( line .equals( "OVER" ) )
		// {
		// String s = sb.toString();
		// byte[] b = s.getBytes(Config.CharSetInstance);
		// int ret = filter.Check(b, -1, null, null, 1);
		// if ( ret == Config.RET_Pass )
		// {
		// System.out.println("yes!");
		// }
		// else
		// {
		// System.out.printf("No! %d \n", ret);
		// }
		// sb.setLength(0);
		// }
		// else
		// {
		// sb.append(line).append('\n');
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// break;
		// }
		// }

		CharHelper.Init();

		test("w.m你好");
		test("w.Ｂ你好");
		test("w.Ｂ");
		test("你好w.Ｂ");
		test("你好 w.Ｂ");
		test("你好 w.Ｂ 你好");
		test("你好w.Ｂ你好");
		test("你好 tudou。com 你好");
		test("你好 tudou。c2om 你好");
	}

	private static void test(String s) {
		byte[] bytes = s.getBytes(Config.CharSetInstance);
		CleanStringType ret = new CleanStringType();
		boolean b = CleanString(bytes, true, ret, false);
		System.out.println("b=" + b + ", ret=" + ret.retValue);
	}

	private static boolean CheckUrlIsTudou(byte[] bytes, int length) {
		// for debug - begin
		// String str = new String(bytes, 0, length, Config.CharSetInstance);
		// System.out.println("url is "+str);
		// for debug - end
		return !checkIllegalDomain(bytes, length);
		// if ( length >= 9)
		// {
		// return
		// ( bytes[length-9]=='t' || bytes[length-9]=='T' ) &&
		// ( bytes[length-8]=='u' || bytes[length-8]=='U' ) &&
		// ( bytes[length-7]=='d' || bytes[length-7]=='D' ) &&
		// ( bytes[length-6]=='o' || bytes[length-6]=='O' ) &&
		// ( bytes[length-5]=='u' || bytes[length-5]=='U' ) &&
		// ( bytes[length-4]=='.' ) &&
		// ( bytes[length-3]=='c' || bytes[length-3]=='C' ) &&
		// ( bytes[length-2]=='o' || bytes[length-2]=='O' ) &&
		// ( bytes[length-1]=='m' || bytes[length-1]=='M' ) && (length==9 ||
		// bytes[length-10] == '.' ) ;
		// }
		//
		// return false;
	}

	private static void InitUrlChecker() {
		urlChar = new byte[Short.MAX_VALUE + 1];
		Arrays.fill(urlChar, OTHER);
		for (int i = 'a'; i <= 'z'; i++)
			urlChar[i] = ALPHA;
		for (int i = 'A'; i <= 'Z'; i++)
			urlChar[i] = ALPHA;
		for (int i = '0'; i <= '9'; i++)
			urlChar[i] = ALPHA;
		// urlChar['']
		urlChar[':'] = urlChar['/'] = urlChar['?'] = urlChar['#'] = urlChar['['] = urlChar[']'] = urlChar['@'] = urlChar['!'] = urlChar['$'] = urlChar['&'] = urlChar['\''] = urlChar['('] = urlChar[')'] = urlChar['*'] = urlChar['+'] = urlChar[','] = urlChar[';'] = urlChar['='] = urlChar['-'] = urlChar['.'] = urlChar['_'] = urlChar['~'] = urlChar['%'] = LEGAL;
	}

	private static final int URL_MAX_LENGTH = 100;

	private static byte[] TakeBytes() {
		synchronized (byteses) {
			if (!byteses.isEmpty())
				return byteses.poll();
		}
		return new byte[URL_MAX_LENGTH];
	}

	private static void BackBytes(byte[] bytes) {
		synchronized (byteses) {
			byteses.add(bytes);
		}
	}

	private static Queue<byte[]> byteses = new ArrayDeque<byte[]>();

	private final static byte OTHER = 0;
	private final static byte ALPHA = 1;
	private final static byte LEGAL = 2;

	static {
		InitUrlChecker();
	}

	private static byte[] urlChar; // readonly

	enum URL_STATUS {
		US_READY, US_PROTOCOL, US_COLON, US_SLASH1, US_SLASH2, US_NAME1, US_DOT, US_NAME_X, US_TAIL,
	}
}