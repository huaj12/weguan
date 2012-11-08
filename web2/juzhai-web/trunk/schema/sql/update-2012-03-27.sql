
ALTER TABLE `juzhai`.`tb_passport` ADD `shield_time`  datetime AFTER last_login_time;


DROP TABLE IF EXISTS `juzhai`.`tb_report` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_report` (

  `id` BIGINT NOT NULL AUTO_INCREMENT ,

  `report_uid` BIGINT NOT NULL ,

  `content_url` VARCHAR(300) NOT NULL ,

  `report_type` INT(2) NOT NULL ,

  `create_time` DATETIME NOT NULL ,

  `last_modify_time` DATETIME NOT NULL ,

  `create_uid` BIGINT NOT NULL ,

  `handle` INT(1) NOT NULL DEFAULT 0 ,

  `description` VARCHAR(600) NULL ,

  `content_type` INT(2) NOT NULL ,

  PRIMARY KEY (`id`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci;



SHOW WARNINGS;