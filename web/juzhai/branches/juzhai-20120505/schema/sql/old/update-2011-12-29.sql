-- -----------------------------------------------------
-- Table `juzhai`.`tb_sys_notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_sys_notice` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_sys_notice` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `content` TEXT NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `idx_uid` ON `juzhai`.`tb_sys_notice` (`uid` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_create_time` ON `juzhai`.`tb_sys_notice` (`create_time` DESC) ;

SHOW WARNINGS;


-- -----------------------------------------------------
-- Table `juzhai`.`tb_user_free_date`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_user_free_date` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_user_free_date` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `uid` BIGINT NOT NULL ,
  `free_date` DATE NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_uid` ON `juzhai`.`tb_user_free_date` (`uid` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `idx_uid_freedate` ON `juzhai`.`tb_user_free_date` (`uid` ASC, `free_date` ASC) ;

SHOW WARNINGS;


CREATE SCHEMA IF NOT EXISTS `juzhai` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `juzhai` ;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_raw_ad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_raw_ad` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_raw_ad` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(300) NULL ,
  `img` VARCHAR(300) NULL ,
  `act_ids` VARCHAR(300) NULL ,
  `address` VARCHAR(200) NULL ,
  `circle` VARCHAR(45) NULL ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `original` VARCHAR(45) NULL ,
  `price` VARCHAR(45) NULL ,
  `discount` DOUBLE NULL ,
  `start_date` DATETIME NULL ,
  `end_date` DATETIME NULL ,
  `source` VARCHAR(100) NULL ,
  `target_url` VARCHAR(300) NULL ,
  `from_name` VARCHAR(45) NULL ,
  `from_link` VARCHAR(300) NULL ,
  `status` INT(2) NOT NULL DEFAULT 0 ,
  `md5_target_url` VARCHAR(45) NULL ,
  `category` VARCHAR(45) NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '导入进来的团购信息' ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_md5_url` ON `juzhai`.`tb_raw_ad` (`md5_target_url` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_city_source_status_category` ON `juzhai`.`tb_raw_ad` (`city` ASC, `source` ASC, `status` ASC, `category` ASC) ;

SHOW WARNINGS;


-- -----------------------------------------------------
-- Table `juzhai`.`tb_ad_source`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_ad_source` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_ad_source` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `create_date` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;