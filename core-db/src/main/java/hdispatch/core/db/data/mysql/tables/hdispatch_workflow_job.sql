-- ----------------------------
-- Table structure for hdispatch_workflow_job
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_workflow_job`;
CREATE TABLE `hdispatch_workflow_job` (
  `workflow_job_id` varchar(255) NOT NULL,
  `workflow_id` bigint(20) NOT NULL,
  `job_source_id` bigint(20) DEFAULT NULL,
  `job_type` varchar(32) DEFAULT 'job',
  `parents_job_id` blob,
  PRIMARY KEY (`workflow_job_id`,`workflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;