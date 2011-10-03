<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅器</title>
		<link href="${jz:url('/css/jz.css')}" rel="stylesheet" type="text/css" />
		<link href="${jz:url('/css/skins/blue.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main">
			<!--main begin-->
			<jsp:include page="/WEB-INF/jsp/common/app/app_header.jsp" />
			<div class="skin_body"><!--skin_body begin-->
				<div class="skin_top_bg"><!--content_bg begin-->
					<jsp:include page="/WEB-INF/jsp/common/app/app_prompt.jsp" />
					<jsp:include page="/WEB-INF/jsp/common/app/app_point.jsp" />
					<div class="content white"><!--content begin-->
						<div class="top"></div>
						<div class="mid"><!--mid begin-->
							<div class="message_box"><!--message_box begin-->
								<div class="title"><!--title begin-->
									<div class="tab"><!--tab begin-->
										<a href="/msg/showUnRead.html" class="link new">
											<p class="l"></p>
											<span><em class="mail"></em><strong>未读(${unReadCount})</strong></span>
											<p class="r"></p>
										</a>
										<a href="/msg/showRead.html" class="active">
											<p class="l"></p>
											<span><em class="mail_open"></em><strong>已读(${pager.totalResults})</strong></span>
											<p class="r"></p>
										</a>
									</div><!--tab end-->
									<!--pbr begin-->
									<!-- <div class="pbr"><a href="javascript:;">被我屏蔽的人</a></div><!--pbr end-->
								</div><!--title end-->
								<div class="box_body"><!--box_body begin-->
									<c:forEach var="actMsg" items="${actMsgViewList}" varStatus="msg">
										<div class="item hover"><!--item begin-->
											<span class="l"></span><span class="r"></span>
											<div class="close">
												<a href="javascript:;" onclick="remove('${pager.currentPage}','${msg.index }','read')"></a>
											</div>
											<div class="item_style1"><!--item_style1 begin-->
												<div class="photo fl">
													<img src="images/pic.png" width="80" height="80" />
												</div>
												<div class="infor fl"><!--infor begin-->
													<span class="u">${actMsg.profileCache.nickname }</span>
													<c:choose>
														<c:when test="${actMsg.msgType=='INVITE'}">
															<span class="w">想和你去</span>
															<span class="v">${actMsg.act.name }</span>
														</c:when>
														<c:otherwise>
															最近也想去${actMsg.profileCache.nickname}
														</c:otherwise>
													</c:choose>
													<div class="clear"></div>
													<p>${citys[actMsg.profileCache.city]}</p>
													<strong><fmt:formatDate value="${actMsg.act.createTime}" pattern="yyyy.MM.dd HH:mm" /></strong>
												</div><!--infor end-->
												<div class="btn">
													<c:choose>
														<c:when test="${actMsg.msgType=='INVITE'}">
															<a href="http://www.kaixin001.com/msg/write.php" target="_blank">马上联系他</a>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${actMsg.stuts}">
																	<a href="javascript:;" class="unhover">响应已发</a>
																</c:when>
																<c:otherwise>
																	<a href="javascript:;" onclick="invite_app_friend(this,'${pager.currentPage}','${msg.index }','${actMsg.act.id}','${actMsg.profileCache.uid}')">立即响应</a>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</div>
											</div><!--item_style1 end-->
										</div><!--item end-->
									</c:forEach>
									<!-- page -->
									${pager.stringUrl}
								</div><!--box_body end-->
							</div><!--message_box end-->
						</div><!--mid end-->
						<div class="bot"></div>
					</div><!--content end-->
				</div><!--content_bg end-->
			</div><!--skin_body end-->
			<div class="skin_bottom"></div>
		</div><!--main end-->
		<jsp:include page="/WEB-INF/jsp/common/app/script/script.jsp" />
		<script type="text/javascript" src="${jz:url('/js/module/msg.js')}"></script>
	</body>
</html>