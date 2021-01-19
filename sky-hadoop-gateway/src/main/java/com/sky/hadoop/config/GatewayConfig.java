package com.sky.hadoop.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name: GatewayConfiguration <tb>
 * @title: 请输入类描述  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/22 11:30 <tb>
 */
@Configuration
public class GatewayConfig {

    /**
     * 自定义路由，通过代码实现
     * 当访问她址http://Localhost：9527/guonei 时会自动转发到她址：http://news.baidu.com/guonei
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        RouteLocatorBuilder. Builder routes=builder. routes();
        routes. route("path_route_news1",r->r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes. build();
    }

    @Bean
    public RouteLocator customRouteLocator2(RouteLocatorBuilder builder){
        RouteLocatorBuilder. Builder routes =builder. routes();
        routes. route("path_route_news2",r->r. path("/guoji").uri("http://news.baidu.com/guoji")).build();
        return routes. build();
    }


}
