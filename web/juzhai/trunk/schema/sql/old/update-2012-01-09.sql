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