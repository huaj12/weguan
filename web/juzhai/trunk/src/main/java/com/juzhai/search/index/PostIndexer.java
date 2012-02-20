package com.juzhai.search.index;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.core.util.DateFormat;
import com.juzhai.post.model.Post;

@Service
public class PostIndexer implements Indexer<Post> {
	@Autowired
	private IndexWriter postIndexWriter;

	@Override
	public void addIndex(Post post, boolean isCommit)
			throws CorruptIndexException, IOException {
		Document doc = buildDoc(post);
		postIndexWriter.addDocument(doc);
		if (isCommit) {
			commit();
		}

	}

	private Document buildDoc(Post post) {
		String dateTime = null;
		try {
			dateTime = DateFormat.SDF.format(post.getDateTime());
		} catch (Exception e) {
			dateTime = "";
		}
		Document doc = new Document();
		doc.add(new Field("id", post.getId().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", post.getContent(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("place", post.getPlace(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("dateTime", dateTime, Field.Store.NO,
				Field.Index.ANALYZED));
		doc.add(new Field("categoryId", post.getCategoryId().toString(),
				Field.Store.NO, Field.Index.ANALYZED));
		return doc;
	}

	@Override
	public void deleteIndex(Post post, boolean isCommit)
			throws CorruptIndexException, IOException {
		postIndexWriter
				.deleteDocuments(new Term("id", post.getId().toString()));

	}

	@Override
	public void updateIndex(Post post, boolean isCommit)
			throws CorruptIndexException, IOException {
		postIndexWriter.updateDocument(new Term("id", post.getId().toString()),
				buildDoc(post));
		if (isCommit) {
			commit();
		}

	}

	@Override
	public void commit() throws CorruptIndexException, IOException {
		postIndexWriter.commit();
	}

}
