-- 主题组和主题之间关系表
DROP TABLE IF EXISTS `hdispatch_themegroup_theme`;
CREATE TABLE `hdispatch_themegroup_theme` (
`themegroup_theme_id`  bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '当前映射关系表的id' ,
`theme_group_id`  bigint NOT NULL COMMENT '主题组id' ,
`theme_id`  bigint(20) NOT NULL COMMENT '主题id' ,
PRIMARY KEY (`themegroup_theme_id`)
)
;