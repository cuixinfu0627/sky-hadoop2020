package com.sky.hadoop.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sky.hadoop.myhandler.CustomerBlockHandler;
import com.sky.hadoop.view.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: FlowLimitController <tb>
 * @title: 2、Sentinel 服务限流-流控测试  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/5 10:23<tb>
 */
@RestController
public class RateLimitController {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitController.class);

    /**
     * @title: 1.按照资源名称-限流 <tb>
     * @author: cuixinfu@51play.com <tb>
     * @date: 2021/1/5 15:35<tb>
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public R byResource(){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ---按照资源名称限流测试---byResource,o(^_^)o");
        return R.ok("------byResource,资源名称-配置限流-服务测试ok!");
    }

    public R handleException(BlockException exception){
        logger.info(Thread.currentThread().getName() + "\t"+"RateLimitController ---按照资源名称限流测试BlockException---byResource,o(T-T)o");
        return R.error(exception.getClass().getCanonicalName()+"\t------byResource,资源名称-配置限流-服务不可用!");
    }

    /**
     * @title: 2.按url地址-限流 <tb>
     * @author: cuixinfu@51play.com <tb>
     * @date: 2021/1/5 15:35<tb>
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl")
    public R byUrl(){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ---按照url地址限流测试---byUrl,o(^_^)o");
        return R.ok("------byUrl,服务测试ok!");
    }


    /**自定义全局处理兜底方法
     * customerBlockHandler
     * @title: 全局处理-限流 <tb>
     * @date: 2021/1/5 15:35<tb>
     */
    @GetMapping("/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException")
    public R customerBlockHandler(){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ---客户自定义全局兜底方法---customerBlockHandler,o(^_^)o");
        return R.ok("------customerBlockHandler,客户自定义全局兜底方法测试ok!");
    }

}
