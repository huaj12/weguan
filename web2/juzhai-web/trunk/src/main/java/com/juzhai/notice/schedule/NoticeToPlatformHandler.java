package com.juzhai.notice.schedule;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.platform.exception.AdminException;

@Component
public class NoticeToPlatformHandler extends AbstractScheduleHandler {
	@Autowired
	private INoticeService noticeService;

	@Override
	protected void doHandle() {
		while (true) {
			List<Long> uids = noticeService.getNoticUserList(200);
			if (CollectionUtils.isEmpty(uids)) {
				return;
			}
			for (long uid : uids) {
				long num = noticeService.getNoticeNum(uid, NoticeType.DIALOG);
				try {
					noticeService.noticeUserUnReadNum(uid, num);
				} catch (AdminException e) {
					return;
				}
				noticeService.removeFromNoticeUsers(uid);
			}
		}
	}
}
