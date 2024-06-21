package com.zzsj.dm.base.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/8 15:58
 * @description：jwt工具
 */


@ConfigurationProperties("jwt.config")
@Component
public class JwtUtil {
    //签名私钥
    private static String key="25652df365494c0aac947f8b318606c4";
    //签名有效时间
    private static long ttl=10800000*24;

    public static String createJwtToken(String name) {
        //设置失效时间
        //获取当前时间
        long now = System.currentTimeMillis();
        //当前时间+有效时间=过期时间
        long exp = now + ttl;
        //创建JwtBuilder

        JwtBuilder jwtBuilder = Jwts.builder().setId(name).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, key);
        //设置失效时间
        jwtBuilder.setExpiration(new Date(exp));
        String token = jwtBuilder.compact();
        return token;
    }

    public static Claims parseToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

    public static void main(String[] args) {
        String token=JwtUtil.createJwtToken("");
        System.out.println(token);
        JwtUtil.parseToken(token);
    }

}
