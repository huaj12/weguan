package com.juzhai.search.rabbit.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.core.lucene.searcher.IndexSearcherManager;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.post.model.Post;
import com.juzhai.search.rabbit.message.PostIndexMessage;

@Component
public class PostIndexMessageListener implements
		IRabbitMessageListener<PostIndexMessage, Object> {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private Indexer<Post> postIndexer;
	@Autowired
	private IndexSearcherManager postIndexSearcherManager;

	@Override
	public Object handleMessage(PostIndexMessage postIndexMessage) {
		Post post = postIndexMessage.getBody();
		if (null == post) {
			log.error("Index act must not be null.");
			return null;
		}
		try {
			switch (postIndexMessage.getActionType()) {
			case CREATE:
				postIndexer.addIndex(post, true);
				if (log.isDebugEnabled()) {
					log.debug("create post index. id is " + post.getId());
				}
				break;
			case UPDATE:
				postIndexer.updateIndex(post, true);
				if (log.isDebugEnabled()) {
					log.debug("update post index. id is " + post.getId());
				}
				break;
			case DELETE:
				postIndexer.deleteIndex(post, true);
				if (log.isDebugEnabled()) {
					log.debug("delete post index. id is " + post.getId());
				}
				break;
			}
		} catch (Exception e) {
			log.error("create post index failed.[postId:" + post.getId() + "]",
					e);
		}
		try {
			postIndexSearcherManager.maybeReopen();
		} catch (Exception e) {
			log.error("reopen indexReader failed when create post["
					+ post.getId() + "] index");
		}
		return null;
	}
}
