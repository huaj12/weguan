alter table tb_profile change height height INT(3);
alter table tb_profile add blood_type VARCHAR(20) AFTER feature;
alter table tb_profile add education VARCHAR(60) AFTER blood_type;
alter table tb_profile add house VARCHAR(60) AFTER education;
alter table tb_profile add car VARCHAR(60) AFTER house;
alter table tb_profile change home home VARCHAR(100);
alter table tb_profile add min_monthly_income INT(8) AFTER car;
alter table tb_profile add max_monthly_income INT(8) AFTER min_monthly_income;