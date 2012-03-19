package com.juzhai.preference.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.cms.controller.form.PreferenceForm;
import com.juzhai.cms.controller.form.PreferenceListForm;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.mapper.PreferenceMapper;
import com.juzhai.passport.model.Preference;
import com.juzhai.passport.model.PreferenceExample;
import com.juzhai.preference.exception.InputPreferenceException;
import com.juzhai.preference.service.IPreferenceService;

@Service
public class PreferenceService implements IPreferenceService {

	private final Log log = LogFactory.getLog(getClass());

	@Value("${preference.name.length.min}")
	private int preferenceNameLengthMin;
	@Value("${preference.name.length.max}")
	private int preferenceNameLengthMax;
	@Value("${preference.cache.expire.time}")
	private int preferenceCacheExpireTime = 0;
	@Value("${preference.list.cache.expire.time}")
	private int preferenceListCacheExpireTime = 0;
	@Autowired
	private PreferenceMapper preferenceMapper;
	@Autowired
	private MemcachedClient memcachedClient;

	@Override
	public void addPreference(PreferenceForm form)
			throws InputPreferenceException {
		validatePreference(form);
		Preference preference = new Preference();
		preference.setCreateTime(new Date());
		preference.setDefunct(false);
		preference.setInput(form.getInputString());
		preference.setLastModifyTime(new Date());
		preference.setName(form.getName());
		preference.setOpen(form.getOpen());
		preference.setSequence(getPreferenceCount() + 1);
		preference.setType(form.getType());
		preferenceMapper.insertSelective(preference);
		// 更新缓存
		cacheListPreference();
	}

	private void validatePreference(PreferenceForm form)
			throws InputPreferenceException {
		if (StringUtils.isEmpty(form.getName())) {
			throw new InputPreferenceException(
					InputPreferenceException.PREFERENCE_NAME_IS_NULL);
		}
		// 验证name字数 2-200
		int nameLength = StringUtil.chineseLength(form.getName());
		if (nameLength < preferenceNameLengthMin
				|| nameLength > preferenceNameLengthMax) {
			throw new InputPreferenceException(
					InputPreferenceException.PREFERENCE_NAME_IS_INVALID);
		}
		String inputString = null;
		try {
			inputString = form.getInput().toJsonString();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(inputString)) {
			throw new InputPreferenceException(
					InputPreferenceException.PREFERENCE_INPUT_IS_NULL);
		}
		form.setInputString(inputString);
	}

	private int getPreferenceCount() {
		return preferenceMapper.countByExample(new PreferenceExample());
	}

	@Override
	public List<Preference> listPreference() {
		String key = MemcachedKeyGenerator.genPreferenceListCacheKey();
		List<Preference> preferences = null;
		try {
			preferences = memcachedClient.get(key);
			if (CollectionUtils.isNotEmpty(preferences)) {
				return preferences;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return cacheListPreference();
	}

	private List<Preference> cacheListPreference() {
		String key = MemcachedKeyGenerator.genPreferenceListCacheKey();
		List<Preference> preferences = null;
		PreferenceExample example = new PreferenceExample();
		example.createCriteria().andDefunctEqualTo(false);
		example.setOrderByClause("sequence");
		preferences = preferenceMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(preferences)) {
			try {
				memcachedClient.set(key, preferenceListCacheExpireTime,
						preferences);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return preferences;
		} else {
			return Collections.emptyList();
		}

	}

	@Override
	public void shieldPreference(long id) {
		Preference preference = new Preference();
		preference.setId(id);
		preference.setDefunct(true);
		preferenceMapper.updateByPrimaryKeySelective(preference);
		cacheListPreference();
	}

	@Override
	public void updatePreferenceSort(PreferenceListForm preferenceListForm) {
		List<PreferenceForm> forms = preferenceListForm.getPreferenceForms();
		if (CollectionUtils.isNotEmpty(forms)) {
			for (PreferenceForm form : forms) {
				Integer sequence = form.getSequence();
				if (sequence == null) {
					sequence = 0;
				}
				Long id = form.getId();
				if (id == null) {
					continue;
				}
				Preference p = new Preference();
				p.setId(id);
				p.setSequence(sequence);
				if (StringUtils.isNotEmpty(form.getName())) {
					p.setName(form.getName());
				}
				p.setLastModifyTime(new Date());
				preferenceMapper.updateByPrimaryKeySelective(p);
			}
		}
		cacheListPreference();
	}

	@Override
	public void updatePreference(PreferenceForm form)
			throws InputPreferenceException {
		validatePreference(form);
		Preference preference = new Preference();
		preference.setId(form.getId());
		preference.setName(form.getName());
		preference.setInput(form.getInputString());
		preference.setOpen(form.getOpen());
		preference.setType(form.getType());
		preference.setLastModifyTime(new Date());
		preferenceMapper.updateByPrimaryKeySelective(preference);
		cacheListPreference();
	}

	@Override
	public Preference getPreference(Long id) {
		String key = MemcachedKeyGenerator.genPreferenceCacheKey(id);
		if (id == null) {
			return null;
		}
		Preference preference = null;
		try {
			preference = memcachedClient.get(key);
			if (null != preference) {
				return preference;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		preference = preferenceMapper.selectByPrimaryKey(id);
		if (preference != null) {
			try {
				memcachedClient.set(key, preferenceCacheExpireTime, preference);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return preference;
	}
}
