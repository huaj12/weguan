var Pager = new Class( {
	initialize : function(id, url, itemCount, maxPageItems, maxIndexPages,
			pageOffset, options) {
		this.options = $extend( {
			evalScript : true,
			callback : {
				onPageChange : function(pagerObject) {
				}
			},
			delimiter : ' ',
			indexMask : Pager.MASK_TOTAL_COUNT | Pager.MASK_FIRST
					| Pager.MASK_PREV | Pager.MASK_NEXT | Pager.MASK_LAST
					| Pager.MASK_INDEX,
			anchor : false,
			parameters : false,
			cssNames : {
				disabled : 'pager_disabled',
				enabled : 'pager_enabled',
				enabledActive : 'pager_enabled_active',
				enabledCurrent : 'pager_cur_page'
			}
		}, options || {});

		if (this.options.anchor) {
			this.setAnchorName(this.options.anchor);
		}

		this.parameters = [];
		if (this.options.parameters) {
			for (var i in this.options.parameters) {
				this.addParameter(i, this.options.parameters[i]);
			}
		}

		this.id = id;
		this.url = url;
		this.itemCount = itemCount;
		this.maxPageItems = maxPageItems;
		this.maxIndexPages = maxIndexPages;
		this.pageOffset = pageOffset;
		this.currentPageNumber = pageOffset ? pageOffset / maxPageItems + 1 : 1;
		this.totalPages = Math.ceil(itemCount / maxPageItems);
		this.pagerId = null;
		this.contentId = null;

		// 向前兼容
		this.MASK_TOTAL_COUNT = 1;
		this.MASK_FIRST = 1 << 1;
		this.MASK_PREV = 1 << 2;
		this.MASK_NEXT = 1 << 3;
		this.MASK_LAST = 1 << 4;
		this.MASK_JUMP = 1 << 5;
		this.MASK_INDEX = 1 << 6;
	}
});
// 向前兼容
pintang.Pager = Pager;

Pager.MASK_TOTAL_COUNT = 1;
Pager.MASK_FIRST = 1 << 1;
Pager.MASK_PREV = 1 << 2;
Pager.MASK_NEXT = 1 << 3;
Pager.MASK_LAST = 1 << 4;
Pager.MASK_JUMP = 1 << 5;
Pager.MASK_INDEX = 1 << 6;

Pager.implement( {
	addParameter : function(name, value) {
		this.parameters.push([name, value]);
	},
	setDelimiter : function(delim) {
		this.options.delimiter = delim;
		if (this.pagerId && this.contentId)
			this._refresh();
	},
	setIndexMask : function(mask) {
		this.options.indexMask = mask;
		if (this.pagerId && this.contentId)
			this._refresh();
	},
	setAnchorName : function(anchorName) {
		this.options.anchor = document.anchors[anchorName];
	},
	showPager : function(cId, pId) {
		this.contentId = cId;
		this.pagerId = pId;
		this._refresh();
	},
	setCurrentPage : function(pageNumber, scroll) {
		if (this.currentPageNumber != pageNumber) {
			this._setPage(pageNumber, scroll);
		}
	},
	getCurrentPageOffset : function() {
		return (this.currentPageNumber - 1) * this.maxPageItems;
	},
	getPageOffsetParameterName : function() {
		return this.id + ".offset";
	},
	getItemCount : function() {
		return this.itemCount;
	},
	refresh : function(pageOffset, itemCount) {
		this.currentPageNumber = pageOffset / this.maxPageItems + 1;
		this.itemCount = itemCount;
		this._refresh();
	},
	refreshCurrentPage : function(scroll) {
		this._setPage(this.currentPageNumber, scroll);
	}
});

Pager.implement(new Events);

