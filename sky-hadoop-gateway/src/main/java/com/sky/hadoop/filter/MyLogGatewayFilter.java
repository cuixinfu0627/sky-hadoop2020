package com.sky.hadoop.filter;

/**
 * @name: MyLogGatewayFilter <tb>
 * @title: 自定义日志过滤器  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/22 15:41 <tb>
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class MyLogGatewayFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(MyLogGatewayFilter.class);

    /**
     * 如果接口不带参数username为空，则返回失败
     * 不过滤某些的实现：https://blog.csdn.net/djrm11/article/details/104488517/
     * @param exchange
     * @param chain
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("**********come in MyLogGatewayFilter:"+new Date());
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if(username == null){
            logger.info("*******用户名为nu11，非法用户，o(T-T)o");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return  exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
