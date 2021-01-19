package com.sky.hadoop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.hadoop.entity.ItemEntity;
import com.sky.hadoop.view.PageUtils;

import java.util.Map;

/**
 * @name: ItemService <tb>
 * @title: 请输入类描述  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/21 13:52 <tb>
 */
public interface ItemService extends IService<ItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
