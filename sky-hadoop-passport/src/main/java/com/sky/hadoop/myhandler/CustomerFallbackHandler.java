package com.sky.hadoop.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sky.hadoop.view.R;

/**
 * @name: CustomerBlockHandler <tb>
 * @title: 客户自定义sentinel 全局兜底方法处理  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2021/1/7 11:26 <tb>
 */
public class CustomerFallbackHandler {

    public static R handlerException(BlockException exception){
        return R.error("按客户自定义全局业务兜底异常--->业务异常,handlerFallback,global handlerException-0");
    }

    public static R handlerException2(BlockException exception){
        return R.error("按客户自定义全局业务兜底异常--->业务异常,handlerFallback,global handlerException-2");
    }

}
