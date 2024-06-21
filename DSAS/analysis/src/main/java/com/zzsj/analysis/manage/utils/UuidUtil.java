package com.zzsj.analysis.manage.utils;

import java.util.UUID;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/7 14:34
 * @description：为mysql列生成id主键
 */
public class UuidUtil {
    /**
     * Description: 生成32位随机数 
     * @Date: 2020/9/7 14:54
     * @Author: zbya 
     * 
     * @param 
     * @return: java.lang.String
     */ 
    public static String get(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static void main(String []args){
        System.out.println(get());
    }
}
