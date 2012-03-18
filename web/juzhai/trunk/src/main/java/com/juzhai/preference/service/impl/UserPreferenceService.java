package com.juzhai.preference.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.controller.form.UserPreferenceForm;
import com.juzhai.passport.controller.form.UserPreferenceListForm;
import com.juzhai.passport.mapper.UserPreferenceMapper;
import com.juzhai.passport.model.UserPreference;
import com.juzhai.passport.model.UserPreferenceExample;
import com.juzhai.preference.exception.InputUserPreferenceException;
import com.juzhai.preference.service.IUserPreferenceService;

@Service
public class UserPreferenceService implements IUserPreferenceService {
	@Value("${user.preference.answer.length.max}")
	private int userPreferenceAnswerLengthMax;
	@Value("${user.preference.description.length.max}")
	private int userPreferenceDescriptionLengthMax;
	@Autowired
	private UserPreferenceMapper userPreferenceMapper;

	@Override
	public void addUserPreference(
			UserPreferenceListForm userPreferenceListForm, Long uid)
			throws InputUserPreferenceException {
		List<UserPreferenceForm> userPreferenceForms = userPreferenceListForm
				.getUserPreferences();
		for (UserPreferenceForm userPreferenceForm : userPreferenceForms) {
			if (StringUtils.isEmpty(userPreferenceForm.getAnswer())) {
				continue;
			}
			validate(userPreferenceForm);
			UserPreference userPreference = getUserPreference(
					userPreferenceForm.getPreferenceId(), uid);
			if (userPreference == null) {
				userPreference = new UserPreference();
				userPreference.setAnswer(userPreferenceForm.getAnswer());
				userPreference.setCreateTime(new Date());
				userPreference.setDescription(userPreferenceForm
						.getDescription());
				userPreference.setLastModifyTime(new Date());
				userPreference.setOpen(userPreferenceForm.getOpen());
				userPreference.setPreferenceId(userPreferenceForm
						.getPreferenceId());
				userPreference.setUid(uid);
				userPreferenceMapper.insert(userPreference);
			} else {
				userPreference.setAnswer(userPreferenceForm.getAnswer());
				userPreference.setDescription(userPreferenceForm
						.getDescription());
				userPreference.setOpen(userPreferenceForm.getOpen());
				userPreference.setLastModifyTime(new Date());
				userPreferenceMapper
						.updateByPrimaryKeySelective(userPreference);
			}

		}
	}

	@Override
	public UserPreference getUserPreference(Long preferenceId, Long uid) {
		UserPreferenceExample example = new UserPreferenceExample();
		example.createCriteria().andPreferenceIdEqualTo(preferenceId)
				.andUidEqualTo(uid);
		List<UserPreference> list = userPreferenceMapper
				.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	private void validate(UserPreferenceForm userPreferenceForm)
			throws InputUserPreferenceException {
		int answerLength = StringUtil.chineseLength(userPreferenceForm
				.getAnswer());
		if (answerLength > userPreferenceAnswerLengthMax) {
			throw new InputUserPreferenceException(
					InputUserPreferenceException.PREFERENCE_ANSWER_IS_INVALID);
		}
		int descriptionLength = StringUtil.chineseLength(userPreferenceForm
				.getDescription());
		if (descriptionLength > userPreferenceDescriptionLengthMax) {
			throw new InputUserPreferenceException(
					InputUserPreferenceException.PREFERENCE_DESCRIPTION_IS_INVALID);
		}
		if (userPreferenceForm.getOpen() == null) {
			userPreferenceForm.setOpen(false);
		}
	}

}
