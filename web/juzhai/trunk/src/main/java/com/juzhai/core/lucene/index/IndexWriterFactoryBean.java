package com.juzhai.core.lucene.index;

import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.FactoryBean;

public class IndexWriterFactoryBean implements FactoryBean<IndexWriter> {

	private String indexPath;
	
	private boolean ram;
	
	@Override
	public IndexWriter getObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<IndexWriter> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
