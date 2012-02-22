package com.juzhai.search.rabbit.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.core.lucene.searcher.IndexSearcherManager;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.passport.model.Profile;
import com.juzhai.post.model.Post;
import com.juzhai.search.rabbit.message.PostIndexMessage;
import com.juzhai.search.rabbit.message.ProfileIndexMessage;

@Component
public class ProfileIndexMessageListener implements
		IRabbitMessageListener<ProfileIndexMessage, Object> {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private Indexer<Profile> profileIndexer;
	@Autowired
	private IndexSearcherManager postIndexSearcherManager;

	@Override
	public Object handleMessage(ProfileIndexMessage profileIndexMessage) {
		Profile profile = profileIndexMessage.getBody();
		if (null == profile) {
			log.error("Index act must not be null.");
			return null;
		}
		try {
			switch (profileIndexMessage.getActionType()) {
			case CREATE:
				profileIndexer.addIndex(profile, true);
				if (log.isDebugEnabled()) {
					log.debug("create profile index. uid is "
							+ profile.getUid());
				}
				break;
			case UPDATE:
				profileIndexer.updateIndex(profile, true);
				if (log.isDebugEnabled()) {
					log.debug("update profile index. uid is "
							+ profile.getUid());
				}
				break;
			case DELETE:
				profileIndexer.deleteIndex(profile, true);
				if (log.isDebugEnabled()) {
					log.debug("delete profile index. uid is "
							+ profile.getUid());
				}
				break;
			}
		} catch (Exception e) {
			log.error("create profile index failed.[uid:" + profile.getUid()
					+ "]", e);
		}
		try {
			postIndexSearcherManager.maybeReopen();
		} catch (Exception e) {
			log.error("reopen indexReader failed when create profile["
					+ profile.getUid() + "] index");
		}
		return null;
	}
}
