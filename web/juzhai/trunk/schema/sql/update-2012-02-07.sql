-- -----------------------------------------------------
-- Table `juzhai`.`tb_post_window`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_post_window` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_post_window` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `post_id` BIGINT NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `purpose_type` INT(2) NOT NULL ,
  `content` VARCHAR(800) NOT NULL ,
  `sequence` INT(4) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = 'post window' ;

SHOW WARNINGS;
CREATE INDEX `idx_sequence` ON `juzhai`.`tb_post_window` (`sequence` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_postid` ON `juzhai`.`tb_post_window` (`post_id` ASC) ;

SHOW WARNINGS;
