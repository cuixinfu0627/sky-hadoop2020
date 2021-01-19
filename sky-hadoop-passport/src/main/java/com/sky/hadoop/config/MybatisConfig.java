package com.sky.hadoop.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 依赖项目 sky-cloud-common-key
 * @name: MybatisConfig <tb>
 * @title: 自动递增主键-leaf  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/13 9:40 <tb>
 *
 *         !-- 美团点评-leaf递增主键-->
 *         <dependency>
 *             <groupId>com.sky.cloud</groupId>
 *             <artifactId>sky-cloud-common-key</artifactId>
 *             <version>1.0-SNAPSHOT</version>
 *         </dependency>
 */
@MapperScan(basePackages="com.sky.hadoop.dao")
@Configuration
public class MybatisConfig {

//    @Value("${spring.datasource.url}")
//    private String dbUrl ;
//
//    @Value("${spring.datasource.druid.username}")
//    private String dbUsername ;
//
//    @Value("${spring.datasource.druid.password}")
//    private String dbPassword ;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverName ;
//
//    @Bean
//    GenCreateInterceptor redisIdGenInterceptor() {
//        return new GenCreateInterceptor(new DBPrimarykeyGeneratorCache(driverName,dbUrl,dbUsername,dbPassword));
//    }

}
