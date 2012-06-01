<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
							<div class="content_box w285">
								<!--content begin-->
								<div class="t"></div>
								<div class="m">
									<div class="right_title"><h2>搜索拒宅</h2></div>
									<div class="search_xz"><!--search_xz begin-->
										<form action="/searchposts" id="search-post-form" method="get"><div class="s_input"><p></p><span><input type="text" init-tip="通过关键词搜索" name="queryString"/></span><input type="submit" style="display: none"/><a href="javascript:void(0);"></a></div></form>
										<div class="xg_tags">
											<ul>
												<c:if test="${not empty hots }">
													<c:forEach items="${hots}" var="hot">
													<li><a href="/searchposts?queryString=${hot.name}&sex=all" class="n">${hot.name}</a><c:if test="${hot.hot!=0}"><a href="/searchposts?queryString=${hot.name}&sex=all" class="g">${hot.hot}人想去</a></c:if></li>
													</c:forEach>
												</c:if>
											</ul>
										</div>
									</div><!--search_xz end-->
								</div>
								<div class="t"></div>
							</div>