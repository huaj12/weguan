<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:choose>
	<c:when test="${!empty actMsgViewList}">
		<c:forEach var="actMsg" items="${actMsgViewList}" varStatus="msg">
			<div id="msgItem" onmouseover='moveoverItem(this)' onmouseout='moveoutItem(this)' class="item link">
				<!--item begin-->
				<span class="l"></span><span class="r"></span>
				<div class="close">
					<a href="javascript:;"
						onclick="remove('${pager.currentPage}','${msg.index }','read','${pager.totalResults}')"></a>
				</div>
				<div class="item_style1">
					<!--item_style1 begin-->
					<div class="photo fl">
						<img src="${actMsg.profileCache.logoPic }" width="80" height="80" />
					</div>
					<div class="infor fl">
						<!--infor begin-->
						<span class="u">${actMsg.profileCache.nickname }</span>
						<c:choose>
							<c:when test="${actMsg.msgType=='INVITE'}">
								<span class="w">想和你去</span>
								<c:forEach items="${actMsg.acts}" var="act" varStatus="step">
									<c:if test="${step.index<3}">
										<span class="v">${jz:truncate(act.name,14,'...')}</span>
									</c:if>
								</c:forEach>
								<c:if test="${actMsg.actCount>3}">
									<div style="display: none" id="interestBox_${msg.index}"
										class="show_pro">
										<!--show_pro begin-->
										<c:forEach items="${actMsg.acts}" var="act" varStatus="step">
											<p>${jz:truncate(act.name,14,'...')}</p>
										</c:forEach>
										<div class="clear"></div>
										<div class="btn">
											<!--btn begin-->
											<a href="javascript:;" onclick="closeAllDiv();">知道了</a>
										</div>
										<!--btn end-->
									</div>
									<!--show_pro end-->
									<a href="javascript:;"
										onclick="queryAll('${actMsg.profileCache.nickname }想和你去','${msg.index }')">等${actMsg.actCount}个项目</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<span class="w">想和你去</span>
								<c:forEach items="${actMsg.acts}" var="act" varStatus="step">
									<c:if test="${step.index<3}">
										<span class="v">${jz:truncate(act.name,14,'...')}</span>
									</c:if>
								</c:forEach>
								<c:if test="${actMsg.actCount>3}">
									<div style="display: none" id="interestBox_${msg.index}"
										class="show_pro">
										<!--show_pro begin-->
										<c:forEach items="${actMsg.acts}" var="act" varStatus="step">
											<p>${jz:truncate(act.name,14,'...')}</p>
										</c:forEach>
										<div class="clear"></div>
										<div class="btn">
											<!--btn begin-->
											<a href="javascript:;" onclick="closeAllDiv();">知道了</a>
										</div>
										<!--btn end-->
									</div>
									<!--show_pro end-->
									<a href="javascript:;"
										onclick="queryAll('${actMsg.profileCache.nickname }想和你去','${msg.index }')">等${actMsg.actCount}个项目</a>
								</c:if>
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
					<c:set var="s" value="" />
					<c:forEach items="${actMsg.acts}" var="act" varStatus="step"><c:choose><c:when test="${step.last}"><c:set var="s" value="${s}${act.name}"></c:set></c:when><c:otherwise><c:set var="s" value="${s}${act.name}、"></c:set></c:otherwise></c:choose> </c:forEach>
						<a href="javascript:;" onclick="showBoard('${actMsg.profileCache.nickname }','${s}','${actMsg.profileCache.tpIdentity}')">联系ta</a>
					</div>
				</div>
				<!--item_style1 end-->
			</div>
			<!--item end-->
			<!--show_pro end-->
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="other_message">
			<p>目前没有信息</p>
		</div>
	</c:otherwise>
</c:choose>
<!-- page -->
<jsp:include page="/WEB-INF/jsp/app/pager/pager.jsp" />
<!-- page end -->