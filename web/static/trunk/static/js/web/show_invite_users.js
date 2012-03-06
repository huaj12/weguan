$(document).ready(function() {
	$("a#showMoreInvite").bind("click", function(){
		showMoreInviteUser();
		return false;
	});
	
	$("a#inviteBtn").bind("click", function(){
		var activeLi = $("div.friend_list > ul >li.active");
		if(activeLi.length > 10){
			$("em#inviteError").text("每次邀请不要超过10个人哦").show();
			return false;
		}
		if(activeLi.length <= 0){
			$("em#inviteError").text("请选择邀请的人").show();
			return false;
		}
		var uNames = "";
		var uids = "";
		var i = 0;
		activeLi.each(function(){
			var name = $(this).attr("name");
			if(null != name){
				if(i > 0){
					uNames += ",";
				}
				uNames += name;
			}

			var uid = $(this).attr("uid");
			if(null != uid){
				if(i > 0){
					uids += ",";
				}
				uids += uid;
			}
			i++;
		});
		invite(uNames, uids);
		$("em#inviteError").hide();
		return false;
	});
});

function showMoreInviteUser(){
	$("a#showMoreInvite").hide();
	$("p#showMoreInviteLoading").show();
	var totalPage = parseInt($("div.friend_list").attr("totalpage"));
	var page = parseInt($("div.friend_list").attr("currentpage")) + 1;
	if(page <= 0||page > totalPage){
		$("p#showMoreInviteLoading").hide();
		return;
	}
	jQuery.ajax({
		url : "/pageInviteUser",
		type : "get",
		cache : false,
		data : {
			"page" : page
		},
		dataType : "html",
		success : function(result) {
			$("div.friend_list > ul").append(result);
			$("div.friend_list").attr("currentpage", page);
			$("p#showMoreInviteLoading").hide();
			if(page < totalPage){
				$("a#showMoreInvite").show();
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function clickInviteUser(obj){
	if($(obj).hasClass("active")){
		$(obj).removeClass("active");
		$(obj).attr("title", "点击添加");
	}else{
		$(obj).addClass("active");
		$(obj).attr("title", "点击取消");
	}
}