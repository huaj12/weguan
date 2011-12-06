/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
names of its contributors may be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package kx4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import kx4j.http.AccessToken;
import kx4j.http.HttpClient;
import kx4j.http.PostParameter;
import kx4j.http.RequestToken;
import kx4j.http.Response;
import kx4j.org.json.JSONException;
import kx4j.org.json.JSONObject;

/**
 * A java reporesentation of the <a href="http://wiki.open.kaixin001.com/">KxSDK API</a>
 * @editor polaris
 */
/**
 * @author polaris
 *
 */
public class KxSDK extends KxSupport implements java.io.Serializable {

    public static String CONSUMER_KEY = "";
    public static String CONSUMER_SECRET = "";
    private String baseURL = Configuration.getScheme() + "api.kaixin001.com/";
    private String searchBaseURL = Configuration.getScheme() + "api.kaixin001.com/";  //api.kaixin001.com/
    private static final long serialVersionUID = -1486360080128882436L;
    
    public KxSDK() {
        super();
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        http.setRequestTokenURL(Configuration.getScheme() + "api.kaixin001.com/oauth/request_token");
        http.setAuthorizationURL(Configuration.getScheme() + "api.kaixin001.com/oauth/authorize");
        http.setAccessTokenURL(Configuration.getScheme() + "api.kaixin001.com/oauth/access_token");
    }
    
    //--------------construct method----------
     public KxSDK(String baseURL) {
        this();
        this.baseURL = baseURL;
    }

//    public KxSDK(String id, String password) {
//        this();
//        setUserId(id);
//        setPassword(password);
//    }
//    public KxSDK(String id, String password, String baseURL) {
//        this();
//        setUserId(id);
//        setPassword(password);
//        this.baseURL = baseURL;
//    }
    
    //--------------auth method----------
    /**
     *
     * @param consumerKey OAuth consumer key
     * @param consumerSecret OAuth consumer secret
     * @since Kx4J 1.0
     */
    public synchronized void setOAuthConsumer(String consumerKey, String consumerSecret) {
        this.http.setOAuthConsumer(consumerKey, consumerSecret);
    }

    /**
     * Retrieves a request token
     * @return generated request token.
     * @throws KxException when KxSDK service or network is unavailable
     * @since Kx4J 1.0
     * @see <a href="http://oauth.net/core/1.0/#auth_step1">OAuth Core 1.0 - 6.1.  Obtaining an Unauthorized Request Token</a>
     */
    public RequestToken getOAuthRequestToken() throws KxException {
        return http.getOAuthRequestToken();
    }

    public RequestToken getOAuthRequestToken(String callback_url) throws KxException {
        return http.getOauthRequestToken(callback_url);
    }

