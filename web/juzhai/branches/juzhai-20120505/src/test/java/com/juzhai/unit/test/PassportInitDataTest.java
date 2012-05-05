package com.juzhai.unit.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Constellation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class PassportInitDataTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getConstellationTest() {
		Constellation c = InitData.getConstellation(2, 29);
		Assert.assertNotNull(c);
		System.out.println(c.getName());
		System.out.println(c.getStartMonth() + "." + c.getStartDate() + " -- "
				+ c.getEndMonth() + "." + c.getEndDate());
	}
}
