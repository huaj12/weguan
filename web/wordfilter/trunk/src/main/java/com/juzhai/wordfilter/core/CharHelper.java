package com.juzhai.wordfilter.core;

import java.nio.charset.Charset;
import java.util.Arrays;

public class CharHelper {
	public static final byte CT_Unknown = -1;
	public static final byte CT_Return = 1;
	public static final byte CT_SymbolHalf = 2;
	public static final byte CT_DigitAndCharHalf = 3;
	public static final byte CT_Chinese = 6;
	public static final byte CT_SymbolFull = 9;
	public static final byte CT_DigitAndCharFull = 10;
	public static final byte CT_Japan1 = 13;
	public static final byte CT_Japan2 = 14;
	public static final byte CT_Greek = 15;
	public static final byte CT_Russian = 16;

	public static byte GetType(byte c) {
		return types[c];
	}

	public static byte GetType(int c) {
		return types[c];
	}

	public static boolean IsEmpty(int c) {
		return emptyChar[c];
	}

	public static boolean IsEmpty(byte c) {
		return emptyChar[c];
	}

	public static byte Full2Half(int c) {
		return (byte) (c - 0xA380);
	}

	public static byte Full2Half2(int c) {
		if (c > 0 && c < full2half.length) {
			return full2half[c];
		}
		return (byte) 0;
	}

	public static void Init() {
		InitTypes();
		InitEmpties();
	}

	private static void InitEmpties() {
		int c;
		emptyChar = new boolean[65536];
		for (c = 0; c < emptyChar.length; c++) {
			emptyChar[c] = false;
		}
		for (c = 0; c < Config.EmptyChars.length; c++) {
			emptyChar[Config.EmptyChars[c]] = true;
		}
	}

