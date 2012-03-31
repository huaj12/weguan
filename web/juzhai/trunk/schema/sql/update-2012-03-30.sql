
ALTER TABLE `juzhai`.`tb_preference` ADD `open_description`  TINYINT(1) NOT NULL DEFAULT 0 AFTER default_answer;

alter table tb_user_preference change description description varchar(200);