$(document).ready(function() {
	var content = "";
	$("div.sen2_btn >a").bind("click", function() {
		$("#step1").hide();
		$("#step2").show();
		jQuery.ajax({
			url : "/occasional/ajax/send",
			type : "get",
			dataType : "html",
			success : function(result) {
				content = result;
			},
			statusCode : {
				401 : function() {
					alert("系统异常");
				}
			}
		});
		dynamic();
	});
	
	var count = 0;
	function dynamic(){ 
		count++;
		loadText(count);
		if(count < 6){
			setTimeout(dynamic, 1000);
		} else {
			while(true) {
				if(content != ""){
					$("#step2").html(content);
					break;
				}
			}
		}
	}
	function loadText(index){
		if(index > 1){
			$("#loading_text" + (index-1)).hide();
		}
		$("#loading_text"+index).show();
	}
});		
			
			