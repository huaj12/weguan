<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
				<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
				<meta name="keywords" content="拒宅 ,找伴, 出去玩, 约会, 交友" />
				<meta name="description" content="不想宅在家,找伴儿,出去玩,发现出去玩的好主意和同兴趣的朋友，促成约会" />
				<title>搜索拒宅 拒宅网-助你找伴儿出去玩(51juzhai.com)</title>
				<link href="${jzr:static('/css/jz_web.css')}" rel="stylesheet" type="text/css" />
		</head>
		<body>
				<div class="warp"><!--warp begin-->
				<div class="main"><!--main begin-->
				<jsp:include page="/WEB-INF/jsp/web/common/header.jsp" />
				<div class="main_part"><!--main_part begin-->
					<div class="main_left"><!--main_left begin-->
					<div class="content_box w660 800"><!--content begin-->
						<div class="t"></div>
						<div class="m">
							<div class="jz_list"><!--jz_list begin-->
							<form action="/searchposts" id="search-post-form" method="get">
								<div class="search_jg"><!--search_jg begin-->
									<h2>拒宅搜索</h2>
									<div class="input"><!--input begin-->
									<p class="l"></p><span class="width70"><input name="queryString" type="text" value="${queryString}" /></span><p class="r"></p>
									</div><!--input end-->
									<a href="javascript:void(0);">搜</a>
								</div><!--search_jg end-->
								<div class="clear"></div>
								<div class="jg_title"><!--jg_title begin-->
									<div class="jg_left"><p>在<c:choose><c:when test="${city== 0}">全国</c:when><c:otherwise>${jzd:cityName(city)}</c:otherwise></c:choose>共搜索到<font>${pager.totalResults}</font>条 关于<i>${queryString}</i>的信息</p></div>
									<div class="jg_right">
										<h5>筛选:</h5>
										<div class="select_menu" id="sex-select" name="sex"><!--select_menu begin-->
													<p><a href="javascript:void(0);">所有小宅</a></p>
													<div></div>
													<div class="select_box"><!--select_box begin-->
														<span>
															<a href="javascript:void(0);" value="all" <c:if test="${empty sex||sex=='all'}">class="selected"</c:if>>所有小宅</a>
															<a href="javascript:void(0);" value="male" <c:if test="${sex=='male'}">class="selected"</c:if>>宅男</a>
															<a href="javascript:void(0);" value="female" <c:if test="${sex=='female'}">class="selected"</c:if>>宅女</a>
														</span>
														<em></em>
													</div><!--select_box end-->
										</div><!--select_menu end-->
									</div>
								</div><!--jg_title end-->
								<input type="submit" style="display: none"/>
							</form>
							<div class="jz_main"><!--jz_main begin-->
								<c:choose>
								<c:when test="${not empty postViewList}">
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
														<p><font><c:import url="/WEB-INF/jsp/web/common/fragment/post_purpose_type.jsp"><c:param name="purposeType" value="${postView.post.purposeType}"/></c:import>:</font><a href="/post/${postView.post.id}">${postView.post.content}</a></p>
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
															<c:if test="${not empty postView.post.link}">
																<span class="link"><a href="${postView.post.link}" <c:if test="${empty isQplus || !isQplus}">target="_blank"</c:if>>查看相关链接</a></span>
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
																<div class="share_icon"><a href="javascript:void(0);" onclick="qPlusShare('我想找伴去:${fn:replace(fn:replace(postView.post.content,'</i>',''),'<i>','')}<c:if test='${postView.post.dateTime != null}'> 时间:<fmt:formatDate value='${postView.post.dateTime}' pattern='yyyy.MM.dd'/></c:if><c:if test='${not empty postView.post.place}'> 地点:${jzu:truncate(postView.post.place,40,'...')}</c:if>','','${jzr:postPic(postView.post.id, postView.post.ideaId, postView.post.pic, 200)}','','拒宅网');return false;" title="分享">分享</a></div>
														</c:if>
														<div class="message_s2"><a href="javascript:void(0);" post-id="${postView.post.id}">留言<c:if test="${postView.post.commentCnt > 0}">(${postView.post.commentCnt})</c:if></a></div>
														<c:if test="${postView.profileCache.uid != context.uid}">
															<c:choose>
																<c:when test="${postView.hasResponse}">
																	<div class="like done"><a href="javascript:void(0);" class="xy">好主意</a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);">${postView.post.responseCnt}</a><p class="r"></p></div></div>
																</c:when>
																<c:otherwise>
																	<div class="like post-response" id="response${postView.post.id}" post-id="${postView.post.id}" resp-count="${postView.post.responseCnt}" nickname="<c:out value='${postView.profileCache.nickname}' />" post-content="<c:out value="${jzu:truncate(postView.post.content,50,'...')}" />"><a href="javascript:void(0);" class="xy">好主意</a><div class="xy_num"><p class="l"></p><a href="javascript:void(0);">${postView.post.responseCnt}</a><p class="r"></p></div></div>
																</c:otherwise>
															</c:choose>
															<div class="zfa"><a href="javascript:void(0);" post-id="${postView.post.id}">转发</a></div>
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
									</c:when>
									<c:otherwise>
										<div class="search_none"><!--search_none begin-->
											抱歉，没有搜到合适的内容<br />
											<br />
											建议：<br />
											请尽量输入常用词<br />
											请尽量使用简洁的关键词<br />
											</div><!--search_none end-->
									</c:otherwise>
									</c:choose>
							</div><!--jz_main end-->
						</div><!--jz_list end-->
							<div class="clear"></div>
							<c:if test="${pager.totalResults > 0}">
								<c:import url="/WEB-INF/jsp/web/common/pager.jsp">
								<c:param name="pager" value="${pager}"/>
								<c:param name="url" value="/searchposts?queryString=${queryString}&sex=${sex}" />
								<c:param name="urlRewrite" value="flase"></c:param>
								</c:import>
							</c:if>
						</div>
						<div class="t"></div>
						</div><!--content end-->
					</div><!--main_left end-->
					<div class="main_right"><!--main_right begin-->
						<c:set scope="request" var="hotListType" value="result"></c:set>
						<jsp:include page="/WEB-INF/jsp/web/search/common/search_post_input.jsp" />
					</div><!--main_right end-->
					</div><!--main_part end-->
					</div><!--main end-->
					<jsp:include page="/WEB-INF/jsp/web/common/script/script.jsp" />
					<script type="text/javascript" src="${jzr:static('/js/web/search_posts.js')}"></script>
					<script type="text/javascript" src="${jzr:static('/js/web/home.js')}"></script>
					<jsp:include page="/WEB-INF/jsp/web/common/foot.jsp" />
					</div><!--warp end-->
		</body>

</html>