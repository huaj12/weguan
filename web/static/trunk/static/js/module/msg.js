	function openmsg(curPage,curIndex,type,point){
		$.get('/msg/openMessage', {
			curPage:curPage,
			curIndex:curIndex,
			type:type,
		    random : Math.random()
		}, function(result) {
			if(result&&result.success){
				//成功
				location.href='/msg/showRead';
			}else{
				if(result.errorCode=="-1"){
					//余额不足
						$("#_ponit").html(point);
					$.dialog({
					    content: document.getElementById('pointDiv'),
					    top:"50%",
					    fixed: true,
					    lock: true,
					    title:"积分不足",
					    id: 'point_box'
					});
				}else{
					//未知错误请刷新页面后重试
					$.dialog({
					    content:'未知错误请刷新页面后重试',
					    id: 'point_box',
					    icon:'error'
					});
				}
			}
		});
	}
	function remove(curPage,curIndex,type,totalCount){
		$.dialog({
		    icon: 'question',
		    fixed: true,
		    top:'50%',
		    id: 'question_box',
		    content: '确定删除该条消息？',
		     ok: function () {
		    	 $.post('/msg/reMoveMessage', {
		 			curPage:curPage,
		 			curIndex:curIndex,
		 			type:type,
		 		    random : Math.random()
		 		}, function(data) {
		 			if(data!=null){
		 				if('unread'==type){
		 					$("#unReadContent").html(data);	
		 					$("#unReadCnt").html(totalCount-1);
		 				}else{
		 					$("#readContent").html(data);
		 					$("#readCnt").html(totalCount-1);
		 				}
		 				setHeight();
		 			}
		 		});
		    	        return true;
		    	  },
		   cancelVal: '取消',
		   cancel: true 
		});
		
	}
	function invite_app_friend(obj,curPage,curIndex,receiverId){
		var actIds="";
		$(".key_words").each(function(){
			var parent = $(this).parent();
			if(parent.hasClass("selected")){
				if(this.id!=null){
					actIds=actIds+this.id+",";
				}
			}
		});
		if(actIds==""){
			alert('请至少选择一个兴趣。');
			return ;
		}
		$.post('/msg/agreeMessage', {
			curPage:curPage,
			curIndex:curIndex,
			receiverId:receiverId,
			actIds:actIds,
		    random : Math.random()
		}, function(result) {
			if(result&&result.success){
				closeAllDiv();
				var tagerObj=document.getElementById("invite_btn_"+curIndex);
				tagerObj.outerHTML="<a class='unhover'>响应已发</a>";
			}else{
				//未知错误请刷新页面后重试
				$.dialog({
				    lock: true,
				    content: '未知错误请刷新页面后重试',
				    icon: 'error',
				    top:20
				});
			}
		});
	}
	function initinvite_Div(){
		closeAllDiv();
		$(".key_words").each(function(){
			var parent = $(this).parent();
			parent.removeClass("hover");
			parent.removeClass("selected");
		});
	}
	function invite_app_div(title,index){
		$.dialog({
		    content: document.getElementById('invite_app_div_'+index),
		    top:"50%",
		    title:title,
		});
	}
	
	function queryAll(title,index){
		$.dialog({
		    content: document.getElementById('interestBox_'+index),
		    top:"50%",
		    title:title,
		});
	}
	
	function moveoverItem(obj){
		obj.className="item hover";
	}
	function moveoutItem(obj){
		obj.className="item link";
	}
	function closeAllDiv(){
		var list = $.dialog.list;
		for (var i in list) {
		    list[i].close();
		};
	}
	
	
	function clickAct(obj){
		var parent = $(obj).parent();
		if(parent.hasClass("selected")){
			parent.removeClass("selected");
			$(obj).attr("title", "点击添加");
		}else {
			parent.addClass("selected");
			$(obj).attr("title", "点击取消");
		}
	}

	function hoverAct(obj, isOver){
		var parent = $(obj).parent();
		if(parent.hasClass("selected")){
			return;
		}
		if(!isOver){
			parent.removeClass("hover");
		}else {
			parent.addClass("hover");
		}
	}
	
	//让火狐支持outerHTML方法
	if(typeof(HTMLElement)!="undefined" && !window.opera)
	{
	HTMLElement.prototype.__defineGetter__("outerHTML",function()
	{
	var a=this.attributes, str="<"+this.tagName, i=0;for(;i<a.length;i++)
	if(a[i].specified)
	str+=" "+a[i].name+'="'+a[i].value+'"';
	if(!this.canHaveChildren)
	return str+" />";
	return str+">"+this.innerHTML+"</"+this.tagName+">";
	});
	HTMLElement.prototype.__defineSetter__("outerHTML",function(s)
	{
	var r = this.ownerDocument.createRange();
	r.setStartBefore(this);
	var df = r.createContextualFragment(s);
	this.parentNode.replaceChild(df, this);
	return s;
	});
	HTMLElement.prototype.__defineGetter__("canHaveChildren",function()
	{
	return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
	});
	} 