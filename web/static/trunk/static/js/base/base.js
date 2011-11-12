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
		inputObj.autocomplete("/autoSearch", {
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
		infoObj.text("输入你的拒宅兴趣，如：逛街、K歌...").stop(true, true).show();
	}else{
		infoObj.hide();
	}
}

function mouseHover(li, isOver){
	if(isOver){
		$(li).addClass("hover");
	}else {
		$(li).removeClass("hover");
	}
}