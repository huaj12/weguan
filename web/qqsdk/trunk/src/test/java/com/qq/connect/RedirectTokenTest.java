package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.junit.Test;

import com.qq.util.ParseString;

public class RedirectTokenTest {

    @Test
    public void testGetRedirectURL() throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        final RequestToken rt = new RequestToken();
        final String requesttoken = rt.getRequestToken();
        HashMap<String, String> tokens = ParseString.parseTokenString(requesttoken);
        RedirectToken ret = new RedirectToken();
        System.out.println(ret.getRedirectURL(tokens));
    }

}
