package com.juzhai.home.service.impl;

import com.juzhai.home.service.IUserFreeDateService;

//@Service
public class UserFreeDateService implements IUserFreeDateService {

	// private final Log log = LogFactory.getLog(getClass());
	//
	// @Autowired
	// private UserFreeDateMapper userFreeDateMapper;
	// @Autowired
	// private MemcachedClient memcachedClient;
	//
	// @Override
	// public void setFreeDate(long uid, Date freeDate) {
	// Date cDate = new Date();
	// // for (Date date : dateList) {
	// UserFreeDate userFreeDate = new UserFreeDate();
	// userFreeDate.setUid(uid);
	// userFreeDate.setFreeDate(freeDate);
	// userFreeDate.setCreateTime(cDate);
	// userFreeDate.setLastModifyTime(cDate);
	// try {
	// userFreeDateMapper.insertSelective(userFreeDate);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// // }
	// clearCache(uid);
	// }
	//
	// @Override
	// public void unSetFreeDate(long uid, Date date) {
	// UserFreeDateExample example = new UserFreeDateExample();
	// example.createCriteria().andUidEqualTo(uid).andFreeDateEqualTo(date);
	// userFreeDateMapper.deleteByExample(example);
	// clearCache(uid);
	// }
	//
	// @Override
	// public List<Date> userFreeDateList(long uid) {
	// List<Date> dateList = null;
	// try {
	// dateList = memcachedClient.get(MemcachedKeyGenerator
	// .genUserFreeDateListKey(uid));
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// if (null == dateList) {
	// Date cDate = DateUtils.truncate(new Date(), Calendar.DATE);
	// UserFreeDateExample example = new UserFreeDateExample();
	// example.createCriteria().andUidEqualTo(uid)
	// .andFreeDateGreaterThanOrEqualTo(cDate);
	// example.setOrderByClause("free_date asc");
	// List<UserFreeDate> list = userFreeDateMapper
	// .selectByExample(example);
	// dateList = new ArrayList<Date>();
	// for (UserFreeDate ufd : list) {
	// dateList.add(ufd.getFreeDate());
	// }
	// try {
	// memcachedClient.set(
	// MemcachedKeyGenerator.genUserFreeDateListKey(uid),
	// Long.valueOf(
	// DateUtils.addDays(cDate, 1).getTime() / 1000)
	// .intValue(), dateList);
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// }
	// return dateList;
	// }
	//
	// @Override
	// public int countFreeDate(long uid) {
	// Date cDate = DateUtils.truncate(new Date(), Calendar.DATE);
	// UserFreeDateExample example = new UserFreeDateExample();
	// example.createCriteria().andUidEqualTo(uid)
	// .andFreeDateGreaterThanOrEqualTo(cDate);
	// return userFreeDateMapper.countByExample(example);
	// }
	//
	// @Override
	// public void clearCache(long uid) {
	// try {
	// memcachedClient.delete(MemcachedKeyGenerator
	// .genUserFreeDateListKey(uid));
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// }
	//
	// @Override
	// public List<UserFreeDate> listOutFreeDate(int firstResult, int
	// maxResults) {
	// UserFreeDateExample example = new UserFreeDateExample();
	// example.createCriteria().andFreeDateLessThan(
	// DateUtils.truncate(new Date(), Calendar.DATE));
	// example.setLimit(new Limit(firstResult, maxResults));
	// example.setOrderByClause("create_time asc");
	// return userFreeDateMapper.selectByExample(example);
	// }
	//
	// @Override
	// public void deleteUserFreeDate(List<Long> userFreeDateIdList) {
	// UserFreeDateExample example = new UserFreeDateExample();
	// example.createCriteria().andIdIn(userFreeDateIdList);
	// userFreeDateMapper.deleteByExample(example);
	// }
}
