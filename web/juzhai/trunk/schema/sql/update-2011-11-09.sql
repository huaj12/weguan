alter table tb_profile add last_update_time datetime not null;
CREATE INDEX `idx_uid_lastupdatetime` USING BTREE ON `juzhai`.`tb_profile` (`uid` DESC, `last_update_time` DESC);

update tb_profile set last_update_time = last_modify_time where uid > 0;