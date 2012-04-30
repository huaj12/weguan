-- -----------------------------------------------------

-- Table `juzhai`.`tb_search_hot`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `juzhai`.`tb_search_hot` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_search_hot` (

  `id` BIGINT NOT NULL AUTO_INCREMENT ,

  `city` BIGINT NOT NULL DEFAULT 0 ,

  `name` VARCHAR(100) NOT NULL ,

  `create_time` DATETIME NOT NULL ,

  `last_modify_time` DATETIME NOT NULL ,

  `hot` INT(10) NOT NULL DEFAULT 0 ,

  PRIMARY KEY (`id`) )

ENGINE = MyISAM

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci

COMMENT = '搜索热词' ;



SHOW WARNINGS;

CREATE INDEX `idx_city_hot` ON `juzhai`.`tb_search_hot` (`city` ASC, `hot` ASC) ;



SHOW WARNINGS;

CREATE UNIQUE INDEX `uidx_city_name` ON `juzhai`.`tb_search_hot` (`name` ASC, `city` ASC) ;



SHOW WARNINGS;