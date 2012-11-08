-- -----------------------------------------------------
-- Table `juzhai`.`tb_active_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_active_code` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_active_code` (
  `code` VARCHAR(100) NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `type` INT(1) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `expire_time` DATETIME NOT NULL ,
  PRIMARY KEY (`code`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_expiretime` ON `juzhai`.`tb_active_code` (`expire_time` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_type` ON `juzhai`.`tb_active_code` (`uid` ASC, `type` ASC) ;

SHOW WARNINGS;