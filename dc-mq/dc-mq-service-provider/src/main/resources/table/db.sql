/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : spring

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 05/04/2020 21:51:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
  `order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单名称',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `amount` int(10) NULL DEFAULT NULL COMMENT '金额',
  `product_id` bigint(11) NULL DEFAULT NULL COMMENT '商品id',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '逻辑删除: 1、已删除、2、未删除',
  `gmt_created` datetime(0) NULL DEFAULT NULL COMMENT '创建',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `stock` int(10) NULL DEFAULT NULL COMMENT '库存数量',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '逻辑删除: 1、已删除、2、未删除',
  `gmt_created` datetime(0) NULL DEFAULT NULL COMMENT '创建',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_transaction_message
-- ----------------------------
DROP TABLE IF EXISTS `t_transaction_message`;
CREATE TABLE `t_transaction_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `producer_service_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务提供者',
  `consumer_service_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消费者',
  `message` varchar(8096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `send_count` int(10) NULL DEFAULT NULL COMMENT '发送次数，超过该次数就不发送消息',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态：1、待发送、2、已消费、3、已关闭、4、已发送',
  `last_send_date` datetime(0) NULL DEFAULT NULL COMMENT '最近上次发送时间',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '逻辑删除: 1、已删除、2、未删除',
  `gmt_created` datetime(0) NULL DEFAULT NULL COMMENT '创建',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
