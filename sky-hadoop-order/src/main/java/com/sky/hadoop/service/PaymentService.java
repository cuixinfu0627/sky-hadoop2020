package com.sky.hadoop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.hadoop.entity.PaymentEntity;
import com.sky.hadoop.view.PageUtils;

import java.util.Map;

/**
 * @name: PaymentService <tb>
 * @title: 请输入类描述  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/21 13:51 <tb>
 */
public interface PaymentService extends IService<PaymentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
