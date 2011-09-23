package com.juzhai.wordfilter.core;

public class MD5_Count {
	MD5_Count(byte[] m, int length) {
		md5 = m;
		textLength = length;
		count = 1;
	}

	byte[] md5;
	public int textLength;
	byte count;

	public boolean CheckMd5(byte[] m) {
		for (int i = 0; i < m.length; i++) {
			if (m[i] != md5[i])
				return false;
		}
		return true;
	}
}
