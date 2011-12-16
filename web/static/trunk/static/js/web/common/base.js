$(document).ready(function(){
	$(".mouseHover").bind("mouseover", function(){
		mouseHover(this, true);
	}).bind("mouseout", function(){
		mouseHover(this, false);
	});
	
	$("a.like").bind("click", function() {
		interest(this);
	});
	$("a.cancel_like").bind("click", function(){
		removeInterest(this);
	});
});

function mouseHover(li, isOver){
	if(isOver){
		$(li).addClass("hover");
	}else {
		$(li).removeClass("hover");
	}
}

function interest(clickObj){
	var uid = $(clickObj).attr("uid");
	jQuery.ajax({
		url : "/home/interest",
		type : "post",
		cache : false,
		data : {"uid" : uid},
		dataType : "json",
		context : $(clickObj),
		success : function(result) {
			if (result && result.success) {
				$(this).removeClass("like").addClass("cancel_like").text("我感兴趣");
				$(this).unbind("click").bind("click",function(){
					removeInterest(this);
				});
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login";
			}
		}
	});
}

function removeInterest(clickObj){
	var uid = $(clickObj).attr("uid");
	var content = $("#dialog-remove-interest").html();
	showConfirm(clickObj, "removeInterest", content, function(){
		jQuery.ajax({
			url : "/home/removeInterest",
			type : "post",
			cache : false,
			data : {"uid" : uid},
			dataType : "json",
			context : $(clickObj),
			success : function(result) {
				if (result && result.success) {
					$(this).removeClass("cancel_like").addClass("like").text("感兴趣");
					$(this).unbind("click").bind("click",function(){
						interest($(this));
					});
				} else {
					alert(result.errorInfo);
				}
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login";
				}
			}
		});
	});
}

function removeAct(actId, successCallback){
	jQuery.ajax({
		url : "/act/removeAct",
		type : "post",
		cache : false,
		data : {
			"actId" : actId
		},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				successCallback();
			} else {
				alert("system error.");
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login";
			}
		}
	});
}

function showConfirm(followObj, dialogId, dialogContent, okCallback){
	var showBox = $.dialog.list[dialogId];
	if(showBox != null){
		showBox.close();
	}
	$.dialog({
		follow : followObj,
		fixed : true,
		drag : false,
		resize : false,
		esc : true,
		id : dialogId,
		content : dialogContent,
		cancelVal : '取消',
		cancel : true,
		ok : function() {
			okCallback();
			return true;
		}
	});
}

function openDialog(followObj, dialogId, dialogContent){
	var showBox = $.dialog.list[dialogId];
	if(showBox != null){
		showBox.close();
	}
	$.dialog({
		follow : followObj,
		fixed : true,
		drag : false,
		resize : false,
		esc : true,
		lock: true,
		id : dialogId,
		content : dialogContent
	});
}

function openDating(buttonObj){
	var uid = $(buttonObj).attr("uid");
	jQuery.ajax({
		url : "/act/openDating",
		type : "get",
		cache : false,
		data : {
			"uid" : uid
		},
		dataType : "html",
		success : function(result) {
			openDialog(buttonObj, "openDating", result);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login";
			}
		}
	});
}