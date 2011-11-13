package com.juzhai.act.rabbit.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.act.model.Act;
import com.juzhai.act.rabbit.message.ActIndexMessage;
import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.core.lucene.searcher.IndexSearcherManager;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;

@Component
public class ActIndexMessageListener implements
		IRabbitMessageListener<ActIndexMessage, Object> {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private Indexer<Act> actIndexer;
	@Autowired
	private IndexSearcherManager actIndexSearcherManager;

	@Override
	public Object handleMessage(ActIndexMessage actIndexMessage) {
		Act act = actIndexMessage.getBody();
		if (null == act) {
			log.error("Index act must not be null.");
			return null;
		}
		try {
			switch (actIndexMessage.getActionType()) {
			case CREATE:
				actIndexer.addIndex(act, true);
			case UPDATE:
				actIndexer.updateIndex(act, true);
			case DELETE:
				actIndexer.deleteIndex(act, true);
			}
		} catch (Exception e) {
			log.error("create act index failed.[actId:" + act.getId() + "]", e);
		}
		try {
			actIndexSearcherManager.maybeReopen();
		} catch (Exception e) {
			log.error("reopen indexReader failed when create act["
					+ act.getId() + "] index");
		}
		return null;
	}
}
