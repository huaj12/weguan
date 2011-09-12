package com.juzhai.act.lucene.index;

import java.io.IOException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.core.lucene.index.Indexer;

@Service
public class ActIndexer implements Indexer<Act> {

	@Autowired
	private IndexWriter actIndexWriter;

	@Override
	public void addIndex(Act act, boolean isCommit)
			throws CorruptIndexException, IOException {
		// TODO 分类进行存储，今后再更新
		// StringBuilder categorys = new StringBuilder();
		Document doc = new Document();
		doc.add(new Field("id", act.getId().toString(), Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field("name", act.getName(), Field.Store.YES,
				Field.Index.ANALYZED));
		// doc.add(new Field("categorys", categorys, Field.Store.NO,
		// Field.Index.ANALYZED));
		float boost = (BooleanUtils.isTrue(act.getActive()) ? 5F : 0F)
				+ (act.getPopularity() == null ? 1 : act.getPopularity())
				* 0.1f;
		doc.setBoost(boost);
		actIndexWriter.addDocument(doc);
		if (isCommit) {
			actIndexWriter.commit();
		}
	}

	@Override
	public void commit() throws CorruptIndexException, IOException {
		actIndexWriter.commit();
	}

}
