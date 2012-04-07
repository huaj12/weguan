package com.juzhai.cms.task;

import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gdata.client.douban.DoubanService;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;

public class UpdateDoubanUserTask implements Callable<Boolean> {
	private final Log log = LogFactory.getLog(getClass());
	private TpUser tpUser;
	private DoubanService doubanService;
	private TpUserMapper tpUserMapper;

	public UpdateDoubanUserTask(TpUser tpUser, DoubanService doubanService,
			TpUserMapper tpUserMapper) {
		this.tpUser = tpUser;
		this.doubanService = doubanService;
		this.tpUserMapper = tpUserMapper;
	}

	@Override
	public Boolean call() throws Exception {
		try {
			// 获取个性域名的数字uid
			String content = getContent(tpUser.getTpIdentity());
			if (StringUtils.isEmpty(content)) {
				return false;
			}
			String uid = doubanService.getUid(content,
					"http://api.douban.com/people/(\\d*)");
			TpUserExample countExample = new TpUserExample();
			countExample.createCriteria().andTpIdentityEqualTo(uid);
			// 如果该数字id存在把TpIdentity 更新为uid_个性域名
			// 不存在把个性域名更新为数字id
			if (tpUserMapper.countByExample(countExample) > 0) {
				tpUser.setTpIdentity(uid + "_" + tpUser.getTpIdentity());
			} else {
				tpUser.setTpIdentity(uid);
			}
			tpUser.setLastModifyTime(new Date());
			tpUserMapper.updateByPrimaryKeySelective(tpUser);
		} catch (Exception e) {
			log.error("UpdateDoubanUserTask is error", e);
			return false;
		}
		return true;
	}

	public String getContent(String uid) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet accessget = new HttpGet("http://api.douban.com/people/" + uid);
		String content = "";
		try {
			content = httpclient.execute(accessget, responseHandler);
		} catch (Exception e) {
			log.error("douban getConeten is error uid=" + uid, e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

}
