function selectCity(obj) {
	$.get('/base/selectCity', {
		proId : obj.value,
		random : Math.random()
	}, function(result) {
		$("#citys").html(result);
		if($("#c_id")[0]){
			selectTown($("#c_id").val());
			$("#towns").show();
		}else{
			$("#towns").hide();
		}
	});
}
function selectTown(id) {
	$.get('/base/selectTown', {
		cityId : id,
		random : Math.random()
	}, function(result) {
		$("#towns").html(result);
	});
}
function profession(obj){
	if(obj.value==0){
		$("#profession_input").show();
	}else{
		$("#profession_input").hide();
	}
}
function show_updateDiv(str){
	$("#user_"+str).hide();
	$("#input_"+str).show();
	$("#ts_"+str).show();
}
function cancel_updateDiv(str){
	$("#user_"+str).show();
	$("#input_"+str).hide();
	$("#ts_"+str).hide();
}
function setNickname(){
	$("#ts_nickname").hide();
	var name=$("#nickname").val();
	if(!checkValLength(name, 1, 20)){
		$("#error_nickname").html("控制在1-10个中文内！");
		return ;
	}
	jQuery.ajax({
		url: "/profile/setting/nickname",
		type: "post",
		data: {"nickName" : name},
		dataType: "json",
		success: function(result){
			if(result.success){
				$("#nickname_xg").html("");
				cancel_updateDiv('nickname');
				$("#new_nickname").html(name);
				$("#error_nickname").html("");
			}else{
				$("#error_nickname").html(result.errorInfo);
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}
function setGender(){
	$("#ts_gender").hide();
	var gender=$("#gender").val();
	jQuery.ajax({
		url: "/profile/setting/gender",
		type: "post",
		data: {"gender" : gender},
		dataType: "json",
		success: function(result){
			if(result.success){
				$("#gender_xg").html("");
				cancel_updateDiv('gender');
				if(gender==1){
					$("#new_gender").html('男');	
				}else{
					$("#new_gender").html('女');
				}
				$("#error_nickname").html("");
			}else{
				$("#error_gender").html(result.errorInfo);
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}
function setting(){
	var province=$("#province").val();
	var city=$("#city").val();
	var town=$("#town").val();
	var birthYear=$("#birthYear").val();
	var birthMonth=$("#birthMonth").val();
	var birthDay=$("#birthDay").val();
	var birthSecret="0";
	var professionId=$("#professionId").val();
	var profession=$("#profession").val();
	var feature=$("#feature").val();
	if(professionId==0&&(profession==""||profession=="10个字以内描述")){
		 $(".pj_error").html("请输入您的职业描述");
		 return ;
	}
	if(professionId==0&&!checkValLength(profession, 1, 20)){
		$("#profession")[0].focus();
		 $(".pj_error").html("描述在1-10个字之间");
		 return ;
	}
	if(!checkValLength(feature, 0, 70)){
		$("#feature")[0].focus();
		 $("#feature_tp").html("不要超过70个字哦!");
		 return ;
	}
	
	if($("#towns").css("display")=="none"){
		town="-1";
	}
	if($("#birthSecret").is(':checked')){
		birthSecret="1";
	}
	if(professionId!=0){
		profession="";
	}
	jQuery.ajax({
		url: "/profile/setting",
		type: "post",
		data: {"province":province,"city" : city,"town" : town,"birthYear" : birthYear,"birthMonth" : birthMonth,
			"birthDay" : birthDay,
			"birthSecret" : birthSecret,
			"professionId" : professionId,
			"profession" : profession,
			"feature" : feature},
		dataType: "json",
		success: function(result){
			if(result.success){
				//保存成功后跳转
				alert('跳转');
			}else{
				$(".pj_error").html(result.errorInfo);
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
	
	
}