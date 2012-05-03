$(document).ready(function() {
	
	$("#genderSelect > span > select").bind("change", function(){
		var requestUri = $(this).parent().parent().attr("requesturi");
		var genderType = $(this).children('option:selected').val();
		window.location.href="/" + requestUri + "/" + genderType + "/1";
	});
	
	//添加约
	$("div.btn > a.yueta").bind("click", function(){
		var uid = $(this).attr("uid");
		openDating(uid, 0);
		return false;
	});
	
	$("div.btn > div.ygxq > a.delete").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		removeInterestConfirm(uid, this, function(){
			$(obj).parent().hide();
			$("#interest" + uid).show();
		});
		return false;
	});
	$("div.btn > a.like").bind("click", function() {
		var uid = $(this).attr("uid");
		var obj = $(this);
		interest(this, uid, function(){
			$(obj).hide();
			$("#removeInterest" + uid).show();
		});
		return false;
	});
	
	$("div.item_zbr > div.btn > a.mail").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
	
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
		var i = 0;
		activeLi.each(function(){
			if(i > 0){
				uNames += ",";
			}
			uNames += $(this).attr("name");
			i++;
		});
		invite(uNames);
		$("em#inviteError").hide();
		return false;
	});
	
	$("div#freeDateForm").show().animate({bottom:"+=100"}, 1000);
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

function submitDating(uid){
	date(function(){
		$("#dating"+uid).hide();
		$("#removeDating"+uid).show();
	});
}

function clickInviteUser(obj){
	if($(obj).hasClass("active")){
		$(obj).removeClass("active");
	}else{
		$(obj).addClass("active");
	}
}