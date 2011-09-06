package com.juzhai.account.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.account.InitData;
import com.juzhai.account.bean.ConsumeAction;
import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.dao.impl.AccountDao;
import com.juzhai.account.mapper.AccountMapper;
import com.juzhai.account.model.Account;
import com.juzhai.account.service.IAccountService;

@Service
public class AccountService implements IAccountService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountDao accountDao;

	@Override
	public void createAccount(long uid) {
		if (uid <= 0) {
			log.error("create account failed because uid is equal or less than zero.");
		}
		Account account = new Account();
		account.setUid(uid);
		account.setCreateTime(new Date());
		account.setLastModifyTime(account.getCreateTime());
		accountMapper.insert(account);
	}

	@Override
	public int queryPoint(long uid) {
		Account account = accountMapper.selectByPrimaryKey(uid);
		return account == null ? 0 : account.getPoint();
	}

	@Override
	public boolean checkPoint(long uid, ConsumeAction consumeAction) {
		return (queryPoint(uid) + InitData.CONSUME_ACTION_RULE
				.get(consumeAction)) >= 0;
	}

	@Override
	public boolean consumePoint(long uid, ConsumeAction consumeAction) {
		int point = InitData.CONSUME_ACTION_RULE.get(consumeAction);
		if (point < 0) {
			return accountDao.updatePoint(uid, point) == 1;
		}
		return true;
	}

	@Override
	public void profitPoint(long uid, ProfitAction profitAction) {
		int point = InitData.PROFIT_ACTION_RULE.get(profitAction);
		if (point > 0) {
			accountDao.updatePoint(uid, point);
		}
	}
}
