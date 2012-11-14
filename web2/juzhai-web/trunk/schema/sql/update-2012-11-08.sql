ALTER TABLE `juzhai`.`tb_idea` ADD  buy_link  varchar(200)  AFTER link;
ALTER TABLE `juzhai`.`tb_raw_idea` ADD  buy_link  varchar(200)  AFTER link;





-- -----------------------------------------------------

-- Table `juzhai`.`tb_ios_device`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `juzhai`.`tb_ios_device` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_ios_device` (

  `device_token` VARCHAR(100) NOT NULL ,

  `uid` BIGINT NOT NULL DEFAULT 0 ,

  `create_time` DATETIME NOT NULL ,

  `last_modify_time` DATETIME NOT NULL ,

  PRIMARY KEY (`device_token`) )

ENGINE = MyISAM

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci

COMMENT = '用户和设备' ;



SHOW WARNINGS;

CREATE INDEX `idx_uid` ON `juzhai`.`tb_ios_device` (`uid` ASC) ;



SHOW WARNINGS;



