ALTER TABLE `juzhai`.`tb_passport` ADD  device_name  varchar(20)  NOT NULL DEFAULT 'browser' AFTER use_level;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_user_position`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_user_position` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_user_position` (
  `uid` BIGINT NOT NULL ,
  `location` POINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`uid`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE SPATIAL INDEX `idx_location` ON `juzhai`.`tb_user_position` (`location` ASC) ;

SHOW WARNINGS;

alter table tb_dialog_content convert to character set utf8mb4 collate utf8mb4_bin;
set character_set_client=utf8mb4;
set character_set_connection=utf8mb4;
set character_set_database=utf8mb4;
set character_set_results=utf8mb4;
set character_set_server=utf8mb4;

--[mysqld]
--character-set-server=utf8mb4
--[mysql]
--default-character-set=utf8mb4
