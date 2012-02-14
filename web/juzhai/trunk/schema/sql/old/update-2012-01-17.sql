update tb_act set active = false where id > 0;
update tb_act set active = true where id in (select act_id from tb_act_detail);