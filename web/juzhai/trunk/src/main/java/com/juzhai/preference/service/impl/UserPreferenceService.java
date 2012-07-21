package com.juzhai.preference.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.juzhai.core.util.StringUtil;
import com.juzhai.home.service.IGuessYouService;
import com.juzhai.passport.controller.form.UserPreferenceForm;
import com.juzhai.passport.controller.form.UserPreferenceListForm;
import com.juzhai.passport.controller.view.UserPreferenceView;
import com.juzhai.preference.InitData;
import com.juzhai.preference.bean.Input;
import com.juzhai.preference.bean.PreferenceType;
import com.juzhai.preference.exception.InputUserPreferenceException;
import com.juzhai.preference.mapper.UserPreferenceMapper;
import com.juzhai.preference.model.Preference;
import com.juzhai.preference.model.UserPreference;
import com.juzhai.preference.model.UserPreferenceExample;
import com.juzhai.preference.service.IUserPreferenceService;

@Service
public class UserPreferenceService implements IUserPreferenceService {
	protected final Log log = LogFactory.getLog(getClass());
	@Value("${user.preference.answer.length.max}")
	private int userPreferenceAnswerLengthMax;
	@Value("${user.preference.description.length.max}")
	private int userPreferenceDescriptionLengthMax;
	@Autowired
	private UserPreferenceMapper userPreferenceMapper;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IGuessYouService guessYouService;

