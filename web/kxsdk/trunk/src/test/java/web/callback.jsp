<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="kx4j.http.*" %>
<%@ page language="java" import="kx4j.*" %>

<jsp:useBean id="weboauth" scope="session" class="examples.WebOAuth" />
<%
	
	String verifier=request.getParameter("oauth_verifier");
	
        String method = request.getParameter("method");
        
	if(verifier!=null)
	{
	System.out.println("oauth:"+verifier);
		RequestToken resToken=(RequestToken) session.getAttribute("resToken");

		if(resToken!=null)
		{
			AccessToken accessToken=weboauth.requstAccessToken(resToken,verifier);
				if(accessToken!=null)
				{
					//out.println("Token: " + accessToken.getToken());
					//out.println("TokenSecret: " + accessToken.getTokenSecret());
					//第二个参数每次只能用一次，发表微博内容不能重复，如果重复发会返回400错误
					//这个accessToken不用每次访问都重新取，可以存到session里面用
					//weboauth.update(accessToken,"web方式发表微博4");
                                    
                                        if("me".equals(method)) {
                                             String fields = "";//"uid,name,gender,logo50";
                                             User user = weboauth.getMyInfo(accessToken, fields);
                                             
                                             out.println(user.toString());
                                        } else if("show".equals(method)) {
                                             String uids = "13895,13868";
                                             String fields = "";//"uid,name,gender,logo50";
                                             List<User> userList = weboauth.getUsers(accessToken, uids, fields, 0, 20);
                                             
                                             for(User user : userList) {
                                                out.println(user.toString());
                                            }
                                        } else if("friends".equals(method)) {
                                             String fields = "";//"uid,name,gender,logo50";
                                             List<User> userList = weboauth.getFriends(accessToken, fields, 0, 20);
                                             
                                            for(User user : userList) {
                                                out.println(user.toString());
                                            }
                                        } else if("relation".equals(method)) {
                                             long uid1 = 13926;
                                             long uid2 = 13895;
                                             int relationship = weboauth.getRelationShip(accessToken, uid1, uid2);
                                             
                                            out.println(relationship);
                                        } else if("status".equals(method)) {
                                             String uids = "13895,13868";
                                             List<AppStatus> appStatuses = weboauth.getAppStatus(accessToken, uids, 0, 20);
                                             
                                            for(AppStatus appStatus : appStatuses) {
                                                out.println(appStatus.toString());
                                            }
                                        } else if("friendstatus".equals(method)) {
                                             UIDs idsList = weboauth.getAppFriendUids(accessToken, 0, 20);
                                             
                                             out.println(idsList.toString());
                                        } else if("invited".equals(method)) {
                                             long uid = 13926;
                                             InvitedUIDs invitedUids = weboauth.getAppInvitedUids(accessToken, uid, 0, 20);
                                             
                                            if(invitedUids == null) {
                                                out.println("no invited anyone!");
                                            } else {
                                                    out.println(invitedUids.toString());
                                            }
                                        } else {
                                            out.println("授权成功");	
                                            out.println("<br/>");
                                        }
                                        
					
                                        
				}else
					{
					out.println("access token request error");
					}
		
		}
		else
			{
			out.println("request token session error");
			}
	}
	else
		{
		out.println("verifier String error");
		}

%>
<div>
    <h2>接口列表：</h2>
<ul>
    <li><a href="callback.jsp?oauth_verifier=<%=verifier%>&method=me">/users/me　获取当前登录用户的资料</a></li>
    <li><a href="callback.jsp?oauth_verifier=<%=verifier%>&method=show">/users/show　根据UID获取多个用户的资料</a></li>
    <li><a href="callback.jsp?oauth_verifier=<%=verifier%>&method=friends">/friends/me　获取当前登录用户的好友资料 </a></li>
    <li><a href="callback.jsp?oauth_verifier=<%=verifier%>&method=relation">/friends/relationship　获取两个用户间的好友关系 </a></li>
    <li><a href="callback.jsp?oauth_verifier=<%=verifier%>&method=status">/app/status　 获取用户安装组件的状态</a></li>
    <li><a href="callback.jsp?oauth_verifier=<%=verifier%>&method=friendstatus">/app/friends　获取当前用户安装组件的好友uid列表 </a></li>
    <li><a href="callback.jsp?oauth_verifier=<%=verifier%>&method=invited">/app/invited　获取某用户邀请成功的好友uid列表</a></li>
</ul>
</div>
<div><a href="call.jsp">重新执行授权过程</a></div>