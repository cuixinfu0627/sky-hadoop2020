package com.sky.hadoop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 经销商用户
 * 
 * @author live
 * @email 870459550@qq.com
 * @date 2020-12-19 15:36:10
 */
@Data
@TableName("tab_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 姓名
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 性别：1男，2女
	 */
	private Integer gender;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 商品金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String money;

}
