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
					    	window.location.href="/login";
					    }
					}
				});
				return true;
			},
		cancelVal: '取消',
		cancel: true 
	});
}

function addCategoryAct(a){
	var actId = $(a).attr("actid");
	if(isNaN(actId)){
		return false;
	}
	//ajax
	jQuery.ajax({
		url: "/app/ajax/addAct",
		type: "post",
		cache : false,
		data: {"actId": actId},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$(a).removeAttr("onclick");
				$(a).text("已添加");
				$(a).addClass("unclick");
			}else{
				alert("system error.");
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
		    }
		}
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
		    	window.location.href="/login";
		    }
		}
	});
}

function switchCategory(a, categoryId){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageCategoryAct",
		type: "get",
		cache : false,
		data: {"categoryId": categoryId},
		dataType: "html",
		context: $(".i_w_g"),
		success: function(responseHTML){
			changeCategoryTab($(a).parent());
			$(this).html(responseHTML);
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
		    }
		}
	});
}

function changeCategoryTab(span){
	$(".ca > span").each(function(){
		$(this).removeClass("hover");
	});
	$(span).addClass("hover");
}

function pageCategoryAct(categoryId, pageId){
	//ajax
	jQuery.ajax({
		url: "/app/ajax/pageCategoryAct",
		type: "get",
		cache : false,
		data: {"categoryId": categoryId, "page": pageId},
		dataType: "html",
		context: $(".i_w_g"),
		success: function(responseHTML){
			$(this).html(responseHTML);
			setHeight();
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
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
		$("#categoryAddActError").text("请先输入").stop(true, true).show()
				.fadeOut(2000);
		return false;
	}
	if (!checkValLength(value, 2, 20)) {
		$("#categoryAddActError").text("拒宅兴趣字数控制在1－10个中文内！").stop().show().fadeOut(
				2000);
		return false;
	}
	jQuery.ajax({
		url: "/app//ajax/recommendAct",
		type: "get",
		cache : false,
		data: {"name":value},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				 $("#categoryAddAct").val('');
				$("#categoryAddActError").text("感谢推荐!我们审核后会加入拒宅器").stop().show().fadeOut(
						3000);
			}else{
				alert("system error.");
			}
		},
		statusCode: {
		    401: function() {
		    	window.location.href="/login";
		    }
		}
	});
}