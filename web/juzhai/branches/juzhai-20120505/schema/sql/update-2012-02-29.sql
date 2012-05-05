alter table tb_idea add window tinyint(1) NOT NULL default 0 AFTER random;

alter table tb_post add comment_cnt int(10) NOT NULL default 0 AFTER response_cnt;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_post_comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_post_comment` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_post_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `post_id` BIGINT NOT NULL ,
  `post_content` VARCHAR(800) NOT NULL ,
  `post_create_uid` BIGINT NOT NULL DEFAULT 0 ,
  `content` VARCHAR(800) NOT NULL ,
  `content_md5` VARCHAR(80) NOT NULL ,
  `parent_id` BIGINT NOT NULL DEFAULT 0 ,
  `parent_content` VARCHAR(800) NULL ,
  `parent_create_uid` BIGINT NOT NULL DEFAULT 0 ,
  `defunct` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_uid` BIGINT NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_postid_createtime` ON `juzhai`.`tb_post_comment` (`post_id` ASC, `create_time` DESC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_postid_createuid_contentmd5` ON `juzhai`.`tb_post_comment` (`post_id` ASC, `create_uid` ASC, `content_md5` ASC) ;

SHOW WARNINGS;