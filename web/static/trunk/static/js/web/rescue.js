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
		var rescueUid = $(this).attr("rescue-uid");
		changeRescueUser(rescueUid);
		return false;
	});
	
	$("a.xy").click(function(){
		var postId = $(this).attr("post-id");
		var respCount = $(this).attr("resp-count");
		var rescueUid = $(this).attr("rescue-uid");
		var obj = this;
		openResponse(obj, postId, respCount, function(){
			changeRescueUser(rescueUid);
		});
		return false;
	});
});

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
						var rescueUid = $(this).attr("rescue-uid");
						changeRescueUser(rescueUid);
						return false;
					});
					$("div.jj_mid > div").find("a.xy").click(function(){
						var postId = $(this).attr("post-id");
						var respCount = $(this).attr("resp-count");
						var rescueUid = $(this).attr("rescue-uid");
						openResponse(postId, respCount, function(){
							changeRescueUser(rescueUid);
						});
						return false;
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