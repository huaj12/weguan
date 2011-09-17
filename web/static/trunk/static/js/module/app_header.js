var Act = Class.extend({
	init: function(id, name){
		this.id = id;
		this.name = name;
	},
	check: function(){
		alert(name);
		if(!this.name||this.name==""||isNaN(this.id)){
			return false;
		}
		return true;
	}
});

$(document).ready(function(){
	//注册添加Act事件
	$("#addActBtn").bind("click", function(){
		var act = new Act($("#addAct").attr("actId"), $("#addAct").attr("value"));
		if(!act.check()){
			alert("error");
			return;
		}
		jQuery.ajax({
			url: "/app/ajax/addAct",
			type: "post",
			data: {"actName": act.name, "actId": act.id},
			dataType: "json",
			success: function(result){
				//result handle plugin
				if(result&&result.success){
					alert("保存成功");
				}else if(result){
					alert(result.errorInfo);
				}
			},
			statusCode: {
			    401: function() {
			      alert("未登录");
			    }
			},
			complete: function(){
				resetAddAct(inputObjId);
			}
		});
	});
	//注册订阅邮箱事件
	$("#subEmailBtn").bind("click", function(){
		
	});
	//注册获取新消息数事件
});