<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邀请好友获取拒宅基金 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="warp"><!--warp begin--> 
		<div class="main"><!--main begin-->
			<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content_hd"><!--content begin-->
					<div class="t"></div>
						<div class="m"><!--m begin-->
							<div class="jz_hd"><!--jz_hd begin-->
								<div class="hd_pic"></div>
									<div class="hd_right"><!--hd_right begin-->
										<h2>开奖说明</h2>
										<ul>
											<li><span>·</span><p>本周五(08.31)12点前，抽取1名幸运小宅</p></li>
											<li><span>·</span><p>500元奖金通过支付宝发放</p></li>
											<li><span>·</span><p>请小宅们留意拒宅网或者微博上的私信通知</p></li>
											<li><span>·</span><p>最终获奖名单将在下周一(08.31)公布</p></li>
											<li><span>·</span><p>粉丝少于10人的微博账号不能参与抽奖</p></li>
										</ul>
									</div><!--hd_right end-->
									<div id="bdshare" class="hd_left"><!--hd_left begin-->
										<h2>邀请好友，赢取拒宅自助金</h2>
										<h3>只要分享下面的内容到微博,即有机会赢取本周<font>500元</font>的拒宅自助金哦</h3>
										<div class="share_area">刚发现一个有趣的脱宅社区；周末不想宅在家的朋友可以来试试~，还有机会赢取500元的#拒宅自助金#哦。 http://t.cn/zWRIehS </div>
									        <a href="http://v.t.sina.com.cn/share/share.php?url=http%3A%2F%2Fwww.51juzhai.com%2F%23593065-tsina-1-58528-357aeb2577d0b4df256629dbfb0b0f76&title=刚发现一个有趣的脱宅社区；周末不想宅在家的朋友可以来试试~，还有机会赢取500元的%23拒宅自助金%23哦。&appkey=3631414437&pic=http%3A%2F%2Fimg.51juzhai.com%2Fimages%2Fweb2%2Fshare_pic.jpg" target="_blank"></a>
									</div><!--hd_left end-->
									<div class="hd_over" style="display: none">
										<h2>本期活动已结束</h2>
										<p></p>
										<div class="clear"></div>
									</div>
							</div><!--jz_hd end-->
						</div><!--m end-->
					<div class="b"></div>
			</div><!--content end-->
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
		<c:set var="footType" value="fixed" scope="request"/>
		<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
	</div><!--warp end-->
</body>
</html>
