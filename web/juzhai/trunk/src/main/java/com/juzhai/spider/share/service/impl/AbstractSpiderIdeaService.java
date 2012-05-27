package com.juzhai.spider.share.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.jstl.JzDataFunction;
import com.juzhai.core.web.jstl.JzUtilFunction;
import com.juzhai.platform.service.impl.SynchronizeService;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.post.service.impl.IdeaImageService;
import com.juzhai.spider.bean.Domain;
import com.juzhai.spider.exception.SpiderIdeaException;
import com.juzhai.spider.share.ShareRegexConfig;
import com.juzhai.spider.share.service.ISpiderIdeaService;

public abstract class AbstractSpiderIdeaService implements ISpiderIdeaService {
	private final Log log = LogFactory.getLog(SynchronizeService.class);
	@Autowired
	private IdeaImageService ideaImageService;
	@Value("${idea.content.length.max}")
	private int ideaContentLengthMax;
	@Value("${idea.place.length.max}")
	private int ideaPlaceLengthMax;

	@Override
	public RawIdeaForm crawl(String url) throws SpiderIdeaException {
		String joinType = null;
		for (Domain domain : Domain.values()) {
			if (url.indexOf(domain.getUrl()) != -1) {
				joinType = domain.getName();
				break;
			}
		}
		String htmlContent = getContent(url);
		String title = find(htmlContent,
				ShareRegexConfig.REGEXS.get(joinType + "_title"));
		// 标题抓取不到网站样式改版或者url输入有误
		if (StringUtils.isEmpty(title)) {
			throw new SpiderIdeaException(
					SpiderIdeaException.SPIDER_IDEA_URL_IS_ERROR);
		}
		RawIdeaForm form = new RawIdeaForm();
		String charge = find(htmlContent,
				ShareRegexConfig.REGEXS.get(joinType + "_charge"));

		form.setContent(JzUtilFunction.truncate(title,
				ideaContentLengthMax - 3, "..."));
		form.setCategoryId(1l);
		getDetail(form, htmlContent, joinType);
		getPic(form, htmlContent, joinType);
		getCharge(form, charge);
		getAddress(form, htmlContent, joinType);
		getTime(form, htmlContent, joinType);
		getLink(form, htmlContent, joinType, url);
		if (form.getCity() != null && form.getCity() != 0) {
			form.setCityName(JzDataFunction.cityName(form.getCity()));
		}
		if (form.getTown() != null && form.getTown() != -1) {
			form.setTownName(JzDataFunction.townName(form.getTown()));
		}
		if (form.getPlace() != null) {
			form.setPlace(JzUtilFunction.truncate(form.getPlace(),
					ideaPlaceLengthMax - 3, "..."));
		}
		return form;

	}

	protected void getLink(RawIdeaForm form, String content, String joinType,
			String url) throws SpiderIdeaException {
		form.setLink(url);

	}

	protected String getContent(String url) throws SpiderIdeaException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet accessget = new HttpGet(url);
		String content = "";
		try {
			accessget
					.setHeader("User-Agent",
							"Mozilla/5.0 (Windows NT 5.1; rv:12.0) Gecko/20100101 Firefox/12.0");
			accessget
					.setHeader("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			accessget.setHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			content = httpclient.execute(accessget, responseHandler);
		} catch (Exception e) {
			log.error("spider is error url=" + url, e);
			throw new SpiderIdeaException(
					SpiderIdeaException.SPIDER_IDEA_TIME_OUT);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	protected List<String> findList(String content, String regEx) {
		List<String> list = new ArrayList<String>();
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(content);
		while (mat.find()) {
			list.add(mat.group(1));
		}
		return list;
	}

	protected String find(String content, String regEx) {
		List<String> list = findList(content, regEx);
		return list.size() > 0 ? list.get(0) : null;
	}

	protected void uploadPic(RawIdeaForm form, String pic)
			throws SpiderIdeaException {
		if (StringUtils.isEmpty(pic)) {
			throw new SpiderIdeaException(
					SpiderIdeaException.SPIDER_IDEA_URL_IS_ERROR);
		}
		try {
			String[] str = ideaImageService.uploadTempIdeaImg(pic);
			form.setPic(str[1]);
			form.setPicWeb(str[0]);
		} catch (UploadImageException e) {
			throw new SpiderIdeaException(
					SpiderIdeaException.SPIDER_IDEA_IMAGE_IS_ERROR);
		}
	}

	protected void getDetail(RawIdeaForm form, String content, String joinType) {
		String detail = find(content,
				ShareRegexConfig.REGEXS.get(joinType + "_detail"));
		form.setDetail(detail);
	}

	protected void getPic(RawIdeaForm form, String content, String joinType)
			throws SpiderIdeaException {
		String pic = find(content, ShareRegexConfig.REGEXS.get(joinType + "_pic"));
		uploadPic(form, pic);
	}

	protected abstract void getCharge(RawIdeaForm form, String charge);

	protected abstract void getAddress(RawIdeaForm form, String content,
			String joinType);

	protected abstract void getTime(RawIdeaForm form, String content,
			String joinType);
}
