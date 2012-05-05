ALTER TABLE `juzhai`.`tb_profile` CHANGE COLUMN `last_update_time` `last_update_time` DATETIME NULL  ;
update tb_profile set last_update_time = null where uid > 0;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_preference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_preference` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_preference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(200) NOT NULL ,
  `input` VARCHAR(500) NOT NULL ,
  `defunct` TINYINT(1) NOT NULL DEFAULT 0 ,
  `type` INT(2) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  `open` TINYINT(1) NOT NULL DEFAULT 0 ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_user_preference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_user_preference` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_user_preference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `uid` BIGINT NOT NULL ,
  `preference_id` BIGINT NOT NULL ,
  `answer` VARCHAR(200) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  `open` TINYINT(1) NOT NULL DEFAULT 0 ,
  `description` VARCHAR(100) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_questionid` ON `juzhai`.`tb_user_preference` (`uid` ASC, `preference_id` ASC) ;

SHOW WARNINGS;
