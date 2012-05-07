<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jz" uri="http://www.51juzhai.com/jsp/jstl/jz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台设置</title>
<script type="text/javascript">
	function showChild(divId) {
		var div_obj = document.getElementById(divId);
		if (div_obj.style.display == '') {
			div_obj.style.display = "none";
		} else {
			div_obj.style.display = '';
		}
	}
</script>
<style type="text/css">
.headInfo {
	margin: 5px auto;
	width: 100%;
	background-color: #104E8B;
}

.leftInfo {
	width: 160px;
	height: 400px;
	float: left;
	position: absolute;
	left: 8px;
}

.leftInfo ul {
	padding: 1px;
	margin: 4px;
}

.leftInfo ul li {
	list-style-image: none;
	list-style-type: none;
	margin-bottom: 5px;
	text-align: center;
	padding: 5px;
	color: white;
	background-color: #104E8B;
}

.leftInfo ul div {
	background-color: white;
}

.leftInfo ul div ul li {
	list-style-image: none;
	list-style-type: none;
}

a {
	color: white;
	text-decoration: none;
}

a:hover {
	color: white
}

.rightInfo {
	float: left;
	margin-left: 170px;
	width: 1200px;
	height: 800px;
}

.child {
	background-color: #A4D3EE;;
}
</style>
</head>
<body>
	<div class="headInfo">
		<table width="100%">
			<tr>
				<td style="font-size: 22px; padding-left: 10px; color: white;">拒宅网后台管理</td>
			</tr>
			<tr>
				<td style="text-align: right; padding-right: 10px; color: white;">admin</td>
			</tr>
		</table>
	</div>
	<div class="leftInfo">
		<ul>
			<!-- <li>
				<a href="javascript:showChild('actData');">拒宅项目管理</a>
				<ul style="display: none;" id="actData" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/showCreateAct"
						target="config">添加拒宅项目</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/showActManager"
						target="config">修改拒宅项目</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/cmsShowCategoryList"
						target="config">拒宅分类管理</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/showHotAct?active=true&page=1"
						target="config">系统推荐项目设置</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/showRawActs"
						target="config">审核用户添加项目</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="javascript:showChild('statsData');">统计数据</a>
				<ul style="display: none;" id="statsData" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/recommendWantStats"
						target="config">推荐点击统计</a>
					</li>
				</ul>
			</li> -->
			<li>
				<a href="javascript:showChild('managerLogo');">头像审核</a>
				<ul style="display: none;" id="managerLogo" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/profile/listVerifyingLogo?page=1"
						target="config">待审核头像列表</a>
					</li>
					<!-- <li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/profile/listVerifiedLogo?page=1"
						target="config">已通过头像列表</a>
					</li> -->
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/profile/listUnVerifiedLogo?page=1"
						target="config">未通过头像列表</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/profile/listHighQuality?page=1"
						target="config">优质用户列表</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/profile/showUser"
						target="config">用户查询</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="javascript:showChild('managerJz');">管理拒宅</a>
				<ul style="display: none;" id="managerJz" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/post/unhandle"
						target="config">未处理内容</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/post/handle"
						target="config">合格内容</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/post/shield"
						target="config">屏蔽内容</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/cmsShowCategoryList"
						target="config">拒宅分类管理</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/post/query"
						target="config">拒宅查询</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/searchHot"
						target="config">拒宅热词</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="javascript:showChild('postWindow_');">好主意管理</a>
				<ul style="display: none;" id="postWindow_" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/idea"
						target="config">拒宅好主意</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/defunctidea"
						target="config">被屏蔽的好主意</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/postwindow/list"
						target="config">橱窗内容列表</a>
					</li>
				</ul>
			</li>
				<li>
				<a href="javascript:showChild('adManager_');">优惠信息管理</a>
				<ul style="display: none;" id="adManager_" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/list/ad"
						target="config">已发布优惠信息管理</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/ad/manager"
						target="config">优惠信息管理</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/raw/ad"
						target="config">导入团购信息</a>
					</li>
				</ul>
				
			</li>
			<li>
				<a href="javascript:showChild('stats_');">数据统计</a>
				<ul style="display: none;" id="stats_" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/showStats"
						target="config">数据统计</a>
					</li>
				
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/weibo/version"
						target="config">切换微博版本</a>
					</li>
				</ul>
				
			</li>
			
			<li>
				<a href="javascript:showChild('recommend_');">推荐内容管理</a>
				<ul style="display: none;" id="recommend_" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms//show/recommend/post"
						target="config">首页推荐拒宅内容</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms//show/recommend/idea"
						target="config">欢迎页好主意推荐</a>
					</li>
				</ul>
				
			</li>
			
			<li>
				<a href="javascript:showChild('preference_');">偏好设置管理</a>
				<ul style="display: none;" id="preference_" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/add/preference"
						target="config">添加偏好设置问题</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/list/preference"
						target="config">偏好设置问题列表</a>
					</li>
				</ul>
				
			</li>
		<li>
				<a href="javascript:showChild('report_');">举报管理</a>
				<ul style="display: none;" id="report_" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/report/show"
						target="config">举报管理</a>
					</li>
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/show/lock/user"
						target="config">被锁定用户列表</a>
					</li>
				</ul>
				
			</li>
				<li>
				<a href="javascript:showChild('findDialog_');">调查私信</a>
				<ul style="display: none;" id="findDialog_" class="child">
					<li style="background-color: #A4D3EE;"><a
						style="color: black;" href="/cms/find/dialog"
						target="config">调查私信</a>
					</li>
				</ul>
			</li>
			
			<!-- <li>
				<a href="/cms/searchActs" target="config">近义词设置</a>
			</li>
			<li>
				<a href="/cms/showShield" target="config">屏蔽词设置</a>
			</li>
			<li>
				<a href="/cms/showSearchActAction" target="config">用户找伴记录</a>
			</li>
			<li>
				<a href="/cms/showAddActAction" target="config">用户推荐记录</a>
			</li>
			<li>
				<a href="/cms/showSynonymActs" target="config">指向词管理</a>
			</li>
			<li>
				<a href="javascript:;">热门分类</a>
			</li>
			<li>
				<a href="javascript:;">男女兴趣排行榜</a>
			</li> -->
		</ul>
	</div>
	<div class="rightInfo">
		<iframe frameborder="0" width="100%" name="config" height="3000">
		</iframe>
	</div>
</body>
</html>