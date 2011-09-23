/* 
 * FilterAnalyzer.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.juzhai.wordfilter.core.CharHelper;
import com.juzhai.wordfilter.core.CleanStringType;
import com.juzhai.wordfilter.core.Config;
import com.juzhai.wordfilter.core.Filter;
import com.juzhai.wordfilter.core.MultiWordsChecker;
import com.juzhai.wordfilter.core.MultiWordsItem;
import com.juzhai.wordfilter.core.SpamStruct;
import com.juzhai.wordfilter.dataservice.ISpamDataService;

/**
 * @author xiaolin
 * 
 *         2008-5-14 create
 */
public class FilterAnalyzer {

	private static final Logger logger = Logger.getLogger(FilterAnalyzer.class);
	private static FilterAnalyzer analyzer;

	public static FilterAnalyzer getInstance() {
		if (analyzer == null)
			analyzer = new FilterAnalyzer();
		return analyzer;
	}

	private FilterAnalyzer() {
		try {
			CharHelper.Init();

			Initial(((ISpamDataService) SpringContextUtil
					.getBean("spamDataService")).getSpamWords());
			if (logger.isInfoEnabled()) {
				logger.info("Update spam words successfully!");
			}
		} catch (Exception e) {
			logger.error("Updating spam words failed!" + e.getMessage(), e);
		}

		launchTask();
	}

	public List<Token> analyze(byte[] text) {
		CleanStringType cst = new CleanStringType();

		boolean result = Filter.CleanString(text, false, cst, true);

		List<Token> res = analyzer.getTextSpam(0, cst.clearString, cst.strLen,
				1000000);
		return res;
	}

