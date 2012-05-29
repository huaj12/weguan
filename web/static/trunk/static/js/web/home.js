$(document).ready(function(){
	var closePostTipsCookieName = 'C_POST_TIPS';
	//判断cookie是否显示tips
	var closePostTips= $.cookie(closePostTipsCookieName);
	if(null == closePostTips){
		$("div.tips").show();
	}
	$("div.tips > a").click(function(){
		$(this).parent().remove();
		$.cookie(closePostTipsCookieName, true, {expires: 0, path: '/', domain: '51juzhai.com', secure: false});
		return false;
	});
	var postSender = new PostSender($("form[name='sendPost']"));
	postSender.bindSubmit(function(sendForm){
		$.ajax({
			url : "/post/createPost",
			type : "post",
			cache : false,
			data : sendForm.serialize(),
			dataType : "json",
			success : function(result) {
				sendForm.find("div.sending").hide();
				sendForm.find("div.btn").show();
				if(result&&result.success){
					//reset form
					resetSendPostForm(sendForm);
					var content = $("#dialog-success").html().replace("{0}", "发布成功！");
					showSuccess(null, content);
				}else{
					sendForm.find(".send_box_error").text(result.errorInfo).show();
				}
			},
			statusCode : {
				401 : function() {
					window.location.href = "/login?turnTo=" + window.location.href;
				}
			}
		});
	});
	
	//绑定下拉框	
	$("#town-select").find("span > a").bind("click", function(){
		var queryType = $("div.category").attr("queryType");
		var townId = $(this).attr("value");
		var gender = $("#gender-select").find("a.selected").attr("value");
		window.location.href = "/home/" + queryType + "/" + townId + "_" + gender + "/1";
		return false;
	});
	$("#gender-select").find("span > a").bind("click", function(){
		var queryType = $("div.category").attr("queryType");
		var gender = $(this).attr("value");
		var townId = $("#town-select").find("a.selected").attr("value");
		window.location.href = "/home/" + queryType + "/" + (townId == null ? "" : (townId + "_")) + gender + "/1";
		return false;
	});
	
	//列表
	$("div.message_s2 > a").click(function(){
		var postId = $(this).attr("post-id");
		var commentListBox = $("div#comment-box-" + postId);
		var commentWidget = new CommentWidget(commentListBox.find("form"), commentListBox.find("div.comment-list"));
		if(commentListBox.attr("loaded") == "false"){
			commentListBox.attr("loaded", true);
			commentWidget.bindReply();
			commentWidget.loadList();
		}
		if(commentListBox.is(":visible")){
			commentListBox.fadeOut(100, function(){
				commentWidget.initForm();
			});
		}else{
			commentListBox.fadeIn(200);
		}
		return false;
	});
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("div.remove-interest-" + uid).hide();
			$("div.interest-" + uid).attr("style", "");
		});
		return false;
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).attr("style", "");
		});
		return false;
	});
	$("div.mail > a").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
	});
	
	$("div.zfa > a").click(function(){
		var postId = $(this).attr("post-id");
		prepareRepost(postId);
		return false;
	});
	
	$("div.float_box > div > div.close > a").click(function(){
		var tip = $(this).parent().parent().parent();
		tip.animate({bottom:'+=-100'}, 800, function(){tip.remove();});
	});
	
	$("div.float_box").show().animate({bottom:"+=100"}, 1000);
	
	$("div.cake_icon > a").click(function(){
		$("div.cake_show").toggle();
		return false;
	});
	$("body").bind("mousedown",function(event){
		if($(event.target).closest($("div.cake")).length <= 0){
			$("div.cake_show").hide();
		}
	});
	
	$("div.random_select > a").each(function(){
		$(this).click(function(){
			var type=$(this).attr("class");
			var dialog = openDialog(null, "shareIdeaBox", $("#share-idea-box").html());
			$(dialog.content()).find("div.title > p").attr("class", type);
			$(dialog.content()).find("div.link > a").attr("href", $("#link-"+type).val());
			$(dialog.content()).find("div.link > a").text($("#link-"+type).attr("name"));
			$(dialog.content()).find("div.link > a").attr("target","_blank");
			$(dialog.content()).find("div.pub_input > span > input").attr("init-tip",$("#link-"+type).attr("init-tip"));
			$(dialog.content()).find("div.title > h2").text($("#link-"+type).attr("title"));
			registerInitMsg($(dialog.content()).find("div.pub_input > span > input"));
			$(dialog.content()).find("div.title > a").click(function(){
				closeDialog("shareIdeaBox");
				return false;
			});
			$(dialog.content()).find("div.btn > a").click(function(){
				shareIdea($(this),dialog);
				return false;
			});
			return false;
		});
    });
});

function resetSendPostForm(sendForm){
	sendForm[0].reset();
	resetAdditionForm(sendForm);
}

