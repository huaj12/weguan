/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.spider.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.juzhai.common.InitData;
import com.juzhai.common.mapper.CityMapper;
import com.juzhai.common.mapper.CityMappingMapper;
import com.juzhai.common.mapper.ProvinceMapper;
import com.juzhai.common.mapper.TownMapper;
import com.juzhai.common.model.City;
import com.juzhai.common.model.CityMapping;
import com.juzhai.common.model.Province;
import com.juzhai.common.model.Town;
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.JackSonSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class ProvinceAndCity {

	@Autowired
	private CityMapper cityMapper;
	@Autowired
	private ProvinceMapper provinceMapper;
	@Autowired
	private CityMappingMapper cityMappingMapper;
	@Autowired
	private TownMapper townMapper;

	@Test
	public void initKaixinProvinceAndCity() throws HttpException, IOException {
		int i = 1;
		for (Map<String, Object> provinceMap : getKaixinCityDoGet("type=0")) {
			String provinceName = (String) provinceMap.get("name");
			Province province = new Province();
			province.setName(provinceName);
			province.setSequence(i);
			province.setCreateTime(new Date());
			province.setLastModifyTime(province.getCreateTime());
			provinceMapper.insertSelective(province);
			System.out.println("create province:" + province.getName());
			i++;
			Integer id = (Integer) provinceMap.get("id");
			Map<String, Object>[] cityArray = getKaixinCityDoGet("type=1&id="
					+ id);
			if (ArrayUtils.isEmpty(cityArray)) {
				createCity(provinceName, province.getId(), 1);
			} else {
				int j = 1;
				for (Map<String, Object> cityMap : cityArray) {
					createCity((String) cityMap.get("name"), province.getId(),
							j);
					j++;
				}
			}
		}
	}

	public void createCity(String name, long provinceId, int sequence) {
		City city = new City();
		city.setName(name);
		city.setProvinceId(provinceId);
		city.setSequence(sequence);
		city.setCreateTime(new Date());
		city.setLastModifyTime(city.getCreateTime());
		cityMapper.insertSelective(city);
		// System.out.println("        create city:" + city.getName());
	}

	@Test
	public void kaixin() throws HttpException, IOException {
		for (Map<String, Object> provinceMap : getKaixinCityDoGet("type=0")) {
			System.out.print(provinceMap.get("name") + ":");
			Integer id = (Integer) provinceMap.get("id");
			for (Map<String, Object> cityMap : getKaixinCityDoGet("type=1&id="
					+ id)) {
				System.out.print(cityMap.get("name") + ",");
			}
			System.out.println();
		}
	}

	private Map<String, Object>[] getKaixinCityDoGet(String queryString)
			throws JsonGenerationException, URIException, HttpException,
			IOException {
		return JackSonSerializer.toMapArray(getCityDoGet(
				"http://www.kaixin001.com/interface/suggestlocation.php",
				queryString));
	}

	private String getCityDoGet(String url, String queryString)
			throws URIException, IOException, HttpException {
		String response = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		method.setQueryString(URIUtil.encodeQuery(queryString));
		client.executeMethod(method);
		if (method.getStatusCode() == HttpStatus.SC_OK) {
			response = method.getResponseBodyAsString();
		}
		method.releaseConnection();
		return response;
	}

	@Test
	public void renren() throws HttpException, IOException {
		String[] provinceArray = new String[] { "\u5317\u4eac", "\u4e0a\u6d77",
				"\u9ed1\u9f99\u6c5f", "\u5409\u6797", "\u8fbd\u5b81",
				"\u5929\u6d25", "\u5b89\u5fbd", "\u6c5f\u82cf", "\u6d59\u6c5f",
				"\u9655\u897f", "\u6e56\u5317", "\u5e7f\u4e1c", "\u6e56\u5357",
				"\u7518\u8083", "\u56db\u5ddd", "\u5c71\u4e1c", "\u798f\u5efa",
				"\u6cb3\u5357", "\u91cd\u5e86", "\u4e91\u5357", "\u6cb3\u5317",
				"\u6c5f\u897f", "\u5c71\u897f", "\u8d35\u5dde", "\u5e7f\u897f",
				"\u5185\u8499\u53e4", "\u5b81\u590f", "\u9752\u6d77",
				"\u65b0\u7586", "\u6d77\u5357", "\u897f\u85cf", "\u9999\u6e2f",
				"\u6fb3\u95e8", "\u53f0\u6e7e", "\u5176\u5b83\u56fd\u5bb6" };
		String body = getCityDoGet(
				"http://s.xnimg.cn/a13818/js/inCityArray.js", "");
		BufferedReader br = new BufferedReader(new StringReader(body));
		int i = 0;
		while (true) {
			String temp = br.readLine();
			if (StringUtils.isEmpty(temp) || provinceArray.length <= i) {
				break;
			}
			String province = StringEscapeUtils.unescapeJava(provinceArray[i]);
			System.out.print(province + ":");
			String line = StringEscapeUtils.unescapeJava(temp);
			String value = line.split("=")[1].trim();
			String[] cityArray = StringUtils.substring(value, 1,
					value.length() - 2).split(",");
			for (String city : cityArray) {
				city = city.split(":")[1];
				city = city.substring(0, city.length() - 1);
				if (null == InitData.getCityByName(city)) {
					String destCity = city;
					if (destCity.endsWith("市")) {
						destCity = city.substring(0, city.length() - 1);
					}
					if (destCity.endsWith(")")) {
						destCity = city.substring(0, city.lastIndexOf("("));
					}
					if (destCity.endsWith("特别行政区")) {
						destCity = city.substring(0, city.lastIndexOf("特别行政区"));
					}
					if (StringUtils.equals(destCity, "台湾省")) {
						destCity = "台湾";
					}
					if (StringUtils.equals(destCity, "普洱")) {
						destCity = "思茅";
					}
					City cityObj = InitData.getCityByName(destCity);
					if (null != cityObj) {
						try {
							CityMapping cityMapping = new CityMapping();
							cityMapping.setCityId(cityObj.getId());
							cityMapping.setMappingCityName(city);
							cityMappingMapper.insertSelective(cityMapping);
						} catch (Exception e) {
						}
					}
				}
				System.out.print(city + ",");
			}
			System.out.println();
			i++;
		}
	}

	@Test
	public void weibo() throws IOException {
		Properties p = new Properties();
		p.load(getClass().getClassLoader().getResourceAsStream(
				"weiboCity.properties"));
		String[] provinceNames = new String[] { "安徽", "北京", "重庆", "福建", "甘肃",
				"广东", "广西", "贵州", "海南", "河北", "黑龙江", "河南", "湖北", "湖南", "内蒙古",
				"江苏", "江西", "吉林", "辽宁", "宁夏", "青海", "山西", "山东", "上海", "四川",
				"天津", "西藏", "新疆", "云南", "浙江", "陕西", "台湾", "香港", "澳门", "海外" };
		int i = 0;
		for (String provinceName : provinceNames) {
			System.out.print(provinceName + ": ");
			String[] cityNames = p.getProperty(provinceName).split(",");
			for (String cityName : cityNames) {
				City city = InitData.getCityByName(cityName);
				if (city == null) {
					i++;
					System.out.print(cityName + ",");
				}
			}
			System.out.println();
		}
		System.out.println("total:" + i);
	}

	@Test
	public void createWeiboCity() throws IOException {
		Properties p = new Properties();
		p.load(getClass().getClassLoader().getResourceAsStream(
				"weiboCity.properties"));
		Properties cityMappingProp = new Properties();
		cityMappingProp.load(getClass().getClassLoader().getResourceAsStream(
				"cityMapping.properties"));
		String[] provinceNames = new String[] { "安徽", "北京", "重庆", "福建", "甘肃",
				"广东", "广西", "贵州", "海南", "河北", "黑龙江", "河南", "湖北", "湖南", "内蒙古",
				"江苏", "江西", "吉林", "辽宁", "宁夏", "青海", "山西", "山东", "上海", "四川",
				"天津", "西藏", "新疆", "云南", "浙江", "陕西", "台湾", "香港", "澳门", "海外" };
		for (String provinceName : provinceNames) {
			String[] cityNames = p.getProperty(provinceName).split(",");
			for (String cityName : cityNames) {
				String mappingCityName = cityMappingProp.getProperty(cityName);
				if (StringUtils.isNotEmpty(mappingCityName)) {
					City city = InitData.getCityByName(mappingCityName);
					if (null != city) {
						CityMapping cityMapping = new CityMapping();
						cityMapping.setCityId(city.getId());
						cityMapping.setMappingCityName(cityName);
						cityMappingMapper.insertSelective(cityMapping);
					}
				}
			}
		}
	}

	@Test
	public void createTown() throws IOException {
		Properties p = new Properties();
		p.load(getClass().getClassLoader().getResourceAsStream(
				"town.properties"));
		String[] cityNames = new String[] { "北京", "上海", "天津", "重庆", "广州", "深圳",
				"武汉", "成都", "杭州", "西安" };
		for (String cityName : cityNames) {
			City city = InitData.getCityByName(cityName);
			if (city == null) {
				System.out.println(cityName + " is null.");
				continue;
			}
			int sequence = 1;
			String[] townNames = p.getProperty(cityName).split(",");
			for (String townName : townNames) {
				if (StringUtils.isNotEmpty(townName)) {
					Town town = new Town();
					town.setCityId(city.getId());
					town.setName(townName);
					town.setSequence(sequence);
					town.setCreateTime(new Date());
					town.setLastModifyTime(town.getCreateTime());
					townMapper.insertSelective(town);
					sequence++;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void douban() throws IOException {
		byte[] bytes = FileUtil.readFileToByteArray(new File(getClass()
				.getClassLoader().getResource("douban.properties").getFile()));
		String content = new String(bytes);
		Map<String, Object> map = JackSonSerializer.toMap(content);
		List<Map<String, Object>> locations = (List<Map<String, Object>>) map
				.get("locations");
		int i = 0;
		for (Map<String, Object> locMap : locations) {
			String provinceName = String.valueOf(locMap.get("name"));
			System.out.print(provinceName + ": ");
			List<Map<String, Object>> cityList = (List<Map<String, Object>>) locMap
					.get("children");
			for (Map<String, Object> cityMap : cityList) {
				String cityName = String.valueOf(cityMap.get("name"));
				City city = InitData.getCityByName(cityName);
				if (city == null) {
					i++;
					System.out.print(cityName + ",");
				}
			}
			System.out.println();
		}
		System.out.println("total:" + i);
	}
}
