package com.sky.hadoop.service;

import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.service.impl.OrderServiceFallBackImpl;
import com.sky.hadoop.view.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @name: OrderService <tb>
 * @title: open-feign-order  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/21 15:27 <tb>
 */
@Component
@FeignClient(value = "${service-url.nacos-order-service}",fallback = OrderServiceFallBackImpl.class)
public interface OrderService {

    @RequestMapping("order/list")
    public R list(@RequestParam Map<String, Object> params);

    @RequestMapping("order/info/{id}")
    public R info(@PathVariable("id") Long id);

    @PostMapping("order/create")
    public R create(@RequestBody OrderEntity order);

    @PostMapping("order/update")
    public R update(@RequestBody OrderEntity order);

    @PostMapping("order/updateStatus")
    public R updateStatus(@RequestBody OrderEntity order);

    @RequestMapping("/order/openfeign/timeout")
    public R openfeignTimeout();

    @RequestMapping("/order/hystrix/circuitBreaker")
    public R hystrixCircuitBreaker(OrderEntity order);
}
