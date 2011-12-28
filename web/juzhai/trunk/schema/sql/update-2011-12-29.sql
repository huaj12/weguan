-- -----------------------------------------------------
-- Table `juzhai`.`tb_sys_notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_sys_notice` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_sys_notice` (
  `id` BIGINT NOT NULL ,
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