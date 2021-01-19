package com.sky.hadoop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品表
 * 
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@Data
@TableName("tab_item")
public class ItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	private Long id;
	/**
	 * 商品名称
	 */
	private String title;
	/**
	 * 商品卖价格，单位为：分
	 */
	private Long price;
	/**
	 * 商品卖点
	 */
	private String sellPoint;
	/**
	 * 库存数量
	 */
	private Integer stockNum;
	/**
	 * 商品图片
	 */
	private String pic;
	/**
	 * 商品状态，1-正常，2-已下架，3-删除
	 */
	private Integer status;
	/**
	 * 商品规格
	 */
	private String spec;
	/**
	 * 商品描述
	 */
	private String describes;
	/**
	 * 创建时间
	 */
	private Date created;
	/**
	 * 更新时间
	 */
	private Date updated;

}
