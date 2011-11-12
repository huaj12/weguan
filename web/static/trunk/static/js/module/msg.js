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
						lock: true,
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
			lock: true,
		    content: document.getElementById('invite_app_div_'+index),
		    top:"50%",
		    title:title
		});
	}
	
	function queryAll(title,index){
		$.dialog({
			lock: true,
		    content: document.getElementById('interestBox_'+index),
		    top:"50%",
		    title:title
		});
	}
	
	function moveoverItem(obj){
		$(obj).removeClass("item link").addClass("item hover");
	}
	function moveoutItem(obj){
		$(obj).removeClass("item hover").addClass("item link");
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
	
	function showBoard(name,str,fid){
		clearBoard();
		var content="你最近何时有空啊？想不想一起去"+str+"?(来自拒宅器)";
		$("#board_content").val(content);
		$("#board_fid").val(fid);
		$("#board_name").val(name);
		$.dialog({
		    lock: true,
		    content:document.getElementById('boardDiv'),
		    title:"联系"+name,
		    top:"50%"
		});
	}
	
	function showAbout(name,actId,actName,fid){
		clearAbout();
		var content="";
		if(actName!=null&&actName.length>0){
			content="hi，想不想一起去"+actName+"?";
		}else{
			content="hi，想不想一起出去玩？";
		}
		$("#about_content").val(content);
		$("#about_fid").val(fid);
		$("#about_actId").val(actId);
		$("#about_name").val(name);
		$.dialog({
		    lock: true,
		    content:document.getElementById('aboutDiv'),
		    title:"给"+name+"留言",
		    top:"50%"
		});
	}
	function clearAbout(){
		$("#about_content").val('');
		$("#about_fid").val('');
		$("#about_actId").val('');
		$("#about_name").val('');
		
	}
	function sendAbout(){
		var content=$("#about_content").val();
		var fuid=$("#about_fid").val();
		var name=$("#about_name").val();
		var actId=$("#about_actId").val();
		if(content.length>30){
			alert("不要超过30个字哦");
			return ;
		}
		$.post('/msg/sendAbout', {
			content:content,
			fuid:fuid,
			actId:actId,
		    random : Math.random()
		}, function(result) {
			if(result&&result.success){
				closeAllDiv();
				$.dialog({
					lock: true,
				    content: '<div>发送成功!</br>等待他的回复吧</div>',
				    top:"50%",
				    width:305,
				    time:2,
				    title:'给'+name+'留言'
				});
			}else{
				closeAllDiv();
				//未知错误请刷新页面后重试
				$.dialog({
				    lock: true,
				    content: '刷新页面后重试',
				    icon: 'error',
				    time:3,
				    top:"50%"
				    	
				});
			}
		});
	}
	
	
	
	function clearBoard(){
		$("#board_content").val('');
		$("#board_fid").val('');
		$("#board_name").val('');
	}
	function sendBoard(){
		var content=$("#board_content").val();
		var fids=$("#board_fid").val();
		var name=$("#board_name").val();
		$.post('/msg/sendBoard', {
			content:content,
			fids:fids,
		    random : Math.random()
		}, function(result) {
			if(result&&result.success){
				closeAllDiv();
				$.dialog({
					lock: true,
				    content: '<div>发送成功!</br>等待他的回复吧</div>',
				    top:"50%",
				    width:305,
				    time:2,
				    title:'联系'+name
				});
			}else{
				closeAllDiv();
				//未知错误请刷新页面后重试
				$.dialog({
				    lock: true,
				    content: '查看ta是否是您的好友或刷新页面后重试',
				    icon: 'error',
				    time:3,
				    top:"50%"
				    	
				});
			}
		});
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