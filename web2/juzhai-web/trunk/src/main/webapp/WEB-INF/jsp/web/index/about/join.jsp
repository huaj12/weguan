<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>加入我们 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="content"><!--content begin-->
					<div class="t"></div>
					<div class="m"><!--m begin-->
						<div class="other_page"><!--other_page begin-->
							<div class="other_left"><!--other_left begin-->
								<h2>拒宅网招聘运营实习生或兼职</h2>
								<h3>希望你能帮我们：</h3>
								<p>
								1、发现并整理城市里的拒宅好主意（活动，商户，景点等）<br />
								2、维护拒宅网上海站官方微博，每日发微博并与粉丝互动<br />
								3、协助微博营销活动以及线下活动<br />
								<br />
								</p>
								<h3>你能够得到的：</h3>
								<p>
								1、体验社区及其微博从小做大的全过程，参与每一个环节<br />
								2、刷微博就是工作，学习实践更好的养微博<br />
								3、不用坐班，不挤公交，可以躺在家里办公，但需保持日常电话或网络沟通<br />
								4、兼职工资1k以上，具体看工作效果<br />
								</p>
								<h3>希望你是：</h3>
								<p>
								1、热爱生活，喜欢把好的事物与大家分享，经常拒宅<br />
								2、善于观察生活，说写清晰，逻辑和文法优秀<br />
								3、网虫，微博控，不潜水，版聊王<br />
								4、希望像互联网运营方向发展，信仰创意运营可以创造大场面<br />
								5、有充足的上网时间，大三大四学生优先<br /><br />
								有意者请发简历发给拒宅大叔<br />
								邮箱：max@51juzhai.com
								</p>
							</div><!--other_left end-->
							<div class="other_right"><!--other_right begin-->
								<a href="/about/us">了解拒宅网</a>
								<a href="/about/rule">社区管理细则</a>
								<a href="/about/contact">联系我们</a>
								<a href="javascript:void(0);" class="select">加入我们</a>
							</div><!--other_right end-->
						</div><!--other_page end-->
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
