<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="jz_list"><!--jz_list begin-->
	<div class="title"><!--title begin-->
		<%-- <div id="city-select" class="l_select_menu"><!--l_select_menu begin-->
			<p><a href="javascript:void(0);" hidefocus city-id="${cityId}"><c:choose><c:when test="${cityId == 0}">全国</c:when><c:otherwise>${jzd:cityName(cityId)}</c:otherwise></c:choose></a></p>
			<div></div>
			<div class="l_select_menu_box"><!--city_list begin-->
				<div class="l_select_menu_box_t"></div>
				<div class="l_select_menu_box_m"><!--l_select_menu_box_m begin-->
					<div class="vip_city"><!--vip_city begin-->
						<h3>主要城市:</h3>
						<div class="list city-list"><!--list begin-->
							<c:forEach var="specialCity" items="${jzd:specialCityList()}">
								<a href="javascript:void(0);" value="${specialCity.id}" <c:if test="${cityId==specialCity.id}">class="act"</c:if>>${specialCity.name}</a>
							</c:forEach>
							<a href="javascript:void(0);" value="0" <c:if test="${cityId == 0}">class="act"</c:if>>全国</a>
						</div><!--list end-->
					</div><!--vip_city end-->
					<div class="all_city"><!--all_city begin-->
						<h3>所有城市:</h3>
						<div class="list"><!--list begin-->
							<div class="sheng">
							</div>
							<div class="shi city-list" style="display: none;"><!--city_area begin-->
							</div><!--city_area end-->
						</div><!--list end-->
					</div><!--all_city end-->
				</div><!--l_menu_box_m end-->
				<div class="l_select_menu_box_b"></div>
			</div><!--l_select_menu_box end-->
		</div><!--l_select_menu end--> --%>
		<c:if test="${not empty townId}">
			<div id="town-select" class="select_menu"><!--select_menu begin-->
				<p><a href="javascript:void(0);"></a></p>
				<div></div>
				<div class="select_box"><!--select_box begin-->
					<span>
						<a href="javascript:void(0);" value="0"   <c:if test="${townId == 0}">class="selected"</c:if>><c:choose><c:when test="${cityId > 0}">全${jzd:cityName(cityId)}</c:when><c:otherwise>全国</c:otherwise></c:choose></a>
						<c:forEach var="town" items="${jzd:townList(cityId)}">
							<a href="javascript:void(0);" value="${town.id}"   <c:if test="${townId==town.id}">class="selected"</c:if>>${town.name}</a>
						</c:forEach>
					</span>
					<div class="ch_area"><a href="/profile/index">我要搬家</a></div>
					<em></em>
				</div><!--select_box end-->
			</div><!--select_menu end-->
		</c:if>
		<div id="gender-select" class="select_menu"><!--select_menu begin-->
			<p><a href="javascript:void(0);" hidefocus></a></p>
			<div></div>
			<div class="select_box"><!--select_box begin-->
				<span>
					<a href="javascript:void(0);" value="all" <c:if test="${genderType == 'all'}">class="selected"</c:if>>所有小宅</a>
					<a href="javascript:void(0);" value="male" <c:if test="${genderType == 'male'}">class="selected"</c:if>>宅男</a>
					<a href="javascript:void(0);" value="female" <c:if test="${genderType == 'female'}">class="selected"</c:if>>宅女</a>
				</span>
				<em></em>
			</div><!--select_box end-->
		</div><!--select_menu end-->
		<div class="cake" open-preference="${openPreference}"><!--cake begin-->
			<div class="cake_icon"><a href="javascript:void(0);" title="调整口味"></a></div>
			<div class="cake_show" style="display: none;">
				<c:if test="${not empty preferenceInput }">
				<p>对哪些人的拒宅信息有兴趣？</p>
					<form id="preferenceForm">
						<div class="ck_box">
							<c:forEach items="${preferenceInput.options}" var="option">
								<span><b><input name="userPreferences[0].answer" type="checkbox" value="${option.value }" <c:forEach items="${userGenders}" var="box"><c:if test="${box==option.value}"> checked="checked"</c:if></c:forEach> /></b><em>${option.name}</em></span>
							</c:forEach>
						</div>
						<input type="hidden" value="${preferenceId}" name="userPreferences[0].preferenceId"/>
						<a href="javascript:void(0);" class="btn">保存</a>
					</form>
				</c:if>
				<a href="/profile/preference" class="txt">去设置偏好</a>
			</div>
		</div><!--cake end-->
		
		<div class="category" queryType="${queryType}"><!--category begin-->
			<span <c:if test="${queryType == 'showposts'}">class="act"</c:if>><p></p><a href="/home/showposts/0_${genderType}/1">最新</a><p></p></span>
			<span <c:if test="${queryType == 'showoposts'}">class="act"</c:if>><p></p><a href="/home/showoposts/0_${genderType}/1">活跃</a><p></p><c:if test="${not empty onlineCount&&onlineCount>0}"><c:choose><c:when test="${onlineCount<100 }"><em title="当前${onlineCount}人在线">${onlineCount}</em></c:when><c:otherwise><em title="当前99+人在线">${showUserOnlineMaxRows}+</em></c:otherwise></c:choose></c:if></span>
			<span <c:if test="${queryType == 'showiposts'}">class="act"</c:if>><p></p><a href="/home/showiposts/${genderType}/1">关注</a><p></p></span>
			<span <c:if test="${queryType == 'showrposts'}">class="act"</c:if>><p></p><a href="/home/showrposts/${genderType}/1">喜欢</a><p></p></span>
		</div><!--category end-->
	</div><!--title end-->		
	<div class="jz_main"><!--jz_main begin-->
		<c:if test="${pager.totalResults <= 0}">
			<c:choose>
				<c:when test="${empty postViewList}"><div class="none">这里还没有人发布拒宅哦</div></c:when>
				<c:otherwise>
					<div class="other_recom"><!--other_recom begin-->
						<em></em>
						<p>这里还没人发布拒宅哦</p>
					</div><!--other_recom end-->
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${not empty postViewList}">
			<c:forEach var="postView" items="${postViewList}">
				<div class="jz_item mouseHover <c:choose><c:when test="${postView.profileCache.gender == 1}">boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--jz_item begin-->
					<div class="face_infor"><!--face_infor begin-->
						<p><a href="/home/${postView.profileCache.uid}"><img src="${jzr:userLogo(postView.profileCache.uid,postView.profileCache.logoPic,120)}" width="120" height="120" /></a></p>
						<a href="/home/${postView.profileCache.uid}"><c:out value="${postView.profileCache.nickname}" /></a>
						<c:if test="${queryType == 'showoposts'}">	
							<c:if test="${postView.lastWebLoginTime != null}">
								<c:set var="date" value="${postView.lastWebLoginTime}" scope="request"/><c:import url="/WEB-INF/jsp/web/common/fragment/show_login_time.jsp" />
							</c:if>
						</c:if>
						<c:set var="age" value="${jzu:age(postView.profileCache.birthYear, postView.profileCache.birthSecret)}" />
						<c:set var="constellationName" value="${jzd:constellationName(postView.profileCache.constellationId)}" />
						<span><c:if test="${age > 0}">${age}岁&nbsp;</c:if><c:if test="${postView.profileCache.city != null && postView.profileCache.city > 0}">${jzd:cityName(postView.profileCache.city)}<c:if test="${postView.profileCache.town != null && postView.profileCache.town > 0}">${jzd:townName(postView.profileCache.town)}</c:if>&nbsp;</c:if><c:if test="${not empty constellationName}">${constellationName}&nbsp;</c:if><c:if test="${not empty postView.profileCache.profession}">${postView.profileCache.profession}</c:if></span>
					</div><!--face_infor end-->
					<div class="wtg"><!--wtg begin-->
						<div class="w_t"></div>
						<div class="w_m"><!--w_m begin-->
							<div class="arrow"></div>
							<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${postView.post.purposeType}"/></c:import>:</font><c:out value="${postView.post.content}" />
								<c:if test="${not empty postView.post.link}">
									<c:choose>
											<c:when test="${not empty  postView.post.ideaId }">
													<c:set value="/idea/${postView.post.ideaId}" var="link"></c:set>		
											</c:when>
											<c:otherwise>
													<c:set value="${postView.post.link}" var="link"></c:set>	
											</c:otherwise>
									</c:choose>
									<a href="${link}" class="lj_more" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>了解更多&gt;</a>
								</c:if>
							</p>
							<div class="infor"><!--infor begin-->
								<c:if test="${not empty postView.post.pic}">
									<div class="img"><a href="/post/${postView.post.id}"><img data-original="${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}" src="${jzr:static('/images/web2/1px.gif')}"/></a></div>
								</c:if>
								<%-- <span><c:set var="date" value="${postView.post.createTime}" scope="request" /><c:import url="/WEB-INF/jsp/web/common/fragment/show_time.jsp" />更新</span> --%>
								<span class="tag">${jzd:categoryName(postView.post.categoryId)}</span>
								<c:if test="${postView.post.dateTime != null}">
									<span class="time"><fmt:formatDate value="${postView.post.dateTime}" pattern="yyyy.MM.dd"/></span>
								</c:if>
								<c:if test="${not empty postView.post.place}">
									<span class="adress"><c:out value="${jzu:truncate(postView.post.place,40,'...')}"></c:out></span>
								</c:if>
								<c:if test="${not empty postView.useCount&&(postView.useCount-1)>0}">
									<span><a href="/idea/${postView.post.ideaId}">还有${postView.useCount-1}人想去</a></span>
								</c:if>
							</div><!--infor end-->
						</div><!--w_m end-->
						<div class="clear"></div>
						<div class="w_b"></div>
						<div class="btn"><!--btn begin-->
							<c:if test="${postView.profileCache.uid != context.uid}">
								<div class="keep user-remove-interest remove-interest-${postView.profileCache.uid}" <c:if test="${!postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" class="done" uid="${postView.profileCache.uid}" title="点击取消关注">已关注</a></div>
								<div class="keep user-add-interest interest-${postView.profileCache.uid}" <c:if test="${postView.hasInterest}">style="display: none;"</c:if>><a href="javascript:void(0);" uid="${postView.profileCache.uid}" title="点击关注">关注ta</a></div>
								<div class="mail"><a href="javascript:void(0);" title="给ta发私信" target-uid="${postView.profileCache.uid}" target-nickname="<c:out value='${postView.profileCache.nickname}' />">私信</a></div>
							</c:if>
							<c:if test="${not empty isQplus && isQplus}">	
									<div class="share_icon"><a href="javascript:void(0);" onclick="qPlusShare('我想找伴去:${postView.post.content}<c:if test='${postView.post.dateTime != null}'> 时间:<fmt:formatDate value='${postView.post.dateTime}' pattern='yyyy.MM.dd'/></c:if><c:if test='${not empty postView.post.place}'> 地点:${jzu:truncate(postView.post.place,40,'...')}</c:if>','','${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}','','拒宅网');return false;" title="分享">分享</a></div>
							</c:if>
							<div class="message_s2"><a href="javascript:void(0);" post-id="${postView.post.id}">留言<c:if test="${postView.post.commentCnt > 0}">(${postView.post.commentCnt})</c:if></a></div>
							<c:if test="${postView.profileCache.uid != context.uid}">
								<c:choose>
									<c:when test="${postView.hasResponse}">
										<div class="like done"><a href="javascript:void(0);" class="xy">有兴趣</a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);">${postView.post.responseCnt}</a><p class="r"></p></div></div>
									</c:when>
									<c:otherwise>
										<div class="like post-response" id="response${postView.post.id}" post-id="${postView.post.id}" resp-count="${postView.post.responseCnt}" nickname="<c:out value='${postView.profileCache.nickname}' />" post-content="<c:out value="${jzu:truncate(postView.post.content,50,'...')}" />"><a href="javascript:void(0);" class="xy">有兴趣 </a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);">${postView.post.responseCnt}</a><p class="r"></p></div></div>
									</c:otherwise>
								</c:choose>
								<div class="zfa"><a href="javascript:void(0);" post-id="${postView.post.id}">我也想去</a></div>
							</c:if>
						</div><!--btn end-->
						<div class="clear"></div>
						<div class="message_s2_box" id="comment-box-${postView.post.id}" loaded="false" style="display: none;"><!--message_box begin-->
							<div class="box_top"></div>
							<div class="box_main"><!--box_main begin-->
								<div class="arrow"></div>
								<c:set var="postId" value="${postView.post.id}" scope="request"/>
								<jsp:include page="/WEB-INF/jsp/web/post/comment_send_box.jsp" />
								<div class="comment-list">
									<div class="repy_list_s2 bd_line">
										<div class="list_loading"><em><img src="${jzr:static('/images/web2/list_loading.gif')}"  width="16" height="16"/></em><p>留言加载中...</p></div>
									</div>
								</div>
								<div class="clear"></div>
							</div><!--box_main end-->
							<div class="box_bottom"></div>
						</div><!--message_box end-->
					</div><!--wtg end-->
				</div><!--jz_item end-->
			</c:forEach>
		</c:if>
	</div><!--jz_main end-->
</div><!--jz_list end-->
<div class="clear"></div>
<div class="line"></div>
<c:if test="${not empty townId}">
	<c:set var="urlPrefix" value="${townId}_" />
</c:if>
<c:if test="${pager.totalResults > 0}">
	<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
		<c:param name="pager" value="${pager}"/>
		<c:param name="url" value="/home/${queryType}/${urlPrefix}${genderType}" />
	</c:import>
</c:if>