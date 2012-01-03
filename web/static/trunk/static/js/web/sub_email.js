$(document).ready(function(){
	$("#updateEmail").submit(function(){
		var email = $("#subEmail").val();
		if(!email || email == ""){
			$("#emailError").text("请输入邮箱地址！").show();
			return false;
		}
		if(!checkEmail(email)){
			$("#emailError").text("邮箱地址格式有误！").show();
			return false;
		}
		return true;
	});
	
	$("#updateEmailBtn").bind("click", function(){
		$("#updateEmail").submit();
	});
});