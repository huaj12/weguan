package com.qq.connect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;

import com.qq.oauth.Config;

public class RedirectToken {

    public String getRedirectURL(Map<String, String> tokens) throws UnsupportedEncodingException {
        return getRedirectURL(tokens, null);
    }

    public String getRedirectURL(Map<String, String> tokens, List<NameValuePair> parameters) throws UnsupportedEncodingException {
        String url = "http://openapi.qzone.qq.com/oauth/qzoneoauth_authorize";
        StringBuffer redirect_url = new StringBuffer(url);
        redirect_url.append("?oauth_consumer_key=").append(Config.APP_ID);
        String oauth_token = tokens.get("oauth_token");
        redirect_url.append("&oauth_token=").append(oauth_token);
        redirect_url.append("&oauth_callback=").append(encode(Config.OAUTH_CALLBACK));
        if (parameters != null && parameters.size() != 0) {
            for (int i = 0; i < parameters.size(); i++) {
                NameValuePair p = parameters.get(i);
                redirect_url.append("&").append(encode(p.getName())).append("=").append(encode(p.getValue()));
            }
        }
        return redirect_url.toString();
    }

    private String encode(String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, "UTF-8");
    }
}
