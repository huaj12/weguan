package com.juzhai.wordfilter.core;

import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.Iterator;

public class IpRepeatStruct {
	public IpRepeatStruct(long t, byte[] text, int length) {
		banned = false;
		time = t;
		data = new ArrayDeque<MD5_Count>(Config.SpamIpRepeatCount);
		byte[] m;
		synchronized (md) {
			md.update(text, 0, length);
			m = md.digest();
		}

		data.add(new MD5_Count(m, length));
	}

	public int Check(long t, byte[] text, int length) {
		if (banned) {
			if (time + Config.RepeatIpBanPeriod < t)// unbanned
			{
				data.clear();
				banned = false;
			} else {
				return Config.RET_RepeatBannedIp;
			}
		}
		MD5_Count mc;
		byte[] md5;
		synchronized (md) {
			md.update(text, 0, length);
			md5 = md.digest();
		}
		if (time + Config.SpamIpRepeatPeriod < t) {
			time = t;
			mc = data.peekFirst();
			if (mc == null) {
				mc = new MD5_Count(md5, length);
				data.add(mc);
			} else {
				mc.count = 1;
				mc.md5 = md5;
			}
			return Config.RET_Pass;
		}
		time = t;
		Iterator<MD5_Count> i = data.iterator();
		while (i.hasNext()) {
			mc = i.next();
			if (mc.textLength == length && mc.CheckMd5(md5)) {
				mc.count++;
				// return mc.count > Config.IpMaxRepeat?
				// Config.RET_RepeatIp: Config.RET_Pass;
				if (mc.count > Config.IpMaxRepeat) {
					banned = true;
					return Config.RET_RepeatIp;
				} else
					return Config.RET_Pass;
			}
		}
		if (data.size() < Config.SpamIpRepeatCount) {
			mc = new MD5_Count(md5, length);
		} else {
			mc = data.pollFirst();
			mc.count = 1;
			mc.textLength = length;
			mc.md5 = md5;
		}
		data.addLast(mc);
		return Config.RET_Pass;
	}

	// last speek time
	long time;

	ArrayDeque<MD5_Count> data;
	boolean banned;
	static MessageDigest md;
}
