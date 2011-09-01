package com.juzhai.act.rabbit.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.juzhai.act.model.Act;
import com.juzhai.act.rabbit.message.ActIndexMessage;
import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;

@Component
public class ActIndexMessageListener implements
		IRabbitMessageListener<ActIndexMessage, Object> {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private Indexer<Act> actIndexer;

	@Override
	public Object handleMessage(ActIndexMessage actIndexMessage) {
		Act act = actIndexMessage.getBody();
		Assert.notNull(act, "Index act must not be null.");
		try {
			switch (actIndexMessage.getActionType()) {
			case CREATE:
				actIndexer.addIndex(act, true);
			}
		} catch (Exception e) {
			log.error("create act index failed.[actId:" + act.getId() + "]", e);
		}
		return null;
	}
}
