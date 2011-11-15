package com.juzhai.cms.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.cms.mapper.AddActActionMapper;
import com.juzhai.cms.mapper.SearchActActionMapper;
import com.juzhai.cms.model.AddActAction;
import com.juzhai.cms.model.SearchActAction;
import com.juzhai.cms.model.SearchActActionExample;
import com.juzhai.cms.service.impl.UserActionService;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class DeleteUserActionHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private SearchActActionMapper searchActActionMapper;
	@Autowired
	private AddActActionMapper addActActionMapper;
	@Autowired
	private UserActionService userActionService;

	@Override
	protected void doHandle() {
		deleteAddActAction();
		log.debug("deleteAddActAction is success");
		deleteSearchActAction();
		log.debug("deleteSearchActAction is success");
	}

	private void deleteAddActAction() {
		try {
			PagerManager pager = new PagerManager(1, 50,
					userActionService.getAddActActionCount());
			for (int i = 1; i <= pager.getTotalPage(); i++) {
				List<AddActAction> list = userActionService
						.getAddActAction((i - 1) * pager.getResults(),
								pager.getResults());
				for (AddActAction act : list) {
					addActActionMapper.deleteByPrimaryKey(act.getId());
				}
			}
		} catch (Exception e) {
			log.error("deleteAddActAction is error Date=" + new Date(), e);
		}
	}
	private void deleteSearchActAction() {
		try {
			PagerManager pager = new PagerManager(1, 50,
					userActionService.getSearchActActionCount());
			for (int i = 1; i <= pager.getTotalPage(); i++) {
				List<SearchActAction> list = userActionService
						.getSearchActAction((i - 1) * pager.getResults(),
								pager.getResults());
				for (SearchActAction act : list) {
					searchActActionMapper.deleteByPrimaryKey(act.getId());
				}
			}
		} catch (Exception e) {
			log.error("deleteSearchActAction is error Date=" + new Date(), e);
		}
	}
}
