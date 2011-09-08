package com.juzhai.core.lucene.searcher;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.IndexSearcher;

public class IndexSearcherTemplate {

	private final Log log = LogFactory.getLog(getClass());

	private IndexSearcherManager searcherManager;

	public interface SearcherCallback {
		<T extends Object> T doCallback(IndexSearcher indexSearcher)
				throws IOException;
	}

	public <T extends Object> T excute(SearcherCallback callback) {
		T result = null;
		IndexSearcher indexSearcher = searcherManager.get();
		try {
			result = callback.doCallback(indexSearcher);
		} catch (IOException e) {
			log.error("do callback failed.", e);
		} finally {
			try {
				searcherManager.release(indexSearcher);
			} catch (IOException e) {
				log.error("release searcher error.", e);
			}
		}
		return result;
	}

	public void setSearcherManager(IndexSearcherManager searcherManager) {
		this.searcherManager = searcherManager;
	}
}
