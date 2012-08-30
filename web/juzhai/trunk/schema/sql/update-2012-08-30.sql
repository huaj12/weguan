SHOW WARNINGS;

USE `juzhai` ;



-- -----------------------------------------------------

-- Table `juzhai`.`tb_idea_position`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `juzhai`.`tb_idea_position` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_idea_position` (

  `idea_id` BIGINT NOT NULL ,

  `location` POINT NOT NULL ,

  `create_time` DATETIME NOT NULL ,

  `last_modify_time` DATETIME NOT NULL ,

  PRIMARY KEY (`idea_id`) )

ENGINE = MyISAM

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci;



SHOW WARNINGS;

CREATE SPATIAL INDEX `idx_location` ON `juzhai`.`tb_idea_position` (`location` ASC) ;



SHOW WARNINGS;