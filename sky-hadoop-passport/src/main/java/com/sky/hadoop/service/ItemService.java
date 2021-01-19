package com.sky.hadoop.service;

import com.sky.hadoop.entity.ItemEntity;
import com.sky.hadoop.service.impl.ItemServiceFallBackImpl;
import com.sky.hadoop.view.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @name: ItemService <tb>
 * @title: open-feign-item  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/21 15:27 <tb>
 */
@Component
@FeignClient(value = "${service-url.nacos-item-service}",fallback = ItemServiceFallBackImpl.class)
public interface ItemService {

    @PostMapping("item/decrease")
    public R decrease(@RequestBody ItemEntity item);

}
