package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class InfoTokenTest {

    @Test
    public void testGetInfo() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        final String html = new InfoToken().getInfo("13282187343341746703", "5pp3pzFaDWrqGBGB", "703608128", "xml");
        System.out.println(html);
    }

}
