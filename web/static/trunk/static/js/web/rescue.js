$(document).ready(function(){
	
	$("a.kw").click(function(){
		var kwShowDiv = $("div.kw_show").show();
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(kwShowDiv[0]).length <= 0){
				kwShowDiv.hide();
			}
		});
		return false;
	});
	
	$("a.jj").click(function(){
		var jjShowDiv = $("div.jj_show").show();
		$("body").bind("mousedown",function(event){
			if($(event.target).closest(jjShowDiv[0]).length <= 0){
				jjShowDiv.hide();
			}
		});
		return false;
	});
	
	$("a.next").click(function(){
		next(this);
	});
	
	$("a.xy").click(function(){
		responseClick(this);
	});
	
	$("div.user-remove-interest > a.done").bind("click", function() {
		var uid = $(this).attr("uid");
		removeInterestConfirm(uid, this, function(){
			$("div.remove-interest-" + uid).hide();
			$("div.interest-" + uid).attr("style", "");
		});
		return false;
	});
	$("div.user-add-interest > a").bind("click", function() {
		var uid = $(this).attr("uid");
		interest(this, uid, function(){
			$("div.interest-" + uid).hide();
			$("div.remove-interest-" + uid).attr("style", "");
		});
		return false;
	});
});

function next(btn){
	var rescueUid = $(btn).attr("rescue-uid");
	$(btn).unbind("click").attr("class", "loading").text("加载中");
	changeRescueUser(rescueUid);
	return false;
}

function responseClick(btn){
	var postId = $(btn).attr("post-id");
	var rescueUid = $(btn).attr("rescue-uid");
	var nickname = $(btn).attr("nickname");
	var postContent = $(btn).attr("post-content");
	openResponse(btn, postId, nickname, postContent, function(){
		$(btn).unbind("click");
		changeRescueUser(rescueUid);
	});
	return false;
}

function changeRescueUser(rescueUid){
	$.ajax({
		url : "/changerescueuser",
		type : "post",
		cache : false,
		data : {"rescueUid" : rescueUid},
		dataType : "html",
		success : function(result) {
			$("div.jj_mid > div").fadeOut(500, function(){
				$("div.jj_mid > div").html(result).fadeIn(500, function(){
					$("div.jj_mid > div").find("a.next").click(function(){
						next(this);
					});
					$("div.jj_mid > div").find("a.xy").click(function(){
						responseClick(this);
					});
				});
			});
		},
		statusCode : {
			401 : function() {
				window.location.href = "/login?turnTo=" + window.location.href;
			}
		}
	});
}