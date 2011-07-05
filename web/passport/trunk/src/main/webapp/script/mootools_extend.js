MooTools.Browser = {
    IE : !!(window.attachEvent && !window.opera),
    Opera : !!window.opera,
    WebKit : navigator.userAgent.indexOf('AppleWebKit/') > -1,
    Gecko : navigator.userAgent.indexOf('Gecko') > -1
            && navigator.userAgent.indexOf('KHTML') == -1,
    isCookieEnabled : function() {
        return window.navigator.cookieEnabled;
    },
    isJavaEnabled : function() {
        return window.navigator.javaEnabled();
    },
    isFlashEnabled : function() {
        var a = 0;
        if (navigator.plugins && navigator.plugins.length) {
            a = (navigator.plugins["Shockwave Flash"] || navigator.plugins["Shockwave Flash 2.0"])
                    ? 2
                    : 1;
        } else if (navigator.mimeTypes && navigator.mimeTypes.length) {
            var x = navigator.mimeTypes['application/x-shockwave-flash'];
            a = (x && x.enabledPlugin) ? 2 : 1;
        }
        return !!(a == 2);
    }
};
MooTools.Platform = {
    'name' : (navigator.platform.match(/mac|win|linux|nix/i) || ['other'])[0]
            .toLowerCase()
};
MooTools.Platform[MooTools.Platform.name] = true;

// 兼容以前的程序
var JSON = Json;
JSON.parse = Json.evaluate;
var pintang = {};

function toQueryPair(key, value) {
    if (!$type(value))
        return key;
    return key + '=' + encodeURIComponent($type(value) ? value : '');
}

Hash.implement( {
    toQueryString : function() {
        return this.keys().map(function(k) {
            var key = encodeURIComponent(k), values = this.get(k);
            if ($type(values) == 'array') {
                return values.map(function(value) {
                    return toQueryPair(key, value);
                }, this).join('&');
            }
            return toQueryPair(key, values);
        }, this).join('&');
    }
});

Element.extend( {
    hide : function() {
        this.style.display = 'none';
        return this;
    },
    show : function() {
        this.style.display = '';
        return this;
    },
    isVisible : function() {
        return this.style.display != 'none';
    },
    getDimensions : function() {
        var display = this.getStyle('display');
        if (display != 'none' && display != null) // Safari bug
            return {
                width : this.offsetWidth,
                height : this.offsetHeight
            };

        // All *Width and *Height properties give 0 on elements with display
        // none,
        // so enable the element temporarily
        var els = this.style;
        var originalVisibility = els.visibility;
        var originalPosition = els.position;
        var originalDisplay = els.display;
        els.visibility = 'hidden';
        els.position = 'absolute';
        els.display = 'block';
        var originalWidth = this.clientWidth;
        var originalHeight = this.clientHeight;
        els.display = originalDisplay;
        els.position = originalPosition;
        els.visibility = originalVisibility;
        return {
            width : originalWidth,
            height : originalHeight
        };
    },
    setAbsCoordinates : function(c) {
        if (!c)
            return this;
        this.setStyles( {
            left : c.left + 'px',
            top : c.top + 'px',
            width : c.width + 'px',
            height : c.height + 'px'
        });
        return this;
    }
});

if (MooTools.Browser.IE || MooTools.Browser.Opera) {
    Element._insertionTranslations = {
        tags : {
            TABLE : ['<table>', '</table>', 1],
            TBODY : ['<table><tbody>', '</tbody></table>', 2],
            TR : ['<table><tbody><tr>', '</tr></tbody></table>', 3],
            TD : ['<table><tbody><tr><td>', '</td></tr></tbody></table>', 4],
            SELECT : ['<select>', '</select>', 1]
        }
    }
    Element._getContentFromAnonymousElement = function(tagName, html) {
        var div = new Element('div'), t = Element._insertionTranslations.tags[tagName];
        div.innerHTML = t[0] + html + t[1];
        t[2].times(function() {
            div = div.firstChild
        });
        return $A(div.childNodes);
    }
    Element.extend( {
        setHTML : function(html) {
            html = html ? html : '';
            var tagName = this.tagName.toUpperCase();
            if (tagName in Element._insertionTranslations.tags) {
                $A(this.childNodes).each(function(node) {
                    this.removeChild(node)
                }, this);
                Element._getContentFromAnonymousElement(tagName, html).each(
                        function(node) {
                            this.appendChild(node)
                        }, this);
            } else
                this.innerHTML = html;
        }
    });
}

String.extend( {
    byteLength : function() {
        return this.replace(/[^\x00-\xff]/g, "  ").length;
    },
    copyToClipboard : function() {
        if (window.clipboardData) {
            window.clipboardData.setData("Text", this);
            return true;
        } else if (MooTools.Browser.isFlashEnabled()) {
            var b = 'flashcopier';
            if (!document.getElementById(b)) {
                var c = document.createElement('div');
                c.id = b;
                document.body.appendChild(c);
            }
            document.getElementById(b).innerHTML = '';
            var d = '<embed src="/images/ajax/_clipboard.swf"'
                    + ' FlashVars="clipboard=' + escape(this)
                    + '" width="0" height="0"'
                    + ' type="application/x-shockwave-flash"></' + 'embed>';
            document.getElementById(b).innerHTML = d;
            return true;
        } else if (window.netscape) {
            try {
                netscape.security.PrivilegeManager
                        .enablePrivilege('UniversalXPConnect');
            } catch (er) {
                return false;
            }
            var f = Components.classes['@mozilla.org/widget/clipboard;1']
                    .createInstance(Components.interfaces.nsIClipboard);
            if (!f)
                return false;
            var g = Components.classes['@mozilla.org/widget/transferable;1']
                    .createInstance(Components.interfaces.nsITransferable);
            if (!g)
                return false;
            g.addDataFlavor('text/unicode');
            var h = new Object();
            var i = new Object();
            var h = Components.classes["@mozilla.org/supports-string;1"]
                    .createInstance(Components.interfaces.nsISupportsString);
            var j = this;
            h.data = j;
            g.setTransferData("text/unicode", h, j.length * 2);
            var k = Components.interfaces.nsIClipboard;
            if (!f)
                return false;
            f.setData(g, null, k.kGlobalClipboard);
            return true;
        }
    },
    getDisplayLength : function() {
        var len = 0;
        for (var i = 0;i < this.length; i++) {
            len += this.charCodeAt(i) > 255 ? 2 : 1;
        }
        return len;
    },
    redirect : function() {
        window.location.href = "/RedirectPage.do?link="
                + encodeURIComponent(this);
    },
    removeAnchor : function() {
        var i = this.indexOf("#");
        if (i >= 0) {
            return this.substring(0, i);
        }
        return this;
    },
    replaceAll : function(AFindText, ARepText) {
        raRegExp = new RegExp(AFindText, "g");
        return this.replace(raRegExp, ARepText);
    }
});