package com.zzsj.dm.base.utils;

import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/2 15:27
 * @description：
 */
public class TokenUtil {
    private static final String secret = "secret";
    public static final String tokenHeard = "tokenHead";
    private static final Long expTime = 60 * 5 * 1000L;

    public static String getToken(String name,String id,String ip) {
        JwtBuilder builder = Jwts.builder();
        builder.signWith(SignatureAlgorithm.HS256,secret);
        builder.setId(id).setSubject(name).setAudience(ip);
        builder.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expTime));
        String token = builder.compact();
        return token;
    }

    public static Claims getTokenBody(String token) {
        JwtParser parser = Jwts.parser();
        Claims body = parser.setSigningKey(secret).parseClaimsJws(token).getBody();
        return body;
    }

    public static String getName(String token) {
        Claims body = getTokenBody(token);
        String id = body.getId();
        return id;
    }
}

