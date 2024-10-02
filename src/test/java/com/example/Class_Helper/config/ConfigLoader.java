package com.example.Class_Helper.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();
    static {
        try {
            properties.load(ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e){
            throw new RuntimeException("Cannot load", e);
        }
    }
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
