-- ----------------------------
-- Table structure for hdispatch_group_dependency
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_group_dependency`;
CREATE TABLE `hdispatch_group_dependency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) DEFAULT NULL,
  `depent_group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;