-- -----------------------------------------------------
-- Table `juzhai`.`tb_show_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_show_act` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_show_act` (
  `act_id` BIGINT NOT NULL ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `category_ids` VARCHAR(100) NULL COMMENT '|id1|id2|id3|' ,
  `recent_popularity` INT(7) NOT NULL DEFAULT 0 ,
  `hot_create_time` DATETIME NOT NULL ,
  `act_end_time` DATETIME NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`act_id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_city_categoryids` ON `juzhai`.`tb_show_act` (`city` ASC, `category_ids` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_recentpopularity` ON `juzhai`.`tb_show_act` (`recent_popularity` DESC) ;

SHOW WARNINGS;
CREATE INDEX `idx_hotcreatetime` ON `juzhai`.`tb_show_act` (`hot_create_time` DESC) ;

SHOW WARNINGS;


alter table tb_profile add last_web_login_time DATETIME;