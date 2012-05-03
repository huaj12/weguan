package com.juzhai.search.index;

import java.io.IOException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.core.lucene.index.Indexer;

//@Service
public class ActIndexer implements Indexer<Act> {

	@Autowired
	private IndexWriter actIndexWriter;

	@Override
	public void addIndex(Act act, boolean isCommit)
			throws CorruptIndexException, IOException {
		Document doc = buildDoc(act);
		actIndexWriter.addDocument(doc);
		if (isCommit) {
			commit();
		}
	}

	private Document buildDoc(Act act) {
		Document doc = new Document();
		doc.add(new Field("id", act.getId().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("name", act.getName(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("keywords",
				null == act.getKeyWords() ? StringUtils.EMPTY : act
						.getKeyWords(), Field.Store.NO, Field.Index.ANALYZED));
		float boost = (BooleanUtils.isTrue(act.getActive()) ? 5F : 0F)
				+ (act.getPopularity() == null ? 1 : act.getPopularity())
				* 0.1f;
		doc.setBoost(boost);
		return doc;
	}

	@Override
	public void deleteIndex(Act act, boolean isCommit)
			throws CorruptIndexException, IOException {
		actIndexWriter.deleteDocuments(new Term("id", act.getId().toString()));
		if (isCommit) {
			commit();
		}
	}

	@Override
	public void updateIndex(Act act, boolean isCommit)
			throws CorruptIndexException, IOException {
		actIndexWriter.updateDocument(new Term("id", act.getId().toString()),
				buildDoc(act));
		if (isCommit) {
			commit();
		}
	}

	@Override
	public void commit() throws CorruptIndexException, IOException {
		actIndexWriter.commit();
	}
}
