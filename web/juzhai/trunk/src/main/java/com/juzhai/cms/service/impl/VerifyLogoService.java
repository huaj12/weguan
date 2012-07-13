package com.juzhai.cms.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.cms.service.IVerifyLogoService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.search.service.IProfileSearchService;
import com.juzhai.stats.counter.service.ICounter;

@Service
public class VerifyLogoService implements IVerifyLogoService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private ICounter auditLogoCounter;
	@Autowired
	private IProfileSearchService profileSearchService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private IPostSearchService postSearchService;
	@Value("${user.post.lucene.rows}")
	private int userPostLuceneRows;

	@Override
	public List<Profile> listVerifyLogoProfile(LogoVerifyState logoVerifyState,
			int firstResult, int maxResult) {
		ProfileExample example = new ProfileExample();
		example.createCriteria().andNewLogoPicNotEqualTo(StringUtils.EMPTY)
				.andNewLogoPicIsNotNull()
				.andLogoVerifyStateEqualTo(logoVerifyState.getType());
		example.setLimit(new Limit(firstResult, maxResult));
		example.setOrderByClause("last_modify_time desc");
		return profileMapper.selectByExample(example);
	}

	@Override
	public int countVerifyLogoProfile(LogoVerifyState logoVerifyState) {
		ProfileExample example = new ProfileExample();
		example.createCriteria().andNewLogoPicNotEqualTo(StringUtils.EMPTY)
				.andNewLogoPicIsNotNull()
				.andLogoVerifyStateEqualTo(logoVerifyState.getType());
		return profileMapper.countByExample(example);
	}

	@Override
	public void passLogo(long uid) {
		boolean falg = false;
		if (profileService.isValidLogo(uid)) {
			falg = true;
		}
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		if (null != profile) {
			Profile updateProfile = new Profile();
			updateProfile.setUid(uid);
			updateProfile.setLogoPic(profile.getNewLogoPic());
			updateProfile
					.setLogoVerifyState(LogoVerifyState.VERIFIED.getType());
			if (profileMapper.updateByPrimaryKeySelective(updateProfile) > 0) {
				ProfileCache profileCache = profileService
						.getProfileCacheByUid(uid);
				if (null != profileCache) {
					profileService.clearProfileCache(uid);
					dialogService.sendOfficialSMS(uid,
							DialogContentTemplate.PASS_LOGO,
							profileCache.getNickname());
					auditLogoCounter.incr(null, 1L);
					// 后台通过头像
					if (!falg && userGuideService.isCompleteGuide(uid)) {
						profileSearchService.createIndex(uid);
					}
					// 通过头像后通过好主意发布的拒宅自动通过
					List<Post> posts = postService.listPostByIdea(uid);
					if (CollectionUtils.isNotEmpty(posts)) {
						List<Long> postIds = new ArrayList<Long>(posts.size());
						for (Post post : posts) {
							postIds.add(post.getId());
						}
						try {
							postService.handlePost(postIds);
						} catch (InputPostException e) {
						}
					}
				}
			}
		}

	}

	@Override
	public void denyLogo(long uid) {
		Profile updateProfile = new Profile();
		updateProfile.setUid(uid);
		updateProfile.setLogoVerifyState(LogoVerifyState.UNVERIFIED.getType());
		if (profileMapper.updateByPrimaryKeySelective(updateProfile) > 0) {
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(uid);
			if (null != profileCache) {
				profileService.clearProfileCache(uid);
				dialogService.sendOfficialSMS(uid,
						DialogContentTemplate.DENY_LOGO,
						profileCache.getNickname());
			}
		}
	}

	@Override
	public void removeLogo(long uid) {
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		boolean flag = false;
		if (profileService.isValidUser(uid)) {
			flag = true;
		}

		profile.setLogoPic(null);
		profile.setLastUpdateTime(null);
		profile.setLogoVerifyState(LogoVerifyState.UNVERIFIED.getType());
		profileMapper.updateByPrimaryKey(profile);
		redisTemplate.delete(RedisKeyGenerator.genUserLatestPostKey(uid));

		profileService.clearProfileCache(uid);
		dialogService.sendOfficialSMS(uid, DialogContentTemplate.DENY_LOGO);
		// 删除头像
		if (flag) {
			profileSearchService.deleteIndex(uid);
		}
		// 删除头像的用户的所有通过拒宅
		int i = 0;
		while (true) {
			List<Post> posts = postService.getUserQualifiedPost(uid, i,
					userPostLuceneRows);
			for (Post p : posts) {
				postSearchService.deleteIndex(p.getId());
			}
			i += userPostLuceneRows;
			if (posts.size() < userPostLuceneRows) {
				break;
			}
		}
		PostExample postExample = new PostExample();
		postExample.createCriteria().andCreateUidEqualTo(uid)
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType());
		Post post = new Post();
		post.setVerifyType(VerifyType.SHIELD.getType());
		post.setLastModifyTime(new Date());
		postMapper.updateByExampleSelective(post, postExample);
	}

	@Override
	public Boolean realPic(String imgUrl) {
		if (StringUtils.isEmpty(imgUrl)) {
			return null;
		}
		String url = "http://www.google.com.hk/searchbyimage?image_url="
				+ imgUrl
				+ "&encoded_image=&image_content=&filename=&num=10&hl=zh-CN&newwindow=1&safe=strict&bih=762&biw=1440";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet method = new HttpGet(url);
		HttpResponse res = null;
		try {
			method.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 5.1; rv:13.0) Gecko/20100101 Firefox/13.0.1");
			method.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			method.setHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			method.setHeader("Connection", "keep-alive");
			method.setHeader(
					"Cookie",
					"PREF=ID=fd801fe8f5340192:U=061582e14624ae44:FF=1:LD=zh-CN:NW=1:TM=1341909827:LM=1341988947:S=eJwDE1ujDWUbJfNy; NID=61=XYrkd4_U2YgZ6jZ4FyIx5lOmwPUjed2cr2AVlp5pQ77RtpVR6iLCjYWNnh8oalspFB79bnB2b-o2gm6CFzVhUdb0PCifEOQ_HNVvk69D0VPFrsR53_AbPNtuG8erv-tt");
			res = httpclient.execute(method);
			String content = getHtml(res, "UTF-8", false);
			if (content.indexOf("id=resultStats") == -1) {
				return true;
			} else {
				Pattern pat = Pattern
						.compile("id=resultStats>找到约 (.*?) 条结果<nobr>");
				Matcher mat = pat.matcher(content);
				String countStr = null;
				while (mat.find()) {
					countStr = mat.group(1);
				}
				int count = Integer.parseInt(countStr.replace(",", ""));
				if (count > 2) {
					return false;
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			log.error("get is realPic error.", e);
			return null;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	private String getHtml(HttpResponse res, String encode, Boolean breakLine)
			throws IOException {
		breakLine = (breakLine == null) ? false : breakLine;
		InputStream input = null;
		StatusLine status = res.getStatusLine();
		if (status.getStatusCode() != 200) {
			throw new IOException("StatusCode !=200");
		}
		if (res.getEntity() == null) {
			return "";
		}
		input = res.getEntity().getContent();
		InputStreamReader reader = new InputStreamReader(input, encode);
		BufferedReader bufReader = new BufferedReader(reader);
		String tmp = null, html = "";
		while ((tmp = bufReader.readLine()) != null) {
			html += tmp + (breakLine ? "\r\n" : "");
		}
		if (input != null) {
			input.close();
		}
		return html;
	}
}
