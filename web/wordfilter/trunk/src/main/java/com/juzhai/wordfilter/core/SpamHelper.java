package com.juzhai.wordfilter.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class SpamHelper {
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

	public SpamHelper() {
	}

	public int IsTextSpam(int deadScore, byte[] text, int length, int maxLength) {
		if (text == null || length == 0) {
			return Config.RET_Pass;
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
				while (spamMap.containsKey(node)) {
					ias = spamMap.get(node);
					if (ias != null) {
						tempScore = ias.score;
						if (ias.id >= 0) {
							node.multiWords.add(ias.id);
						}
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
					if (score > deadScore) {
						Node.Back(node);
						return Config.RET_SpamText;
					}
				}
			}
		}

		if (node.multiWords.size() > 1) {
			score += multiChecker.MultiWordsCheck(node.multiWords, deadScore);
			Node.Back(node);
			return score > deadScore ? Config.RET_SpamText : Config.RET_Pass;
		}
		Node.Back(node);
		return Config.RET_Pass;

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
			s1 = line.substring(0, i);
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

	private HashMap<Node, IdAndScore> spamMap; // readonly
	private MultiWordsChecker multiChecker; // readonly
	private int[] spamFirstWord;

	// public static void main(String[] argv)
	// {
	// SpamHelper helper = new SpamHelper();
	// CharHelper.Init();
	// byte[] bytes;
	// String[] ss = new String[]{
	// "tudou.com",
	// "Tudou.com",
	// "TUDOU.COM",
	// "56.com",
	// "http://56.com",
	// "http://56.com?ref=tudou.com",
	// "http://56.com?ref=http://tudou.com",
	// "tud0u.com",
	// "www.tudou.com",
	// "http://www.tudou.com",
	// "http://tudou.com",
	// "http://tudou.com?abc=1",
	// "不容错过，让你爽的美女，脱a.b衣激情视频",
	// "不容错过，让你爽的美女，脱.b衣激情视频",
	// "不容错过，让你爽的美女，脱a.衣激情视频",
	// "不容错过，让你爽的美女，脱.衣激情视频",
	// "不容错过，让你爽的美女，脱衣激情视频",
	// "不容错过，让你爽的美女，脱衣激情视频。tudou.com",
	// "很爽的激情视频 http://tudou.com",
	// "很爽的激情视频 http://tudou.com ",
	// "很爽的激情视频 http://tud.com ",
	// "很爽的激情视频 http://.com ",
	// "很爽的激情视频 http://ttudou.com ",
	// "激情的视频聊天 http://www.tudou.com",
	// "激情的视频聊天 http://www.tudou.com?site=http://abc.com",
	// "激情的视频聊天 http://tudou.com?site=abc.com",
	// "快来聊天呀，有激56.com情视频呀 ",
	// "快来聊天呀，有激www.56.com情视频呀 ",
	// "快来聊天呀，有激pic.www.56.com情视频呀 ",
	// "快来聊天呀，有激pic.www.56.com:8080情视频呀 ",
	// "快来聊天呀，有激pic.tudou.com:8080情视频呀 ",
	// "视频https://youku.com/abc?ab=222聊天后，有色情服务（大美女哟），让你拥有激情一夜。",
	// "视频https://tudou.com/abc?ab=222聊天后，有色情服务（大美女哟），让你拥有激情一夜。",
	// "视频https://pic.tudou.com/abc?ab=222 聊天后，有色情服务（大美女哟），让你拥有激情一夜。",
	// "视频https://pic.tudou.com/abc?ab=222/ 聊天后，有色情服务（大美女哟），让你拥有激情一夜。",
	// "视频https://aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaapic.tffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffudou.com/abc?ab=222/ 聊天后，有色情服务（大美女哟），让你拥有激情一夜。",
	// };
	// try {
	// if ( !helper.Initial("D:\\mwu\\code\\TextFilter_v2\\test\\words.txt") )
	// {
	// System.out.println("init err");
	// return;
	// }
	// for ( String s : ss )
	// {
	// System.out.print("["+s+"]\n check url:");
	// bytes = s.getBytes(Config.CharSetInstance);
	//
	// if ( helper.IsTextSpam(100, bytes, bytes.length, 1000, true) !=
	// Config.RET_Pass )
	// {
	// System.out.println("  Kill!");
	// }
	// else
	// {
	// System.out.println("  Pass");
	// }
	// // System.out.print(" not check:");
	// // if ( helper.IsTextSpam(100, bytes, bytes.length, 1000, false) !=
	// Config.RET_Pass )
	// // {
	// // System.out.println("  Kill!");
	// // }
	// // else
	// // {
	// // System.out.println("  Pass");
	// // }
	// System.out.println();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
