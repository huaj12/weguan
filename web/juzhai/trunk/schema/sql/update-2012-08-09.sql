ALTER TABLE `juzhai`.`tb_profile` ADD `last_user_online_time`  datetime AFTER last_web_login_time;
update tb_profile set last_user_online_time=last_web_login_time
CREATE INDEX `idx_uid_lastuseronlinetime` USING BTREE ON `juzhai`.`tb_profile` (`uid` DESC, `last_user_online_time` DESC);
