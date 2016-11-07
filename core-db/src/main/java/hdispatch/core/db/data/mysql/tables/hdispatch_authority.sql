-- 主题组和hap用户之间的权限关系表
DROP TABLE IF EXISTS `hdispatch_authority`;
CREATE TABLE `hdispatch_authority` (
`authority_id`  bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id' ,
`theme_group_id`  bigint NOT NULL ,
`user_id`  bigint(20) NOT NULL ,
`auth_read`  varchar(1) NOT NULL DEFAULT 'Y' COMMENT '读权限' ,
`auth_operate`  varchar(1) NOT NULL DEFAULT 'N' COMMENT '操作权限' ,
PRIMARY KEY (`authority_id`)
)
;