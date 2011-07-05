/**
 * @class HttpRequest
 * @example <code>
 * 			var request = new HttpRequest('abc.do','mydiv','post');
 *          request.addParameter('poin',234);
 *          request.addParameter('type','product');
 *          request.sendRequest(true);
 *          </code>
 */
var HttpRequest = new Class( {
    /**
     * @param action
     *            目的URL
     * @param method
     *            请求所使用的提交方法，get|post，可选
     * @param callback
     *            回调对象，可选<br>
     *            <code>{
     *            	onBeforeRequest : function() { },
     *            	onAfterRequest : function() { }
     *            	onRequestSuccess : function(responseText) { },
     *            	onRequestError : function(errorCode) { },
     *            }</code>
     * @param synchronous
     *            是否同步请求，默认为false，即请求是异步的，可选
     */
    initialize : function(action, method, callback, synchronous) {
        this.xmlHttpRequest = (window.XMLHttpRequest)
                ? new XMLHttpRequest()
                : (window.ie ? new ActiveXObject('Microsoft.XMLHTTP') : false);
        if (!this.xmlHttpRequest) {
            throw 'XMLHttpRequest is not supported by this browser';
        }
        this.action = action;
        this.method = method ? method.toUpperCase() : "GET";
        this.callback = callback || {
            onBeforeRequest : Class.empty,
            onAfterRequest : Class.empty,
            onRequestSuccess : Class.empty,
            onRequestError : Class.empty
        };
        this.synchronous = synchronous;
        this.parameters = [];
    },
    /**
     * 为请求添加一个参数对
     */
    addParameter : function(key, value) {
        this.parameters.push([key, value]);
    },
    /**
     * 获得所有的参数对，其中参数的值已用encodeURIComponent进行了编码
     * 
     * @return 参数字符串
     */
    getParameterString : function() {
        var url = '';
        this.parameters.each(function(param, index) {
            url += param[0] + '=' + encodeURIComponent(param[1]);
            if (index < this.length - 1) {
                url += '&';
            }
        }, this.parameters);
        return url;
    },

    /**
     * 提供一个表单来填充请求参数，表单中的所有控件（包括input,textarea,select,button）都将被添加到请求中。 <br>
     * ！！对input type=file，只能获得文件名信息，不能上传文件。！！
     * 
     * @param formObj
     *            form的id或HTML FORM对象
     */
    fillWithForm : function(formObj) {
        var es = $(formObj).elements;
        for (var i = 0;i < es.length; i++) {
            var tagName = es[i].tagName.toLowerCase();
            var type = es[i].type ? es[i].type.toLowerCase() : tagName;			
            if (type == "submit" || type == "button") {
				continue;
			}
            else if (tagName == "input" && (type == "checkbox" || type == "radio")) {
                if (es[i].checked) {
                    this.addParameter(es[i].name, es[i].value);
                }
            } else if (tagName == "select") {
                for (var j = 0;j < es[i].options.length; j++) {
                    if (es[i].options[j].selected) {
                        this.addParameter(es[i].name, es[i].options[j].value);
                    }
                }
            } else {
                this.addParameter(es[i].name, es[i].value);
            }
        }
    },
    /**
     * 发送请求
     * 
     * @param evalScript
     *            boolean,为true时csi后执行response中的javascript
     */
    sendRequest : function(evalScript) {
        if (this.callback.onBeforeRequest)
            this.callback.onBeforeRequest();
        this.xmlHttpRequest.onreadystatechange = function() {
            if (4 != this.xmlHttpRequest.readyState) {
                return;
            }
            if (200 != this.xmlHttpRequest.status
                    && this.callback.onRequestError) {
                this.callback.onRequestError(this.xmlHttpRequest.status);
            }
            if (this.callback.onRequestSuccess)
                this.callback
                        .onRequestSuccess(this.xmlHttpRequest.responseText);
            // 执行页面中的javascript
            if (evalScript) {
                var scripts = [], script;
                var regexp = /<script[^>]*>([\s\S]*?)<\/script>/gi;
                while ((script = regexp.exec(this.xmlHttpRequest.responseText)))
                    scripts.push(script[1]);
                scripts = scripts.join('\n');
                if (scripts)
                    (window.execScript) ? window.execScript(scripts) : window
                            .setTimeout(scripts, 0);
            }
        }.bindAsEventListener(this);
        if (this.method == "GET") {
            this.action += this.action.indexOf("?") > 0 ? "&" : "?";
            this.action += this.getParameterString();
            this.xmlHttpRequest.open(this.method, this.action, this.synchronous
                    ? false
                    : true);
            this.xmlHttpRequest.send(null);
        } else {
            this.xmlHttpRequest.open(this.method, this.action, this.synchronous
                    ? false
                    : true);
            this.xmlHttpRequest.setRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            this.xmlHttpRequest.send(this.getParameterString());
        }
        if (this.callback.onAfterRequest)
            this.callback.onAfterRequest();
    }

});