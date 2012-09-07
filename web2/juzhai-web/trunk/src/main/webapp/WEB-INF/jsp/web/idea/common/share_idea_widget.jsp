<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jzr" uri="http://www.51juzhai.com/jsp/jstl/jzResource" %>
<%@ taglib prefix="jzu" uri="http://www.51juzhai.com/jsp/jstl/jzUtil" %>
<%@ taglib prefix="jzd" uri="http://www.51juzhai.com/jsp/jstl/jzData" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<c:choose>
					<c:when test="${empty isQplus || !isQplus}">
						<div class="content_box w285"><!--content begin-->
							<div class="t"></div>
							<div class="m">
									<div class="right_title"><h2>邀请好友同去</h2></div>
									<div class="share_icons"><!--share_icons begin-->
										<!-- Baidu Button BEGIN -->
									    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'谁想去:${idea.content}<%-- <c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty idea.place}'> 地点:${jzu:truncate(idea.place,40,'...')}</c:if> ','url':'http://www.51juzhai.com/idea/${idea.id}','pic':'${jzr:ideaPic(idea.id,idea.pic, 200)}'}">
									        <a class="bds_tsina"></a>
									        <a class="bds_tqq"></a>
									        <a class="bds_douban"></a>
									    </div>
									    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'谁想去:${idea.content}<%-- <c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty idea.place}'> 地点:${jzu:truncate(idea.place,40,'...')}</c:if>','url':'http://www.51juzhai.com/idea/${idea.id}'}">
									         <a class="bds_renren"></a>
									    </div>
									    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'text':'加入拒宅找伴儿出去玩!','url':'http://www.51juzhai.com/idea/${idea.id}','comment':'谁想去:${idea.content}<%-- <c:if test='${idea.date != null}'> 时间:<fmt:formatDate value='${idea.date}' pattern='yyyy.MM.dd'/></c:if> --%><c:if test='${not empty idea.place}'> 地点:${jzu:truncate(idea.place,40,'...')}</c:if>','pic':'${jzr:ideaPic(idea.id,idea.pic, 200)}'}">
									       	<a class="bds_qzone"></a>
									       	<a class="bds_kaixin001"></a>
									    </div>
										<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=593065" ></script>
										<script type="text/javascript" id="bdshell_js"></script>
										<script type="text/javascript">
										var bds_config = {'snsKey':{'tsina':'3631414437','qzone':'100249114','douban':'00fb7fece2b96fd202f27fc6a82c4f76'}};
											document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
										</script>
										<!-- Baidu Button END -->
								  	</div><!--share_icons end-->
							</div>
							<div class="t"></div>
						</div><!--content end-->
					</c:when>
					<c:otherwise>
						<div class="content_box w285"><!--content begin-->
							<div class="yqpy_btn"><a href="javascript:void(0);" onclick="qPlusShare('我想找伴去:${idea.content}<c:if test='${not empty idea.place}'> 地点:${idea.place}</c:if>','','${jzr:ideaPic(idea.id,idea.pic, 200)}','','拒宅网');return false;" title="分享"></a></div>
						</div><!--content end-->
					</c:otherwise>
						
						</c:choose>