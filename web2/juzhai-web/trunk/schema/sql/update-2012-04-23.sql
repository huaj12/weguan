-- -----------------------------------------------------
-- Table `juzhai`.`tb_login_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_login_log` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_login_log` (
  `uid` BIGINT NOT NULL ,
  `ip` VARCHAR(50) NOT NULL ,
  `auto_login` TINYINT(1) NOT NULL DEFAULT 0 ,
  `login_time` DATETIME NOT NULL )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_uid` ON `juzhai`.`tb_login_log` (`uid` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_ip` ON `juzhai`.`tb_login_log` (`ip` ASC) ;

SHOW WARNINGS;