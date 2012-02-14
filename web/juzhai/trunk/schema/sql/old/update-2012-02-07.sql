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
COMMENT = 'post window' ;

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