package com.sky.hadoop.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.service.OrderService;
import com.sky.hadoop.view.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @name: CircleBreakerController <tb>
 * @title: 3、Sentinel 服务熔断  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/7 14:32 <tb>
 */
@RestController
@RequestMapping("user-sentinel")
public class CircleBreakerController {

    @Value("${service-url.nacos-order-service}")
    private String invoke_url; //http://order-service

    @Resource
    private RestTemplate restTemplate; //ribbon 方式调用

    @Resource
    private OrderService orderService;

    /**
     * @title: 服务熔断和服务异常处理测试 <tb>
     * @author: cuixinfu@51play.com <tb>
     */
    @GetMapping("/order/info/{id}")
    //@SentinelResource(value = "fallback")
    //@SentinelResource(value = "fallback",fallback = "handlerFallback")
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler")
    @SentinelResource(value = "fallback",
            fallback = "handlerFallback",
            blockHandler = "blockHandler")
    public R info(@PathVariable("id") Long id){

        //1、ribbon方式 @LoadBalanced 调用
        //R result = restTemplate.getForObject(invoke_url + "/order/info/"+id, R.class);

        //2、openfeign 方式调用
        R result = orderService.info(id);

        int code = Integer.valueOf(result.get("code").toString());

        if (code != 200){
            throw  new NullPointerException("NullPointerException,该ID没有对应记录，空指针异常");
        }
        return result;
    }

    // fallback 只负责业务异常
    public R handlerFallback(@PathVariable Long id, Throwable e){
        OrderEntity order = new OrderEntity();
        order.setId(id);
        order.setAddress("兜底异常--->业务异常,handlerFallback");
        return R.error("------/order/info 兜底异常,handlerFallback,Exception内容 "+ e.getMessage()).put("order",order);
    }

    // blockHandler 只负责sentinel控制台违规
    public static R blockHandler(@PathVariable Long id,BlockException exception){
        OrderEntity order = new OrderEntity();
        order.setId(id);
        order.setAddress("兜底异常--->sentinel限流异常,blockHandler");
        return R.error("------/order/info 兜底异常,blockHandler,Exception内容 "+ exception.getMessage()).put("order",order);
    }


}
