package com.juzhai.search.schedule;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class ActIndexOptimizationAndCommitHandler extends
		AbstractScheduleHandler {

	@Autowired
	private IndexWriter actIndexWriter;

	@Override
	protected void doHandle() {
		if (actIndexWriter != null) {
			if (log.isDebugEnabled()) {
				log.debug("start optimize index");
			}
			try {
				actIndexWriter.optimize();
				actIndexWriter.commit();
			} catch (CorruptIndexException e) {
				log.error("act index optimize and commit error.", e);
			} catch (IOException e) {
				log.error("act index optimize and commit error.", e);
			}
		}
	}

}
