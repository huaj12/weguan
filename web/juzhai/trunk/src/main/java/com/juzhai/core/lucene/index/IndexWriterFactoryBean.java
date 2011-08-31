package com.juzhai.core.lucene.index;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.FactoryBean;

public abstract class IndexWriterFactoryBean implements
		FactoryBean<IndexWriter> {

	private String indexPath;
	private String version;
	private Analyzer analyzer;

	@Override
	public IndexWriter getObject() throws Exception {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				Version.valueOf(version), analyzer);
		// indexWriterConfig.setMaxBufferedDeleteTerms(maxBufferedDeleteTerms);
		// indexWriterConfig.setMaxBufferedDocs(maxBufferedDocs);
		// indexWriterConfig.setMaxThreadStates(maxThreadStates);
		// indexWriterConfig.setMergedSegmentWarmer(mergeSegmentWarmer);
		// indexWriterConfig.setMergePolicy(mergePolicy);
		// indexWriterConfig.setMergeScheduler(mergeScheduler);
		// indexWriterConfig.setRAMBufferSizeMB(ramBufferSizeMB);
		// indexWriterConfig.setReaderPooling(readerPooling);
		// indexWriterConfig.setReaderTermsIndexDivisor(divisor);
		// indexWriterConfig.setSimilarity(similarity);
		// indexWriterConfig.setTermIndexInterval(interval);
		// indexWriterConfig.setWriteLockTimeout(writeLockTimeout);
		// indexWriterConfig.setDefaultWriteLockTimeout(writeLockTimeout);
		return new IndexWriter(openDirectory(indexPath), indexWriterConfig);
	}

	@Override
	public Class<IndexWriter> getObjectType() {
		return IndexWriter.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	protected abstract Directory openDirectory(String indexPath)
			throws IOException;

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
}
