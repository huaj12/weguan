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
	function invite_app_friend(obj,curPage,curIndex,actId,receiverId){
		$.post('/msg/agreeMessage', {
			curPage:curPage,
			curIndex:curIndex,
			receiverId:receiverId,
			actId:actId,
		    random : Math.random()
		}, function(result) {
			if(result&&result.success){
				obj.className="unhover";
				obj.innerHTML="响应已发";
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