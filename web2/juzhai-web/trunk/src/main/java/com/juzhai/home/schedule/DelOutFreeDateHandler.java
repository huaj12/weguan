package com.juzhai.home.schedule;

import org.springframework.beans.factory.annotation.Autowired;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.home.service.IUserFreeDateService;

//@Component
public class DelOutFreeDateHandler extends AbstractScheduleHandler {

	@Autowired
	private IUserFreeDateService userFreeDateService;

	@Override
	protected void doHandle() {
		// Set<Long> uidSet = new HashSet<Long>();
		// int firstResult = 0;
		// int maxResults = 200;
		// while (true) {
		// List<UserFreeDate> userFreeDateList = userFreeDateService
		// .listOutFreeDate(firstResult, maxResults);
		// if (CollectionUtils.isEmpty(userFreeDateList)) {
		// break;
		// }
		// List<Long> userFreeDateIdList = new ArrayList<Long>();
		// for (UserFreeDate userFreeDate : userFreeDateList) {
		// userFreeDateIdList.add(userFreeDate.getId());
		// uidSet.add(userFreeDate.getUid());
		// }
		// userFreeDateService.deleteUserFreeDate(userFreeDateIdList);
		// firstResult += maxResults;
		// }
		// for (long uid : uidSet) {
		// userFreeDateService.clearCache(uid);
		// }
	}
}
