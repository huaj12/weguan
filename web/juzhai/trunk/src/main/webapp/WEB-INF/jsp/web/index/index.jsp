<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta  name="keywords"   content="拒宅,找伴,出去玩,上海拒宅好主意,北京拒宅好主意,深圳拒宅好主意,创意拒宅好主意" />
		<meta  name="description"   content="不想宅在家拒宅网帮你找伴儿,出去玩,发现上海拒宅好主意,北京拒宅好主意,深圳拒宅好主意,创意拒宅好主意和同兴趣的朋友,促成约会" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
				<div class="main"><!--main begin-->
					<c:set scope="request" value="index" var="pageType"></c:set>
					<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
					<div class="main_part"><!--main_part begin-->
						<div class="main_left"><!--main_left begin-->
							<div class="content_box w660 z900"><!--content begin-->
							<div class="t"></div>
							<div class="m">
							<div class="qn"><!--qn" begin-->
							<div class="title"><h2>这个周末想去哪儿玩？</h2>
							<c:choose>
								<c:when test="${context.uid>0}">
									<c:set value="/home" var="shareIdeaUrl"></c:set>
								</c:when>
								<c:otherwise>
									<c:set value="/login?turnTo=/index" var="shareIdeaUrl"></c:set>
								</c:otherwise>
							</c:choose>
							<a href="${shareIdeaUrl }">分享拒宅好主意</a></div>
							<div class="good_idea"><!--good_idea begin-->
								<jsp:include page="index_idea_list.jsp" />
							</div><!--good_idea begin-->
							<div class="qn_share">
							<p>分享你想去的:</p>
							<a href="javascript:void(0);" class="hd">活动</a>
							
							<a href="javascript:void(0);" class="qc">好去处</a>
							
							<a href="javascript:void(0);" class="tg">团购</a>
							</div>
							<div class="qn_more"><a href="/showideas">发现更多&gt;</a></div>
							</div><!--qn" end-->
							</div>
							<div class="t"></div>
							</div><!--content end-->
						</div><!--main_left end-->
						<div class="main_right"><!--main_right begin-->
							<c:if test="${context.uid>0}">
							<jsp:include page="../home/index/home_logo.jsp" />
							</c:if>
							<c:if test="${context.uid<=0}">
							<jsp:include page="index_login.jsp" />
							</c:if>
							<jsp:include page="index_post_list.jsp" />
							<c:set scope="request" var="hotListType" value="result"></c:set>
							<jsp:include page="/WEB-INF/jsp/web/search/common/search_post_input.jsp" />
						</div><!--main_right end-->
					</div><!--main_part end-->
				
				</div><!--main end-->
							<jsp:include page="/WEB-INF/jsp/web/home/dialog/share_box.jsp" />
							<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
							<script type="text/javascript" src="${jzr:static('/js/web/index.js')}"></script>
							<c:set var="footType" value="welcome" scope="request"/>
							<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
				</div><!--warp end-->

</body>

</html>
