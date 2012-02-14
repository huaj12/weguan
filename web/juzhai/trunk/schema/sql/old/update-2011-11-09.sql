alter table tb_profile add last_update_time datetime not null;
CREATE INDEX `idx_uid_lastupdatetime` USING BTREE ON `juzhai`.`tb_profile` (`uid` DESC, `last_update_time` DESC);

update tb_profile set last_update_time = last_modify_time where uid > 0;
alter table tb_thirdparty add `app_id` varchar(45) NOT NULL;
update tb_thirdparty set app_id = '100012402' where id=1;

CREATE  TABLE `juzhai`.`tb_synonym_act` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `act_id` BIGINT NOT NULL ,
  `name` VARCHAR(100) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `uidx_name` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE  TABLE `juzhai`.`tb_search_act_action` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `user_id` BIGINT NOT NULL ,
  `user_name` VARCHAR(100) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_createtime` (`create_time` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE  TABLE `juzhai`.`tb_add_act_action` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `user_id` BIGINT NOT NULL ,
  `user_name` VARCHAR(100) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_createtime` (`create_time` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;