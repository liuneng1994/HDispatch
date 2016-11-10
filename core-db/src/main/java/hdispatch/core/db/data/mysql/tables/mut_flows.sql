-- ----------------------------
-- Table structure for mut_flows,designed by Deng Zhilong,
-- record by Yazheng Young
-- ----------------------------
DROP TABLE IF EXISTS `mut_flows`;
CREATE TABLE `mut_flows` (
  `flow_id` varchar(128) NOT NULL,
  `mut_flow_id` varchar(128) NOT NULL,
  `submit_date` date DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `mut_project_id` int(11) NOT NULL,
  `project_name` varchar(128) NOT NULL,
  `mut_project_name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;