function resetAdditionForm(sendForm){
	sendForm.find('input[name="categoryId"]').val(0);
	sendForm.find('input[name="place"]').val("");
	sendForm.find("input[name='dateString']").val("");
	sendForm.find("input[name='pic']").val("");
	sendForm.find(".send_box_error").hide();
	
	var initText = sendForm.find("#send-post-category").find("div.tag_list > a").removeClass("act").first().addClass("act").text();
	sendForm.find("#send-post-category > p > a").text(initText);
	sendForm.find("#send-post-category").removeClass("done").removeClass("active");
	
	sendForm.find("#send-post-address").find("input[type='text']").val("");
	sendForm.find("#send-post-address").removeClass("done").removeClass("active");
	sendForm.find("#send-post-address").find(".error").hide();
	
	sendForm.find("#send-post-date > p > a.time").text("时间");
	sendForm.find("#send-post-date").removeClass("done").removeClass("active");
	
	sendForm.find("#send-post-pic").removeClass("done").removeClass("active");
	sendForm.find("div.upload").show();
	sendForm.find("div.upload > div.load_error").text("").hide();
	sendForm.find("div.upload_ok > div.img > img").attr("src", sendForm.find("div.upload_ok > div.img > img").attr("init-pic"));
	sendForm.find("div.upload_ok").hide();
}

function shareIdea(obj,dialog){
	obj.addClass("loading");
	obj.text("加载中...");
	var url=$(dialog.content()).find("div.pub_input > span > input").val();
	var urlDes=$(dialog.content()).find("div.pub_input > span > input").attr("init-tip");
	if(url==""||url==urlDes){
		$(dialog.content()).find("div.error").show().text("请输入链接");
		obj.removeClass("loading");
		obj.text("确认");
		return false;
	}else{
		$(dialog.content()).find("div.error").hide();
	}
	$.ajax({
		url : "/idea/share",
		type : "post",
		cache : false,
		data : {
			"url" : url
		},
		dataType : "json",
		success : function(result) {
			if(result&&result.success){
				var content = $("#show-share-idea-box").html();
				content=content.replace("{title}",result.result.content);
				if(result.result.startDateString!=null&&result.result.endDateString!=null){
					content=content.replace("{beginTime}",result.result.startDateString.substring(0,10));
					content=content.replace("{endTime}",result.result.endDateString.substring(0,10));
				}
				if(result.result.cityName!=null){
					content=content.replace("{cityName}",result.result.cityName);
				}else{
					content=content.replace("{cityName}","");
				}
				if(result.result.townName!=null){
					content=content.replace("{townName}",result.result.townName);
				}else{
					content=content.replace("{townName}","");
				}
				if(result.result.place!=null){
					content=content.replace("{place}",result.result.place);
				}else{
					content=content.replace("{place}","");
				}
				var showDialog=openDialog(null, "showShareIdeaBox",content);
				if(result.result.startDateString==null||result.result.endDateString==null){
					$(showDialog.content()).find("div.img_infor > #tip").text("");
					$(showDialog.content()).find("div.img_infor > #data").text("");
				}
				//关闭上一个层
				closeDialog("shareIdeaBox");
				$(showDialog.content()).find("div.img > img").attr("src",result.result.picWeb);
				$(showDialog.content()).find("div.title > a").click(function(){
					closeDialog("showShareIdeaBox");
					return false;
				});
				$(showDialog.content()).find("div.btn > a").click(function(){
					saveIdea($(this),showDialog,result.result);
				});
				
			}else{
				obj.removeClass("loading");
				obj.text("确认");
				$(dialog.content()).find("div.error").show().text(result.errorInfo);
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

function saveIdea(obj,dialog,result){
	obj.addClass("loading");
	obj.text("提交中...");
	var startDateString="";
	var endDateString="";
	var city="";
	var town="";
	var place="";
	var detail="";
	var charge="";
	var link="";
	if(result.startDateString!=null){
		startDateString=result.startDateString;
	}
	if(result.endDateString!=null){
		endDateString=result.endDateString;
	}
	if(result.city!=null){
		city=result.city;
	}
	if(result.town!=null){
		town=result.town;
	}
	if(result.place!=null){
		place=result.place;
	}
	if(result.detail!=null){
		detail=result.detail;
	}
	if(result.charge!=null){
		charge=result.charge;
	}
	if(result.link!=null){
		link=result.link;
	}
	$.ajax({
		url : "/idea/save",
		type : "post",
		cache : false,
		data : {
			"content" : result.content,
			"pic" : result.pic,
			"startDateString" : startDateString,
			"endDateString" : endDateString,
			"city" : city,
			"town" : town,
			"place" : place,
			"detail" : detail,
			"charge" : charge,
			"link" : link,
			"categoryId":result.categoryId
		},
		dataType : "json",
		success : function(result) {
			var content = $("#share-idea-tip-box").html();
			var tipDialog=openDialog(null, "shareIdeaTipBox",content);
			closeDialog("showShareIdeaBox");
			if(result&&result.success){
				$(tipDialog.content()).find("div.title > h2").text("分享成功");
				$(tipDialog.content()).find("div.share_suc > p").text("好主意提交成功:)");
			}else{
				$(tipDialog.content()).find("div.title > h2").text("分享失败");
				$(tipDialog.content()).find("div.share_suc > p").text(result.errorInfo);
			}
			$(tipDialog.content()).find("div.title > a").click(function(){
				closeDialog("shareIdeaTipBox");
				return false;
			});
			$(tipDialog.content()).find("div.btn > a").click(function(){
				closeDialog("shareIdeaTipBox");
				return false;
			});
			tipDialog.time(2);
			
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}