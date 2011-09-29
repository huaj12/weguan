	function openmsg(curPage,curIndex){
		$.get('/msg/openMessage', {
			curPage:curPage,
			curIndex:curIndex,
		    random : Math.random()
		}, function(data) {
			if(data==1){
				//成功
				location.href='/msg/showRead';
			}else if(data==0){
				//余额不足
				$.dialog({
				    content: document.getElementById('pointDiv'),
				    top:"5%",
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
		});
	}
	function remove(curPage,curIndex,type){
		$.post('/msg/reMoveMessage', {
			curPage:curPage,
			curIndex:curIndex,
			type:type,
		    random : Math.random()
		}, function(data) {
			location.reload();
		});
	}
	function invite_app_friend(obj,curPage,curIndex,actId,receiverId){
		$.post('/msg/agreeMessage', {
			curPage:curPage,
			curIndex:curIndex,
			receiverId:receiverId,
			actId:actId,
		    random : Math.random()
		}, function(data) {
			if(data==1){
				obj.className="unhover";
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
	
	function closeAllDiv(){
		var list = $.dialog.list;
		for (var i in list) {
		    list[i].close();
		};
	}