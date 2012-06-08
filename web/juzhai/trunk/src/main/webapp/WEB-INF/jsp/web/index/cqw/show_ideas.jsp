<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
			
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${jzd:cityName(cityId)}拒宅好主意 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
		<meta name="keywords" content="${jzd:cityName(cityId)}拒宅,${jzd:cityName(cityId)}出去玩" />
		<meta name="description" content="周末不想宅在家拒宅网为你提供${jzd:cityName(cityId)}拒宅好主意,发现同兴趣的朋友,帮你拒宅" />
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="warp"><!--warp begin-->
			<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<jsp:include page="/WEB-INF/jsp/web/common/youke_login.jsp" />
					<c:if test="${topIdea != null}">
						<div class="main_tj"><!--main_tj begin-->
							<div class="big_pic"><!--big_pic begin-->
								<p><a href="/idea/${topIdea.id}"><img src="${jzr:ideaPic(topIdea.id, topIdea.pic,450)}" width="430" /></a></p>
								<span><em>${jzu:truncate(topIdea.content,46,'...')}</em><a href="/idea/${topIdea.id}">共${topIdea.useCount}人想去</a></span>
							</div><!--big_pic end-->
							<div class="small_pic"><!--small_pic begin-->
								<ul>
									<li><div class="tj"><a href="/idea/select/category"></a></div></li>
									<c:forEach var="topIdea" items="${topIdeaList}">
										<li class="mouseHover">
											<p><a href="/idea/${topIdea.id}"><img src="${jzr:ideaPic(topIdea.id, topIdea.pic,200)}" width="160"/></a></p>
											<span><em>${jzu:truncate(topIdea.content,48,'...')}</em></span>
										</li>
									</c:forEach>
								</ul>
							</div><!--small_pic end-->
						</div><!--main_tj end-->
					</c:if>
					
					<div class="idea_right"><!--main_right begin-->
						<div class="content_box w660 800"><!--content begin-->
						<div class="t"></div>
							<div class="m">
								<div class="jz_list"><!--jz_list begin-->
									<div class="search_title"><!--search_title begin-->
										<div class="idea_category" order-type="${orderType}"><!--category begin-->
											<span <c:if test="${empty orderType}"> class="act"</c:if>><p></p><a href="/showrecideas/${categoryId}/1">推荐</a><p></p></span>
											<span <c:if test="${'time'==orderType}"> class="act"</c:if>><p></p><a href="/showideas/${categoryId}/time/1">最新</a><p></p></span>
											<span <c:if test="${'pop'==orderType}"> class="act"</c:if>><p></p><a href="/showideas/${categoryId}/pop/1" >最热</a><p></p></span>
										</div><!--category end-->
										<c:if test="${not empty ideaViewList}">
											<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
												<c:param name="pager" value="${pager}"/>
												<c:param name="url" value="/showideas/${categoryId}/${orderType}" />
											</c:import>
										</c:if>
								 	</div><!--search_title end-->
								 	<div class="clear"></div>
									<div class="good_idea"><!--good_idea begin-->
									<c:choose>
											<c:when test="${not empty ideaViewList}">
												<jsp:include page="../index_idea_list.jsp" />
											</c:when>
											<c:otherwise>
												<div class="none">这里还没有内容</div>
											</c:otherwise>
										</c:choose>
									</div><!--good_idea end-->
								</div><!--jz_list end-->
								<div class="clear"></div>
								<c:if test="${not empty ideaViewList}">
								<c:choose>
									<c:when test="${not empty orderType }">
										<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
											<c:param name="pager" value="${pager}"/>
											<c:param name="url" value="/showideas/${categoryId}/${orderType}" />
										</c:import>
									</c:when>
									<c:otherwise>
										<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
											<c:param name="pager" value="${pager}"/>
											<c:param name="url" value="/showrecideas/${categoryId}" />
										</c:import>
									</c:otherwise>
									</c:choose>
								</c:if>
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</div><!--main_right end-->
					
					<div class="idea_left"><!--main_right begin-->
						<jsp:include page="idea_category_widget.jsp" />
						<jsp:include page="recent_idea_widget.jsp" />
						<jsp:include page="idea_ad_widget.jsp" />
					</div><!--main_right end-->
				</div><!--main_part end-->
			</div><!--main end-->
			<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
			<script type="text/javascript" src="${jzr:static('/js/web/show_ideas.js')}"></script>
			<script type="text/javascript" id="bdshare_js" data="mini=1&uid=593065" ></script>
			<script type="text/javascript" id="bdshell_js"></script>
			<script type="text/javascript">
				var bds_config = {'snsKey':{'tsina':'3631414437','qzone':'100249114','douban':'00fb7fece2b96fd202f27fc6a82c4f76'}};
				document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
			</script>
			<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
		</div><!--warp end-->
	</body>
</html>

