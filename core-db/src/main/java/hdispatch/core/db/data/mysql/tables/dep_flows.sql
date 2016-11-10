-- ----------------------------
-- Table structure for dep_flows,designed by Deng Zhilong,
-- record by Yazheng Young
-- ----------------------------
DROP TABLE IF EXISTS `dep_flows`;
CREATE TABLE `dep_flows` (
  `flow_id` varchar(128) NOT NULL,
  `dep_flow_id` varchar(128) NOT NULL,
  `submit_date` date DEFAULT NULL,
  `project_id` int(10) NOT NULL,
  `dep_project_id` int(11) NOT NULL,
  `project_name` varchar(256) DEFAULT NULL,
  `dep_project_name` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;