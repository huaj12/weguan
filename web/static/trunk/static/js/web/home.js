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
		bindShareBtn(this);
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



