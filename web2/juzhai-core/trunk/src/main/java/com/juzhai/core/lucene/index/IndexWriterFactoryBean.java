package com.juzhai.core.lucene.index;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.FactoryBean;

public abstract class IndexWriterFactoryBean implements
		FactoryBean<IndexWriter> {

	private IndexWriter reference;

	private String indexPath;
	private String version;
	private Analyzer analyzer;

	private Integer maxBufferedDeleteTerms;
	private Integer maxBufferedDocs;
	private Integer maxThreadStates;
	private Double ramBufferSizeMB;
	private Boolean readerPooling;
	private Integer writeLockTimeout;

	@Override
	public IndexWriter getObject() throws Exception {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				Version.valueOf(version), analyzer);
		if (null != maxBufferedDeleteTerms) {
			indexWriterConfig.setMaxBufferedDeleteTerms(maxBufferedDeleteTerms);
		}
		indexWriterConfig.setMaxBufferedDocs(maxBufferedDocs);
		indexWriterConfig.setMaxThreadStates(maxThreadStates);
		// indexWriterConfig.setMergeScheduler(mergeScheduler);
		indexWriterConfig.setRAMBufferSizeMB(ramBufferSizeMB);
		indexWriterConfig.setReaderPooling(readerPooling);
		// indexWriterConfig.setReaderTermsIndexDivisor(divisor);
		// indexWriterConfig.setTermIndexInterval(interval);
		indexWriterConfig.setWriteLockTimeout(writeLockTimeout);
		reference = new IndexWriter(openDirectory(indexPath), indexWriterConfig);
		return reference;
	}

	@Override
	public Class<IndexWriter> getObjectType() {
		return IndexWriter.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void close() throws CorruptIndexException, IOException {
		if (null != reference) {
			reference.close();
		}
	}

	protected abstract Directory openDirectory(String indexPath)
			throws IOException;

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public void setMaxBufferedDeleteTerms(Integer maxBufferedDeleteTerms) {
		this.maxBufferedDeleteTerms = maxBufferedDeleteTerms;
	}

	public void setMaxBufferedDocs(Integer maxBufferedDocs) {
		this.maxBufferedDocs = maxBufferedDocs;
	}

	public void setMaxThreadStates(Integer maxThreadStates) {
		this.maxThreadStates = maxThreadStates;
	}

	public void setRamBufferSizeMB(Double ramBufferSizeMB) {
		this.ramBufferSizeMB = ramBufferSizeMB;
	}

	public void setReaderPooling(Boolean readerPooling) {
		this.readerPooling = readerPooling;
	}

	public void setWriteLockTimeout(Integer writeLockTimeout) {
		this.writeLockTimeout = writeLockTimeout;
	}
}
