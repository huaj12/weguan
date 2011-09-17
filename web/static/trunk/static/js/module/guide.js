function clickAct(obj){
	var parent = $(obj).parent();
	if(parent.hasClass("hover")){
		parent.removeClass("hover");
	}else {
		parent.addClass("hover");
	}
}

$(document).ready(function(){
	$(".key_words").each(function(){
		$(this).bind("click", function(){
			clickAct(this);
		});
	});
	
	$("a.add").bind("click", function(){
		var actNameInput = $("#actName");
		var value = actNameInput.attr("value");
		if(!checkValLength(value, 2, 20)){
			//window.setTimeout(function(){
			//	$("#actNameError").addClass("hidden");
			//	clearTimeout(this);
			//},2000);
			$("#actNameError").show().fadeOut(2000);
			return false;
		}
		var p = $("<p class='hover'><span class='fl'></span></p>");
		var a = $("<a href='#' class='key_words'></a>");
		a.html(value);
		a.attr("actName", value);
		a.bind("click", function(){
			clickAct(a);
		});
		p.append(a);
		p.append("<span class='fr'></span><em></em>");
		$("#acts").append(p);
		
		actNameInput.attr("value","");
	});
	
	$("#nextForm").submit(function(){
		$("p.hover > a").each(function(){
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
		return true;
	});
	
	$(".next_btn > a").bind("click", function(){
		$("#nextForm").submit();
	});
});