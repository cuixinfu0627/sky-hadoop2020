package com.sky.hadoop.controller;

import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.service.OrderService;
import com.sky.hadoop.view.PageUtils;
import com.sky.hadoop.view.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private OrderService orderService;

    @Autowired
    DiscoveryClient discoveryClient;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok("查询订单列表成功,serverPort:" + serverPort).put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);
        return R.ok("查询订单信息成功,serverPort:" + serverPort).put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/create")
    public R create(@RequestBody OrderEntity order){
        order.setCreateTime(new Date());
		orderService.save(order);

        return R.ok("创建订单成功,serverPort:" + serverPort);
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public R update(@RequestBody OrderEntity order){
        orderService.updateById(order);
        return R.ok();
    }

    /**
     * 更新订单状态
     */
    @RequestMapping("/updateStatus")
    public R updateStatus(@RequestBody OrderEntity order){
        OrderEntity orderEntity = orderService.getById(order.getId());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setPayment(order.getPayment());
        orderEntity.setPaymentTime(new Date());
        orderService.updateById(orderEntity);
        return R.ok();
    }

    /**
     * discovery
     */
    @GetMapping("/discovery")
    public R discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services){
            logger. info("***** element:"+service);
        }
        List<ServiceInstance> instances= discoveryClient.getInstances("ORDER-SERVICE");
        for (ServiceInstance instance : instances){
            logger. info(instance.getInstanceId()+"\t"+instance. getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return  R.ok().put("discovery",this.discoveryClient);
    }

    /**
     * instances
     */
    @GetMapping("/instances")
    public R getInstances() {
        List<ServiceInstance> instances = discoveryClient.getServices().stream()
                .map(sid -> discoveryClient.getInstances(sid))
                .collect(Collectors.toList())
                .stream().flatMap(list -> list.stream()).collect(Collectors.toList());
        return  R.ok().put("instances",instances);
    }

    /**
     * openfeign 调用超时
     */
    @GetMapping("/openfeign/timeout")
    public R openFeignTimeOut() {

        TimeUnit.SECONDS.toSeconds(3);

        return  R.ok("测试openFeign超时!!!");
    }

    /**
     * hystrix 服务熔断
     */
    @RequestMapping("/hystrix/circuitBreaker")
    public R circuitBreaker(@RequestBody OrderEntity order){

        order.setCreateTime(new Date());
        orderService.save(order);

        return R.ok("创建订单成功,serverPort:" + serverPort);
    }

    /**
     * zipkin 链路追踪
     */
    @GetMapping("/zipkin")
    public String orderZipkin(){
        return "hi,i'am paymentzipkin server fall back，welcome to atguigu,o（n_n）o哈哈~";
    }

}
