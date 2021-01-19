package com.sky.hadoop.service.impl;

import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.service.OrderService;
import com.sky.hadoop.view.R;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @name: OrderServiceImpl <tb>
 * @title: 请输入类描述  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/8 16:30 <tb>
 */
@Component
public class OrderServiceFallBackImpl implements OrderService {

    @Override
    public R create(OrderEntity order) {
        return R.error("-----------------------服务返回 create OrderServiceFallBackImpl fall back-list");
    }

    @Override
    public R update(OrderEntity order) {
        return R.error("----------------------- 服务返回 update OrderServiceFallBackImpl fall back-list");
    }

    @Override
    public R updateStatus(OrderEntity order) {
        return R.error("----------------------- 服务返回 updateStatus OrderServiceFallBackImpl fall back-list");
    }


    @Override
    public R list(Map<String, Object> params) {
        return R.error("-----------------------服务返回 OrderFeignFallBackService fall back-list");
    }

    @Override
    public R info(Long id) {
        return R.error("-----------------------服务返回 OrderFeignService fall back-info");
    }

    @Override
    public R openfeignTimeout() {
        return R.error("-----------------------服务返回 OrderFeignService fall back-openfeignTimeout");
    }

    @Override
    public R hystrixCircuitBreaker(OrderEntity order) {
        return R.error("-----------------------服务返回 OrderFeignService fall back-hystrixCircuitBreaker");
    }

}
