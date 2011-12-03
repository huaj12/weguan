function clickAct(obj){
	var parent = $(obj).parent();
	if(parent.hasClass("selected")){
		parent.removeClass("selected");
		$(obj).attr("title", "点击添加");
		$(".goon_add").hide();
	}else {
		//判断数量
		if(!checkActCnt(4)){
			return;
		}
		parent.addClass("selected");
		$(obj).attr("title", "点击取消");
	}
}

function hoverAct(obj, isOver){
	var parent = $(obj).parent();
	if(parent.hasClass("selected")){
		return;
	}
	if(!isOver){
		parent.removeClass("hover");
	}else {
		parent.addClass("hover");
	}
}

function checkActCnt(checkCnt){
	//判断数量
	if($("#acts > p.selected").size() > checkCnt){
		$(".goon_add").text("不要超过5个项目哦!").css("color", "red").show();
		return false;
	}
	return true;
}

$(document).ready(function(){
	$(".key_words").each(function(){
		$(this).bind("click", function(){
			clickAct(this);
		});
		$(this).bind("mouseover", function(){
			hoverAct(this, true);
		});
		$(this).bind("mouseout", function(){
			hoverAct(this, false);
		});
	});
	
	var addActInput = new AddActInput($("#actName"), $("#actNameError"));
	//addActInput.bindKeyUp();
	//addActInput.bindFocus();
	//addActInput.bindBlur();
	addActInput.bindAutocomplete();
	
	$("a.add").bind("click", function(){
		var actNameInput = $("#actName");
		var value = actNameInput.attr("value");
		if(!value || value == ""){
			$("#actNameError").text("请先输入").stop(true, true).show().fadeOut(2000);
			return false;
		}
		if(!checkValLength(value, 2, 20)){
			$("#actNameError").text("拒宅兴趣字数控制在1－10个中文内！").stop(true, true).show().fadeOut(2000);
			return false;
		}
//		var p = $("<p class='hover selected'><span class='fl'></span></p>");
		var p = $("<p><span class='fl'></span></p>");
		var a = $("<a href='javascript:;' class='key_words'></a>");
		a.html(value);
		a.attr("actName", value);
		a.attr("title", "点击取消");
		a.bind("click", function(){
			clickAct(a);
		});
		a.bind("mouseover", function(){
			hoverAct(a, true);
		});
		a.bind("mouseout", function(){
			hoverAct(a, false);
		});
		p.append(a);
		p.append("<span class='fr'></span><em></em>");
		$("#acts").append(p);
		actNameInput.attr("value","");
		a.click();
		if(p.hasClass("selected")){
			$(".goon_add").text("请继续添加，或点击下一步!").css("color", "").show();
		}
	});
	
	$("#nextForm").submit(function(){
		if(!checkActCnt(5)){
			return false;
		}
		$("#acts > p.selected > a").each(function(){
			var input = $("<input type='hidden' />");
			if($(this).attr("actId")){
				input.attr("name", "actId");
				input.attr("value", $(this).attr("actId"));
			}else if($(this).attr("actName")) {
				input.attr("name", "actName");
				input.attr("value", $(this).attr("actName"));
			}
			if(input.attr("name")){
				$("#nextForm").append(input);
			}
		});
		
//		var email = $("div.add_dy > strong > input").attr("value");
//		if(email && email != ""){
//			if(!checkEmail(email)){
//				$("div.add_dy > div.error").text("邮箱格式有误！").stop(true, true).show().fadeOut(2000);
//				return false;
//			} else {
//				var input = $("<input type='hidden' name='email' />");
//				input.attr("value", email);
//				$("#nextForm").append(input);
//			}
//		}
		return true;
	});
	
	$(".next").bind("click", function(){
		$("#nextForm").submit();
		return false;
	});
	
	$(".complete").bind("click", function(){
		var email = $("div.add_dy > strong > input").attr("value");
		if(email && email != ""){
			if(!checkEmail(email)){
				$("div.add_dy > div.error").text("邮箱格式有误！").stop(true, true).show().fadeOut(2000);
				return false;
			} else {
				var input = $("<input type='hidden' name='email' />");
				input.attr("value", email);
				$("#nextForm").append(input);
				
				$("div.dy_ok > span > span").text(email);
				$("div.dy_ok > a.edit").bind("click", function(){
					$("div.add_dy > strong > input").val(email);
					// 移除hidden
					input.remove();
					$("div.dy_ok").hide();
					$("div.add_dy").show();
				});
				$("div.dy_ok > a.delete").bind("click", function(){
					$("div.add_dy > strong > input").val("");
					// 移除hidden
					input.remove();
					$("div.dy_ok").hide();
					$("div.add_dy").show();
				});
				$("div.add_dy").hide();
				$("div.dy_ok").show();
			}
		}
	});
});