$(document).ready(function(){
	$(".mouseHover").bind("mouseover", function(){
		mouseHover(this, true);
	}).bind("mouseout", function(){
		mouseHover(this, false);
	});
});

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
				var content = $("#dialog-success").html().replace("{0}", "加兴趣成功");
				showSuccess(clickObj, content);
				successCallback();
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
				window.location.href = "/login";
			}
		}
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
				window.location.href = "/login";
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
				window.location.href = "/login";
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
//				var showBox = $.dialog.list["openDating"];
				var content = $("#dialog-success").html().replace("{0}", "好的，我们会通知ta！");
				closeDialog('openDating');
//				if(showBox!=null){
//					showBox.content(content);
//					showBox.time(2);
//				}
				showSuccess(null, content);
				successCallback(result.result);
			}else{
				$("div.con > div.error").text(result.errorInfo).show();
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login";
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
				window.location.href = "/login";
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
			rSelects();
			showSelect();
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login";
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
				window.location.href = "/login";
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
				window.location.href = "/login";
			}
		}
	});
}

function showConfirm(followObj, dialogId, dialogContent, okCallback){
	closeDialog(dialogId);
	$.dialog({
		follow : followObj,
//		fixed : true,
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
//		fixed : true,
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