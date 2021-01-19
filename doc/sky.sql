/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1(本地机器)
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : 127.0.0.1:3306
 Source Schema         : sky

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 08/01/2021 17:08:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leaf_alloc
-- ----------------------------
DROP TABLE IF EXISTS `leaf_alloc`;
CREATE TABLE `leaf_alloc`  (
  `biz_tag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务标识',
  `max_id` bigint(20) NULL DEFAULT NULL COMMENT '分配的id号段的最大值',
  `step` bigint(11) NULL DEFAULT NULL COMMENT '步长',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `last_update_time` datetime NULL DEFAULT NULL,
  `current_update_time` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '号段存储表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of leaf_alloc
-- ----------------------------
INSERT INTO `leaf_alloc` VALUES ('leaf-segment-test', 2001, 2000, 'Test leaf Segment Mode Get Id', '2020-07-23 11:23:55', NULL, NULL);
INSERT INTO `leaf_alloc` VALUES ('_sky_db_tab_user', 2001, 1000, '用户表', '2020-07-22 15:00:29', '2020-07-22 15:00:29', NULL);
INSERT INTO `leaf_alloc` VALUES ('_sky_db_tab_item', 2001, 1000, '商品表', '2020-07-22 15:00:29', '2020-07-22 15:00:29', NULL);
INSERT INTO `leaf_alloc` VALUES ('_sky_db_tab_order', 1001, 1000, '订单表', '2020-07-22 15:00:29', '2020-07-22 15:00:29', NULL);
INSERT INTO `leaf_alloc` VALUES ('_sky_db_tab_payment', 1001, 1000, '支付表', '2020-07-22 15:00:29', '2020-07-22 15:00:29', NULL);

-- ----------------------------
-- Table structure for leaf_segment
-- ----------------------------
DROP TABLE IF EXISTS `leaf_segment`;
CREATE TABLE `leaf_segment`  (
  `biz_tag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务标识',
  `max_id` bigint(20) NULL DEFAULT NULL COMMENT '分配的id号段的最大值',
  `step` bigint(11) NULL DEFAULT NULL COMMENT '步长',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `last_update_time` datetime NULL DEFAULT NULL,
  `current_update_time` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '号段存储表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of leaf_segment
-- ----------------------------
INSERT INTO `leaf_segment` VALUES ('_sky_db_tab_user', 100, 1000, '用户表', '2020-07-22 15:00:29', '2020-07-22 15:00:29');
INSERT INTO `leaf_segment` VALUES ('_sky_db_tab_item', 100, 1000, '商品表', '2020-07-22 15:00:29', '2020-07-22 15:00:29');
INSERT INTO `leaf_segment` VALUES ('_sky_db_tab_order', 100, 1000, '订单表', '2020-07-22 15:00:29', '2020-07-22 15:00:29');
INSERT INTO `leaf_segment` VALUES ('_sky_db_tab_payment', 100, 1000, '支付表', '2020-07-22 15:00:29', '2020-07-22 15:00:29');
INSERT INTO `leaf_segment` VALUES ('leaf-segment-test', 100, 1000, 'Test leaf Segment Mode Get Id', '2020-07-22 15:27:04', '2020-07-22 15:27:07');

-- ----------------------------
-- Table structure for tab_item
-- ----------------------------
DROP TABLE IF EXISTS `tab_item`;
CREATE TABLE `tab_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `price` bigint(20) NOT NULL COMMENT '商品卖价格，单位为：分',
  `sell_point` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品卖点',
  `stock_num` int(10) NOT NULL COMMENT '库存数量',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '商品状态，1-正常，2-已下架，3-删除',
  `spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品规格',
  `describes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品描述',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `updated`(`updated`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tab_item
-- ----------------------------
INSERT INTO `tab_item` VALUES (1001, '冷面（1组3包）（零售10元3袋）', 650, '冷面', 100, 'http://file-cdn-dealer.cykonhy.com/202010/20201024-bdd0d19c5e284cc2b5fb01e02ace7e12.jpg', 2, '组*3包', '冷面', '2020-11-24 10:31:17', '2020-10-24 08:35:42');
INSERT INTO `tab_item` VALUES (1002, '0°玻璃水（12瓶一箱）（零售10元6瓶）', 1200, '玻璃水', 100, 'http://file-cdn-dealer.cykonhy.com/202010/20201024-567c9ad9687947538b9f80d9a763c047.jpg', 1, '箱*1', '玻璃水', '2020-11-14 13:59:43', '2020-10-24 08:38:10');
INSERT INTO `tab_item` VALUES (1003, '湿巾（一箱9包）', 1000, '湿巾', 100, 'http://file-cdn-dealer.cykonhy.com/202010/20201024-8e12bb060c754ada91b2735522ec5bbd.jpg', 3, '箱', '湿巾', '2020-11-01 22:37:51', '2020-10-24 08:39:32');

-- ----------------------------
-- Table structure for tab_order
-- ----------------------------
DROP TABLE IF EXISTS `tab_order`;
CREATE TABLE `tab_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `payment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品金额。精确到2位小数;单位:元。如:200.07，表示:200元7分',
  `order_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `item_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品名称',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配送地址',
  `message` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '买家留言',
  `status` int(10) NULL DEFAULT NULL COMMENT '状态：1、待支付，2、已支付，3、已取消',
  `create_time` datetime NULL DEFAULT NULL COMMENT '订单创建时间',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '付款时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9176 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tab_order
-- ----------------------------
INSERT INTO `tab_order` VALUES (1001, '358800', '202011020817013874', 1001, '冷面（1组3包）（零售10元3袋）', 1, 'sky', '13001021240', '北京市海淀区', NULL, 3, '2020-11-02 08:17:02', NULL);
INSERT INTO `tab_order` VALUES (1002, '7700', '202011021329507928', 1002, '0°玻璃水（12瓶一箱）（零售10元6瓶）', 2, 'Java', '13964571329', '南岩子口村碑旁', NULL, 3, '2020-11-02 13:29:51', NULL);
INSERT INTO `tab_order` VALUES (1003, '5940', '202011021341447562', 1003, '湿巾（一箱9包）', 3, 'Cloud', '18615010308', '山东烟台栖霞松山镇郝家楼村', NULL, 2, '2020-11-02 13:41:45', NULL);
INSERT INTO `tab_order` VALUES (9175, '790000', '1235649864', 1001, '40g海婷海带丝（25包一箱）卖9.9', 1, '孙行者', '15010089114', '花果山水帘洞齐天大圣美猴王孙悟空', '充数下班侧', 1, NULL, NULL);

-- ----------------------------
-- Table structure for tab_payment
-- ----------------------------
DROP TABLE IF EXISTS `tab_payment`;
CREATE TABLE `tab_payment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `order_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `payment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分',
  `pay_way` int(11) NULL DEFAULT NULL COMMENT '支付方式，1微信，2支付宝，3其他',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '付款时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tab_payment
-- ----------------------------

-- ----------------------------
-- Table structure for tab_user
-- ----------------------------
DROP TABLE IF EXISTS `tab_user`;
CREATE TABLE `tab_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别：1男，2女',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `money` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账户金额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '经销商用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tab_user
-- ----------------------------
INSERT INTO `tab_user` VALUES (1, 'sky', 'Sky', '15010089114', '870459550@qq.com', 'https://thirdwx.qlogo.cn/mmopen/vi_32/fM00qjNViazMDDmglVohmquQXrxU59UhDBm7WOhdSLPU1WID72yHRf4s94rSeYte6oibheonuYIO1KAcQcShudwg/132', 1, 'China', 'Gansu', 'Longnan', '2020-09-10 11:21:03', NULL);
INSERT INTO `tab_user` VALUES (2, 'Java', 'Java', '15010089115', '870459550@qq.com', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eric94nHxDbfs1FOChqibjpZuiaQfe3zdibhs3sfgEyhQoGJiamgR18Ka9t6HuouHibeRMYWy7ib1XeyFZDg/132', 1, 'China', 'Beijing', 'Haidian', '2020-10-28 17:47:24', NULL);
INSERT INTO `tab_user` VALUES (3, 'Cloud', 'Python', '15010089116', '870459550@qq.com', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRzZhag9AicB5kBEBtrDcztt7Z4JJOiatQVtp7jT5BBY2WpmYB8IMtibfuibUxEl3wuJFUYqQB7tg4ibg/132', 1, 'China', 'Shandong', 'Yantai', '2020-10-28 23:04:01', NULL);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
