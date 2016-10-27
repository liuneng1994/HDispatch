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
-- 测试和使用示例
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


-- 权限判定函数
-- version 2.0
-- 修复version 1.1中可能出现的一个用户对同一个主题有多个权限组合时，多对一的错误
-- 参数说明：in_theme_id       bigint        传入的主题id
-- 参数说明：in_user_id        bigint        传入的用户id
-- 参数说明：in_auth_read      VARCHAR(1)    传入的读权限：'Y'、'N'、NULL，其中为NULL表示不需要检查此权限(不等同于'N')
-- 参数说明：in_auth_operate   VARCHAR(1)    传入的操作权限：'Y'、'N'、NULL，其中为NULL表示不需要检查此权限(不等同于'N')
DROP FUNCTION IF EXISTS `hasPermission`;

CREATE FUNCTION hasPermission(in_theme_id bigint,in_user_id bigint,in_auth_read VARCHAR(1),in_auth_operate VARCHAR(1))
	RETURNS VARCHAR(1)
BEGIN
	DECLARE has_read_str VARCHAR(1);
	DECLARE has_operate_str VARCHAR(1);

	IF(in_auth_read IS NULL AND in_auth_operate IS NULL) THEN RETURN 'N';
	END IF;

	IF(in_auth_read IS NOT NULL AND in_auth_operate IS NOT NULL) THEN
		SELECT auth_read,auth_operate
		INTO has_read_str,has_operate_str
		FROM hdispatch_authority auth JOIN hdispatch_themegroup_theme relation
		ON auth.theme_group_id = relation.theme_group_id
		WHERE user_id=in_user_id AND theme_id=in_theme_id AND auth_read=in_auth_read AND auth_operate=in_auth_operate;
		IF(has_read_str IS NOT NULL AND has_operate_str IS NOT NULL) THEN RETURN 'Y';END IF;
		RETURN 'N';
	END IF;

	IF(in_auth_read IS NOT NULL) THEN
		SELECT auth_read,auth_operate
		INTO has_read_str,has_operate_str
		FROM hdispatch_authority auth JOIN hdispatch_themegroup_theme relation
		ON auth.theme_group_id = relation.theme_group_id
		WHERE user_id=in_user_id AND theme_id=in_theme_id AND auth_read=in_auth_read;
		IF(has_read_str IS NOT NULL) THEN RETURN 'Y'; END IF;
		RETURN 'N';
	END IF;

	IF(in_auth_operate IS NOT NULL) THEN
		SELECT auth_read,auth_operate
		INTO has_read_str,has_operate_str
		FROM hdispatch_authority auth JOIN hdispatch_themegroup_theme relation
		ON auth.theme_group_id = relation.theme_group_id
		WHERE user_id=in_user_id AND theme_id=in_theme_id AND auth_operate=in_auth_operate;
		IF(has_operate_str IS NOT NULL) THEN RETURN 'Y'; END IF;
		RETURN 'N';
	END IF;
END;