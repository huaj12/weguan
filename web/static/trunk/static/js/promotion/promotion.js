		var count=0;
		var content="";
$(document).ready(function() {
	$("div.sen2_btn >a").bind("click", function() {
		$("#step1").hide();
		$("#step2").show();
		jQuery.ajax({
			url : "/occasional/ajax/send",
			type : "get",
			dataType : "html",
			success : function(result) {
				content=result;
			},
			statusCode : {
				401 : function() {
					alert("系统异常");
				}
			}
		});
		dynamic();
	});
});		
			function dynamic(){ 
				count++;
				loadText(count);
					if(count<6){
					 setTimeout(function(){dynamic();},1000);
					} 
			}
			function loadText(index){
						for(var i=1;i<6;i++){
							$("#loading_text"+i).hide();
						}
						$("#loading_text"+index).show();
						if(index==6){
							while(true){
								if(content!=""){
									break;
								}
							}
							$("#step2").html(content);
						}
			}
			