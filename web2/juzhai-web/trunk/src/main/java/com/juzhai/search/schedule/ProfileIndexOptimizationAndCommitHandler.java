package com.juzhai.search.schedule;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class ProfileIndexOptimizationAndCommitHandler extends
		AbstractScheduleHandler {

	@Autowired
	private IndexWriter profileIndexWriter;

	@Override
	protected void doHandle() {
		if (profileIndexWriter != null) {
			if (log.isDebugEnabled()) {
				log.debug("start optimize index");
			}
			try {
				profileIndexWriter.optimize();
				profileIndexWriter.commit();
			} catch (CorruptIndexException e) {
				log.error("profile index optimize and commit error.", e);
			} catch (IOException e) {
				log.error("profile index optimize and commit error.", e);
			}
		}
	}

}
