$(document).ready(function(){
	//date
	$("#send-post-date > p > a.time").click(function(){
		var timeClick = $(this);
		WdatePicker({
			errDealMode : 3,
			dateFmt:"MM-dd",
			onpicked:function(){
				var value = $dp.cal.getP('y') + "-" + $dp.cal.getP('M') + "-" + $dp.cal.getP('d');
				$("input[name='dateString']").val(value);
				$("#send-post-date").addClass("done");
			},
			oncleared:function(){
				$("input[name='dateString']").val("");
				timeClick.text("时间");
				$("#send-post-date").removeClass("done");
			}
		});
	});
	
	//place
	$("#send-post-address > p > a").bind("click", function(){
		$(this).parent().parent().addClass("active");
	});
	$("#send-post-address > div.show_area > div.area_title > a").click(function(){
		$("#send-post-address").removeClass("active");
	});
	$("body").bind("mousedown",function(event){
		if($(event.target).closest("#send-post-address").length <= 0){
			$("#send-post-address").removeClass("active");
		}
	});
	$("#send-post-address > div.show_area > div.ok_btn > a").bind("click", function(){
		var value = $(this).parent().parent().find("div.input > span > input").val();
		//check place
		if(!checkValLength(value, 0, 40)){
			$("#send-post-address").find(".error").text("地点字数控制在20字以内").show();
			return;
		}
		$('input[name="place"]').val(value);
		$("#send-post-address").removeClass("active");
		$("#send-post-address > p > a").attr("title", value);
		if(value != null && value != ""){
			$("#send-post-address").addClass("done");
		}else{
			$("#send-post-address").removeClass("done");
		}
		$("#send-post-address").find(".error").hide();
	});
	
	//pic
	$("#send-post-pic > p > a.photo").click(function(){
		$(this).parent().parent().addClass("active");
	});
	$("body").bind("mousedown",function(event){
		if($(event.target).closest("#send-post-pic").length <= 0){
			$("#send-post-pic").removeClass("active");
		}
	});
	$("input.btn_file_molding").change(function(){uploadPic();});
	$("div.upload_ok > em > a").click(function(){
		$("#send-post-pic").removeClass("done");
		$("input[name='pic']").val("");
		$("div.upload_ok > div.img > img").attr("src", $("div.upload_ok > div.img > img").attr("init-pic"));
		$("div.upload_ok").hide();
		$("#send-post-pic").find("div.load_error").hide();
		$("#send-post-pic > div.show_area > div.upload-input").show();
	});
	
	$("div.tb").bind("click", function(){
		if($(this).hasClass("tb_click")){
			$(this).removeClass("tb_click");
			$('input[name="sendWeibo"]').val(false);
		}else{
			$(this).addClass("tb_click");
			$('input[name="sendWeibo"]').val(true);
		}
	});
	
	//submit
	$("div.send_area > div.btn > a").bind("click", function(){
		var content = $("div.send_area > div.textarea > textarea").val();
		if(!checkValLength(content, 10, 280)){
			$(".send_box_error").text("内容控制在5-140个汉字内").show();
			return;
		}
		var sendBtn = $(this).hide();
		var sendingBtn = $("div.send_area > div.sending").show();
		$.ajax({
			url : "/post/createPost",
			type : "post",
			cache : false,
			data : $('form[name="sendPost"]').serialize(),
			dataType : "json",
			success : function(result) {
				sendingBtn.hide();
				sendBtn.show();
				if(result&&result.success){
					//reset form
					resetSendPostForm();
					var content = $("#dialog-success").html().replace("{0}", "发布成功！");
					showSuccess(null, content);
				}else{
					$(".send_box_error").text(result.errorInfo).show();
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
			$("div.interest-" + uid).show();
		});
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).show();
		});
	});
	
	//右侧idea
	$("div.idea").find("div.send > a").click(function(){
		var ideaId = $(this).attr("idea-id");
		var send = $(this).parent().hide();
		var sending = send.prev().show();
		$.ajax({
			url : "/post/postIdea",
			type : "post",
			cache : false,
			data : {"ideaId" : ideaId},
			dataType : "json",
			success : function(result) {
				if(result&&result.success){
					sending.attr("class", "sended").children("a").text("已发布");
					send.unbind("click");
					$("#useCount-" + ideaId).text(parseInt($("#useCount-" + ideaId).text()) + 1);
					var content = $("#dialog-success").html().replace("{0}", "发布成功！");
					showSuccess(sending[0], content);
				}else{
					alert(result.errorInfo);
					sending.hide();
					send.show();
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

function resetSendPostForm(){
	$('form[name="sendPost"]')[0].reset();
	$('input[name="place"]').val("");
	$("input[name='dateString']").val("");
	$(".send_box_error").hide();
	
	$("#send-post-address").find("input[type='text']").val("");
	$("#send-post-address").removeClass("done");
	$("#send-post-address").find(".error").hide();
	
	$("#send-post-date > p > a.time").text("时间");
	$("#send-post-date").removeClass("done");
}

function uploadPic() {
	$("#send-post-pic > div.show_area > div.upload-input").hide();
	$("#send-post-pic").find("div.load_error").hide();
//	$("div.upload > div.loading").show();
	var options = {
		url : "/post/pic/upload",
		type : "POST",
		dataType : "json",
		iframe : "true",
		success : function(result) {
			if (result.success) {
				$("input[name='pic']").val(result.result[1]);
				$("div.upload_ok > div.img > img").attr("src", result.result[0]);
				$("div.upload_ok").show();
				$("#send-post-pic").addClass("done");
//				$("div.upload > div.loading").hide();
			} else if (result.errorCode == "00003") {
				window.location.href = "/login?turnTo=" + window.location.href;
			} else {
//				$("div.upload > div.loading").hide();
				$("#send-post-pic").find("div.load_error").text(result.errorInfo).show();
				$("#send-post-pic > div.show_area > div.upload-input").show();
			}
		},
		error : function(data) {
//			$("div.upload > div.loading").hide();
			$("#send-post-pic").find("div.load_error").text("上传失败").show();
			$("#send-post-pic > div.show_area > div.upload-input").show();
		}
	};
	$("#uploadPicForm").ajaxSubmit(options);
	return false;
}