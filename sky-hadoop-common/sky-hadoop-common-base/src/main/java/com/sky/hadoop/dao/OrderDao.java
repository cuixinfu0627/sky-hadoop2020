package com.sky.hadoop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.hadoop.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
