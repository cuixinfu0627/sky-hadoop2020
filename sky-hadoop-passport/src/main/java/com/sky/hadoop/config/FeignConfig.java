package com.sky.hadoop.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name: FeignConfig <tb>
 * @title: FeignConfig 日志级别  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/21 16:03 <tb>
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger. Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

}
