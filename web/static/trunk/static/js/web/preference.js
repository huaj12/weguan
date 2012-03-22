$(document).ready(function() {
	$(".save").bind("click", function() {
		var preference_count=$("#preference_count").val();
		for(var i=0;i<preference_count;i++){
			var preferenceId=$("#preferenceId_"+i).val();
			var type=$("#inputType_"+i).val();
			if(type==0){
				var flag=false;
				$('input[name="userPreferences['+i+'].answer"]').each(function(){
					if(this.checked){
						flag=true;
					}
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
					 $("#error_"+i).html("请输入数字！").stop(true, true).show()
						.fadeOut(4000);
				        return ;	
				}
				min=parseInt(min);
				max=parseInt(max);
				if(max<min){
					var t=0;
					t=max;
					max=min;
					min=t;
				}
				if(min<16||max>50){
					 $("#error_"+i).html("请输入18-50之间的数字！").stop(true, true).show()
						.fadeOut(4000);
				        return ;	
				}
				$("#minText_"+i).val(min);
				$("#maxText_"+i).val(max);
			}
			$("#answerDiv_"+i).html("");
			var obj=$("#answerDiv_"+i)[0];
			var preferenceIdInput = document.createElement("input");   
			preferenceIdInput.name="userPreferences["+i+"].preferenceId";
			preferenceIdInput.type="hidden";
			preferenceIdInput.value=preferenceId;
	        obj.appendChild(preferenceIdInput);
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

