ALTER TABLE `juzhai`.`tb_idea` ADD  detail  varchar(3500)  AFTER buy_link;

delete  from `juzhai`.`tb_raw_idea`

ALTER TABLE `juzhai`.`tb_raw_idea`  MODIFY COLUMN detail VARCHAR(3500);