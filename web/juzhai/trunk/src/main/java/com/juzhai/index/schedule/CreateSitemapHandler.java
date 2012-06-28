package com.juzhai.index.schedule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.SystemConfig;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.core.util.DateFormat;
import com.juzhai.index.bean.SiteMap;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;

@Component
public class CreateSitemapHandler extends AbstractScheduleHandler {
	@Value("${sitemap.file.path}")
	private String filePath;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private IdeaMapper ideamapper;
	@Value("${sitemap.page.count}")
	private int sitemapPageCount;
	@Value("${sitemap.web.path}")
	private String sitemapWebPath;
	private List<SiteMap> siteMaps = new ArrayList<SiteMap>();
	private String domian = SystemConfig.getDomain();

	@Override
	protected void doHandle() {
		// 首先判断sitemap是否存在如果不存在查询所有数据。存在则查询一天前数据
		File index = new File(filePath + "sitemap_index.xml");
		int i = 0;
		if (index.exists()) {
			getUpdateDate();
			Document index_doc = parseDocument(filePath + "sitemap_index.xml");
			Element index_root = index_doc.getRootElement();
			int indexSize = index_root.elements().size();
			if (indexSize > 0) {
				i = indexSize - 1;
			}
			Document doc = parseDocument(filePath + "sitemap_index_" + i
					+ ".xml");
			Element root = doc.getRootElement();
			// sitemap大于20000则i++
			if (root.elements().size() == sitemapPageCount) {
				i++;
			}
		} else {
			// 获取所有数据
			getAllDate();
			// 生成sitemap_index
			createIndexDoc(i);
		}
		// 写入数据到sitemap里
		siteMaps.add(new SiteMap(domian + "/searchusers", DateFormat.SDF
				.format(new Date()), "1.0", "always"));
		siteMaps.add(new SiteMap(domian + "/showideas", DateFormat.SDF
				.format(new Date()), "1.0", "always"));
		writeDocument(i);

	}

	private void writeDocument(int index) {
		Document doc = parseDocument(filePath + "sitemap_index_" + index
				+ ".xml");
		for (int i = 0; i < siteMaps.size(); i++) {
			if (doc == null) {
				doc = DocumentHelper.createDocument();
			}
			SiteMap siteMap = siteMaps.get(i);
			Element root = doc.getRootElement();
			if (root == null) {
				doc.addElement("urlset");
				root = doc.getRootElement();
			}
			int size = 0;
			try {
				size = root.elements().size();
			} catch (Exception e) {
			}
			if (size < sitemapPageCount) {
				createDoc(root, siteMap);
				if (size == (sitemapPageCount - 1)) {
					xmlWriter(doc, filePath + "sitemap_index_" + index + ".xml");
				} else if (siteMaps.size() - 1 == i) {
					xmlWriter(doc, filePath + "sitemap_index_" + index + ".xml");
				}
			} else {
				index++;
				createIndexDoc(index);
				doc = parseDocument(filePath + "sitemap_index_" + index
						+ ".xml");
				createDoc(root, siteMap);
			}

		}
	}

	private void createDoc(Element element, SiteMap siteMap) {
		Element urlElement = element.addElement("url");
		urlElement.addElement("loc").addText(siteMap.getUrl());
		urlElement.addElement("lastmod").addText(siteMap.getTime());
		urlElement.addElement("changefreq").addText(siteMap.getChangefreq());
		urlElement.addElement("priority").addText(siteMap.getPriority());
	}

	private void createIndexDoc(int index) {
		Document docment = parseDocument(filePath + "sitemap_index.xml");
		Element root = null;
		if (docment == null) {
			docment = DocumentHelper.createDocument();
			root = docment.addElement("sitemapindex");
		} else {
			root = docment.getRootElement();
		}
		Element ele = root.addElement("sitemap");
		ele.addElement("loc").addText(
				domian + sitemapWebPath + "/sitemap_index_" + index + ".xml");
		ele.addElement("lastmod").addText(DateFormat.SDF.format(new Date()));
		xmlWriter(docment, filePath + "sitemap_index.xml");
	}

