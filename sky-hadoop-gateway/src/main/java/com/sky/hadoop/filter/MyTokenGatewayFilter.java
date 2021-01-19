package com.sky.hadoop.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @name: MyTokenGatewayFilter <tb>
 * @title: 自定义token过滤器  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/22 15:41 <tb>
 */
@Component
public class MyTokenGatewayFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(MyLogGatewayFilter.class);


    /**
     * 可自定义获取用户token或者签名等校验
     * 不过滤某些的实现：https://blog.csdn.net/djrm11/article/details/104488517/
     * @param exchange
     * @param chain
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("**********come in MyTokenGatewayFilter:"+new Date());
        // 获取校验参数 可参考happy-sky-web项目模块的token校验
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        String timestamp = exchange.getRequest().getQueryParams().getFirst("timestamp");
        String nonce = exchange.getRequest().getQueryParams().getFirst("nonce");
        String sign = exchange.getRequest().getQueryParams().getFirst("sign");

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
