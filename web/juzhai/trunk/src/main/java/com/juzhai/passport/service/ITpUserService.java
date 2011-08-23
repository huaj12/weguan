package com.juzhai.passport.service;

import com.juzhai.passport.model.TpUser;

public interface ITpUserService {

	TpUser getTpUserByTpIdAndIdentity(long tpId, String identity);
}
