-- ----------------------------
-- Table structure for hdispatch_job_property
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_job_property`;
CREATE TABLE `hdispatch_job_property` (
  `job_id` bigint(20) DEFAULT NULL,
  `job_property_name` varchar(45) DEFAULT NULL,
  `job_property_value` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;