DROP TABLE IF EXISTS  HDISPATCH_THEME;
CREATE TABLE `HDISPATCH_THEME` (
`theme_id`  bigint UNSIGNED NOT NULL AUTO_INCREMENT ,
`name`  varchar(50) NOT NULL ,
`description`  varchar(255) NULL ,
`project_id`  int NULL ,
`project_name`  varchar(255) NOT NULL ,
`project_version`  int(11) NOT NULL DEFAULT 1 ,
`project_description`  varchar(255) NULL ,
PRIMARY KEY (`theme_id`)
);