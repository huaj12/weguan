$(document).ready(function(){
	bindMouseHover();
});

function bindMouseHover(){
	$(".mouseHover").bind("mouseover", function(){
		mouseHover(this, true);
	}).bind("mouseout", function(){
		mouseHover(this, false);
	});
}

function mouseHover(li, isOver){
	if(isOver){
		$(li).addClass("hover");
	}else {
		$(li).removeClass("hover");
	}
}

//感兴趣的人操作
function interest(clickObj, uid, successCallback){
	jQuery.ajax({
		url : "/home/interest",
		type : "post",
		cache : false,
		data : {"uid" : uid},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				var content = $("#dialog-success").html().replace("{0}", "好的，ta会看到你");
				showSuccess(clickObj, content);
				successCallback();
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function removeInterest(uid, successCallback){
	jQuery.ajax({
		url : "/home/removeInterest",
		type : "post",
		cache : false,
		data : {"uid" : uid},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				successCallback();
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function removeInterestConfirm(uid, followObj, callback){
	var content = $("#dialog-confirm").html().replace("{0}", "确定收回这次敲门么？");
	showConfirm(followObj, "removeInterest", content, function(){
		removeInterest(uid, function(){
			callback();
		});
	});
}

function removeDatingConfirm(datingId, followObj, callback){
	var content = $("#dialog-confirm").html().replace("{0}", "确定不再约ta么？");
	showConfirm(followObj, "removeDating", content, function() {
		removeDating(datingId, function() {
			callback();
		});
	});
}

//对项目操作
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
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

//对项目操作
function addAct(actId, successCallback){
	jQuery.ajax({
		url : "/act/addAct",
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
				alert(result.errorInfos);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

//约会操作
function date(successCallback){
	$.ajax({
		url : "/act/date",
		type : "post",
		cache : false,
		data : $("#datingForm").serialize(),
		dataType : "json",
		success : function(result) {
			if(result&&result.success){
				var content = $("#dialog-success").html().replace("{0}", "好的，我们会通知ta！");
				closeDialog('openDating');
				showSuccess(null, content);
				successCallback(result.result);
			}else{
				$("div.con > div.error").text(result.errorInfo).show();
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function removeDating(datingId, successCallback){
	jQuery.ajax({
		url : "/act/removeDating",
		type : "post",
		cache : false,
		data : {"datingId" : datingId},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				successCallback();
			} else {
				alert(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}


//弹框
function openDating(uid, datingId){
	jQuery.ajax({
		url : "/act/openDating",
		type : "get",
		cache : false,
		data : {"uid": uid, "datingId": datingId},
		dataType : "html",
		success : function(result) {
			openDialog(null, "openDating", result);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function openDatingResp(datingId){
	jQuery.ajax({
		url : "/act/openDatingResp",
		type : "get",
		cache : false,
		data : {"datingId": datingId},
		dataType : "html",
		success : function(result) {
			openDialog(null, "openDatingResp", result);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

//
function respDating(successCallback){
	$.ajax({
		url : "/act/respDating",
		type : "post",
		cache : false,
		data : $("#datingForm").serialize(),
		dataType : "json",
		success : function(result) {
			if(result&&result.success){
				var content = $("#dialog-success").html().replace("{0}", "好的，我们会通知ta！");
				closeDialog('openDatingResp');
				showSuccess(null, content);
				successCallback(result.result);
			}else{
				if(result.errorCode=='30005'){
					$("div.show_box2 > form > div.cantact > div.input").addClass("wrong");
				}else{
					$("div.show_box2 > div.error").text(result.errorInfo).show();
				}
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function showConfirm(followObj, dialogId, dialogContent, okCallback){
	closeDialog(dialogId);
	$.dialog({
		follow : followObj,
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

function showSuccess(followObj, dialogContent){
	var options={
		icon: 'succeed',
		drag : false,
		resize : false,
		content : dialogContent,
		time: 2
	};
	if(null!=followObj){
		options["follow"]=followObj;
	} else {
		options["top"]="50%";
	}
	$.dialog(options);
}

function openDialog(followObj, dialogId, dialogContent){
	closeDialog(dialogId);
	var options={
		fixed : true,
		drag : false,
		resize : false,
		esc : true,
		lock : true,
		id : dialogId,
		content : dialogContent
	};
	if(null!=followObj){
		options["follow"]=followObj;
	} else {
		options["top"]="50%";
	}
	$.dialog(options);
}

function closeDialog(dialogId){
	var showBox = $.dialog.list[dialogId];
	if(showBox != null){
		showBox.close();
	}
}

function openMessage(targetUid, nickname){
	var content = $("#dialog-message").html().replace("{0}", nickname).replace("[0]", targetUid);
	openDialog(null, "openMessage", content);
}

function sendMessage(obj){
	var targetUid = $(obj).attr("target-uid");
	var content = $(obj).parent().prev().children("textarea").val();
	if(!checkValLength(content, 1, 400)){
		$(obj).next().next().text("私聊内容字数控制在1-200个汉字内").show();
		return;
	}
	$(obj).hide();
	$(obj).next().show();
	jQuery.ajax({
		url : "/home/sendMessage",
		type : "post",
		cache : false,
		data : {"targetUid" : targetUid, "content" : content},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				var content = $("#dialog-success").html().replace("{0}", "恭喜，发送私信成功！");
				closeDialog("openMessage");
				showSuccess(null, content);
			} else {
				$(obj).next().next().text(result.errorInfo).show();
				$(obj).next().hide();
				$(obj).show();
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}