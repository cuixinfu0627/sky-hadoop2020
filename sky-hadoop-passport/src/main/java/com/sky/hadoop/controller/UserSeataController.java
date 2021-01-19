package com.sky.hadoop.controller;

import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.service.UserService;
import com.sky.hadoop.view.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @name: UserFeignSeataController <tb>
 * @title: 测试分布式事务seata配合feign调用  <tb>
 * @author: cuixinfu@51play.com <tb>
 */
@RestController
@RequestMapping("user-seata")
public class UserSeataController {

    @Autowired
    private UserService userService;

    @PostMapping("/createOrder")
    public R create(@RequestBody OrderEntity order){
        R result = userService.createOrder(order);;
        return result;
    }

}
