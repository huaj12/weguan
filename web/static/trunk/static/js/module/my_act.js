function removeAct(a, pageId){
	var actId = $(a).attr("actid");
	var actName = $(a).attr("actname");
	if(actName == null || actName == '' || isNaN(actId)){
		return false;
	}
	$.dialog({
		icon: 'question',
		fixed: true,
		top:'50%',
		id: 'question_box',
		content: '确定不再想去 ' + actName + ' 么？',
		ok: function () {
				jQuery.ajax({
					url: "/app/ajax/removeAct",
					type: "post",
					cache : false,
					data: {"actId": actId},
					dataType: "json",
					success: function(result){
						if(result&&result.success){
							//移除内容
							pageMyAct(pageId);
							var count = $("#myActCnt").text();
							$("#myActCnt").text(count-1);
						}else{
							alert("system error.");
						}
					},
					statusCode: {
					    401: function() {
					    	window.location.href="/app/login";
					    }
					}
				});
				return true;
			},
		cancelVal: '取消',
		cancel: true 
	});
}

function pageMyAct(page){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageMyAct",
		type: "get",
		cache : false,
		data: {"page": page},
		dataType: "html",
		context: $(".box"),
		success: function(responseHTML){
			$(this).html(responseHTML);
			setHeight();
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}

$(document).ready(function(){
	// 注册推荐事件
	$("#_addMyActs").bind("click", function() {
		add_my_act();
	});
});

function add_my_act(){
	var value = $("#categoryAddAct").attr("value");
	if (!value || value == ""||value=='手动输入') {
		$("#categoryAddActError").html("<em>请先输入</em>").stop().show()
				.fadeOut(2000);
		return false;
	}
	if (!checkValLength(value, 2, 20)) {
		$("#categoryAddActError").html("<em>拒宅兴趣字数控制在1－10个中文内！</em>").stop().show().fadeOut(
				2000);
		
		return false;
	}
	jQuery.ajax({
		url: "/app/ajax/recommendAct",
		type: "get",
		cache : false,
		data: {"name":value},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$("#categoryAddAct").val('');
				$("#categoryAddActError").html("<em>感谢推荐!我们审核后会加入拒宅器</em>").stop().show().fadeOut(
						3000);
			}else{
				alert("system error.");
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/app/login";
		    }
		}
	});
}