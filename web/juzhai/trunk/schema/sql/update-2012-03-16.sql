ALTER TABLE `juzhai`.`tb_idea` ADD `defunct` TINYINT(1) NOT NULL DEFAULT 0 AFTER create_uid;

drop index `idx_city_createtime` on `juzhai`.`tb_idea`;
drop index `idx_city_usecount` on `juzhai`.`tb_idea`;

CREATE INDEX `idx_city_defunct_createtime` ON `juzhai`.`tb_idea` (`city` ASC, `defunct` ASC, `create_time` DESC) ;
CREATE INDEX `idx_city_defunct_usecount` ON `juzhai`.`tb_idea` (`city` ASC, `defunct` ASC, `use_count` DESC) ;