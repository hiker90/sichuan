package com.zzsj.analysis.base.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/1 10:47
 * @description：shiro配置类
 */
@Configuration
public class ShiroConfig {

    @Bean
    public MyRealm customRealm(){
        MyRealm customRealm = new MyRealm();
        return customRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //如果不是前后端分离，则不必设置下面的sessionManager
        manager.setSessionManager(sessionManager());
        //设置realm（推荐放到最后，不然某些情况会不生效）
        manager.setRealm(customRealm());
        return manager;
    }

    @Bean(name="sessionDAO")
    public EnterpriseCacheSessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO abstractSessionDAO=new EnterpriseCacheSessionDAO();
        abstractSessionDAO.setSessionIdGenerator(new CustomsSessionIdGenerator());
        return abstractSessionDAO;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        //超时时间，默认 30分钟，会话超时；方法里面的单位是毫秒
        //customSessionManager.setGlobalSessionTimeout(20000);
        customSessionManager.setSessionDAO(sessionDAO());
        return customSessionManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager);

        //加入自定义的filter
        Map<String, Filter> filterMap = filter.getFilters();
        filterMap.put("client", new ClientShiroFilter());
        filter.setFilters(filterMap);

        //定义登录相关路径
        filter.setLoginUrl("/login");
        //权限认证失败地址
        filter.setUnauthorizedUrl("/notlogin");

        //定义拦截路径,记得将静态资源也排除过滤
        /*进行权限的控制,必须使用LinkHashMap,shrio要按照顺序进行设置*/
        Map<String, String> authMap = new LinkedHashMap<>();
//        authMap.put("/guest/**", "anon");
//        authMap.put("/static/**", "anon");
//        authMap.put("/user/**", "client,roles[user]");
//        authMap.put("/admin/**", "client,roles[admin]");
        authMap.put("/", "anon");
        authMap.put("/login", "anon");
        authMap.put("/notlogin", "anon");
        filter.setFilterChainDefinitionMap(authMap);

        //配置完成
        return filter;
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
