package com.sky.hadoop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.hadoop.dao.PaymentDao;
import com.sky.hadoop.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.hadoop.entity.PaymentEntity;
import com.sky.hadoop.view.PageUtils;
import com.sky.hadoop.view.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("paymentService")
public class PaymentServiceImpl extends ServiceImpl<PaymentDao, PaymentEntity> implements PaymentService {

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PaymentEntity> page = this.page(
                new Query<PaymentEntity>().getPage(params),
                new QueryWrapper<PaymentEntity>()
        );

        return new PageUtils(page);
    }

}