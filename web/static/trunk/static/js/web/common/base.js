var SelectInput =  Class.extend({
	init: function(div){
		this.selectDiv = div;
    	var name = $(this.selectDiv).find("a.selected").text();
    	var value = $(this.selectDiv).find("a.selected").attr("value");
    	$(this.selectDiv).find("p > a").text(name);
    	var inputName = $(this.selectDiv).attr("name");
    	if(null != inputName){
    		$(this.selectDiv).prepend('<input type=\"hidden\" name=\"' + inputName + '\" value=\"' + value + '\" />');
    	}
	},
	bindBlur:function(){
		var selectDiv = this.selectDiv;
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(selectDiv).length <= 0){
				$(selectDiv).removeClass("select_active");
			}
		});
//		$(selectDiv).hover(function(){
//    		$("body").unbind("mousedown");
//    	}, function(){
//    		$("body").bind("mousedown",function(){
//    			$(selectDiv).removeClass("select_active");
//    		});
//    	});
	},
	bindClick:function(){
		var selectDiv = this.selectDiv;
		$(selectDiv).find("p > a").bind("click", function(){
			if($(selectDiv).hasClass("select_active")){
				$(selectDiv).removeClass("select_active");
	    	}else{
	    		$(selectDiv).addClass("select_active");
			}
		});
	},
	bindSelect:function(){
		var selectDiv = this.selectDiv;
		$(selectDiv).find("div.select_box > span > a").bind("click", function(){
        	var name = $(this).text();
        	var value = $(this).attr("value");
        	$(this).parent().children("a").removeClass("selected");
        	$(this).addClass("selected");
        	$(selectDiv).find("p > a").text(name);
        	$(selectDiv).find("input[type='hidden']").val(value);
        	$(selectDiv).removeClass("select_active");
        });
	}
});

$(document).ready(function(){
	if($("div.back_top").length > 0){
		$.waypoints.settings.scrollThrottle = 30;
		$("div.warp").waypoint(function(event, direction) {
			if(direction === "up"){
				$("div.back_top").fadeOut(200);
			}else{
				$("div.back_top").fadeIn(200);
			}
		}, {
			offset: '-100%'
		});
		$body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');// 这行是 Opera 的补丁, 少了它 Opera 是直接用跳的而且画面闪烁 by willin
		$("div.back_top > span > a").bind("click", function(){
			$body.animate({scrollTop: "0px"}, 500, function(){});
			return false;
		});
	}
    $("img").lazyload({
        effect : "fadeIn"
    });
    
    bindMouseHover();
    
    //select
    $("div.select_menu").each(function(){
    	var select = new SelectInput(this);
    	select.bindBlur();
    	select.bindClick();
    	select.bindSelect();
    });
    
    //右侧idea
	$("div.idea").find("div.send > a").click(function(){
		var ideaId = $(this).attr("idea-id");
		var send = $(this).parent().hide();
		var sending = send.prev().show();
		postIdea(ideaId, send, sending, function(){
			sending.attr("class", "sended").children("a").text("已发布");
			if($("#useCount-" + ideaId).is(":visible")){
				$("#useCount-" + ideaId).text(parseInt($("#useCount-" + ideaId).text()) + 1);
			}else{
				$("#useCountShow-" + ideaId).text("1人想去");
			}
		});
	});
});

