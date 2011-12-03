	function showAbout(name,actId,actName,fid){
		clearAbout();
		var isWeibo=false;
		if($ ("#isWeibo").length>0){
			isWeibo=true;
		}
		var content="";
		if(actName!=null&&actName.length>0){
			if(isWeibo){
				content="hi，想不想一起去 "+actName+" ?";
			}else{
				content="hi，想不想一起去"+actName+"?";
			}
		}else{
			content="hi，想不想一起出去玩？";
		}
		$("#about_content").val(content);
		$("#about_fid").val(fid);
		$("#about_actId").val(actId);
		$("#about_name").val(name);
		$.dialog({
		    lock: true,
		    content:$("#aboutDiv")[0],
		    title:"给"+name+"留言",
		    top:"50%"
		});
	}
	
	function clearAbout(){
		show_btn('about');
		var isWeibo=false;
		if($ ("#isWeibo").length>0){
			isWeibo=true;
		}
		if(isWeibo){
			$("#type_comment").attr('checked',true);
			$("#type_weibo").attr('checked',false);
		}
		$("#about_content").val('');
		$("#about_fid").val('');
		$("#about_actId").val('');
		$("#about_name").val('');
		
	}
	
	function sendAbout(){
		var isWeibo=false;
		var type_comment="";
		var type_weibo="";
		if($ ("#isWeibo").length>0){
			if($("#type_comment").is(':checked')){
				type_comment=$("#type_comment").val();
			}
			if($("#type_weibo").is(':checked')){
				type_weibo=$("#type_weibo").val();
			}
			isWeibo=true;
		}
		if(isWeibo){
			if(type_comment==""&&type_weibo==""){
				alert("请选择通过评论发布还是微博发布。");
				show_btn('about');
				return;
			}
		}
		var content=$("#about_content").val();
		var fuid=$("#about_fid").val();
		var name=$("#about_name").val();
		var actId=$("#about_actId").val();
		if(trimStr(content).length==0){
			alert("给你的好友留点言吧！");
			show_btn('about');
			return ;
		}
		hide_btn('about');
		$.post('/msg/sendAbout', {
			content:content,
			fuid:fuid,
			actId:actId,
			typeComment:type_comment,
			typeWeibo:type_weibo,
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
	
	
