ALTER TABLE `juzhai`.`tb_idea` ADD `create_window_time` datetime AFTER start_time;
ALTER TABLE `juzhai`.`tb_idea` ADD `interest_cnt` int(10) NOT NULL DEFAULT 0 AFTER create_window_time;
-- -----------------------------------------------------

-- Table `juzhai`.`tb_idea_interest`

-- -----------------------------------------------------

DROP TABLE IF EXISTS `juzhai`.`tb_idea_interest` ;



SHOW WARNINGS;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_idea_interest` (

  `id` BIGINT NOT NULL AUTO_INCREMENT ,

  `idea_id` BIGINT NOT NULL ,

  `uid` BIGINT NOT NULL ,

  `create_time` DATETIME NOT NULL ,

  `last_modify_time` DATETIME NOT NULL ,

  PRIMARY KEY (`id`) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8

COLLATE = utf8_general_ci

COMMENT = '好主意感兴趣' ;



SHOW WARNINGS;

CREATE INDEX `idx_ideaid` ON `juzhai`.`tb_idea_interest` (`idea_id` ASC) ;



SHOW WARNINGS;

CREATE UNIQUE INDEX `uidx_uid_ideaid` ON `juzhai`.`tb_idea_interest` (`idea_id` ASC, `uid` ASC) ;



SHOW WARNINGS;

