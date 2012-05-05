package com.juzhai.core.lucene.searcher;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;

public class IndexSearcherManager {

	private IndexWriter indexWriter;

	private IndexSearcher currentSearcher;

	public void init() throws CorruptIndexException, IOException {
		currentSearcher = new IndexSearcher(IndexReader.open(indexWriter, true));
	}

	private boolean reopening;

	private synchronized void startReopen() throws InterruptedException {
		while (reopening) {
			wait();
		}
		reopening = true;
	}

	private synchronized void doneReopen() {
		reopening = false;
		notifyAll();
	}

	public void maybeReopen() throws InterruptedException, IOException {
		startReopen();
		try {
			IndexSearcher searcher = this.get();
			try {
				IndexReader newReader = currentSearcher.getIndexReader()
						.reopen();
				if (newReader != currentSearcher.getIndexReader()) {
					IndexSearcher newSearcher = new IndexSearcher(newReader);
					swapSearcher(newSearcher);
				}
			} finally {
				this.release(searcher);
			}

		} finally {
			doneReopen();
		}
	}

	public synchronized IndexSearcher get() {
		currentSearcher.getIndexReader().incRef();
		return currentSearcher;
	}

	public synchronized void release(IndexSearcher searcher) throws IOException {
		searcher.getIndexReader().decRef();
	}

	private synchronized void swapSearcher(IndexSearcher newSearcher)
			throws IOException {
		release(currentSearcher);
		currentSearcher = newSearcher;
	}

	public void close() throws IOException {
		swapSearcher(null);
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		this.indexWriter = indexWriter;
	}
}
