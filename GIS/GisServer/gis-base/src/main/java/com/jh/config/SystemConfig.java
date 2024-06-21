package com.jh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "system")
public class SystemConfig {
    // 是否本地调试环境
    private static boolean debug;

    private static String filepath;

    private static String glurl;

    private static String downloadurlprefix;

    public static boolean isDebug()
    {
        return debug;
    }

    public void setDebug(boolean debug)
    {
        SystemConfig.debug = debug;
    }

    public static String getFilepath()
    {
        return filepath;
    }

    public void setFilepath(String filepath)
    {
        SystemConfig.filepath = filepath;
    }

    public static String getGlurl()
    {
        return glurl;
    }

    public void setGlurl(String glurl)
    {
        SystemConfig.glurl = glurl;
    }

    public static String getDownloadurlprefix()
    {
        return downloadurlprefix;
    }

    public void setDownloadurlprefix(String downloadurlprefix)
    {
        SystemConfig.downloadurlprefix = downloadurlprefix;
    }
}
