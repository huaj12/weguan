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

  PRIMARY KEY (`id`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci

COMMENT = '搜索热词' ;