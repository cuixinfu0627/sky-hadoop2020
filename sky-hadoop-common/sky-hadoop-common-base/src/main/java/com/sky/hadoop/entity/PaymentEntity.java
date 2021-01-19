package com.sky.hadoop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@Data
@TableName("tab_payment")
public class PaymentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@TableId
	private Long id;
	/**
	 * 商品id
	 */
	private Long orderId;
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String payment;
	/**
	 * 支付方式，1微信，2支付宝，3其他
	 */
	private Integer payWay;
	/**
	 * 付款时间
	 */
	private Date payTime;

}
