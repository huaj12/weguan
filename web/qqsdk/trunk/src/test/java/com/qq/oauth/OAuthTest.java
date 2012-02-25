package com.qq.oauth;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class OAuthTest {
    
    public static final int THOUSAND = 1000;
    
    @SuppressWarnings("static-access")
    @Test
    public void testGetInstance() {
        OAuth oauth= OAuth.getInstance();
        assertThat(oauth.THOUSAND, is(OAuthTest.THOUSAND));
    }

    @Test
    public void testGetOauthNonce() {

    }

    @Test
    public void testGetOauthTimestamp() {

    }

    @Test
    public void testGetOauthSignature() {

    }

    @Test
    public void testGetSerialParameters() {

    }

    @Test
    public void testGetBase64Mac() {

    }

}
