$(document).ready(function(){
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
	
	setTimeout(rollAnimation, 2000);
});

function rollAnimation(){
	var liObj = $("div.wel_cqw > ul").find("li:last").hide();
	liObj.remove();
	$("div.wel_cqw > ul").prepend(liObj);
	liObj.slideDown();
	setTimeout(rollAnimation, Math.floor(Math.random()*5000 + 1000));
}

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