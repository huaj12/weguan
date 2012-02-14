SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `juzhai` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `juzhai` ;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_passport`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_passport` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_passport` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `login_name` VARCHAR(100) NOT NULL ,
  `email` VARCHAR(100) NULL ,
  `email_active` TINYINT NOT NULL DEFAULT 0 ,
  `password` VARCHAR(100) NOT NULL ,
  `admin` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  `last_login_time` DATETIME NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '用户通行证' ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_loginname` USING BTREE ON `juzhai`.`tb_passport` (`login_name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_thirdparty`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_thirdparty` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_thirdparty` (
  `id` BIGINT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `join_type` VARCHAR(45) NOT NULL ,
  `app_key` VARCHAR(45) NOT NULL ,
  `app_secret` VARCHAR(45) NOT NULL ,
  `app_url` VARCHAR(100) NULL ,
  `user_home_url` VARCHAR(100) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '第三方平台' ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_name_jointype` ON `juzhai`.`tb_thirdparty` (`name` ASC, `join_type` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_profile` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_profile` (
  `uid` BIGINT NOT NULL ,
  `nickname` VARCHAR(100) NOT NULL ,
  `logo_pic` VARCHAR(200) NULL ,
  `country` BIGINT NOT NULL DEFAULT 0 ,
  `province` BIGINT NOT NULL DEFAULT 0 ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `town` BIGINT NOT NULL DEFAULT 0 ,
  `gender` INT(1) NOT NULL DEFAULT 0 ,
  `birth_year` INT(4) NOT NULL DEFAULT 0 ,
  `birth_month` INT(2) NOT NULL DEFAULT 0 ,
  `birth_day` INT(2) NOT NULL DEFAULT 0 ,
  `qq` VARCHAR(100) NULL ,
  `msn` VARCHAR(100) NULL ,
  `email` VARCHAR(100) NULL ,
  `sub_email` TINYINT NOT NULL DEFAULT 0 ,
  `weibo` VARCHAR(100) NULL ,
  `blog` VARCHAR(100) NULL ,
  `telephone` VARCHAR(100) NULL ,
  `mobile` VARCHAR(100) NULL ,
  `finish_school` VARCHAR(45) NULL ,
  `finish_subject` VARCHAR(45) NULL ,
  `company` VARCHAR(45) NULL ,
  `profession` VARCHAR(45) NULL ,
  `constellation` VARCHAR(45) NULL ,
  `nature` VARCHAR(450) NULL ,
  `address` VARCHAR(45) NULL ,
  `home` VARCHAR(45) NULL ,
  `height` VARCHAR(45) NULL ,
  `shape` VARCHAR(45) NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`uid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '用户资料表' ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_tp_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_tp_user` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_tp_user` (
  `uid` BIGINT NOT NULL ,
  `tp_name` VARCHAR(45) NOT NULL ,
  `tp_identity` VARCHAR(100) NOT NULL ,
  `bind_uid` BIGINT NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`uid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '第三方用户表' ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_tpname_tpidentity` ON `juzhai`.`tb_tp_user` (`tp_name` ASC, `tp_identity` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_tp_user_auth`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_tp_user_auth` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_tp_user_auth` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uid` BIGINT NOT NULL ,
  `tp_id` BIGINT NOT NULL ,
  `auth_info` VARCHAR(800) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_tpid` ON `juzhai`.`tb_tp_user_auth` (`uid` ASC, `tp_id` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_act` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_act` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `category_ids` VARCHAR(45) NULL ,
  `popularity` INT(7) NOT NULL DEFAULT 0 ,
  `active` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_uid` BIGINT NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '行为' ;

SHOW WARNINGS;
CREATE INDEX `idx_active` USING BTREE ON `juzhai`.`tb_act` (`active` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_name` ON `juzhai`.`tb_act` (`name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_act_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_act_category` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_act_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '行为分类' ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_user_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_user_act` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_user_act` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `uid` BIGINT NOT NULL ,
  `act_id` BIGINT NOT NULL ,
  `hot_lev` INT(8) NOT NULL DEFAULT 1 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '用户行为' ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_actid` USING BTREE ON `juzhai`.`tb_user_act` (`uid` ASC, `act_id` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_city` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_city` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NOT NULL ,
  `province_id` BIGINT NOT NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_name` USING BTREE ON `juzhai`.`tb_city` (`name` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_provinceid_sequence` USING BTREE ON `juzhai`.`tb_city` (`province_id` ASC, `sequence` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_province`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_province` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_province` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NOT NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_name` USING BTREE ON `juzhai`.`tb_province` (`name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_random_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_random_act` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_random_act` (
  `role_code` INT(1) NOT NULL COMMENT '0.two girls 1.one boy one girl 2.two boys' ,
  `act_id` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_hot_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_hot_act` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_hot_act` (
  `act_id` BIGINT NOT NULL ,
  `tp_id` BIGINT NOT NULL COMMENT '二进制\n第一位表示kaixin\n第二位表示renren\n第三位表示weibo' ,
  `sequence` INT(1) NOT NULL DEFAULT 0 )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_account` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_account` (
  `uid` BIGINT NOT NULL ,
  `point` INT(11) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`uid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_user_guide`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_user_guide` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_user_guide` (
  `uid` BIGINT NOT NULL ,
  `guide_step` INT(2) NOT NULL DEFAULT 0 ,
  `complete` TINYINT NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`uid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_act_charts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_act_charts` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_act_charts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `act_id` BIGINT NOT NULL ,
  `role_code` INT(1) NOT NULL ,
  `sequence` INT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_actid_rolecode` USING BTREE ON `juzhai`.`tb_act_charts` (`act_id` ASC, `role_code` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_question` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_question` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `type` INT(1) NOT NULL DEFAULT 0 COMMENT '0.choice 1.true-false' ,
  `name` VARCHAR(200) NOT NULL ,
  `answer` VARCHAR(200) NOT NULL COMMENT '\"|\"分割' ,
  `invite_text` VARCHAR(200) NOT NULL COMMENT '“|” 分割' ,
  `invite_word` VARCHAR(200) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
