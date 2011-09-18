function addAct(successCallback){
	var value = $("#addAct").attr("value");
	if(!checkValLength(value, 2, 20)){
		$("#addActError").text("拒宅兴趣字数控制在1－10个中文内！").show().fadeOut(2000);
		return false;
	}
	jQuery.ajax({
		url: "/app/ajax/addAct",
		type: "post",
		data: {"actName": value},
		dataType: "json",
		success: function(result){
			//result handle plugin
			if(result&&result.success){
				$("#addActError").text("保存成功").show().fadeOut(3000);
				$("#addAct").attr("value", "");
			}else if(result){
				$("#addActError").text(result.errorInfo).show().fadeOut(2000);
			}else {
				alert("系统异常！");
			}
			if(successCallback){
				successCallback(result);
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

function subEmail(){
	//validation
	var email = $("div.dy > span > input").attr("value");
	if(!checkEmail(email)){
		$("div.dy > div.error").text("邮箱格式有误！").show().fadeOut(2000);
		return false;
	}
	jQuery.ajax({
		url: "/app/ajax/subEmail",
		type: "post",
		data: {"email": email},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$("div.dy > div.error").text("订阅成功").show().fadeOut(3000);
			}else if(result){
				$("div.dy > div.error").text(result.errorInfo).show().fadeOut(2000);
			}else {
				alert("系统异常！");
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

$(document).ready(function(){
	//注册添加Act事件
	$("div.zjxq_input > a.add").bind("click", function(){
		addAct();
	});
	//注册召集令
	$("div.zjxq_input > a.zhao").bind("click", function(){
		addAct(function(result){
			alert(result.errorInfo);
		});
	});
	//注册邀请
	$("div.zjxq_input > a.yao").bind("click", function(){
		addAct(function(result){
			alert(result);
		});
	});
	//注册订阅邮箱事件
	$("div.dy > a").bind("click", function(){
		subEmail();
	});
	//注册获取新消息数事件
	
	//注册autoComplete
	$("#addAct").autocomplete("/autoSearch", {
		dataType: "json",
		parse: function(datas) {
			var parsed = [];
			for (var i=0; i < datas.length; i++) {
				var data = datas[i];
				if (data) {
					parsed[parsed.length] = {
						data: data,
						value: data.name,
						result: data.name
					};
				}
			}
			return parsed;
		},
		formatItem: function(item) {
			return item.name;
		}
	});
});