	private void getAllDate() {

		List<Profile> profiles = profileMapper
				.selectByExample(new ProfileExample());
		for (Profile profile : profiles) {
			SiteMap siteMap = new SiteMap();
			siteMap.setUrl(domian + "/home/" + profile.getUid());
			siteMap.setTime(DateFormat.SDF.format(profile.getLastModifyTime()));
			siteMap.setPriority("0.7");
			siteMaps.add(siteMap);
		}
		// ////////////////////////////////////////////////////////////////////////////
		PostExample postExample = new PostExample();
		postExample.createCriteria().andDefunctEqualTo(false)
				.andVerifyTypeEqualTo(1);
		List<Post> posts = postMapper.selectByExample(postExample);
		for (Post post : posts) {
			SiteMap siteMap = new SiteMap();
			siteMap.setUrl(domian + "/post/" + post.getId());
			siteMap.setTime(DateFormat.SDF.format(post.getLastModifyTime()));
			siteMap.setPriority("0.8");
			siteMaps.add(siteMap);
		}
		// ////////////////////////////////////////////////////////////////////////////

		List<Idea> ideas = ideamapper.selectByExample(new IdeaExample());
		for (Idea idea : ideas) {
			SiteMap siteMap = new SiteMap();
			siteMap.setUrl(domian + "/idea/" + idea.getId());
			siteMap.setTime(DateFormat.SDF.format(idea.getLastModifyTime()));
			siteMap.setPriority("0.9");
			siteMaps.add(siteMap);
		}
	}

	private void xmlWriter(Document document, String path) {
		try {
			XMLWriter output = new XMLWriter(new FileWriter(new File(path)));
			output.write(document);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getUpdateDate() {

		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);

		ProfileExample profileExample = new ProfileExample();
		profileExample.createCriteria().andLastModifyTimeGreaterThan(
				date.getTime());
		List<Profile> profiles = profileMapper.selectByExample(profileExample);
		for (Profile profile : profiles) {
			SiteMap siteMap = new SiteMap();
			siteMap.setUrl(domian + "/home/" + profile.getUid());
			siteMap.setTime(DateFormat.SDF.format(profile.getCreateTime()));
			siteMap.setPriority("0.7");
			siteMaps.add(siteMap);
		}
		// ////////////////////////////////////////////////////////////////////////////
		PostExample postExample = new PostExample();
		postExample.createCriteria()
				.andLastModifyTimeGreaterThan(date.getTime())
				.andDefunctEqualTo(false).andVerifyTypeEqualTo(1);
		List<Post> posts = postMapper.selectByExample(postExample);
		for (Post post : posts) {
			SiteMap siteMap = new SiteMap();
			siteMap.setUrl(domian + "/post/" + post.getId());
			siteMap.setTime(DateFormat.SDF.format(post.getCreateTime()));
			siteMap.setPriority("0.8");
			siteMaps.add(siteMap);
		}
		// ////////////////////////////////////////////////////////////////////////////
		IdeaExample ideaExample = new IdeaExample();
		ideaExample.createCriteria().andLastModifyTimeGreaterThan(
				date.getTime());
		List<Idea> ideas = ideamapper.selectByExample(ideaExample);
		for (Idea idea : ideas) {
			SiteMap siteMap = new SiteMap();
			siteMap.setUrl(domian + "/idea/" + idea.getId());
			siteMap.setTime(DateFormat.SDF.format(idea.getCreateTime()));
			siteMap.setPriority("0.9");
			siteMaps.add(siteMap);
		}
	}

	private Document parseDocument(String xmlFilePath) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(xmlFilePath));
		} catch (DocumentException e) {
		}
		return document;
	}

}
