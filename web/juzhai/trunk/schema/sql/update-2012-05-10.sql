ALTER TABLE `juzhai`.`tb_idea` ADD `charge`  int(6) AFTER last_modify_time;
ALTER TABLE `juzhai`.`tb_idea` ADD `town`  bigint NOT NULL DEFAULT -1 AFTER charge;
ALTER TABLE `juzhai`.`tb_idea` ADD `start_time` datetime AFTER town;
alter table tb_idea change date end_time datetime;


-- -----------------------------------------------------

-- Table `juzhai`.`tb_raw_idea`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `juzhai`.`tb_raw_idea` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_raw_idea` (

  `id` BIGINT NOT NULL AUTO_INCREMENT ,

  `content` VARCHAR(800) NOT NULL ,

  `content_md5` VARCHAR(80) NOT NULL ,

  `place` VARCHAR(200) NULL ,

  `pic` VARCHAR(100) NULL ,

  `link` VARCHAR(200) NULL ,

  `category_id` BIGINT NOT NULL DEFAULT 0 ,

  `city` BIGINT NOT NULL DEFAULT 0 ,

  `create_uid` BIGINT NOT NULL DEFAULT 0 ,

  `create_time` DATETIME NOT NULL ,

  `last_modify_time` DATETIME NOT NULL ,

  `charge` INT(6) NULL ,

  `detail` MEDIUMTEXT NULL ,

  `town` BIGINT NOT NULL DEFAULT -1 ,

  `start_time` DATETIME NULL ,

  `end_time` DATETIME NULL ,

  `idea_id` BIGINT NULL ,

  `correction_uid` BIGINT NULL ,

  PRIMARY KEY (`id`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci;



SHOW WARNINGS;

CREATE UNIQUE INDEX `uidx_contentmd5` ON `juzhai`.`tb_raw_idea` (`content_md5` ASC) ;



SHOW WARNINGS;



-- -----------------------------------------------------

-- Table `juzhai`.`tb_idea_detail`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `juzhai`.`tb_idea_detail` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_idea_detail` (

  `idea_id` BIGINT NOT NULL ,

  `detail` MEDIUMTEXT NOT NULL ,

  `create_time` DATETIME NOT NULL ,

  `last_modify_time` DATETIME NOT NULL ,

  PRIMARY KEY (`idea_id`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci

COMMENT = '好主意详情' ;



SHOW WARNINGS;