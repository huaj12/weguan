package com.juzhai.cms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.service.IMsgService;

@Deprecated
public class MigrateController {

	@Autowired
	private RedisTemplate<String, ActMsg> redisTemplate;
	@Autowired
	private RedisTemplate<String, MergerActMsg> redisMergerActMsgTemplate;
	@Autowired
	private IMsgService<MergerActMsg> msgService;
	private final Log log = LogFactory.getLog(getClass());

	public String migrateMsg(HttpServletRequest request, Model model) {
		// 迁移未读消息
		try {
			Set<String> unReadkeys = redisTemplate.keys("*.unreadActMsg");
			migrate(unReadkeys, "unread");
			Set<String> readkeys = redisTemplate.keys("*.readActMsg");
			migrate(readkeys, "read");
		} catch (Exception e) {
			log.error("migrate is error", e);
		}
		return null;
	}

	private void migrate(Set<String> keys, String type) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String key : keys) {
			long count = redisTemplate.opsForList().size(key);
			MergerActMsg merge = new MergerActMsg();
			Map<String, List<ActMsg>> map = new HashMap<String, List<ActMsg>>();
			for (int i = 0; i < count; i++) {
				ActMsg actMsg = redisTemplate.opsForList().leftPop(key);
				if (actMsg == null) {
					log.info("actMsg is null key=" + key);
					continue;
				}
				List<ActMsg> actMsgs = null;

				String nowDate = sdf.format(actMsg.getDate());
				log.debug(actMsg.getUid() + ":" + actMsg.getType() + ":"
						+ nowDate);
				String mapKey = actMsg.getUid() + ":" + actMsg.getType() + ":"
						+ nowDate;
				if (map.get(mapKey) == null) {
					actMsgs = new ArrayList<ActMsg>();
				} else {
					actMsgs = map.get(mapKey);
				}
				actMsgs.add(actMsg);
				map.put(mapKey, actMsgs);
			}
			for (Map.Entry<String, List<ActMsg>> entry : map.entrySet()) {
				String str[] = entry.getKey().split(":");
				long uid = Long.valueOf(str[0]);
				MsgType msgType = MsgType.valueOf(str[1]);
				String nowDate = str[2];
				merge.setUid(uid);
				merge.setType(msgType);
				merge.setDate(sdf.parse(nowDate));
				merge.setMsgs(entry.getValue());
				if ("read".equals(type)) {
					String readKey = RedisKeyGenerator.genReadMsgsKey(
							getreceiverId(key),
							MergerActMsg.class.getSimpleName());
					redisMergerActMsgTemplate.opsForList().leftPush(readKey,
							merge);
				} else {
					msgService.sendMsg(getreceiverId(key), merge);
				}

			}
		}
		redisTemplate.delete(keys);
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
