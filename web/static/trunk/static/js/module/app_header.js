//TODO 封装不同第三方app
function showKxDialog(para)
{
	var t = document.createElement("div");
	t.innerHTML = '<iframe src="http://www.kaixin001.com/rest/rest.php?para='+para+'" scrolling="yes" height="0px"  width="0px" style="display:none"></iframe>';
	document.body.appendChild(t.firstChild);
}
function showSysnewsDialog(url)
{
	var t = document.createElement("div");
	t.innerHTML = '<iframe id="iframeinfo" src="'+ url +'" scrolling="no" height="344px"  width="547px" style="display:none;"></iframe>';
	document.body.appendChild(t.firstChild);
}
//TODO 封装不同第三方app
function kaixinFeed(name){
	jQuery.get('/kaixinFeed', {
		name:name,
	    random : Math.random()
	}, function(data) {
		showKxDialog(data);
	});
}
//TODO 封装不同第三方app
function kaixinRequest(name){
	jQuery.get('/kaixinRequest', {
		name:name,
	    random : Math.random()
	}, function(data) {
		showKxDialog(data);
	});
}
//TODO 
function dialogSysnews(link,linktext,text,pic){
	showSysnewsDialog('http://api.kaixin001.com/dialog/sysnews?display=iframe&linktext='+linktext+'&text='+text+'&link='+link+'&picurl='+pic+'&app_id=100012402&redirect_uri=http://www.kaixin001.com/!app_100012402114474624/&need_redirect=0');
}

function addAct(successCallback){
	var value = $("#addAct").attr("value");
	if(!checkValLength(value, 2, 20)){
		$("#addActError").text("拒宅兴趣字数控制在1－10个中文内！").show().fadeOut(2000);
		return false;
	}
	jQuery.ajax({
		url: "/app/ajax/addAct",
		type: "post",
		data: {"actName": value},
		dataType: "json",
		success: function(result){
			//result handle plugin
			if(result&&result.success){
				$("#addActError").text("保存成功").show().fadeOut(3000);
				$("#addAct").attr("value", "");
				if($("div.interesting").length > 0){
					pageMyAct(1);
				}
				if(successCallback){
					successCallback();
				}
			}else if(result){
				$("#addActError").text(result.errorInfo).show().fadeOut(2000);
			}else {
				alert("系统异常！");
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

function subEmail(){
	//validation
	var email = $("div.dy > span > input").attr("value");
	if(!checkEmail(email)){
		$("div.dy > div.error").text("邮箱格式有误！").show().fadeOut(2000);
		return false;
	}
	jQuery.ajax({
		url: "/app/ajax/subEmail",
		type: "post",
		data: {"email": email},
		dataType: "json",
		success: function(result){
			if(result&&result.success){
				$("div.dy > div.error").text("订阅成功").show().fadeOut(3000);
			}else if(result){
				$("div.dy > div.error").text(result.errorInfo).show().fadeOut(2000);
			}else {
				alert("系统异常！");
			}
		},
		statusCode: {
		    401: function() {
		      alert("未登录");
		    }
		}
	});
}

$(document).ready(function(){
	//注册添加Act事件
	$("div.zjxq_input > a.add").bind("click", function(){
		addAct();
	});
	//注册召集令
	$("div.zjxq_input > a.zhao").bind("click", function(){
		var value = $("#addAct").attr("value");
		if(value && value != ""){
			addAct(function(){
				kaixinFeed(value);
			});
		}else {
			kaixinFeed();
		}
	});
	//注册邀请
	$("div.zjxq_input > a.yao").bind("click", function(){
		var value = $("#addAct").attr("value");
		if(value && value != ""){
			addAct(function(){
				kaixinRequest(value);
			});
		}else {
			kaixinRequest();
		}
	});
	//注册订阅邮箱事件
	$("div.dy > a").bind("click", function(){
		subEmail();
	});
	//注册获取新消息数事件
	
	//注册autoComplete
	$("#addAct").autocomplete("/autoSearch", {
		dataType: "json",
		parse: function(datas) {
			var parsed = [];
			for (var i=0; i < datas.length; i++) {
				var data = datas[i];
				if (data) {
					parsed[parsed.length] = {
						data: data,
						value: data.name,
						result: data.name
					};
				}
			}
			return parsed;
		},
		formatItem: function(item) {
			return item.name;
		}
	});
	
	var proBoxNum = $("div.pro_box").size();
	if(proBoxNum > 1){
		var tipId = 1;
		setInterval(function(){
			$("div.tips_" + tipId).fadeOut(500, function(){
				tipId = tipId == proBoxNum ? 1 : (tipId+1);
				$("div.tips_" + tipId).fadeIn(500);
			});
		}, 5000);
	}
});