	private void launchTask() {
		Timer timer = new Timer("update spam data", true);
		long period = 1000 * 60; // one minute
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					Initial(((ISpamDataService) SpringContextUtil
							.getBean("spamDataService")).getSpamWords());
					if (logger.isInfoEnabled()) {
						logger.info("Update spam words successfully!");
					}
				} catch (Exception e) {
					logger.error(
							"Updating spam words failed!" + e.getMessage(), e);
				}

			}
		}, period, period);
		if (logger.isInfoEnabled()) {
			logger.info("The task updating spam data every one minute has been launched!");
		}
	}

	private static class Node {
		Node(byte[] b, int begin, int l) {
			bytes = b;
			_length = l;
			this._begin = begin;
			_hashCode();
		}

		Node() {
			// System.out.println("node");
			multiWords = new HashSet<Integer>(16, 0.5f);
		}

		public Set<Integer> multiWords;
		public byte[] bytes;

		public void Set(int l, int b) {
			_length = l;
			_begin = b;
			_hashCode();
		}

		private int _length;
		private int _begin;

		private int _hash;

		private void _hashCode() {
			if (_length >= 2) {
				_hash = ((bytes[_begin] & 255) << 24)
						| ((bytes[_begin + 1] & 255) << 16)
						| ((bytes[_length - 1 + _begin] & 255) << 8)
						| (bytes[_length - 2 + _begin] & 255);
			} else if (_length == 1) {
				_hash = bytes[_begin];
			} else {
				_hash = 0;
			}

		}

		public int hashCode() {
			return _hash;
		}

		public boolean equals(Object obj) {
			Node node = (Node) obj;
			if (_length == node._length) {
				for (int i = 0; i < _length; i++) {
					if (bytes[_begin + i] != node.bytes[node._begin + i])
						return false;
				}
				return true;
			}
			return false;
		}

		static public Node Take() {
			synchronized (nodes) {
				if (!nodes.isEmpty())
					return nodes.poll();
			}
			return new Node();
		}

		static public void Back(Node node) {
			node.multiWords.clear();
			synchronized (nodes) {
				nodes.add(node);
			}
		}

		static private Queue<Node> nodes = new ArrayDeque<Node>();
	}

	class IdAndScore {
		public int id;
		public int score;
	}

	public List<Token> getTextSpam(int deadScore, byte[] text, int length,
			int maxLength) {

		List<Token> list = new ArrayList<Token>();
		if (text == null || length == 0) {
			return list;
		}

		int score = 0;
		int n;
		byte b;
		int s;
		int tempScore;
		int begin;
		IdAndScore ias;
		if (maxLength < length) {
			length = maxLength;
		}

		Node node = Node.Take();
		node.bytes = text;

		for (int i = 0; i < length; i++) {
			b = text[begin = i];
			if (b >= 0)
				s = b;
			else {
				i++;
				if (i >= length)
					break;
				s = ((b & 255) << 8) | (text[i] & 255);
			}

			n = spamFirstWord[s];
			if (n > 0 && length >= begin + n) {
				node.Set(n, begin);
				tempScore = 0;
				Token token = null;
				while (spamMap.containsKey(node)) {
					ias = spamMap.get(node);
					if (ias != null) {
						tempScore = ias.score;
						if (ias.id >= 0) {
							node.multiWords.add(ias.id);
						}

						byte[] realByte = new byte[n];
						int readBegin = begin;
						for (int nn = 0; nn < n; nn++, readBegin++) {
							realByte[nn] = text[readBegin];
						}
						token = new Token(i, realByte);
						token.setScore(tempScore);
					}

					n++;
					if (length >= begin + n) {
						node.Set(n, begin);
					} else {
						break;
					}
				}
				if (tempScore > 0) {
					score += tempScore;
					list.add(token);
					// if ( score > deadScore)
					// {
					// Node.Back(node);
					// return Config.RET_SpamText;
					// }
				}
			}
		}

		// if ( node.multiWords.size() > 1 )
		// {
		// score += multiChecker.MultiWordsCheck(node.multiWords, deadScore);
		// Node.Back(node);
		// return score > deadScore?Config.RET_SpamText:Config.RET_Pass;
		// }
		Node.Back(node);
		return list;

	}

	/**
	 * MUSTINIT:此处需要从数据库中读取屏蔽词
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public boolean Initial(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filename), Charset.forName("UTF-8")));
		String line;
		List<SpamStruct> list = new ArrayList<SpamStruct>();
		int i;
		String s1, s2;
		SpamStruct ss;
		while (true) {
			line = reader.readLine();
			if (line == null)
				break;
			if (line.length() == 0)
				continue;
			for (i = line.length() - 1; i >= 0; i--) {
				if (line.charAt(i) == ',')
					break;
			}
			s1 = line.substring(1, i - 1);
			s2 = line.substring(i + 1);
			ss = new SpamStruct();
			ss.setText(s1);
			ss.setScore(Integer.parseInt(s2));
			list.add(ss);
		}
		reader.close();
		return Initial(list);
	}

	public boolean Initial(List<SpamStruct> spamList) {
		/** two temporary collections */
		int[] spamFirstWord_tmp = new int[65536];
		HashMap<Node, IdAndScore> spamMap_tmp = new HashMap<Node, IdAndScore>(
				spamList.size() * 4, 0.3f);
		multiChecker = new MultiWordsChecker();
		List<MultiWordsItem> mwis = new ArrayList<MultiWordsItem>();

		for (int i = 0; i < spamFirstWord_tmp.length; i++) {
			spamFirstWord_tmp[i] = 0;
		}

		String[] strstr;
		String s;
		Map<String, Integer> word2Id = new HashMap<String, Integer>();
		int wordId = 0;
		int id;
		MultiWordsItem mwi;
		for (SpamStruct ss : spamList) {
			s = ss.getText();
			strstr = s.split("\t");
			if (strstr.length > 1) {
				mwi = new MultiWordsItem();
				mwi.wordIds = new int[strstr.length];
				mwi.score = ss.getScore();
				for (int i = 0; i < strstr.length; i++) {
					s = strstr[i];
					if (!word2Id.containsKey(s)) {
						id = wordId++;
						word2Id.put(s, id);
					} else {
						id = word2Id.get(s);
					}
					mwi.wordIds[i] = id;
				}
				mwis.add(mwi);
			}
		}
		multiChecker.Init(mwis);

		byte[] bytes;
		IdAndScore ias;

		for (SpamStruct ss : spamList) {
			if (ss.getScore() < 0) {
				System.out
						.printf("---ERROR:spam score < 0 , spam text {0}, spam score {1}\nSpamHelper initial failed!\n",
								ss.getText(), ss.getScore());
				return false;
			}

			s = ss.getText();

			if (s == null || s.isEmpty())
				continue;

			strstr = s.split("\t");
			if (strstr.length > 1) {
				continue;
			}

			if (word2Id.containsKey(s))
				id = word2Id.get(s);
			else
				id = -1;

			bytes = s.getBytes(Config.CharSetInstance);
			CleanStringType cst = new CleanStringType();

			Filter.CleanString(bytes, false, cst, true);

			bytes = cst.clearString;
			if (bytes == null) {
				System.out.println("NULL : " + s);
				continue;
			}
			int sh;
			if (cst.strLen == 1 || bytes[0] >= 0) {
				sh = bytes[0];
			} else {
				sh = ((bytes[0] & 255) << 8) | (bytes[1] & 255);
			}

			if (spamFirstWord_tmp[sh] == 0
					|| spamFirstWord_tmp[sh] > cst.strLen) {
				spamFirstWord_tmp[sh] = bytes.length;
			}

			Node node = new Node(bytes, 0, cst.strLen);
			ias = new IdAndScore();
			ias.score = ss.getScore();
			ias.id = id;
			spamMap_tmp.put(node, ias);
			int j;
			if (bytes[0] < 0)
				j = 2;
			else
				j = 1;

			for (; j < cst.strLen; j++) {
				// if ( bytes[j] < 0 )
				// {
				// j++;
				// if ( j>= cst.p )
				// break;
				// }
				Node tempNode = new Node(bytes, 0, j + 1);

				if (!spamMap_tmp.containsKey(tempNode))
					spamMap_tmp.put(tempNode, null);
			}
		}

		/** switch reference */
		spamFirstWord = spamFirstWord_tmp;
		spamMap = spamMap_tmp;

		return true;
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
			CleanStringType ret) {
		// check too long
		// if ( text.length > Config.MaxStringLength )
		// {
		// ret.retValue = Config.RET_TextTooLong;
		// return false;
		// }

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

				if (checkUrl) {
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
									}
									status = URL_STATUS.US_TAIL;
									break;
								}
							}
							// other
							if (!CheckUrlIsTudou(urlBuffer, pBuffer)) {
								ret.retValue = Config.RET_OutsideURL;
								return false;
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
							if (maxChLength > Config.MaxSequenceChinese) {
								// ret.retValue = Config.RET_ChineseTooLong;
								// return false;
							}
						}
					} else {
						if (maxEnLength < termLength) {
							maxEnLength = termLength;
							if (maxEnLength > Config.MaxSequenceCharAndDigit) {
								// ret.retValue =
								// Config.RET_CharAndDigitialTooLong;
								// return false;
							}
						}
					}
					termLength = bSingle ? 1 : 2;
					preCharType = charType;
				} else {
					termLength += bSingle ? 1 : 2;
				}
			}

			if (checkUrl && status == URL_STATUS.US_NAME_X
					&& !CheckUrlIsTudou(urlBuffer, pBuffer)) {
				// ret.retValue = Config.RET_OutsideURL;
				// return false;
			}
		} finally {
			if (urlBuffer != null)
				BackBytes(urlBuffer);
		}

		// if (preCharType == CharHelper.CT_Chinese)
		// {
		// if ( termLength > Config.MaxSequenceChinese )
		// {
		// ret.retValue = Config.RET_ChineseTooLong;
		// return false;
		// }
		// }
		// else
		// {
		// if ( termLength > Config.MaxSequenceCharAndDigit )
		// {
		// ret.retValue = Config.RET_CharAndDigitialTooLong;
		// return false;
		// }
		// }
		//
		// if ( brCount > Config.SmallLineCount &&
		// (float)validLength/(float)brCount < Config.NarrowTextRate )
		// {
		// ret.retValue = Config.RET_ShapeTooNarrow;
		// return false;
		// }
		//
		// if ((float)emptyCount/(float)strLen > Config.MaxEmptyRate )
		// {
		// ret.retValue = Config.RET_TooManyEmpties;
		// return false;
		// }
		ret.clearString = sb;
		ret.strLen = pSb;
		return true;
	}

	private static boolean CheckUrlIsTudou(byte[] bytes, int length) {
		// for debug - begin
		// String str = new String(bytes, 0, length, Config.CharSetInstance);
		// System.out.println("url is "+str);
		// for debug - end
		if (length >= 9) {
			return (bytes[length - 9] == 't' || bytes[length - 9] == 'T')
					&& (bytes[length - 8] == 'u' || bytes[length - 8] == 'U')
					&& (bytes[length - 7] == 'd' || bytes[length - 7] == 'D')
					&& (bytes[length - 6] == 'o' || bytes[length - 6] == 'O')
					&& (bytes[length - 5] == 'u' || bytes[length - 5] == 'U')
					&& (bytes[length - 4] == '.')
					&& (bytes[length - 3] == 'c' || bytes[length - 3] == 'C')
					&& (bytes[length - 2] == 'o' || bytes[length - 2] == 'O')
					&& (bytes[length - 1] == 'm' || bytes[length - 1] == 'M')
					&& (length == 9 || bytes[length - 10] == '.');
		}

		return false;
	}

	private static final int URL_MAX_LENGTH = 100;
	private static Queue<byte[]> byteses = new ArrayDeque<byte[]>();

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

	private final static byte OTHER = 0;
	private final static byte ALPHA = 1;
	private final static byte LEGAL = 2;

	private HashMap<Node, IdAndScore> spamMap;
	private MultiWordsChecker multiChecker; // readonly
	private int[] spamFirstWord;
	private static byte[] urlChar;

	enum URL_STATUS {
		US_READY, US_PROTOCOL, US_COLON, US_SLASH1, US_SLASH2, US_NAME1, US_DOT, US_NAME_X, US_TAIL,
	}

	static {
		InitUrlChecker();
	}
}
