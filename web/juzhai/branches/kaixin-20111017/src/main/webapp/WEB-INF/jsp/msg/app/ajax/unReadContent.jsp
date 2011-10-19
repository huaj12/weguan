<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:choose>
	<c:when test="${!empty actMsgViewList}">
		<c:forEach var="actMsg" items="${actMsgViewList}" varStatus="msg">
			<div onmouseover="moveoverItem(this)" onmouseout="moveoutItem(this)"
				class="item link">
				<!--item begin-->

				<span class="l"></span><span class="r"></span>
				<div class="close">
					<a href="javascript:;"
						onclick="remove('${pager.currentPage}','${msg.index }','unread','${pager.totalResults}')"></a>
				</div>

				<div class="item_style1">
					<!--item_style1 begin-->

					<div class="photo fl">
						<img src="${actMsg.profileCache.logoPic}" width="80" height="80" />
					</div>

					<div class="infor fl">
						<!--infor begin-->
						<c:choose>
							<c:when test="${actMsg.msgType=='INVITE'}">
								<span class="u">${actMsg.profileCache.nickname }</span>
								<span class="w">发给你${actMsg.actCount }个邀你拒宅</span>
							</c:when>
							<c:otherwise>
								<span class="u">${actMsg.profileCache.nickname }</span>
								<span class="w">有${actMsg.actCount}个跟你相同的拒宅兴趣</span>
							</c:otherwise>
						</c:choose>
						<div class="clear"></div>

						<p>
							<c:choose>
								<c:when test="${!empty actMsg.profileCache.cityName }">
									ta在${actMsg.profileCache.cityName}
								</c:when>
								<c:otherwise>
									ta来自地球
								</c:otherwise>
							</c:choose>
							发布于
							<fmt:formatDate value="${actMsg.date}" pattern="yyyy.MM.dd" />
						</p>

					</div>
					<!--infor end-->

					<div class="btn">
						<c:choose>
							<c:when test="${actMsg.msgType=='INVITE'}">
								<a href="javascript:;" title="需消耗${invitePoint}积分"
									onclick="javascrpit:openmsg('${pager.currentPage}','${msg.index }','INVITE','${invitePoint}');"
									>立即查看</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:;" title="需消耗${recommendPoint}积分"
									onclick="javascrpit:openmsg('${pager.currentPage}','${msg.index }','RECOMMEND','${recommendPoint}');"
									>立即查看</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<!--item_style1 end-->

			</div>
			<!--item end-->

		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="other_message">
			<p>目前没有信息</p>
		</div>
	</c:otherwise>
</c:choose>
<!-- page -->
<jsp:include page="/WEB-INF/jsp/pager/pager.jsp" />
<!-- page end -->