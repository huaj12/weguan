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
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.model.Post;

@Service
public class PostIndexer implements Indexer<Post> {
	@Autowired
	private IndexWriter postIndexWriter;
	@Autowired
	private IProfileService profileService;
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
		Profile profile=profileService.getProfile(post.getCreateUid());
		Document doc = new Document();
		doc.add(new Field("id", post.getId().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", post.getContent(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("place", post.getPlace(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("dateTime", dateTime, Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("categoryId", post.getCategoryId().toString(),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		//用户属性
		doc.add(new Field("uid", profile.getUid().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("name", profile.getNickname().toString(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("city", profile.getCity().toString(), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("town", profile.getTown().toString(), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("gender", profile.getGender().toString(),
				Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("age", profile.getBirthYear().toString(),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("education", profile.getEducation().toString(),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("minMonthlyIncome", profile.getMinMonthlyIncome()
				.toString(), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("maxMonthlyIncome", profile.getMaxMonthlyIncome()
				.toString(), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("height", profile.getHeight().toString(),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
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
