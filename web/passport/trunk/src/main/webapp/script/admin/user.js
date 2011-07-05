Ext.BLANK_IMAGE_URL = '/style/ext/resources/images/default/s.gif';
Ext.onReady(function() {
	function renderEmailAddress(value, cellmeta, record, rowIndex, columnIndex,
			store) {
		return "<a href='mailto:" + value + "'>" + value + "</a>";
	}
	function renderLoginName(value, cellmeta, record, rowIndex, columnIndex,
			store) {
		return "<a href='" + record.data.pageLink + "'>" + value + "</a>";
	}
	function renderTypeName(value, cellmeta, record, rowIndex, columnIndex,
			store) {
		if (value == 'text')
			return '日志';
		else if (value == 'link')
			return '链接';
		else if (value == 'image')
			return '图片';
		else if (value == 'quote')
			return '摘录';
		else if (value == 'video')
			return '视频';
		else if (value == 'music')
			return '音乐';
	}
	var userModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
		header : '用户名',
		dataIndex : 'loginName',
		width : 100,
		renderer : renderLoginName
	}, {
		header : '邮箱地址',
		dataIndex : 'emailAddress',
		width : 200,
		renderer : renderEmailAddress
	}, {
		header : '注册时间',
		dataIndex : 'createdDateTime',
		width : 150,
		sortable : true
	}, {
		header : '最后登录时间',
		dataIndex : 'lastLoginDateTime',
		width : 150,
		sortable : true
	}]);

	var userStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'userList.do'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'totalProperty',
			root : 'root'
		}, [{
			name : 'loginName'
		}, {
			name : 'createdDateTime'
		}, {
			name : 'pageLink'
		}, {
			name : 'emailAddress'
		}, {
			name : 'lastLoginDateTime'
		}])
	});

	var userPanel = new Ext.grid.GridPanel({
		id : 'users',
		title : '用户信息',
		ds : userStore,
		cm : userModel,
		// height : 600,
		// region : 'center',
		// columnWidth : 0.8,
		tbar : new Ext.PagingToolbar({
			pageSize : 25,
			store : userStore,
			displayInfo : true,
			displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg : "没有记录"
		})
	});

	userStore.load({
		params : {
			start : 0,
			limit : 25
		}
	});

	var stuffModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
		header : '类型',
		dataIndex : 'typeName',
		width : 100,
		renderer : renderTypeName
	}, {
		header : '总数',
		dataIndex : 'totalCount',
		width : 100
	}, {
		header : '转贴数',
		dataIndex : 'repostCount',
		width : 100
	}, {
		header : '当日增加数',
		dataIndex : 'addCount',
		width : 100
	}, {
		header : '平均每天发布数',
		dataIndex : 'averageCount',
		width : 100
	}]);

	var stuffStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'stuffList.do'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'totalProperty',
			root : 'root'
		}, [{
			name : 'typeName'
		}, {
			name : 'totalCount'
		}, {
			name : 'repostCount'
		}, {
			name : 'addCount'
		}, {
			name : 'averageCount'
		}])
	});
	var stuffPanel = new Ext.grid.GridPanel({
		id : 'stuffs',
		title : '网站信息',
		ds : stuffStore,
		cm : stuffModel,
		closable : true
	});

	function renderStuffViewTitle(value, b, record) {
		if (value == '') {
			value = '无标题';
		}
		return "<a href='" + record.data.pageLink + "' target='_blank'>"
				+ value + "</a>"
	}
	var stuffViewModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
			{
				header : '类型',
				dataIndex : 'typeName',
				width : 50,
				renderer : renderTypeName
			}, {
				header : '标题',
				dataIndex : 'title',
				width : 500,
				renderer : renderStuffViewTitle
			}, {
				header : '作者',
				dataIndex : 'author',
				width : 100
			}, {
				header : '创建时间',
				dataIndex : 'createdDateTime',
				width : 100
			}]);

	var stuffViewStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'stuffViewList.do'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'totalProperty',
			root : 'root'
		}, [{
			name : 'typeName'
		}, {
			name : 'title'
		}, {
			name : 'author'
		}, {
			name : 'createdDateTime'
		}, {
			name : 'pageLink'
		}])
	});
	var stuffViewPanel = new Ext.grid.GridPanel({
		id : 'stuffViews',
		title : 'stuff信息',
		ds : stuffViewStore,
		cm : stuffViewModel,
		closable : true,
		tbar : new Ext.PagingToolbar({
			pageSize : 25,
			store : stuffViewStore,
			displayInfo : true,
			displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg : "没有记录"
		})
	});

	var root = new Ext.tree.AsyncTreeNode({
		text : '网站信息'
	});

	var infoPanel = new Ext.tree.TreePanel({
		id : 'info',
		root : root,
		autoWidth : true,
		autoScroll : true,
		rootVisible : false,
		loader : new Ext.tree.TreeLoader({
			dataUrl : 'info.txt'
		})
	});

	root.expand();

	var tabPanel = new Ext.TabPanel({
		id : 'tab',
		region : 'center',
		items : [userPanel],
		activeTab : 'users',
		// autoShow : false,
		margins : '0 0 5 0'
	});

	var stuffInit = false;
	var stuffViewInit = false;
	infoPanel.on('click', function(node) {
		if (node.id == 'user') {
			tabPanel.add(userPanel).show();
		} else if (node.id == 'stuff') {
			if (!stuffInit) {
				stuffStore.load();
				stuffInit = true;
			}
			tabPanel.add(stuffPanel).show();
		} else if (node.id == 'stuffView') {
			if (!stuffViewInit) {
				stuffViewStore.load({
					params : {
						start : 0,
						limit : 25
					}
				});
				stuffViewInit = true;
			}
			tabPanel.add(stuffViewPanel).show();
		}
	});

	new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '信息',
			region : 'west',
			split : true,
			border : true,
			collapsible : true,
			width : 150,
			maxSize : 200,
			minSize : 120,
			items : [infoPanel],
			margins : '0 0 5 0'
		}, tabPanel, {
			html : "<a href='http://www.weguan.com' target='_blank'>www.weguan.com</a>",
			region : 'south',
			style : 'text-align:center',
			border : false,
			height : 25
		}]
	});

	/*
	 * var gridPanel = new Ext.Panel({ title : '用户', id : 'usr', layout :
	 * 'border', items : [{ region : 'center', layout : 'column', xtype :
	 * 'panel', style : 'margin-bottom: 10px', border : false, items : [grid, {
	 * columnWidth : .2, height : 350, title : 'new', xtype : 'panel', html : '<h3>ddd</h3>' }] }, {
	 * region : 'south', layout : 'column', xtype : 'panel', height : 170,
	 * border : false, items : [{ columnWidth : .8, title : 'new2', xtype :
	 * 'panel', style : 'margin-right: 10px', height : 170, html : '<h3>ddd2</h3>' }, {
	 * columnWidth : .2, title : '123', xtype : 'panel', height : 170, width:
	 * 100 }] }, { region : 'north', xtype : 'toolbar', items : [{ text : '文件',
	 * menu : { items : [{ text : '打开' }, { text : '保存' }] } }, { text : '帮助',
	 * menu : { items : [{ text : '打开' }, { text : '保存' }] } }, '-', { cls :
	 * 'x-btn-icon', icon: '/style/ext/resources/images/default/tree/leaf.gif' }, {
	 * cls : 'x-btn-icon', icon:
	 * '/style/ext/resources/images/default/tree/folder.gif' }, { cls :
	 * 'x-btn-icon', icon:
	 * '/style/ext/resources/images/default/tree/folder-open.gif' }] }] });
	 * 
	 * var tabPanel = new Ext.TabPanel({ id : 'users', region : 'center', items :
	 * [gridPanel,{title:'good',xtype:'panel',html:'hello,good'}], activeTab :
	 * 'usr', autoShow : true });
	 * 
	 * var root = new Ext.tree.AsyncTreeNode({ text : 'dd' });
	 * 
	 * var tree = new Ext.tree.TreePanel({ id : 'left', root : root, autoWidth :
	 * true, autoHeight : true, autoScroll : true, loader : new
	 * Ext.tree.TreeLoader({ dataUrl : '03-03.txt' }), containerScroll : true
	 * 
	 * }); tree.on('click', function(node) { if (node.text == '用户') {
	 * tabPanel.add({ id : 'newPanel', title : 'new', xtype : 'panel', html : '<h3>ddd</h3>',
	 * closable : true }).show(); } })
	 * 
	 * var viewport = new Ext.Viewport({ layout : 'border', items : [{ title :
	 * '后台选项', region : 'west', split : true, border : true, collapsible : true,
	 * width : 150, maxSize : 150, minSize : 100, items : [tree] }, tabPanel]
	 * }); root.expand();
	 */
})