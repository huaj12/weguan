<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource"%>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<div class="project_want_go"><!--project_want_go begin-->
	<div class="title"><!--title begin-->
		<h2>想约个人一起去玩<c:out value="${act.name}" />么？</h2>
		<span>
			<select id="genderSelect" actid="${act.id}" cityid="${cityId}">
				<option value="all" <c:if test="${genderType=='all'}">selected="selected"</c:if>>性别不限</option>
				<option value="male" <c:if test="${genderType=='male'}">selected="selected"</c:if>>男性</option>
				<option value="female" <c:if test="${genderType=='female'}">selected="selected"</c:if>>女性</option>
			</select>
		</span>
		<span>
			<select id="citySelect" actid="${act.id}" gendertype="${genderType}">
				<option value="0" <c:if test="${cityId==0}">selected="selected"</c:if>>全国</option>
				<option value="2" <c:if test="${cityId==2}">selected="selected"</c:if>>上海</option>
				<option value="1" <c:if test="${cityId==1}">selected="selected"</c:if>>北京</option>
				<option value="181" <c:if test="${cityId==181}">selected="selected"</c:if>>广州</option>
				<option value="183" <c:if test="${cityId==183}">selected="selected"</c:if>>深圳</option>
				<option value="108" <c:if test="${cityId==108}">selected="selected"</c:if>>武汉</option>
				<option value="3" <c:if test="${cityId==3}">selected="selected"</c:if>>天津</option>
				<option value="69" <c:if test="${cityId==69}">selected="selected"</c:if>>西安</option>
				<option value="157" <c:if test="${cityId==157}">selected="selected"</c:if>>南京</option>
				<option value="343" <c:if test="${cityId==343}">selected="selected"</c:if>>杭州</option>
				<option value="241" <c:if test="${cityId==241}">selected="selected"</c:if>>成都</option>
				<option value="4" <c:if test="${cityId==4}">selected="selected"</c:if>>重庆</option>
				<option value="27" <c:if test="${cityId==27}">selected="selected"</c:if>>沈阳</option>
				<option value="90" <c:if test="${cityId==90}">selected="selected"</c:if>>郑州</option>
				<option value="125" <c:if test="${cityId==125}">selected="selected"</c:if>>长沙</option>
				<option value="363" <c:if test="${cityId==363}">selected="selected"</c:if>>香港</option>
			</select>
		</span>
	</div><!--title end-->
	<c:choose>
		<c:when test="${empty actUserViewList}">
			<div class="none">
				目前还没有人想玩${act.name}！
			</div>
		</c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="actUserView" items="${actUserViewList}">
					<li class="mouseHover <c:choose><c:when test='${actUserView.profileCache.gender==1}'>boy</c:when><c:otherwise>girl</c:otherwise></c:choose>"><!--li begin-->
						<div class="photo"><a href="/home/${actUserView.profileCache.uid}"><img data-original="${jzr:userLogo(actUserView.profileCache.uid,actUserView.profileCache.logoPic,80)}" src="${jzr:static('/images/web/1px.gif')}" height="80" width="80"/></a></div>
						<div class="name"><a href="/home/${actUserView.profileCache.uid}"><c:out value="${actUserView.profileCache.nickname}" /></a><c:if test="${actUserView.online}"><p class="online">当前在线</p></c:if></div>
						<c:set var="cityName" value="${jzd:cityName(actUserView.profileCache.city)}" />
						<c:set var="townName" value="${jzd:townName(actUserView.profileCache.town)}" />
						<div class="time">
							<c:if test="${not empty cityName || not empty townName}">
								<span><c:if test="${not empty cityName}">${cityName}</c:if><c:if test="${not empty townName}">${townName}</c:if><em>|</em></span>
							</c:if>
							<p><c:choose><c:when test='${not empty actUserView.freeDateList}'>${jzu:showFreeDates(actUserView.freeDateList,7)}&nbsp;有空</c:when><c:otherwise>还未标注空闲时间</c:otherwise></c:choose></p>
						</div>
						<div></div>
						<div class="btn">
							<c:if test="${context.uid!=actUserView.profileCache.uid}">
								<a href="javascript:void(0);" class="message_sbtn" target-uid="${actUserView.profileCache.uid}" target-nickname="${actUserView.profileCache.nickname}">私信</a>
								<a id="date${actUserView.profileCache.uid}" href="javascript:void(0);" class="yueta_sbtn" <c:if test="${actUserView.hasDating}">style="display:none;"</c:if> uid="${actUserView.profileCache.uid}">约ta</a>
								<a id="hasDate${actUserView.profileCache.uid}" href="javascript:void(0);" class="yueta_done_sbtn" <c:if test="${!actUserView.hasDating}">style="display:none;"</c:if>>已约</a>
								<a id="interest${actUserView.profileCache.uid}" href="javascript:void(0);" class="qm_sbtn" <c:if test="${actUserView.hasInterest}">style="display:none;"</c:if> uid="${actUserView.profileCache.uid}">敲门</a>
								<div id="hasInterest${actUserView.profileCache.uid}" class="cancel_qm" <c:if test="${!actUserView.hasInterest}">style="display:none;"</c:if>><p>已敲门</p><a href="javascript:void(0);" class="delete" uid="${actUserView.profileCache.uid}"></a></div>
							</c:if>
							<%-- <c:if test="${context.uid!=actUserView.profileCache.uid}">
								<div class="link_t" <c:if test="${actUserView.hasInterest}">style="display:none;"</c:if>><!--link_t begin-->
									<p></p><a href="javascript:void(0);" uid="${actUserView.profileCache.uid}">敲门</a><p></p>
								</div><!--link_t end-->
								<div id="hasInterest${actUserView.profileCache.uid}" class="link_t_done" <c:if test="${!actUserView.hasInterest}">style="display:none;"</c:if>><!--link_t_done begin-->
									<p></p><span>已敲门</span><p></p>
								</div><!--link_t_done end-->
							</c:if>
							<c:if test="${context.uid!=actUserView.profileCache.uid}">
								<div id="date${actUserView.profileCache.uid}" class="date_t" <c:if test="${actUserView.hasDating}">style="display:none;"</c:if>><!--date_t begin-->
									<p></p><a href="javascript:void(0);" uid="${actUserView.profileCache.uid}">约ta</a><p></p>
								</div><!--date_t end-->
								<div id="hasDate${actUserView.profileCache.uid}" class="date_t_done" <c:if test="${!actUserView.hasDating}">style="display:none;"</c:if>><!--date_t_done begin-->
									<p></p><span>已约</span><p></p>
								</div><!--date_t_done end-->
							</c:if> --%>
						</div>
					</li><!--li end-->
				</c:forEach>
			</ul>
			<div class="clear"></div>
			<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
				<c:param name="pager" value="${pager}"/>
				<c:param name="url" value="/act/${act.id}/users_${genderType}_${cityId}" />
			</c:import>
		</c:otherwise>
	</c:choose>
</div><!--project_want_go end-->
