-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hap_dev
-- -----------------------------------------------------

USE `hap_dev` ;

-- -----------------------------------------------------
-- Table `hap_dev`.`hdispatch_theme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hap_dev`.`hdispatch_theme` ;

CREATE TABLE IF NOT EXISTS `hap_dev`.`hdispatch_theme` (
  `theme_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NULL,
  `description` VARCHAR(256) NULL,
  `active` TINYINT NULL DEFAULT 1,
  `display_sequence` INT NULL DEFAULT 0,
  OBJECT_VERSION_NUMBER DECIMAL(20, 0) DEFAULT 1,
  REQUEST_ID            BIGINT         DEFAULT -1,
  PROGRAM_ID            BIGINT         DEFAULT -1,
  CREATION_DATE         DATETIME       DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY            DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATED_BY       DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATE_DATE      DATETIME       DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN     DECIMAL(20, 0),
  ATTRIBUTE_CATEGORY    VARCHAR(30),
  ATTRIBUTE1            VARCHAR(240),
  ATTRIBUTE2            VARCHAR(240),
  ATTRIBUTE3            VARCHAR(240),
  ATTRIBUTE4            VARCHAR(240),
  ATTRIBUTE5            VARCHAR(240),
  ATTRIBUTE6            VARCHAR(240),
  ATTRIBUTE7            VARCHAR(240),
  ATTRIBUTE8            VARCHAR(240),
  ATTRIBUTE9            VARCHAR(240),
  ATTRIBUTE10           VARCHAR(240),
  ATTRIBUTE11           VARCHAR(240),
  ATTRIBUTE12           VARCHAR(240),
  ATTRIBUTE13           VARCHAR(240),
  ATTRIBUTE14           VARCHAR(240),
  ATTRIBUTE15           VARCHAR(240)
  PRIMARY KEY (`theme_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hap_dev`.`hdispatch_layer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hap_dev`.`hdispatch_layer` ;

CREATE TABLE IF NOT EXISTS `hap_dev`.`hdispatch_layer` (
  `layer_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NULL,
  `description` VARCHAR(256) NULL,
  `active` TINYINT NULL DEFAULT 1,
  `display_sequence` INT NULL DEFAULT 0,
  `theme_id` BIGINT NULL,
  OBJECT_VERSION_NUMBER DECIMAL(20, 0) DEFAULT 1,
  REQUEST_ID            BIGINT         DEFAULT -1,
  PROGRAM_ID            BIGINT         DEFAULT -1,
  CREATION_DATE         DATETIME       DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY            DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATED_BY       DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATE_DATE      DATETIME       DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN     DECIMAL(20, 0),
  ATTRIBUTE_CATEGORY    VARCHAR(30),
  ATTRIBUTE1            VARCHAR(240),
  ATTRIBUTE2            VARCHAR(240),
  ATTRIBUTE3            VARCHAR(240),
  ATTRIBUTE4            VARCHAR(240),
  ATTRIBUTE5            VARCHAR(240),
  ATTRIBUTE6            VARCHAR(240),
  ATTRIBUTE7            VARCHAR(240),
  ATTRIBUTE8            VARCHAR(240),
  ATTRIBUTE9            VARCHAR(240),
  ATTRIBUTE10           VARCHAR(240),
  ATTRIBUTE11           VARCHAR(240),
  ATTRIBUTE12           VARCHAR(240),
  ATTRIBUTE13           VARCHAR(240),
  ATTRIBUTE14           VARCHAR(240),
  ATTRIBUTE15           VARCHAR(240)
  PRIMARY KEY (`layer_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hap_dev`.`hdispatch_job`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hap_dev`.`hdispatch_job` ;

CREATE TABLE IF NOT EXISTS `hap_dev`.`hdispatch_job` (
  `job_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `svn` VARCHAR(256) NULL,
  `active` TINYINT NULL DEFAULT 1,
  `theme_id` BIGINT NULL,
  `layer_id` BIGINT NULL,
  OBJECT_VERSION_NUMBER DECIMAL(20, 0) DEFAULT 1,
  REQUEST_ID            BIGINT         DEFAULT -1,
  PROGRAM_ID            BIGINT         DEFAULT -1,
  CREATION_DATE         DATETIME       DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY            DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATED_BY       DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATE_DATE      DATETIME       DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN     DECIMAL(20, 0),
  ATTRIBUTE_CATEGORY    VARCHAR(30),
  ATTRIBUTE1            VARCHAR(240),
  ATTRIBUTE2            VARCHAR(240),
  ATTRIBUTE3            VARCHAR(240),
  ATTRIBUTE4            VARCHAR(240),
  ATTRIBUTE5            VARCHAR(240),
  ATTRIBUTE6            VARCHAR(240),
  ATTRIBUTE7            VARCHAR(240),
  ATTRIBUTE8            VARCHAR(240),
  ATTRIBUTE9            VARCHAR(240),
  ATTRIBUTE10           VARCHAR(240),
  ATTRIBUTE11           VARCHAR(240),
  ATTRIBUTE12           VARCHAR(240),
  ATTRIBUTE13           VARCHAR(240),
  ATTRIBUTE14           VARCHAR(240),
  ATTRIBUTE15           VARCHAR(240)
  PRIMARY KEY (`job_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hap_dev`.`hdispatch_job_property`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hap_dev`.`hdispatch_job_property` ;

CREATE TABLE IF NOT EXISTS `hap_dev`.`hdispatch_job_property` (
  `job_id` BIGINT NULL,
  `job_property_name` VARCHAR(45) NULL,
  `job_property_value` VARCHAR(45) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hap_dev`.`hdispatch_workflow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hap_dev`.`hdispatch_workflow` ;

CREATE TABLE IF NOT EXISTS `hap_dev`.`hdispatch_workflow` (
  `workflow_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NULL,
  `project_name` VARCHAR(64) NULL,
  `flow_id` VARCHAR(128) NULL,
  `description` VARCHAR(256) NULL,
  `theme_id` BIGINT NULL,
  `layer_id` BIGINT NULL,
  OBJECT_VERSION_NUMBER DECIMAL(20, 0) DEFAULT 1,
  REQUEST_ID            BIGINT         DEFAULT -1,
  PROGRAM_ID            BIGINT         DEFAULT -1,
  CREATION_DATE         DATETIME       DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY            DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATED_BY       DECIMAL(20, 0) DEFAULT -1,
  LAST_UPDATE_DATE      DATETIME       DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN     DECIMAL(20, 0),
  ATTRIBUTE_CATEGORY    VARCHAR(30),
  ATTRIBUTE1            VARCHAR(240),
  ATTRIBUTE2            VARCHAR(240),
  ATTRIBUTE3            VARCHAR(240),
  ATTRIBUTE4            VARCHAR(240),
  ATTRIBUTE5            VARCHAR(240),
  ATTRIBUTE6            VARCHAR(240),
  ATTRIBUTE7            VARCHAR(240),
  ATTRIBUTE8            VARCHAR(240),
  ATTRIBUTE9            VARCHAR(240),
  ATTRIBUTE10           VARCHAR(240),
  ATTRIBUTE11           VARCHAR(240),
  ATTRIBUTE12           VARCHAR(240),
  ATTRIBUTE13           VARCHAR(240),
  ATTRIBUTE14           VARCHAR(240),
  ATTRIBUTE15           VARCHAR(240)
  PRIMARY KEY (`workflow_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hap_dev`.`hdispatch_workflow_property`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hap_dev`.`hdispatch_workflow_property` ;

CREATE TABLE IF NOT EXISTS `hap_dev`.`hdispatch_workflow_property` (
  `workflow_id` BIGINT NULL,
  `workflow_property_name` VARCHAR(128) NULL,
  `workflow_property_value` VARCHAR(256) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hap_dev`.`hdispatch_workflow_job`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hap_dev`.`hdispatch_workflow_job` ;

CREATE TABLE IF NOT EXISTS `hap_dev`.`hdispatch_workflow_job` (
  `workflow_id` VARCHAR(45) NULL,
  `job_source_id` BIGINT NULL,
  `job_type` VARCHAR(32) NULL DEFAULT 'job',
  `parent_id` BIGINT NULL)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
