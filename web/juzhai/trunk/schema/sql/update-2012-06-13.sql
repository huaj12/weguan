ALTER TABLE `juzhai`.`tb_report` ADD `ip`  varchar(50) AFTER content_type;
-- -----------------------------------------------------

-- Table `juzhai`.`tb_ad_lock_ip`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `juzhai`.`tb_ad_lock_ip` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_ad_lock_ip` (

  `id` BIGINT NOT NULL AUTO_INCREMENT ,

  `ip` VARCHAR(50) NOT NULL ,

  `create_time` DATETIME NOT NULL ,

  PRIMARY KEY (`id`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci;



SHOW WARNINGS;

CREATE INDEX `idx_ip` ON `juzhai`.`tb_ad_lock_ip` (`ip` ASC) ;



SHOW WARNINGS;

