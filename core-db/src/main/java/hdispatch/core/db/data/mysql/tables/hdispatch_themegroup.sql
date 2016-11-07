-- 主题组
DROP TABLE IF EXISTS `hdispatch_themegroup`;
CREATE TABLE `hdispatch_themegroup` (
`theme_group_id`  bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id' ,
`theme_group_name`  varchar(255) NOT NULL UNIQUE COMMENT '主题组名称' ,
`theme_group_desc`  varchar(255) NULL ,
PRIMARY KEY (`theme_group_id`)
)
;