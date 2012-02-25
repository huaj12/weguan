package com.qq.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.qq.oauth.Config;
import com.qq.oauth.OAuth;

public final class Verify {

    private static Verify verify = new Verify();

    private Verify() {
    }

    public static Verify getInstance() {
        return verify;
    }

    public static boolean verifyOpenID(String openid, String timestamp, String oauth_signature) throws UnsupportedEncodingException,
            InvalidKeyException, NoSuchAlgorithmException {
        String str = openid + timestamp;
        String signature = OAuth.getBase64Mac(str, Config.APP_KEY);
        return signature.equals(URLDecoder.decode(oauth_signature, "UTF-8"));
    }
}
