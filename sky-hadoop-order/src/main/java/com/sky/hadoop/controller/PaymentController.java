package com.sky.hadoop.controller;

import com.sky.hadoop.entity.PaymentEntity;
import com.sky.hadoop.service.PaymentService;
import com.sky.hadoop.view.PageUtils;
import com.sky.hadoop.view.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


/**
 * 
 *
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = paymentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PaymentEntity payment = paymentService.getById(id);

        return R.ok().put("payment", payment);
    }

    /**
     * 保存
     */
    @RequestMapping("/create")
    public R create(@RequestBody PaymentEntity payment){
        payment.setPayTime(new Date());
		paymentService.save(payment);
        return R.ok();
    }

}
