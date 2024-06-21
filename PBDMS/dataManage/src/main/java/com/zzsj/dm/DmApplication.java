package com.zzsj.dm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: 数据管理启动类
 * @Date: 2020/9/7 15:19
 * @Author: zbya
 *
 * @param
 * @return:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@EnableAsync
@MapperScan(basePackages = {"com.zzsj.dm.manage.mapper"})
public class DmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmApplication.class, args);
    }

}
