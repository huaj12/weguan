package com.juzhai.cms.task;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.juzhai.cms.model.RawAd;
import com.juzhai.cms.service.IRawAdService;
import com.juzhai.cms.service.ISpiderUrlService;
import com.juzhai.common.InitData;
import com.juzhai.common.model.City;

public class ImportAdTask implements Callable<Boolean> {
	private String content;
	private IRawAdService rawAdService;
	private CountDownLatch down;
	private ISpiderUrlService spiderUrlService;

	public ImportAdTask(String content, IRawAdService rawAdService,
			CountDownLatch down, ISpiderUrlService spiderUrlService) {
		this.content = content;
		this.rawAdService = rawAdService;
		this.down = down;
		this.spiderUrlService = spiderUrlService;
	}

	@Override
	public Boolean call() throws Exception {
		RawAd rawAd = new RawAd();
		try {
			rawAd.setTitle(findContent(content, "title"));
			rawAd.setImg(findContent(content, "img"));
			rawAd.setAddress(findContent(content, "address"));
			City city = InitData.getCityByName(findContent(content, "city"));
			if (null != city) {
				rawAd.setCity(city.getId());
			}
			rawAd.setOriginal(findContent(content, "original"));
			rawAd.setPrice(findContent(content, "price"));
			double discount = 0;
			try {
				discount = Double.valueOf(findContent(content, "discount"));
			} catch (Exception e) {
			}
			Date startDate = getDate(findContent(content, "startDate"));
			Date endDate = getDate(findContent(content, "endDate"));
			rawAd.setStartDate(startDate);
			rawAd.setEndDate(endDate);
			rawAd.setDiscount(discount);
			rawAd.setCircle(findContent(content, "circle"));
			String source = findContent(content, "source");
			rawAd.setSource(source);
			String targetUrl = findContent(content, "targetUrl");
			rawAd.setTargetUrl(targetUrl);
			rawAd.setFromName(findContent(content, "from"));
			rawAd.setFromLink(findContent(content, "fromLink"));
			rawAd.setCategory(findContent(content, "category"));
			rawAd.setStatus(0);
			rawAd.setCreateTime(new Date());
			rawAd.setLastModifyTime(new Date());
			if (StringUtils.isEmpty(targetUrl)) {
				return Boolean.FALSE;
			}
			String MD5_link = DigestUtils.md5Hex(targetUrl);
			rawAd.setMd5TargetUrl(MD5_link);
			// 已经爬取过的链接
			if (spiderUrlService.isUrlExist(MD5_link)) {
				return Boolean.FALSE;
			}
			rawAdService.createRawAd(rawAd);
		} catch (Throwable e) {
			return Boolean.FALSE;
		} finally {
			down.countDown();
		}
		return Boolean.TRUE;
	}

	private String findContent(String content, String name) {
		String result = "";
		try {
			String str[] = content.split("\\r\\n");
			for (String s : str) {
				if (s.split("=")[0].startsWith(name)) {
					result = s.substring(name.length() + 1);
					break;
				}
			}
		} catch (Exception e) {
		}
		return result;
	}

	private Date getDate(String date) {
		try {
			if (StringUtils.isNotEmpty(date)) {
				return DateUtils.parseDate(date,
						new String[] { "yyyy-MM-dd HH:mm:ss" });
			} else {
				return null;
			}
		} catch (ParseException e) {
			return null;
		}
	}

}
