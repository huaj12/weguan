alter table tb_act add `full_name` VARCHAR(200) NULL;
alter table tb_act add `logo` VARCHAR(200) NULL;
alter table tb_act add `intro` VARCHAR(600) NULL;
alter table tb_act add `province` BIGINT NOT NULL DEFAULT 0;
alter table tb_act add `city` BIGINT NOT NULL DEFAULT 0;
alter table tb_act add `address` VARCHAR(200) NULL;
alter table tb_act add `start_time` DATETIME NULL;
alter table tb_act add `end_time` DATETIME NULL;
alter table tb_act add `suit_gender` INT(1) NOT NULL DEFAULT 0;
alter table tb_act add `suit_age` INT(2) NOT NULL DEFAULT 0;
alter table tb_act add `suit_status` INT(2) NOT NULL DEFAULT 0;
alter table tb_act add `min_role_num` INT(5) NOT NULL DEFAULT 0;
alter table tb_act add `max_role_num` INT(5) NOT NULL DEFAULT 0;
alter table tb_act add `min_charge` INT(6) NOT NULL DEFAULT 0;
alter table tb_act add `max_charge` INT(6) NOT NULL DEFAULT 0;

alter table tb_act_category rename to tb_category;

insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('流行',5,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('常用',6,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('新奇',7,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('美食',8,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('活动',9,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('展览',10,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('演出',11,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('电影',12,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('趣味',13,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('赛事',14,'2011-11-01 09:20:46','2011-11-01 09:20:46');
insert into juzhai.tb_category (name,sequence,create_time,last_modify_time) values ('桌游',15,'2011-11-01 09:20:46','2011-11-01 09:20:46');

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_act_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `act_id` BIGINT NOT NULL ,
  `category_id` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `uidx_actid_categoryid` USING BTREE (`act_id` ASC, `category_id` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_hot_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_hot_act` ;

CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_hot_act` (
  `act_id` BIGINT NOT NULL ,
  `active` TINYINT(1) NOT NULL DEFAULT 1 ,
  `sequence` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`act_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;