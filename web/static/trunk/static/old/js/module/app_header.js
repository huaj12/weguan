$(document).ready(function() {
	var addActInput = new AddActInput($("#addAct"), $("#headAddActError"));
	addActInput.bindKeyUp();
	addActInput.bindFocus();
	addActInput.bindBlur();
	addActInput.bindAutocomplete();
	
	// 注册搜索Act事件
	$("#_queryActs").bind("click", function() {
		_queryActs();
	});
});

var AddActInput =  Class.extend({
	init: function(jInputObj, jErrorObj){
		inputObj = jInputObj;
		errorObj = jErrorObj;
	},
	bindKeyUp:function(){
		inputObj.keyup(function(event){
			showActTip(event.target, errorObj);
		});
	},
	bindFocus:function(){
		inputObj.bind("focus", function(event){
			showActTip(event.target, errorObj);
		});
	},
	bindBlur:function(){
		inputObj.bind("blur",function(){
			errorObj.hide();
		});
	},
	bindAutocomplete:function(){
		//注册autoComplete
		inputObj.autocomplete("/searchAutoMatch", {
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
	}
});

function showActTip(inputObj, infoObj){
	if($(inputObj).val() == ""){
		infoObj.html("<em>输入拒宅项目,如:逛街</em>").stop(true, true).show();
	}else{
		infoObj.hide();
	}
}

function _queryActs(){
	var value = $("#addAct").attr("value");
	if (!value || value == ""||value=='输入拒宅项目,如:逛街') {
		$("#headAddActError").html("<em>请先输入</em>").stop(true, true).show()
				.fadeOut(2000);
		return false;
	}
	if (!checkValLength(value, 2, 20)) {
		$("#headAddActError").html("<em>拒宅兴趣字数控制在1－10个中文内！</em>").stop().show().fadeOut(
				2000);
		return false;
	}
	location.href="/queryAct?name="+value;
}