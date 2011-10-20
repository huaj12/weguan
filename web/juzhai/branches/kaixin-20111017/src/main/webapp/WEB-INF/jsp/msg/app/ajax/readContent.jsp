<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
							<c:choose>
										<c:when test="${!empty actMsgViewList}">
									<c:forEach var="actMsg" items="${actMsgViewList}" varStatus="msg">
										<div id="msgItem" onmouseover="moveoverItem(this)" onmouseout="moveoutItem(this)" class="item link"><!--item begin-->
											<span class="l"></span><span class="r"></span>
											<div class="close">
												<a href="javascript:;" onclick="remove('${pager.currentPage}','${msg.index }','read','${pager.totalResults}')"></a>
											</div>
											<div class="item_style1"><!--item_style1 begin-->
												<div class="photo fl">
													<img src="${actMsg.profileCache.logoPic }" width="80" height="80" />
												</div>
												<div class="infor fl"><!--infor begin-->
													<span class="u">${actMsg.profileCache.nickname }</span>
													<c:choose>
														<c:when test="${actMsg.msgType=='INVITE'}">
															<span class="w">想和你去</span>
															<c:forEach items="${actMsg.acts}" var="act" varStatus="step" >
																<c:if test="${step.index<3}">
																<span class="v">${jz:truncate(act.name,14,'...')}</span>
																</c:if>
															</c:forEach>
															<c:if test="${actMsg.actCount>3}">
															<div style="display: none" id="interestBox_${msg.index}" class="show_pro"><!--show_pro begin-->
															<c:forEach items="${actMsg.acts}" var="act" varStatus="step" >
																<p>${jz:truncate(act.name,14,'...')}</p>
															</c:forEach>
																<div class="clear"></div>
																<div class="btn"><!--btn begin-->
																<a href="javascript:;" onclick="closeAllDiv();">知道了</a>
																</div><!--btn end-->
															</div><!--show_pro end-->
															<a  href="javascript:;" onclick="queryAll('${actMsg.profileCache.nickname }想和你去','${msg.index }')">等${actMsg.actCount}个项目</a>
															</c:if>
														</c:when>
														<c:otherwise>
															<span class="w">最近也想去</span>
															<c:forEach items="${actMsg.acts}" var="act" varStatus="step" >
																<c:if test="${step.index<3}">
																<span class="v">${jz:truncate(act.name,14,'...')}</span>
																</c:if>
															</c:forEach>
															<c:if test="${actMsg.actCount>3}">
															<div style="display: none" id="interestBox_${msg.index}" class="show_pro"><!--show_pro begin-->
															<c:forEach items="${actMsg.acts}" var="act" varStatus="step" >
																<p>${jz:truncate(act.name,14,'...')}</p>
															</c:forEach>
																<div class="clear"></div>
																<div class="btn"><!--btn begin-->
																<a href="javascript:;" onclick="closeAllDiv();">知道了</a>
																</div><!--btn end-->
															</div><!--show_pro end-->
															<a  href="javascript:;" onclick="queryAll('${actMsg.profileCache.nickname }最近也想去','${msg.index }')">等${actMsg.actCount}个项目</a>
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
															发布于<fmt:formatDate value="${actMsg.date}" pattern="yyyy.MM.dd" />
													 </p>
												</div><!--infor end-->
												<div class="btn">
													<c:choose>
														<c:when test="${actMsg.msgType=='INVITE'}">
															<a href="http://www.kaixin001.com/msg/write.php?uids=${actMsg.profileCache.tpIdentity}" target="_blank">联系ta</a>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${actMsg.stuts}">
																	<a class="unhover">响应已发</a>
																</c:when>
																<c:otherwise>
																	<a href="javascript:;"  id="invite_btn_${msg.index }" onclick="initinvite_Div();invite_app_div('邀请${actMsg.profileCache.nickname}去','${msg.index }');">邀请ta</a>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</div>
											</div><!--item_style1 end-->
										</div><!--item end-->
										
										<!-- artDialog div -->
										<div class="show_pro" style="display: none" id="invite_app_div_${msg.index }"><!--show_pro begin-->
										<c:forEach items="${actMsg.acts}" var="act" varStatus="step" >
										<p><span class="l"></span><a href="javascript:;" id="${act.id}" onmouseout="hoverAct(this,false)" onmouseover="hoverAct(this,true)" onclick="clickAct(this)" class="key_words">${jz:truncate(act.name,14,'...')}</a><span class="r"></span><em></em></p>
										</c:forEach>
										<div class="clear"></div>
										<div class="btn"><!--btn begin-->
										<a href="javascript:;" onclick="invite_app_friend(this,'${pager.currentPage}','${msg.index }','${actMsg.profileCache.uid}')">邀请ta</a>
										</div><!--btn end-->
										</div><!--show_pro end-->
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