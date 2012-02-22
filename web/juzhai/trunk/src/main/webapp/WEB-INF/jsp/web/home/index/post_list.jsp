<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="jz_list"><!--jz_list begin-->
	<div class="title"><!--title begin-->
		<div id="city-select" class="select_menu"><!--select_menu begin-->
			<p><a href="javascript:void(0);"></a></p>
			<div></div>
			<div class="select_box"><!--select_box begin-->
				<span>
					<a href="javascript:void(0);" value="0"   <c:if test="${cityId == 0}">class="selected"</c:if>>全国</a>
					<c:forEach var="specialCity" items="${jzd:specialCityList()}">
						<a href="javascript:void(0);" value="${specialCity.id}"   <c:if test="${cityId==specialCity.id}">class="selected"</c:if>>${specialCity.name}</a>
					</c:forEach>
				</span>
				<em></em>
			</div><!--select_box end-->
		</div><!--select_menu end-->
		<div id="gender-select" class="select_menu"><!--select_menu begin-->
			<p><a href="javascript:void(0);"></a></p>
			<div></div>
			<div class="select_box"><!--select_box begin-->
				<span>
					<a href="javascript:void(0);" value="all" <c:if test="${genderType == 'all'}">class="selected"</c:if>>性别不限</a>
					<a href="javascript:void(0);" value="male" <c:if test="${genderType == 'male'}">class="selected"</c:if>>男生</a>
					<a href="javascript:void(0);" value="female" <c:if test="${genderType == 'female'}">class="selected"</c:if>>女生</a>
				</span>
				<em></em>
			</div><!--select_box end-->
		</div><!--select_menu end-->
		<div class="category" queryType="${queryType}"><!--category begin-->
			<span <c:if test="${queryType == 'showNewPosts'}">class="act"</c:if>><p></p><a href="/home/showNewPosts/${cityId}_${genderType}/1">最新的</a><p></p></span>
			<span <c:if test="${queryType == 'showRespPosts'}">class="act"</c:if>><p></p><a href="/home/showRespPosts/${cityId}_${genderType}/1">我响应的</a><p></p></span>
			<span <c:if test="${queryType == 'showIntPosts'}">class="act"</c:if>><p></p><a href="/home/showIntPosts/${cityId}_${genderType}/1">我❤的人</a><p></p></span>
		</div><!--category end-->
	</div><!--title end-->		
	<div class="jz_main"><!--jz_main begin-->
		<c:choose>
			<c:when test="${empty postViewList}"><div class="none">这里还没有人发布拒宅哦</div></c:when>
			<c:otherwise>
				<c:forEach var="postView" items="${postViewList}">
					<div class="jz_item mouseHover <c:choose><c:when test="${postView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--jz_item begin-->
						<div class="face_infor"><!--face_infor begin-->
							<p><a href="/home/${postView.profileCache.uid}"><img src="${jzr:userLogo(postView.profileCache.uid,postView.profileCache.logoPic,120)}" width="120" height="120" /></a></p>
							<a href="/home/${postView.profileCache.uid}"><c:out value="${postView.profileCache.nickname}" /></a>
							<c:set var="age" value="${jzu:age(postView.profileCache.birthYear, postView.profileCache.birthSecret)}" />
							<c:set var="constellationName" value="${jzd:constellationName(postView.profileCache.constellationId)}" />
							<span><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${postView.profileCache.city != null && postView.profileCache.city > 0}">${jzd:cityName(postView.profileCache.city)}<c:if test="${postView.profileCache.town != null && postView.profileCache.town > 0}">${jzd:townName(postView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty postView.profileCache.profession}">${postView.profileCache.profession}</c:if></span>
						</div><!--face_infor end-->
						<div class="wtg"><!--wtg begin-->
							<div class="w_t"></div>
							<div class="w_m"><!--w_m begin-->
								<div class="arrow"></div>
								<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${postView.post.purposeType}"/></c:import>:</font><a href="/post/${postView.post.id}"><c:out value="${postView.post.content}" /></a></p>
								<div class="infor"><!--infor begin-->
									<c:if test="${not empty postView.post.pic}">
										<div class="img"><a href="/post/${postView.post.id}"><img data-original="${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}" src="${jzr:static('/images/web/1px.gif')}"/></a></div>
									</c:if>
									<span><c:set var="date" value="${postView.post.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span>
									<span class="tag">${jzd:categoryName(postView.post.categoryId)}</span>
									<c:if test="${postView.post.dateTime != null}">
										<span class="time"><fmt:formatDate value="${postView.post.dateTime}" pattern="yyyy.MM.dd"/></span>
									</c:if>
									<c:if test="${not empty postView.post.place}">
										<span class="adress"><c:out value="${postView.post.place}" /></span>
									</c:if>
									<c:if test="${not empty postView.post.link}">
										<span class="link"><a href="${postView.post.link}" target="_blank">查看相关链接</a></span>
									</c:if>
								</div><!--infor end-->
							</div><!--w_m end-->
							<div class="clear"></div>
							<div class="w_b"></div>
							<div class="keep user-remove-interest remove-interest-${postView.profileCache.uid}" <c:if test="${!postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${postView.profileCache.uid}" title="点击取消收藏">已收藏</a></div>
							<div class="keep user-add-interest interest-${postView.profileCache.uid}" <c:if test="${postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${postView.profileCache.uid}" title="点击收藏">收藏ta</a></div>
							<c:choose>
								<c:when test="${postView.hasInterest}"></c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							<div class="btn"><!--btn begin-->
								<div class="message_s1"><a href="javascript:void(0);" target-uid="${postView.profileCache.uid}" target-nickname="${postView.profileCache.nickname}">私信</a></div>
								<c:choose>
									<c:when test="${postView.hasResponse}">
										<div class="like done"><a href="javascript:void(0);" class="xy">已响应</a><a href="/post/${postView.post.id}" class="xy_num">(${postView.post.responseCnt})</a></div>
									</c:when>
									<c:otherwise>
										<div class="like post-response" id="response${postView.post.id}"><a href="javascript:void(0);" class="xy" post-id="${postView.post.id}">响应</a><a href="/post/${postView.post.id}" class="xy_num">(<font>${postView.post.responseCnt}</font>)</a></div>
									</c:otherwise>
								</c:choose>
							</div><!--btn end--> 
							<div class="zfa"><a href="javascript:void(0);" post-id="${postView.post.id}">转发</a></div>
						</div><!--wtg end-->
					</div><!--jz_item end-->
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div><!--jz_main end-->
</div><!--jz_list end-->
<div class="clear"></div>
<div class="line"></div>
<c:if test="${not empty postViewList}">
	<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
		<c:param name="pager" value="${pager}"/>
		<c:param name="url" value="/home/${queryType}/${cityId}_${genderType}" />
	</c:import>
</c:if>