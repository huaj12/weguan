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
