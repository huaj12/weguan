<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微观首页</title>
<%@include file="../Script.jsp" %>

<!-- <link href="${homepageRssLink}" rel="alternate" title="微观分享"
	type="application/rss+xml" /> -->
<link href="/passport/style/user/template1/public.css" rel="stylesheet" type="text/css" />
<link href="/passport/style/user/template1/index.css" rel="stylesheet" type="text/css" />
<link href="/passport/style/homepage.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/passport/script/user/share.js"></script>
<script type="text/javascript" src="/passport/script/homepage.js"></script>
<script type="text/javascript" src="/passport/script/user/audio-player.js"></script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td class="Bg_head">&nbsp;</td>
    </tr>
</table>
<table width="600" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="48">&nbsp;</td>
        <td width="362"><a href="${indexPageLink}"><img
            src="/passport/images/homepage/logo.gif" width="200" height="98" border="0" /></a></td>
        <c:if test="${!hasLogin}">
            <td width="188" align="center"><a href="${registerLink}"
                class="login">注册</a> &nbsp;&nbsp;&nbsp;&nbsp;<a
                href="${loginLink}" class="login">登录</a></td>
        </c:if>
        <c:if test="${hasLogin}">
            <script language="javascript">
			    function logout(){
			        Cookie.remove("loginCookie",{path:'/'});
			        window.top.location.href = "${logoutLink}";
			    }
    		</script>
            <td width="188" align="center"><a href="javascript:logout();"
                class="login">退出</a></td>
        </c:if>
    </tr>
</table>
<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td><img src="/passport/images/homepage/Bg_head.gif" width="600" height="58" /></td>
    </tr>
    <tr>
        <td><img src="/passport/images/homepage/Bg_slogan.gif" width="600"
            height="66" /></td>
    </tr>
    <tr>
        <td height="26" class="Bg_body">&nbsp;</td>
    </tr>

    <c:if test="${hasLogin}">
        <tr>
            <td class="Bg_body">
            <table width="600" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="10%"></td>
                    <td height="26" align="center" class="Bg_body1"><a
                        href="${myLink}" class="mylink">去 我 的 个 人 主 页
                    &gt;&gt;</a></td>
                    <td width="10%"></td>
                </tr>
            </table>
            </td>

        </tr>
        <tr>
            <td height="26" class="Bg_body">&nbsp;</td>
        </tr>
        <tr>
            <td height="26" class="Bg_body">
            <div class="htitle">所有人的微观</div>
            </td>
        </tr>
        <tr>
            <td height="16" class="Bg_body">&nbsp;</td>
        </tr>
        <tr>
            <td class="Bg_body">
            <table width="600" cellpadding="0" cellspacing="0">
                <tr>
                    <td valign="top">
                    <table width="600" cellpadding="0" cellspacing="0">
                        <tbody id="STUFF_CONTENT">
                        	<%@include file="HomePageStuffListPage.jsp" %>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" align="center" class="text"
                                    id="STUFF_PAGER"></td>
                            </tr>
                        </tfoot>
                    </table>
                    <script type="text/javascript">
				        //var stuffPager = new Pager("stuff", "${httpService}/stuff.html", 
				    //${stuffItems}, ${stuffMaxPageItems}, ${stuffMaxIndexPages}, ${stuffPageOffset},{
				      //          delimiter : ' | ',
				        //        indexMask : Pager.MASK_PREV|Pager.MASK_NEXT
				       //});
       				//stuffPager.showPager("STUFF_CONTENT", "STUFF_PAGER");
       				</script></td>
                </tr>
            </table>
            </td>
        </tr>
    </c:if>
    <c:if test="${!hasLogin}">
        <tr>

            <td height="26" align="center" class="Bg_body"><a
                href="${registerLink}" onmouseout="changeImage(this,'on')"
                onmouseover="changeImage(this,'off')"><img
                src="/passport/images/homepage/I_button.gif" name="Image11" width="551"
                height="67" border="0" id="Image11" /></a></td>

        </tr>

        <tr>
            <td height="26" align="center" class="Bg_body">&nbsp;</td>
        </tr>
        <tr>
            <td align="center" class="Bg_body"><img
                src="/passport/images/homepage/Bg_ad.jpg" width="551" height="267"
                border="0" usemap="#Map" /></td>
        </tr>
        <tr>
            <td height="26" align="center" class="Bg_body">&nbsp;</td>
        </tr>

        <tr>
            <td height="26" align="center" class="Bg_body">
            <table width="551" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><img src="/passport/images/homepage/Bg_bottomleft.gif"
                        width="7" height="138" /></td>
                    <td width="537" valign="top" bgcolor="#e1e5e7">
                    <table width="537" border="0" cellspacing="0"
                        cellpadding="0">
                        <tr>
                            <td height="15">&nbsp;</td>
                            <td colspan="2" align="right"></td>
                        </tr>
                        <tr>
                            <td width="175">
                            <table width="146" height="96" border="0"
                                cellpadding="0" cellspacing="0"
                                class="Bottom_image">
                                <tr>
                                    <td><img
                                        src="/passport/images/homepage/i_text.gif"
                                        id="image" width="146" height="96" /></td>
                                </tr>
                            </table>
                            </td>
                            <td width="350" align="left" valign="top">
                            <table width="100%" border="0" cellspacing="0"
                                cellpadding="0">
                                <tr>
                                    <td height="50" class="Bottom_title"
                                        id="title">日&nbsp;记</td>
                                </tr>
                                <tr>
                                    <td class="Bottom_text" id="context">用简单的文字分享你生活中的点滴精彩，让你的思绪飞舞在随性的文字中。</td>
                                </tr>
                            </table>
                            </td>
                            <td width="12">&nbsp;</td>
                        </tr>


                    </table>
                    </td>
                    <td><img src="/passport/images/homepage/Bg_bottomright.gif"
                        width="7" height="138" /></td>
                </tr>

            </table>
            </td>
        </tr>
    </c:if>
    <tr>
        <td align="center" class="Bg_body"><img
            src="/passport/images/homepage/Bg_bottom.gif" width="600" height="58" /></td>
    </tr>

</table>
<map name="Map" id="Map">
    <area shape="rect" coords="31,24,107,231"
        onmouseover="javascript:changeTitle('story')" />
    <area shape="rect" coords="118,19,192,218"
        onmouseover="javascript:changeTitle('pic')" />
    <area shape="rect" coords="204,16,271,204"
        onmouseover="javascript:changeTitle('link')" />
    <area shape="rect" coords="280,15,348,204"
        onmouseover="javascript:changeTitle('refer')" />
    <area shape="rect" coords="358,20,431,218"
        onmouseover="javascript:changeTitle('video')" />
    <area shape="rect" coords="442,24,516,231"
        onmouseover="javascript:changeTitle('music')" />
</map>
<div id="Rss" style="text-align: center; "><a
	href="${homepageRssLink}"><img src="/passport/images/i-rss.gif"></img></a><br />
<a href="http://admin.weguan.com/" target="_blank" style="color: #fff">官方通告</a></div>
<jsp:include page="../GoogleAnalytics.jsp" flush="true"></jsp:include>
</body>
</html>

