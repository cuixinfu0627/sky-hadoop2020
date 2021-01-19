package com.sky.hadoop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.entity.UserEntity;
import com.sky.hadoop.view.PageUtils;
import com.sky.hadoop.view.R;

import java.util.Map;

/**
 * @name: UserService <tb>
 * @title: 请输入类描述  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/21 13:50 <tb>
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R createOrder(OrderEntity order);

    String getIDBySnowFlake();

}