alter table tb_user_act add tp_name varchar(45);

update tb_user_act set tp_name = 'kaixin001' where id > 0;