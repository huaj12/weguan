-- -----------------------------------------------------
-- Table `juzhai`.`tb_face`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_face` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_face` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NOT NULL ,
  `pic` VARCHAR(30) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;
SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_name` ON `juzhai`.`tb_face` (`name` ASC) ;
SHOW WARNINGS;

INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('呵呵','smilea_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('嘻嘻','tootha_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('哈哈','laugh.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('可爱','tza_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('可怜','kl_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('挖鼻屎','kbsa_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('吃惊','cj_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('害羞','shamea_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('挤眼','zy_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('闭嘴','bz_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('鄙视','bs2_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('爱你','lovea_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('泪','sada_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('偷笑','heia_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('亲亲','qq_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('生病','sb_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('太开心','mb_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('懒得理你','ldln_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('右哼哼','yhh_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('左哼哼','zhh_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('嘘','x_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('衰','cry.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('委屈','wq_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('吐','t_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('打哈气','k_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('抱抱','bba_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('怒','angrya_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('疑问','yw_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('馋嘴','cza_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('拜拜','88_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('思考','sk_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('汗','sweata_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('困','sleepya_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('睡觉','sleepa_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('钱','money_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('失望','sw_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('酷','cool_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('花心','hsa_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('哼','hatea_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('鼓掌','gza_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('晕','dizzya_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('悲伤','bs_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('抓狂','crazya_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('黑线','h_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('阴险','yx_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('怒骂','nm_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('心','hearta_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('伤心','unheart.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('猪头','pig.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('ok','ok_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('耶','ye_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('good','good_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('不要','no_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('赞','z2_thumb.gif');	
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('来','come_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('弱','sad_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('蜡烛','lazu_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('钟','clock_thumb.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('蛋糕','cake.gif');
INSERT INTO `juzhai`.`tb_face` (`name`,`pic`) VALUES ('话筒','m_thumb.gif');