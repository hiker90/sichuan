package com.jh.jwt.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jh.config.JwtConfig;
import com.jh.constant.Constants;
import com.jh.utils.Base64Util;
import com.jh.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * JWT工具类
 **/
@Slf4j
public class JwtUtil {
    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String loginname = getLoginname(token);
            String secret = loginname + Base64Util.decodeThrowsException(JwtConfig.getEncryptJwtKey());

            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("loginname", loginname).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken认证解密出现UnsupportedEncodingException异常:{}", e.getMessage());
        }
        return false;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token 用户token
     * @param claim 名称
     */
    private static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @param token 用户token
     * @return token中包含的用户名
     */
    public static String getLoginname(String token) {
        return getClaim(token, Constants.CLAIM_LOGINNAME);
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token 用户token
     * @return token中包含的salt值
     */
    public static String getClaimSalt(String token) {
        return getClaim(token, Constants.CLAIM_SALT);
    }

    /**
     * 生成签名，token有效期交给用户管理
     *
     * @param loginname 用户登录名
     * @return 加密的token
     */
    public static String sign(String loginname, String salt) {
        try {
            // 帐号加JWT私钥加密
            String secret = loginname + Base64Util.decodeThrowsException(JwtConfig.getEncryptJwtKey());
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + JwtConfig.getAccessTokenExpireTime() * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带用户loginname及本次登录salt值
            return JWT.create()
                      .withClaim(Constants.CLAIM_LOGINNAME, loginname)
                      .withClaim(Constants.CLAIM_SALT, salt)
                      .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 根据request中的token获取用户登录名
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getLoginnameByToken(HttpServletRequest request) throws Exception {
        String accessToken = request.getHeader(Constants.ACCESS_TOKEN);
        String loginname = getLoginname(accessToken);
        if (StringUtils.isEmpty(loginname)) {
            throw new Exception("未获取到用户");
        }
        return loginname;
    }

    /* general salt */
    public static String generalSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes(4).toHex();
    }
}