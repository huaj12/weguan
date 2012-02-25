package com.qq.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class Base64EncoderTest {
    
    public static final double TEMP = 1.34;
    
    @SuppressWarnings("static-access")
    @Test
    public void testBase64Encoder() {
        Base64Encoder base64Encoder = Base64Encoder.getInstance();
        assertThat(base64Encoder.TEMP, is(Base64EncoderTest.TEMP));
    }

}
