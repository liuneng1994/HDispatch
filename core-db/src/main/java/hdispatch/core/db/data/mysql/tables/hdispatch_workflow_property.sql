-- ----------------------------
-- Table structure for hdispatch_workflow_property
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_workflow_property`;
CREATE TABLE `hdispatch_workflow_property` (
  `workflow_id` bigint(20) DEFAULT NULL,
  `workflow_property_name` varchar(128) DEFAULT NULL,
  `workflow_property_value` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;