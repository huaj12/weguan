package com.juzhai.core.mail.manager.impl;

import java.util.Map;

import com.juzhai.core.mail.manager.IFrequencyStrategy;

public class FrequencyStrategy implements IFrequencyStrategy {

	private Map<String, Long> frequencyMap;

	@Override
	public long getfrequency(String address) {
		int beginIndex = address.indexOf("@") + 1;
		String key = address.substring(beginIndex);
		if (frequencyMap.get(key) == null) {
			return 0;
		} else {
			return frequencyMap.get(key);
		}
	}

	public void setFrequencyMap(Map<String, Long> frequencyMap) {
		this.frequencyMap = frequencyMap;
	}

}
