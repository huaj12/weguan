<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
										<div class="content_box w285"><!--content begin-->
											<div class="t" style="background:#EFF6FC;"></div>
											<div class="m" style="background:#EFF6FC;">
												<div class="right_title"><h3>加入拒宅网，找伴儿出去玩</h3></div>
													<div class="index_login_area"><!--wel_login_area begin-->
														<a href="javascript:void(0);" class="wb login-btn" title="使用微博账号登录" go-uri="/web/login/6"></a>
														<a href="javascript:void(0);" class="db login-btn" title="使用豆瓣账号登录" go-uri="/web/login/7"></a>
														<c:choose>
															<c:when test="${not empty isQplus&&isQplus}">
															<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/qplus/loginDialog/9"></a>
															</c:when>
															<c:otherwise>
															<a href="javascript:void(0);" class="qq login-btn" title="使用QQ账号登录" go-uri="/web/login/8"></a>	
															</c:otherwise>
														</c:choose>
												</div><!--wel_login_area end-->
											</div>
											<div class="t" style="background:#EFF6FC;"></div>
										</div><!--content end-->