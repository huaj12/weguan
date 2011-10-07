<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
								<c:choose>
									<c:when test="${!empty actMsgViewList}">
	 									<c:forEach var="actMsg" items="${actMsgViewList}" varStatus="msg">
											<div class="item hover"><!--item begin-->
												<span class="l"></span><span class="r"></span>
												<div class="close">
													<a  href="javascript:;" onclick="remove('${pager.currentPage}','${msg.index }','unread')"></a>
												</div>
												<div class="item_style2"><!--item_style2 begin-->
													<div class="photo fl">
														<img src="${actMsg.profileCache.logoPic }" width="80" height="80" />	
													</div>
													<div class="infor fl"><!--infor begin-->
														<h2>
															<c:choose>
																<c:when test="${actMsg.msgType=='INVITE'}">
																	${actMsg.profileCache.nickname } 邀你拒宅
																</c:when>
																<c:otherwise>
																	${actMsg.profileCache.nickname } 添加了1个跟你相同的拒宅兴趣
																</c:otherwise>
															</c:choose>
														</h2>
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
														</p>
														<c:choose>
																<c:when test="${actMsg.msgType=='INVITE'}">
																		<a href="javascript:;" title="需消耗20积分" onclick="javascrpit:openmsg('${pager.currentPage}','${msg.index }','INVITE');" class="view">立即查看</a>
																		<strong>需消耗20积分</strong>
																</c:when>
																<c:otherwise>
																		<a href="javascript:;" title="需消耗10积分" onclick="javascrpit:openmsg('${pager.currentPage}','${msg.index }','RECOMMEND');" class="view">立即查看</a>
																		<strong>需消耗10积分</strong>
																</c:otherwise>
															</c:choose>
														<em><fmt:formatDate value="${actMsg.date}" pattern="yyyy.MM.dd HH:mm"/></em>
													</div><!--infor end-->
												</div><!--item_style2 end-->
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