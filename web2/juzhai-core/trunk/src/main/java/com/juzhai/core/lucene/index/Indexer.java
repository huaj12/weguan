package com.juzhai.core.lucene.index;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;

public interface Indexer<D> {

	void addIndex(D indexObj, boolean isCommit) throws CorruptIndexException,
			IOException;

	void deleteIndex(D indexObj, boolean isCommit)
			throws CorruptIndexException, IOException;

	void updateIndex(D indexObj, boolean isCommit)
			throws CorruptIndexException, IOException;

	void commit() throws CorruptIndexException, IOException;
}
