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
    $("div.select_menu:not([load-lazy])").each(function(){
    	var select = new SelectInput(this);
    	select.bindBlur();
    	select.bindClick();
    	select.bindSelect();
    });
    
    $("div.xz_menu:not([load-lazy])").each(function(){
    	var select = new SelectBoxInput(this);
    	select.bindBlur();
    	select.bindClick();
    	select.bindSelect();
    });
    
	$("div.idea-btn > a").click(function(){
		var ideaId = $(this).attr("idea-id");
		prepareSendIdea(ideaId);
		return false;
	});
	
	if($("div#idea-widget").length > 0){
		nextIdeaWidget($("div#idea-widget"), 1);
	}
	
	$("div.post-response").click(function(){
		var postId = $(this).attr("post-id");
		var respCount = $(this).attr("resp-count");
		var nickname = $(this).attr("nickname");
		var postContent = $(this).attr("post-content");
		var obj = this;
		openResponse(obj, postId, nickname, postContent, function(){
			var currentCnt = parseInt(respCount);
			$(obj).find("div.xy_num > a").text(currentCnt + 1);
			$(obj).find("a.xy").text("已" + $(obj).find("a.xy").text());
			$(obj).unbind("click").addClass("done");
		});
		return false;
	});
	
	$("div.bq").each(function(){
		new FaceWidget($(this));
	});
	
	$("div.big_input").each(function(){
		var inputDiv = this;
		$(inputDiv).find("input").focus(function(){
			$(inputDiv).addClass("hover");
		}).blur(function(){
			$(inputDiv).removeClass("hover");
		});
	});
});

function nextIdeaWidget(containDiv, page){
	$(containDiv).find("a.hyge").attr("class", "h_loading").unbind("click");
	$.ajax({
		url : "/idea/showwidget",
		type : "get",
		cache : false,
		data : {page: page},
		dataType : "html",
		success : function(result) {
			$(containDiv).fadeOut(400, function(){
				$(this).html(result).fadeIn(400, function(){
					//bind事件
					$(containDiv).find("a.hyge").click(function(){
						nextIdeaWidget(containDiv, page + 1);
						return false;
					});
					$(containDiv).find("a.wtgo").click(function(){
						var ideaId = $(this).attr("idea-id");
						prepareSendIdea(ideaId, function(){
							nextIdeaWidget(containDiv, page);
						});
						return false;
					});
				});
			});
		}
	});
}

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
	var content = $("#dialog-confirm").html().replace("{0}", "确定取消关注么？");
	showConfirm(followObj, "removeInterest", content, function(){
		removeInterest(uid, function(){
			callback();
		});
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
		id : dialogId,
		content : dialogContent
	};
	if(null!=followObj){
		options["follow"]=followObj;
		options["lock"]=false;
	} else {
		options["top"]="50%";
		options["lock"]=true;
	}
	return $.dialog(options);
}

function closeDialog(dialogId){
	var showBox = $.dialog.list[dialogId];
	if(showBox != null){
		showBox.close();
	}
}

