package com.juzhai.preference.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.cms.controller.form.PreferenceForm;
import com.juzhai.cms.controller.form.PreferenceListForm;
import com.juzhai.core.util.StringUtil;
import com.juzhai.preference.InitData;
import com.juzhai.preference.bean.Input;
import com.juzhai.preference.bean.InputType;
import com.juzhai.preference.bean.PreferenceType;
import com.juzhai.preference.exception.InputPreferenceException;
import com.juzhai.preference.mapper.PreferenceMapper;
import com.juzhai.preference.model.Preference;
import com.juzhai.preference.model.PreferenceExample;
import com.juzhai.preference.service.IPreferenceService;

@Service
public class PreferenceService implements IPreferenceService {
	@Value("${preference.name.length.min}")
	private int preferenceNameLengthMin;
	@Value("${preference.name.length.max}")
	private int preferenceNameLengthMax;
	@Autowired
	private PreferenceMapper preferenceMapper;

	@Override
	public void addPreference(PreferenceForm form)
			throws InputPreferenceException {
		validatePreference(form);
		handleDefaultAnswer(form);
		Preference preference = new Preference();
		preference.setCreateTime(new Date());
		preference.setDefunct(false);
		preference.setInput(form.getInputString());
		preference.setLastModifyTime(new Date());
		preference.setName(form.getName());
		preference.setOpen(form.getOpen());
		preference.setSequence(getPreferenceCount() + 1);
		preference.setType(form.getType());
		preference.setDefaultAnswer(StringUtils.join(form.getDefaultAnswer(),
				StringUtil.separator));
		preference.setOpenDescription(form.getOpenDescription());
		preferenceMapper.insertSelective(preference);
	}

	private void handleDefaultAnswer(PreferenceForm form) {
		Input input = form.getInput();
		if (InputType.MINANDMAX.getType() == input.getInputType()) {
			String str[] = form.getDefaultAnswer();
			int min = 0;
			int max = 0;
			try {
				min = Integer.parseInt(str[0]);
			} catch (Exception e) {
			}
			try {
				max = Integer.parseInt(str[1]);
			} catch (Exception e) {
			}
			form.setDefaultAnswer(new String[] {
					String.valueOf(Math.min(min, max)),
					String.valueOf(Math.max(min, max)) });
		}
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
	public List<Preference> listCachePreference() {
		List<Preference> list = new ArrayList<Preference>();
		list.addAll(InitData.PREFERENCE_MAP.values());
		return list;
	}

	@Override
	public void shieldPreference(long id) {
		Preference preference = new Preference();
		preference.setId(id);
		preference.setDefunct(true);
		preferenceMapper.updateByPrimaryKeySelective(preference);
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
	}

	@Override
	public void updatePreference(PreferenceForm form)
			throws InputPreferenceException {
		validatePreference(form);
		handleDefaultAnswer(form);
		// 为了设置清空setDefaultAnswer
		Preference preference = getPreference(form.getId());
		if (preference == null) {
			throw new InputPreferenceException(
					InputPreferenceException.ILLEGAL_OPERATION);
		}
		preference.setId(form.getId());
		preference.setName(form.getName());
		preference.setInput(form.getInputString());
		preference.setOpen(form.getOpen());
		preference.setType(form.getType());
		preference.setLastModifyTime(new Date());
		preference.setDefaultAnswer(StringUtils.join(form.getDefaultAnswer(),
				StringUtil.separator));
		preference.setOpenDescription(form.getOpenDescription());
		preferenceMapper.updateByPrimaryKey(preference);
	}

	@Override
	public Preference getPreference(Long id) {
		if (id == null)
			return null;
		return preferenceMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Preference> listPreference() {
		PreferenceExample example = new PreferenceExample();
		example.createCriteria().andDefunctEqualTo(false);
		example.setOrderByClause("sequence");
		return preferenceMapper.selectByExample(example);
	}

	@Override
	public List<Preference> listCacheShowPreference() {
		List<Preference> list = new ArrayList<Preference>();
		for (Preference preference : InitData.PREFERENCE_MAP.values()) {
			if (PreferenceType.FILTER.getType() != preference.getType()) {
				list.add(preference);
			}
		}
		return list;
	}

	@Override
	public void loadPreferenceCache() {
		InitData.PREFERENCE_MAP.clear();
		List<Preference> list = listPreference();
		for (Preference preference : list) {
			InitData.PREFERENCE_MAP.put(preference.getId(), preference);
		}
	}
}
