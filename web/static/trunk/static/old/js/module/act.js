$(document).ready(function(){
	//召集令
	var zjl = $(".zjl");
	if(zjl.length > 0){
		zjl.bind("click", function(){
			var actId = $(this).attr("actid");
			feed(actId);
		});
	}
	//邀请
	var yqhy = $(".yqhy");
	if(yqhy.length > 0){
		yqhy.bind("click", function(){
			var actId = $(this).attr("actid");
			requestByName(actId);
		});
	}
	//取消
	var cancelBtn = $(".ytj > span > a");
	if(cancelBtn.length > 0){
		cancelBtn.bind("click", function(){
			removeAct(this);
		});
	}
	//添加
	var addBtn = $(".want");
	if(addBtn.length > 0){
		addBtn.bind("click", function(){
			addAct(this);
		});
	}
	//加载好友
	$("#friendUser").bind("click", function(){
		$(this).removeClass("link").addClass("active");
		$("#allUser").removeClass("active").addClass("link");
		pageFriendUser(1);
	});
	$("#allUser").bind("click", function(){
		$(this).removeClass("link").addClass("active");
		$("#friendUser").removeClass("active").addClass("link");
		pageAllUser(1);
	});
	var showAllUser = $(".title").attr("friendUser");
	if(showAllUser == "true"){
		$("#friendUser").click();
	}else{
		$("#allUser").click();
	}
});

function removeAct(obj){
	var actId = $(obj).attr("actid");
	var actName = $(obj).attr("actname");
	if(isNaN(actId)){
		return false;
	}
	$.dialog({
		icon: 'question',
		fixed: true,
		top:'50%',
		id: 'question_box',
		content: '确定不再想去'+actName+'么？',
		ok: function () {
				jQuery.ajax({
					url: "/app/ajax/removeAct",
					type: "post",
					cache : false,
					data: {"actId": actId},
					dataType: "json",
					success: function(result){
						if(result&&result.success){
							$(".ytj").hide();
							$(".want").show();
						}else{
							alert("system error.");
						}
					},
					statusCode: {
					    401: function() {
					    	window.location.href="/app/login";
					    }
					}
				});
				return true;
			},
		cancelVal: '取消',
		cancel: true 
	});
}

function addAct(obj){
	var actId = $(obj).attr("actid");
	if(isNaN(actId)){
		return false;
	}
	//ajax
	jQuery.ajax({
		url: "/app/ajax/addAct",
		type: "post",
		cache : false,
		data: {"actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$(obj).hide();
				$(".ytj").show();
			}else{
				alert("system error.");
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}

function pageFriendUser(page){
	
	showUser("/app/ajax/pageActFriend", page);
}

function pageAllUser(page){
	showUser("/app/ajax/pageActUser", page);
}

function pageUser(isFriend, page){
	if(isFriend){
		pageFriendUser(page);
	}else{
		pageAllUser(page);
	}
}

function showUser(url, page){
	//ajax
	$("#actUserList").hide();
	$(".item_loading").show();
	var actId = $(".title").attr("actid");
	jQuery.ajax({
		url: url,
		type: "get",
		cache : false,
		data: {"actId": actId,"page": page},
		dataType: "html",
		context: $("#actUserList"),
		success: function(responseHTML){
			$(".item_loading").hide();
			$(this).html(responseHTML).show();
			setHeight();
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}

function actUserHover(obj, isHover){
	if(isHover){
		$(obj).removeClass("link").addClass("hover");
	}else {
		$(obj).removeClass("hover").addClass("link");
	}
}

function inviteHer(obj, friendId){
	var actId = $(".title").attr("actid");
	jQuery.ajax({
		url: "/app/ajax/inviteHer",
		type: "post",
		cache : false,
		data: {"actId": actId,"friendId": friendId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$(obj).addClass("unclick").text("已约");
				$(obj).removeAttr("onclick");
			}else{
				alert("system error.");
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}