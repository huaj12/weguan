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
					var postContent=sendForm.find("textarea[name='content']").val();
					resetSendPostForm(sendForm);
					waitRescueUser(postContent);
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
	
	$("div.send_box").find("div.random_select > a.random").click(function(){
		$("textarea[name='content']").bind("keyup", function(){
			var random = $(this).attr("random");
			if(null != random && random){
				$(this).attr("random", false);
				resetAdditionForm($("form[name='sendPost']"));
			}
		});
		$.ajax({
			url : "/idea/random",
			type : "post",
			cache : false,
			data : {},
			dataType : "json",
			success : function(result) {
				if(result && result.success){
					var content = result.result.content;
					var dateTime = result.result.dateTime;
					var place = result.result.place;
					var categoryId = result.result.categoryId;
					var pic = result.result.pic;
					var picUrl = result.result.picUrl;
					var ideaId = result.result.id;
					
					resetSendPostForm($("form[name='sendPost']"));
					$("textarea[name='content']").val(content);
					$("textarea[name='content']").attr("random", true);
					//place
					if(place != null && place != ""){
						var sendPostAddress = $("div#send-post-address");
						sendPostAddress.find("input[name='place']").val(place);
						sendPostAddress.find("input[type='text']").val(place);
						sendPostAddress.addClass("done");
					}
					//date
					if(null != dateTime && dateTime != ""){
						var sendPostDate = $("div#send-post-date");
						sendPostDate.find("input[name='dateString']").val(dateTime);
						var array =  dateTime.split("-");
						sendPostDate.find("p > a").text(array[1] + "-" + array[2]);
						sendPostDate.addClass("done");
					}
					//pic
					if(null != pic && pic != "" && null != picUrl && picUrl != ""){
						var sendPostPic = $("div#send-post-pic");
						sendPostPic.find("div.show_area > div.upload_photo_area > div.upload").hide();
						sendPostPic.find("div.load_error").hide();
						sendPostPic.find("input[name='pic']").val(pic);
						sendPostPic.find("input[name='picIdeaId']").val(ideaId);
						sendPostPic.find("div.upload_ok > div.img > img").attr("src", picUrl);
						sendPostPic.find("div.upload_ok").show();
						sendPostPic.addClass("done");
					}
					//category
					if(categoryId > 0){
						var sendPostCategory = $("div#send-post-category");
						var selectCategory = sendPostCategory.find("div.tag_list > a[value='" + categoryId + "']");
						if(null != selectCategory){
							sendPostCategory.find("div.tag_list > a").first().removeClass("act");
							selectCategory.addClass("act");
							sendPostCategory.find("input[name='categoryId']").val(selectCategory.attr("value"));
							sendPostCategory.find("p > a").text(selectCategory.text());
							sendPostCategory.addClass("done");
						}
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
	
	$("div.s_input > a").click(function(){
		$("#search-post-form").submit();
		return false;
	});
	
	$("div.s_input> span >input").each(function(){
		registerInitMsg($(this));
	});
	
	$("a.user-remove-interest").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("a.remove-interest-" + uid).hide();
			$("a.interest-" + uid).attr("style", "");
		});
		return false;
	});
	
	$("a.user-add-interest").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("a.interest-" + uid).hide();
			$("a.remove-interest-" + uid).attr("style", "");
		});
		return false;
	});
	$("span > a.send-message").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
		return false;
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

function waitRescueUser(postContent){
	$.ajax({
		url : "/profile/wait/rescue/user",
		type : "get",
		cache : false,
		dataType : "html",
		success : function(result) {
			if(result!=null&&result.indexOf("send_suss")!=-1){
			var dialog = openDialog(null, "waitRescueUserBox", result);
			$(dialog.content()).find("div.btns").first().click(function(){
				var uids="";
				$(dialog.content()).find('input[name=uids]').each(function(){
					if(this.value!=null&&this.value!=''){
					 uids=uids+this.value+",";
					}
				});
				waitRescueUserSMS(uids, postContent);
				closeDialog("waitRescueUserBox");
				return false;
			});
			$(dialog.content()).find("div.btns>a.ws").click(function(){
				closeDialog("waitRescueUserBox");
				return false;
			});
			}else{
				var content = $("#dialog-success").html().replace("{0}", "发布成功！");
				showSuccess(null, content);
			}
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

function waitRescueUserSMS(uids,postContent){
	$.ajax({
		url : "/home/rescueUserSMS",
		type : "post",
		cache : false,
		data : {
			"uids" : uids,"postContent":postContent
		},
		dataType : "json",
		success : function(result) {
				var content = $("#dialog-success").html().replace("{0}", "发布成功！");
				showSuccess(null, content);
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}

