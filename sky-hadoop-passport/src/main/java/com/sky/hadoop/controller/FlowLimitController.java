package com.sky.hadoop.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sky.hadoop.view.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: FlowLimitController <tb>
 * @title: 1、Sentinel 服务限流-流控测试  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/5 10:23<tb>
 */
@RestController
public class FlowLimitController {

    private static final Logger logger = LoggerFactory.getLogger(FlowLimitController.class);

    @GetMapping("/testA")
    public R testA(){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ------testA");
        return R.ok("------testA");
    }

    @GetMapping("/testB")
    public R testB(){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ------testB");
        return R.ok("------testB");
    }

    @GetMapping("/testC")
    public R testC(){
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ------testC");
//        logger.info(Thread.currentThread().getName() + "\t"+"异常比例 ------testC");
//        int age = 10/0;
        return R.ok("------testC");
    }

    @GetMapping("/testD")
    public R testD(){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ------testD");
        return R.ok("------testD");
    }

    /**
     * @title: 测试热点可以限流配置方法 <tb>
     * @author: cuixinfu@51play.com <tb>
     * @date: 2021/1/5 15:35<tb>
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public R testHotKey(@RequestParam(value = "p1",required = false) String p1,
                        @RequestParam(value = "p1",required = false) String p2){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ---测试热点限流---testHotKey,o(^_^)o");
        return R.ok("------testHotKey");
    }

    public R deal_testHotKey(String p1, String p2, BlockException exception){
        logger.info(Thread.currentThread().getName() + "\t"+"FlowLimitController ---测试热点限流BlockException---deal_testHotKey,o(T-T)o");
        return R.error("------testHotKey,服务不可用");
    }

}
