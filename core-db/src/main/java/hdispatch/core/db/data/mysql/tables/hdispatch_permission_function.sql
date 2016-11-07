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
-- 权限判定函数--by yazheng.yang@hand-china.com
-- version 2.1
-- 修复version 1.1中可能出现的一个用户对同一个主题有多个权限组合时，多对一的错误
-- 参数说明：in_theme_id       bigint        传入的主题id
-- 参数说明：in_user_id        bigint        传入的用户id
-- 参数说明：in_auth_read      VARCHAR(1)    传入的读权限：'Y'、'N'、NULL，其中为NULL表示不需要检查此权限(不等同于'N')
-- 参数说明：in_auth_operate   VARCHAR(1)    传入的操作权限：'Y'、'N'、NULL，其中为NULL表示不需要检查此权限(不等同于'N')
DROP FUNCTION IF EXISTS `hasPermission`;

CREATE FUNCTION hasPermission(in_theme_id bigint,in_user_id bigint,in_auth_read VARCHAR(1),in_auth_operate VARCHAR(1))
	RETURNS VARCHAR(1)
BEGIN
	DECLARE return_rows_num_int bigint;

	IF(in_auth_read IS NULL AND in_auth_operate IS NULL) THEN RETURN 'N';
	END IF;

	IF(in_auth_read IS NOT NULL AND in_auth_operate IS NOT NULL) THEN
		SELECT count(*)
		INTO return_rows_num_int
		FROM hdispatch_authority auth JOIN hdispatch_themegroup_theme relation
		ON auth.theme_group_id = relation.theme_group_id
		WHERE user_id=in_user_id AND theme_id=in_theme_id AND auth_read=in_auth_read AND auth_operate=in_auth_operate;
		IF(return_rows_num_int IS NOT NULL AND return_rows_num_int>0) THEN RETURN 'Y';END IF;
		RETURN 'N';
	END IF;

	IF(in_auth_read IS NOT NULL) THEN
		SELECT count(*)
		INTO return_rows_num_int
		FROM hdispatch_authority auth JOIN hdispatch_themegroup_theme relation
		ON auth.theme_group_id = relation.theme_group_id
		WHERE user_id=in_user_id AND theme_id=in_theme_id AND auth_read=in_auth_read;
		IF(return_rows_num_int IS NOT NULL AND return_rows_num_int>0) THEN RETURN 'Y';END IF;
		RETURN 'N';
	END IF;

	IF(in_auth_operate IS NOT NULL) THEN
		SELECT count(*)
		INTO return_rows_num_int
		FROM hdispatch_authority auth JOIN hdispatch_themegroup_theme relation
		ON auth.theme_group_id = relation.theme_group_id
		WHERE user_id=in_user_id AND theme_id=in_theme_id AND auth_operate=in_auth_operate;
		IF(return_rows_num_int IS NOT NULL AND return_rows_num_int>0) THEN RETURN 'Y';END IF;
		RETURN 'N';
	END IF;
END;