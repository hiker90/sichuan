package com.jh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfig
{
    private static String encryptJwtKey;

    private static Long accessTokenExpireTime;

    private static Long shiroCacheExpireTime;

    public static Long getAccessTokenExpireTime()
    {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(Long accessTokenExpireTime)
    {
        JwtConfig.accessTokenExpireTime = accessTokenExpireTime;
    }

    public static String getEncryptJwtKey()
    {
        return encryptJwtKey;
    }

    public void setEncryptJwtKey(String encryptJwtKey)
    {
        JwtConfig.encryptJwtKey = encryptJwtKey;
    }

    public static Long getShiroCacheExpireTime()
    {
        return shiroCacheExpireTime;
    }

    public void setShiroCacheExpireTime(Long shiroCacheExpireTime)
    {
        JwtConfig.shiroCacheExpireTime = shiroCacheExpireTime;
    }
}
