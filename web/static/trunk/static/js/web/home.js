$(document).ready(function(){
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
	$("#city-select").find("span > a").bind("click", function(){
		var queryType = $("div.category").attr("queryType");
		var cityId = $(this).attr("value");
		var gender = $("#gender-select").find("a.selected").attr("value");
		window.location.href = "/home/" + queryType + "/" + cityId + "_" + gender + "/1";
	});
	$("#gender-select").find("span > a").bind("click", function(){
		var queryType = $("div.category").attr("queryType");
		var gender = $(this).attr("value");
		var cityId = $("#city-select").find("a.selected").attr("value");
		window.location.href = "/home/" + queryType + "/" + cityId + "_" + gender + "/1";
	});
	
	//列表
	$("div.post-response > a").click(function(){
		var postId = $(this).attr("post-id");
		var obj = $(this);
		responsePost(this, postId, function(){
			var currentCnt = parseInt(obj.find("font").text());
			obj.find("font").text(currentCnt + 1);
			obj.text("已" + obj.text()).unbind("click").parent().addClass("done");
		});
	});
	
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("div.remove-interest-" + uid).hide();
			$("div.interest-" + uid).attr("style", "");
		});
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).attr("style", "");
		});
	});
	$("div.message_s1 > a").bind("click", function(){
		var uid = $(this).attr("target-uid");
		var nickname = $(this).attr("target-nickname");
		openMessage(uid, nickname);
	});
	
	$("div.zfa > a").click(function(){
		var postId = $(this).attr("post-id");
		prepareRepost(postId);
	});
	
	$("div.random_select > a").click(function(){
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
					
					$("textarea[name='content']").val(content);
					
					//place
					$("div#send-post-address").find("input[name='place']").val(place!=null&&place!=""?place:"");
					$("div#send-post-address").find("input[type='text']").val(place!=null&&place!=""?place:"");
					$("div#send-post-address").toggleClass("done", place!=null&&place!="");
					
					//date
					if(null!=dateTime&&dateTime!=""){
						$("div#send-post-date").find("input[name='dateString']").val(dateTime);
						var array =  dateTime.split("-");
						$("div#send-post-date").find("p > a").text(array[1] + "-" + array[2]);
					}else{
						$("div#send-post-date").find("input[name='dateString']").val("");
						$("div#send-post-date").find("p > a").text("时间");
					}
					$("div#send-post-date").toggleClass("done", null!=dateTime&&dateTime!="")
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

function resetSendPostForm(sendForm){
	sendForm[0].reset();
	sendForm.find('input[name="place"]').val("");
	sendForm.find("input[name='dateString']").val("");
	sendForm.find("input[name='pic']").val("");
	sendForm.find(".send_box_error").hide();
	
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