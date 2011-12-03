package com.juzhai.cms.controller.migrate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.comparator.DescDateComparator;

@Controller
@RequestMapping("/cms")
public class MigrateUnreadController {
	@Autowired
	private RedisTemplate<String, MergerActMsg> redisMergerActMsgTemplate;

	@RequestMapping(value = "/migrateUnRead", method = RequestMethod.GET)
	public String migrateUnRead() {
		Set<String> unReadkeys = redisMergerActMsgTemplate
				.keys("*.unreadMergerActMsg");
		for (String key : unReadkeys) {
			long size = redisMergerActMsgTemplate.opsForList().size(key);
			String readKey = RedisKeyGenerator.genReadMsgsKey(
					getreceiverId(key), MergerActMsg.class.getSimpleName());
			List<MergerActMsg> list = new ArrayList<MergerActMsg>();
			for (int i = 0; i < size; i++) {
				MergerActMsg msg = redisMergerActMsgTemplate.opsForList()
						.rightPop(key);
				list.add(msg);
			}
			long readSize = redisMergerActMsgTemplate.opsForList()
					.size(readKey);
			for (int i = 0; i < readSize; i++) {
				MergerActMsg msg = redisMergerActMsgTemplate.opsForList()
						.leftPop(readKey);
				list.add(msg);
			}
			Collections.sort(list, new DescDateComparator());
			for (MergerActMsg value : list) {
				redisMergerActMsgTemplate.opsForList().leftPush(readKey, value);
			}
		}
		if (unReadkeys != null && unReadkeys.size() > 0) {
			redisMergerActMsgTemplate.delete(unReadkeys);
		}
		return null;
	}

	private int getreceiverId(String key) {
		try {
			String str[] = key.split("\\.");
			return Integer.parseInt(str[0]);
		} catch (Exception e) {
			return 0;
		}

	}
}
