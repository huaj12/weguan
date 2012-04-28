package com.juzhai.core.web.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractLoginSessionManager implements
		LoginSessionManager {

	protected final Log log = LogFactory.getLog(getClass());

	protected final String UID_ATTRIBUTE_NAME = "uid";
	protected final String TPID_ATTRIBUTE_NAME = "tpId";
	protected final String ADMIN_ATTRIBUTE_NAME = "admin";
	@Value(value = "${login.expire.time.seconds}")
	protected int loginExpireTimeSeconds;

}
