/*
Navicat MySQL Data Transfer

Source Server         : test_db
Source Server Version : 50621
Source Host           : 127.0.0.1:3306
Source Database       : hap_dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-09-27 17:11:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hdispatch_fnd_schedule_parameter
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_fnd_schedule_parameter`;
CREATE TABLE `hdispatch_fnd_schedule_parameter` (
  `SCHEDULE_PARA_ID` bigint(64) NOT NULL AUTO_INCREMENT,
  `PARAMETER_NAME` varchar(240) NOT NULL,
  `PARAMETER_SORT` int(11) DEFAULT NULL,
  `SUBJECT_NAME` varchar(240) NOT NULL,
  `MAPPING_NAME` varchar(240) NOT NULL,
  `SESSION_NAME` varchar(240) NOT NULL,
  `WORKFLOW_NAME` varchar(240) NOT NULL,
  `PARAMETER_VALUE` varchar(4000) NOT NULL,
  `FORMAT_MASK` varchar(30) DEFAULT NULL,
  `PARA_OFFSET` int(11) DEFAULT NULL,
  `FREQUENCY` varchar(30) DEFAULT NULL,
  `ENABLE_FLAG` varchar(1) DEFAULT NULL,
  `START_DATE_ACTIVE` datetime DEFAULT NULL,
  `END_DATE_ACTIVE` datetime DEFAULT NULL,
  `CREATION_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` int(11) NOT NULL DEFAULT '-1',
  `LAST_UPDATED_BY` int(11) NOT NULL DEFAULT '-1',
  `LAST_UPDATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_LOGIN` int(11) DEFAULT NULL,
  `PARAMETER_DESC` varchar(240) DEFAULT NULL,
  `PARAMETER_VALUE_INI` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`SCHEDULE_PARA_ID`),
  UNIQUE KEY `APD_FND_SCHEDULE_PARAMETER_U1` (`PARAMETER_NAME`,`WORKFLOW_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
