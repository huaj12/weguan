alter table tb_profile add new_logo_pic VARCHAR(200) AFTER max_monthly_income;
alter table tb_profile add logo_verify_state INT(1) NOT NULL DEFAULT 0 AFTER new_logo_pic;

update tb_profile set new_logo_pic = logo_pic,logo_verify_state = 1 where uid > 0;
update tb_profile set logo_pic = null where uid > 0;