package com.qq.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class ParseStringTest {

    @SuppressWarnings("static-access")
    @Test
    public void testParseTokenString() {
        String request_token = "oauth_token=1307259593546545401&oauth_token_secret=dVSM7suBmDbG9rZ9";
        ParseString parsestring = ParseString.getInstance();
        HashMap<String, String> tokens = parsestring.parseTokenString(request_token);
        String oauth_token = tokens.get("oauth_token");
        String oauth_token_secret = tokens.get("oauth_token_secret");
        assertThat(tokens.size(), is(2));
        assertThat(oauth_token, is("1307259593546545401"));
        assertThat(oauth_token_secret, is("dVSM7suBmDbG9rZ9"));
    }

}
