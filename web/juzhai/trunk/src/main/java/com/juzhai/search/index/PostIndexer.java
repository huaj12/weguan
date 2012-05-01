package com.juzhai.search.index;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
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
		Profile profile = profileService.getProfile(post.getCreateUid());
		Document doc = new Document();
		// TODO (review) 基于最终搜索结果展示方式，重新整理一下下面field的策略
		doc.add(new Field("id", post.getId().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("content", post.getContent(), Field.Store.NO,
				Field.Index.ANALYZED));
		if (StringUtils.isEmpty(post.getPlace())) {
			doc.add(new Field("place", post.getPlace(), Field.Store.NO,
					Field.Index.NOT_ANALYZED));
		}
		if (StringUtils.isNotEmpty(dateTime)) {
			doc.add(new Field("dateTime", dateTime, Field.Store.NO,
					Field.Index.NOT_ANALYZED));
		}
		if (post.getCategoryId() != null) {
			doc.add(new Field("categoryId", post.getCategoryId().toString(),
					Field.Store.NO, Field.Index.NOT_ANALYZED));
		}
		// 用户属性
		doc.add(new Field("uid", profile.getUid().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("name", profile.getNickname().toString(),
				Field.Store.YES, Field.Index.ANALYZED));
		if (null != profile.getProvince()) {
			doc.add(new Field("province", profile.getProvince().toString(),
					Field.Store.NO, Field.Index.NOT_ANALYZED));
		}
		if (null != profile.getCity()) {
			doc.add(new Field("city", profile.getCity().toString(),
					Field.Store.NO, Field.Index.NOT_ANALYZED));
		}
		if (null != profile.getTown()) {
			doc.add(new Field("town", profile.getTown().toString(),
					Field.Store.NO, Field.Index.NOT_ANALYZED));
		}
		if (null != profile.getGender()) {
			doc.add(new Field("gender", profile.getGender().toString(),
					Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
		}
		doc.add(new NumericField("age", Field.Store.NO, true)
				.setIntValue(profile.getBirthYear()));
		if (null != profile.getEducation()) {
			doc.add(new Field("education", profile.getEducation().toString(),
					Field.Store.NO, Field.Index.NOT_ANALYZED));
		}
		if (null != profile.getMinMonthlyIncome()) {
			doc.add(new Field("minMonthlyIncome", profile.getMinMonthlyIncome()
					.toString(), Field.Store.NO, Field.Index.NOT_ANALYZED));
		}
		if (null != profile.getMaxMonthlyIncome()) {
			doc.add(new Field("maxMonthlyIncome", profile.getMaxMonthlyIncome()
					.toString(), Field.Store.NO, Field.Index.NOT_ANALYZED));
		}
		if (null != profile.getMinMonthlyIncome()) {
			doc.add(new NumericField("minIncomeNum", Field.Store.NO, true)
					.setIntValue(profile.getMinMonthlyIncome()));
		}
		if (null != profile.getHeight()) {
			doc.add(new NumericField("height", Field.Store.NO, true)
					.setIntValue(profile.getHeight()));
		}
		doc.add(new Field("constellationId", profile.getConstellationId()
				.toString(), Field.Store.NO, Field.Index.NOT_ANALYZED));
		if (null != profile.getHome()) {
			doc.add(new Field("home", profile.getHome(), Field.Store.YES,
					Field.Index.ANALYZED));
		}
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
