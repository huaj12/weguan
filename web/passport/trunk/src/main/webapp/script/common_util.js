function evaluateJsById(scriptId) {
    try {
        var st = document.getElementById(scriptId);
        eval(st.innerHTML);
    } catch (e) {
        alert(e);
    }
}

var StyleUtil = {
    FLOAT_BOTTOM : 1,
    FLOAT_TOP : 1 << 1,
    FLOAT_LEFT : 1 << 2,
    FLOAT_RIGHT : 1 << 3,
    ADJUST_LEFT : 1 << 4,
    ADJUST_RIGHT : 1 << 5,
    ADJUST_TOP : 1 << 6,
    ADJUST_BOTTOM : 1 << 7,
    floatOn : function(srcObj, destObj, fp, ox, oy) {
        if (!srcObj || !destObj) {
            return;
        }
        srcObj = $(srcObj);
        destObj = $(destObj);
        var normalize = function(v) {
            return v >= 0 ? v : 0;
        }
        fp = fp ? fp : this.FLOAT_BOTTOM | this.ADJUST_LEFT;
        var left = 0, top = 0;
        if (fp & (this.FLOAT_BOTTOM | this.FLOAT_TOP)) {
            top = normalize((fp & this.FLOAT_TOP)
                    ? (destObj.getTop() - srcObj.offsetHeight)
                    : (destObj.getTop() + destObj.offsetHeight));
            left = normalize((fp & this.ADJUST_RIGHT) ? (destObj.getLeft()
                    + destObj.offsetWidth - srcObj.offsetWidth) : (destObj
                    .getLeft()));
        } else {
            left = normalize((fp & this.FLOAT_LEFT)
                    ? (destObj.getLeft() - srcObj.offsetWidth)
                    : (destObj.getLeft() + destObj.offsetWidth));
            top = normalize((fp & this.ADJUST_BOTTOM) ? (destObj.getTop()
                    + destObj.offsetHeight - srcObj.offsetHeight) : (destObj
                    .getTop()));
        }
        srcObj.style.top = normalize(top + (oy ? oy : 0)) + 'px';
        srcObj.style.left = normalize(left + (ox ? ox : 0)) + 'px';
    }
}

var Logger = {
    log : function(msg) {
        if ($("logger")) {
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(msg));
            $("logger").appendChild(li);
        }
    }
}
