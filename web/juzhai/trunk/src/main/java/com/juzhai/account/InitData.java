/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.account.bean.ConsumeAction;
import com.juzhai.account.bean.ProfitAction;

//@Component("accountInitData")
//@Lazy(false)
public class InitData {

	private final Log log = LogFactory.getLog(getClass());
	private static final String POINT_RULE_PATH = "properties/pointRule.properties";
	public static final Map<ProfitAction, Integer> PROFIT_ACTION_RULE = new HashMap<ProfitAction, Integer>();
	public static final Map<ConsumeAction, Integer> CONSUME_ACTION_RULE = new HashMap<ConsumeAction, Integer>();

	@PostConstruct
	public void init() {
		// initActionRule();
	}

	private void initActionRule() {
		Properties config = new Properties();
		try {
			config.load(getClass().getClassLoader().getResourceAsStream(
					POINT_RULE_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (ProfitAction profitAction : ProfitAction.values()) {
			Integer value = Integer.valueOf(config.getProperty(profitAction
					.name()));
			value = Math.max(value, 0);
			PROFIT_ACTION_RULE.put(profitAction, value);
		}
		for (ConsumeAction consumeAction : ConsumeAction.values()) {
			Integer value = Integer.valueOf(config.getProperty(consumeAction
					.name()));
			value = Math.min(value, 0);
			CONSUME_ACTION_RULE.put(consumeAction, value);
		}
	}
}
