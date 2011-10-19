package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.service.IMsgService;

@Controller
@RequestMapping("/cms")
public class MigrateController {

	@Autowired
	private RedisTemplate<String, ActMsg> redisTemplate;
	@Autowired
	private RedisTemplate<String, MergerActMsg> redisMergerActMsgTemplate;
	@Autowired
	private IMsgService<MergerActMsg> msgService;
	private final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/migrateMsg")
	public String migrateMsg(HttpServletRequest request, Model model) {
		// 迁移未读消息
		Set<String> unReadkeys = redisTemplate.keys("*.unreadActMsg");
		migrate(unReadkeys,"unread");
		Set<String> readkeys = redisTemplate.keys("*.readActMsg");
		migrate(readkeys,"read");
		return null;
	}

	private void migrate(Set<String> keys,String type) {
		for (String key : keys) {
			long count = redisTemplate.opsForList().size(key);
			MergerActMsg merge = new MergerActMsg();
			Map<Long, List<ActMsg>> map = new HashMap<Long, List<ActMsg>>();
			for (int i = 0; i < count; i++) {
				ActMsg actMsg = redisTemplate.opsForList().leftPop(key);
				List<ActMsg> actMsgs = null;
				if (map.get(actMsg.getUid()) == null) {
					actMsgs = new ArrayList<ActMsg>();
				} else {
					actMsgs = map.get(actMsg.getUid());
				}
				actMsgs.add(actMsg);
				map.put(actMsg.getUid(), actMsgs);
			}
			for (Map.Entry<Long, List<ActMsg>> entry : map.entrySet()) {
				merge.setUid(entry.getKey());
				merge.setMsgs(entry.getValue());
				if("read".equals(type)){
					String readKey = RedisKeyGenerator.genReadMsgsKey(getreceiverId(key),
							MergerActMsg.class.getSimpleName());
					redisMergerActMsgTemplate.opsForList().leftPush(readKey, merge);
				}else{
					msgService.sendMsg(getreceiverId(key), merge);
				}
			
			}

		}
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
