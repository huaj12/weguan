package com.juzhai.cms.controller.useless;

import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequestMapping("/cms")
public class PopularizeController {
	// private final Log log = LogFactory.getLog(getClass());
	//
	// @Autowired
	// private ITpUserService tpUserService;
	// @Autowired
	// private IInboxService inboxService;
	// @Autowired
	// private IUserActService userActService;
	// @Autowired
	// private ThreadPoolTaskExecutor taskExecutor;
	//
	// @RequestMapping(value = "/autoKxAnswer", method = RequestMethod.GET)
	// public String login(HttpServletRequest request, Model model, int start,
	// int end) {
	// taskExecutor.execute(new Runnable() {
	// @Override
	// public void run() {
	// log.error("start auto answer...");
	// String[] kxUids = parseUids();
	// int index = 0;
	// for (String kxUid : kxUids) {
	// index++;
	// if (StringUtils.isEmpty(kxUid)) {
	// continue;
	// }
	// TpUser tpUser = tpUserService.getTpUserByTpIdAndIdentity(
	// 1L, kxUid.trim());
	// if (tpUser != null) {
	// answerQuestion(index, tpUser);
	// try {
	// Thread.sleep(RandomUtils.nextInt(2000));
	// } catch (InterruptedException e) {
	// }
	// respRecommend(index, tpUser);
	// try {
	// Thread.sleep(RandomUtils.nextInt(2000));
	// } catch (InterruptedException e) {
	// }
	// }
	// }
	// log.error("end auto answer...");
	// }
	//
	// private void answerQuestion(int index, TpUser tpUser) {
	// for (int i = 0; i < 1; i++) {
	// Feed feed = inboxService.showQuestion(tpUser.getUid());
	// if (feed == null || feed.getQuestion() == null) {
	// break;
	// }
	// int answer = 1;
	// if (feed.getQuestion().getType() == 0) {
	// answer = 5;
	// }
	// inboxService.answer(tpUser.getUid(), 1L, feed.getQuestion()
	// .getId(), feed.getTpFriend().getUserId(), answer,
	// true);
	// }
	// log.error("user[" + tpUser.getUid() + "] the " + index
	// + " question end");
	// }
	//
	// private void respRecommend(int index, TpUser tpUser) {
	// Thirdparty tp = InitData.getTpByTpNameAndJoinType(
	// tpUser.getTpName(), JoinTypeEnum.APP);
	// if (null != tp) {
	// for (int i = 0; i < 1; i++) {
	// Feed feed = inboxService.showRecommend(tpUser.getUid());
	// if (feed == null || feed.getAct() == null) {
	// break;
	// }
	// userActService.respRecommend(tpUser.getUid(),
	// tp.getId(), feed.getAct().getId(),
	// ReadFeedType.WANT, true);
	// log.error("user[" + tpUser.getUid()
	// + "] recommend act[" + feed.getAct().getId()
	// + "] end");
	// }
	// log.error("user[" + tpUser.getUid() + "] the " + index
	// + " recommend end");
	// } else {
	// log.error("user[" + tpUser.getUid() + "] can not find tp.");
	// }
	// }
	// });
	// return null;
	// }
	//
	// private String[] parseUids() {
	// String[] uids = new String[0];
	// try {
	// File file = new File(getClass().getClassLoader()
	// .getResource("/cms/kxUsers").getFile());
	// BufferedReader br = new BufferedReader(new FileReader(file));
	// while (true) {
	// try {
	// String uid = br.readLine();
	// if (StringUtils.isEmpty(uid)) {
	// break;
	// }
	// uids = (String[]) ArrayUtils.add(uids, uid);
	// } catch (IOException e) {
	// log.error(e.getMessage(), e);
	// break;
	// }
	// }
	// } catch (FileNotFoundException e) {
	// log.error(e.getMessage(), e);
	// }
	// return uids;
	// }
}
