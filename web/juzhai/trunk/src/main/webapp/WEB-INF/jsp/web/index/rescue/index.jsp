<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>解救小宅</title>
		<meta name="keywords"   content="拒宅,找伴,出去玩,上海约会地点,北京约会地点,深圳约会地点,创意约会地点,约会地点,约会" />
		<meta  name="description"   content="不想宅在家拒宅网帮你找伴儿,出去玩,发现上海约会地点,北京约会地点,深圳约会地点,创意约会地点和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="jjxz"><!--jjxz begin-->
						<div class="title"><!--title begin-->
							<div class="jj_time"><p></p><span>离周末还有1天34小时</span></div>
							<div class="jj_title">解救小宅</div>
							<div class="view_btn"><a href="#" class="kw">调整口味</a><a href="#" class="jj">我要被解救</a></div>
							<div class="kw_show" style="display:none;"><p>我们会根据你的拒宅偏好，为您推荐合适的人</p><a href="#">去设置偏好</a></div>
							<div class="jj_show" ><p>发布一条拒宅即可被更新到被解救的队列中哦</p><a href="#">去发布拒宅</a></div>
						</div><!--title end-->
						<div class="clear"></div>
						<div class="jjxz_con"><!--jjxz_con begin-->
							<div class="jj_top"></div>
							<div class="jj_mid"><!--jj_mid begin-->
								<div class="card girl"><!--card begin-->
									<div class="card_top"></div>
									<div class="card_mid"><!--card_mid begin-->
										<div class="photo"><img src="images/web2/face_girl.png" /></div>
										<div class="card_infor"><!--card_infor begin-->
											<p><font>我想找伴儿去:</font><a href="#">好累好累的说。。。。。。好想呆在安静的地方休息休息，看看书，听听音乐~~~</a></p>
											<div class="infor"><!--infor begin-->
												<span class="adress">张江故园餐厅</span>
												<span class="time">01.21 周一</span>
												<span class="tag">演出活动</span>
												<span class="link"><a href="#">查看相关链接</a></span>
											</div><!--infor end-->
											<div class="ta_infor"><a href="#">其实不想走</a><span>8岁, 上海浦东新区   双鱼座, it行业</span></div>
										</div><!--card_infor end-->
									</div><!--card_mid end-->
									<div class="card_bot"></div>
								</div><!--card end-->
								<div class="btn"><a href="#" class="xy">响应ta</a> <a href="#" class="next">换一个</a></div>
							</div><!--jj_mid end-->
							<div class="jj_bot"></div>
						</div><!--jjxz_con end-->
					</div><!--jjxz end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/welcome.js')}"></script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>
