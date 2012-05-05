package com.juzhai.cms.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActExample;
import com.juzhai.cms.mapper.AddActActionMapper;
import com.juzhai.cms.mapper.SearchActActionMapper;
import com.juzhai.cms.model.AddActAction;
import com.juzhai.cms.model.AddActActionExample;
import com.juzhai.cms.model.SearchActAction;
import com.juzhai.cms.model.SearchActActionExample;
import com.juzhai.cms.service.impl.UserActionService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
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
			AddActActionExample example = new AddActActionExample();
			example.setOrderByClause("id asc");
			int firstResult = 0;
			int maxResults = 200;
			while (true) {
				example.setLimit(new Limit(firstResult, maxResults));
				List<AddActAction> actList = addActActionMapper
						.selectByExample(example);
				if (CollectionUtils.isEmpty(actList)) {
					break;
				}
				for (AddActAction act : actList) {
					addActActionMapper.deleteByPrimaryKey(act.getId());
				}
				firstResult += maxResults;
			}
		} catch (Exception e) {
			log.error("deleteAddActAction is error Date=" + new Date(), e);
		}

	}

	private void deleteSearchActAction() {
		try {
			SearchActActionExample example = new SearchActActionExample();
			example.setOrderByClause("id asc");
			int firstResult = 0;
			int maxResults = 200;
			while (true) {
				example.setLimit(new Limit(firstResult, maxResults));
				List<SearchActAction> actList = searchActActionMapper
						.selectByExample(example);
				if (CollectionUtils.isEmpty(actList)) {
					break;
				}
				for (SearchActAction act : actList) {
					searchActActionMapper.deleteByPrimaryKey(act.getId());
				}
				firstResult += maxResults;
			}
		} catch (Exception e) {
			log.error("deleteSearchActAction is error Date=" + new Date(), e);
		}
	}
}
