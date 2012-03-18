$(document).ready(function() {
	$(".save").bind("click", function() {
		var preference_count=$("#preference_count").val();
		for(var i=0;i<preference_count;i++){
			var preferenceId=$("#preferenceId_"+i).val();
			var type=$("#inputType_"+i).val();
			var answer="";
			if(type==0){
				var flag=false;
				$('input[name=inputBoxs_'+i+']').each(function(){
					var v="";
					if(this.checked){
						flag=true;
						v=this.value;
					}
					answer=answer+v+",";
				});
				if(!flag){
					$("#error_"+i).html("至少选择一个选项！").stop(true, true).show()
					.fadeOut(4000);
			        return ;
				}
			}else if(type==2){
				var min=$("#minText_"+i).val();
				var max=$("#maxText_"+i).val();
				if(!isNum(min)||!isNum(max)){
					 $("#error_"+i).html("请输入0-100数字！").stop(true, true).show()
						.fadeOut(4000);
				        return ;
				}
				answer=min+","+max;
			}
			$("#answerDiv_"+i).html("");
			var obj=$("#answerDiv_"+i)[0];
			var preferenceIdInput = document.createElement("input");   
			preferenceIdInput.name="userPreferences["+i+"].preferenceId";
			preferenceIdInput.type="hidden";
			preferenceIdInput.value=preferenceId;
	        obj.appendChild(preferenceIdInput);
	        var answerInput = document.createElement("input");   
	        answerInput.name="userPreferences["+i+"].answer";
	        answerInput.type="hidden";
	        answerInput.value=answer;
	        obj.appendChild(answerInput);
		}
		jQuery.ajax({
			url: "/profile/preference/save",
			type: "post",
			data:  $("#preferenceForm").serialize(),
			dataType: "json",
			success: function(result){
				if(result&&result.success){
					var content = $("#dialog-success").html().replace("{0}", "保存成功！");
					showSuccess(null, content);
				}else{
					alert(result.errorInfo);
				}
			},
			statusCode: {
			    401: function() {
			    	window.location.href = "/login?turnTo=" + window.location.href;
			    }
			}
		});
		return false;
	});
	
});

function   isNum(a)
{
    var   type="^[0-9]{1,2}$"; 
    var   re   =   new   RegExp(type); 
    if(a.match(re)==null) 
    { 
	    return false;
    } 
    return true;
}