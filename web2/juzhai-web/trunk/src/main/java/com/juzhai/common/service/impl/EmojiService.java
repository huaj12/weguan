package com.juzhai.common.service.impl;

import org.springframework.stereotype.Service;

import com.juzhai.common.service.IEmojiRemoteService;
import com.juzhai.common.util.IOSEmojiUtil;

@Service
public class EmojiService implements IEmojiRemoteService {

	@Override
	public String transToIOS4AndAdEmoji(String string) {
		return IOSEmojiUtil.transToIOS4emoji(string);
	}

	@Override
	public String transToIOS5Emoji(String string) {
		return IOSEmojiUtil.transToIOS5emoji(string);
	}

}
