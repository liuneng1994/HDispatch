DROP TABLE `HDISPATCH_LAYER` IF EXISTS ;
CREATE TABLE `HDISPATCH_LAYER` (
`layer_id`  bigint UNSIGNED NOT NULL AUTO_INCREMENT ,
`name`  varchar(50) NOT NULL ,
`description`  varchar(255) NULL ,
`theme_id`  bigint NOT NULL ,
`seq`  int UNSIGNED NOT NULL ,
`flow_id`  varchar(128) NULL ,
PRIMARY KEY (`layer_id`)
);