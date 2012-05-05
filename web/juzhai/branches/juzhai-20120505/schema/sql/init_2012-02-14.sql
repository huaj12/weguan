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
  `app_id` VARCHAR(45) NOT NULL ,
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
  `has_modify_nickname` TINYINT(1) NOT NULL DEFAULT 0 ,
  `logo_pic` VARCHAR(200) NULL ,
  `country` BIGINT NOT NULL DEFAULT 0 ,
  `province` BIGINT NOT NULL DEFAULT 0 ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `town` BIGINT NOT NULL DEFAULT -1 ,
  `gender` INT(1) NULL ,
  `has_modify_gender` TINYINT(1) NOT NULL DEFAULT 0 ,
  `birth_year` INT(4) NOT NULL DEFAULT 0 ,
  `birth_month` INT(2) NOT NULL DEFAULT 0 ,
  `birth_day` INT(2) NOT NULL DEFAULT 0 ,
  `birth_secret` TINYINT(1) NOT NULL DEFAULT 0 ,
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
  `profession_id` BIGINT NOT NULL DEFAULT 0 ,
  `profession` VARCHAR(60) NULL ,
  `constellation_id` BIGINT NOT NULL DEFAULT 0 ,
  `nature` VARCHAR(450) NULL ,
  `address` VARCHAR(45) NULL ,
  `home` VARCHAR(100) NULL ,
  `height` INT(3) NULL ,
  `shape` VARCHAR(45) NULL ,
  `feature` VARCHAR(800) NULL ,
  `blood_type` VARCHAR(20) NULL COMMENT 'O A B AB' ,
  `education` VARCHAR(60) NULL ,
  `house` VARCHAR(60) NULL ,
  `car` VARCHAR(60) NULL ,
  `min_monthly_income` INT(8) NULL ,
  `max_monthly_income` INT(8) NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  `last_update_time` DATETIME NOT NULL ,
  `last_web_login_time` DATETIME NULL ,
  PRIMARY KEY (`uid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '用户资料表' ;

SHOW WARNINGS;
CREATE INDEX `idx_uid_lastupdatetime` ON `juzhai`.`tb_profile` (`uid` DESC, `last_update_time` DESC) ;

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
-- Table `juzhai`.`tb_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_category` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_category` (
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
-- Table `juzhai`.`tb_city_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_city_mapping` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_city_mapping` (
  `mapping_city_name` VARCHAR(100) NOT NULL ,
  `city_id` BIGINT NOT NULL ,
  PRIMARY KEY (`mapping_city_name`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_province_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_province_mapping` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_province_mapping` (
  `mapping_province_name` VARCHAR(100) NOT NULL ,
  `province_id` BIGINT NOT NULL ,
  PRIMARY KEY (`mapping_province_name`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_town`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_town` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_town` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NOT NULL ,
  `city_id` BIGINT NOT NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_name` ON `juzhai`.`tb_town` (`name` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_cityid_sequence` ON `juzhai`.`tb_town` (`city_id` ASC, `sequence` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_profession`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_profession` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_profession` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NOT NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_constellation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_constellation` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_constellation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `start_month` INT(2) NOT NULL DEFAULT 0 ,
  `start_date` INT(2) NOT NULL DEFAULT 0 ,
  `end_month` INT(2) NOT NULL DEFAULT 0 ,
  `end_date` INT(2) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_interest_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_interest_user` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_interest_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `interest_uid` BIGINT NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_uid` ON `juzhai`.`tb_interest_user` (`uid` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_interestuid` ON `juzhai`.`tb_interest_user` (`interest_uid` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_interestuid` ON `juzhai`.`tb_interest_user` (`uid` ASC, `interest_uid` ASC) ;

SHOW WARNINGS;

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
-- Table `juzhai`.`tb_dialog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_dialog` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_dialog` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `uid` BIGINT NOT NULL ,
  `target_uid` BIGINT NOT NULL ,
  `has_new` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_uid_lastmodifytime` ON `juzhai`.`tb_dialog` (`uid` ASC, `last_modify_time` DESC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_targetuid` ON `juzhai`.`tb_dialog` (`uid` ASC, `target_uid` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_dialog_content`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_dialog_content` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_dialog_content` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `content` VARCHAR(1000) NOT NULL ,
  `sender_uid` BIGINT NOT NULL ,
  `receiver_uid` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_senderuid` ON `juzhai`.`tb_dialog_content` (`sender_uid` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_receiveruid` ON `juzhai`.`tb_dialog_content` (`receiver_uid` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_spider_url`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_spider_url` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_spider_url` (
  `md5_url` VARCHAR(32) NOT NULL ,
  `url` VARCHAR(300) NOT NULL ,
  `create_time` DATETIME NOT NULL )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '爬虫爬过的url' ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_md5url` ON `juzhai`.`tb_spider_url` (`md5_url` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_post` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_post` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `content` VARCHAR(800) NOT NULL ,
  `content_md5` VARCHAR(80) NOT NULL ,
  `place` VARCHAR(200) NULL ,
  `pic` VARCHAR(100) NULL ,
  `link` VARCHAR(200) NULL ,
  `category_id` BIGINT NOT NULL DEFAULT 0 ,
  `purpose_type` INT(2) NOT NULL DEFAULT 0 COMMENT '0-I want 1-I want people 2-I want boy 3.I want girl' ,
  `date_time` DATETIME NULL ,
  `idea_id` BIGINT NOT NULL DEFAULT 0 ,
  `response_cnt` INT(10) NOT NULL DEFAULT 0 ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `user_city` BIGINT NULL ,
  `user_gender` INT(1) NULL ,
  `verify_type` INT(1) NOT NULL DEFAULT 0 COMMENT '0-未处理 1-合格 2-屏蔽 3-好主意' ,
  `create_uid` BIGINT NOT NULL ,
  `defunct` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `uidx_createuid_ideaid` ON `juzhai`.`tb_post` (`create_uid` ASC, `idea_id` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_createuid_contentmd5` ON `juzhai`.`tb_post` (`create_uid` ASC, `content_md5` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_post_response`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_post_response` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_post_response` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `post_id` BIGINT NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_postid` ON `juzhai`.`tb_post_response` (`uid` ASC, `post_id` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_postid` ON `juzhai`.`tb_post_response` (`post_id` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_idea`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_idea` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_idea` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `content` VARCHAR(800) NOT NULL ,
  `content_md5` VARCHAR(80) NOT NULL ,
  `place` VARCHAR(200) NULL ,
  `pic` VARCHAR(100) NULL ,
  `link` VARCHAR(200) NULL ,
  `date` DATETIME NULL ,
  `category_id` BIGINT NOT NULL DEFAULT 0 ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `use_count` INT(8) NOT NULL DEFAULT 0 ,
  `first_uid` BIGINT NOT NULL DEFAULT 0 ,
  `gender` INT(1) NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_city_createtime` ON `juzhai`.`tb_idea` (`city` ASC, `create_time` DESC) ;

SHOW WARNINGS;
CREATE INDEX `idx_city_usecount` ON `juzhai`.`tb_idea` (`city` ASC, `use_count` DESC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_contentmd5` ON `juzhai`.`tb_idea` (`content_md5` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_post_window`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_post_window` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_post_window` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `post_id` BIGINT NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `purpose_type` INT(2) NOT NULL ,
  `content` VARCHAR(800) NOT NULL ,
  `sequence` INT(4) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '橱窗' ;

SHOW WARNINGS;
CREATE INDEX `idx_sequence` ON `juzhai`.`tb_post_window` (`sequence` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_postid` ON `juzhai`.`tb_post_window` (`post_id` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_ad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_ad` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_ad` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(300) NOT NULL ,
  `pic_url` VARCHAR(300) NOT NULL ,
  `city` BIGINT NOT NULL ,
  `district` VARCHAR(200) NULL ,
  `address` VARCHAR(200) NULL ,
  `start_time` DATETIME NULL ,
  `end_time` DATETIME NULL ,
  `prime_price` VARCHAR(45) NULL ,
  `price` VARCHAR(45) NULL ,
  `discount` DOUBLE NULL ,
  `source` VARCHAR(60) NOT NULL ,
  `source_link` VARCHAR(300) NULL ,
  `sequence` INT(4) NULL ,
  `link` VARCHAR(300) NOT NULL ,
  `md5_link` VARCHAR(45) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '优惠信息(前台)' ;

SHOW WARNINGS;
CREATE INDEX `idx_city` ON `juzhai`.`tb_ad` (`city` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_linkmd5` ON `juzhai`.`tb_ad` (`md5_link` ASC) ;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;