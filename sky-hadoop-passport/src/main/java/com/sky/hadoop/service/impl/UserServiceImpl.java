/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.sky.hadoop.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.hadoop.dao.UserDao;
import com.sky.hadoop.entity.ItemEntity;
import com.sky.hadoop.entity.OrderEntity;
import com.sky.hadoop.entity.UserEntity;
import com.sky.hadoop.service.ItemService;
import com.sky.hadoop.service.OrderService;
import com.sky.hadoop.service.UserService;
import com.sky.hadoop.snowflake.IdGeneratorSnowflake;
import com.sky.hadoop.view.PageUtils;
import com.sky.hadoop.view.Query;
import com.sky.hadoop.view.R;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private IdGeneratorSnowflake idGenerator;

	@Resource
	private OrderService orderService;

	@Resource
	private ItemService itemService;

	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");

		IPage<UserEntity> page = this.page(
				new Query<UserEntity>().getPage(params),
				new QueryWrapper<UserEntity>().like(StringUtils.isNotBlank(username),
						"username", username)

		);

		return new PageUtils(page);
	}


	@Override
	public String getIDBySnowFlake() {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		for (int i = 1;i<=20; i++){
			threadPool.submit(() -> {
				System.out.println("UserServiceImpl.getIDBySnowFlake: " + idGenerator.snowflakeId());
			});
		}
		threadPool.shutdown();
		return "hello snowflake";
	}


	@Override
	//@GlobalTransactional(name = "sky-create-order",rollbackFor = Exception.class)
	public R createOrder(OrderEntity order) {
		//0、开始创建订单-start
		logger.info("1、------>开始创建订单-start");
		//1、用户创建订单 order-service
		logger.info("2、------>订单微服务开始创建订单,新增Order");
		order.setOrderNum(generateOrderNum());
		order.setStatus(1);
		order.setCreateTime(new Date());
		orderService.create(order);

		//2、用户减去库存 item-service
		logger.info("3、------>商品微服务调用商品库存，做扣减数量Count");
		ItemEntity item = new ItemEntity();
		item.setId(order.getItemId());
		item.setStockNum(order.getCount()); //订单数量
		itemService.decrease(item);

		//3、扣除用户款 user-service - userDao
		logger.info("4、------>用户微服务调用用户账号,扣除用户款Money");
		UserEntity user = new UserEntity();
		user.setId(order.getUserId());
		user.setMoney(order.getPayment());
		userDao.updateById(user);

		//4、修改订单状态 order-service
		logger.info("5、------>订单服务开始修改订单号状态");
		OrderEntity orderUpdate = new OrderEntity();
		orderService.updateStatus(orderUpdate);

		//5、创建订单结束-end
		logger.info("6、------>创建订单结束-end");

		return R.ok();
	}

	/**
	 * 生成订单号：
	 *
	 * @return
	 */
	public String generateOrderNum() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		String dateStr = format.format(new Date()).replace(":", "");
		String randomNum = getRandomNum(4);
		return dateStr + randomNum;
	}

	/**
	 * 产生N位的随机数字
	 *
	 * @return
	 */
	public static String getRandomNum(int n) {
		Random rad = new Random();
		String result = rad.nextInt(1000000) + "";
		if (result.length() != n) {
			return getRandomNum(n);
		}
		return result;
	}
}
