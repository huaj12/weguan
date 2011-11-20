alter table tb_user_act add tp_name varchar(45);

update tb_user_act set tp_name = 'kaixin001' where id > 0;

insert into `tb_thirdparty` (`id`, `name`, `join_type`, `app_key`, `app_secret`, `app_url`, `user_home_url`, `app_id`) values('2','renren','app','ec44a955931e42519c411ae385a55aa6','f53e005764c04d778d6b97c628452c84','http://apps.renren.com/juzhaiqi/','http://www.renren.com/profile.do?id={0}','163941');