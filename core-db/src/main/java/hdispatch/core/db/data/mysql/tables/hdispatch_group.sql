-- ----------------------------
-- Table structure for hdispatch_group
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_group`;
CREATE TABLE `hdispatch_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(128) DEFAULT NULL,
  `layer_id` bigint(20) DEFAULT NULL,
  `schedule_expression` varchar(256) DEFAULT NULL,
  `flow_id` varchar(128) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
