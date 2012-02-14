-- -----------------------------------------------------
-- Table `juzhai`.`tb_post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_post` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_post` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `content` VARCHAR(800) NOT NULL ,
  `content_md5` VARCHAR(80) NOT NULL ,
  `place` VARCHAR(200) NULL ,
  `pic` VARCHAR(100) NULL ,
  `link` VARCHAR(200) NULL ,
  `category_id` BIGINT NOT NULL DEFAULT 0 ,
  `purpose_type` INT(2) NOT NULL DEFAULT 0 COMMENT '0-I want 1-I want people 2-I want boy 3.I want girl' ,
  `date_time` DATETIME NULL ,
  `idea_id` BIGINT NOT NULL DEFAULT 0 ,
  `response_cnt` INT(10) NOT NULL DEFAULT 0 ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `user_city` BIGINT NULL ,
  `user_gender` INT(1) NULL ,
  `verify_type` INT(1) NOT NULL DEFAULT 0 COMMENT '0-未处理 1-合格 2-屏蔽 3-好主意' ,
  `create_uid` BIGINT NOT NULL ,
  `defunct` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `uidx_createuid_ideaid` ON `juzhai`.`tb_post` (`create_uid` ASC, `idea_id` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_createuid_contentmd5` ON `juzhai`.`tb_post` (`create_uid` ASC, `content_md5` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_post_response`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_post_response` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_post_response` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `post_id` BIGINT NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_postid` ON `juzhai`.`tb_post_response` (`uid` ASC, `post_id` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_postid` ON `juzhai`.`tb_post_response` (`post_id` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_idea`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_idea` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_idea` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `content` VARCHAR(800) NOT NULL ,
  `content_md5` VARCHAR(80) NOT NULL ,
  `place` VARCHAR(200) NULL ,
  `pic` VARCHAR(100) NULL ,
  `link` VARCHAR(200) NULL ,
  `date` DATETIME NULL ,
  `category_id` BIGINT NOT NULL DEFAULT 0 ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `use_count` INT(8) NOT NULL DEFAULT 0 ,
  `first_uid` BIGINT NOT NULL DEFAULT 0 ,
  `gender` INT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_city_createtime` ON `juzhai`.`tb_idea` (`city` ASC, `create_time` DESC) ;

SHOW WARNINGS;
CREATE INDEX `idx_city_usecount` ON `juzhai`.`tb_idea` (`city` ASC, `use_count` DESC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_contentmd5` ON `juzhai`.`tb_idea` (`content_md5` ASC) ;

SHOW WARNINGS;