Pager.implement( {
	_refresh : function() {
		if (this.totalPages < 2) {
			return;
		}
		var cell = document.createElement("span");
		var needFirstDelimiter = false;
		if (this.options.indexMask & Pager.MASK_FIRST) {
			this._createFirst(cell);
			needFirstDelimiter = true;
		}
		if (this.options.indexMask & Pager.MASK_PREV) {
			if (needFirstDelimiter)
				this._createTextSpan(cell, this.options.delimiter);
			this._createPrev(cell);
			needFirstDelimiter = true;
		}
		if (this.options.indexMask & Pager.MASK_JUMP) {
			if (needFirstDelimiter)
				this._createTextSpan(cell, this.options.delimiter);
			this._createJump(cell);
			needFirstDelimiter = true;
		}
		if (this.options.indexMask & Pager.MASK_INDEX) {
			if (needFirstDelimiter)
				this._createTextSpan(cell, this.options.delimiter);
			this._createIndexs(cell);
			needFirstDelimiter = true;
		}
		if (this.options.indexMask & Pager.MASK_NEXT) {
			if (needFirstDelimiter)
				this._createTextSpan(cell, this.options.delimiter);
			this._createNext(cell);
			needFirstDelimiter = true;
		}
		if (this.options.indexMask & Pager.MASK_LAST) {
			if (needFirstDelimiter)
				this._createTextSpan(cell, this.options.delimiter);
			this._createLast(cell);
			needFirstDelimiter = true;
		}
		if (this.options.indexMask & Pager.MASK_TOTAL_COUNT) {
			if (needFirstDelimiter)
				this._createTextSpan(cell, this.options.delimiter);
			this._createTotalCount(cell);
			needFirstDelimiter = true;
		}
		var target = $(this.pagerId);
		target.setHTML('');
		target.appendChild(cell);
	},
	_setPage : function(pageNumber, scroll) {
		this.currentPageNumber = pageNumber;
		var thisObject = this;
		var csiObj = new ClientSideInclude(this.url, this.contentId, "POST", {
			doAfterRequest : function() {
				thisObject._refresh();
				if (scroll) {
					if (thisObject.options.anchor) {
						thisObject.options.anchor.focus();
					} else if (document.body.scrollIntoView) {
						document.body.scrollIntoView();
					} else {
						document.body.firstChild.scrollIntoView();
					}
				}
				thisObject.options.callback.onPageChange(thisObject);
			}
		});
		this.parameters.each(function(parameter) {
			csiObj.addParameter(parameter[0], parameter[1]);
		});
		csiObj.addParameter(this.id + ".offset", (this.currentPageNumber - 1)
				* this.maxPageItems);
		csiObj.csi(this.options.evalScript);
	},
	_createPageIndex : function(text, pageNumber, curPage) {
		var node = null;
		if (pageNumber == this.currentPageNumber
				|| pageNumber > this.totalPages || pageNumber <= 0) {
			node = document.createElement("span");
			node.className = curPage
					? this.options.cssNames.enabledCurrent
					: this.options.cssNames.disabled;
		} else {
			node = document.createElement("a");
			node.href = 'javascript:;';
			node.className = this.options.cssNames.enabled;
			var thisObject = this;
			node.onmouseover = function() {
				node.className = thisObject.options.cssNames.enabledActive;
			}
			node.onmouseout = function() {
				node.className = thisObject.options.cssNames.enabled;
			}
			node.onclick = function() {
				thisObject._setPage(pageNumber, true);
			}
		}
		node.appendChild(document.createTextNode(text));
		return node;
	},
	_createTextSpan : function(parent, text) {
		var spanBody = document.createElement("span");
		spanBody.className = this.options.cssNames.disabled;
		var textNode = document.createTextNode(text);
		spanBody.appendChild(textNode);
		parent.appendChild(spanBody);
	},
	_createTotalCount : function(parent) {
		this._createTextSpan(parent, "每页" + this.maxPageItems + "条/共"
				+ this.totalPages + "页 ");
	},
	_createFirst : function(parent) {
		parent.appendChild(this._createPageIndex("第一页", 1));
	},
	_createPrev : function(parent) {
		parent.appendChild(this._createPageIndex("上一页",
				this.currentPageNumber > 1 ? this.currentPageNumber - 1 : 1));
	},
	_createIndexs : function(parent) {
		var middleIndex = Math.floor(this.maxIndexPages / 2);
		var startIndex = 1;
		if (this.currentPageNumber > middleIndex
				&& this.currentPageNumber < this.totalPages - middleIndex) {
			startIndex = this.currentPageNumber - middleIndex;
		} else if (this.currentPageNumber >= this.totalPages - middleIndex) {
			startIndex = this.totalPages > this.maxIndexPages ? this.totalPages
					- this.maxIndexPages + 1 : 1;
		}
		var pageNumber = startIndex;
		for (var i = 0;i < this.maxIndexPages; i++) {
			if (pageNumber > this.totalPages) {
				break;
			}
			parent.appendChild(this._createPageIndex(pageNumber, pageNumber,
					true));
			this._createTextSpan(parent, " ");
			pageNumber++;
		}
	},
	_createNext : function(parent) {
		parent.appendChild(this._createPageIndex("下一页",
				this.currentPageNumber < this.totalPages
						? parseInt(this.currentPageNumber) + 1
						: this.totalPages));
	},
	_createLast : function(parent) {
		parent.appendChild(this._createPageIndex("最后一页", this.totalPages));
	},
	_createJump : function(parent) {
		this._createTextSpan(parent, "第");
		var selectField = document.createElement("select");
		for (var i = 1;i <= this.totalPages; i++) {
			var option = document.createElement("option");
			option.value = i;
			option.appendChild(document.createTextNode(i));
			selectField.appendChild(option);
		}
		selectField.style.fontSize = 12;
		selectField.value = this.currentPageNumber;
		var thisObject = this;
		selectField.onchange = function() {
			thisObject._setPage(this.value, true);
		};
		parent.appendChild(selectField);
		this._createTextSpan(parent, "页");
	}
});