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
import com.juzhai.passport.model.Profile;

@Service
public class ProfileIndexer implements Indexer<Profile> {
	@Autowired
	private IndexWriter profileIndexWriter;

	@Override
	public void addIndex(Profile profile, boolean isCommit)
			throws CorruptIndexException, IOException {
		Document doc = buildDoc(profile);
		profileIndexWriter.addDocument(doc);
		if (isCommit) {
			commit();
		}
	}

	private Document buildDoc(Profile profile) {
		Document doc = new Document();
		doc.add(new Field("uid", profile.getUid().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("city", profile.getCity().toString(), Field.Store.NO,
				Field.Index.ANALYZED));
		doc.add(new Field("town", profile.getTown().toString(), Field.Store.NO,
				Field.Index.ANALYZED));
		doc.add(new Field("gender", profile.getGender().toString(),
				Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("age", profile.getBirthYear().toString(),
				Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("education", profile.getEducation().toString(),
				Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("minMonthlyIncome", profile.getMinMonthlyIncome()
				.toString(), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("maxMonthlyIncome", profile.getMaxMonthlyIncome()
				.toString(), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("height", profile.getHeight().toString(),
				Field.Store.NO, Field.Index.ANALYZED));

		return doc;
	}

	@Override
	public void deleteIndex(Profile profile, boolean isCommit)
			throws CorruptIndexException, IOException {
		profileIndexWriter.deleteDocuments(new Term("uid", profile.getUid()
				.toString()));
		if (isCommit) {
			commit();
		}
	}

	@Override
	public void updateIndex(Profile profile, boolean isCommit)
			throws CorruptIndexException, IOException {
		profileIndexWriter.updateDocument(new Term("uid", profile.getUid()
				.toString()), buildDoc(profile));
		if (isCommit) {
			commit();
		}
	}

	@Override
	public void commit() throws CorruptIndexException, IOException {
		profileIndexWriter.commit();
	}

}
