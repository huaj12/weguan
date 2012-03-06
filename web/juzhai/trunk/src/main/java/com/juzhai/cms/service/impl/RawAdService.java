package com.juzhai.cms.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.cms.exception.RawAdInputException;
import com.juzhai.cms.mapper.RawAdMapper;
import com.juzhai.cms.model.RawAd;
import com.juzhai.cms.model.RawAdExample;
import com.juzhai.cms.model.RawAdExample.Criteria;
import com.juzhai.cms.service.IRawAdService;
import com.juzhai.cms.service.ISpiderUrlService;
import com.juzhai.cms.task.ImportAdTask;
import com.juzhai.core.Constants;
import com.juzhai.core.dao.Limit;
import com.juzhai.post.exception.InputAdException;
import com.juzhai.post.service.IAdService;

@Service
public class RawAdService implements IRawAdService {
	private final Log log = LogFactory.getLog(getClass());
	private final String SEPARATOR = "-------------------------------------------------";

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private RawAdMapper rawAdMapper;
	@Autowired
	private ISpiderUrlService spiderUrlService;
	@Autowired
	private IAdService adService;

	@Override
	public int importAd(MultipartFile rawAd) throws RawAdInputException {
		if (rawAd == null || rawAd.getSize() == 0) {
			// 导入文件不能为空
			throw new RawAdInputException(
					RawAdInputException.RAW_AD_FILE_IS_NULL);
		}
		StringBuilder fileContent = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					rawAd.getInputStream(), Constants.UTF8));
			String line;
			while ((line = br.readLine()) != null) {
				fileContent.append(line);
				fileContent.append("\r\n");
			}
		} catch (Exception e) {
			log.error("importAd is error." + e.getMessage());
			// 读取文件失败
			throw new RawAdInputException(
					RawAdInputException.RAW_AD_READ_FILE_IS_INVALID);
		}
		String[] contents = fileContent.toString().split(SEPARATOR);
		if (contents == null || contents.length == 0) {
			// 文件没有内容或者内容格式错误
			throw new RawAdInputException(
					RawAdInputException.RAW_AD_FILE_CONTENT_INVALID);

		}
		CountDownLatch down = new CountDownLatch(contents.length);
		AtomicInteger index = new AtomicInteger();
		for (String content : contents) {
			Future<Boolean> future = taskExecutor.submit(new ImportAdTask(
					content, this, down, spiderUrlService));
			try {
				if (future.get() != null && future.get().booleanValue()) {
					index.getAndIncrement();
				}
			} catch (Exception e) {
				log.error("get future is error." + e.getMessage());
			}
		}
		try {
			down.await();
		} catch (InterruptedException e) {
			log.error("importAd down.await()." + e.getMessage());
		}
		return index.get();
	}

	@Override
	public void createRawAd(RawAd rawAd) {
		if (null == rawAd) {
			return;
		}
		try {
			rawAdMapper.insert(rawAd);
		} catch (Exception e) {
			log.error("createRawAd is error." + e.getMessage());
		}
	}

	@Override
	public boolean isUrlExist(String md5Link) {
		RawAdExample example = new RawAdExample();
		example.createCriteria().andMd5TargetUrlEqualTo(md5Link);
		return rawAdMapper.countByExample(example) > 0 ? true : false;

	}

	@Override
	public List<RawAd> showRawAdList(int firstResult, int maxResults) {
		RawAdExample example = new RawAdExample();
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("start_date desc,end_date asc");
		return rawAdMapper.selectByExample(example);
	}

	@Override
	public int countRawAd() {
		RawAdExample example = new RawAdExample();
		return rawAdMapper.countByExample(example);
	}

	@Override
	public List<RawAd> searchRawAd(String status, Long cityId, String source,
			String category, int firstResult, int maxResults) {
		RawAdExample example = getSearchRawAdExample(status, cityId, source,
				category);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("start_date desc,end_date asc");
		return rawAdMapper.selectByExample(example);
	}

	private RawAdExample getSearchRawAdExample(String status, Long cityId,
			String source, String category) {
		RawAdExample example = new RawAdExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(status)) {
			if ("0".equals(status)) {
				criteria.andStatusEqualTo(0);
			} else if ("1".equals(status)) {
				criteria.andStatusEqualTo(1);
			} else if ("2".equals(status)) {
				criteria.andEndDateLessThan(new Date());
			}
		}
		if (cityId != null && cityId != 0) {
			criteria.andCityEqualTo(cityId);
		}
		if (StringUtils.isNotEmpty(source)) {
			criteria.andSourceEqualTo(source);
		}
		if (StringUtils.isNotEmpty(category)) {
			criteria.andCategoryEqualTo(category);
		}
		return example;
	}

	@Override
	public int countSearchRawAd(String status, Long cityId, String source,
			String category) {
		RawAdExample example = getSearchRawAdExample(status, cityId, source,
				category);
		return rawAdMapper.countByExample(example);
	}

	@Override
	public RawAd getRawAd(long id) {
		return rawAdMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateRawAd(RawAd rawAd) {
		rawAd.setLastModifyTime(new Date());
		rawAdMapper.updateByPrimaryKeySelective(rawAd);
	}

	@Override
	public RawAd getRawAd(String url) {
		RawAdExample example = new RawAdExample();
		example.createCriteria().andTargetUrlEqualTo(url);
		List<RawAd> list = rawAdMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public void remove(long rawId) throws RawAdInputException {
		if (rawId == 0) {
			throw new RawAdInputException(
					RawAdInputException.RAW_AD_REMOVE_ID_IS_NULL);
		}
		rawAdMapper.deleteByPrimaryKey(rawId);
	}

	@Override
	public void publishAd(long rawAdId) throws InputAdException {
		adService.addAd(rawAdId);
		RawAd rawAd = new RawAd();
		rawAd.setId(rawAdId);
		rawAd.setStatus(1);
		updateRawAd(rawAd);
	}

	@Override
	public void removeAllExpiredRawAd() {
		RawAdExample example = new RawAdExample();
		example.createCriteria().andEndDateLessThan(new Date());
		List<RawAd> list = rawAdMapper.selectByExample(example);
		for (RawAd rawAd : list) {
			rawAdMapper.deleteByPrimaryKey(rawAd.getId());
		}
	}

}