function openMessage(targetUid){
//	var content = $("#dialog-message").html().replace("{0}", nickname).replace("[0]", targetUid);
//	var login = $(content).attr("login");
//	if(login=="false"){
//		window.location.href = "/login?turnTo=" + window.location.href;
//	}else{
//		openDialog(null, "openMessage", content);
//	}
	jQuery.ajax({
		url : "/home/presendmsg",
		type : "get",
		cache : false,
		data : {"targetUid": targetUid},
		dataType : "html",
		success : function(result) {
			var dialog = openDialog(null, "openMessage", result);
			//绑定事件
			$(dialog.content()).find("div.btn > a.send").click(function(){
				sendMessage(this);
				return false;
			});
			new FaceWidget($(dialog.content()).find("div.bq"));
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
	
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

function openDate(targetUid, nickname){
	var content = $("#dialog-date").html().replace("{0}", nickname).replace("[0]", targetUid);
	var login = $(content).attr("login");
	if(login=="false"){
		window.location.href = "/login?turnTo=" + window.location.href;
	}else{
		var dialog = openDialog(null, "openDate", content);
		//绑定事件
		var textarea = $(dialog.content()).find("textarea");
		registerInitMsg(textarea, function(isEdit){
			textarea.parent().toggleClass("ts", !isEdit);
		});
		textarea.trigger("blur");
		$(dialog.content()).find("div.btn > a").click(function(){
			sendDate(targetUid, $(this).parent());
			return false;
		});
		$(dialog.content()).find("div.random_select > a.random").click(function(){
			$.ajax({
				url : "/idea/random",
				type : "post",
				cache : false,
				data : {},
				dataType : "json",
				success : function(result) {
					if(result && result.success){
						var content = result.result.content;
						textarea.val(content);
					}
				},
				statusCode : {
					401 : function() {
						window.location.href = "/login?turnTo=" + window.location.href;
					}
				}
			});
			return false;
		});
	}
}

function sendDate(uid, btn, ideaId, followBtn, successCallback){
	var content = null;
	var initMsg = null;
	if($(btn).length > 0){
		$(btn).hide();
		$(btn).next().show();
		content = $(btn).parent().find("textarea").val();
		initMsg = $(btn).parent().find("textarea").attr("init-tip");
	}
	if($(btn).length > 0 && (content == initMsg || !checkValLength(content, 1, 160))){
		$(btn).parent().find("div.date_error").text("约会内容控制在1-160个汉字以内").show();
		$(btn).next().hide();
		$(btn).show();
		return false;
	}
	jQuery.ajax({
		url : "/home/senddate",
		type : "post",
		cache : false,
		data : {"targetUid" : uid, "content" : content, "ideaId" : ideaId},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				var successContent = $("#dialog-success").html().replace("{0}", "发送成功！");
				closeDialog("openDate");
				showSuccess(followBtn, successContent);
				if(null != successCallback){
					successCallback();
				}
			} else {
				$(btn).parent().find("div.date_error").text(result.errorInfo).show();
				$(btn).next().hide();
				$(btn).show();
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
	return false;
}

function openResponse(obj, postId, nickname, postContent, clickCallback){
	var content = $("#dialog-response").html().replace("{0}", nickname).replace("{1}", postContent);
	var login = $(content).attr("login");
	if(login=="false"){
		window.location.href = "/login?turnTo=" + window.location.href;
	}else{
		var dialog = openDialog(null, "openResponse", content);
		//绑定事件
		var textarea = $(dialog.content()).find("textarea");
		registerInitMsg(textarea, function(isEdit){
			textarea.parent().toggleClass("ts", !isEdit);
		});
		textarea.trigger("blur");
		$(dialog.content()).find("a.resp-btn").click(function(){
			//换loading按钮
			var sendBtn = this;
			$(sendBtn).parent().hide();
			$(sendBtn).parent().next().show();
			var content = "";
			if(trimStr(textarea.val()) != "" && textarea.val() != textarea.attr("init-tip")){
				content = "附言：" + textarea.val();
			}
			responsePost(postId, content, function(){
				closeDialog("openResponse");
				clickCallback();
			}, function(errorInfo){
				//回复发布按钮
				$(sendBtn).parent().next().hide();
				$(sendBtn).parent().show();
				//显示错误
				$(dialog.content()).find("div.error").text(errorInfo).show();
			});
			return false;
		});
	}
}

//响应
function responsePost(postId, content, successCallback, errorCallback){
	if(!checkValLength(content, 0, 160)){
		errorCallback("附言内容请控制在0~80字以内");
	}
	jQuery.ajax({
		url : "/post/response",
		type : "post",
		cache : false,
		data : {"postId" : postId, "content" : content},
		dataType : "json",
		success : function(result) {
			if (result && result.success) {
				successCallback();
				var content = $("#dialog-success").html().replace("{0}", "好的，我们会通知ta！");
				showSuccess(null, content);
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

function registerInitMsg(inputObj, callback){
	var initMsg = $(inputObj).attr("init-tip");
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

function prepareSendIdea(ideaId, callback){
	jQuery.ajax({
		url : "/idea/presendidea",
		type : "get",
		cache : false,
		data : {"ideaId": ideaId},
		dataType : "html",
		success : function(result) {
			openDialog(null, "openIdeaSender", result);
			$("form#send-idea").find("div.select_menu").each(function(){
		    	var select = new SelectInput(this);
		    	select.bindBlur();
		    	select.bindClick();
		    	select.bindSelect();
		    });
		    $("form#send-idea").find("div.tb").bind("click", function(){
				if($(this).hasClass("tb_click")){
					$(this).removeClass("tb_click");
					$(this).find('input[name="sendWeibo"]').val(false);
				}else{
					$(this).addClass("tb_click");
					$(this).find('input[name="sendWeibo"]').val(true);
				}
				return false;
			});
		    $("form#send-idea").find("div.btn > a").click(function(){
		    	var ideaId = $(this).attr("idea-id");
		    	postIdea($("form#send-idea"), function(){
		    		$(".idea-btn-" + ideaId).attr("class", "done").children("a").text("已想去").unbind("click");
					if(callback != null){
						callback();
					}
		    	});
		    	return false;
		    });
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

var CitySelectInput =  Class.extend({
	init: function(div, selectCallback){
		this.selectDiv = div;
		this.selectCallback = selectCallback;
    	var value = $(this.selectDiv).find("p > a").attr("city-id");
    	var inputName = $(this.selectDiv).attr("name");
    	if(null != inputName){
    		$(this.selectDiv).prepend('<input type=\"hidden\" name=\"' + inputName + '\" value=\"' + value + '\" />');
    	}
    	this.bindSelect(this.selectDiv, $(this.selectDiv).find("div.vip_city"), selectCallback);
    	this.bindSelect(this.selectDiv, $(this.selectDiv).find("div.shi"), selectCallback);
	},
	bindBlur:function(){
		var selectDiv = this.selectDiv;
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(selectDiv).length <= 0){
				$(selectDiv).removeClass("l_active");
			}
		});
	},
	bindProvinceSelect: function(selectDiv, bindSelect, citySelectCallback){
		var provinceDiv = $(selectDiv).find("div.sheng");
		$(provinceDiv).find("a").click(function(){
			$(this).parent().children("a").removeClass("act");
        	$(this).addClass("act");
			var provinceId = $(this).attr("value");
			jQuery.ajax({
				url : "/base/initCity",
				type : "get",
				data : {"provinceId" : provinceId},
				cache : false,
				dataType : "json",
				success : function(result) {
					if (result && result.success) {
						$(provinceDiv).next().find("a").remove();
						for(var key in result.result){
							if(key > 0){
								$(provinceDiv).next().append('<a href=\"javascript:void(0);\" value=\"' + key + '\">' + result.result[key] + '</a>');
							}
						}
						$(provinceDiv).next().show();
						bindSelect(selectDiv, $(provinceDiv).next(), citySelectCallback);
						if($(provinceDiv).next().find("a").length == 1){
							$(provinceDiv).next().find("a").trigger("click");
						}
					}
				}
			});
			return false;
		});
	},
	bindClick:function(){
		var selectDiv = this.selectDiv;
		var selectCallback = this.selectCallback;
		var bindProvinceSelect = this.bindProvinceSelect;
		var bindSelect =  this.bindSelect;
		$(selectDiv).find("p > a").bind("click", function(){
			if($(selectDiv).hasClass("l_active")){
				$(selectDiv).removeClass("l_active");
	    	}else{
	    		$(selectDiv).addClass("l_active");
	    		if($(selectDiv).find("div.sheng > a").length == 0){
	    			jQuery.ajax({
	    				url : "/base/initProvince",
	    				type : "get",
	    				cache : false,
	    				dataType : "json",
	    				success : function(result) {
	    					if (result && result.success) {
	    						for(var key in result.result){
	    							if(key > 0){
	    								$(selectDiv).find("div.sheng").append('<a href=\"javascript:void(0);\" value=\"' + key + '\">' + result.result[key] + '</a>');
	    							}
	    						}
	    						bindProvinceSelect(selectDiv, bindSelect, selectCallback);
	    					}
	    				}
	    			});
	    		}
			}
		});
		return false;
	},
	bindSelect:function(selectDiv, cityList, callback){
		$(cityList).find("a").bind("click", function(){
        	var name = $(this).text();
        	var value = $(this).attr("value");
        	$(this).parent().children("a").removeClass("act");
        	$(this).addClass("act");
        	$(selectDiv).find("p > a").attr("city-id", value).text(name);
        	$(selectDiv).find("input[type='hidden']").val(value);
        	$(selectDiv).removeClass("l_active");
        	if(null!=callback){
        		callback(value);
        	}
    		return false;
        });
	}
});

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
		return false;
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
    		return false;
        });
	},
	resetSelect:function(){
		var selectDiv = this.selectDiv;
		var name = $(selectDiv).find("span > a").first().text();
    	var value = $(selectDiv).find("span >a").attr("value");
    	$(this.selectDiv).find("p > a").text(name);
    	$(selectDiv).find("input[type='hidden']").val(value);
	}
});

var PostSender =  Class.extend({
	init: function(sendForm){
		this.sendForm = sendForm;
		this.sendPostContent = sendForm.find("textarea[name='content']");
		this.contentInitMsg = this.sendPostContent.attr("init-tip");
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
			return false;
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
			return false;
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
			return false;
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
			return false;
		});
		sendPostAddress.find("div.show_area > div.area_title > a").click(function(){
			sendPostAddress.removeClass("active");
			return false;
		});
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(sendPostAddress).length <= 0){
				sendPostAddress.removeClass("active");
			}
		});
		sendPostAddress.find("div.show_area > div.ok_btn > a").bind("click", function(){
			var value = addressInput.val();
			if(value == addressInput.attr("init-tip")){
				value = "";
			}
			//check place
			if(!checkValLength(value, 0, 40)){
				sendPostAddress.find(".error").text("地点字数控制在20字以内").show();
				return false;;
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
			return false;
		});
	},
	initPic : function(){
		//pic
		var sendPostPic = this.sendPostPic;
		var sendForm = this.sendForm;
		sendPostPic.find("p > a.photo").click(function(){
			sendPostPic.addClass("active");
			return false;
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
			return false;
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
			return false;
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
			return false;
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
		var bindReportLink=this.bindReportLink;
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
					bindReportLink(this);
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
			return false;
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
				return false;
			}
			var content = commentForm.find("input[name='content']").val();
			if(!checkValLength(content, 4, 280)){
				commentForm.find(".error").text("留言内容控制在2~140个汉字内").show();
				return false;
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
			return false;
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
			return false;
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
			return false;
		});
	},
	bindAllDelLink: function(){
		var bindDelLink = this.bindDelLink;
		this.commentList.find("div.repy_list_s2 > ul > li").each(function(){
			bindDelLink(this);
		});
	},
	bindReportLink: function(item){
		$(item).find("a#report-conment").click(function(){
			var postId = $(this).attr("post-id");
			var targetUid = $(this).attr("uid");
			var content = $(this).attr("content");
			report(targetUid,'2',content,postId);
			return false;
		});
	},
	bindAllReportLink: function(){
		var bindReportLink=this.bindReportLink;
		this.commentList.find("div.repy_list_s2 > ul > li").each(function(){
			bindReportLink(this);
		});
	}
});

var FaceWidget = Class.extend({
	init : function(bqDiv){
		this.bqDiv = bqDiv;
		this.bindShow();
		this.bindHide();
		this.bindClick();
	},
	bindShow : function(){
		var bqDiv = this.bqDiv;
		$(bqDiv).find("p").click(function(){
			//bq选中状态
			$(bqDiv).addClass("bq_hover");
		});
	},
	bindHide : function(){
		var bqDiv = this.bqDiv;
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(bqDiv).length <= 0){
				bqDiv.removeClass("bq_hover");
			}
		});
	},
	bindClick : function(){
		var bqDiv = this.bqDiv;
		var inputObj = $("#" + $(bqDiv).find("p").attr("input-div-id"));
		$(bqDiv).find("div.bq_showbox > ul > li").click(function(){
			inputObj.focus();
			var value = $(this).find("img").attr("title");
			$(inputObj).val($(inputObj).val() + "[" + value + "]");
			bqDiv.removeClass("bq_hover");
		});
	}
});


function report(uid,contentType,content,contentId) {
	jQuery.ajax({
		url : "/plug/report/show",
		type : "post",
		data : {
			"uid" : uid,
			"contentType" : contentType,
			"content" : content,
			"contentId":contentId
		},
		dataType : "html",
		success : function(data) {
			openDialog(null, 'report_dialog', data);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function saveReport(){
	var description=$("input[name=description]").val();
	if(getByteLen(description)>400){
		$("#report_description_tip").html("字数控制在200字以内").stop(true, true).show()
		.fadeOut(4000);
	}
	jQuery.ajax({
		url: "/plug/report/save",
		type: "post",
		data:  $("#report_form").serialize(),
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				var content = $("#dialog-success").html().replace("{0}", "提交成功！");
				showSuccess(null, content);
			}else{
				alert(result.errorInfo);
			}
			closeDialog('report_dialog');
		},
		statusCode: {
		    401: function() {
		    	window.location.href = "/login?turnTo=" + window.location.href;
		    }
		}
	});
}

function shieldUid(uid, successCallback){
	jQuery.ajax({
		url : "/home/blacklist/shield",
		type : "post",
		cache : false,
		data : {"shieldUid" : uid},
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

function bindShieldUid(obj){
	var targetUid = obj.attr("target-uid");
	var nickname = obj.attr("target-name");
	var content = $("#dialog-shield-uid").html().replace("{0}", nickname);
	showConfirm(obj[0], "shieldUid", content, function(){
		shieldUid(targetUid, function(){
			var content = $("#dialog-success").html().replace("{0}", "屏蔽成功！");
			showSuccess(null, content);
			obj.unbind("click").addClass("done").text("已屏蔽");
		});
	});
}

function cancelShieldUid(uid, successCallback){
	jQuery.ajax({
		url : "/home/blacklist/cancel",
		type : "post",
		cache : false,
		data : {"shieldUid" : uid},
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

var SelectBoxInput =  Class.extend({
	init: function(div){
		this.selectDiv = div;
		var inputName = $(this.selectDiv).attr("name");
		var i=0;
		var value=$(this.selectDiv).find("p > a").attr("value");
		var name=$(this.selectDiv).find("p > a").attr("initName");
    	$(this.selectDiv).find("li").each(function(){
    		if($(this).hasClass("act")){
    			if(i==0){
        			value=$(this).attr("value");	
        			name=$(this).text();
        		}else{
        			value+=","+$(this).attr("value");
        			if(i<=1){
        				name+="...";
        			}
        		}
    			i++;
    		}
        });
    	$(this.selectDiv).find("p > a").text(name);
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
		return false;
	},
	bindSelect:function(){
		var selectDiv = this.selectDiv;
		$(selectDiv).find("ul > li").bind("click", function(){
        	if($(this).hasClass("act")){
        		$(this).removeClass("act");
			}else{
	        	$(this).addClass("act");
			}
        	var boxname=$(selectDiv).find("p > a").attr("initName");
        	var boxvalue=$(this).attr("value");
        	var i=0;
        	$(selectDiv).find("ul > li").each(function(){
        		var value = $(this).attr("value");	
        		if($(this).hasClass("act")){
        			if(i==0){
        				boxname= $(this).text();
        				boxvalue=value;
        			}else{
        				boxname= $(this).text()+"...";
        				boxvalue=boxvalue+","+value;
        			}
        			i++;
        		}
		    });
        	if(i==0){
        		boxvalue="";
        	}
        	$(selectDiv).find("input[type='hidden']").val(boxvalue);
        	$(selectDiv).find("p > a").text(boxname);
    		return false;
        });
	},
	resetSelect:function(){
		var selectDiv = this.selectDiv;
		var value=$(this.selectDiv).find("p > a").attr("value");
		var name=$(this.selectDiv).find("p > a").attr("initName");
		$(selectDiv).find("li").each(function(){
    			$(this).removeClass("act");
        });
    	$(selectDiv).find("p > a").text(name);
    	$(selectDiv).find("input[type='hidden']").val(value);
	}
});
