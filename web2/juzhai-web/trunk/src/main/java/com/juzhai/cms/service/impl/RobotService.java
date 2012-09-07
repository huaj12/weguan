package com.juzhai.cms.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.cms.exception.RobotInputException;
import com.juzhai.cms.service.IRobotService;
import com.juzhai.common.InitData;
import com.juzhai.common.model.City;
import com.juzhai.common.model.Town;
import com.juzhai.core.Constants;
import com.juzhai.core.bean.UseLevel;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.home.exception.InterestUserException;
import com.juzhai.home.service.IInterestUserService;
import com.juzhai.passport.bean.DeviceName;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.form.RegisterRobotForm;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.post.controller.form.PostCommentForm;
import com.juzhai.post.exception.InputPostCommentException;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.service.IPostCommentService;
import com.juzhai.post.service.IPostService;

@Service
public class RobotService implements IRobotService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IPostCommentService postCommentService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private ProfileMapper profileMapper;
	@Value("${robot.email.default}")
	private String robotEmailDefault;
	@Value("${robot.passport.default}")
	private String robotPassportDefault;

	@Override
	public void add(Long uid) throws RobotInputException {
		if (uid == null) {
			throw new RobotInputException(RobotInputException.ILLEGAL_OPERATION);
		}
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (profileCache.getCity() == null || profileCache.getCity() <= 0) {
			throw new RobotInputException(
					RobotInputException.ROBOT_CITY_IS_NULL);
		}
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genRobotUserKey(profileCache.getCity()), uid);
	}

	@Override
	public void del(Long uid, Long cityId) throws RobotInputException {
		if (uid == null || cityId == null) {
			throw new RobotInputException(RobotInputException.ILLEGAL_OPERATION);
		}
		redisTemplate.opsForSet().remove(
				RedisKeyGenerator.genRobotUserKey(cityId), uid);

	}

	@Override
	public int importRobot(MultipartFile robotConfig)
			throws RobotInputException {
		if (robotConfig == null || robotConfig.getSize() <= 0) {
			throw new RobotInputException(
					RobotInputException.ROBOT_FILE_IS_NULL);
		}
		List<RegisterRobotForm> forms = new ArrayList<RegisterRobotForm>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					robotConfig.getInputStream(), Constants.GBK));
			Random random = new Random();
			String line;
			while ((line = br.readLine()) != null) {
				String[] str = line.split("\\s");
				if (str.length > 7) {
					try {
						RegisterRobotForm form = new RegisterRobotForm();
						City city = InitData.getCityByName(str[0]);
						if (city == null) {
							continue;
						}
						form.setCity(city.getId());
						form.setProvince(city.getProvinceId());
						List<Long> towns = new ArrayList<Long>();
						for (Entry<Long, Town> entry : InitData.TOWN_MAP
								.entrySet()) {
							if (city.getId() == entry.getValue().getCityId()) {
								towns.add(entry.getValue().getId());
							}
						}
						if (CollectionUtils.isNotEmpty(towns)) {
							form.setTown(towns.get(random.nextInt(towns.size())));
						}
						for (Entry<Long, Profession> entry : com.juzhai.passport.InitData.PROFESSION_MAP
								.entrySet()) {
							if (str[6] != null
									&& entry.getValue().getName()
											.equals(str[6])) {
								form.setProfessionId(entry.getKey());
							}
						}
						String male = messageSource.getMessage("gender.male",
								null, Locale.SIMPLIFIED_CHINESE);
						if (str[7] != null && male.equals(str[7])) {
							form.setGender(1);
						} else {
							form.setGender(0);
						}
						form.setEmail(str[1] + robotEmailDefault);
						form.setNickname(str[2]);
						form.setYear(Integer.parseInt(str[3]));
						form.setMonth(Integer.parseInt(str[4]));
						form.setDay(Integer.parseInt(str[5]));
						forms.add(form);
					} catch (Exception e) {
						log.error("resolve robot is error." + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			throw new RobotInputException(
					RobotInputException.ROBOT_READ_FILE_IS_INVALID);
		}
		return registerRobot(forms);

	}

	@Override
	public void interest(Long cityId, Long targetUid)
			throws RobotInputException, InterestUserException {
		if (targetUid == null) {
			throw new RobotInputException(RobotInputException.ILLEGAL_OPERATION);
		}
		Long uid = random(cityId);
		interestUserService.interestUser(uid, targetUid);

	}

	private Long random(Long cityId) throws RobotInputException {
		if (cityId == null) {
			throw new RobotInputException(RobotInputException.ILLEGAL_OPERATION);
		}
		Long uid = redisTemplate.opsForSet().pop(
				RedisKeyGenerator.genRobotUserKey(cityId));
		if (uid == null) {
			throw new RobotInputException(RobotInputException.ROBOT_NOT_EXIST);
		}
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genRobotUserKey(cityId), uid);
		passportService.updateLastLoginTime(uid, RunType.WEB);
		return uid;
	}

	@Override
	public void comment(Long cityId, Long postId, String text)
			throws RobotInputException, InputPostCommentException {
		if (postId == null) {
			throw new RobotInputException(RobotInputException.ILLEGAL_OPERATION);
		}
		if (StringUtils.isEmpty(text)) {
			throw new RobotInputException(
					RobotInputException.ROBOT_COMMON_TEXT_IS_NULL);
		}
		Long uid = random(cityId);
		PostCommentForm form = new PostCommentForm();
		form.setContent(text);
		form.setPostId(postId);
		form.setParentId(0);
		postCommentService.comment(uid, form);

	}

	@Override
	public void response(Long cityId, Long postId) throws RobotInputException,
			InputPostException {
		if (postId == null) {
			throw new RobotInputException(RobotInputException.ILLEGAL_OPERATION);
		}
		Long uid = random(cityId);
		postService.responsePost(uid, postId, "");

	}

	@Override
	public List<ProfileCache> listRobot(Long cityId) {
		if (cityId == null) {
			return null;
		}
		Set<Long> uids = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genRobotUserKey(cityId));
		List<ProfileCache> profileList = new ArrayList<ProfileCache>(
				uids.size());
		for (Long uid : uids) {
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(uid);
			profileList.add(profileCache);
		}
		return profileList;
	}

	@Override
	public int registerRobot(List<RegisterRobotForm> forms) {
		if (CollectionUtils.isEmpty(forms)) {
			return 0;
		}
		int i = 0;
		for (RegisterRobotForm form : forms) {
			try {
				long uid = registerService.register(form.getEmail(),
						form.getNickname(), robotPassportDefault,
						robotPassportDefault, 0l, DeviceName.BROWSER);
				Profile profile = new Profile();
				profile.setUid(uid);
				profile.setProfessionId(form.getProfessionId());
				profile.setBirthYear(form.getYear());
				profile.setBirthMonth(form.getMonth());
				profile.setBirthDay(form.getDay());
				profile.setGender(form.getGender());
				profile.setCity(form.getCity());
				profile.setProvince(form.getProvince());
				profile.setTown(form.getTown());
				if (profile.getProfessionId() > 0) {
					Profession p = com.juzhai.passport.InitData.PROFESSION_MAP
							.get(profile.getProfessionId());
					if (null != p) {
						profile.setProfession(p.getName());
					}
				}
				profile.setConstellationId(com.juzhai.passport.InitData
						.getConstellation(profile.getBirthMonth(),
								profile.getBirthDay()).getId());
				profileMapper.updateByPrimaryKeySelective(profile);
				redisTemplate.opsForSet().add(
						RedisKeyGenerator.genRobotUserKey(form.getCity()), uid);
				userGuideService.createAndCompleteGuide(uid);
				// 完成引导后提升用户使用等级
				passportService.setUseLevel(uid, UseLevel.Level1);
				passportService.updateLastLoginTime(uid, RunType.WEB);
			} catch (Exception e) {
				if (e.getMessage().equals(
						ProfileInputException.PROFILE_NICKNAME_IS_EXIST)) {
					log.error("exist nickname:" + form.getNickname());
				} else if ((e.getMessage()
						.equals(PassportAccountException.ACCOUNT_EXIST))) {
					log.error("exist email:" + form.getEmail());
				} else {
					log.error("registerRobot is error");
				}
				continue;
			}
			i++;
		}
		return i;
	}

}
