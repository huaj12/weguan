$(document).ready(function() {
	$("span.width250 >input").each(function(){
		registerInitMsg($(this));
	});

	$(".save").bind("click", function() {
		var preference_count=parseInt($("#preference_count").val());
		var filterPreference_count=parseInt($("#filterPreference_count").val());
		for(var i=0;i<(preference_count+filterPreference_count);i++){
			var preferenceId=$("#preferenceId_"+i).val();
			var type=$("#inputType_"+i).val();
			var preferenceType=$("#preferenceType_"+i).val();
			if(type==0){
				//不等于筛选是必填
				if(preferenceType==1){
					var flag=false;
					$('input[name="userPreferences['+i+'].answer"]').each(function(){
						if(this.checked){
							flag=true;
						}
					});
					if(!flag){
						$("#error_"+i).text("至少选择一个选项！").stop(true, true).show();
				        return ;
					}else{
						$("#error_"+i).text("").hide();
					}
				}
			}else if(type==2){
				var min=$("#minText_"+i).val();
				var max=$("#maxText_"+i).val();
				if(preferenceType==1||min!=""||max!=""){
					if(!isNum(min)||!isNum(max)){
						 $("#error_"+i).text("请输入数字！").stop(true, true).show();
					        return ;	
					}else{
						$("#error_"+i).text("").hide();
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
						 $("#error_"+i).text("请输入16-50之间的数字！").stop(true, true).show();
					        return ;	
					}else{
						$("#error_"+i).text("").hide();
					}
					$("#minText_"+i).val(min);
					$("#maxText_"+i).val(max);
				}
			}else if(type==3){
				if(getByteLen($('textarea[name="userPreferences['+i+'].answer"]').val())>100){
					 
					 $("#error_"+i).text("内容不能大于50个字").stop(true, true).show();
				        return ;	
				}else{
					$("#error_"+i).text("").hide();
				}
			}else if(type==1){
				//不等于筛选是必填
				if(preferenceType==1){
					var answer=$('select[name="userPreferences['+i+'].answer"]').val();
					if(answer==""){
						$("#error_"+i).text("至少选择一个选项！").stop(true, true).show();
				        return ;
					}else{
						$("#error_"+i).text("").hide();
					}
				}
			}
			var des=$('input[name="userPreferences['+i+'].description"]').val();
			if (undefined != des) {
				if(getByteLen(des)>100){
					 $("#error_"+i).text("描述内容不能大于50个字").stop(true, true).show();
				        return ;	
				}else{
					$("#error_"+i).text("").hide();
				}
				var initDes=$('input[name="userPreferences['+i+'].description"]').attr("init-tip");
				if(des==initDes){
					des="";
				}
				$('input[name="userPreferences['+i+'].description"]').val(des);
			}
			$("#answerDiv_"+i).html("");
			var obj=$("#answerDiv_"+i)[0];
			var preferenceIdInput = document.createElement("input");   
			preferenceIdInput.name="userPreferences["+i+"].preferenceId";
			preferenceIdInput.type="hidden";
			preferenceIdInput.value=preferenceId;
	        obj.appendChild(preferenceIdInput);
		}
		setPreference($("#preferenceForm").serialize(),
		function(){
			var content = $("#dialog-success").html().replace("{0}", "保存成功！");
			showSuccess(null, content);
			$("div.error").text("");
			$("span.width250 >input").each(function(){
				$(this).trigger("blur");;
			});
		},
		function(){
			alert(result.errorInfo);
			$("span.width250 >input").each(function(){
				$(this).trigger("blur");;
			});
		}
		);
		return false;
	});
	
});