function bindMouseHover(){
	$(".mouseHover").mouseenter(function(){
		mouseHover(this, true);
	}).mouseleave(function(){
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

//发布好主意
function postIdea(ideaId, sendBtn, sendingBtn, successCallback){
	$.ajax({
		url : "/post/postIdea",
		type : "post",
		cache : false,
		data : {"ideaId" : ideaId},
		dataType : "json",
		success : function(result) {
			if(result&&result.success){
				successCallback();
				sendBtn.unbind("click");
				var content = $("#dialog-success").html().replace("{0}", "发布成功！");
				showSuccess(sendingBtn[0], content);
			}else{
				alert(result.errorInfo);
				sendingBtn.hide();
				sendBtn.show();
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
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
	var content = $("#dialog-confirm").html().replace("{0}", "确定取消收藏么？");
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
function addAct(clickObj, actId, successCallback){
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
				var content = $("#dialog-success").html().replace("{0}", "好的，同兴趣的人会看到你");
				showSuccess(clickObj, content);
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
				var content = $("#dialog-success").html().replace("{0}", "好的，我们会通知ta");
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
		time: 1
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
		fixed : false,
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

function doPostMessage(url, targetUid, content, errorCallback, completeCallback){
	if(!checkValLength(content, 1, 400)){
		errorCallback("私聊内容字数控制在1-200个汉字内");
		return;
	}
	jQuery.ajax({
		url : url,
		type : "post",
		cache : false,
		data : {"targetUid" : targetUid, "content" : content},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				completeCallback(result.result);
			} else {
				errorCallback(result.errorInfo);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function sendMessage(obj){
	$(obj).hide();
	$(obj).next().show();
	var targetUid = $(obj).attr("target-uid");
	var content = $(obj).parent().prev().children("textarea").val();
	doPostMessage("/home/sendMessage", targetUid, content, function(errorInfo){
		$(obj).next().next().text(errorInfo).show();
		$(obj).next().hide();
		$(obj).show();
	}, function(result){
		var successContent = $("#dialog-success").html().replace("{0}", "发送成功！");
		closeDialog("openMessage");
		showSuccess(null, successContent);
	});
}

//感兴趣的人操作
function responsePost(clickObj, postId, successCallback){
	jQuery.ajax({
		url : "/post/response",
		type : "post",
		cache : false,
		data : {"postId" : postId},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				var content = $("#dialog-success").html().replace("{0}", "好的，我们会通知ta！");
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

var PostSender =  Class.extend({
	init: function(sendForm){
		this.sendForm = sendForm;
		this.sendPostCategory = sendForm.find("#send-post-category");
		this.sendPostDate = sendForm.find("#send-post-date");
		this.sendPostAddress = sendForm.find("#send-post-address");
		this.sendPostPic = sendForm.find("#send-post-pic");
		this.sendPostTb = sendForm.find("div.tb");
		this.initCategory();
		this.initDate();
		this.initAddress();
		this.initPic();
		this.initTb();
	},
	initCategory : function(){
		//category
		var sendPostCategory = this.sendPostCategory;
		sendPostCategory.find("p > a").bind("click", function(){
			sendPostCategory.addClass("active");
		});
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(sendPostCategory).length <= 0){
				sendPostCategory.removeClass("active");
			}
		});
		sendPostCategory.find("div.tag_list > a").click(function(){
			var value = $(this).attr("value");
			var name = $(this).text();
			sendPostCategory.find('input[name="categoryId"]').val(value);
			sendPostCategory.find("p > a").text(name);
			sendPostCategory.find("div.tag_list > a").removeClass("act");
			$(this).addClass("act");
			sendPostCategory.toggleClass("done", value>0);
			sendPostCategory.removeClass("active");
		});
	},
	initDate : function(){
		//date
		var sendPostDate = this.sendPostDate;
		sendPostDate.find("p > a.time").click(function(){
			var timeClick = $(this);
			WdatePicker({
				errDealMode : 3,
				dateFmt:"MM-dd",
				onpicked:function(){
					var value = $dp.cal.getP('y') + "-" + $dp.cal.getP('M') + "-" + $dp.cal.getP('d');
					sendPostDate.find("input[name='dateString']").val(value);
					sendPostDate.addClass("done");
				},
				oncleared:function(){
					sendPostDate.find("input[name='dateString']").val("");
					timeClick.text("时间");
					sendPostDate.removeClass("done");
				}
			});
		});
	},
	initAddress : function(){
		//place
		var sendPostAddress = this.sendPostAddress;
		sendPostAddress.find("p > a").bind("click", function(){
			sendPostAddress.addClass("active");
		});
		sendPostAddress.find("div.show_area > div.area_title > a").click(function(){
			sendPostAddress.removeClass("active");
		});
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(sendPostAddress).length <= 0){
				sendPostAddress.removeClass("active");
			}
		});
		sendPostAddress.find("div.show_area > div.ok_btn > a").bind("click", function(){
			var value = sendPostAddress.find("div.input > span > input").val();
			//check place
			if(!checkValLength(value, 0, 40)){
				sendPostAddress.find(".error").text("地点字数控制在20字以内").show();
				return;
			}
			sendPostAddress.find('input[name="place"]').val(value);
			sendPostAddress.removeClass("active");
			sendPostAddress.find("p > a").attr("title", value);
			if(value != null && value != ""){
				sendPostAddress.addClass("done");
			}else{
				sendPostAddress.removeClass("done");
			}
			sendPostAddress.find(".error").hide();
		});
	},
	initPic : function(){
		//pic
		var sendPostPic = this.sendPostPic;
		var sendForm = this.sendForm;
		sendPostPic.find("p > a.photo").click(function(){
			sendPostPic.addClass("active");
		});
		
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(sendPostPic).length <= 0){
				sendPostPic.removeClass("active");
			}
		});
		sendPostPic.find("div.upload_photo_area > div.close1 > a").click(function(){
			sendPostPic.removeClass("active");
		});
		sendPostPic.find("input.btn_file_molding").change(function(){
			sendPostPic.find("div.show_area > div.upload_photo_area > div.upload").hide();
			sendPostPic.find("div.load_error").hide();
			//$("div.upload > div.loading").show();
			var options = {
				url : "/post/pic/upload",
				type : "POST",
				dataType : "json",
				iframe : "true",
				success : function(result) {
					if (result.success) {
						sendPostPic.find("input[name='pic']").val(result.result[1]);
						sendPostPic.find("div.upload_ok > div.img > img").attr("src", result.result[0]);
						sendPostPic.find("div.upload_ok").show();
						sendPostPic.addClass("done");
						//$("div.upload > div.loading").hide();
					} else if (result.errorCode == "00003") {
						window.location.href = "/login?turnTo=" + window.location.href;
					} else {
						//$("div.upload > div.loading").hide();
						sendPostPic.find("div.load_error").text(result.errorInfo).show();
						sendPostPic.find("div.show_area > div.upload_photo_area > div.upload").show();
					}
				},
				error : function(data) {
					//$("div.upload > div.loading").hide();
					sendPostPic.find("div.load_error").text("上传失败").show();
					sendPostPic.find("div.show_area > div.upload_photo_area > div.upload-input").show();
				}
			};
			sendForm.ajaxSubmit(options);
			return false;
		});
		sendPostPic.find("div.upload_ok > em > a").click(function(){
			sendPostPic.removeClass("done");
			sendPostPic.find("input[name='pic']").val("");
			sendPostPic.find("input[name='picIdeaId']").val(0);
			sendPostPic.find("div.upload_ok > div.img > img").attr("src", sendPostPic.find("div.upload_ok > div.img > img").attr("init-pic"));
			sendPostPic.find("div.upload_ok").hide();
			sendPostPic.find("div.load_error").hide();
			sendPostPic.find("div.show_area > div.upload_photo_area > div.upload").show();
		});
	},
	initTb : function(){
		this.sendPostTb.bind("click", function(){
			if($(this).hasClass("tb_click")){
				$(this).removeClass("tb_click");
				$(this).find('input[name="sendWeibo"]').val(false);
			}else{
				$(this).addClass("tb_click");
				$(this).find('input[name="sendWeibo"]').val(true);
			}
		});
	},
	bindSubmit : function(submitHandler){
		//submit
		var sendForm = this.sendForm;
		sendForm.find("div.btn > a").bind("click", function(){
			var content = sendForm.find("div.textarea > textarea").val();
			if(!checkValLength(content, 4, 160)){
				sendForm.find(".send_box_error").text("发布内容请控制在2~80字以内").show();
				return;
			}
			$(this).parent().hide();
			sendForm.find("div.sending").show();
			submitHandler(sendForm);
		});
	}
});

function prepareModifyPost(postId){
	jQuery.ajax({
		url : "/post/prepareModifyPost",
		type : "get",
		cache : false,
		data : {"postId": postId},
		dataType : "html",
		success : function(result) {
			openDialog(null, "openPostSender", result);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function prepareRepost(postId){
	jQuery.ajax({
		url : "/post/prepareRepost",
		type : "get",
		cache : false,
		data : {"postId": postId},
		dataType : "html",
		success : function(result) {
			openDialog(null, "openPostSender", result);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function deletePost(postId, successCallback){
	jQuery.ajax({
		url : "/post/deletePost",
		type : "post",
		cache : false,
		data : {"postId" : postId},
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