	@Override
	public void addUserPreference(
			UserPreferenceListForm userPreferenceListForm, final Long uid)
			throws InputUserPreferenceException {
		boolean flag = false;
		List<UserPreferenceForm> userPreferenceForms = userPreferenceListForm
				.getUserPreferences();
		List<UserPreference> userList = listUserPreference(uid);
		for (UserPreferenceForm userPreferenceForm : userPreferenceForms) {
			validate(userPreferenceForm);
			String answer = "";
			if (ArrayUtils.isNotEmpty(userPreferenceForm.getAnswer())) {
				answer = StringUtils.join(userPreferenceForm.getAnswer(),
						StringUtil.separator);
			}

			UserPreference userPreference = null;
			for (UserPreference user : userList) {
				if (user.getPreferenceId().longValue() == userPreferenceForm
						.getPreferenceId().longValue()) {
					userPreference = user;
					break;
				}
			}
			Preference preference = InitData.PREFERENCE_MAP
					.get(userPreferenceForm.getPreferenceId());
			boolean isShow = false;
			if (preference.getType() == PreferenceType.SHOW.getType()) {
				isShow = true;
			}
			if (userPreference == null) {
				// 问题的类型不属于显示
				if (!isShow) {
					flag = true;
				}
				userPreference = new UserPreference();
				userPreference.setAnswer(answer);
				userPreference.setCreateTime(new Date());
				userPreference.setDescription(userPreferenceForm
						.getDescription());
				userPreference.setLastModifyTime(new Date());
				userPreference.setOpen(userPreferenceForm.isOpen());
				userPreference.setPreferenceId(userPreferenceForm
						.getPreferenceId());
				userPreference.setUid(uid);
				userPreferenceMapper.insertSelective(userPreference);
			} else {
				// 回答有变化且问题的类型不属于显示
				if (answer != null
						&& !answer.equals(userPreference.getAnswer())
						&& !isShow) {
					flag = true;
				}
				userPreference.setAnswer(answer);
				userPreference.setDescription(userPreferenceForm
						.getDescription());
				userPreference.setOpen(userPreferenceForm.isOpen());
				userPreference.setLastModifyTime(new Date());
				userPreferenceMapper.updateByPrimaryKey(userPreference);
			}

		}
		if (flag) {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					guessYouService.clearRescueUsers(uid);
					guessYouService.updateLikeUsers(uid);
				}
			});
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
		if (userPreferenceForm.getPreferenceId() == null) {
			throw new InputUserPreferenceException(
					InputUserPreferenceException.ILLEGAL_OPERATION);
		}
		if (ArrayUtils.isNotEmpty(userPreferenceForm.getAnswer())) {
			StringBuilder sb = new StringBuilder();
			for (String answer : userPreferenceForm.getAnswer()) {
				sb.append(answer);
			}
			int answerLength = StringUtil.chineseLength(sb.toString());
			if (answerLength > userPreferenceAnswerLengthMax) {
				throw new InputUserPreferenceException(
						InputUserPreferenceException.PREFERENCE_ANSWER_IS_INVALID);
			}
		}
		int descriptionLength = StringUtil.chineseLength(userPreferenceForm
				.getDescription());
		if (descriptionLength > userPreferenceDescriptionLengthMax) {
			throw new InputUserPreferenceException(
					InputUserPreferenceException.PREFERENCE_DESCRIPTION_IS_INVALID);
		}
	}

	@Override
	public List<UserPreference> listUserPreference(Long uid) {
		UserPreferenceExample example = new UserPreferenceExample();
		example.createCriteria().andUidEqualTo(uid);
		return userPreferenceMapper.selectByExample(example);
	}

	@Override
	public List<String> getUserAnswer(long uid, long preferenceId) {
		Preference preference = InitData.PREFERENCE_MAP.get(preferenceId);
		if (preference == null) {
			return null;
		}
		UserPreference userPreference = getUserPreference(preferenceId, uid);
		String answer = null;
		if (null == userPreference
				|| StringUtils.isEmpty(userPreference.getAnswer())) {
			answer = preference.getDefaultAnswer();
		} else {
			answer = userPreference.getAnswer();
		}
		if (answer == null) {
			return null;
		}
		String array[] = StringUtils.split(answer, StringUtil.separator);
		return Arrays.asList(array);
	}

	@Override
	public List<UserPreferenceView> convertToUserPreferenceView(
			List<UserPreference> userPreferences, List<Preference> preferences) {
		List<UserPreferenceView> views = new ArrayList<UserPreferenceView>();
		for (Preference preference : preferences) {
			try {
				String answer = null;
				UserPreferenceView view = new UserPreferenceView(preference,
						null, Input.convertToBean(preference.getInput()));
				for (UserPreference userPreference : userPreferences) {
					if (userPreference.getPreferenceId().longValue() == preference
							.getId().longValue()) {
						if (null == userPreference.getAnswer()) {
							answer = preference.getDefaultAnswer();
							userPreference.setAnswer(answer);
						}
						view.setUserPreference(userPreference);
						break;
					}
				}
				if (view.getUserPreference() == null) {
					UserPreference defaultUserPreference = new UserPreference();
					answer = preference.getDefaultAnswer();
					defaultUserPreference.setAnswer(answer);
					view.setUserPreference(defaultUserPreference);
				}
				view.setAnswer(StringUtils.split(view.getUserPreference()
						.getAnswer(), StringUtil.separator));
				views.add(view);
			} catch (Exception e) {
				log.error("preference convertToBean json is error ");
			}
		}
		return views;
	}

	@Override
	public List<UserPreferenceView> convertToUserHomePreferenceView(
			List<UserPreference> userPreferences, List<Preference> preferences) {
		List<UserPreferenceView> views = new ArrayList<UserPreferenceView>();
		for (Preference preference : preferences) {
			try {
				String answer = null;
				UserPreferenceView view = new UserPreferenceView(preference,
						null, Input.convertToBean(preference.getInput()));
				for (UserPreference userPreference : userPreferences) {
					if (userPreference.getPreferenceId().longValue() == preference
							.getId().longValue()) {
						if (null == userPreference.getAnswer()) {
							answer = preference.getDefaultAnswer();
							userPreference.setAnswer(answer);
						}
						view.setUserPreference(userPreference);
						break;
					}
				}
				if (view.getUserPreference() == null) {
					UserPreference defaultUserPreference = new UserPreference();
					answer = preference.getDefaultAnswer();
					defaultUserPreference.setAnswer(answer);
					view.setUserPreference(defaultUserPreference);
				}
				// 如果用户没有设置值并且没有默认值则不显示
				if (StringUtils.isEmpty(view.getUserPreference().getAnswer())) {
					continue;
				}
				view.setAnswer(StringUtils.split(view.getUserPreference()
						.getAnswer(), StringUtil.separator));
				views.add(view);
			} catch (Exception e) {
				log.error("preference convertToBean json is error ");
			}
		}
		return views;
	}
}
