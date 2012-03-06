$body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');// 这行是 Opera 的补丁, 少了它 Opera 是直接用跳的而且画面闪烁 by willin
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
    
	$("div.idea-btn > a").click(function(){
		var ideaId = $(this).attr("idea-id");
		prepareSendIdea(ideaId);
	});
});

function bindItemMouseHover(item){
	$(item).mouseenter(function(){
		mouseHover(this, true);
	}).mouseleave(function(){
		mouseHover(this, false);
	});
}

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
function postIdea(form, successCallback){
	if(form.find("div.sending").length > 0){
		return;
	}
	form.find("div.btn").attr("class", "sending");
	$.ajax({
		url : "/post/postIdea",
		type : "post",
		cache : false,
		data : form.serialize(),
		dataType : "json",
		success : function(result) {
			if(result&&result.success){
				closeDialog("openIdeaSender");
				var content = $("#dialog-success").html().replace("{0}", "发布成功！");
				showSuccess(null, content);
				successCallback();
			}else{
				form.find(".error").text(result.errorInfo).show();
				form.find("div.sending").attr("class", "btn");
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

function showError(followObj, dialogContent){
	var options={
		icon: 'error',
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
	var login = $(content).attr("login");
	if(login=="false"){
		window.location.href = "/login?turnTo=" + window.location.href;
	}else{
		openDialog(null, "openMessage", content);
	}
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

function registerInitMsg(inputObj, callback){
	var initMsg = $(inputObj).attr("init-msg");
	$(inputObj).bind("focus", function(){
		if($(inputObj).val() == initMsg){
			$(inputObj).val("");
			if(null != callback){
				callback(true);
			}
		}
	}).bind("blur", function(){
		if($(inputObj).val() == ""){
			$(inputObj).val(initMsg);
			if(null != callback){
				callback(false);
			}
		}
	});
	$(inputObj).trigger("blur");
}

function prepareSendIdea(ideaId){
	jQuery.ajax({
		url : "/idea/presendidea",
		type : "get",
		cache : false,
		data : {"ideaId": ideaId},
		dataType : "html",
		success : function(result) {
			openDialog(null, "openIdeaSender", result);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

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

var PostSender =  Class.extend({
	init: function(sendForm){
		this.sendForm = sendForm;
		this.sendPostContent = sendForm.find("textarea[name='content']");
		this.contentInitMsg = this.sendPostContent.attr("init-msg");
		this.sendPostCategory = sendForm.find("#send-post-category");
		this.sendPostDate = sendForm.find("#send-post-date");
		this.sendPostAddress = sendForm.find("#send-post-address");
		this.sendPostPic = sendForm.find("#send-post-pic");
		this.sendPostTb = sendForm.find("div.tb");
		this.initContent();
		this.initCategory();
		this.initDate();
		this.initAddress();
		this.initPic();
		this.initTb();
	},
	initContent : function(){
		var sendPostContent = this.sendPostContent;
		registerInitMsg(sendPostContent, function(isEdit){
			sendPostContent.parent().toggleClass("ts", !isEdit);
		});
		sendPostContent.trigger("blur");
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
		var addressInput = sendPostAddress.find("div.input > span > input");
		registerInitMsg(addressInput);
		addressInput.trigger("blur");
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
			var value = addressInput.val();
			if(value == addressInput.attr("init-msg")){
				value = "";
			}
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
		var initMsg = this.contentInitMsg;
		sendForm.find("div.btn > a").bind("click", function(){
			var content = sendForm.find("div.textarea > textarea").val();
			if(content == initMsg || !checkValLength(content, 4, 160)){
				sendForm.find(".send_box_error").text("发布内容请控制在2~80字以内").show();
				return;
			}
			$(this).parent().hide();
			sendForm.find("div.sending").show();
			submitHandler(sendForm);
		});
	}
});

var LocationWidget = Class.extend({
	init: function(){
		var provinceSelect = $("select#province-select");
		var citySelect = $("select#city-select");
		var townSelect = $("select#town-select");
		
		var initSelect = this.initSelect;
		
		provinceSelect.bind("change", function(){
			citySelect.val("0");
			citySelect.trigger("change");
			citySelect.children("option[value!='0']").remove();
			var selectValue;
			if($.browser.msie && $.browser.version=="6.0") { 
				selectValue = this.value; 
		    }else{
		    	selectValue = $(this).val();
		    }
			if(selectValue > 0){
				$.get('/base/initCity', {
					provinceId : selectValue,
					random : Math.random()
				}, function(result) {
					initSelect(citySelect, result.result);
				});
			}
		});
		
		citySelect.bind("change", function(){
			townSelect.val("-1");
			townSelect.children("option[value!='-1']").remove();
			var selectValue;
			if($.browser.msie && $.browser.version=="6.0") { 
				selectValue = this.value; 
		    }else{
		    	selectValue = $(this).val();
		    }
			if(selectValue > 0){
				$.get('/base/initTown', {
					cityId : selectValue,
					random : Math.random()
				}, function(result) {
					if(!result.success){
						townSelect.hide();
					}else{
						townSelect.show();
						initSelect(townSelect, result.result);
						townSelect.append("<option value=\"" + 0 + "\">其他</option>");
					}
				});
			}else{
				townSelect.hide();
			}
		});
		
		$.get('/base/initProvince', {
			random : Math.random()
		}, function(result) {
			initSelect(provinceSelect, result.result);
		});
	},
	initSelect: function(jselect, listResult){
		if(listResult!=null){
			for(key in listResult){
				jselect.append("<option value=\"" + key + "\">" + listResult[key] + "</option>");
			}
		}
		var selectData = jselect.attr("select-data");
		if(selectData!=null && selectData!=""){
			if($.browser.msie && $.browser.version=="6.0") { 
		        setTimeout(function(){ 
		        	jselect.val(selectData);
					jselect.trigger("change"); 
		        },1); 
		    }else { 
		    	jselect.val(selectData);
				jselect.trigger("change"); 
		    }
			jselect.removeAttr("select-data");
		}
	}
});

var CommentWidget = Class.extend({
	init: function(commentForm, commentList){
		if(null != commentForm && commentForm.length > 0){
			this.postId = commentForm.find("input[name='postId']").val();
		}
		this.commentForm = commentForm;
		this.commentList = commentList;
	},
	loadList: function(){
		if(this.commentList.length <= 0){
			return;
		}
		var postId = this.postId;
		var commentList = this.commentList;
		var commentForm = this.commentForm;
		var bindReplyLink = this.bindReplyLink;
		var bindDelLink = this.bindDelLink;
		$.ajax({
			url : "/post/newcomment",
			type : "get",
			cache : false,
			data : {"postId" : postId},
			dataType : "html",
			success : function(result) {
				commentList.html(result);
				commentList.find("div.repy_list_s2 > ul > li").each(function(){
					bindItemMouseHover(this);
					bindReplyLink(this, commentForm);
					bindDelLink(this);
				});
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login?turnTo=" + window.location.href;
				}
			}
		});
	},
	initForm: function(){
		this.privateInitForm(this.commentForm);
	},
	privateInitForm: function(commentForm){
		commentForm.find("input[name='content']").val("");
		commentForm.find("input[name='parentId']").val(0);
		commentForm.find("div.repy_for").hide();
		commentForm.find(".error").text("").hide();
	},
	bindCloseRepyFor : function(commentForm){
		if(null == this.commentList || this.commentList.length <= 0){
			return;
		}
		var commentForm = this.commentForm;
		commentForm.find("div.repy_for > a").click(function(){
			commentForm.find("input[name='parentId']").val(0);
			commentForm.find("div.repy_for").hide();
		});
	},
	bindReply: function(){
		var commentForm = this.commentForm;
		var commentList = this.commentList;
		if(commentForm.length <= 0){
			return;
		}
		var privateInitForm = this.privateInitForm;
		var bindDelLink = this.bindDelLink;
		commentForm.find("div.repy_btn > a").click(function(){
			var sendBtn = $(this);
			if(sendBtn.hasClass("done")){
				return;
			}
			var content = commentForm.find("input[name='content']").val();
			if(!checkValLength(content, 4, 280)){
				commentForm.find(".error").text("留言内容控制在2~140个汉字内").show();
				return;
			}
			sendBtn.text("发布中").addClass("done");
			$.ajax({
				url : "/post/quickcomment",
				type : "post",
				cache : false,
				data : commentForm.serialize(),
				dataType : "html",
				success : function(result) {
					sendBtn.text("发布留言").removeClass("done");
					result = $.trim(result);
					var isJson = /^{.*}$/.test(result); 
					if(isJson){
						var jsonResult = (new Function("return " + result))();
						if(!jsonResult.success){
							commentForm.find(".error").text(jsonResult.errorInfo).show();
							return;
						}
					} else {
						privateInitForm(commentForm);
						if(null != commentList && commentList.length > 0){
							var item = $(result);
							bindItemMouseHover(item);
							bindDelLink(item);
							commentList.find("div.repy_list_s2").addClass("bd_line").find("ul").prepend(item);
							item.fadeIn("slow");	
						}else{
							var content = $("#dialog-success").html().replace("{0}", "好的，ta会看到你");
							showSuccess(sendBtn[0], content);
						}
					}
				},
				statusCode : {
					401 : function() {
						window.location.href = "/login?turnTo=" + window.location.href;
					}
				}
			});
		});
		this.bindCloseRepyFor();
	},
	bindReplyLink: function(item, commentForm){
		$(item).find("a.reply-link").click(function(){
			var nickname = $(this).attr("nickname");
			var content = $(this).attr("content");
			var postCommentId = $(this).attr("post-comment-id");
			var replyInfo = commentForm.find("div.repy_for");
			replyInfo.find("font.reply-nickname").html(nickname);
			replyInfo.find("font.reply-content").html(content);
			commentForm.find("input[name='parentId']").val(postCommentId);
			replyInfo.show();
			$body.animate({scrollTop: commentForm.offset().top - 200}, 300, function(){
				commentForm.find("input[name='content']").focus();
			});
		});
	},
	bindAllReplyLink: function(){
		this.bindReplyLink(this.commentList.find("div.repy_list_s2 > ul > li"), this.commentForm);
	},
	bindDelLink: function(item){
		$(item).find("a.delete-link").click(function(){
			var delBtn = this;
			var postCommentId = $(delBtn).attr("post-comment-id");
			var content = $("#dialog-confirm").html().replace("{0}", "确定删除留言么？");
			showConfirm(delBtn, "removeComment", content, function(){
				$.ajax({
					url : "/post/delcomment",
					type : "post",
					cache : false,
					data : {"postCommentId" : postCommentId},
					dataType : "json",
					success : function(result) {
						if (result && result.success) {
							$(item).remove();
						}else{
							var content = $("#dialog-success").html().replace("{0}", result.errorInfo);
							showError(delBtn, content);
						}
					},
					statusCode : {
						401 : function() {
							window.location.href = "/login?turnTo=" + window.location.href;
						}
					}
				});
			});
		});
	},
	bindAllDelLink: function(){
		var bindDelLink = this.bindDelLink;
		this.commentList.find("div.repy_list_s2 > ul > li").each(function(){
			bindDelLink(this);
		});
	}
});