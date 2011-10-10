<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
							<c:choose>
										<c:when test="${!empty actMsgViewList}">
									<c:forEach var="actMsg" items="${actMsgViewList}" varStatus="msg">
										<div id="msgItem" onmouseover="moveoverItem(this)" onmouseout="moveoutItem(this)" class="item link"><!--item begin-->
											<span class="l"></span><span class="r"></span>
											<div class="close">
												<a href="javascript:;" onclick="remove('${pager.currentPage}','${msg.index }','read','${pager.totalPage}')"></a>
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
															<span class="v">${actMsg.act.name }</span>
														</c:when>
														<c:otherwise>
															<span class="w">最近也想去</span><span class="v"> ${actMsg.act.name }</span>
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
															<a href="http://www.kaixin001.com/msg/write.php?uids=${actMsg.profileCache.tpIdentity}" target="_blank">马上联系他</a>
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