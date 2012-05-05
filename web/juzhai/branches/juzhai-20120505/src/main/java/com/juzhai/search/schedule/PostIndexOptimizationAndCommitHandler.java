package com.juzhai.search.schedule;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class PostIndexOptimizationAndCommitHandler extends
		AbstractScheduleHandler {

	@Autowired
	private IndexWriter postIndexWriter;

	@Override
	protected void doHandle() {
		if (postIndexWriter != null) {
			if (log.isDebugEnabled()) {
				log.debug("start optimize index");
			}
			try {
				postIndexWriter.optimize();
				postIndexWriter.commit();
			} catch (CorruptIndexException e) {
				log.error("post index optimize and commit error.", e);
			} catch (IOException e) {
				log.error("post index optimize and commit error.", e);
			}
		}
	}

}
