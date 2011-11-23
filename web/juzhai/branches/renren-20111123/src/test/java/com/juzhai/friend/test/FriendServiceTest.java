package com.juzhai.friend.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.service.IFriendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class FriendServiceTest {

	@Autowired
	private IFriendService friendService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void unInstallFriendsTest() {
		List<TpFriend> list = friendService.getUnInstallFriends(1L);
		System.out.println(list.size());
	}

	@Test
	public void appFriendsTest() {
		List<Long> ids = friendService.getAppFriends(1L);
		System.out.println(ids.size());
	}
}
