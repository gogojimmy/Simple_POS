/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50514
 Source Host           : localhost
 Source Database       : Simple_POS

 Target Server Version : 50514
 File Encoding         : utf-8

 Date: 01/02/2012 14:38:34 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Customer`
-- ----------------------------
DROP TABLE IF EXISTS `Customer`;
CREATE TABLE `Customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lot_no` varchar(255) DEFAULT NULL,
  `serial_no` varchar(255) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `iv_min` float DEFAULT NULL,
  `iv_avg` float DEFAULT NULL,
  `iv_max` float DEFAULT NULL,
  `d_min` float DEFAULT NULL,
  `d_avg` float DEFAULT NULL,
  `d_max` float DEFAULT NULL,
  `vf_min` float DEFAULT NULL,
  `vf_avg` float DEFAULT NULL,
  `vf_max` float DEFAULT NULL,
  `wd` varchar(255) DEFAULT NULL,
  `iv` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_shipped` bit(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_by` int(11) NOT NULL,
  `input_no` varchar(255) NOT NULL,
  `input_date` date NOT NULL,
  `in_po` varchar(255) NOT NULL,
  `in_lot` varchar(255) NOT NULL,
  `repo_id` int(11) NOT NULL,
  `stor_id` int(11) NOT NULL,
  `ship_id` int(11) NOT NULL,
  `output_no` varchar(255) DEFAULT NULL,
  `output_date` date DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `customer_order_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3290 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `repo_stor_ship`
-- ----------------------------
DROP TABLE IF EXISTS `repo_stor_ship`;
CREATE TABLE `repo_stor_ship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `is_admin` bit(1) NOT NULL,
  `is_login` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
