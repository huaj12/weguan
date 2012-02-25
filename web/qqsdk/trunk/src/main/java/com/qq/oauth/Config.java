package com.qq.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    
    public static final String FILE_NAME = "connect.properties";
    public static String APP_ID = "";
    public static String APP_KEY = "";
    public static String OAUTH_CALLBACK = "http://www.domain.com/connect/qq/callback";

    static {
        InputStream in = Config.class.getClassLoader().getResourceAsStream(FILE_NAME);
        Properties properties = new Properties();
        try {
            properties.load(in);
            APP_ID = properties.getProperty("APP_ID");
            APP_KEY = properties.getProperty("APP_KEY");
            OAUTH_CALLBACK = properties.getProperty("OAUTH_CALLBACK", OAUTH_CALLBACK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Config config = new Config();

    private Config() {
    }

    public static Config getInstance() {
        return config;
    }
}
