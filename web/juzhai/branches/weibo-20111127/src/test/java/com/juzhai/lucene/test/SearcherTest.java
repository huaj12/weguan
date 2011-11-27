package com.juzhai.lucene.test;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.core.lucene.searcher.IndexSearcherManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class SearcherTest {

	@Autowired
	private IActService actService;
	@Autowired
	private Indexer<Act> actIndexer;
	@Autowired
	private IndexSearcherManager searcherManager;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void searchTest() throws CorruptIndexException, IOException,
			InterruptedException {
		// Act act = actService.getActById(298L);
		Act act = actService.getActById(689L);
		actIndexer.addIndex(act, true);
		// actIndexer.deleteIndex(act, true);
		searcherManager.maybeReopen();
		List<String> results = actService.indexSearchName("喝茶", 10);
		for (String result : results) {
			System.out.println(result);
		}
	}
}
