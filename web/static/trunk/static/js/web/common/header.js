			function showLogin(){
				$.dialog({
					content:$("#_loginDiv")[0],
					top:"50%",
					fixed: true,
					lock: true,
					title:"加入拒宅",
					id: 'login_div_box',
					padding: 0
				});
			}
