package com.juzhai.core.web.session;

import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;

/**
 * 适用于manager层的用户上下文环境。可用于模拟用户的登录状态、用户当前的session ID、用户当前的IP地址。
 * 
 * @author Topbit 2006-3-23 19:04:25
 */
public class UserContext {

	private long uid = 0L;

	private String remoteAddress = null;

	private String sessionId = null;

	private String userAgentPermanentCode = null;

	private long tpId;

	private boolean admin;

	public UserContext(long uid, String remoteAddress, String sessionId,
			String userAgentPermanentCode, long tpId, boolean admin) {
		super();
		this.uid = uid;
		this.remoteAddress = remoteAddress;
		this.sessionId = sessionId;
		this.userAgentPermanentCode = userAgentPermanentCode;
		this.tpId = tpId;
		this.admin = admin;
	}

	/**
	 * 判断用户是否已登录。
	 * 
	 * @return <code>true</code>，用户已登录；<code>false</code>，用户未登录。
	 */
	public boolean hasLogin() {
		return 0L != uid;
	}

	/**
	 * 获取用户的POIN。0表示未登录。
	 * 
	 * @return 用户的POIN。
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * 获取用户的远程IP地址。
	 * 
	 * @return 远程IP地址。
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * 获取用户当前的session ID。
	 * 
	 * @return 当前的session ID。
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * 获取用户的cookie
	 * 
	 * @return 用户的cookie
	 */
	public String getUserAgentPermanentCode() {
		return userAgentPermanentCode;
	}

	public long getTpId() {
		return tpId;
	}

	public String getTpName() {
		Thirdparty tp = InitData.TP_MAP.get(this.tpId);
		return tp == null ? null : tp.getName();
	}

	public boolean isAdmin() {
		return admin;
	}

}