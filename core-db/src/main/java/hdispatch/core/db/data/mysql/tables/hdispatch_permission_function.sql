/*
Navicat MySQL Data Transfer

Source Server         : azkaban-server
Source Server Version : 50621
Source Host           : 127.0.0.1:3306
Source Database       : azkaban

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-26 9:35:54
权限验证函数--by yazheng.yang@hand-china.com
*/
DROP FUNCTION IF EXISTS `hasPermission`;
-- 权限判定函数
-- version 1.1
CREATE FUNCTION hasPermission(in_theme_id bigint,in_user_id bigint,in_auth_read VARCHAR(1),in_auth_operate VARCHAR(1))
	RETURNS VARCHAR(1)
BEGIN
	DECLARE has_read_str VARCHAR(1);
	DECLARE has_operate_str VARCHAR(1);

	IF(in_auth_read IS NULL AND in_auth_operate IS NULL) THEN RETURN 'N';
	END IF;

	SELECT auth_read,auth_operate
	INTO has_read_str,has_operate_str
	FROM hdispatch_authority auth JOIN hdispatch_themegroup_theme relation
	ON auth.theme_group_id = relation.theme_group_id
	WHERE user_id=in_user_id AND theme_id=in_theme_id;
	IF(in_auth_read IS NOT NULL AND in_auth_operate IS NOT NULL) THEN
		IF(in_auth_read=has_read_str AND in_auth_operate=has_operate_str) THEN
			RETURN('Y');
		ELSE
			RETURN('N');
		END IF;
	ELSEIF(in_auth_read IS NOT NULL) THEN
		IF(in_auth_read=has_read_str) THEN
			RETURN('Y');
		ELSE
			RETURN('N');
		END IF;
	ELSEIF(in_auth_operate IS NOT NULL) THEN
		IF(in_auth_operate=has_operate_str) THEN
			RETURN('Y');
		ELSE
			RETURN('N');
		END IF;
	END IF;
END;
-- 测试
SELECT hasPermission(1,1,'Y','Y');
SELECT hasPermission(1,1,'Y','N');
SELECT hasPermission(2,1,'Y','Y');
SELECT hasPermission(2,1,'Y','N');
SELECT hasPermission(1,2,'Y','Y');
SELECT hasPermission(1,2,'Y','N');
SELECT hasPermission(1,1,NULL,'N');
SELECT hasPermission(1,1,NULL,NULL);

SELECT * FROM hdispatch_theme
WHERE "Y"=hasPermission(1,1,'Y','Y');

SELECT * FROM hdispatch_theme
WHERE 'Y'=hasPermission(1,1,'Y','N');