	private static void InitTypes() {
		int c;
		types = new byte[65536];
		for (c = 0; c < types.length; c++) {
			types[c] = CT_Unknown;
		}

		types[0xD] = types[0xA] = CT_Return;

		for (c = 0x20; c <= 0x2F; c++) {
			types[c] = CT_SymbolHalf;
		}
		for (c = 0x30; c <= 0x39; c++) // 0-9
		{
			types[c] = CT_DigitAndCharHalf;
		}
		for (c = 0x3A; c <= 0x40; c++) {
			types[c] = CT_SymbolHalf;
		}
		for (c = 0x41; c <= 0x5A; c++) // A-Z
		{
			types[c] = CT_DigitAndCharHalf;
		}
		for (c = 0x5B; c <= 0x60; c++) {
			types[c] = CT_SymbolHalf;
		}
		for (c = 0x61; c <= 0x7A; c++) // a-z
		{
			types[c] = CT_DigitAndCharHalf;
		}
		for (c = 0x7B; c <= 0x7E; c++) {
			types[c] = CT_SymbolHalf;
		}
		// for ( int a = 0xB0; a<=0xF7; a++ ) // B0-F7|A1-FE: GB2312简体中文
		// {
		// for ( int b = 0xA1; b<=0xFE; b++ )
		// {
		// types[(a<<8)|b] = CT_Chinese;
		// }
		// }
		//
		// for ( c=0x8140; c<=0xA0FE; c++ ) // 0x8140-0xA0FE: GBK/3 繁体
		// {
		// types[c] = CT_Chinese;
		// }
		//
		// for ( int a = 0xAA; a<=0xFE; a++ ) // B0-F7|A1-FE: GB2312简体中文
		// {
		// for ( int b = 0x40; b<=0xA0; b++ )
		// {
		// types[(a<<8)|b] = CT_Chinese;
		// }
		// }
		//
		// for ( c=0x8140; c<=0xA1B7; c++) // 0xA1A1-0xA1B7: 符号一区
		// {
		// types[c] = CT_SymbolFull;
		// }
		// for ( c=0xA3A0; c<=0xA3AF; c++) // 0xA3A0-0xA3AF: 符号二区
		// {
		// types[c] = CT_SymbolFull;
		// }
		// for ( c=0xA3B0; c<=0xA3B9; c++) // 0xA3B0-0xA3B9: 全角数字
		// {
		// types[c] = CT_DigitAndCharFull;
		// }
		// for ( c=0xA3BA; c<=0xA3C0; c++) // 0xA3BA-0xA3C0: 符号三区
		// {
		// types[c] = CT_SymbolFull;
		// }
		// for ( c=0xA3C1; c<=0xA3DA; c++) // 0xA3C1-0xA3DA: 全角字母大写
		// {
		// types[c] = CT_DigitAndCharFull;
		// }
		//
		// for ( c=0xA3DB; c<=0xA3E0; c++) // 0xA3DB-0xA3E0: 符号四区
		// {
		// types[c] = CT_SymbolFull;
		// }
		//
		// for ( c=0xA3E1; c<=0xA3FA; c++) // 0xA3E1-0xA3FA: 全角字母小写
		// {
		// types[c] = CT_DigitAndCharFull;
		// }
		//
		// for ( c=0xA3FB; c<=0xA3FD; c++) // 0xA3FB-0xA3FD: 符号五区
		// {
		// types[c] = CT_SymbolFull;
		// }
		//
		// for ( c=0xA4A1; c<=0xA4F3; c++) // 0xA4A1-0xA4F3: 日文字母一区
		// {
		// types[c] = CT_Japan1;
		// }
		//
		// for ( c=0xA5A1; c<=0xA5F6; c++) // 0xA5A1-0xA5F6: 日文字母二区
		// {
		// types[c] = CT_Japan2;
		// }
		//
		// for ( c=0xA6A1; c<=0xA6D8; c++) // 0xA6A1-0xA6F8: 希腊字母
		// {
		// types[c] = CT_Greek;
		// }
		//
		// for ( c=0xA7A1; c<=0xA7F1; c++) // 0xA7A1-0xA7F1: 俄文字母
		// {
		// types[c] = CT_Russian;
		// }

		for (int a = 0xB0; a <= 0xF7; a++) // B0-F7|A1-FE: GB2312简体中文 汉字区a
		{
			for (int b = 0xA1; b <= 0xFE; b++) {
				types[(a << 8) | b] = CT_Chinese;
			}
		}

		for (int a = 0x81; a <= 0xA0; a++) // 汉字区 b1
		{
			for (int b = 0x40; b <= 0xFE; b++) {
				types[(a << 8) | b] = CT_Chinese;
			}
		}

		for (int a = 0xaa; a <= 0xfe; a++) // 汉字区 b2
		{
			for (int b = 0x40; b <= 0xa0; b++) {
				types[(a << 8) | b] = CT_Chinese;
			}
		}

		for (int a = 0xa1; a <= 0xa9; a++) // 图形符号区a
		{
			for (int b = 0xa1; b <= 0xfe; b++) {
				types[(a << 8) | b] = CT_SymbolFull;
			}
		}

		for (int a = 0xa8; a <= 0xa9; a++) // 图形符号区b
		{
			for (int b = 0x40; b <= 0xa0; b++) {
				types[(a << 8) | b] = CT_SymbolFull;
			}
		}

		for (c = 0xA4A1; c <= 0xA4F3; c++) // 0xA4A1-0xA4F3: 日文字母一区
		{
			types[c] = CT_Japan1;
		}

		for (c = 0xA5A1; c <= 0xA5F6; c++) // 0xA5A1-0xA5F6: 日文字母二区
		{
			types[c] = CT_Japan2;
		}

		full2half = new byte[65536];
		Arrays.fill(full2half, (byte) 0);
		String S = "０ａＡ。：／？＃【】＠！￥＆‘（）×＋，；＝－。—～％";
		String s = "0aA.:/?#[]@!$&'()*+,;=-._~%";
		byte[] B = S.getBytes(Charset.forName("GBK"));
		byte[] b = s.getBytes(Charset.forName("GBK"));
		int n;
		for (byte i = 0; i < 10; i++) {
			// 0--9
			n = (((B[0] & 255) << 8) | (B[1] & 255)) + i;
			full2half[n] = (byte) (b[0] + i);
		}
		for (byte i = 0; i < 26; i++) {
			// a--z
			n = (((B[2] & 255) << 8) | (B[3] & 255)) + i;
			full2half[n] = (byte) (b[1] + i);

			// A--Z
			n = (((B[4] & 255) << 8) | (B[5] & 255)) + i;
			full2half[n] = (byte) (b[2] + i);
		}
		for (byte i = 3; i < b.length; i++) {
			n = ((B[i * 2] & 255) << 8) | (B[i * 2 + 1] & 255);
			full2half[n] = b[i];
		}
	}

	private static boolean[] emptyChar;
	private static byte[] types;
	private static byte[] full2half;

	public static void main(String[] argv) {
		Init();
		String s = "你好wwwwwwｗｗｗｗ。￥.aaa ~~~０１２３４５６７８９０ＡＢＣＤＥＦＧＨ0123。：／？＃【】＠！￥＆‘（）×＋，；＝－。—～％-";
		// String s = "—";
		byte[] b = s.getBytes(Config.CharSetInstance);

		byte[] bb = new byte[10000];
		int p = 0;
		byte tmp;
		int tmp2;
		for (int i = 0; i < b.length; i++) {
			if (b[i] > 0) {
				tmp = Full2Half2(b[i]);
				bb[p++] = tmp == 0 ? b[i] : tmp;

			} else {
				tmp2 = ((b[i] & 255) << 8) | (b[i + 1] & 255);
				tmp = Full2Half2(tmp2);
				if (tmp == 0) {
					bb[p++] = b[i];
					bb[p++] = b[i + 1];
				} else {
					bb[p++] = tmp;
				}
				i++;
			}
		}

		String s2 = new String(bb, 0, p, Config.CharSetInstance);
		System.out.println("s=" + s);
		System.out.println("s2=" + s2);
	}

}
