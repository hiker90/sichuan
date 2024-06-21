package com.jh.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jh.config.SystemConfig;
import com.jh.utils.CommonUtils;
import com.jh.utils.string.StringUtils;
import com.jh.web.domain.SSOUserModel;
import com.jh.web.exception.SSOUserFetchErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SSOLoginService
{
    private static String sso_url = "http://172.16.21.130/TSSOService/rest/base";
    private static String access_token = "8f25f92c2f48490eb8e80bce04eb15d9";

    public static SSOUserModel login(String username, String password) throws SSOUserFetchErrorException
    {
        // 参数检查
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            log.info("参数错误,username:{},password:{}", username, password);
            return null;
        }
        String path = SystemConfig.getFilepath();
        SSOUserModel userModel = null;
        if (SystemConfig.isDebug()) {
            userModel = new SSOUserModel();
            switch (username)
            {
                case "ceshi1":
                    userModel.setIdCardNum("33333333333333");
                    break;
                case "ceshi2":
                    userModel.setIdCardNum("11111111111111");
                    break;
                case "ceshi3":
                    userModel.setIdCardNum("123456789012346");
                    break;
                default:
                    userModel.setIdCardNum("12510000MB1402917C");
                    break;
            }

            return userModel;
        } else {
            // 首先登录，获取token
            String login_url =
                    sso_url + "/loginService/login?access_code=" + access_token + "&loginId="
                    + username + "&password=" + password;

            try
            {
                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForObject(login_url, String.class);
                log.debug("url: {}, result:{}", login_url, result);
                // 登录正常，返回【{"loginToken":"9f8caefc0355434bb1d28e4bdca18f0d@sso","error":null}】
                // 登录异常，返回【{"loginToken":"","error":"登录错误，用户名或密码不正确,还有3次机会！"}】
                JSONObject jo = JSONObject.parseObject(result);
                String loginToken = jo.getString("loginToken");
                String error = jo.getString("error");
                // 如果error为null
                if (!StringUtils.isEmpty(error)) {
                    log.error("SSO登录失败！{}", result);
                    return userModel;
                }

                // 首先登录，获取token
                String getuser_url =
                        sso_url + "/ssoService/getUserByToken?access_code=" + access_token
                        + "&loginToken=" + loginToken;
                log.debug("url: {}, result:{}", getuser_url, result);

                restTemplate = new RestTemplate();
                result = restTemplate.getForObject(getuser_url, String.class);

                userModel = JSON.parseObject(result, SSOUserModel.class);
                if (CommonUtils.isNull(userModel) || CommonUtils.isNotNull(userModel.getHasError())) {
                    log.error("SSO登录获取用户信息失败！{}", result);
                    throw new SSOUserFetchErrorException(userModel.getErrorMessage());
                    //return userModel;
                }

            } catch (RestClientException e) {
                log.error("SSO登录异常！{}", e.getMessage());
                throw e;
            }
        }



        return userModel;
    }
}
