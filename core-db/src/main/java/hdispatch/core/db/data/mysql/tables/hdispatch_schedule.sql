-- ----------------------------
-- Table structure for hdispatch_schedule
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_schedule`;
CREATE TABLE `hdispatch_schedule` (
  `sch_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(11) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `flow_id` varchar(128) NOT NULL,
  `submit_date` varchar(64) NOT NULL,
  `project_name` varchar(64) NOT NULL,
  PRIMARY KEY (`sch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;