    /**
     * Retrieves an access token assosiated with the supplied request token.
     * @param requestToken the request token
     * @return access token associsted with the supplied request token.
     * @throws KxException when KxSDK service or network is unavailable, or the user has not authorized
     * @see <a href="http://wiki.open.kaixin001.com/index.php?id=OAuth%E6%96%87%E6%A1%A3">开心网OAuth文档</a>
     * @see <a href="http://oauth.net/core/1.0/#auth_step2">OAuth Core 1.0 - 6.2.  Obtaining User Authorization</a>
     * @since Kx4J 1.0
     */
    public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken) throws KxException {
        return http.getOAuthAccessToken(requestToken);
    }

    /**
     * Retrieves an access token assosiated with the supplied request token and sets userId.
     * @param requestToken the request token
     * @param pin pin
     * @return access token associsted with the supplied request token.
     * @throws KxException when KxSDK service or network is unavailable, or the user has not authorized
     * @see <a href="http://wiki.open.kaixin001.com/index.php?id=OAuth%E6%96%87%E6%A1%A3">开心网OAuth文档</a>
     * @see <a href="http://oauth.net/core/1.0/#auth_step2">OAuth Core 1.0 - 6.2.  Obtaining User Authorization</a>
     * @since  Kx4J 1.0
     */
    public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken, String pin) throws KxException {
        AccessToken accessToken = http.getOAuthAccessToken(requestToken, pin);
//        setUserId(accessToken.getScreenName());
        return accessToken;
    }

    /**
     * Retrieves an access token assosiated with the supplied request token and sets userId.
     * @param token request token
     * @param tokenSecret request token secret
     * @return access token associsted with the supplied request token.
     * @throws KxException when KxSDK service or network is unavailable, or the user has not authorized
     * @see <a href="http://wiki.open.kaixin001.com/index.php?id=OAuth%E6%96%87%E6%A1%A3">开心网OAuth文档</a>
     * @see <a href="http://oauth.net/core/1.0/#auth_step2">OAuth Core 1.0 - 6.2.  Obtaining User Authorization</a>
     * @since  Kx4J 1.0
     */
    public synchronized AccessToken getOAuthAccessToken(String token, String tokenSecret) throws KxException {
        AccessToken accessToken = http.getOAuthAccessToken(token, tokenSecret);
//        setUserId(accessToken.getScreenName());
        return accessToken;
    }

    /**
     * Retrieves an access token assosiated with the supplied request token.
     * @param token request token
     * @param tokenSecret request token secret
     * @param oauth_verifier oauth_verifier or pin
     * @return access token associsted with the supplied request token.
     * @throws KxException when KxSDK service or network is unavailable, or the user has not authorized
     * @see <a href="http://wiki.open.kaixin001.com/index.php?id=OAuth%E6%96%87%E6%A1%A3">开心网OAuth文档</a>
     * @see <a href="http://oauth.net/core/1.0/#auth_step2">OAuth Core 1.0 - 6.2.  Obtaining User Authorization</a>
     * @since  Kx4J 1.0
     */
    public synchronized AccessToken getOAuthAccessToken(String token, String tokenSecret, String oauth_verifier) throws KxException {
        return http.getOAuthAccessToken(token, tokenSecret, oauth_verifier);
    }

    public synchronized AccessToken getXAuthAccessToken(String userId, String passWord, String mode) throws KxException {
        return http.getXAuthAccessToken(userId, passWord, mode);
    }

    public synchronized AccessToken getXAuthAccessToken(String userid, String password) throws KxException {
        return getXAuthAccessToken(userid, password, Constants.X_AUTH_MODE);

    }

    /**
     * Sets the access token
     * @param accessToken accessToken
     * @since  Kx4J 1.0
     */
    public void setOAuthAccessToken(AccessToken accessToken) {
        this.http.setOAuthAccessToken(accessToken);
    }

    /**
     * Sets the access token
     * @param token token
     * @param tokenSecret token secret
     * @since  Kx4J 1.0
     */
    public void setOAuthAccessToken(String token, String tokenSecret) {
        setOAuthAccessToken(new AccessToken(token, tokenSecret));
    }

    /* Help Methods */
    /**
     * Returns the string "ok" in the requested format with a 200 OK HTTP status code.
     * @return true if the API is working
     * @throws KxException when KxSDK service or network is unavailable
     * @since Kx4J 1.0
     */
    public boolean test() throws KxException {
        return -1 != get(getBaseURL() + "help/test.json", false).
                asString().indexOf("ok");
    }
    
    private SimpleDateFormat format = new SimpleDateFormat(
            "EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KxSDK kxSDK = (KxSDK) o;

        if (!baseURL.equals(kxSDK.baseURL)) {
            return false;
        }
        if (!format.equals(kxSDK.format)) {
            return false;
        }
        if (!http.equals(kxSDK.http)) {
            return false;
        }
        if (!searchBaseURL.equals(kxSDK.searchBaseURL)) {
            return false;
        }
        if (!source.equals(kxSDK.source)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = http.hashCode();
        result = 31 * result + baseURL.hashCode();
        result = 31 * result + searchBaseURL.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + format.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KxSDK{"
                + "http=" + http
                + ", baseURL='" + baseURL + '\''
                + ", searchBaseURL='" + searchBaseURL + '\''
                + ", source='" + source + '\''
                + ", format=" + format
                + '}';
    }

    //--------------base method----------
    
    /**
     * Sets token information
     * @param token
     * @param tokenSecret
     */
    public void setToken(String token, String tokenSecret) {
        http.setToken(token, tokenSecret);
    }
    
    /**
     * Sets the base URL
     *
     * @param baseURL String the base URL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * Returns the base URL
     *
     * @return the base URL
     */
    public String getBaseURL() {
        return this.baseURL;
    }

    /**
     * Issues an HTTP GET request.
     *
     * @param url          the request url
     * @param authenticate if true, the request will be sent with BASIC authentication header
     * @return the response
     * @throws KxException when KxSDK service or network is unavailable
     */
    private Response get(String url, boolean authenticate) throws KxException {
        return get(url, null, authenticate);
    }

    /**
     * Issues an HTTP GET request.
     *
     * @param url          the request url
     * @param authenticate if true, the request will be sent with BASIC authentication header
     * @param name1        the name of the first parameter
     * @param value1       the value of the first parameter
     * @return the response
     * @throws KxException when KxSDK service or network is unavailable
     */
    protected Response get(String url, String name1, String value1, boolean authenticate) throws KxException {
        return get(url, new PostParameter[]{new PostParameter(name1, value1)}, authenticate);
    }

    /**
     * Issues an HTTP GET request.
     *
     * @param url          the request url
     * @param name1        the name of the first parameter
     * @param value1       the value of the first parameter
     * @param name2        the name of the second parameter
     * @param value2       the value of the second parameter
     * @param authenticate if true, the request will be sent with BASIC authentication header
     * @return the response
     * @throws KxException when KxSDK service or network is unavailable
     */
    protected Response get(String url, String name1, String value1, String name2, String value2, boolean authenticate) throws KxException {
        return get(url, new PostParameter[]{new PostParameter(name1, value1), new PostParameter(name2, value2)}, authenticate);
    }

    /**
     * Issues an HTTP GET request.
     *
     * @param url          the request url
     * @param params       the request parameters
     * @param authenticate if true, the request will be sent with BASIC authentication header
     * @return the response
     * @throws KxException when KxSDK service or network is unavailable
     */
    protected Response get(String url, PostParameter[] params, boolean authenticate) throws KxException {
        if (url.indexOf("?") == -1) {
            url += "?source=" + CONSUMER_KEY;
        } else if (url.indexOf("source") == -1) {
            url += "&source=" + CONSUMER_KEY;
        }
        if (null != params && params.length > 0) {
            url += "&" + HttpClient.encodeParameters(params);
        }
        return http.get(url, authenticate);
    }

    /**
     * Issues an HTTP GET request.
     *
     * @param url          the request url
     * @param params       the request parameters
     * @param paging controls pagination
     * @param authenticate if true, the request will be sent with BASIC authentication header
     * @return the response
     * @throws KxException when KxSDK service or network is unavailable
     */
    protected Response get(String url, PostParameter[] params, Paging paging, boolean authenticate) throws KxException {
        if (null != paging) {
            List<PostParameter> pagingParams = new ArrayList<PostParameter>(4);
            if (-1 != paging.getMaxId()) {
                pagingParams.add(new PostParameter("max_id", String.valueOf(paging.getMaxId())));
            }
            if (-1 != paging.getSinceId()) {
                pagingParams.add(new PostParameter("since_id", String.valueOf(paging.getSinceId())));
            }
            if (-1 != paging.getPage()) {
                pagingParams.add(new PostParameter("page", String.valueOf(paging.getPage())));
            }
            if (-1 != paging.getCount()) {
                if (-1 != url.indexOf("search")) {
                    // search api takes "rpp"
                    pagingParams.add(new PostParameter("rpp", String.valueOf(paging.getCount())));
                } else {
                    pagingParams.add(new PostParameter("count", String.valueOf(paging.getCount())));
                }
            }
            PostParameter[] newparams = null;
            PostParameter[] arrayPagingParams = pagingParams.toArray(new PostParameter[pagingParams.size()]);
            if (null != params) {
                newparams = new PostParameter[params.length + pagingParams.size()];
                System.arraycopy(params, 0, newparams, 0, params.length);
                System.arraycopy(arrayPagingParams, 0, newparams, params.length, pagingParams.size());
            } else {
                if (0 != arrayPagingParams.length) {
                    String encodedParams = HttpClient.encodeParameters(arrayPagingParams);
                    if (-1 != url.indexOf("?")) {
                        url += "&source=" + CONSUMER_KEY
                                + "&" + encodedParams;
                    } else {
                        url += "?source=" + CONSUMER_KEY
                                + "&" + encodedParams;
                    }
                }
            }
            return get(url, newparams, authenticate);
        } else {
            return get(url, params, authenticate);
        }
    }

    private PostParameter[] generateParameterArray(Map<String, String> parames)
            throws KxException {
        PostParameter[] array = new PostParameter[parames.size()];
        int i = 0;
        for (String key : parames.keySet()) {
            if (parames.get(key) != null) {
                array[i] = new PostParameter(key, parames.get(key));
                i++;
            }
        }
        return array;
    }
    public final static Device IM = new Device("im");
    public final static Device SMS = new Device("sms");
    public final static Device NONE = new Device("none");

    static class Device {

        final String DEVICE;

        public Device(String device) {
            DEVICE = device;
        }
    }

    public void setToken(AccessToken accessToken) {
        this.setToken(accessToken.getToken(), accessToken.getTokenSecret());

    }

    /**
     * 获取当前登录用户的资料
     * @param fields 用户自定义返回的字段，以逗号分隔，可选。如：只返回字段uid,name,gender,logo50
     * @return
     * @throws KxException 
     */
    public User getMyInfo(String fields) throws KxException {
        String getParameters = "";
        if (fields.length() != 0) {
            getParameters = "?fields=" + fields;
        }
        return new User(get(getBaseURL() + "users/me.json" + getParameters, true).asJSONObject());
    }

    /**
     * 根据UID获取多个用户的资料
     * @param uids 用户ID，可以设置多个，以半角逗号分隔。
     * @param fields 用户自定义返回的字段，以逗号分隔，可选。如：只返回字段uid,name,gender,logo50
     * @param start 起始搜索 (选填，默认0) 
     * @param num 需要返回记录的个数(选填，默认20，最多50) 
     * @return
     * @throws KxException 
     */
    public List<User> getUsers(String uids, String fields, int start, int num) throws KxException {

        if (uids.length() == 0) {
            throw new KxException("app_param_lost");
        }

        String[] uidArr = uids.split(",");
        int uidNum = uidArr.length;

        if (uidNum > 50) {
            throw new KxException("app_uids_wrong");
        }

        String getParameters = "";

        if (start < 0) {
            start = 0;
        }

        if (num < 0 || num > 50) {
            num = 20;
        }
        getParameters += "?start=" + start + "&num=" + num;

        // TODO:考虑过滤两个UID之间的空格
        getParameters += "&uids=" + uids;

        if (fields.length() != 0) {
            getParameters += "&fields=" + fields;
        }

        return User.constructUser(get(getBaseURL() + "users/show.json" + getParameters, true));
    }

    /**
     * 取出当前登录用户的好友资料
     * @param fields 用户自定义返回的字段，以逗号分隔，可选，如只返回字段uid,name,gender,logo50
     * @param start 起始搜索 (选填，默认0) 
     * @param num 需要返回记录的个数(选填，默认20，最多50) 
     * @return
     * @throws KxException 
     */
    public List<User> getFriends(String fields, int start, int num) throws KxException {

        String getParameters = "";

        if (start < 0) {
            start = 0;
        }

        if (num < 0 || num > 50) {
            num = 20;
        }
        getParameters += "?start=" + start + "&num=" + num;

        if (fields.length() != 0) {
            getParameters += "&fields=" + fields;
        }

        return User.constructUser(get(getBaseURL() + "friends/me.json" + getParameters, true));
    }

    /**
     * 获取两个用户间的好友关系
     * @param uid1 用户UID
     * @param uid2 另一个用户UID
     * @return 返回int类型
     *      0: 没有关系
     *      1: 偶像
     *      2: 粉丝 
     *      3: 互为好友
     * @throws KxException 
     */
    public int getRelationShip(long uid1, long uid2) throws KxException {

        if (uid1 <= 0 || uid2 <= 0) {
            throw new KxException("app_param_lost");
        }

        String getParameters = "?uid1=" + uid1 + "&uid2=" + uid2;

        JSONObject json = get(getBaseURL() + "friends/relationship.json" + getParameters, true).asJSONObject();

        int relationship = -1;
        try {
            relationship = json.getInt("relationship");
        } catch (JSONException e) {
            relationship = -1;
        }
        return relationship;
    }

    /**
     * 获取用户安装组件的状态
     * @param uids 用户ID,可以设置多个，以逗号分隔。如：123456,220993, 最多不能超过50个 
     * @param start 起始搜索 (选填，默认0) 
     * @param num 需要返回记录的个数(选填，默认20，最多50) 
     * @return
     * @throws KxException 
     */
    public List<AppStatus> getAppStatus(String uids, int start, int num) throws KxException {

        if (uids.length() == 0) {
            throw new KxException("app_param_lost");
        }

        String[] uidArr = uids.split(",");
        int uidNum = uidArr.length;

        if (uidNum > 50) {
            throw new KxException("app_uids_wrong");
        }

        String getParameters = "";

        if (start < 0) {
            start = 0;
        }

        if (num < 0 || num > 50) {
            num = 20;
        }
        getParameters += "?start=" + start + "&num=" + num;

        // TODO:考虑过滤两个UID之间的空格
        getParameters += "&uids=" + uids;

        return AppStatus.constructStatus(get(getBaseURL() + "app/status.json" + getParameters, true));
    }

    /**
     * 获取本人安装本组件的好友的uid列表
     * @param start 起始搜索 (选填，默认0) 
     * @param num 需要返回记录的个数(选填，默认20，最多50) 
     * @return
     * @throws KxException 
     */
    public UIDs getAppFriendUids(int start, int num) throws KxException {

        String getParameters = "";

        if (start < 0) {
            start = 0;
        }

        if (num < 0 || num > 50) {
            num = 20;
        }
        getParameters += "?start=" + start + "&num=" + num;

        return new UIDs(get(getBaseURL() + "app/friends.json" + getParameters, true), this);
    }

    /**
     * 获取某人邀请成功的好友uid列表
     * @param uid 某人的开心网UID
     * @param start
     * @param num
     * @return 返回InvitedUIDs对象，包含邀请的好友UID及安装状态等
     * @throws KxException 
     */
    public InvitedUIDs getAppInvitedUids(long uid, int start, int num) throws KxException {
        if (uid <= 0) {
            throw new KxException("app_param_lost");
        }

        String getParameters = "";

        if (start < 0) {
            start = 0;
        }

        if (num < 0 || num > 50) {
            num = 20;
        }
        getParameters += "?start=" + start + "&num=" + num + "&uid=" + uid;

        return new InvitedUIDs(get(getBaseURL() + "app/invited.json" + getParameters, true), this);
    }
}