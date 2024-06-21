package com.zzsj.analysis.base.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * mybatis plus 提供的代码生成器
 * 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码
 *
 * @author zbya
 */
public class CodeGenerator {

    /**
     * 数据库 URL
     */
    private static final String URL = "jdbc:mysql://10.25.171.24:13306/analysis?characterEncoding=utf-8&amp;useUnicode=true&amp;allowMultiQueries=true";
    /**
     * 数据库驱动
     */
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    /**
     *  数据库用户名
     */
    private static final String USERNAME = "root";
    /**
     * 数据库密码
     */
    private static final String PASSWORD = "123654";
    /**
     * @author 值
     */
    private static final String AUTHOR = "zbya";
    /**
     * 包的基础路径
     */
    private static final String BASE_PACKAGE_URL = "com.zzsj.analysis";
    /**
     * xml文件路径
     */
    private static final String XML_PACKAGE_URL = "/src/main/resources/mapper/data";
//    /**
//     * xml 文件模板
//     */
//    private static final String XML_MAPPER_TEMPLATE_PATH = "generator/templates/mapper.xml";
//    /**
//     *mapper 文件模板
//     */
//    private static final String MAPPER_TEMPLATE_PATH = "generator/templates/mapper.java";
    /**
     * entity 文件模板
     */
    private static final String ENTITY_TEMPLATE_PATH = "generator/templates/entity.java";
//    /**
//     * service 文件模板
//     */
//    private static final String SERVICE_TEMPLATE_PATH = "generator/templates/service.java";
//    /**
//     * serviceImpl 文件模板
//     */
//    private static final String SERVICE_IMPL_TEMPLATE_PATH = "generator/templates/serviceImpl.java";
//    //
//    /**
//     * controller 文件模板
//     */
//    private static final String CONTROLLER_TEMPLATE_PATH = "generator/templates/controller.java";
    /**
     * 地址
     */
    private static final String PROJECT_PATH=System.getProperty("user.dir");

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(PROJECT_PATH + "/src/main/java");
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(URL);
        dataSourceConfig.setDriverName(DRIVER_NAME);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName("manage");
        packageConfig.setParent(BASE_PACKAGE_URL);
        generator.setPackageInfo(packageConfig);
        // 配置自定义代码模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity(ENTITY_TEMPLATE_PATH);
//        templateConfig.setXml(XML_MAPPER_TEMPLATE_PATH);
//        templateConfig.setMapper(MAPPER_TEMPLATE_PATH);
        templateConfig.setEntity(ENTITY_TEMPLATE_PATH);
//        templateConfig.setService(SERVICE_TEMPLATE_PATH);
//        templateConfig.setServiceImpl(SERVICE_IMPL_TEMPLATE_PATH);
//        templateConfig.setController(CONTROLLER_TEMPLATE_PATH);
        templateConfig.setEntity(ENTITY_TEMPLATE_PATH);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名"));
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt))
            {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
