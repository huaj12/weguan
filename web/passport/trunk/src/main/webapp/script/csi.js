/**
 * @class ClientSideInclude <br>
 *        客户端包含
 * @example <code>
 * 			var csi = new ClientSideInclude('abc.do','mydiv','post');
 *          csi.addParameter('poin',234);
 *          csi.addParameter('type','product');
 *          csi.csi(true);
 *          </code>
 */
var ClientSideInclude = new Class( {
    /**
     * @param action
     *            目的URL
     * @param element
     *            HTML对象的id或是HTML对象<br>
     *            请求返回的文本将显示到以element中
     * @param method
     *            请求所使用的提交方法，get|post，可选
     * @param callback
     *            回调对象<br>
     *            <code>{
     *            	doBeforeCSI : function(responseText) { },
     *            	doAfterRequest : function(targetHtml, returnHtml, responseText) { }
     *            }</code>
     */
    initialize : function(action, element, method, callback) {
        this.callback = callback ? callback : {
            doBeforeCSI : Class.empty,
            doAfterRequest : Class.empty
        }
        this.target = $(element);
        this.divCover = $('CSI_DIV_COVER');
        this.httpRequest = new HttpRequest(action, method, this);
    },
    /**
     * @see HttpRequest.addParameter
     */
    addParameter : function(key, value) {
        this.httpRequest.addParameter(key, value);
    },
    /**
     * @see HttpRequest.fillWithForm
     */
    fillWithForm : function(formObj) {
        this.httpRequest.fillWithForm(formObj);
    },
    /**
     * 执行csi操作
     * 
     * @param evalScript
     *            boolean,为true时csi后执行response中的javascript
     */
    csi : function(evalScript) {
        if (!this.divCover) {
            this.divCover = new Element('div');
            this.divCover.id = 'CSI_DIV_COVER';
            this.divCover.setStyles( {
                position : 'absolute',
                zIndex : 100,
                backgroundColor : '#D8D4DE',
                left : '0px',
                top : '0px',
                margin : '0px',
                padding : '0px',
                opacity : 0.75
            });
            var loadingImg = new Element('img');
            loadingImg.src = '/images/loading.gif';
            loadingImg.setStyles( {
                position : 'absolute',
                backGroundColor : '#D8D4DE'
            });
            this.divCover.appendChild(loadingImg);
            document.body.insertBefore(this.divCover, document.body.firstChild);
        }
        var lHeight = 0, lWidth;
        if (document.body.clientHeight > document.body.scrollHeight) {
            lHeight = document.body.clientHeight;
        } else {
            lHeight = document.body.scrollHeight
        }
        lWidth = document.body.offsetWidth;
        this.divCover.style.width = lWidth + 'px';
        this.divCover.style.height = lHeight + 'px';
        this.divCover.oncontextmenu = function(evt) {
            return false;
        };
        var docElement = document.documentElement;
        var imgObj = this.divCover.firstChild;
        //imgObj.style.left = docElement.scrollLeft + (docElement.clientWidth - imgObj.offsetWidth) / 2 + 'px';
        imgObj.style.top = docElement.scrollTop + (docElement.clientHeight - imgObj.offsetHeight) / 2 + 'px';
        imgObj.style.left = '300px';
        //imgObj.style.top = '300px';
        this.divCover.style.display = 'block';
        this.httpRequest.sendRequest(evalScript);
    },
    /**
     * @private
     */
    onRequestSuccess : function(responseText) {
        this.divCover.style.display = "none";
        if (this.callback) {
            if (this.callback.doBeforeCSI) {
                this.callback.doBeforeCSI(responseText);
            }
            if (this.callback.doAfterRequest) {
                var returnHtml = this.target.innerHTML;
                this.target.setHTML(responseText);
                var result = this.callback.doAfterRequest(
                        this.target.innerHTML, returnHtml, responseText);
                if (result) {
                    this.target.setHTML(result);
                }
            }
        } else {
            this.target.setHTML(responseText);
        }
    },
    /**
     * @private
     */
    onRequestError : function(errorCode) {
        this.divCover.style.display = "none";
        this.target.innerHTML = "[error csi response, http status code is: "
                + errorCode + "]";
    }
});