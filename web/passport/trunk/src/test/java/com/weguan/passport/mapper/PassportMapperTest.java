package com.weguan.passport.mapper;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.weguan.passport.mapper.PassportMapper;
import com.weguan.passport.model.Passport;
import com.weguan.passport.model.PassportExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context.xml" })
public class PassportMapperTest {

	@Autowired
	private PassportMapper passportMapper;

	private Passport passport;

	@Before
	public void setUp() throws Exception {
		passport = new Passport();
		passport.setActive(0);
		passport.setCreateTime(new Date());
		passport.setEmail("wujiajun1020@gmail.com");
		passport.setLastModifyTime(passport.getCreateTime());
		passport.setPassword("65012331");
		passport.setUserName("wujiajun");
	}

	@After
	public void tearDown() throws Exception {
		PassportExample example = new PassportExample();
		example.createCriteria().andUserNameEqualTo(passport.getUserName());
		passportMapper.deleteByExample(example);
	}

	@Test
	public void mapperTest() {
		Assert.assertEquals(1, passportMapper.insert(passport));
		PassportExample example = new PassportExample();
		example.createCriteria().andUserNameEqualTo(passport.getUserName());
		List<Passport> list = passportMapper.selectByExample(example);
		Assert.assertEquals(1, list.size());
		Assert.assertNotNull(list.get(0));
		Assert.assertEquals(passport.getUserName(), list.get(0).getUserName());
		System.out.println(passport.getId());
	}
}
