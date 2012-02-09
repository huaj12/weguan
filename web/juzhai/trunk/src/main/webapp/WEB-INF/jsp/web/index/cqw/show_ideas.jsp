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
		<meta name="keywords" content="拒宅 找伴 出去玩 约会 交友" />
		<meta name="description" content="不想宅在家，找伴儿，出去玩，发现出去玩的好主意和同兴趣的朋友，促成约会" />
		<title>出去玩-拒宅网</title>
		<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
	</head>
<body>
				<div class="warp"><!--warp begin-->
				<div class="main"><!--main begin-->
					<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
					<jsp:include page="/WEB-INF/jsp/web/common/back_top.jsp" />
				<div class="main_part"><!--main_part begin-->
				<div class="main_left"><!--main_left begin-->
				<div class="content_box w660 800"><!--content begin-->
				<div class="t"></div>
				<div class="m">
				<div class="jz_list"><!--jz_list begin-->
				
				<div class="search_title"><!--search_title begin-->
				
				<h2>拒宅好主意</h2>
				
				<div class="w70"><!--w70 begin-->
				<div class="select_menu"><!--select_menu begin-->
				<p><a href="javascript:void(0);" <c:if test="${cityId==0}">class="selected"</c:if> >全国</a></p>
				<div></div>
				<div class="select_box"><!--select_box begin-->
				<span>
				<a href="javascript:void(0);" value="2"   onclick="javascript:location.href='/showIdeas/${orderType}_2/1'"  <c:if test="${cityId==2}">class="selected"</c:if>>上海</a>
				<a href="javascript:void(0);" value="1"   onclick="javascript:location.href='/showIdeas/${orderType}_1/1'" <c:if test="${cityId==1}">class="selected"</c:if>>北京</a>
				<a href="javascript:void(0);" value="181" onclick="javascript:location.href='/showIdeas/${orderType}_181/1'" <c:if test="${cityId==181}">class="selected"</c:if>>广州</a>
				<a href="javascript:void(0);" value="183" onclick="javascript:location.href='/showIdeas/${orderType}_183/1'"<c:if test="${cityId==183}">class="selected"</c:if>>深圳</a>
				<a href="javascript:void(0);" value="343" onclick="javascript:location.href='/showIdeas/${orderType}_343/1'"<c:if test="${cityId==343}">class="selected"</c:if>>杭州</a>
				<a href="javascript:void(0);" value="157" onclick="javascript:location.href='/showIdeas/${orderType}_157/1'"<c:if test="${cityId==157}">class="selected"</c:if>>南京</a>
				<a href="javascript:void(0);" value="4"   onclick="javascript:location.href='/showIdeas/${orderType}_4/1'"<c:if test="${cityId==4}">class="selected"</c:if>>重庆</a>
				<a href="javascript:void(0);" value="241" onclick="javascript:location.href='/showIdeas/${orderType}_241/1'"<c:if test="${cityId==241}">class="selected"</c:if>>成都</a>
				<a href="javascript:void(0);" value="108" onclick="javascript:location.href='/showIdeas/${orderType}_108/1'"<c:if test="${cityId==108}">class="selected"</c:if>>武汉</a>
				</span>
				<em></em>
				</div><!--select_box end-->
				
				</div><!--select_menu end-->
				
				</div><!--w70 end-->
				
				
				
				<div class="category"><!--category begin-->
				
				<span <c:if test="${empty orderType||'time'==orderType}"> class="act"</c:if>><p></p><a href="/showIdeas/time_${cityId}/1">最新</a><p></p></span>
				
				<span <c:if test="${'pop'==orderType}"> class="act"</c:if>><p></p><a href="/showIdeas/pop_${cityId}/1" >最热</a><p></p></span>
				
				</div><!--category end-->
				
				</div><!--search_title end-->
				
				<div class="good_idea"><!--good_idea begin-->
				
				<div class="pub_box hover"><!--pub_box begin-->
				
				<div class="pub_box_t"></div>
				
				<c:choose>
					<c:when test="${!empty ideaViewList&&fn:length(ideaViewList)>0}">
				<c:forEach items="${ideaViewList}" var="ideaView">
				<div class="pub_box_m"><!--pub_box_m begin-->
				
				<p><a href="/idea/${ideaView.idea.id}">${ideaView.idea.content}</a></p>
				
				<div class="infor"><!--infor begin-->
				
				<div class="img"><a href="/idea/${ideaView.idea.id}"><img src="${jzr:ideaPic(ideaView.idea.id,ideaView.idea.pic)}" width="150" height="100" /></a></div>
				
				<div class="clear"></div>
				
				<span><c:set var="date" value="${ideaView.idea.lastModifyTime}" scope="request"/><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
				
				<span class="adress">${ideaView.idea.place}</span>
				
				<span class="time"><fmt:formatDate value="${ideaView.idea.date}" pattern="yyyy-MM-dd" /></span>
				<c:if test="${!empty ideaView.idea.link }">
				<span class="link"><a href="${ideaView.idea.link}">查看相关链接</a></span>
				</c:if>
				</div><!--infor end-->
				<div class="fb_area"><!--fb_area begin-->
				<c:choose>
					<c:when test="${!empty ideaView.ideaUserViews}">
					<div class="fb_members">
						<c:forEach items="${ideaView.ideaUserViews}"  var="ideaUser">
						<em><a href="#"><img src="${jzr:userLogo(ideaUser.profileCache.uid,ideaUser.profileCache.logoPic,40)}" width="40" height="40"/></a></em>
						</c:forEach>
						<b><a href="#">共${ideaView.idea.useCount}人想去</a></b>
					</div>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<div class="fb_btn"><a href="#">发布拒宅</a></div>
				
				</div><!--fb_area end-->
				</div><!--pub_box_m end-->
				<div class="clear"></div>
				</c:forEach>
					</c:when>
					<c:otherwise>
						无内容
					</c:otherwise>
				</c:choose>
				<div class="pub_box_b"></div>
				</div><!--pub_box end-->
				</div><!--good_idea end-->
				</div><!--jz_list end-->
				<div class="clear"></div>
				<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
						<c:param name="pager" value="${pager}"/>
						<c:param name="url" value="/showIdeas/${orderType}_${cityId}" />
					</c:import>
				</div>
				
				<div class="t"></div>
				
				</div><!--content end-->
				</div><!--main_left end-->
				
				<div class="main_right"><!--main_right begin-->
				
				
				<div class="content_box w285"><!--content begin-->
				
				<div class="t"></div>
				
				<div class="m">
				
				<div class="right_title"><h2>相关优惠</h2></div>
				
				
				
				<div class="preferential"><!--preferential begin-->
				<c:forEach items="${ads}" var="ad">
				<div class="item"><!--item begin-->
				<p><a href="${ad.link }"><img src="${ad.picUrl }" /></a></p>
				<span><a href="${ad.link }">${jzu:truncate(ad.name,70,'...')}</a></span>
				<b>团购价：${ad.price}元</b>
				
				<strong>${ad.district}</strong>
				
				</div><!--item end-->
				</c:forEach>
				</div><!--preferential end-->
				</div>
				<div class="t"></div>
				</div><!--content end-->
				</div><!--main_right end-->
				</div><!--main_part end-->
				</div><!--main end-->
					<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
				</div><!--warp end-->
					<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
					<jsp:include page="/WEB-INF/jsp/web/common/script/invite_plug.jsp" />

</body>

</html>

