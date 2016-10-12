-- ----------------------------
-- Table structure for hdispatch_job
-- ----------------------------
DROP TABLE IF EXISTS `hdispatch_job`;
CREATE TABLE `hdispatch_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `svn` varchar(256) DEFAULT NULL,
  `active` tinyint(4) DEFAULT '1',
  `theme_id` bigint(20) DEFAULT NULL,
  `layer_id` bigint(20) DEFAULT NULL,
  `OBJECT_VERSION_NUMBER` decimal(20,0) DEFAULT '1',
  `REQUEST_ID` bigint(20) DEFAULT '-1',
  `PROGRAM_ID` bigint(20) DEFAULT '-1',
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` decimal(20,0) DEFAULT '-1',
  `LAST_UPDATED_BY` decimal(20,0) DEFAULT '-1',
  `LAST_UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_LOGIN` decimal(20,0) DEFAULT NULL,
  `ATTRIBUTE_CATEGORY` varchar(30) DEFAULT NULL,
  `ATTRIBUTE1` varchar(240) DEFAULT NULL,
  `ATTRIBUTE2` varchar(240) DEFAULT NULL,
  `ATTRIBUTE3` varchar(240) DEFAULT NULL,
  `ATTRIBUTE4` varchar(240) DEFAULT NULL,
  `ATTRIBUTE5` varchar(240) DEFAULT NULL,
  `ATTRIBUTE6` varchar(240) DEFAULT NULL,
  `ATTRIBUTE7` varchar(240) DEFAULT NULL,
  `ATTRIBUTE8` varchar(240) DEFAULT NULL,
  `ATTRIBUTE9` varchar(240) DEFAULT NULL,
  `ATTRIBUTE10` varchar(240) DEFAULT NULL,
  `ATTRIBUTE11` varchar(240) DEFAULT NULL,
  `ATTRIBUTE12` varchar(240) DEFAULT NULL,
  `ATTRIBUTE13` varchar(240) DEFAULT NULL,
  `ATTRIBUTE14` varchar(240) DEFAULT NULL,
  `ATTRIBUTE15` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;