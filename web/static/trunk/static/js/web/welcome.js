$(document).ready(function(){
	var windowCnt = $("#window-box > ul").attr("window-count");
	var windowBox = $("#window-box");
	$("div.arrow_left > a").bind("click", function(){
		if(!windowBox.is(':animated')){
			var cLeft = windowBox.position().left;
			if(5 > cLeft){
				windowBox.animate({
					left: '+=150'
				}, 500);
			}
		}
		return false;
	});
	
	$("div.arrow_right > a").bind("click", function(){
		if(!windowBox.is(':animated')){
			var cLeft = windowBox.position().left;
			if(((windowCnt-5) * -150 + 5) < cLeft){
				windowBox.animate({
					left: '-=150'
				}, 500);
			}
		}
		return false;
	});
	
	$("a.go-login").click(function(){
		showLogin(window.location.href);
		return false;
	});
	
//	setInterval(function(){
//		jQuery.ajax({
//			url : "/welcomenum",
//			type : "get",
//			cache : false,
//			dataType : "html",
//			success : function(result) {
//				$("div.wel_num").html(result);
//			}
//		});
//	}, 5000);
});


function showLogin(turnTo){
	if(turnTo == null || turnTo == ""){
		turnTo = "";
	}
	var content = $("#dialog-login").html().replace(/\[0\]/ig, turnTo);
	var dialog = openDialog(null, "loginBox", content);
	var form = $(dialog.content()).find("#login-box-form");
	form.find("div.btn > a").click(function(){
		var account = trimStr(form.find("#form-account").find("input").val());
		if(!checkValLength(account, 6, 100) || !checkEmail(account)){
			form.find("div.error").text("请输入正确格式的邮箱").show();
			return false;
		}
		var pwd = form.find("#form-pwd").find("input").val();
		if(pwd == ""){
			form.find("div.error").text("请输入密码").show();
			return false;
		}
		jQuery.ajax({
			url: "/ajaxlogin",
			type: "post",
			data: form.serialize(),
			dataType: "json",
			success: function(result){
				if(result && result.success){
					window.location.href = result.result;
				}else{
					form.find("div.error").text(result.errorInfo).show();
				}
			}
		});
		return false;
	});
}