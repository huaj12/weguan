<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>关于我们 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
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
							<div class="other_page"><!--other_page begin-->
							<div class="other_left"><!--other_left begin-->
								<h2>社区管理细则：</h2>
								<h3>什么样的头像不能通过审核？</h3>
								<p>
									1、宠物，卡通等非人类头像<br />
									2、来源于网络的明星或虚假的帅哥美女图片<br />
									3、头像模糊难以辨认的<br />
									4、赤膊等不雅照片<br />
									<br />
									未通过头像审核的用户不能发布拒宅，且不能被其他用户看到。
								</p>
								<h3>哪些行为可能会被封号：</h3>
								<p>
									1、发送涉嫌性骚扰的文字、图片信息；<br />
									2、使用含色情、淫秽意味或其他令人不适的头像或资料；<br />
									3、在交谈中使用辱骂、恐吓、威胁等言论；<br />
									4、发布各类垃圾广告、恶意信息、诱骗信息；<br />
									5、盗用他人头像或资料，伪装他人身份；<br />
									6、发布不当政治言论或者任何违反国家法规政策的言论。<br />
									<br />
									我们会根据违规情况，进行封号一周，一月，以及永久封号的处理。
								</p>
								<h3>什么样的拒宅会被屏蔽？</h3>
								<p>
									1、含有色情、淫秽信息<br />
									2、内容与找伴儿出去玩没有任何关系<br />
									3、含有无关广告信息<br />
									4、含有不当政治言论或者任何违反国家法规政策的言论
									<br /><br />
									为了维护良好的社区氛围，我们会对内容进行一一审核。
								</p>
							</div><!--other_left end-->
							<div class="other_right"><!--other_right begin-->
								<a href="/about/us">了解拒宅网</a>
								<a href="javascript:void(0);" class="select">社区管理细则</a>
								<a href="/about/contact">联系我们</a>
								<a href="/about/join">加入我们</a>
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
