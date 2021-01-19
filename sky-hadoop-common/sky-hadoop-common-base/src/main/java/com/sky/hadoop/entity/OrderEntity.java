package com.sky.hadoop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@Data
@TableName("tab_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@TableId
	private Long id;
	/**
	 * 商品金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String payment;
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 商品id
	 */
	private Long itemId;
	/**
	 * 商品名称
	 */
	private String itemTitle;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户
	 */
	private String username;
	/**
	 * 联系电话
	 */
	private String mobile;
	/**
	 * 配送地址
	 */
	private String address;
	/**
	 * 买家留言
	 */
	private String message;
	/**
	 * 状态：1、待支付，2、已支付，3、已取消
	 */
	private Integer status;
	/**
	 * 订单创建时间
	 */
	private Date createTime;
	/**
	 * 付款时间
	 */
	private Date paymentTime;

	@TableField(exist=false)
	private ItemEntity item;

	/**
	 * 订单数量
	 */
	private Integer count;
}
