package com.qq.connect;

import static org.junit.Assert.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class RequestTokenTest {

    @Test
    public void testGetRequestToken() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        final RequestToken rt = new RequestToken();
        final String html = rt.getRequestToken();
        System.out.println(html);
        assertThat(html, startsWith("oauth_token="));
    }

}
