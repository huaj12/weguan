package com.juzhai.core.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class FSDirectoryIndexWriter extends IndexWriterFactoryBean {

	@Override
	protected Directory openDirectory(String indexPath) throws IOException {
		File file = new File(indexPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		return FSDirectory.open(file);
	}

}
