package com.qq.connect;

import static org.junit.Assert.*;
import org.junit.Test;

import com.qq.oauth.Config;

import static org.hamcrest.Matchers.*;

public class ConfigTest {

    @SuppressWarnings("static-access")
    @Test
    public void testGetInstance() {
        final Config config = Config.getInstance();
        assertThat(config.APP_ID, is("205076"));
        assertThat(Config.APP_ID, is("205076"));
    }

}
